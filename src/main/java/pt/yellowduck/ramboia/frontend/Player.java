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
package pt.yellowduck.ramboia.frontend;

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
