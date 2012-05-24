package pt.yellowduck.ramboia.frontend.playlist;

import java.util.List;
import pt.yellowduck.ramboia.backend.model.Song;
import pt.yellowduck.ramboia.frontend.RamboIAView;

public interface PlaylistInterface extends RamboIAView< PlaylistController > {

	public void fill( List< Song > playlistSongs );

	public interface PlaylistPresenter {

		public void play( Song song );
		
		public void removeSelectedSongs( List< Song > selectedSongs );
	}
	
}
