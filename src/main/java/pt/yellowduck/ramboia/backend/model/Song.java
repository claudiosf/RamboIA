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
package pt.yellowduck.ramboia.backend.model;

import org.bff.javampd.objects.MPDArtist;
import org.bff.javampd.objects.MPDSong;

public class Song {

	private static final long serialVersionUID = 1L;
	
	private final MPDSong song;

	public Song( MPDSong song ) {
		this.song = song;
	}

	public MPDSong getSong() {
		return song;
	}

	@Override
	public String toString() {
		return song.getFile();
	}

	public String getTitle(){
		return song.getTitle();
	}
	
	public String getArtist(){
		MPDArtist artist = song.getArtist();
		return artist.getName();
	}
	
	public int getLenght(){
		return song.getLength();
	}
}
