package pt.yellowduck.ramboia.frontend.playlist;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import pt.yellowduck.ramboia.backend.model.Song;

public class PlaylistView extends Panel implements PlaylistInterface {

	private PlaylistPresenter presenter = null;

	private final Table tablePlaylist = new Table();

	public PlaylistView(PlaylistPresenter presenter) {
		setPresenter(presenter);
		setupComponents();
		setupLayout();
	}

	private void setupComponents() {
		setCaption("Playlist");
		tablePlaylist.setImmediate( true );
		tablePlaylist.setSelectable(true);
		tablePlaylist.setContainerDataSource(presenter.getContainer());
		tablePlaylist.addListener( new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick( ItemClickEvent itemClickEvent ) {
				if ( itemClickEvent.isDoubleClick() ) {
					if ( presenter != null ) {
						Song selectedSong = getSelectedSong();
						presenter.play( selectedSong );
					}
				}
			}
		});
	}

	private void setupLayout() {
		addComponent( tablePlaylist );
	}

	@Override
	public void setPresenter( PlaylistPresenter presenter ) {
		this.presenter = presenter;
	}

	@Override
	public Song getSelectedSong() {
		Object value = tablePlaylist.getValue();
		if ( value instanceof Song ) {
			return ( Song ) value;
		}
		return null;
	}
}
