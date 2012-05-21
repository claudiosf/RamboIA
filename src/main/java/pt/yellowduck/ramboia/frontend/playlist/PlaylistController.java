package pt.yellowduck.ramboia.frontend.playlist;

import com.vaadin.ui.AbstractComponent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.exception.MPDPlaylistException;
import org.bff.javampd.objects.MPDSong;
import pt.yellowduck.ramboia.RamboiaApplication;
import pt.yellowduck.ramboia.backend.model.Song;
import pt.yellowduck.ramboia.backend.model.SongContainer;

public class PlaylistController implements PlaylistInterface.PlaylistPresenter{

	private final RamboiaApplication application;
	private final PlaylistView PlaylistView;
	
	public PlaylistController( RamboiaApplication application ) {
		this.application = application;
		PlaylistView = new PlaylistView(this);
	}

	@Override
	public AbstractComponent getView() {
		return PlaylistView;
	}

	@Override
	public Song getSelectedSong() {
		return PlaylistView.getSelectedSong();
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
				Collection< MPDSong > songs = application.getServer().listAllPlaylistSongs();
				for ( MPDSong song : songs ) {
					System.out.println(song.toString());
					result.add( new Song( song ) );
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public SongContainer getContainer() {
		SongContainer container = null;
		try {
			container = new SongContainer();
			container.addAll(fetchSongs());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return container;
	}
}
