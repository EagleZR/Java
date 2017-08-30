import eaglezr.support.logs.LoggingTool;

import java.io.File;

public class CS3304_HW_Extractor {

	public static void main( String[] args ) {
		if ( args == null || args[0].equals( "" ) ) {
			System.out.println( "No file path was specified." );
			return;
		}

		LoggingTool.getLogger().setDefault( LoggingTool.generateLogPrinter( "ExtractorConsole" ) );
		DirectoryExtractor extractor = new DirectoryExtractor( new File( args[0] ) );
		extractor.extract();

	}
}
