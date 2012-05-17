package pt.yellowduck.ramboia.frontend.player;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;
import pt.yellowduck.ramboia.backend.model.Song;

/**
 * User: laught
 * Date: 17-05-2012 Time: 21:17
 */
public class PlayerView extends VerticalLayout implements PlayerInterface {

	private PlayerPresenter presenter = null;

	private final Button buttonPlay = new Button( "Play" );
	
	private final Button buttonStop = new Button( "Stop" );

	private final Slider sliderTime = new Slider( "Time Elapsed" );

	public PlayerView() {
		setupComponents();
		setupListeners();
		setupLayout();
	}

	private void setupComponents() {
		sliderTime.setSizeFull();
		sliderTime.setImmediate( true );
		sliderTime.setMin( 0 );
		sliderTime.setOrientation( Slider.ORIENTATION_HORIZONTAL );
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
		this.setSizeFull();

		HorizontalLayout layoutButtons = new HorizontalLayout();
		layoutButtons.addComponent( buttonPlay );
		layoutButtons.addComponent( buttonStop );
		
		addComponent( layoutButtons );
		addComponent( sliderTime );
	}

	@Override
	public void setPresenter( PlayerPresenter presenter ) {
		this.presenter = presenter;
	}

	@Override
	public void updateTrackPosition( Song song, long elapsedTime ) {
		sliderTime.setMax( song == null ? 0 : song.getSong().getLength() );
		try {
			sliderTime.setValue( elapsedTime );
		} catch ( Slider.ValueOutOfBoundsException e ) {
			e.printStackTrace();
		}
	}
}
