package zz.ebs.dmcomp;

/**
* Interface with integer constants/flags for the types of data model comparison diffs files.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*
*/
public interface IDMCompDiffsPSVFileTypes {
	
	public static final byte SOURCES_FILE = 1;

	public static final byte REF_DATA_FILE = 2;
	
	public static final byte SUMMARY_FILE = 4;
	
	public static final byte OBJECTS_FILE = 8;
	
	public static final byte OBJECT_CHANGES_FILE = 16;
	
	public static final byte DETAIL_PATHS_FILE = 32;
	
	public static final byte DETAILS_FILE = 64;
	
	public static final byte ERRORS_FILE = (byte)0x80;
	
	
	public static final byte NON_SHARED_DATA_FILES = OBJECTS_FILE | OBJECT_CHANGES_FILE | DETAIL_PATHS_FILE | DETAILS_FILE;
	
	public static final byte NO_FILES_SPECIFIED = 0;

}