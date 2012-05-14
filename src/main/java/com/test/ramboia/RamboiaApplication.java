package com.test.ramboia;

import org.bff.javampd.objects.MPDSong;

import com.vaadin.Application;
import com.vaadin.data.Container;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Sample application layout, similar (almost identical) to Apple iTunes.
 * 
 * @author IT Mill Ltd.
 * 
 */
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

	public void buttonClick(ClickEvent event) {
		Button button = event.getButton();
		if(button != null)
			player.setTitle(button.getCaption());
	}
}