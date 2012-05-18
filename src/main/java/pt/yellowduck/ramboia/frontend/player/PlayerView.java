package pt.yellowduck.ramboia.frontend.player;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
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
	private final Button buttonPrevious = new Button( "Previous" );
	private final Button buttonNext = new Button( "Next" );
	private final Slider slider = new Slider(1, 100);
	private Panel panel = new Panel("RamboIA Music Machine");

	public PlayerView() {
		setupComponents();
		setupListeners();
		setupLayout();
	}

	private void setupComponents() {
		slider.setSizeFull();
		slider.setImmediate( true );
		slider.setMin( 0 );
		slider.setOrientation( Slider.ORIENTATION_HORIZONTAL );
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
		setSizeFull();
		
		setWidth(800, Sizeable.UNITS_PIXELS);
		setHeight(200, Sizeable.UNITS_PIXELS);

		panel.setSizeFull();
		panel.setWidth("300px");
		panel.addComponent( new Label("ARTISTA - MUSICA"));
		panel.addComponent( new Label("Duração 00:00"));
		panel.addComponent(slider);
		addComponent(panel);
		
		HorizontalLayout horizontal = new HorizontalLayout();
		horizontal.setSizeFull();
		horizontal.setSpacing(false);
		horizontal.addComponent(buttonPrevious);
		horizontal.addComponent(buttonStop);
		horizontal.addComponent(buttonPlay);
		horizontal.addComponent(buttonNext);
		horizontal.setComponentAlignment(buttonPrevious, Alignment.TOP_CENTER);
		horizontal.setComponentAlignment(buttonStop, Alignment.TOP_CENTER);
		horizontal.setComponentAlignment(buttonPlay, Alignment.TOP_CENTER);
		horizontal.setComponentAlignment(buttonNext, Alignment.TOP_CENTER);
		
		panel.addComponent(horizontal);
	}

	@Override
	public void setPresenter( PlayerPresenter presenter ) {
		this.presenter = presenter;
	}

	@Override
	public void updateTrackPosition( Song song, long elapsedTime ) {
		slider.setMax( song == null ? 0 : song.getSong().getLength() );
		try {
			slider.setValue( elapsedTime );
		} catch ( Slider.ValueOutOfBoundsException e ) {
			e.printStackTrace();
		}
	}
}
