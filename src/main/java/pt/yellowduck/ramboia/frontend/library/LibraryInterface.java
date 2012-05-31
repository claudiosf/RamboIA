package pt.yellowduck.ramboia.frontend.library;

import java.util.List;
import pt.yellowduck.ramboia.backend.model.SongFile;
import pt.yellowduck.ramboia.frontend.RamboIAView;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:32
 */
public interface LibraryInterface extends RamboIAView< LibraryController > {

	public void fillLibrary( List<SongFile> songs );

	public Object getSelectedSong();

	public interface LibraryPresenter {

		public void play( SongFile song );

		public void addToPlaylist(SongFile selected);
	}
	
}
