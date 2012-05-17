package pt.yellowduck.ramboia.frontend.player;

import com.vaadin.ui.AbstractComponent;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDPlayerException;
import pt.yellowduck.ramboia.RamboiaApplication;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:24
 */
public class PlayerController implements PlayerInterface.PlayerPresenter {

	private final PlayerView playerView = new PlayerView();

	private final RamboiaApplication application;

	public PlayerController( RamboiaApplication application ) {
		this.application = application;
		playerView.setPresenter( this );
	}

	@Override
	public AbstractComponent getView() {
		return playerView;
	}

	@Override
	public void play() {
		if ( application != null ) {
			try {
				application.getServer().play();
			} catch ( MPDConnectionException e ) {
				e.printStackTrace();
			} catch ( MPDPlayerException e ) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stop() {
		if ( application != null ) {
			try {
				application.getServer().stop();
			} catch ( MPDConnectionException e ) {
				e.printStackTrace();
			} catch ( MPDPlayerException e ) {
				e.printStackTrace();
			}
		}
	}

}
