package pt.yellowduck.ramboia.frontend.player;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.VerticalLayout;
import java.util.concurrent.TimeUnit;
import pt.yellowduck.ramboia.backend.model.Song;
import pt.yellowduck.ramboia.frontend.Utils;

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
	private final Label labelMusic = new Label( "ARTIST - MUSIC" );
	private final Label labelDuration = new Label( "Duration: 00:00" );
	private Panel panel = new Panel("RamboIA Music Machine");
	private final ProgressIndicator indicator = new ProgressIndicator(new Float(0.0));

	public PlayerView() {
		setupComponents();
		setupListeners();
		setupLayout();
	}

	private void setupComponents() {
		indicator.setPollingInterval( 1000 );
		indicator.setIndeterminate( false );
		indicator.setSizeFull();
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

		buttonNext.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick( Button.ClickEvent event ) {
				if ( presenter != null ) {
					presenter.playNext();
				}
			}
		});

		buttonPrevious.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick( Button.ClickEvent event ) {
				if ( presenter != null ) {
					presenter.playPrevious();
				}
			}
		});
	}

	private void setupLayout() {
		HorizontalLayout horizontal = new HorizontalLayout();
		horizontal.setSpacing(false);
		horizontal.addComponent(buttonPrevious);
		horizontal.addComponent(buttonStop);
		horizontal.addComponent(buttonPlay);
		horizontal.addComponent( buttonNext );
		horizontal.setComponentAlignment( buttonPrevious, Alignment.TOP_CENTER );
		horizontal.setComponentAlignment( buttonStop, Alignment.TOP_CENTER );
		horizontal.setComponentAlignment( buttonPlay, Alignment.TOP_CENTER );
		horizontal.setComponentAlignment( buttonNext, Alignment.TOP_CENTER );

		panel.setWidth( "300px" );
		panel.addComponent( labelMusic );
		panel.addComponent( labelDuration );
		panel.addComponent( indicator );
		panel.addComponent( horizontal );

		this.addComponent( panel );
		this.setComponentAlignment( panel, Alignment.TOP_CENTER );
	}

	@Override
	public void setPresenter( PlayerController presenter ) {
		this.presenter = presenter;
	}

	@Override
	public void updateTrackPosition( Song song, long elapsedTime ) {
		if(song != null) {
			double lenght = (double)song.getSong().getLength();
			indicator.setValue(new Float(elapsedTime / lenght));
			labelMusic.setValue(song.getArtist() + " - " + song.getTitle());
			labelDuration.setValue( Utils.formatTime( song.getSong().getLength(), TimeUnit.SECONDS ) + " / " + Utils.formatTime( elapsedTime, TimeUnit.SECONDS ) );
		}
	}
}
