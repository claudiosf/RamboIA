package pt.yellowduck.ramboia.frontend.player;

import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDPlayerException;
import pt.yellowduck.ramboia.RamboiaApplication;
import pt.yellowduck.ramboia.backend.RamboIAAdapter;
import pt.yellowduck.ramboia.backend.RamboIALogger;
import pt.yellowduck.ramboia.backend.model.Song;
import pt.yellowduck.ramboia.frontend.RamboIAController;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:24
 */
public class PlayerController extends RamboIAController< PlayerInterface > implements PlayerInterface.PlayerPresenter {

	public PlayerController( PlayerInterface otherView, RamboiaApplication mainApplication ) {
		super( otherView, mainApplication );

		application.getServer().addStateListener( new RamboIAAdapter() {
			@Override
			public void playerStarted( Song currentSong ) {
				view.updateTrackPosition( currentSong, 0 );
			}

			@Override
			public void playerStopped() {
				view.updateTrackPosition( null, 0 );
			}

			@Override
			public void trackPositionChanged( Song currentSong, long elapsedTime ) {
				view.updateTrackPosition( currentSong, elapsedTime );
			}

			@Override
			public void volumeChanged( int volume ) {
				RamboIALogger.notify( application.getMainWindow(), "Info :: volumeChanged", "Volume: " + volume );
			}
		} );
	}

	@Override
	public void play() {
		if ( application != null ) {
			try {
				application.getServer().play();
			} catch ( MPDConnectionException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			} catch ( MPDPlayerException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void stop() {
		if ( application != null ) {
			try {
				application.getServer().stop();
			} catch ( MPDConnectionException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage() );
			} catch ( MPDPlayerException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void playNext() {
		if ( application != null ) {
			try {
				application.getServer().next();
			} catch ( MPDConnectionException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			} catch ( MPDPlayerException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void playPrevious() {
		if ( application != null ) {
			try {
				application.getServer().previous();
			} catch ( MPDConnectionException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			} catch ( MPDPlayerException e ) {
				RamboIALogger.notify( application.getMainWindow(), "Error", e.getLocalizedMessage());
			}
		}
	}
}
