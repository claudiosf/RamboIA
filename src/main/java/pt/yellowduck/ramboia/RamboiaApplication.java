/*
 * Copyright (C) 2012 yellowduck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.yellowduck.ramboia;

import com.vaadin.Application;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.net.UnknownHostException;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDResponseException;
import pt.yellowduck.ramboia.backend.RamboIALogger;
import pt.yellowduck.ramboia.backend.Server;
import pt.yellowduck.ramboia.frontend.library.LibraryController;
import pt.yellowduck.ramboia.frontend.library.LibraryView;
import pt.yellowduck.ramboia.frontend.player.PlayerController;
import pt.yellowduck.ramboia.frontend.player.PlayerView;
import pt.yellowduck.ramboia.frontend.playlist.PlaylistController;
import pt.yellowduck.ramboia.frontend.playlist.PlaylistView;


public class RamboiaApplication extends Application {

	private Server server;

	private final Window mainWindow = new Window( "RamboIA" );

	private final PlayerView viewPlayer = new PlayerView();

	private final LibraryView viewLibrary = new LibraryView();
	
	private final PlaylistView viewPlaylist = new PlaylistView();

	@Override
	public void init() {
		setTheme( "runo" );
		setMainWindow( mainWindow );

		try {
			this.server = new Server("127.0.0.1");
		} catch ( MPDConnectionException e ) {
			RamboIALogger.notify(getMainWindow(), "Error", e.getLocalizedMessage());
		} catch ( UnknownHostException e ) {
			RamboIALogger.notify(getMainWindow(), "Error", e.getLocalizedMessage());
		}

		new PlayerController( viewPlayer, this );
		new PlaylistController( viewPlaylist, this );
		new LibraryController( viewLibrary, this );

		buildMainLayout();
	}


	@Override
	public void close() {
		try {
			getServer().close();
		} catch ( MPDConnectionException e ) {
			e.printStackTrace();
		} catch ( MPDResponseException e ) {
			e.printStackTrace();
		}
	}

	private void buildMainLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		layout.addComponent( viewPlayer );
		HorizontalLayout hLayout = new HorizontalLayout();
		hLayout.setWidth( "100%" );
		hLayout.addComponent( viewLibrary );
		hLayout.addComponent( viewPlaylist );
		layout.addComponent(hLayout);
		layout.setComponentAlignment( viewPlayer, Alignment.TOP_CENTER);

		mainWindow.setContent( layout );
	}

	public Server getServer() {
		return server;
	}

}
