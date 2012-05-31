package pt.yellowduck.ramboia.frontend.upload;

import java.io.File;
import java.io.OutputStream;

import org.vaadin.easyuploads.MultiFileUpload;

import pt.yellowduck.ramboia.frontend.playlist.PlaylistInterface.PlaylistPresenter;

import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.SucceededEvent;

public class UploadView extends Panel implements UploadInterface {

	private static final long serialVersionUID = 1L;
	private UploadPresenter presenter;
	private MultiFileUpload multiFileUpload;

	public UploadView() {
		setupComponents();
		setupLayout();
	}

	private void setupComponents() {
		setCaption("Get some new tunes");

		multiFileUpload = new MultiFileUpload() {
			@Override
			protected void handleFile(File file, String fileName,
					String mimeType, long length) {
				Boolean success = presenter.uploadFile(file, mimeType);
				String msg = fileName + " ";
				if(success == true){
					msg = msg + "uploaded with success!!";
				}else{
					msg = msg + "did not upload!!";
				}
				presenter.showNotification(msg);
			}
		};
		multiFileUpload.setCaption("MultiFileUpload");
	}

	private void setupLayout() {
		addComponent(multiFileUpload);
		setWidth( "400px" );
	}

	@Override
	public void setPresenter(UploadController presenter) {
		this.presenter = presenter;
	}
}
