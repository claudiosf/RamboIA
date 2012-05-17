package pt.yellowduck.ramboia.frontend.player;

import com.vaadin.ui.AbstractComponent;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:26
 */
public interface PlayerInterface {

	public void setPresenter( PlayerPresenter presenter );

	public interface PlayerPresenter {

		public AbstractComponent getView();

		public void play();

		public void stop();

	}

}
