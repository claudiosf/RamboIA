package com.test.ramboia;

import java.net.UnknownHostException;
import java.util.Collection;

import org.bff.javampd.MPD;
import org.bff.javampd.MPDDatabase;
import org.bff.javampd.MPDPlayer;
import org.bff.javampd.MPDPlaylist;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.objects.MPDSong;

public class Server {

	private MPD mpd;
	private MPDPlayer player;
	private MPDDatabase database;
	private MPDPlaylist playlist;
	
	public Server(){
		try {
			mpd = new MPD("localhost", 6600);
			player = mpd.getMPDPlayer();
			playlist = mpd.getMPDPlaylist();
			database = mpd.getMPDDatabase();
		} catch(MPDConnectionException e) {
			System.out.println("Error Connecting:"+e.getMessage());
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public Collection<MPDSong> getAllSongs(){
		try {
			return database.listAllSongs();
		} catch (MPDDatabaseException e) {
			e.printStackTrace();
		} catch (MPDConnectionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
