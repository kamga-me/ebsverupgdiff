package zz.ebs.dmcomp;

import string.String;
import string.CString;

/**
* Utility class with static methods that are handy in dealing with data model comparison changed types.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public final class DMCompDetailedChgdTypeUtil {


	private static String[] CODES = new String[DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES + 6];
	static {
		CODES[0] = String.EMPTY;
		CODES[DMCompDetailChangedTypes.tab] = new string.Code.Code3((byte)'t', (byte)'a', (byte)'b'); 
		CODES[DMCompDetailChangedTypes.col] = new string.Code.Code3((byte)'c', (byte)'o', (byte)'l'); 
		CODES[DMCompDetailChangedTypes.initial_extent] = new string.Code.Code4((byte)'i', (byte)'n', (byte)'x', (byte)'t'); 
		CODES[DMCompDetailChangedTypes.text] = new string.Code.Code3((byte)'t', (byte)'x', (byte)'t'); 
		CODES[DMCompDetailChangedTypes.max_value] = new string.Code.Code4((byte)'m', (byte)'a', (byte)'x', (byte)'v'); 
		CODES[DMCompDetailChangedTypes.min_value] = new string.Code.Code4((byte)'m', (byte)'i', (byte)'n', (byte)'v'); 
		CODES[DMCompDetailChangedTypes.char_length] = new string.Code.Code4((byte)'c', (byte)'l', (byte)'e', (byte)'n'); 
		CODES[DMCompDetailChangedTypes.data_length] = new string.Code.Code4((byte)'d', (byte)'l', (byte)'e', (byte)'n'); 
		CODES[DMCompDetailChangedTypes.proc] = new string.Code.Code4((byte)'p', (byte)'r', (byte)'o', (byte)'c'); 
		CODES[DMCompDetailChangedTypes.func] = new string.Code.Code4((byte)'f', (byte)'u', (byte)'n', (byte)'c'); 
		CODES[DMCompDetailChangedTypes.owner] = new string.Code.Code4((byte)'o', (byte)'w', (byte)'n', (byte)'r'); 
		CODES[DMCompDetailChangedTypes.arg] = new string.Code.Code3((byte)'a', (byte)'r', (byte)'g'); 
		CODES[DMCompDetailChangedTypes.data_type] = new string.Code.Code4((byte)'d', (byte)'t', (byte)'y', (byte)'p'); 
		CODES[DMCompDetailChangedTypes.base_object_type] = new string.Code.Code3((byte)'b', (byte)'o', (byte)'t'); 
		CODES[DMCompDetailChangedTypes.return_type] = new string.Code.Code4((byte)'r', (byte)'t', (byte)'y', (byte)'p'); 
		CODES[DMCompDetailChangedTypes.in_out] = new string.Code.Code3((byte)'i', (byte)'_', (byte)'o'); 
		CODES[DMCompDetailChangedTypes.data_precision] = new string.Code.Code4((byte)'p', (byte)'r', (byte)'e', (byte)'c'); 
		CODES[DMCompDetailChangedTypes.data_scale] = new string.Code.Code4((byte)'s', (byte)'c', (byte)'a', (byte)'l'); 
		CODES[DMCompDetailChangedTypes.nullable] = new string.Code.Code3((byte)'n', (byte)'u', (byte)'l'); 
		CODES[DMCompDetailChangedTypes.default_value] = new string.Code.Code4((byte)'d', (byte)'e', (byte)'f', (byte)'v'); 
		CODES[DMCompDetailChangedTypes.column_position] = new string.Code.Code4((byte)'c', (byte)'p', (byte)'o', (byte)'s'); 
		CODES[DMCompDetailChangedTypes.attr] = new string.Code.Code3((byte)'a', (byte)'t', (byte)'t'); 
		CODES[DMCompDetailChangedTypes.attr_type] = new string.Code.Code4((byte)'a', (byte)'t', (byte)'y', (byte)'p'); 
		CODES[DMCompDetailChangedTypes.order_flag] = new string.Code.Code4((byte)'o', (byte)'f', (byte)'l', (byte)'g'); 
		CODES[DMCompDetailChangedTypes.ini_trans] = new string.Code.Code4((byte)'i', (byte)'n', (byte)'t', (byte)'r'); 
		CODES[DMCompDetailChangedTypes.href] = new string.Code.Code3((byte)'h', (byte)'r', (byte)'f'); 
		CODES[DMCompDetailChangedTypes.length] = new string.Code.Code3((byte)'l', (byte)'e', (byte)'n'); 
		CODES[DMCompDetailChangedTypes.default_] = new string.Code.Code3((byte)'d', (byte)'e', (byte)'f'); 
		CODES[DMCompDetailChangedTypes.type] = new string.Code.Code3((byte)'t', (byte)'y', (byte)'p'); 
		CODES[DMCompDetailChangedTypes.var] = new string.Code.Code3((byte)'v', (byte)'a', (byte)'r'); 
		CODES[DMCompDetailChangedTypes.except] = new string.Code.Code4((byte)'x', (byte)'c', (byte)'p', (byte)'t'); 
		CODES[DMCompDetailChangedTypes.view] = new string.Code((byte)'v', (byte)'w'); 
		CODES[DMCompDetailChangedTypes.mview] = new string.Code.Code3((byte)'m', (byte)'v', (byte)'w'); 
		CODES[DMCompDetailChangedTypes.column_id] = new string.Code.Code3((byte)'c', (byte)'i', (byte)'d'); 
		
		
		CODES[DMCompDetailChangedTypes.object_type] = new string.Code((byte)'o', (byte)'t'); 
		CODES[DMCompDetailChangedTypes.query] = new string.Code.Code3((byte)'q', (byte)'r', (byte)'y'); 
		CODES[DMCompDetailChangedTypes.cache_size] = new string.Code.Code3((byte)'c', (byte)'s', (byte)'z'); 
		CODES[DMCompDetailChangedTypes.table_owner] = new string.Code.Code3((byte)'t', (byte)'b', (byte)'i'); 
		CODES[DMCompDetailChangedTypes.index_owner] = new string.Code.Code3((byte)'n', (byte)'d', (byte)'o'); 
		CODES[DMCompDetailChangedTypes.obj] = string.Char.valueOf('o'); 
		CODES[DMCompDetailChangedTypes.attr_no] = new string.Code.Code3((byte)'a', (byte)'t', (byte)'n'); 
		CODES[DMCompDetailChangedTypes.PARAMETER_LIST] = new string.Code.Code3((byte)'p', (byte)'r', (byte)'l'); 
		CODES[DMCompDetailChangedTypes.overload_1] = new string.Code.Code3((byte)'o', (byte)'v', (byte)'l'); 
		CODES[DMCompDetailChangedTypes.part] = new string.Code.Code3((byte)'p', (byte)'r', (byte)'t'); 
		CODES[DMCompDetailChangedTypes.overload_2] = new string.Code.Code4((byte)'o', (byte)'v', (byte)'l', (byte)'2'); 
		CODES[DMCompDetailChangedTypes.here] = new string.Code.Code3((byte)'h', (byte)'e', (byte)'r'); 
		
	}
	
	private static byte NUMBER_OF_DTL_CHGD_TYPES = DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES;
	private static short MAX_DTL_CHGD_TYPE_NUM = DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES;
	
	public static final String getShortCode(final short detailedChgTypeNum) {
		if (detailedChgTypeNum > DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES) {
			if (detailedChgTypeNum >= CODES.length) {
				if (detailedChgTypeNum > 255/*127*/) {
					throw new IllegalArgumentException(
					"DMCompDetailedChgdTypeUtil::getShortCode-1: input argument out of bounds (" + detailedChgTypeNum + ")"
					);
				}
				int newLen = CODES.length + (CODES.length >>> 1);
				String[] arr = new String[newLen <= detailedChgTypeNum ? detailedChgTypeNum + 1 : newLen > 256/*128*/ ? 256/*128*/ : newLen];
				System.arraycopy(CODES, 0, arr, 0, CODES.length);
				CODES = arr;
			}
			String code = CODES[detailedChgTypeNum];
			if (code == null) {
				code = new string.CharBuffer(6).append('C').append(detailedChgTypeNum).ToString(true/*createAsciiIfPossible*/);
				CODES[detailedChgTypeNum] = code;
				NUMBER_OF_DTL_CHGD_TYPES++;
				if (MAX_DTL_CHGD_TYPE_NUM < detailedChgTypeNum) {
					MAX_DTL_CHGD_TYPE_NUM = detailedChgTypeNum;
				}
			}
			return code;
		}
		return CODES[detailedChgTypeNum];
	}
/*
	public static final void main(java.lang.String[] args) {
		for (byte i=1;i<=DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES;i++)
		{
			System.out.println("UPDATE BI_APPS.WC_FOP_DM_CMP_DETAIL_TYPE\r\nSET SHORT_CODE = '" + CODES[i] + "' WHERE CODE='" + DMCompDetailChangedType.get_DMCompDetailChangedType(i).code + "';\r\n");
		}
	}
*/
	
}
	