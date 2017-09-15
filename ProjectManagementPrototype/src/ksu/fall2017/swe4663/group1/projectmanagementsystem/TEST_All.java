package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.effort.TEST_Effort;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.effort.TEST_ProjectEffort;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.TEST_FunctionalRequirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.TEST_NonFunctionalRequirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.TEST_Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.TEST_Team;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith( Suite.class ) @Suite.SuiteClasses( { TEST_FunctionalRequirement.class, TEST_NonFunctionalRequirement.class,
		TEST_Effort.class, TEST_ProjectEffort.class, TEST_Person.class, TEST_Team.class } ) public class TEST_All {
}
