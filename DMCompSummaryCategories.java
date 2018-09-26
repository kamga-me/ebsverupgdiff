package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import string.CString;

/**
* Utility class with constants and static methods that are handy in dealing with data model comparison summary categories.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public final class DMCompSummaryCategories {

	/**Constant for <code>Regular_Tables</code> summary category. */public static final byte Regular_Tables = 1; 
	/**Constant for <code>Partitioned_Tables</code> summary category. */public static final byte Partitioned_Tables = 2; 
	/**Constant for <code>Queue_Tables</code> summary category. */public static final byte Queue_Tables = 3; 
	/**Constant for <code>Index_Orgnized_Tables</code> summary category. */public static final byte Index_Orgnized_Tables = 4; 
	/**Constant for <code>Global_Temp_Tables</code> summary category. */public static final byte Global_Temp_Tables = 5; 
	/**Constant for <code>Indexes</code> summary category. */public static final byte Indexes = 6; 
	/**Constant for <code>New_Indexes_on_Existing_Tables_$_Columns</code> summary category. */public static final byte New_Indexes_on_Existing_Tables_$_Columns = 7; 
	/**Constant for <code>New_Indexes_on_new_Tables_$_Columns</code> summary category. */public static final byte New_Indexes_on_new_Tables_$_Columns = 8; 
	/**Constant for <code>other_Index_Changes</code> summary category. */public static final byte other_Index_Changes = 9; 
	/**Constant for <code>Views</code> summary category. */public static final byte Views = 10; 
	/**Constant for <code>Sequences</code> summary category. */public static final byte Sequences = 11; 
	/**Constant for <code>Materialized_Views</code> summary category. */public static final byte Materialized_Views = 12; 
	/**Constant for <code>Materialized_View_Logs</code> summary category. */public static final byte Materialized_View_Logs = 13; 
	/**Constant for <code>Advanced_Queues</code> summary category. */public static final byte Advanced_Queues = 14; 
	/**Constant for <code>Triggers</code> summary category. */public static final byte Triggers = 15; 
	/**Constant for <code>Packages</code> summary category. */public static final byte Packages = 16; 
	/**Constant for <code>Types</code> summary category. */public static final byte Types = 17; 
	/**Constant for <code>Security_Object</code> summary category. */public static final byte Security_Object = 18; 
	/**Constant for <code>Synonyms</code> summary category - not present in the source fiels but added. */public static final byte Synonyms = 19; 
	
	/**
	* The number of summary categories.<br>
	*/
	public static final byte NUMBER_OF_SUMMARY_CATEGORIES = Synonyms;
	
	private static final String[] CATEGORY_NAMES = new String[NUMBER_OF_SUMMARY_CATEGORIES + 1];
	static {
		CATEGORY_NAMES[0] = String.EMPTY;
		CATEGORY_NAMES[Regular_Tables] = CString.valueOf(new byte[]{'R', 'e', 'g', 'u', 'l', 'a', 'r', '_', 'T', 'a', 'b', 'l', 'e', 's'}, true, true); 
		CATEGORY_NAMES[Partitioned_Tables] = CString.valueOf(new byte[]{'P', 'a', 'r', 't', 'i', 't', 'i', 'o', 'n', 'e', 'd', '_', 'T', 'a', 'b', 'l', 'e', 's'}, true, true); 
		CATEGORY_NAMES[Queue_Tables] = CString.valueOf(new byte[]{'Q', 'u', 'e', 'u', 'e', '_', 'T', 'a', 'b', 'l', 'e', 's'}, true, true); 
		CATEGORY_NAMES[Index_Orgnized_Tables] = CString.valueOf(new byte[]{'I', 'n', 'd', 'e', 'x', '_', 'O', 'r', 'g', 'n', 'i', 'z', 'e', 'd', '_', 'T', 'a', 'b', 'l', 'e', 's'}, true, true); 
		CATEGORY_NAMES[Global_Temp_Tables] = CString.valueOf(new byte[]{'G', 'l', 'o', 'b', 'a', 'l', '_', 'T', 'e', 'm', 'p', '_', 'T', 'a', 'b', 'l', 'e', 's'}, true, true); 
		CATEGORY_NAMES[Indexes] = CString.valueOf(new byte[]{'I', 'n', 'd', 'e', 'x', 'e', 's'}, true, true); 
		CATEGORY_NAMES[New_Indexes_on_Existing_Tables_$_Columns] = CString.valueOf(new byte[]{'N', 'e', 'w', '_', 'I', 'n', 'd', 'e', 'x', 'e', 's', '_', 'o', 'n', '_', 'E', 'x', 'i', 's', 't', 'i', 'n', 'g', '_', 'T', 'a', 'b', 'l', 'e', 's', '_', '&', '_', 'C', 'o', 'l', 'u', 'm', 'n', 's'}, true, true); 
		CATEGORY_NAMES[New_Indexes_on_new_Tables_$_Columns] = CString.valueOf(new byte[]{'N', 'e', 'w', '_', 'I', 'n', 'd', 'e', 'x', 'e', 's', '_', 'o', 'n', '_', 'n', 'e', 'w', '_', 'T', 'a', 'b', 'l', 'e', 's', '_', '&', '_', 'C', 'o', 'l', 'u', 'm', 'n', 's'}, true, true); 
		CATEGORY_NAMES[other_Index_Changes] = CString.valueOf(new byte[]{'o', 't', 'h', 'e', 'r', '_', 'I', 'n', 'd', 'e', 'x', '_', 'C', 'h', 'a', 'n', 'g', 'e', 's'}, true, true); 
		CATEGORY_NAMES[Views] = CString.valueOf(new byte[]{'V', 'i', 'e', 'w', 's'}, true, true); 
		CATEGORY_NAMES[Sequences] = CString.valueOf(new byte[]{'S', 'e', 'q', 'u', 'e', 'n', 'c', 'e', 's'}, true, true); 
		CATEGORY_NAMES[Materialized_Views] = CString.valueOf(new byte[]{'M', 'a', 't', 'e', 'r', 'i', 'a', 'l', 'i', 'z', 'e', 'd', '_', 'V', 'i', 'e', 'w', 's'}, true, true); 
		CATEGORY_NAMES[Materialized_View_Logs] = CString.valueOf(new byte[]{'M', 'a', 't', 'e', 'r', 'i', 'a', 'l', 'i', 'z', 'e', 'd', '_', 'V', 'i', 'e', 'w', '_', 'L', 'o', 'g', 's'}, true, true); 
		CATEGORY_NAMES[Advanced_Queues] = CString.valueOf(new byte[]{'A', 'd', 'v', 'a', 'n', 'c', 'e', 'd', '_', 'Q', 'u', 'e', 'u', 'e', 's'}, true, true); 
		CATEGORY_NAMES[Triggers] = CString.valueOf(new byte[]{'T', 'r', 'i', 'g', 'g', 'e', 'r', 's'}, true, true); 
		CATEGORY_NAMES[Packages] = CString.valueOf(new byte[]{'P', 'a', 'c', 'k', 'a', 'g', 'e', 's'}, true, true); 
		CATEGORY_NAMES[Types] = CString.valueOf(new byte[]{'T', 'y', 'p', 'e', 's'}, true, true); 
		CATEGORY_NAMES[Security_Object] = CString.valueOf(new byte[]{'S', 'e', 'c', 'u', 'r', 'i', 't', 'y', '_', 'O', 'b', 'j', 'e', 'c', 't'}, true, true); 
		CATEGORY_NAMES[Synonyms] = CString.valueOf(new byte[]{'S', 'y', 'n', 'o', 'n', 'y', 'm', 's'}, true, true); 
	}
	
	public static final String get_CategoryName(final int summaryCategoryNumber) {
		return CATEGORY_NAMES[summaryCategoryNumber];
	}
	
	/**
	* Gets the number of the summary category whose name is equal to that indicated.<br>
	*/
	public static final byte getCategoryNumber(final ICharSequence name, final int start, final int end) {
		for (byte n=1;n<=NUMBER_OF_SUMMARY_CATEGORIES;n++)
		{
			if (CATEGORY_NAMES[n].equalsSubString(name, start, end)) return n;
		}
		return -1;
	}
	/**
	* Gets the number of the summary category whose name is equal to that indicated.<br>
	*/
	public static final byte getCategoryNumber(final ICharSequence name) {
		for (byte n=1;n<=NUMBER_OF_SUMMARY_CATEGORIES;n++)
		{
			if (CATEGORY_NAMES[n].equals(name)) return n;
		}
		return -1;
	}
	/**
	* Gets the number of the summary category whose name is equal to that indicated.<br>
	*/
	public static final byte getCategoryNumber(final CharSequence name) {
		for (byte n=1;n<=NUMBER_OF_SUMMARY_CATEGORIES;n++)
		{
			if (CATEGORY_NAMES[n].equals(name)) return n;
		}
		return -1;
	}
	
	/**
	* Gets the number of the parent category of the category whose number is supplied.<br>
	* @return <code>0</code> if the specified summary category does not have a parent summary category; the number of the parent category of the category whose number is supplied, otherwise. 
	*/
	public static final byte getParentCategoryNumber(final byte summaryCategoryNumber) {
		switch(summaryCategoryNumber)
		{
		case New_Indexes_on_Existing_Tables_$_Columns:
		case New_Indexes_on_new_Tables_$_Columns:
		case other_Index_Changes: 
			return Indexes;
		}
		return 0;
	}
	
}