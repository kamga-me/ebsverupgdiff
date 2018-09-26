package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import static string.util.AsciiStringFactoryInternals.astr;

/**
* Utility with constants and static methods that are handy in dealing with change types.<br>
* Please note that the vanilla constants declared here are just the known change types (contextualized instances of change categories) that were there at the start.<br>
* The others, notably those for other versions, are to be created as non vanilla and maintained in the persistent store/database.<br> 
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public class ChangeTypes {
	/**
	* Constant for <code>&quot;Added in 12.2.6&quot;</code> change category.<br>
	*/
	public static final byte Added_in_12_2_6 = 1;
	/**
	* Constant for <code>&quot;Removed in 12.2.6&quot;</code> change category.<br>
	*/
	public static final byte Removed_in_12_2_6 = 2;
	/**
	* Constant for <code>&quot;Attribute Changes between 12.1.3 and 12.2.6&quot;</code> change category.<br>
	*/
	public static final byte Attribute_Changes_between_12_1_3_and_12_2_6 = 3;
	/**
	* Constant for <code>&quot;Objects Added in 12.2.6&quot;</code> change category.<br>
	*/
	public static final byte Objects_Added_in_12_2_6 = 4;
	/**
	* Constant for <code>&quot;Objects Removed in 12.2.6&quot;</code> change category.<br>
	*/
	public static final byte Objects_Removed_in_12_2_6 = 5;
	
	/**
	* The number of vanilla change types.<br>
	*/
	public static final byte NUMBER_OF_VANILLA_CHANGE_TYPES = 5;
	
	private static int NUMBER_OF_CHANGE_TYPES = NUMBER_OF_VANILLA_CHANGE_TYPES;
	
	private static int MAX_CHANGE_TYPE_NUM = NUMBER_OF_VANILLA_CHANGE_TYPES;
	
	
	private static String[] CODES = new String[NUMBER_OF_VANILLA_CHANGE_TYPES + 4];
	static {
		CODES[0] = String.EMPTY;
		CODES[Added_in_12_2_6] = astr(new byte[]{'A', '1', '2', '2', '6'});
		CODES[Removed_in_12_2_6] = astr(new byte[]{'R', '1', '2', '2', '6'});
		CODES[Attribute_Changes_between_12_1_3_and_12_2_6] = astr(new byte[]{'A', 'C', '1', '3', '2', '6'});
		CODES[Objects_Added_in_12_2_6] = astr(new byte[]{'O', 'A', '1', '2', '2', '6'});
		CODES[Objects_Removed_in_12_2_6] = astr(new byte[]{'O', 'R', '1', '2', '2', '6'});
	}
	public static final String getShortCode(final int chgType) {
		if (chgType > NUMBER_OF_VANILLA_CHANGE_TYPES) {
			if (chgType >= CODES.length) {
				if (chgType > 0xFFFF) {
					throw new IllegalArgumentException(
					"ChangeTypes::getShortCode-1: input argument out of bounds (" + chgType + ")"
					);
				}
				int newLen = CODES.length + (CODES.length >>> 1);
				String[] arr = new String[newLen <= chgType ? chgType + 1 : newLen > 0x10000 ? 0x10000 : newLen];
				System.arraycopy(CODES, 0, arr, 0, CODES.length);
				CODES = arr;
			}
			String code = CODES[chgType];
			if (code == null) {
				code = new string.CharBuffer(6).append('C', 'T').append(chgType).ToString(true/*createAsciiIfPossible*/);
				CODES[chgType] = code;
				NUMBER_OF_CHANGE_TYPES++;
				if (MAX_CHANGE_TYPE_NUM < chgType) {
					MAX_CHANGE_TYPE_NUM = chgType;
				}
			}
			return code;
		}
		return CODES[chgType];
	}

}