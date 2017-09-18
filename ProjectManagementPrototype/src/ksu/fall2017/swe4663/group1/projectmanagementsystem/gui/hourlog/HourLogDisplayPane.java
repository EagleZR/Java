package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;

public class HourLogDisplayPane extends FramedPane implements ProjectPane {

	private Project project;
	private Config config;

	public HourLogDisplayPane( Project project, Config config ) {
		this.project = project;
		this.config = config;
	}

	@Override public void loadNewProject( Project project ) {
		this.project = project;
	}
}
