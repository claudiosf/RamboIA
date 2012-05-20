package pt.yellowduck.ramboia.frontend.playlist;

import com.vaadin.data.Container;
import com.vaadin.ui.AbstractComponent;
import java.util.List;
import pt.yellowduck.ramboia.backend.model.Song;

public interface PlaylistInterface {

	public void setPresenter( PlaylistPresenter presenter );

	public Song getSelectedSong();

	public interface PlaylistPresenter {
		
		public AbstractComponent getView();

		public Song getSelectedSong();

		public void play( Song song );

		public Container getContainer();
	}
	
}
