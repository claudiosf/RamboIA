package pt.yellowduck.ramboia.frontend.player;

import com.vaadin.ui.AbstractComponent;
import pt.yellowduck.ramboia.backend.model.Song;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:26
 */
public interface PlayerInterface {

	public void setPresenter( PlayerPresenter presenter );

	public void updateTrackPosition( Song song, long elapsedTime );

	public interface PlayerPresenter {

		public AbstractComponent getView();

		public void play();

		public void stop();

	}

}
