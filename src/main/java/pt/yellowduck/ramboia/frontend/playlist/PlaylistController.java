package pt.yellowduck.ramboia.frontend.playlist;

import java.util.LinkedList;
import java.util.List;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.exception.MPDPlaylistException;
import pt.yellowduck.ramboia.RamboiaApplication;
import pt.yellowduck.ramboia.backend.RamboIAAdapter;
import pt.yellowduck.ramboia.backend.RamboIALogger;
import pt.yellowduck.ramboia.backend.model.Song;
import pt.yellowduck.ramboia.frontend.RamboIAController;

public class PlaylistController extends RamboIAController< PlaylistInterface > implements PlaylistInterface.PlaylistPresenter{

	public PlaylistController( PlaylistView view, RamboiaApplication application ) {
		super( view, application );

		view.fill(fetchSongs());

		application.getServer().addStateListener( new RamboIAAdapter() {
			@Override
			public void playlistChanged() {
				PlaylistController.this.view.fill( fetchSongs() );
			}
		} );
	}

	@Override
	public void play( Song song ) {
		if ( song != null ) {
			try {
				application.getServer().play( song.getSong() );
			} catch ( MPDConnectionException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			} catch ( MPDPlayerException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			} catch ( MPDPlaylistException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void removeSelectedSongs( List<Song> selectedSongs ) {
		try {
			application.getServer().removePlaylistSongs( selectedSongs );
		} catch ( MPDPlaylistException e ) {
			RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
		} catch ( MPDConnectionException e ) {
			RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
		}
	}

	public List< Song > fetchSongs() {
		List< Song > result = null;
		if ( application != null ) {
			try {
				result = application.getServer().listAllPlaylistSongs();
			} catch ( Exception e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			}
		}
		return result != null ? result : new LinkedList<Song>();
	}

}
