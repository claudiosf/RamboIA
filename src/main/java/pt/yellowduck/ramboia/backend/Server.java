/*
 * Copyright (C) 2012 yellowduck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.yellowduck.ramboia.backend;

import java.net.UnknownHostException;
import java.util.Collection;

import java.util.LinkedList;
import java.util.List;
import org.bff.javampd.MPD;
import org.bff.javampd.MPDDatabase;
import org.bff.javampd.MPDPlayer;
import org.bff.javampd.MPDPlaylist;
import org.bff.javampd.events.PlayerBasicChangeEvent;
import org.bff.javampd.events.PlayerBasicChangeListener;
import org.bff.javampd.events.TrackPositionChangeEvent;
import org.bff.javampd.events.TrackPositionChangeListener;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.exception.MPDPlaylistException;
import org.bff.javampd.exception.MPDResponseException;
import org.bff.javampd.monitor.MPDStandAloneMonitor;
import org.bff.javampd.objects.MPDSong;
import pt.yellowduck.ramboia.backend.model.Song;

public class Server {

	private static final boolean DEBUG = true;
	
	private static final int DEFAULT_PORT = 6600;

	private MPD mpd;
	private MPDPlayer player;
	private MPDDatabase database;
	private MPDPlaylist playlist;

	private Thread threadMonitor = null;
	private MPDStandAloneMonitor monitor = null;

	private final List< PlayerStateListener > playerListeners = new LinkedList< PlayerStateListener >();


	public Server( String host ) throws MPDConnectionException, UnknownHostException {
		this( host, DEFAULT_PORT );
	}
	
	public Server( String host, int port ) throws MPDConnectionException, UnknownHostException {
		mpd = new MPD( host, port );
		player = mpd.getMPDPlayer();
		playlist = mpd.getMPDPlaylist();
		database = mpd.getMPDDatabase();

		monitor = new MPDStandAloneMonitor( mpd );
		monitor.addTrackPositionChangeListener( new TrackPositionChangeListener() {
			@Override
			public void trackPositionChanged( TrackPositionChangeEvent trackPositionChangeEvent ) {
				fireTrackPositionChanged( trackPositionChangeEvent.getElapsedTime() );
			}
		});
		monitor.addPlayerChangeListener( new PlayerBasicChangeListener() {
			@Override
			public void playerBasicChange( PlayerBasicChangeEvent playerBasicChangeEvent ) {
				firePlayerStateChanged( playerBasicChangeEvent.getId() );
			}
		});
		threadMonitor = new Thread( monitor );
		threadMonitor.setName( "StandAlone Monitor" );
		threadMonitor.start();
	}

	public void close() throws MPDConnectionException, MPDResponseException {
		playerListeners.clear();

		player = null;
		playlist = null;
		database = null;

		monitor.stop();
		threadMonitor.interrupt();
		threadMonitor = null;

		mpd.close();
	}

	public void addStateListener( PlayerStateListener listener ) {
		if ( listener != null ) {
			playerListeners.add( listener );
		}
	}

	public void removeStateListener( PlayerStateListener listener ) {
		if ( listener != null ) {
			playerListeners.remove( listener );
		}
	}

	private void fireTrackPositionChanged( long elapsedTime ) {
		try {
			Song currentSong = getCurrentSong();
			debug( "Server . fireTrackPositionChanged : " + currentSong + " -> " + elapsedTime + "s" );
			for ( PlayerStateListener listener : playerListeners ) {
				listener.trackPositionChanged( currentSong, elapsedTime );
			}
		} catch ( MPDConnectionException e ) {
			e.printStackTrace();
		} catch ( MPDPlayerException e ) {
			e.printStackTrace();
		}
	}

	private void firePlayerStateChanged( int state ) {
		switch ( state ) {
			case PlayerBasicChangeEvent.PLAYER_STARTED :
				try {
					Song currentSong = getCurrentSong();
					debug( "Server . firePlayerStateChanged . started : " + currentSong );
					for ( PlayerStateListener listener : playerListeners ) {
						listener.playerStarted( currentSong );
					}
				} catch ( MPDConnectionException e ) {
					e.printStackTrace();
				} catch ( MPDPlayerException e ) {
					e.printStackTrace();
				}
				break;
			case PlayerBasicChangeEvent.PLAYER_STOPPED :
				debug( "Server . firePlayerStateChanged . stopped : " );
				for ( PlayerStateListener listener : playerListeners ) {
					listener.playerStopped();
				}
				break;
			case PlayerBasicChangeEvent.PLAYER_PAUSED :
				for ( PlayerStateListener listener : playerListeners ) {
					listener.playerPaused();
				}
				break;
			case PlayerBasicChangeEvent.PLAYER_UNPAUSED :
				for ( PlayerStateListener listener : playerListeners ) {
					listener.playerUnpaused();
				}
				break;
		}
	}

	public Song getCurrentSong() throws MPDConnectionException, MPDPlayerException {
		Song result = null;
		MPDSong currentSong = player.getCurrentSong();
		if ( currentSong != null ) {
			result = new Song( currentSong );
		}
		return result;
	}

	public Collection<MPDSong> listAllSongs() throws MPDConnectionException, MPDDatabaseException {
		return database.listAllSongs();
	}

	public void play() throws MPDPlayerException, MPDConnectionException {
		player.play();
	}

	public void play( MPDSong song ) throws MPDConnectionException, MPDPlayerException, MPDPlaylistException {
		List< MPDSong > playlistSongs = playlist.getSongList();
		if ( playlistSongs == null || ! playlistSongs.contains( song ) ) {
			playlist.addSong( song );
		}
		player.playId( song );
	}

	public void stop() throws MPDConnectionException, MPDPlayerException {
		player.stop();
	}

	private void debug( String msg ) {
		if ( DEBUG ) {
			System.out.println( msg );
		}
	}
}
