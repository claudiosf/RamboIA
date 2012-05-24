package pt.yellowduck.ramboia.backend;

import pt.yellowduck.ramboia.backend.model.Song;

/**
 * User: laught
 * Date: 17-05-2012 Time: 23:17
 */
public interface RamboIAListener {
	
	public void trackPositionChanged( Song currentSong, long elapsedTime );
	
	public void playerStarted( Song currentSong );

	public void playerStopped();

	public void playerPaused();

	public void playerUnpaused();
	
	public void songAdded();
	
	public void songDeleted();
	
	public void songChanged();

	public void playlistChanged();
	
	public void playlistEnded();
	
	public void volumeChanged( int volume );

}
