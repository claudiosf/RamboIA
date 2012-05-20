package pt.yellowduck.ramboia.backend.model;

import java.io.Serializable;


import com.vaadin.data.util.BeanItemContainer;

public class SongContainer extends BeanItemContainer<Song> implements Serializable {

	/**
	 * Natural property order for Person bean. Used in tables and forms.
	 */
	public static final Object[] NATURAL_COL_ORDER = new Object[] { "Artist", "Title" };

	/**
	 * "Human readable" captions for properties in same order as in
	 * NATURAL_COL_ORDER.
	 */
	public static final String[] COL_HEADERS_ENGLISH = new String[] { "Artist", "Title" };
	
	public SongContainer() throws InstantiationException, IllegalAccessException {
		super(Song.class);
	}
}
