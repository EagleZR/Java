import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DirectoryExtractor {

	File directory;

	public DirectoryExtractor( File directory ) {
		this.directory = directory;
	}

	public void extract() {
		extract( directory );
	}

	public static void extract( File directory ) {
		File newDir = new File( directory.getParent() + "\\Zeagler_" + directory.getName() );

		if ( newDir.mkdirs() ) {
			System.out.println( "A new directory was created at " + newDir.getAbsolutePath() );
		} else {
			System.out.println( "The directory " + newDir.getAbsolutePath() + " already exists." );
		}

		try {
			extract( directory, newDir );

			cleanDirs( newDir );

			try {
				outputMessage( newDir );
			} catch ( IOException e ) {
				System.out.println( "The output message could not be written." );
			}

			System.out.println( "The files were extracted to " + newDir.getAbsolutePath() );
		} catch ( IOException e ) {
			System.out.println( "The files could not be extracted." );
			e.printStackTrace();
		}
	}

	private static void extract( File currDir, File newDir ) throws IOException {
		File[] files = currDir.listFiles();
		for ( File file : files ) {
			if ( !file.isDirectory() ) {
				if ( file.getName().contains( ".cpp" ) || file.getName().contains( ".docx" ) ) {
					Files.copy( file.toPath(), new File( newDir.getAbsolutePath() + "\\" + file.getName() ).toPath(),
							StandardCopyOption.COPY_ATTRIBUTES );
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
			System.out.println( "An empty directory, " + dir.getAbsolutePath() + ", is being deleted." );
			dir.delete();
		}
	}

	private static void outputMessage( File dir ) throws IOException {
		File newFile = new File( dir.getAbsolutePath() + "/readme.txt" );
		PrintStream out = new PrintStream( newFile );
		out.print(
				"This folder was extracted from the VisualStudio solution. All files ending in \".cpp\" or \".docx\" were extracted." );
	}
}