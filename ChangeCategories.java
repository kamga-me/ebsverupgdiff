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
public class ChangeCategories {

	/**
	* Constant for <code>Added</code> change category.<br>
	*/
	public static final byte Added = 1;
	/**
	* Constant for <code>Removed</code> change category.<br>
	*/
	public static final byte Removed = 2;
	/**
	* Constant for <code>Attribute_Changes</code> change category.<br>
	*/
	public static final byte Attribute_Changes = 3;
	/**
	* Constant for <code>Objects_Added</code> change category.<br>
	*/
	public static final byte Objects_Added = 4;
	/**
	* Constant for <code>Objects_Removed</code> change category.<br>
	*/
	public static final byte Objects_Removed = 5;
	
	
	public static final byte NUMBER_OF_VANILLA_CHG_CATEGORIES = 5;
	
	private static byte NUMBER_OF_CHG_CATEGORIES = NUMBER_OF_VANILLA_CHG_CATEGORIES;
	
	private static String[] CODES = new String[NUMBER_OF_VANILLA_CHG_CATEGORIES + 4];
	static {
		CODES[0] = String.EMPTY;
		CODES[Added] = astr('A');
		CODES[Removed] = astr('R');
		CODES[Attribute_Changes] = astr('A', 'C');
		CODES[Objects_Added] = astr('O', 'A');
		CODES[Objects_Removed] = astr('O', 'R');
	}
	public static final String getShortCode(final byte chgCat) {
		if (chgCat > NUMBER_OF_VANILLA_CHG_CATEGORIES) {
			if (chgCat >= CODES.length) {
				if (chgCat > 127) {
					throw new IllegalArgumentException(
					"ChangeCategories::getShortCode-1: input argument out of bounds (" + chgCat + ")"
					);
				}
				int newLen = CODES.length + (CODES.length >>> 1);
				String[] arr = new String[newLen <= chgCat ? chgCat + 1 : newLen > 128 ? 128 : newLen];
				System.arraycopy(CODES, 0, arr, 0, CODES.length);
				CODES = arr;
			}
			String code = CODES[chgCat];
			if (code == null) {
				code = new string.CharBuffer(6).append('C', 'C').append(chgCat).ToString(true/*createAsciiIfPossible*/);
				CODES[chgCat] = code;
				NUMBER_OF_CHG_CATEGORIES++;
			}
			return code;
		}
		return CODES[chgCat];
	}

}