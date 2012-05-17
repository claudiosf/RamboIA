package pt.yellowduck.ramboia.frontend.library;

import com.vaadin.ui.AbstractComponent;
import java.util.List;
import pt.yellowduck.ramboia.backend.model.Song;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:32
 */
public interface LibraryInterface {

	public void setPresenter( LibraryPresenter presenter );

	public void fillLibrary( List< Song > songs );

	public Song getSelectedSong();

	public interface LibraryPresenter {
		
		public AbstractComponent getView();

		public Song getSelectedSong();

		public void play( Song song );
	}
	
}
