package pt.yellowduck.ramboia.frontend.library;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import pt.yellowduck.ramboia.backend.model.Song;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:21
 */
public class LibraryView extends VerticalLayout implements LibraryInterface {

	private LibraryPresenter presenter = null;

	private final Tree treeLibrary = new Tree( "Music Library" );

	public LibraryView() {
		setupComponents();
		setupLayout();
	}

	private void setupComponents() {
		treeLibrary.addListener( new ItemClickEvent.ItemClickListener() {
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
		addComponent( treeLibrary );
	}

	@Override
	public void setPresenter( LibraryPresenter presenter ) {
		this.presenter = presenter;
	}

	@Override
	public void fillLibrary( List< Song > songs ) {
		if ( songs != null ) {
			for ( Song song : songs ) {
				treeLibrary.addItem( song );
			}
		}
	}

	@Override
	public Song getSelectedSong() {
		Object value = treeLibrary.getValue();
		if ( value instanceof Song ) {
			return ( Song ) value;
		}
		return null;
	}
}
