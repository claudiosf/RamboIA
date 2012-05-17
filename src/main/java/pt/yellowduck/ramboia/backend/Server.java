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

import org.bff.javampd.MPD;
import org.bff.javampd.MPDDatabase;
import org.bff.javampd.MPDPlayer;
import org.bff.javampd.MPDPlaylist;
import org.bff.javampd.events.TrackPositionChangeEvent;
import org.bff.javampd.events.TrackPositionChangeListener;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.monitor.MPDStandAloneMonitor;
import org.bff.javampd.objects.MPDSong;
import pt.yellowduck.ramboia.backend.model.Song;

public class Server {

	private static final int DEFAULT_PORT = 6600;

	private MPD mpd;
	private MPDPlayer player;
	private MPDDatabase database;
	private MPDPlaylist playlist;

	public Server( String host ) throws MPDConnectionException, UnknownHostException {
		this( host, DEFAULT_PORT );
	}
	
	public Server( String host, int port ) throws MPDConnectionException, UnknownHostException {
		mpd = new MPD( host, port );
		player = mpd.getMPDPlayer();
		playlist = mpd.getMPDPlaylist();
		database = mpd.getMPDDatabase();

		MPDStandAloneMonitor monitor = new MPDStandAloneMonitor( mpd );
		monitor.addTrackPositionChangeListener( new TrackPositionChangeListener() {
			@Override
			public void trackPositionChanged( TrackPositionChangeEvent trackPositionChangeEvent ) {
				System.out.println( "Track Position Changed : " + trackPositionChangeEvent.getElapsedTime() );
			}
		});
		Thread threadMonitor = new Thread( monitor );
		threadMonitor.setName( "StandAlone Monitor" );
		threadMonitor.start();
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

	public void play( MPDSong song ) throws MPDConnectionException, MPDPlayerException {
		player.playId( song );
	}

	public void stop() throws MPDConnectionException, MPDPlayerException {
		player.stop();
	}

}
