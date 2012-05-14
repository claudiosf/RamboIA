package com.test.ramboia;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Slider;

public class Player extends HorizontalLayout{
	
	private Button.ClickListener listener;
	private Button play;
	private Button stop;
	private Slider slider;
	
	public Player(Button.ClickListener listener){
		this.listener = listener;
		
		play = new Button("play", listener, "buttonPlayClick");
		stop = new Button("stop", listener, "buttonStopClick");

		slider = new Slider(1, 100);
		slider.setOrientation(Slider.ORIENTATION_HORIZONTAL);
		slider.setWidth("50%");
		
		this.addComponent(play);
		this.addComponent(stop);
		this.addComponent(slider);
	}

}
