package pt.yellowduck.ramboia.frontend.player;

import pt.yellowduck.ramboia.backend.model.Song;
import pt.yellowduck.ramboia.frontend.RamboIAView;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:26
 */
public interface PlayerInterface extends RamboIAView< PlayerController > {

	public void updateTrackPosition( Song song, long elapsedTime );

	public interface PlayerPresenter {

		public void play();

		public void stop();

		public void playNext();

		public void playPrevious();

	}

}
