package pt.yellowduck.ramboia.backend;

import pt.yellowduck.ramboia.backend.model.Song;

/**
 * User: laught
 * Date: 17-05-2012 Time: 23:17
 */
public interface PlayerStateListener {
	
	public void trackPositionChanged( Song currentSong, long elapsedTime );
	
	public void playerStarted( Song currentSong );

	public void playerStopped();

	public void playerPaused();

	public void playerUnpaused();

}
