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

import org.bff.javampd.objects.MPDAlbum;
import org.bff.javampd.objects.MPDArtist;
import org.bff.javampd.objects.MPDSong;

public class Song {

	public static final String COLUMN_ARTIST = "artist";
	
	public static final String COLUMN_TITLE = "title";
	
	public static final String COLUMN_LENGTH = "length";
	
	private final MPDSong song;

	public Song( MPDSong song ) {
		this.song = song;
	}

	public MPDSong getSong() {
		return song;
	}

	public String getTitle(){
		String result = song.getTitle();
		if ( result == null ) {
			result = song.getFile();
		}
		return result;
	}
	
	public String getArtist(){
		MPDArtist artist = song.getArtist();
		return artist == null ? null : artist.getName();
	}
        
        public String getAlbum() {
            MPDAlbum album = song.getAlbum();
            return album == null ? null : album.getName();
        }
	
	public int getLength(){
		return song.getLength();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Song song1 = (Song) o;

		if (song != null ? !song.equals(song1.song) : song1.song != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return song != null ? song.hashCode() : 0;
	}

	@Override
	public String toString() {
		return song.getFile();
	}
}
