package pt.yellowduck.ramboia.frontend;

import pt.yellowduck.ramboia.RamboiaApplication;

/**
 * User: laught
 * Date: 24-05-2012 Time: 2:50
 */
public abstract class RamboIAController< V extends RamboIAView > {
	
	protected final V view;

	protected final RamboiaApplication application;

	public RamboIAController( V view, RamboiaApplication application ) {
		this.view = view;
		this.application = application;
		this.view.setPresenter( this );
	}

	public RamboiaApplication getApplication() {
		return application;
	}

	public V getView() {
		return view;
	}
}
