package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Config {

	public File previousSave;
	public int windowWidth = 500;
	public int windowHeight = 600;

	public Config( File configFile ) throws FileNotFoundException {
		Scanner config = new Scanner( configFile );
		while ( config.hasNextLine() ) {
			parseSettingLine( config.nextLine() );
		}
	}

	/**
	 * Reads settings from an individual line in the config file
	 *
	 * @param settingLine
	 */
	private void parseSettingLine( String settingLine ) {
		if ( settingLine.charAt( 0 ) == '#' ) {
			return;
		} else if ( settingLine.contains( "save_file_location" ) ) {
			previousSave = new File( settingLine.substring( settingLine.indexOf( " " ) ) );
		} else if ( settingLine.contains( "window_width" ) ) {
			windowWidth = Integer.parseInt( settingLine.substring( settingLine.indexOf( " " ) ) );
		} else if ( settingLine.contains( "window_height" ) ) {
			windowHeight = Integer.parseInt( settingLine.substring( settingLine.indexOf( " " ) ) );
		}
	}
}
