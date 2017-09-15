package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.hourlog.TEST_WorkedHours;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.hourlog.TEST_ProjectHourLog;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.TEST_FunctionalRequirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.TEST_NonFunctionalRequirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.TEST_Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.TEST_Team;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith( Suite.class ) @Suite.SuiteClasses( { TEST_FunctionalRequirement.class, TEST_NonFunctionalRequirement.class,
		TEST_WorkedHours.class, TEST_ProjectHourLog.class, TEST_Person.class, TEST_Team.class } ) public class TEST_All {
}
