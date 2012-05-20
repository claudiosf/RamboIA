package pt.yellowduck.ramboia.frontend.library;

import com.vaadin.ui.AbstractComponent;
import java.util.List;
import pt.yellowduck.ramboia.backend.model.SongFile;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:32
 */
public interface LibraryInterface {

	public void setPresenter( LibraryPresenter presenter );

	public void fillLibrary( List<SongFile> songs );

	public SongFile getSelectedSong();

	public interface LibraryPresenter {
		
		public AbstractComponent getView();

		public void play( SongFile song );
	}
	
}
