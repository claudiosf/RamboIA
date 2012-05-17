package pt.yellowduck.ramboia.frontend.player;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
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
	
	private final Slider slider = new Slider(1, 100);
	
	private Panel panel = new Panel("RamboIA Music Machine");

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
		setSizeFull();
		
		setWidth(600, Sizeable.UNITS_PIXELS);
		setHeight(300, Sizeable.UNITS_PIXELS);
		
		panel.setWidth("300px");

		panel.addComponent(
		    new Label("ARTISTA - MUSICA"));
		panel.setSizeFull();
		addComponent(panel);
		
		slider.setOrientation(Slider.ORIENTATION_HORIZONTAL);
		slider.setSizeFull();
		addComponent(slider);
		
		GridLayout grid = new GridLayout(4, 1);
		grid.setSizeFull();
		grid.addComponent(buttonPlay, 2, 0);
		grid.addComponent(buttonStop, 1, 0);
		grid.setComponentAlignment(buttonPlay, Alignment.TOP_CENTER);
		grid.setComponentAlignment(buttonStop, Alignment.TOP_CENTER);
		
		addComponent(grid);
		
		setComponentAlignment(slider, Alignment.BOTTOM_CENTER);
		setComponentAlignment(grid, Alignment.TOP_CENTER);
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
