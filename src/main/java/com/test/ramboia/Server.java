package com.test.ramboia;

import java.net.UnknownHostException;
import java.util.Collection;

import org.bff.javampd.MPD;
import org.bff.javampd.MPDDatabase;
import org.bff.javampd.MPDPlayer;
import org.bff.javampd.MPDPlaylist;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.exception.MPDPlayerException;
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

	public Song getCurrentSong(){
		MPDSong song = null;
		try {
			song = player.getCurrentSong();
		} catch (MPDPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MPDConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(song != null){
			Song current = new Song();
			current.setFilename(song.getName());
			return current;
		}
		else{
			return null;
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

	public void play(){
		try {
			player.play();
		} catch (MPDPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MPDConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop(){
		try {
			player.stop();
		} catch (MPDPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MPDConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
