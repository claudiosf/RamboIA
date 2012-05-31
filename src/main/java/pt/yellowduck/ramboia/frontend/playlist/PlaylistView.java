package pt.yellowduck.ramboia.frontend.playlist;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pt.yellowduck.ramboia.backend.RamboIALogger;
import pt.yellowduck.ramboia.backend.model.Song;
import pt.yellowduck.ramboia.backend.model.SongContainer;
import pt.yellowduck.ramboia.frontend.Utils;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

public class PlaylistView extends Panel implements PlaylistInterface {

	private PlaylistPresenter presenter = null;

	private final Table tablePlaylist = new Table();

	private final Action actionRemove = new Action( "Remove" );

	private final Action[] SELECTED_ACTIONS = new Action[] { actionRemove };

	private SongContainer songContainer = null;

	public PlaylistView() {
		setupComponents();
		setupLayout();
	}

	private void setupComponents() {
		setCaption( "Playlist" );

		try {
			songContainer = new SongContainer();
		} catch ( InstantiationException e ) {
			RamboIALogger.notify( getWindow(), "Error", e.getLocalizedMessage() );
		} catch ( IllegalAccessException e ) {
			RamboIALogger.notify( getWindow(), "Error", e.getLocalizedMessage() );
		}

		tablePlaylist.setImmediate( true );
		tablePlaylist.setSelectable( true );
		tablePlaylist.setMultiSelect( true );
		tablePlaylist.setSortDisabled(true);
		tablePlaylist.setContainerDataSource( songContainer );
		tablePlaylist.setVisibleColumns( new String[] { Song.COLUMN_ARTIST, Song.COLUMN_TITLE, Song.COLUMN_LENGTH } );
		tablePlaylist.setColumnHeaders( new String[] { "Artist", "Title", "Length" } );
		tablePlaylist.setColumnAlignment( Song.COLUMN_LENGTH, Table.ALIGN_RIGHT );
		tablePlaylist.addGeneratedColumn( Song.COLUMN_LENGTH, new Table.ColumnGenerator() {
			@Override
			public Component generateCell( Table source, Object itemId, Object columnId ) {
				Label result = new Label();
				Item item = source.getItem( itemId );
				Property itemProperty = item.getItemProperty( columnId );
				if ( itemProperty.getType().equals( Integer.class ) ) {
					result.setValue( Utils.formatTime( ( Integer ) itemProperty.getValue(), TimeUnit.SECONDS ) );
				}
				return result;
			}
		} );
		tablePlaylist.addActionHandler( new Action.Handler() {
			@Override
			public Action[] getActions( Object target, Object sender ) {
				return SELECTED_ACTIONS;
			}

			@Override
			public void handleAction( Action action, Object sender, Object target ) {
				if ( actionRemove.equals( action ) ) {
					removeSelected();
				}
			}
		});
		tablePlaylist.addListener( new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick( ItemClickEvent itemClickEvent ) {
				if ( itemClickEvent.isDoubleClick() ) {
					if ( presenter != null ) {
						List< Song > selectedSongs = getSelectedRows();
						if ( selectedSongs != null && ! selectedSongs.isEmpty() ) {
							presenter.play( selectedSongs.get( 0 ) );
						}
					}
				}
			}
		});
	}

	private void setupLayout() {
		setWidth( "100%" );
		tablePlaylist.setSizeFull();

		addComponent( tablePlaylist );
	}

	@Override
	public void setPresenter( PlaylistController presenter ) {
		this.presenter = presenter;
	}

	@Override
	public void fill( List<Song> playlistSongs ) {
		if ( songContainer != null ) {
			songContainer.removeAllItems();
			if ( playlistSongs != null ) {
				songContainer.addAll( playlistSongs );
			}
			tablePlaylist.refreshRowCache();
		}
	}

	private void removeSelected() {
		if ( presenter != null ) {
			presenter.removeSelectedSongs( getSelectedRows() );
		}
	}

	private List< Song > getSelectedRows() {
		List< Song > result = new LinkedList<Song>();

		Object value = tablePlaylist.getValue();
		if ( value instanceof Collection< ? > ) {
			// multi select mode
			for ( Object valueObject : ( Collection< ? > ) value ) {
				if ( valueObject instanceof Song ) {
					result.add( ( Song ) valueObject );
				}
			}
		} else if ( value instanceof Song ) {
			// single select mode
			result.add( ( Song ) value );
		}
		return result;
	}
}
