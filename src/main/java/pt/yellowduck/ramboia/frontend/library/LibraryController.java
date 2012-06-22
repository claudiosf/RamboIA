package pt.yellowduck.ramboia.frontend.library;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.exception.MPDPlaylistException;
import pt.yellowduck.ramboia.RamboiaApplication;
import pt.yellowduck.ramboia.backend.RamboIALogger;
import pt.yellowduck.ramboia.backend.model.Song;
import pt.yellowduck.ramboia.backend.model.SongFile;
import pt.yellowduck.ramboia.frontend.RamboIAController;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:33
 */
public class LibraryController extends RamboIAController< LibraryInterface > implements LibraryInterface.LibraryPresenter{

	public LibraryController( LibraryView view, RamboiaApplication application ) {
		super( view, application );
		
		view.fillLibrary( getLibrary() );
                
                view.fillLibraryTags( getArtists() );
	}

	@Override
	public void play( SongFile songfile ) {
		if ( songfile != null ) {
			try {
				application.getServer().play( songfile.getFile() );
			} catch ( MPDConnectionException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			} catch ( MPDPlayerException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			} catch ( MPDPlaylistException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			} catch ( MPDDatabaseException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			}
		}
	}
        
        
        private List< String > getArtists() {
            
            List<String> artistList = new LinkedList<String> ();
            
            try {
                Collection<Song> songList = application.getServer().listAllSongs();
            
                for(Song song : songList) {
                    
                    String artist = song.getArtist();
                    
                    if(!artistList.contains(artist)) {
                        artistList.add(artist);
                    }
                    
                }
                
                
            } catch (MPDConnectionException ex) {
                Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MPDDatabaseException ex) {
                Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return artistList;
            
        }
        

	private List< SongFile > getLibrary() {
		List< SongFile > result = null;
		try {
			result = application.getServer().listLibrary();
		} catch ( MPDConnectionException e ) {
			RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
		} catch ( MPDDatabaseException e ) {
			RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
		}
		return result != null ? result : new LinkedList<SongFile>();
	}

	@Override
	public void addToPlaylist(SongFile selected) {
		try {
			getApplication().getServer().addToPlaylist(selected);
		} catch (MPDPlaylistException e) {
			e.printStackTrace();
		} catch (MPDConnectionException e) {
			e.printStackTrace();
		}
	}

}
