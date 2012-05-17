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
package pt.yellowduck.ramboia.frontend;

import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;
import pt.yellowduck.ramboia.backend.model.Song;

public class SongContainer extends BeanItemContainer<Song> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Object[] NATURAL_COL_ORDER = new Object[] {"filename"};
	
	public static final String[] COL_HEADERS_ENGLISH = new String[] {"File"};
	
	public SongContainer() throws InstantiationException,	IllegalAccessException {
		super(Song.class);
	}
}
