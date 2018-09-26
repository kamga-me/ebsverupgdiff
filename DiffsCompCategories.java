package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import static string.util.AsciiStringFactoryInternals.astr;

/**
* Utility with constants and static methods that are handy in dealing with change categories.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public class DiffsCompCategories {

	/**
	* Constant for <code>&quot;Object Definition Differences&quot;</code> change category.<br>
	*/
	public static final byte Object_Definition_Differences = 1;
	/**
	* Constant for <code>&quot;Indexed Tables &amp; Columns&quot;</code> change category.<br>
	*/
	public static final byte Indexed_Tables_and_Columns = 2;
	
	
	public static final byte NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES = 2;
	private static byte NUMBER_OF_DIFFS_COMP_CATEGORIES = NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES;
	private static byte MAX_DIFFS_COMP_CATEGORY_NUM = NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES;
	
	private static String[] CODES = new String[NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES + 3];
	static {
		CODES[Object_Definition_Differences] = astr('O', 'D', 'D');
		CODES[Indexed_Tables_and_Columns] = astr('I', 'T', 'a', 'C');
	}
	
	
	public static final String getShortCode(final byte diffsCompCat) {
		if (diffsCompCat > NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES) {
			if (diffsCompCat >= CODES.length) {
				if (diffsCompCat > 127) {
					throw new IllegalArgumentException(
					"DiffsCompCategories::getShortCode-1: input argument out of bounds (" + diffsCompCat + ")"
					);
				}
				int newLen = CODES.length + (CODES.length >>> 1);
				String[] arr = new String[newLen <= diffsCompCat ? diffsCompCat + 1 : newLen > 128 ? 128 : newLen];
				System.arraycopy(CODES, 0, arr, 0, CODES.length);
				CODES = arr;
			}
			String code = CODES[diffsCompCat];
			if (code == null) {
				code = new string.CharBuffer(6).append('D', 'C', 'C').append(diffsCompCat).ToString(true/*createAsciiIfPossible*/);
				CODES[diffsCompCat] = code;
				NUMBER_OF_DIFFS_COMP_CATEGORIES++;
				if (MAX_DIFFS_COMP_CATEGORY_NUM < diffsCompCat) {
					MAX_DIFFS_COMP_CATEGORY_NUM = diffsCompCat;
				}
			}
			return code;
		}
		return CODES[diffsCompCat];
	}

}