package com.test.ramboia;

import com.vaadin.ui.Table;

public class MusicTable extends Table{
	
	private static final long serialVersionUID = 1L;

	public MusicTable(RamboiaApplication app) {
         setSizeFull();
         setContainerDataSource(app.getTableData());
         setSelectable(true);
         setImmediate(true);
   }
	
}
