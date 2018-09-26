package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import static string.util.AsciiStringFactoryInternals.astr;

/**
* Utility class with constants and static methods that are handy in dealing with data model comparison summary categories.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public final class DMCompSummaryCategoriesUtil {

	private static final String[] CODES = new String[DMCompSummaryCategories.NUMBER_OF_SUMMARY_CATEGORIES + 1];
	static {
		CODES[0] = string.String.EMPTY;
		CODES[DMCompSummaryCategories.Regular_Tables] = astr('R', 'T'); 
		CODES[DMCompSummaryCategories.Partitioned_Tables] = astr('P', 'T'); 
		CODES[DMCompSummaryCategories.Queue_Tables] = astr('Q', 'T'); 
		CODES[DMCompSummaryCategories.Index_Orgnized_Tables] = astr('I', 'O', 'T'); 
		CODES[DMCompSummaryCategories.Global_Temp_Tables] = astr('G', 'T', 'T'); 
		CODES[DMCompSummaryCategories.Indexes] = string.Char.valueOf('I'); 
		CODES[DMCompSummaryCategories.New_Indexes_on_Existing_Tables_$_Columns] = astr('N', 'I', 'o', 'E'); 
		CODES[DMCompSummaryCategories.New_Indexes_on_new_Tables_$_Columns] = astr('N', 'I', 'o', 'N'); 
		CODES[DMCompSummaryCategories.other_Index_Changes] = astr('O', 'I', 'C'); 
		CODES[DMCompSummaryCategories.Views] = string.Char.valueOf('V'); 
		CODES[DMCompSummaryCategories.Sequences] = string.Char.valueOf('S'); 
		CODES[DMCompSummaryCategories.Materialized_Views] = astr('M', 'V'); 
		CODES[DMCompSummaryCategories.Materialized_View_Logs] = astr('M', 'V', 'L'); 
		CODES[DMCompSummaryCategories.Advanced_Queues] = astr('A', 'Q'); 
		CODES[DMCompSummaryCategories.Triggers] = astr('T', 'R'); 
		CODES[DMCompSummaryCategories.Packages] = string.Char.valueOf('P'); 
		CODES[DMCompSummaryCategories.Types] = astr('T', 'Y'); 
		CODES[DMCompSummaryCategories.Security_Object] = astr('S', 'O'); 
		CODES[DMCompSummaryCategories.Synonyms] = astr('S', 'Y'); 
	}
	
	public static final String get_ShortCode(final byte summaryCatNum) {
		return CODES[summaryCatNum];
	}

	/*
	public static final void main(java.lang.String[] args) {
		for (byte i=1;i<=DMCompSummaryCategories.NUMBER_OF_SUMMARY_CATEGORIES;i++)
		{
			System.out.println("UPDATE BI_APPS.WC_FOP_DM_CMP_SUMMARY_CAT\r\nSET SHORT_CODE = '" + CODES[i] + "' WHERE NAME='" + DMCompSummaryCategories.get_CategoryName(i) + "';\r\n");
		}
	}
	*/



}