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
import com.vaadin.data.Container;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.bff.javampd.objects.MPDSong;
import pt.yellowduck.ramboia.backend.Server;
import pt.yellowduck.ramboia.frontend.MusicTable;
import pt.yellowduck.ramboia.frontend.Player;
import pt.yellowduck.ramboia.frontend.Song;
import pt.yellowduck.ramboia.frontend.SongContainer;


@SuppressWarnings("serial")
public class RamboiaApplication extends Application implements ClickListener {

	private Server server;
	private Player player;
	private MusicTable musicTable;
	
	@Override
	public void init() {
		server = new Server();
		setTheme("runo");
		buildMainLayout();
	}

	private void buildMainLayout() {
		setMainWindow(new Window("RamboIA"));

		VerticalLayout layout = new VerticalLayout();

		MusicTable musicTable = new MusicTable(this);
		player = new Player(this);
		
		layout.setSizeFull();
		layout.addComponent(new Player(this));
		layout.addComponent(musicTable);

		getMainWindow().setContent(layout);
	}

	public Container getTableData() {
		SongContainer container = null;
		try {
			container = new SongContainer();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if(container != null){
			for(MPDSong song : server.getAllSongs()){
				Song s = new Song();
				s.setFilename(song.getName());
				container.addBean(s);
			}
		}
		return container;
	}

	public void buttonPlayClick(ClickEvent event) {
		server.play();
	}

	public void buttonStopClick(ClickEvent event) {
		server.stop();
	}
	
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public Song getCurrentSong(){
		return server.getCurrentSong();
	}
}