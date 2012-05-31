package pt.yellowduck.ramboia.frontend.library;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import pt.yellowduck.ramboia.backend.model.SongFile;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:21
 */
public class LibraryView extends VerticalLayout implements LibraryInterface {

	private LibraryPresenter presenter = null;

	private final Panel panelScrollable = new Panel( "Music Library");

	private final Tree treeLibrary = new Tree();

	public LibraryView() {
		setupComponents();
		setupLayout();
	}

	private void setupComponents() {
		treeLibrary.setImmediate( true );
		treeLibrary.addListener( new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick( ItemClickEvent itemClickEvent ) {
				if ( itemClickEvent.isDoubleClick() ) {
					if ( presenter != null ) {
						SongFile selected = (SongFile) getSelectedSong();
							presenter.addToPlaylist(selected);
					}
				}
			}
		});
	}
	
	private void setupLayout() {
		treeLibrary.setSizeUndefined();

		panelScrollable.setWidth( "100%" );
		panelScrollable.setHeight( "300px" );
		panelScrollable.getContent().setSizeUndefined();
		panelScrollable.addComponent( treeLibrary );
		addComponent(panelScrollable);
	}

	@Override
	public void setPresenter( LibraryController presenter ) {
		this.presenter = presenter;
	}

	@Override
	public Object getSelectedSong() {
		return treeLibrary.getValue();
	}

	@Override
	public void fillLibrary( List<SongFile> songFiles ) {
		fillLibrary( songFiles, null );
	}

	public void fillLibrary( List< SongFile > files, SongFile parent ) {
		if ( files != null ) {
			for ( SongFile file : files ) {
				treeLibrary.addItem( file );
				treeLibrary.setChildrenAllowed( file, file.isDirectory() );
				treeLibrary.setParent( file, parent );
				fillLibrary( file.getChildrens(), file );
			}
		}
	}
}
