import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DirectoryExtractorTest {
	@org.junit.Test public void nameContains() throws Exception {
		assertTrue( DirectoryExtractor.fileIsExtension( "file.h", ".h" ) );
		assertFalse( DirectoryExtractor.fileIsExtension( "file.hs", ".h" ) );
		assertFalse( DirectoryExtractor.fileIsExtension( "file.html", ".h" ) );
	}

}
