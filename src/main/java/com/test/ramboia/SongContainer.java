package com.test.ramboia;

import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class SongContainer extends BeanItemContainer<Song> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Object[] NATURAL_COL_ORDER = new Object[] {"filename"};
	
	public static final String[] COL_HEADERS_ENGLISH = new String[] {"File"};
	
	public SongContainer() throws InstantiationException,	IllegalAccessException {
		super(Song.class);
	}
}
