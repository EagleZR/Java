package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;

public class HourLogDisplayPane extends FramedPane {

	private Project project;

	public HourLogDisplayPane( Project project ) {
		this.project = project;
	}
}
