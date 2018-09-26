package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import string.CString;

/**
* Utility with constants and static methods that are handy in dealing with change categories.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public class DiffsCompMethods {

	/**
	* Constant for <code>&quot;Object Definition Differences Between 12.1.3 and 12.2.6&quot;</code> change category.<br>
	*/
	public static final byte Object_Definition_Differences_Between_12_1_3_and_12_2_6 = 1;
	/**
	* Constant for <code>&quot;Indexed Tables &amp; Columns&quot;</code> change category.<br>
	*/
	public static final byte Indexed_Tables_and_Columns = 2;
	/**
	* Constant for <code>&quot;Indexed Tables &amp; Columns (Note: Even though the index is added in 12.1.3, all underlying tables &amp; columns are present in 12.1.3)&quot;</code> change category.<br>
	*/
	public static final byte Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note = 3;
	/**
	* Constant for <code>&quot;Indexed Tables &amp; Columns (Note: Even though the index is added in 12.1.3, all underlying tables &amp; columns are present in 12.1.3)&quot;</code> change category.<br>
	*/
	public static final byte Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note_2 = 4;
	
	/**
	* The number of vanilla diffs comparison methods.<br>
	*/
	public static final byte NUMBER_OF_VANILLA_DIFFS_COMP_METHODS = 4;
	
	private static int NUMBER_OF_DIFFS_COMP_METHODS = NUMBER_OF_VANILLA_DIFFS_COMP_METHODS;
	private static int MAX_DIFFS_COMP_MTHD_NUM = NUMBER_OF_VANILLA_DIFFS_COMP_METHODS;
	
	
	private static String[] CODES = new String[NUMBER_OF_VANILLA_DIFFS_COMP_METHODS + 3];
	static {
		CODES[0] = String.EMPTY;
		CODES[Object_Definition_Differences_Between_12_1_3_and_12_2_6] = CString.valueOf(new byte[]{'O', 'D', 'D', '1', '3', '2', '6'}, true, true);
		CODES[Indexed_Tables_and_Columns] = CString.valueOf(new byte[]{'I', 'T', 'a', 'C'}, true, true);
		CODES[Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note] = CString.valueOf(new byte[]{'I', 'T', 'a', 'C', 'n'}, true, true);
		CODES[Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note_2] = CString.valueOf(new byte[]{'I', 'T', 'a', 'C', 'n', '2'}, true, true);
	}
	public static final String getShortCode(final int diffsCompMthd) {
		if (diffsCompMthd > NUMBER_OF_VANILLA_DIFFS_COMP_METHODS) {
			if (diffsCompMthd >= CODES.length) {
				if (diffsCompMthd > 0xFFFF) {
					throw new IllegalArgumentException(
					"DiffsCompMethods::getShortCode-1: input argument out of bounds (" + diffsCompMthd + ")"
					);
				}
				int newLen = CODES.length + (CODES.length >>> 1);
				String[] arr = new String[newLen <= diffsCompMthd ? diffsCompMthd + 1 : newLen > 128 ? 128 : newLen];
				System.arraycopy(CODES, 0, arr, 0, CODES.length);
				CODES = arr;
			}
			String code = CODES[diffsCompMthd];
			if (code == null) {
				code = new string.CharBuffer(6).append('D', 'M').append(diffsCompMthd).ToString(true/*createAsciiIfPossible*/);
				CODES[diffsCompMthd] = code;
				NUMBER_OF_DIFFS_COMP_METHODS++;
				if (MAX_DIFFS_COMP_MTHD_NUM < diffsCompMthd) {
					MAX_DIFFS_COMP_MTHD_NUM = diffsCompMthd;
				}
			}
			return code;
		}
		return CODES[diffsCompMthd];
	}


}