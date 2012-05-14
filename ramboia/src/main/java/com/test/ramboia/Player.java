package com.test.ramboia;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class Player extends HorizontalLayout{
	
	private Button.ClickListener listener;
	private Button play;
	private Button stop;
	private TextField title;
	
	public Player(Button.ClickListener listener){
		this.listener = listener;
		
		play = new Button("play", listener);
		stop = new Button("stop", listener);
		title = new TextField();
		
		title.setEnabled(false);
		
		this.addComponent(title);
		this.addComponent(play);
		this.addComponent(stop);
	}

	public void setTitle(String t){
		title.setValue(t);
	}
}
