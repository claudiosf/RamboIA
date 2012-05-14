package pt.yellowduck.ramboia.frontend;

import com.vaadin.ui.Table;
import pt.yellowduck.ramboia.RamboiaApplication;

public class MusicTable extends Table{
	
	private static final long serialVersionUID = 1L;

	public MusicTable(RamboiaApplication app) {
         setSizeFull();
         setContainerDataSource(app.getTableData());
         setSelectable(true);
         setImmediate(true);
   }
	
}
