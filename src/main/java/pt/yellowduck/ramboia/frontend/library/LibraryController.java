package pt.yellowduck.ramboia.frontend.library;

import com.vaadin.ui.AbstractComponent;
import java.util.LinkedList;
import java.util.List;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.exception.MPDPlaylistException;
import pt.yellowduck.ramboia.RamboiaApplication;
import pt.yellowduck.ramboia.backend.model.SongFile;

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
		
		libraryView.fillLibrary( getLibrary() );
	}

	@Override
	public AbstractComponent getView() {
		return libraryView;
	}

	@Override
	public void play( SongFile songfile ) {
		if ( songfile != null ) {
			try {
				application.getServer().play( songfile.getFile() );
			} catch ( MPDConnectionException e ) {
				e.printStackTrace();
			} catch ( MPDPlayerException e ) {
				e.printStackTrace();
			} catch ( MPDPlaylistException e ) {
				e.printStackTrace();
			} catch ( MPDDatabaseException e ) {
				e.printStackTrace();
			}
		}
	}

	private List< SongFile > getLibrary() {
		List< SongFile > result = null;
		try {
			result = application.getServer().listLibrary();
		} catch ( MPDConnectionException e ) {
			e.printStackTrace();
		} catch ( MPDDatabaseException e ) {
			e.printStackTrace();
		}
		return result != null ? result : new LinkedList<SongFile>();
	}

}
