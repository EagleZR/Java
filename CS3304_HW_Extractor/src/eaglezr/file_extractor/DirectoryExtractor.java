package eaglezr.file_extractor;

import eaglezr.support.logs.LoggingTool;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DirectoryExtractor {

	File directory;

	static String[] fileEndings = new String[] {".cpp", ".h", ".docx", ".doc"};

	public DirectoryExtractor( File directory ) {
		this.directory = directory;
	}

	public static void extract( File directory ) {
		File newDir = new File( directory.getParent() + "\\Zeagler_" + directory.getName() );

		if ( newDir.mkdirs() ) {
			LoggingTool.print( "A new directory was created at \"" + newDir.getAbsolutePath() + "\"." );
		} else {
			LoggingTool.print( "The directory \"" + newDir.getAbsolutePath() + "\" already exists." );
		}

		try {
			extract( directory, newDir );

			cleanDirs( newDir );

			try {
				outputMessage( newDir );
			} catch ( IOException e ) {
				LoggingTool.print( "The output message could not be written." );
			}

			LoggingTool.print( "The files were extracted to \"" + newDir.getAbsolutePath() + "\"." );
		} catch ( IOException e ) {
			LoggingTool.print( "The files could not be extracted." );
			e.printStackTrace();
		}
	}

	private static void extract( File currDir, File newDir ) throws IOException {
		File[] files = currDir.listFiles();
		for ( File file : files ) {
			if ( !file.isDirectory() ) {
				if ( fileIsExtension( file.getName(), fileEndings )) {
					Files.copy( file.toPath(), new File( newDir.getAbsolutePath() + "\\" + file.getName() ).toPath(),
							StandardCopyOption.COPY_ATTRIBUTES );
					LoggingTool.print( "The file \"" + file.getName() + "\" was copied from \"" + file.getParent()
							+ "\" to \"" + newDir.getAbsolutePath() + "\"." );
				}
			} else {
				File newerDir = new File( newDir.getAbsolutePath() + "\\" + file.getName() );
				newDir.mkdir();
				extract( file, newerDir );
			}
		}
	}

	private static void cleanDirs( File dir ) {
		File[] files = dir.listFiles();

		// Navigates to children files/directories
		for ( File file : files ) {
			if ( file.isDirectory() ) {
				cleanDirs( file );
			}
		}

		// If directory is empty, delete this directory
		if ( dir.listFiles().length == 0 ) {
			//			LoggingTool.print( "An empty directory, " + dir.getAbsolutePath() + ", is being deleted." );
			dir.delete();
		}
	}

	private static void outputMessage( File dir ) throws IOException {
		File newFile = new File( dir.getAbsolutePath() + "/readme.txt" );
		PrintStream out = new PrintStream( newFile );
		out.print(
				"This folder was extracted from the VisualStudio solution. All files ending in \".cpp\" or \".docx\" were extracted." );
	}

	public static boolean fileIsExtension( String fileName, String... fileEndings ) {
		for ( String fileEnding : fileEndings ) {
			if ( fileName.contains( fileEnding ) && fileName.indexOf( fileEnding ) + fileEnding.length() == fileName
					.length() ) {
				return true;
			}
		}
		return false;
	}

	public void extract() {
		extract( directory );
	}
}
