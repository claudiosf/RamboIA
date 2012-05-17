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
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

import org.bff.javampd.exception.MPDConnectionException;
import pt.yellowduck.ramboia.backend.Server;
import pt.yellowduck.ramboia.frontend.library.LibraryController;
import pt.yellowduck.ramboia.frontend.library.LibraryInterface;
import pt.yellowduck.ramboia.frontend.player.PlayerController;
import pt.yellowduck.ramboia.frontend.player.PlayerInterface;


public class RamboiaApplication extends Application {

	private Server server;

	private PlayerInterface.PlayerPresenter playerPresenter;

	private LibraryInterface.LibraryPresenter libraryPresenter;

	@Override
	public void init() {
		setTheme( "runo" );

		try {
			this.server = new Server("192.168.1.7");
		} catch ( MPDConnectionException e ) {
			e.printStackTrace();
		} catch ( UnknownHostException e ) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.playerPresenter = new PlayerController( this );
		this.libraryPresenter = new LibraryController( this );

		buildMainLayout();
	}

	private void buildMainLayout() {
		setMainWindow( new Window( "RamboIA" ) );
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		AbstractComponent pp = playerPresenter.getView();
		layout.addComponent( pp );
		layout.addComponent( libraryPresenter.getView() );
		layout.setComponentAlignment(pp, Alignment.TOP_CENTER);
		getMainWindow().setContent( layout );
	}

	public Server getServer() {
		return server;
	}

}
