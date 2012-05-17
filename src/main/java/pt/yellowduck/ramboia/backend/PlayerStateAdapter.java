package pt.yellowduck.ramboia.backend;

import pt.yellowduck.ramboia.backend.PlayerStateListener;
import pt.yellowduck.ramboia.backend.model.Song;

/**
 * User: laught
 * Date: 17-05-2012 Time: 23:18
 */
public class PlayerStateAdapter implements PlayerStateListener {

	@Override
	public void playerPaused() {

	}

	@Override
	public void trackPositionChanged( Song currentSong, long elapsedTime ) {

	}

	@Override
	public void playerStarted( Song currentSong ) {

	}

	@Override
	public void playerStopped() {

	}

	@Override
	public void playerUnpaused() {

	}
}
