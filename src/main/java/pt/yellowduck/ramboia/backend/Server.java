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
import org.bff.javampd.MPDFile;
import org.bff.javampd.MPDPlayer;
import org.bff.javampd.MPDPlaylist;
import org.bff.javampd.events.PlayerBasicChangeEvent;
import org.bff.javampd.events.PlayerBasicChangeListener;
import org.bff.javampd.events.PlaylistBasicChangeEvent;
import org.bff.javampd.events.PlaylistBasicChangeListener;
import org.bff.javampd.events.TrackPositionChangeEvent;
import org.bff.javampd.events.TrackPositionChangeListener;
import org.bff.javampd.events.VolumeChangeEvent;
import org.bff.javampd.events.VolumeChangeListener;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.exception.MPDPlaylistException;
import org.bff.javampd.exception.MPDResponseException;
import org.bff.javampd.monitor.MPDStandAloneMonitor;
import org.bff.javampd.objects.MPDSong;
import pt.yellowduck.ramboia.backend.model.Song;
import pt.yellowduck.ramboia.backend.model.SongFile;
import pt.yellowduck.ramboia.backend.monitor.Monitor;
import pt.yellowduck.ramboia.backend.monitor.MonitorsHolder;


public class Server {

	private static final int DEFAULT_PORT = 6600;

	private MPD mpd;
	private MPDPlayer player;
	private MPDDatabase database;
	private MPDPlaylist playlist;

	private Monitor monitorHolder = null;
	private MPDStandAloneMonitor monitor = null;

	private final List<RamboIAListener> playerListeners = new LinkedList<RamboIAListener>();


	public Server( String host ) throws MPDConnectionException, UnknownHostException {
		this( host, DEFAULT_PORT );
	}

	public Server( String host, int port ) throws MPDConnectionException, UnknownHostException {
		mpd = new MPD( host, port );
		player = mpd.getMPDPlayer();
		playlist = mpd.getMPDPlaylist();
		database = mpd.getMPDDatabase();

		monitor = new MPDStandAloneMonitor( mpd, 1 );
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
		monitor.addPlaylistChangeListener( new PlaylistBasicChangeListener() {
			@Override
			public void playlistBasicChange( PlaylistBasicChangeEvent playlistBasicChangeEvent ) {
				firePlaylistChanged( playlistBasicChangeEvent.getId() );
			}
		});
		monitor.addVolumeChangeListener( new VolumeChangeListener() {
			@Override
			public void volumeChanged( VolumeChangeEvent volumeChangeEvent ) {
				fireVolumeChanged( volumeChangeEvent.getVolume() );
			}
		});

		monitorHolder = new Monitor( monitor );
		monitorHolder.start();
		MonitorsHolder.getInstance().addMonitor( monitorHolder );
	}

	public void close() throws MPDConnectionException, MPDResponseException {
		playerListeners.clear();

		player = null;
		playlist = null;
		database = null;

		MonitorsHolder.getInstance().destroyMonitor( monitorHolder );

		mpd.close();
	}

	public void addStateListener( RamboIAListener listener ) {
		if ( listener != null ) {
			playerListeners.add( listener );
		}
	}

	public void removeStateListener( RamboIAListener listener ) {
		if ( listener != null ) {
			playerListeners.remove( listener );
		}
	}

	private void fireTrackPositionChanged( long elapsedTime ) {
		try {
			Song currentSong = getCurrentSong();
			for ( RamboIAListener listener : playerListeners ) {
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
				for ( RamboIAListener listener : playerListeners ) {
					listener.playerStarted( currentSong );
				}
			} catch ( MPDConnectionException e ) {
				e.printStackTrace();
			} catch ( MPDPlayerException e ) {
				e.printStackTrace();
			}
			break;
		case PlayerBasicChangeEvent.PLAYER_STOPPED :
			for ( RamboIAListener listener : playerListeners ) {
				listener.playerStopped();
			}
			break;
		case PlayerBasicChangeEvent.PLAYER_PAUSED :
			for ( RamboIAListener listener : playerListeners ) {
				listener.playerPaused();
			}
			break;
		case PlayerBasicChangeEvent.PLAYER_UNPAUSED :
			for ( RamboIAListener listener : playerListeners ) {
				listener.playerUnpaused();
			}
			break;
		}
	}

	private void firePlaylistChanged( int state ) {
		switch ( state ) {
		case PlaylistBasicChangeEvent.SONG_ADDED :
			for ( RamboIAListener listener : playerListeners ) {
				listener.songAdded();
			}
			break;
		case PlaylistBasicChangeEvent.SONG_DELETED :
			for ( RamboIAListener listener : playerListeners ) {
				listener.songDeleted();
			}
			break;
		case PlaylistBasicChangeEvent.SONG_CHANGED :
			for ( RamboIAListener listener : playerListeners ) {
				listener.songChanged();
			}
			break;
		case PlaylistBasicChangeEvent.PLAYLIST_CHANGED :
			for ( RamboIAListener listener : playerListeners ) {
				listener.playlistChanged();
			}
			break;
		case PlaylistBasicChangeEvent.PLAYLIST_ENDED :
			for ( RamboIAListener listener : playerListeners ) {
				listener.playlistEnded();
			}
			break;
		}
	}

	private void fireVolumeChanged( int volume ) {
		for ( RamboIAListener listener : playerListeners ) {
			listener.volumeChanged( volume );
		}
	}

	public List< SongFile > listLibrary() throws MPDConnectionException, MPDDatabaseException {
		return processRoots( database, database.listRootDirectory() );
	}

	private List<SongFile> processRoots( MPDDatabase db, Collection< MPDFile > roots ) throws MPDConnectionException, MPDDatabaseException {
		List< SongFile > result = new LinkedList<SongFile>();
		for ( MPDFile root : roots ) {
			if ( root.isDirectory() ) {
				SongFile dir = new SongFile( root );
				List< SongFile > childrens = processRoots( db, db.listDirectory( root ) );
				dir.setChildrens( childrens );
				result.add( dir );
			} else {
				result.add( new SongFile( root ) );
			}
		}
		return result;
	}

	public Collection<MPDSong> listAllSongs() throws MPDConnectionException, MPDDatabaseException {
		return database.listAllSongs();
	}

	public Song getCurrentSong() throws MPDConnectionException, MPDPlayerException {
		Song result = null;
		MPDSong currentSong = player.getCurrentSong();
		if ( currentSong != null ) {
			result = new Song( currentSong );
		}
		return result;
	}

	public void play() throws MPDPlayerException, MPDConnectionException {
		player.play();
	}

	public void play( MPDSong song ) throws MPDConnectionException, MPDPlayerException, MPDPlaylistException {
		// FIXME this does not work - the requested song is added to the playlist, but its not the one playing
		List< MPDSong > playlistSongs = playlist.getSongList();
		if ( playlistSongs == null || ! playlistSongs.contains( song ) ) {
			playlist.addSong( song );
		}
		player.playId( song );
	}

	public void play( MPDFile file ) throws MPDConnectionException, MPDDatabaseException, MPDPlaylistException, MPDPlayerException {
		Song song = findSong( file );
		if ( song != null ) {
			play( song.getSong() );
		}
	}

	private Song findSong( MPDFile file ) throws MPDConnectionException, MPDDatabaseException {
		Song result = null;
		Collection< MPDSong > found = database.find( MPDDatabase.ScopeType.FILENAME, file.getPath() );
		if ( found != null ) {
			for ( MPDSong song : found ) {
				result = new Song( song );
				break;
			}
		}
		return result;
	}

	public void stop() throws MPDConnectionException, MPDPlayerException {
		player.stop();
	}

	public void next() throws MPDConnectionException, MPDPlayerException {
		player.playNext();
	}

	public void previous() throws MPDConnectionException, MPDPlayerException {
		player.playPrev();
	}

	public List< Song > listAllPlaylistSongs() throws MPDConnectionException, MPDDatabaseException {
		List< Song > result = new LinkedList<Song>();
		List< MPDSong > songList = playlist.getSongList();
		if ( songList != null ) {
			for ( MPDSong song : songList ) {
				result.add( new Song( song ) );
			}
		}
		return result;
	}

	public boolean clearPlaylist() throws MPDPlaylistException, MPDConnectionException {
		return playlist.clearPlaylist();
	}

	public void removePlaylistSongs( List< Song > songs ) throws MPDPlaylistException, MPDConnectionException {
		if ( songs != null ) {
			for ( Song song : songs ) {
				playlist.removeSong( song.getSong() );
			}
		}
	}

	public int getVolume() throws MPDConnectionException, MPDPlayerException {
		return player.getVolume();
	}

	public void setVolume( int volume ) throws MPDConnectionException, MPDPlayerException {
		player.setVolume( volume );
	}

	public void addToPlaylist(SongFile selected) throws MPDPlaylistException, MPDConnectionException {
		playlist.addFileOrDirectory(selected.getFile());
	}
}
