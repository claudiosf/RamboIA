package pt.yellowduck.ramboia.frontend.player;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:17
 */
public class PlayerView extends HorizontalLayout implements PlayerInterface {

	private PlayerPresenter presenter = null;

	private final Button buttonPlay = new Button( "Play" );
	
	private final Button buttonStop = new Button( "Stop" );


	public PlayerView() {
		setupListeners();
		setupLayout();
	}

	private void setupListeners() {
		buttonPlay.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick( Button.ClickEvent clickEvent ) {
				if ( presenter != null ) {
					presenter.play();
				}
			}
		});

		buttonStop.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick( Button.ClickEvent clickEvent ) {
				if ( presenter != null ) {
					presenter.stop();
				}
			}
		});
	}

	private void setupLayout() {
		addComponent( buttonPlay );
		addComponent( buttonStop );
	}

	@Override
	public void setPresenter( PlayerPresenter presenter ) {
		this.presenter = presenter;
	}
}
