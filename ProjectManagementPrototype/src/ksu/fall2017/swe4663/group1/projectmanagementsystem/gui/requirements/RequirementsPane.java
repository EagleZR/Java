package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;

public class RequirementsPane extends FramedPane {

	public RequirementsPane( Config config ) {
		super( "Requirements Pane" );
		LoggingTool.print( "Constructing new RequirementsPane." );
	}
}
