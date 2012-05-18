package pt.yellowduck.ramboia.frontend.library;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.exception.MPDPlaylistException;
import org.bff.javampd.objects.MPDSong;

import pt.yellowduck.ramboia.RamboiaApplication;
import pt.yellowduck.ramboia.backend.model.Song;

import com.vaadin.ui.AbstractComponent;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:33
 */
public class LibraryController implements LibraryInterface.LibraryPresenter{

	private final RamboiaApplication application;

	private final LibraryView libraryView = new LibraryView();

	public LibraryController( RamboiaApplication application ) {
		this.application = application;
		this.libraryView.setPresenter( this );

		libraryView.fillLibrary( fetchSongs() );
	}

	@Override
	public AbstractComponent getView() {
		return libraryView;
	}

	@Override
	public Song getSelectedSong() {
		return libraryView.getSelectedSong();
	}

	@Override
	public void play( Song song ) {
		if ( song != null ) {
			try {
				application.getServer().play( song.getSong() );
			} catch ( MPDConnectionException e ) {
				e.printStackTrace();
			} catch ( MPDPlayerException e ) {
				e.printStackTrace();
			} catch ( MPDPlaylistException e ) {
				e.printStackTrace();
			}
		}
	}

	private List< Song > fetchSongs() {
		List< Song > result = new LinkedList<Song>();
		if ( application != null ) {
			try {
				Collection< MPDSong > songs = application.getServer().listAllSongs();
				for ( MPDSong song : songs ) {
					result.add( new Song( song ) );
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
