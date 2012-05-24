package pt.yellowduck.ramboia.backend.model;

import com.vaadin.data.util.BeanItemContainer;
import java.io.Serializable;

public class SongContainer extends BeanItemContainer<Song> implements Serializable {

	public SongContainer() throws InstantiationException, IllegalAccessException {
		super(Song.class);
	}


}
