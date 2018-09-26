package zz.ebs.dmcomp;

import java.io.OutputStream;
import java.io.FileOutputStream;

import core.io.IOException;
import core.io.encoding.TextOutputStreamBuffer;
import core.io.encoding.UTF8TextOutputStreamBuffer;
import core.io.BufferedTextOutputStream;
import core.io.WriteToStreamException;

import string.ICharSequence;
import string.String;
import string.CString;

import zz.ebs.eBSProductSet;
import zz.ebs.eBSProduct;

import zz.ebs.dmcomp.DataModelComparisonReport.DiffsSummary;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsDetailedChange;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsTableRow;
import zz.ebs.dmcomp.DataModelComparisonReport.SingleChgDiffsTableRow;
import zz.ebs.dmcomp.DataModelComparisonReport.SingleChgDiffsTableRowAdvcd;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsNewIndex;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsTableRowChange;
import zz.ebs.dmcomp.DataModelComparisonReport.MultiChgDiffsTableRow;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsTable;

import static zz.ebs.dmcomp.DMCompSummaryCategories.NUMBER_OF_SUMMARY_CATEGORIES;

import static core.IOperatingSystemConstants.LINE_TERMINATOR;

import static zz.ebs.dmcomp.ChangeType.Attribute_Changes__between_12_1_3_and_12_2_6;

import static zz.ebs.dmcomp.DMCompDetailChangedTypes.NOT_A_FROM_TO_CHANGE_FROM_VAL;
import static zz.ebs.dmcomp.DMCompDetailChangedTypes.WEAK_FROM_TO_CHANGE_FROM_VAL;
import static zz.ebs.dmcomp.DMCompDetailChangedTypes.NO_SPEC_ITEM_FOR_DTL_OR_NO_CHG_TYPES_TO_MATCH;
import static zz.ebs.dmcomp.DMCompDetailChangedTypes.NO_DTL_TYPE_MATCH_FOR_FROM_TO_CHANGE;

import static zz.ebs.dmcomp.ChangeType.__Click_here_for_complete_changes__;
import static zz.ebs.dmcomp.ChangeType.__Click_here_for_complete_changes_SHORTHAND;
import static zz.ebs.dmcomp.DMSource.fromSourceUrl;

import core.IValueTypes;
import core.data.type.IValueTypesExt;

/**
* Utility class with static methods that are handy in performing with data model comparison output tasks.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
class DMCompOutputUtil implements core.ITrinaryValues {

	public static final byte PRODUCT = 1;
	
	public static final byte DIFFS_COMP_METHOD = 2;
	
	public static final byte DIFFS_CHANGE_TYPE = 3;
	
	public static final byte DIFFS_DETAIL_CHANGED_TYPE = 4;
	
	static final string.String __N_A__ = DMCompDetailChangedType.N_A.code; //new string.Code.Code3((byte)'N', (byte)'/', (byte)'A');
	
	
	public static final String PRODUCT_REF_DATA_TYPE = CString.valueOf(new byte[]{'P', 'R', 'O', 'D', 'U', 'C', 'T'}, true, true);
	
	public static final String DIFFS_COMP_METHOD_REF_DATA_TYPE = CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'C', 'O', 'M', 'P', '_', 'M', 'E', 'T', 'H', 'O', 'D'}, true, true);
	
	public static final String DIFFS_CHANGE_TYPE_REF_DATA_TYPE = CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'C', 'H', 'A', 'N', 'G', 'E', '_', 'T', 'Y', 'P', 'E'}, true, true);
	
	public static final String DIFFS_DETAIL_CHANGED_TYPE_REF_DATA_TYPE = CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'D', 'E', 'T', 'A', 'I', 'L', '_', 'C', 'H', 'A', 'N', 'G', 'E', 'D', '_', 'T', 'Y', 'P', 'E'}, true, true);
	
	
	static final String __FROM_TO_OP_SYMBOL__ = new string.Code.Code4((byte)' ', (byte)'=', (byte)'>', (byte)' ');
	
	static final string.Char __Y__ = string.Char.valueOf('Y');
	static final string.Char __N__ = string.Char.valueOf('N');
	static final string.Char __U__ = string.Char.valueOf('U');
	static final string.Char __I__ = string.Char.valueOf('I');
	static final string.Char __D__ = string.Char.valueOf('D');
	static final string.Char __T__ = string.Char.valueOf('T');
	static final string.Char __B__ = string.Char.valueOf('B');
	static final string.Char __O__ = string.Char.valueOf('O');
	static final string.Char __Z__ = string.Char.valueOf('Z');
	
	static final String workoutValueTypeFlag(final byte valueType) {
		switch(valueType)
		{
		case IValueTypes.INTEGER:
		case IValueTypes.INT:
		case IValueTypes.SHORTINT:
		case IValueTypes.LONGINT:
		case IValueTypes.TINYINT:
			return __I__;
		case IValueTypes.DECIMAL:
		case IValueTypes.DFLOAT:
		case IValueTypes.SFLOAT:	
		case IValueTypes.LONGDOUBLE:	
			return __D__;
		case IValueTypes.STRING:
			return __T__;
		case IValueTypesExt.URI:
			return __U__;
		case IValueTypes.BOOLEAN:
			return __B__;
		case IValueTypes.ANY_VALUE:
		case IValueTypes.TRINARY:
		case IValueTypesExt.QNAME:
		case IValueTypesExt.NOTATION:
			return __T__;
		}
		return __O__;
	}
	
	static final String TYPE_FIELD = CString.valueOf(new byte[]{'T', 'Y', 'P', 'E'}, true, true);
	static final String CODE_FIELD = CString.valueOf(new byte[]{'C', 'O', 'D', 'E'}, true, true);
	static final String NAME_FIELD = CString.valueOf(new byte[]{'N', 'A', 'M', 'E'}, true, true);
	static final String NUMBER_FIELD = CString.valueOf(new byte[]{'N', 'U', 'M', 'B', 'E', 'R'}, true, true);
	static final String NUM_FIELD = NUMBER_FIELD.left(3);
	static final String GROUP_NUM_FIELD = CString.valueOf(new byte[]{'G', 'R', 'O', 'U', 'P', '_', 'N', 'U', 'M'}, true, true);
	static final String DESCRIPTION_FIELD = CString.valueOf(new byte[]{'D', 'E', 'S', 'C', 'R', 'I', 'P', 'T', 'I', 'O', 'N'}, true, true);
	static final String COMMENT_FIELD = CString.valueOf(new byte[]{'C', 'O', 'M', 'M', 'E', 'N', 'T'}, true, true);
	static final String CREATED_DT_FIELD = CString.valueOf(new byte[]{'C', 'R', 'E', 'A', 'T', 'E', 'D', '_', 'D', 'T'}, true, true);
	static final String CREATION_TAG_FIELD = CString.valueOf(new byte[]{'C', 'R', 'E', 'A', 'T', 'I', 'O', 'N', '_', 'T', 'A', 'G'}, true, true);
	static final String FROM_SOURCE_FILE_FIELD = CString.valueOf(new byte[]{'F', 'R', 'O', 'M', '_', 'S', 'O', 'U', 'R', 'C', 'E', '_', 'F', 'I', 'L', 'E'}, true, true);
	static final String FROM_SOURCE_FILE_NUM_FIELD = CString.valueOf(new byte[]{'F', 'R', 'O', 'M', '_', 'S', 'O', 'U', 'R', 'C', 'E', '_', 'F', 'I', 'L', 'E', '_', 'N', 'U', 'M'}, true, true);
	static final String REMOVED_IN_VERSION_FIELD = CString.valueOf(new byte[]{'R', 'E', 'M', 'O', 'V', 'E', 'D', '_', 'I', 'N', '_', 'V', 'E', 'R', 'S', 'I', 'O', 'N'}, true, true);
	
	static final String DM_CHG_COMPARISON_FIELD = CString.valueOf(new byte[]{'D', 'M', '_', 'C', 'H', 'G', '_', 'C', 'O', 'M', 'P', 'A', 'R', 'I', 'S', 'O', 'N'}, true, true);
	
	/**
	* constant for <code>&quot;is removed in &quot;</code>
	*/
	static final String is_removed_in___ = CString.valueOf(new byte[]{'i', 's', ' ', 'r', 'e', 'm', 'o', 'v', 'e', 'd', ' ', 'i', 'n', ' '}, true, true);

	static final void writeRefDataHeader(final BufferedTextOutputStream utf8BufferedOS) {
		//TYPE|CODE|NAME|DESCRIPTION|NUM|GROUP_NUM|REMOVED_IN_VERSION|COMMENT|FROM_SOURCE_FILE|FROM_SOURCE_FILE_NUM|DM_CHG_COMPARISON|CREATION_TAG|CREATED_DT
		utf8BufferedOS.Write(TYPE_FIELD).Write('|').Write(CODE_FIELD).Write('|').Write(NAME_FIELD).Write('|')
		.Write(DESCRIPTION_FIELD).Write('|').Write(NUM_FIELD).Write('|').Write(GROUP_NUM_FIELD).Write('|')
		.Write(REMOVED_IN_VERSION_FIELD).Write('|').Write(COMMENT_FIELD).Write('|').Write(FROM_SOURCE_FILE_FIELD).Write('|')
		.Write(FROM_SOURCE_FILE_NUM_FIELD).Write('|').Write(DM_CHG_COMPARISON_FIELD).Write('|').Write(CREATION_TAG_FIELD).Write('|').Write(CREATED_DT_FIELD).write('\n');
	}
	
	/**
	* @return <code>null</code> if failed to get from persistent store.<br>
	* @param nameEnd the index of the right parenthesis right after the name of the product - if nameEnd + 1 < product.length(), there is a comment that can be removed-in-version info
	*/
	static final eBSProduct getProductFromPersistentStore(String product, int codeEnd, int nameStart, int nameEnd, String fromSourceFile, int fromSourceFileNum, String tag) {
		//must persist of the product if it does not already exist in the store and returns it...
		return null;
	}
	
	/**
	* Writes the specified product to the output .psv file if the product does not already exists, neither as a vanilla product nor in the persistent store
	* @return the negated value of the number of the product when the product already exists; the number allocated to the product, otherwise.
	*/
	static final short writeProduct(String product, final DMCompEnumSets enums, String fromSourceFile, int fromSourceFileNum, String tag, final String dmDiffsComparison, final BufferedTextOutputStream utf8BufferedOS) {
		int lastIndex = product.length() - 1;
		__check_loop:
		do 
		{
			if (lastIndex < 0) {
				throw new WriteToStreamException(
				"writeRefDataHeader::writeRefDataHeader-1: product designation of invalid format"
				);
			}
			if (product.getChar(lastIndex) != ')') {
				lastIndex--;
				continue __check_loop;
			}
			break __check_loop;
		} while (true); //end __check_loop
		int nameStart =  0;
		int codeEnd = 0;
		__check_name_start_loop:
		do 
		{
			if (nameStart < lastIndex) {
				if (product.getChar(nameStart) != ' ') {
					nameStart++;
					continue __check_name_start_loop;
				}
				else if (nameStart == 0) {
					throw new WriteToStreamException(
					"writeRefDataHeader::writeRefDataHeader-2: product designation of invalid format"
					);
				}
				codeEnd = nameStart;
				__skip_ws:
				do 
				{
					nameStart++;
					if (nameStart < lastIndex) {
						switch(product.getChar(nameStart))
						{
						case ' ':
							continue __skip_ws;
						case '(':
							nameStart++;
							if (nameStart < lastIndex) { //case the name is not equal to empty string
								break __check_name_start_loop;
							}
							break ;
						}
					}
					throw new WriteToStreamException(
					"writeRefDataHeader::writeRefDataHeader-3: product designation of invalid format"
					);
				} while (true); //end __skip_ws
				//break __check_name_start_loop;
			}
			throw new WriteToStreamException(
			"writeRefDataHeader::writeRefDataHeader-4: product designation of invalid format"
			);
		} while (true); //end __check_name_start_loop
		eBSProduct prod = enums.ebsProducts.getByCode(product, 0, codeEnd);
		if (prod != null) { //case the product already exists
			return (short)-prod.number;
		}
		prod = getProductFromPersistentStore(product, codeEnd, nameStart, lastIndex/*nameEnd*/, fromSourceFile, fromSourceFileNum, tag);
		if (prod != null) { //case the product already exists in the persistent store
			return (short)-prod.number;
		}
		prod = eBSProduct.new_NonVanillaProduct((short)(enums.ebsProducts.size() + 1)/*number*/, product.left(codeEnd)/*code*/, product.substring(nameStart, lastIndex/*nameEnd*/)/*name*/);
		enums.ebsProducts.add(prod);
		int commentStart = lastIndex + 1;
		int commentEnd = product.length();
		__check_comment_start_loop:
		do 
		{
			if (commentStart < commentEnd) {
				if (product.getChar(commentStart) == ' ') {
					commentStart++;
					continue __check_comment_start_loop;
				}
			}
			break __check_comment_start_loop;
		} while (true); //end __check_comment_start_loop
		
		int removedInVerStart = 0;
		int removedInVerEnd = 0;
		if (commentEnd - commentStart > is_removed_in___.length() && is_removed_in___.equalsSubString(product, commentStart, commentStart + is_removed_in___.length())) {
			removedInVerStart = commentStart + is_removed_in___.length();
			removedInVerEnd = commentEnd;
			__skip_leading_space_from_ver_loop:
			do 
			{
				if (removedInVerStart < removedInVerEnd) {
					if (product.getChar(removedInVerStart) ==  ' ') {
						removedInVerStart++;
						continue __skip_leading_space_from_ver_loop;
					}
				}
				else {
					removedInVerStart = lastIndex;
					removedInVerEnd = lastIndex;
				}
				break __skip_leading_space_from_ver_loop;
			} while (true); 
			commentStart = 0;
			commentEnd = 0;
		}
		//TYPE|CODE|NAME|DESCRIPTION|NUM|GROUP_NUM|REMOVED_IN_VERSION|COMMENT|FROM_SOURCE_FILE|FROM_SOURCE_FILE_NUM|DM_CHG_COMPARISON|CREATION_TAG|CREATED_DT
		utf8BufferedOS.Write(PRODUCT_REF_DATA_TYPE).Write('|').Write(prod.code).Write('|').Write(prod.name).Write('|')
		.Write(String.EMPTY/*DESCRIPTION*/).Write('|').Write(prod.number).Write('|').Write(String.EMPTY/*GROUP_NUM*/).Write('|')
		.Write(product, removedInVerStart, removedInVerEnd).Write('|').Write(product, commentStart, commentEnd).Write('|').Write(fromSourceFile).Write('|')
		.Write(fromSourceFileNum).Write('|').Write(dmDiffsComparison).Write('|').Write(tag).Write('|').Write(get_nowDtm()/*CREATED_DT*/).write('\n');
		
		return prod.number;
	}
	
	//TODO - URGENT: DROP THIS WHEN MY STUFF IS READY FOr USE
	static final java.text.SimpleDateFormat TIMESTAMP_FMT = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
	/**
	* TODO - VERY URGENT: change the return type to string.String and the code of the method to use my on datatime related stiff when the latter is available for use.
	*/
	/*private */static final java.lang.String get_nowDtm() {
		return TIMESTAMP_FMT.format(new java.util.Date());
	}
	
	private static final byte[] __toCodeData(String code) {
		if (code.isLatin() == yes) {
			byte[] data = new byte[code.length()];
			code.getChars8(data, 0);
			return data;
		}
		else if (code.isLatin() == no) {
			throw new WriteToStreamException(
			"DMCompOutputUtil::__toCodeData-1: invalid code data - latin string data expected"
			);
		}
		byte[] data = new byte[code.length()];
		for (int i=0;i<data.length;i++)
		{
			char ch = code.getChar(i);
			if (ch > '\u00FF') {
				throw new WriteToStreamException(
				"DMCompOutputUtil::__toCodeData-2: invalid code data - latin string data expected (" + i + ")"
				);
			}
			data[i] = (byte)ch;
		}
		return data;
	}
	
	/**
	* @return <code>null</code> if failed to get from persistent store.<br>
	*/
	static final DiffsCompMethod getDiffsCompMthdFromPersistentStore(String diffsCompMthd, String fromSourceFile, int fromSourceFileNum, String tag) {
		//must persist of the diffs-comp method if it does not already exist in the store and returns it...
		return null;
	}
	
	static final int writeDiffsCompMethod(String diffsCompMthd, final DMCompEnumSets enums, String fromSourceFile, int fromSourceFileNum, String tag, final String dmDiffsComparison, final BufferedTextOutputStream utf8BufferedOS) {
		DiffsCompMethod existing = enums.diffsCompMethodSet.getByCode(diffsCompMthd);
		if (existing != null) { //case the product already exists
			return -existing.code.number;
		}
		existing = getDiffsCompMthdFromPersistentStore(diffsCompMthd, fromSourceFile, fromSourceFileNum, tag);
		if (existing != null) { //case the product already exists in the persistent store
			return -existing.code.number;
		}
		byte diffsCompCategory = enums.diffsCompCategories.checkCategoryOf(diffsCompMthd);
		DiffsCompMethodID code = new DiffsCompMethodID((short)(enums.diffsCompMethodSet.size() + 1)/* number*/, diffsCompCategory, __toCodeData(diffsCompMthd)/*data*/, false/*isName*/);
		existing = new DiffsCompMethod(code, new DiffsCompMethodID(code, true/*altIDType*/));
		enums.diffsCompMethodSet.add(existing);
		
		//TYPE|CODE|NAME|DESCRIPTION|NUM|GROUP_NUM|REMOVED_IN_VERSION|COMMENT|FROM_SOURCE_FILE|FROM_SOURCE_FILE_NUM|DM_CHG_COMPARISON|CREATION_TAG|CREATED_DT
		utf8BufferedOS.Write(DIFFS_COMP_METHOD_REF_DATA_TYPE).Write('|').Write(existing.code).Write('|').Write(existing.name).Write('|')
		.Write(String.EMPTY/*DESCRIPTION*/).Write('|').Write(existing.code.number).Write('|').Write((diffsCompCategory < 0 ? 0 : diffsCompCategory)/*GROUP_NUM*/).Write('|')
		.Write(String.EMPTY/*removedInVersion*/).Write('|').Write(String.EMPTY/*comment*/).Write('|').Write(fromSourceFile).Write('|')
		.Write(fromSourceFileNum).Write('|').Write(dmDiffsComparison).Write('|').Write(tag).Write('|').Write(get_nowDtm()/*CREATED_DT*/).write('\n');
		
		return existing.code.number;
	}
	
	static final void writeDiffsCompMethod(final DiffsCompMethod diffsCompMthd, final String fromSourceFile, final int fromSourceFileNum, String tag, final String dmDiffsComparison, final BufferedTextOutputStream utf8BufferedOS) {
		utf8BufferedOS.Write(DIFFS_COMP_METHOD_REF_DATA_TYPE).Write('|').Write(diffsCompMthd.code).Write('|').Write(diffsCompMthd.name).Write('|')
		.Write(String.EMPTY/*DESCRIPTION*/).Write('|').Write(diffsCompMthd.code.number).Write('|').Write(diffsCompMthd.code.diffsCompCategory/*GROUP_NUM*/).Write('|')
		.Write(String.EMPTY/*removedInVersion*/).Write('|').Write(String.EMPTY/*comment*/).Write('|').Write(fromSourceFile).Write('|')
		.Write(fromSourceFileNum).Write('|').Write(dmDiffsComparison).Write('|').Write(tag).Write('|').Write(get_nowDtm()/*CREATED_DT*/).write('\n');
	}
	
	
	/**
	* @return <code>null</code> if failed to get from persistent store.<br>
	*/
	static final ChangeType getDiffsChangeTypeFromPersistentStore(String changeType, String fromSourceFile, int fromSourceFileNum, String tag) {
		//must persist of the diffs-comp method if it does not already exist in the store and returns it...
		return null;
	}
	
	static final int writeChangeType(String changeType, final DMCompEnumSets enums, String fromSourceFile, int fromSourceFileNum, String tag, final String dmDiffsComparison, final BufferedTextOutputStream utf8BufferedOS) {
		ChangeType existing = enums.changeTypes.getByCode(changeType);
		if (existing != null) { //case the product already exists
			return -existing.code.number;
		}
		existing = getDiffsChangeTypeFromPersistentStore(changeType, fromSourceFile, fromSourceFileNum, tag);
		if (existing != null) { //case the product already exists in the persistent store
			return -existing.code.number;
		}
		byte chgCategory = enums.changeCategories.checkCategoryOf(changeType);
		ChangeTypeID code = new ChangeTypeID((short)(enums.changeTypes.size() + 1)/* number*/, chgCategory, __toCodeData(changeType)/*data*/, false/*isName*/);
		existing = new ChangeType(code, new ChangeTypeID(code, true/*altIDType*/));
		enums.changeTypes.add(existing);
		
		//TYPE|CODE|NAME|DESCRIPTION|NUM|GROUP_NUM|REMOVED_IN_VERSION|COMMENT|FROM_SOURCE_FILE|FROM_SOURCE_FILE_NUM|DM_CHG_COMPARISON|CREATION_TAG|CREATED_DT
		utf8BufferedOS.Write(DIFFS_CHANGE_TYPE_REF_DATA_TYPE).Write('|').Write(existing.code).Write('|').Write(existing.name).Write('|')
		.Write(String.EMPTY/*DESCRIPTION*/).Write('|').Write(existing.code.number).Write('|').Write((chgCategory < 0 ? 0 : chgCategory)/*GROUP_NUM*/).Write('|')
		.Write(String.EMPTY/*removedInVersion*/).Write('|').Write(String.EMPTY/*comment*/).Write('|').Write(fromSourceFile).Write('|')
		.Write(fromSourceFileNum).Write('|').Write(dmDiffsComparison).Write('|').Write(tag).Write('|') .Write(get_nowDtm()/*CREATED_DT*/).write('\n');
		
		return existing.code.number;
	}
	
	static final void writeChangeType(ChangeType changeType, String fromSourceFile, int fromSourceFileNum, String tag, final String dmDiffsComparison, final BufferedTextOutputStream utf8BufferedOS) {
		//TYPE|CODE|NAME|DESCRIPTION|NUM|GROUP_NUM|REMOVED_IN_VERSION|COMMENT|FROM_SOURCE_FILE|FROM_SOURCE_FILE_NUM|DM_CHG_COMPARISON|CREATION_TAG|CREATED_DT
		utf8BufferedOS.Write(DIFFS_CHANGE_TYPE_REF_DATA_TYPE).Write('|').Write(changeType.code).Write('|').Write(changeType.name).Write('|')
		.Write(String.EMPTY/*DESCRIPTION*/).Write('|').Write(changeType.code.number).Write('|').Write(changeType.code.changeCategory/*GROUP_NUM*/).Write('|')
		.Write(String.EMPTY/*removedInVersion*/).Write('|').Write(String.EMPTY/*comment*/).Write('|').Write(fromSourceFile).Write('|')
		.Write(fromSourceFileNum).Write('|').Write(dmDiffsComparison).Write('|').Write(tag).Write('|').Write(get_nowDtm()/*CREATED_DT*/).write('\n');
		
		//return existing.code.number;
	}
	
	
	/**
	* @return <code>null</code> if failed to get from persistent store.<br>
	*/
	static final DMCompDetailChangedType getDetailChangedTypeFromPersistentStore(String detaiChangedType, String fromSourceFile, int fromSourceFileNum, String tag) {
		//must persist the detail changed-type method if it does not already exist in the store and returns it...
		return null;
	}
	
	static final int writeDetailChangedType(String detaiChangedType, final DMCompEnumSets enums, String fromSourceFile, int fromSourceFileNum, String tag, final String dmDiffsComparison, final BufferedTextOutputStream utf8BufferedOS) {
		DMCompDetailChangedType existing = enums.detailChangedTypes.getByCode(detaiChangedType);
		if (existing != null) { //case the product already exists
			return -existing.number;
		}
		existing = getDetailChangedTypeFromPersistentStore(detaiChangedType, fromSourceFile, fromSourceFileNum, tag);
		if (existing != null) { //case the product already exists in the persistent store
			return -existing.number;
		}
		existing = new DMCompDetailChangedType((short)(enums.detailChangedTypes.size() + 1), detaiChangedType, detaiChangedType);
		enums.detailChangedTypes.add(existing);
		
		//TYPE|CODE|NAME|DESCRIPTION|NUM|GROUP_NUM|REMOVED_IN_VERSION|COMMENT|FROM_SOURCE_FILE|FROM_SOURCE_FILE_NUM|DM_CHG_COMPARISON|CREATION_TAG|CREATED_DT
		utf8BufferedOS.Write(DIFFS_DETAIL_CHANGED_TYPE_REF_DATA_TYPE).Write('|').Write(existing.code).Write('|').Write(existing.name).Write('|')
		.Write(String.EMPTY/*DESCRIPTION*/).Write('|').Write(existing.number).Write('|').Write(String.EMPTY/*GROUP_NUM*/).Write('|')
		.Write(String.EMPTY/*removedInVersion*/).Write('|').Write(String.EMPTY/*comment*/).Write('|').Write(fromSourceFile).Write('|')
		.Write(fromSourceFileNum).Write('|').Write(dmDiffsComparison).Write('|').Write(tag).Write('|').Write(get_nowDtm()/*CREATED_DT*/).write('\n');
		
		return existing.number;
	}
	static final void writeDetailChangedType(final DMCompDetailChangedType detailChangedType, final String fromSourceFile, final int fromSourceFileNum, String tag, final String dmDiffsComparison, final BufferedTextOutputStream utf8BufferedOS) {
		
		//TYPE|CODE|NAME|DESCRIPTION|NUM|GROUP_NUM|REMOVED_IN_VERSION|COMMENT|FROM_SOURCE_FILE|FROM_SOURCE_FILE_NUM|DM_CHG_COMPARISON|CREATION_TAG|CREATED_DT
		utf8BufferedOS.Write(DIFFS_DETAIL_CHANGED_TYPE_REF_DATA_TYPE).Write('|').Write(detailChangedType.code).Write('|').Write(detailChangedType.name).Write('|')
		.Write(String.EMPTY/*DESCRIPTION*/).Write('|').Write(detailChangedType.number).Write('|').Write(String.EMPTY/*GROUP_NUM*/).Write('|')
		.Write(String.EMPTY/*removedInVersion*/).Write('|').Write(String.EMPTY/*comment*/).Write('|').Write(fromSourceFile).Write('|')
		.Write(fromSourceFileNum).Write('|').Write(dmDiffsComparison).Write('|').Write(tag).Write('|').Write(get_nowDtm()/*CREATED_DT*/).write('\n');
		
	}
	
	static final String SOURCE_NUM_FIELD = CString.valueOf(new byte[]{'S', 'O', 'U', 'R', 'C', 'E', '_', 'N', 'U', 'M'}, true, true);
	static final String OWNER_PRODUCT_CODE_FIELD = CString.valueOf(new byte[]{'O', 'W', 'N', 'E', 'R', '_', 'P', 'R', 'O', 'D', 'U', 'C', 'T', '_', 'C', 'O', 'D', 'E'}, true, true);
	static final String OWNER_PRODUCT_NUM_FIELD = CString.valueOf(new byte[]{'O', 'W', 'N', 'E', 'R', '_', 'P', 'R', 'O', 'D', 'U', 'C', 'T', '_', 'N', 'U', 'M'}, true, true);
	
	static final String SECONDARY_FLAG_FIELD = CString.valueOf(new byte[]{'S', 'E', 'C', 'O', 'N', 'D', 'A', 'R', 'Y', '_', 'F', 'L', 'A', 'G'}, true, true);
	
	static void writeSourceFileHeader(final BufferedTextOutputStream utf8BufferedOS) {
		//NAME|SOURCE_NUM|DESCRIPTION|SECONDARY_FLAG|DM_CHG_COMPARISON|OWNER_PRODUCT_CODE|OWNER_PRODUCT_NUM|CREATION_TAG|CREATED_DT
		utf8BufferedOS.Write(NAME_FIELD).Write('|').Write(SOURCE_NUM_FIELD).Write('|').Write(DESCRIPTION_FIELD).Write('|')
		.Write(SECONDARY_FLAG_FIELD).Write('|').Write(DM_CHG_COMPARISON_FIELD).Write('|').Write(OWNER_PRODUCT_CODE_FIELD).Write('|')
		.Write(OWNER_PRODUCT_NUM_FIELD).Write('|').Write(CREATION_TAG_FIELD).Write('|').Write(CREATED_DT_FIELD).write('\n');
	}
	
	static void writeSourceFile(String sourceFile, int sourceFileNum, final boolean secondarySourceFile, final String dataModelDiffsComparison, 
								final String productCode, final int productNum, final String tag, final BufferedTextOutputStream utf8BufferedOS) {
		//NAME|SOURCE_NUM|DESCRIPTION|SECONDARY_FLAG|DM_CHG_COMPARISON|OWNER_PRODUCT_CODE|OWNER_PRODUCT_NUM|CREATION_TAG|CREATED_DT
		utf8BufferedOS.Write(sourceFile).Write('|').Write(sourceFileNum).Write('|').Write(String.EMPTY/*DESCRIPTION*/).Write('|')
		.Write(secondarySourceFile ? 'Y' : 'N').Write('|').Write(dataModelDiffsComparison).Write('|').Write(productCode == null ? String.EMPTY : productCode)
		.Write('|').Write(productNum).Write('|').Write(tag).Write('|').Write(get_nowDtm()).write('\n');
	}
	static void writeSourceFile(final String sourceFile, final int sourceFileNum, final String dataModelDiffsComparison, 
								final String productCode, final int productNum, final String tag, 
								final BufferedTextOutputStream utf8BufferedOS) {
		writeSourceFile(sourceFile, sourceFileNum, false/*secondarySourceFile*/, dataModelDiffsComparison, 
								productCode, productNum, tag, utf8BufferedOS);
	}
	static void writeSecondarySourceFile(final DMSource source, final String dataModelDiffsComparison, 
								final String tag, final BufferedTextOutputStream utf8BufferedOS) {
		writeSourceFile(source.ID/*sourceFile*/, source.getOutputNumber()/*sourceFileNum*/, 
								true/*secondarySourceFile*/, dataModelDiffsComparison, 
								source.product.code, source.product.number, tag, utf8BufferedOS);
	}
	
	static final String SUMMARY_NUM_FIELD = CString.valueOf(new byte[]{'S', 'U', 'M', 'M', 'A', 'R', 'Y', '_', 'N', 'U', 'M'}, true, true); 
	static final String SUMMARY_CAT_FIELD = CString.valueOf(new byte[]{'S', 'U', 'M', 'M', 'A', 'R', 'Y', '_', 'C', 'A', 'T'}, true, true); 
	static final String SUMMARY_CAT_NUM_FIELD = CString.valueOf(new byte[]{'S', 'U', 'M', 'M', 'A', 'R', 'Y', '_', 'C', 'A', 'T', '_', 'N', 'U', 'M'}, true, true); 
	static final String PRODUCT_CODE_FIELD = CString.valueOf(new byte[]{'P', 'R', 'O', 'D', 'U', 'C', 'T', '_', 'C', 'O', 'D', 'E'}, true, true); 
	static final String PRODUCT_NUM_FIELD = CString.valueOf(new byte[]{'P', 'R', 'O', 'D', 'U', 'C', 'T', '_', 'N', 'U', 'M'}, true, true); 
	static final String NUMBER_OF_DIFFS_FIELD = CString.valueOf(new byte[]{'N', 'U', 'M', 'B', 'E', 'R', '_', 'O', 'F', '_', 'D', 'I', 'F', 'F', 'S'}, true, true); 
	static final String NUMBER_OF_ADDED_FIELD = CString.valueOf(new byte[]{'N', 'U', 'M', 'B', 'E', 'R', '_', 'O', 'F', '_', 'A', 'D', 'D', 'E', 'D'}, true, true); 
	static final String NUMBER_OF_REMOVED_FIELD = CString.valueOf(new byte[]{'N', 'U', 'M', 'B', 'E', 'R', '_', 'O', 'F', '_', 'R', 'E', 'M', 'O', 'V', 'E', 'D'}, true, true); 
	static final String NUMBER_OF_CHANGED_FIELD = CString.valueOf(new byte[]{'N', 'U', 'M', 'B', 'E', 'R', '_', 'O', 'F', '_', 'C', 'H', 'A', 'N', 'G', 'E', 'D'}, true, true); 
	
	static final String SOURCE_FILE_FIELD = CString.valueOf(new byte[]{'S', 'O', 'U', 'R', 'C', 'E', '_', 'F', 'I', 'L', 'E'}, true, true); 
	static final String SOURCE_FILE_NUM_FIELD = CString.valueOf(new byte[]{'S', 'O', 'U', 'R', 'C', 'E', '_', 'F', 'I', 'L', 'E', '_', 'N', 'U', 'M'}, true, true); 
	
	static final String SUMMARY_LN_SEQ_NUM_FIELD = CString.valueOf(new byte[]{'S', 'U', 'M', 'M', 'A', 'R', 'Y', '_', 'L', 'N', '_', 'S', 'E', 'Q', '_', 'N', 'U', 'M'}, true, true); 
	
	static void writeSummaryHeader(final BufferedTextOutputStream utf8BufferedOS) {
		//SUMMARY_LN_SEQ_NUM|SUMMARY_NUM|SUMMARY_CAT_CODE|SUMMARY_CAT_NUM|PRODUCT_CODE|PRODUCT_NUM|NUMBER_OF_DIFFS|NUMBER_OF_ADDED|NUMBER_OF_REMOVED|NUMBER_OF_CHANGED|DM_CHG_COMPARISON|SOURCE_FILE|SOURCE_FILE_NUM|CREATION_TAG|CREATED_DT|COMMENT
		utf8BufferedOS.Write(SUMMARY_LN_SEQ_NUM_FIELD).Write('|').Write(SUMMARY_NUM_FIELD).Write('|').Write(SUMMARY_CAT_FIELD).Write('|').Write(SUMMARY_CAT_NUM_FIELD).Write('|')
		.Write(PRODUCT_CODE_FIELD).Write('|').Write(PRODUCT_NUM_FIELD).Write('|')
		.Write(NUMBER_OF_DIFFS_FIELD).Write('|').Write(NUMBER_OF_ADDED_FIELD).Write('|').Write(NUMBER_OF_REMOVED_FIELD).Write('|')
		.Write(NUMBER_OF_CHANGED_FIELD).Write('|').Write(DM_CHG_COMPARISON_FIELD).Write('|').Write(SOURCE_FILE_FIELD).Write('|').Write(SOURCE_FILE_NUM_FIELD).Write('|')
		.Write(CREATION_TAG_FIELD).Write('|').Write(CREATED_DT_FIELD).Write('|').Write(COMMENT_FIELD).write('\n');
	}
	
	static void writeSummary(int summaryLineSeqNum, int summaryNumber, byte summaryCat, String productCode, int productNum, 
									int numberOfDiffs, int numberOfAdded, int numberOfRemoved, int numberOfChanged, 
									String sourceFile, int sourceFileNum, String dataModelDiffsComparison, String tag, 
									String comment, final BufferedTextOutputStream utf8BufferedOS) {
		//SUMMARY_LN_SEQ_NUM|SUMMARY_NUM|SUMMARY_CAT_CODE|SUMMARY_CAT_NUM|PRODUCT_CODE|PRODUCT_NUM|NUMBER_OF_DIFFS|NUMBER_OF_ADDED|NUMBER_OF_REMOVED|NUMBER_OF_CHANGED|DM_CHG_COMPARISON|SOURCE_FILE|SOURCE_FILE_NUM|CREATION_TAG|CREATED_DT|COMMENT
		utf8BufferedOS.Write(summaryLineSeqNum).Write('|').Write(summaryNumber).Write('|').Write(DMCompSummaryCategories.get_CategoryName(summaryCat)).Write('|').Write(summaryCat).Write('|')
		.Write(productCode).Write('|').Write(productNum).Write('|').Write(numberOfDiffs).write('|')
		;
		if (numberOfAdded < 0) {
			utf8BufferedOS.Write(String.EMPTY).write('|');
		}
		else {
			utf8BufferedOS.Write(numberOfAdded).write('|');
		}
		if (numberOfRemoved < 0) {
			utf8BufferedOS.Write(String.EMPTY).write('|');
		}
		else {
			utf8BufferedOS.Write(numberOfRemoved).write('|');
		}
		if (numberOfChanged < 0) {
			utf8BufferedOS.Write(String.EMPTY).write('|');
		}
		else {
			utf8BufferedOS.Write(numberOfChanged).write('|');
		}
		utf8BufferedOS.Write(dataModelDiffsComparison).Write('|').Write(sourceFile).Write('|').Write(sourceFileNum).Write('|')
		.Write(tag).Write('|').Write(get_nowDtm()).Write('|').Write(comment == null ? String.EMPTY : comment).write('\n');
	} 
	
	/**
	* <b>TODO - URGENT</b>: check if the error message has line terminator characters and encode then to "<br>" as it is done for diffs table rows - FACTORIZE THE CODE!!!
	*/
	static void writeDiffsSummaryOuputError(final int summaryNumber, final int summaryLineNum, final int summaryCatNumber, 
												final String dataModelDiffsComparison, final String tag, 
												final String sourceFile, final int sourceFileNum, 
												java.lang.String errMsg, 
												final BufferedTextOutputStream utf8BufferedOS, 
												string.CharBuffer tempBuffer) { 
		if (errMsg == null) {
			errMsg = "<null>";
		}
		utf8BufferedOS.Write(get_nowDtm()).Write('|').Write(summaryNumber).Write('|').Write(DMCompSummaryCategories.get_CategoryName(summaryCatNumber)).Write('|')
		.Write(summaryCatNumber).Write('|').Write(String.EMPTY/*DIFFS_TABLE_NUM*/).Write('|').Write(String.EMPTY/*DIFFS_OBJ_CHG_NUM*/).Write('|')
		.Write(String.EMPTY/*OBJECT_NAME*/).Write('|').Write(String.EMPTY/*OBJECT_NUM*/).Write('|').Write(String.EMPTY/**/).Write('|').Write(String.EMPTY/*CHANGE_FIELD*/).Write('|')
		.Write(summaryLineNum/*DIFFS_TABLE_ROW_NUM*/).Write('|').Write(sourceFile).Write('|').Write(sourceFileNum).Write('|')
		.Write(dataModelDiffsComparison).Write('|').Write(tag).Write('|');
		string.CharBuffer inlinedRsltBuffer = ensureErrMsgIsInline(errMsg, tempBuffer);
		if (inlinedRsltBuffer != null) {
			utf8BufferedOS.Write(inlinedRsltBuffer).write('\n');
		}
		else {
			utf8BufferedOS.Write(errMsg).write('\n');
		}
	}
	
	static final void writeSummaryReport(final DiffsSummary summaryRpt, final eBSProduct product, 
									String sourceFile, int sourceFileNum, String dataModelDiffsComparison, 
									String tag, String comment, 
									final BufferedTextOutputStream utf8BufferedOS, 
									final BufferedTextOutputStream errorUtf8BufferedOS, 
									string.CharBuffer tempBuffer) {
		////System.out.println("writeSummaryReport - (summaryRpt == null): " + (summaryRpt == null) + ", (product == null): " + (product == null) + ", (sourceFile == null): " + (sourceFile == null) + ", (dataModelDiffsComparison == null): " + (dataModelDiffsComparison == null) + ", (utf8BufferedOS == null): " + (utf8BufferedOS == null) + ", (errorUtf8BufferedOS == null): " + (errorUtf8BufferedOS == null));						
		final int summaryNumber = summaryRpt.getOutputNumber();
		int summaryLineNum = 0;
		for (byte n=1;n<=NUMBER_OF_SUMMARY_CATEGORIES;n++)
		{
			try
			{
				summaryLineNum = DiffsSummary.nextLineSequenceValue();
				writeSummary(summaryLineNum, summaryNumber/*summaryNumber*/, n/*summaryCat*/, product.code, product.number, 
										summaryRpt.getDiffs(n)/*numberOfDiffs*/, -1/*numberOfAdded*/, -1/*numberOfRemoved*/, -1/*numberOfChanged*/, 
										sourceFile, sourceFileNum, dataModelDiffsComparison, tag, 
										comment, utf8BufferedOS);
			}
			catch(Exception ex)
			{
				System.out.println("DMCompOutputUti::::::::::: dataModelDiffsComparison=" + dataModelDiffsComparison + ", tag: " + tag + ", sourceFile: " + sourceFile + ", (ex==null): " + (ex==null));
				writeDiffsSummaryOuputError(summaryNumber, summaryLineNum, n/*summaryCatNumber*/, 
												dataModelDiffsComparison, tag, 
												sourceFile, sourceFileNum, 
												ex.getMessage(), 
												 errorUtf8BufferedOS, 
												 tempBuffer)
												;
				//ex.printStackTrace();
			}
		}
		
	}
	
	static final String VALUE_FIELD = CString.valueOf(new byte[]{'V', 'A', 'L', 'U', 'E'}, true, true); 
	static final String SHORT_CODE_FIELD = CString.valueOf(new byte[]{'S', 'H', 'O', 'R', 'T', '_', 'C', 'O', 'D', 'E'}, true, true); 
	static final String CATEGORY_FIELD = CString.valueOf(new byte[]{'C', 'A', 'T', 'E', 'G', 'O', 'R', 'Y'}, true, true); 
	
	static final void writeDiffsOuputTranscoHeader(final BufferedTextOutputStream transcoUtf8BufferedOS) {
		//CATEGORY|SHORT_CODE|VALUE
		transcoUtf8BufferedOS.Write(CATEGORY_FIELD).Write('|').Write(SHORT_CODE_FIELD).Write('|').Write(VALUE_FIELD).Write('\n');
	}
	static final void writeDiffsOuputTransco(final string.String category, final string.String shortCode, final string.String value, final BufferedTextOutputStream transcoUtf8BufferedOS) {
		//CATEGORY|SHORT_CODE|VALUE
		transcoUtf8BufferedOS.Write(category).Write('|').Write(shortCode).Write('|').Write(value).Write('\n');
	}
	
	static final String OWNER_FIELD = CString.valueOf(new byte[]{'O', 'W', 'N', 'E', 'R'}, true, true); 
	static final String OBJECT_NAME_FIELD = CString.valueOf(new byte[]{'O', 'B', 'J', 'E', 'C', 'T', '_', 'N', 'A', 'M', 'E'}, true, true); 
	static final String OBJECT_NUM_FIELD = CString.valueOf(new byte[]{'O', 'B', 'J', 'E', 'C', 'T', '_', 'N', 'U', 'M'}, true, true); 
	static final String PACKAGE_APP_CODE_FIELD = CString.valueOf(new byte[]{'P', 'A', 'C', 'K', 'A', 'G', 'E', '_', 'A', 'P', 'P', '_', 'C', 'O', 'D', 'E'}, true, true); 
	static final String SUMMARY_CAT_CODE_FIELD = CString.valueOf(new byte[]{'S', 'U', 'M', 'M', 'A', 'R', 'Y', '_', 'C', 'A', 'T', '_', 'C', 'O', 'D', 'E'}, true, true); 
	static final String TAG_FIELD = CString.valueOf(new byte[]{'T', 'A', 'G'}, true, true);
	
	static final String PARENT_OBJECT_NAME_FIELD = CString.valueOf(new byte[]{'P', 'A', 'R', 'E', 'N', 'T', '_', 'O', 'B', 'J', 'E', 'C', 'T', '_', 'N', 'A', 'M', 'E'}, true, true);
	static final String CHILD_OBJECT_FLG_FIELD = CString.valueOf(new byte[]{'C', 'H', 'I', 'L', 'D', '_', 'O', 'B', 'J', 'E', 'C', 'T', '_', 'F', 'L', 'G'}, true, true);
	
	static final String CHILD_OBJ_TYPE_NUM_FIELD = CString.valueOf(new byte[]{'C', 'H', 'I', 'L', 'D', '_', 'O', 'B', 'J', '_', 'T', 'Y', 'P', 'E', '_', 'N', 'U', 'M'}, true, true);
	static final String CHILD_OBJ_TYPE_CODE_FIELD = CString.valueOf(new byte[]{'C', 'H', 'I', 'L', 'D', '_', 'O', 'B', 'J', '_', 'T', 'Y', 'P', 'E', '_', 'C', 'O', 'D', 'E'}, true, true);
	
	static final String CHILD_OBJ_MODIFIER_FIELD = CString.valueOf(new byte[]{'C', 'H', 'I', 'L', 'D', '_', 'O', 'B', 'J', '_', 'M', 'O', 'D', 'I', 'F', 'I', 'E', 'R'}, true, true);
	
	static void writeObjectHeader(final BufferedTextOutputStream utf8BufferedOS) {
		//OBJECT_NUM|OWNER|NAME|CHILD_OBJECT_FLG|CHILD_OBJ_TYPE_NUM|CHILD_OBJ_TYPE_CODE|
		//PARENT_OBJECT_NAME|CHILD_OBJ_MODIFIER|
		//SUMMARY_CAT_CODE|SUMMARY_CAT_NUM|OWNER_PRODUCT_CODE|OWNER_PRODUCT_NUM|
		//PACKAGE_APP_CODE|DESCRIPTION|SOURCE_FILE|SOURCE_FILE_NUM|DM_CHG_COMPARISON|TAG|CREATED_DT
		utf8BufferedOS.Write(OBJECT_NUM_FIELD).Write('|').Write(OWNER_FIELD).Write('|').Write(NAME_FIELD).Write('|')
		.Write(CHILD_OBJECT_FLG_FIELD).Write('|').Write(CHILD_OBJ_TYPE_NUM_FIELD).Write('|').Write(CHILD_OBJ_TYPE_CODE_FIELD).Write('|')
		.Write(PARENT_OBJECT_NAME_FIELD).Write('|').Write(CHILD_OBJ_MODIFIER_FIELD).Write('|')
		.Write(SUMMARY_CAT_CODE_FIELD).Write('|').Write(SUMMARY_CAT_NUM_FIELD).Write('|').Write(OWNER_PRODUCT_CODE_FIELD).Write('|')
		.Write(OWNER_PRODUCT_NUM_FIELD).Write('|').Write(PACKAGE_APP_CODE_FIELD).Write('|').Write(DESCRIPTION_FIELD).Write('|')
		.Write(SOURCE_FILE_FIELD).Write('|').Write(SOURCE_FILE_NUM_FIELD).Write('|').Write(DM_CHG_COMPARISON_FIELD).Write('|')
		.Write(TAG_FIELD).Write('|').Write(CREATED_DT_FIELD).write('\n');
	}
	
	static void writeObject(final int objectNum, String owner, String objectName, 
									String summaryCatCode, int summaryCatNum, 
									String productCode, int productNumber, 
									String packageAppCode, String description, 
									final boolean isChildObject, int childObjTypeNum, 
									final String childObjTypeCode, final String childObjModifier, 
									final String parentObjectName, 
									String sourceFile, int sourceFileNum, 
									String dmDiffsComparison, String tag, 
									final BufferedTextOutputStream utf8BufferedOS, 
									string.CharBuffer tempBuffer) {
										
		if (isChildObject) {
			if (tempBuffer == null) {
				tempBuffer = new string.CharBuffer(10);
			}
			else {
				tempBuffer.resetLimitOnly();
			}
			tempBuffer.appendInteger(childObjTypeNum);
		}
										
		//OBJECT_NUM|OWNER|NAME|CHILD_OBJECT_FLG|CHILD_OBJ_TYPE_NUM|CHILD_OBJ_TYPE_CODE|PARENT_OBJECT_NAME|
		//SUMMARY_CAT_CODE|SUMMARY_CAT_NUM|OWNER_PRODUCT_CODE|OWNER_PRODUCT_NUM|
		//PACKAGE_APP_CODE|DESCRIPTION|SOURCE_FILE|SOURCE_FILE_NUM|DM_CHG_COMPARISON|TAG|CREATED_DT
		utf8BufferedOS.Write(objectNum).Write('|').Write(owner).Write('|').Write(objectName).Write('|')
		.Write(isChildObject ? __Y__ : __N__/*CHILD_OBJECT_FLG*/).Write('|')
		.Write(isChildObject ? tempBuffer : String.EMPTY/*CHILD_OBJ_TYPE_NUM*/).Write('|')
		.Write(isChildObject ? childObjTypeCode : String.EMPTY/*CHILD_OBJ_TYPE_CODE*/).Write('|')
		.Write(isChildObject ? parentObjectName : String.EMPTY/*PARENT_OBJECT_NAME*/).Write('|')
		.Write(isChildObject && childObjModifier != null ? childObjModifier : String.EMPTY/*PARENT_OBJECT_NAME*/).Write('|')
		.Write(summaryCatCode).Write('|').Write(summaryCatNum).Write('|').Write(productCode).Write('|')
		.Write(productNumber).Write('|').Write(packageAppCode == null ? String.EMPTY : packageAppCode).Write('|')
		.Write(description == null ? String.EMPTY : description).Write('|').Write(sourceFile).Write('|')
		.Write(sourceFileNum).Write('|').Write(dmDiffsComparison).Write('|') 
		.Write(tag).Write('|').Write(get_nowDtm()).write('\n');
	}
	
	static final String DETAIL_PATH_NUM_FIELD = CString.valueOf(new byte[]{'D', 'E', 'T', 'A', 'I', 'L', '_', 'P', 'A', 'T', 'H', '_', 'N', 'U', 'M'}, true, true);
	static final String PATH_SZ_FIELD = CString.valueOf(new byte[]{'P', 'A', 'T', 'H', '_', 'S', 'Z'}, true, true);
	//static final String DM_CHG_COMPARISON_FIELD = CString.valueOf(new byte[]{'D', 'M', '_', 'C', 'H', 'G', '_', 'C', 'O', 'M', 'P', 'A', 'R', 'I', 'S', 'O', 'N'}, true, true);
	static final String TOP_DTL_TYPE_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'D', 'T', 'L', '_', 'T', 'Y', 'P', 'E'}, true, true);
	static final String TOP_DTL_NAME_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'D', 'T', 'L', '_', 'N', 'A', 'M', 'E'}, true, true);
	static final String TOP_DTL_QUAL_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'D', 'T', 'L', '_', 'Q', 'U', 'A', 'L'}, true, true);
	static final String TOP_M1_DTL_TYPE_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '1', '_', 'D', 'T', 'L', '_', 'T', 'Y', 'P', 'E'}, true, true);
	static final String TOP_M1_DTL_NAME_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '1', '_', 'D', 'T', 'L', '_', 'N', 'A', 'M', 'E'}, true, true);
	static final String TOP_M1_DTL_QUAL_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '1', '_', 'D', 'T', 'L', '_', 'Q', 'U', 'A', 'L'}, true, true);
	static final String TOP_M2_DTL_TYPE_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '2', '_', 'D', 'T', 'L', '_', 'T', 'Y', 'P', 'E'}, true, true);
	static final String TOP_M2_DTL_NAME_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '2', '_', 'D', 'T', 'L', '_', 'N', 'A', 'M', 'E'}, true, true);
	static final String TOP_M2_DTL_QUAL_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '2', '_', 'D', 'T', 'L', '_', 'Q', 'U', 'A', 'L'}, true, true);
	static final String TOP_M3_DTL_TYPE_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '3', '_', 'D', 'T', 'L', '_', 'T', 'Y', 'P', 'E'}, true, true);
	static final String TOP_M3_DTL_NAME_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '3', '_', 'D', 'T', 'L', '_', 'N', 'A', 'M', 'E'}, true, true);
	static final String TOP_M3_DTL_QUAL_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '3', '_', 'D', 'T', 'L', '_', 'Q', 'U', 'A', 'L'}, true, true);
	static final String TOP_M4_DTL_TYPE_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '4', '_', 'D', 'T', 'L', '_', 'T', 'Y', 'P', 'E'}, true, true);
	static final String TOP_M4_DTL_NAME_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '4', '_', 'D', 'T', 'L', '_', 'N', 'A', 'M', 'E'}, true, true);
	static final String TOP_M4_DTL_QUAL_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '4', '_', 'D', 'T', 'L', '_', 'Q', 'U', 'A', 'L'}, true, true);
	static final String TOP_M5_DTL_TYPE_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '5', '_', 'D', 'T', 'L', '_', 'T', 'Y', 'P', 'E'}, true, true);
	static final String TOP_M5_DTL_NAME_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '5', '_', 'D', 'T', 'L', '_', 'N', 'A', 'M', 'E'}, true, true);
	static final String TOP_M5_DTL_QUAL_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '5', '_', 'D', 'T', 'L', '_', 'Q', 'U', 'A', 'L'}, true, true);
	static final String TOP_M6_DTL_TYPE_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '6', '_', 'D', 'T', 'L', '_', 'T', 'Y', 'P', 'E'}, true, true);
	static final String TOP_M6_DTL_NAME_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '6', '_', 'D', 'T', 'L', '_', 'N', 'A', 'M', 'E'}, true, true);
	static final String TOP_M6_DTL_QUAL_FIELD = CString.valueOf(new byte[]{'T', 'O', 'P', '_', 'M', '6', '_', 'D', 'T', 'L', '_', 'Q', 'U', 'A', 'L'}, true, true);
	static final String SOURCE_FILE_NAME_FIELD = CString.valueOf(new byte[]{'S', 'O', 'U', 'R', 'C', 'E', '_', 'F', 'I', 'L', 'E', '_', 'N', 'A', 'M', 'E'}, true, true);
	
	static final void writeDetailPathHeader(final BufferedTextOutputStream utf8BufferedOS) {
		utf8BufferedOS.Write(DETAIL_PATH_NUM_FIELD).Write('|').Write(PATH_SZ_FIELD).Write('|').Write(DM_CHG_COMPARISON_FIELD).Write('|')
		.Write(TOP_DTL_TYPE_FIELD).Write('|').Write(TOP_DTL_NAME_FIELD).Write('|').Write(TOP_DTL_QUAL_FIELD).Write('|')
		.Write(TOP_M1_DTL_TYPE_FIELD).Write('|').Write(TOP_M1_DTL_NAME_FIELD).Write('|').Write(TOP_M1_DTL_QUAL_FIELD).Write('|')
		.Write(TOP_M2_DTL_TYPE_FIELD).Write('|').Write(TOP_M2_DTL_NAME_FIELD).Write('|').Write(TOP_M2_DTL_QUAL_FIELD).Write('|')
		.Write(TOP_M3_DTL_TYPE_FIELD).Write('|').Write(TOP_M3_DTL_NAME_FIELD).Write('|').Write(TOP_M3_DTL_QUAL_FIELD).Write('|')
		.Write(TOP_M4_DTL_TYPE_FIELD).Write('|').Write(TOP_M4_DTL_NAME_FIELD).Write('|').Write(TOP_M4_DTL_QUAL_FIELD).Write('|')
		.Write(TOP_M5_DTL_TYPE_FIELD).Write('|').Write(TOP_M5_DTL_NAME_FIELD).Write('|').Write(TOP_M5_DTL_QUAL_FIELD).Write('|')
		.Write(TOP_M6_DTL_TYPE_FIELD).Write('|').Write(TOP_M6_DTL_NAME_FIELD).Write('|').Write(TOP_M6_DTL_QUAL_FIELD).Write('|')
		.Write(TAG_FIELD).Write('|').Write(CREATED_DT_FIELD).Write('|').Write(SOURCE_FILE_NAME_FIELD).Write('|').Write(SOURCE_FILE_NUM_FIELD).write('\n');
	}
	
	
	private static final int __skipNameTrailingSpaceBeforerite(final String name, int nameLen) {
		__skip_trailing_space:
		do 
		{
			if (nameLen > -1) {
				switch(name.getChar(nameLen))
				{
				case ' ':
				case '\t': 
					nameLen--;
					continue ;
				}
				return ++nameLen;
			}
			throw new WriteToStreamException(
			"DMCompOutputUtil::__skipNameTrailingSpaceBeforerite-1: cannot write an empty node simple name (name='" + name + "')"
			);
		} while (true); //end 
		
	}
	static void writeDetailPath(final DMCompDetailPath detailPath, String sourceFile, int sourceFileNum, final String dmDiffsComparison, final String tag, 
									final BufferedTextOutputStream utf8BufferedOS) {
		////System.out.println("DMCompOutputUtil - dmDiffsComparison: " + dmDiffsComparison);
		////System.out.println("DMCompOutputUtil - detailPath: \r\n" + detailPath);
		utf8BufferedOS.Write(detailPath.number).Write('|').Write(detailPath.sz).Write('|').Write(dmDiffsComparison).Write('|');
		/*.Write(TOP_DTL_TYPE_FIELD).Write('|').Write(TOP_DTL_NAME_FIELD).Write('|').Write(TOP_DTL_QUAL_FIELD).Write('|')
		.Write(TOP_M1_DTL_TYPE_FIELD).Write('|').Write(TOP_M1_DTL_NAME_FIELD).Write('|').Write(TOP_M1_DTL_QUAL_FIELD).Write('|')
		.Write(TOP_M2_DTL_TYPE_FIELD).Write('|').Write(TOP_M2_DTL_NAME_FIELD).Write('|').Write(TOP_M2_DTL_QUAL_FIELD).Write('|')
		.Write(TOP_M3_DTL_TYPE_FIELD).Write('|').Write(TOP_M3_DTL_NAME_FIELD).Write('|').Write(TOP_M3_DTL_QUAL_FIELD).Write('|')
		.Write(TOP_M4_DTL_TYPE_FIELD).Write('|').Write(TOP_M4_DTL_NAME_FIELD).Write('|').Write(TOP_M4_DTL_QUAL_FIELD).Write('|')
		.Write(TOP_M5_DTL_TYPE_FIELD).Write('|').Write(TOP_M5_DTL_NAME_FIELD).Write('|').Write(TOP_M5_DTL_QUAL_FIELD).Write('|')
		.Write(TOP_M6_DTL_TYPE_FIELD).Write('|').Write(TOP_M6_DTL_NAME_FIELD).Write('|').Write(TOP_M6_DTL_QUAL_FIELD).Write('|')
		*/
		if (detailPath.sz > DMCompDetailPath.MAX_SZ) {
			throw new WriteToStreamException(
			"DMCompOutputUtil::writeDetailPath-1: size of the path too big - the max size that can be handled is DMCompDetailPath.MAX_SZ"
			);
		}
		int off=0, end = (detailPath.sz & 0xFFFF) << 1;
		for (;off<end;off+=2)
		{
			String type = detailPath.getType(off);
			String name = detailPath.getName(off);
			int nameLen = name.length();
			String qualifier = String.EMPTY;
			int chkRslt = DMCompDetailPath.checkIfQualifiedName(name);
			if (chkRslt > -1) { //case there's a qualifier in the name
				//System.out.println("DMCompOutputUtil++++++++++++++++++ nameLen: " + nameLen + ", chkRslt: " + chkRslt + ", name: " + name);
				qualifier = name.substring(chkRslt/*indexOfOpeningPrenthseis*/ + 1, nameLen - 1);
				nameLen = chkRslt/*indexOfOpeningPrenthseis*/; //BUG-FIX-2017-04-27 - was doing minus one
				name = name.left(nameLen);
				
			} //end case there's a qualifier in the name
			else {
				if (chkRslt == 0x80000000) { //case there's the closing parenthesis but no opening parenthesis
					throw new WriteToStreamException(
					"DMCompOutputUtil::writeDetailPath-3: malformed qualified node name (name='" + name + "')"
					);
				}
			}
			//System.out.println("DMCompOutputUtil::writeDetailPath - type: " + type + ", name: " + name + ", qualifier: " + qualifier);
			utf8BufferedOS.Write(type).Write('|').Write(name).Write('|').Write(qualifier).Write('|');
		}
		////System.out.println("DMCompOutputUtil::writeDetailPath - detailPath.sz: " + detailPath.sz + ", DMCompDetailPath.MAX_SZ: " + DMCompDetailPath.MAX_SZ + ", end: " + end + ", off: " + off);
		if (detailPath.sz < DMCompDetailPath.MAX_SZ) {
			off >>= 1;
			for (;off<DMCompDetailPath.MAX_SZ;off++)
			{
				utf8BufferedOS.write("|||"); //BUG-FIX-2017-05-04 - dropped one pipe separator
			}
		}
		utf8BufferedOS.Write(tag).Write('|').Write(get_nowDtm()).Write('|').Write(sourceFile).Write('|').Write(sourceFileNum).write('\n');
	}
	
	static final String DM_CMP_DETAIL_NUM_FIELD = CString.valueOf(new byte[]{'D', 'M', '_', 'C', 'M', 'P', '_', 'D', 'E', 'T', 'A', 'I', 'L', '_', 'N', 'U', 'M'}, true, true);
	static final String DETAIL_NAME_FIELD = CString.valueOf(new byte[]{'D', 'E', 'T', 'A', 'I', 'L', '_', 'N', 'A', 'M', 'E'}, true, true);
	static final String QUALIFIER_FIELD = CString.valueOf(new byte[]{'Q', 'U', 'A', 'L', 'I', 'F', 'I', 'E', 'R'}, true, true);
	static final String SUBJECT_NAME_FIELD = CString.valueOf(new byte[]{'S', 'U', 'B', 'J', 'E', 'C', 'T', '_', 'N', 'A', 'M', 'E'}, true, true);
	static final String SUBJECT_TYPE_NUM_FIELD = CString.valueOf(new byte[]{'S', 'U', 'B', 'J', 'E', 'C', 'T', '_', 'T', 'Y', 'P', 'E', '_', 'N', 'U', 'M'}, true, true);
	static final String SUBJECT_TYPE_FIELD = CString.valueOf(new byte[]{'S', 'U', 'B', 'J', 'E', 'C', 'T', '_', 'T', 'Y', 'P', 'E'}, true, true);
	static final String SUBJECT_QUAL_FIELD = CString.valueOf(new byte[]{'S', 'U', 'B', 'J', 'E', 'C', 'T', '_', 'Q', 'U', 'A', 'L'}, true, true);
	static final String DETAIL_TYPE_NUM_FIELD = CString.valueOf(new byte[]{'D', 'E', 'T', 'A', 'I', 'L', '_', 'T', 'Y', 'P', 'E', '_', 'N', 'U', 'M'}, true, true);
	static final String DETAIL_CHG_TYPE_NUM_FIELD = CString.valueOf(new byte[]{'D', 'E', 'T', 'A', 'I', 'L', '_', 'C', 'H', 'G', '_', 'T', 'Y', 'P', 'E', '_', 'N', 'U', 'M'}, true, true);
	static final String DETAIL_CHG_TYPE_FIELD = CString.valueOf(new byte[]{'D', 'E', 'T', 'A', 'I', 'L', '_', 'C', 'H', 'G', '_', 'T', 'Y', 'P', 'E'}, true, true);
	static final String DETAIL_SUB_CHG_TYPE_NUM_FIELD = CString.valueOf(new byte[]{'D', 'E', 'T', 'A', 'I', 'L', '_', 'S', 'U', 'B', '_', 'C', 'H', 'G', '_', 'T', 'Y', 'P', 'E', '_', 'N', 'U', 'M'}, true, true);
	static final String DETAIL_SUB_CHG_TYPE_FIELD = CString.valueOf(new byte[]{'D', 'E', 'T', 'A', 'I', 'L', '_', 'S', 'U', 'B', '_', 'C', 'H', 'G', '_', 'T', 'Y', 'P', 'E'}, true, true);
	static final String DETAIL_CHAIN_PATH_SZ_FIELD = CString.valueOf(new byte[]{'D', 'E', 'T', 'A', 'I', 'L', '_', 'C', 'H', 'A', 'I', 'N', '_', 'P', 'A', 'T', 'H', '_', 'S', 'Z'}, true, true);
	
	static final String IS_NEW_INDEX_FLG_FIELD = CString.valueOf(new byte[]{'I', 'S', '_', 'N', 'E', 'W', '_', 'I', 'N', 'D', 'E', 'X', '_', 'F', 'L', 'G'}, true, true);
	static final String OTHER_DATA_VALUE_TYPE_FLG_FIELD = CString.valueOf(new byte[]{'O', 'T', 'H', 'E', 'R', '_', 'D', 'A', 'T', 'A', '_', 'V', 'A', 'L', 'U', 'E', '_', 'T', 'Y', 'P', 'E', '_', 'F', 'L', 'G'}, true, true);
	static final String OTHER_DATA_FIELD = OTHER_DATA_VALUE_TYPE_FLG_FIELD.left(10); //CString.valueOf(new byte[]{'O', 'T', 'H', 'E', 'R', '_', 'D', 'A', 'T', 'A'}, true, true);
	static final String TO_DECIMAL_VALUE_FIELD = CString.valueOf(new byte[]{'T', 'O', '_', 'D', 'E', 'C', 'I', 'M', 'A', 'L', '_', 'V', 'A', 'L', 'U', 'E'}, true, true);
	static final String FROM_DECIMAL_VALUE_FIELD = CString.valueOf(new byte[]{'F', 'R', 'O', 'M', '_', 'D', 'E', 'C', 'I', 'M', 'A', 'L', '_', 'V', 'A', 'L', 'U', 'E'}, true, true);
	static final String TO_INTEGER_VALUE_FIELD = CString.valueOf(new byte[]{'T', 'O', '_', 'I', 'N', 'T', 'E', 'G', 'E', 'R', '_', 'V', 'A', 'L', 'U', 'E'}, true, true);
	static final String FROM_INTEGER_VALUE_FIELD = CString.valueOf(new byte[]{'F', 'R', 'O', 'M', '_', 'I', 'N', 'T', 'E', 'G', 'E', 'R', '_', 'V', 'A', 'L', 'U', 'E'}, true, true);
	static final String TO_TEXT_VALUE_FIELD = CString.valueOf(new byte[]{'T', 'O', '_', 'T', 'E', 'X', 'T', '_', 'V', 'A', 'L', 'U', 'E'}, true, true);
	static final String FROM_TEXT_VALUE_FIELD = CString.valueOf(new byte[]{'F', 'R', 'O', 'M', '_', 'T', 'E', 'X', 'T', '_', 'V', 'A', 'L', 'U', 'E'}, true, true);
	
	static final String VALUE_TYPE_FLG_FIELD = CString.valueOf(new byte[]{'V', 'A', 'L', 'U', 'E', '_', 'T', 'Y', 'P', 'E', '_', 'F', 'L', 'G'}, true, true);
	static final String DETAIL_TYPE_FIELD = CString.valueOf(new byte[]{'D', 'E', 'T', 'A', 'I', 'L', '_', 'T', 'Y', 'P', 'E'}, true, true);
	
	static final String DIFFS_TABLE_ROW_NUM_FIELD = CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'T', 'A', 'B', 'L', 'E', '_', 'R', 'O', 'W', '_', 'N', 'U', 'M'}, true, true);
	static final String DIFFS_CHG_DETAIL_LVL_INDIC_FIELD = CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'C', 'H', 'G', '_', 'D', 'E', 'T', 'A', 'I', 'L', '_', 'L', 'V', 'L', '_', 'I', 'N', 'D', 'I', 'C'}, true, true);
	static final String DIFFS_CHG_NUM_FIELD = CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'C', 'H', 'G', '_', 'N', 'U', 'M'}, true, true);
	
	static final String DIFFS_CMP_MTHD_CAT_CD_FIELD = CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'C', 'M', 'P', '_', 'M', 'T', 'H', 'D', '_', 'C', 'A', 'T', '_', 'C', 'D'}, true, true);
	static final String DIFFS_CMP_MTHD_CAT_NUM_FIELD = CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'C', 'M', 'P', '_', 'M', 'T', 'H', 'D', '_', 'C', 'A', 'T', '_', 'N', 'U', 'M'}, true, true);
	static final String DIFFS_CMP_MTHD_CD_FIELD = CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'C', 'M', 'P', '_', 'M', 'T', 'H', 'D', '_', 'C', 'D'}, true, true);
	static final String DIFFS_CMP_MTHD_NUM_FIELD = CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'C', 'M', 'P', '_', 'M', 'T', 'H', 'D', '_', 'N', 'U', 'M'}, true, true);
	
	static final String SUBJECT_NUM_FIELD = CString.valueOf(new byte[]{'S', 'U', 'B', 'J', 'E', 'C', 'T', '_', 'N', 'U', 'M'}, true, true);
	static final String DIFFS_TABLE_NUM_FIELD = CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'T', 'A', 'B', 'L', 'E', '_', 'N', 'U', 'M'}, true, true);
	
	static void writeDiffsDetailHeader(final BufferedTextOutputStream utf8BufferedOS) {
		utf8BufferedOS.Write(DM_CMP_DETAIL_NUM_FIELD).Write('|').Write(DIFFS_TABLE_ROW_NUM_FIELD/*new*/).Write('|').Write(DIFFS_CHG_DETAIL_LVL_INDIC_FIELD/*new*/).Write('|')
		.Write(DIFFS_CHG_NUM_FIELD/*new*/).Write('|').Write(OWNER_FIELD).Write('|').Write(DETAIL_NAME_FIELD).Write('|')
		.Write(QUALIFIER_FIELD).Write('|').Write(OBJECT_NAME_FIELD).Write('|').Write(OBJECT_NUM_FIELD).Write('|')
		.Write(SUBJECT_NAME_FIELD).Write('|').Write(SUBJECT_TYPE_FIELD).Write('|').Write(SUBJECT_TYPE_NUM_FIELD).Write('|')
		.Write(SUBJECT_QUAL_FIELD).Write('|').Write(SUBJECT_NUM_FIELD).Write('|')
		.Write(PRODUCT_CODE_FIELD).Write('|').Write(PRODUCT_NUM_FIELD).Write('|')
		.Write(DIFFS_CMP_MTHD_CAT_CD_FIELD).Write('|').Write(DIFFS_CMP_MTHD_CAT_NUM_FIELD).Write('|') 
		.Write(DIFFS_CMP_MTHD_CD_FIELD).Write('|').Write(DIFFS_CMP_MTHD_NUM_FIELD).Write('|').Write(DETAIL_TYPE_FIELD).Write('|')
		.Write(DETAIL_TYPE_NUM_FIELD).Write('|').Write(DETAIL_CHG_TYPE_FIELD).Write('|').Write(DETAIL_CHG_TYPE_NUM_FIELD).Write('|')
		.Write(DETAIL_SUB_CHG_TYPE_FIELD).Write('|').Write(DETAIL_SUB_CHG_TYPE_NUM_FIELD).Write('|').Write(DETAIL_CHAIN_PATH_SZ_FIELD).Write('|')
		.Write(DETAIL_PATH_NUM_FIELD).Write('|').Write(VALUE_TYPE_FLG_FIELD).Write('|').Write(FROM_TEXT_VALUE_FIELD).Write('|')
		.Write(TO_TEXT_VALUE_FIELD).Write('|').Write(FROM_INTEGER_VALUE_FIELD).Write('|').Write(TO_INTEGER_VALUE_FIELD).Write('|')
		.Write(FROM_DECIMAL_VALUE_FIELD).Write('|').Write(TO_DECIMAL_VALUE_FIELD).Write('|').Write(OTHER_DATA_FIELD).Write('|').Write(OTHER_DATA_VALUE_TYPE_FLG_FIELD/*since 2017-04-26*/).Write('|')
		.Write(DESCRIPTION_FIELD).Write('|').Write(IS_NEW_INDEX_FLG_FIELD).Write('|').Write(SUMMARY_CAT_CODE_FIELD).Write('|')
		.Write(SUMMARY_CAT_NUM_FIELD).Write('|').Write(DIFFS_TABLE_NUM_FIELD).Write('|').Write(SOURCE_FILE_FIELD).Write('|').Write(SOURCE_FILE_NUM_FIELD).Write('|') 
		.Write(DM_CHG_COMPARISON_FIELD).Write('|').Write(TAG_FIELD).Write('|').Write(CREATED_DT_FIELD).Write('\n')
		;		
	}
	
	static final String NUMBER_OF_DETAILS_FIELD = CString.valueOf(new byte[]{'N', 'U', 'M', 'B', 'E', 'R', '_', 'O', 'F', '_', 'D', 'E', 'T', 'A', 'I', 'L', 'S'}, true, true);
	static final String OBJECT_CHG_NUM_FIELD = CString.valueOf(new byte[]{'O', 'B', 'J', 'E', 'C', 'T', '_', 'C', 'H', 'G', '_', 'N', 'U', 'M'}, true, true);
	
	static void writeDiffsTblObjectChangeHeader(final BufferedTextOutputStream utf8BufferedOS) {
		//OBJECT_CHG_NUM|DIFFS_TABLE_ROW_NUM|DIFFS_CHG_DETAIL_LVL_INDIC|OWNER|
		//OBJECT_NUM|OBJECT_NAME|PRODUCT_NUM|PRODUCT_CODE|
		//SUMMARY_CAT_NUM|SUMMARY_CAT_CODE|DIFFS_TABLE_NUM|DIFFS_CMP_MTHD_NUM|DIFFS_CMP_MTHD_CD|
		//DIFFS_CMP_MTHD_CAT_NUM|DIFFS_CMP_MTHD_CAT_CD|SOURCE_FILE|SOURCE_FILE_NUM|
		//DM_CHG_COMPARISON|NUMBER_OF_DETAILS|TAG|CREATED_DT
		
		utf8BufferedOS.Write(OBJECT_CHG_NUM_FIELD).Write('|').Write(DIFFS_TABLE_ROW_NUM_FIELD).Write('|')
		.Write(DIFFS_CHG_DETAIL_LVL_INDIC_FIELD).Write('|').Write(OWNER_FIELD).Write('|')
		.Write(OBJECT_NUM_FIELD).Write('|').Write(OBJECT_NAME_FIELD).Write('|').Write(PRODUCT_NUM_FIELD).Write('|').Write(PRODUCT_CODE_FIELD).Write('|')
		.Write(SUMMARY_CAT_NUM_FIELD).Write('|').Write(SUMMARY_CAT_CODE_FIELD).Write('|').Write(DIFFS_TABLE_NUM_FIELD).Write('|')
		.Write(DIFFS_CMP_MTHD_NUM_FIELD).Write('|').Write(DIFFS_CMP_MTHD_CD_FIELD).Write('|')
		.Write(DIFFS_CMP_MTHD_CAT_NUM_FIELD).Write('|').Write(DIFFS_CMP_MTHD_CAT_CD_FIELD).Write('|')
		.Write(SOURCE_FILE_FIELD).Write('|').Write(SOURCE_FILE_NUM_FIELD).Write('|')
		.Write(DM_CHG_COMPARISON_FIELD).Write('|').Write(NUMBER_OF_DETAILS_FIELD).Write('|')
		.Write(TAG_FIELD).Write('|').Write(CREATED_DT_FIELD).Write('\n')
		;
	}
	
	private static void writeDiffsTableRow__(SingleChgDiffsTableRow diffsTblRow, eBSProduct product, DiffsTable parentDiffsTable, 
									String sourceFile, int sourceFileNum, 
									final String dmDiffsComparison, final String tag, 
									final DMCompEnumSets enumSets, 
									final boolean advcdTblRowWithNoDetails, 
									final BufferedTextOutputStream objChgUtf8BufferedOS, 
									final BufferedTextOutputStream utf8BufferedOS, 
									string.CharBuffer tempBuffer, 
									String[] checkTextRsltHolder) {
		DMObject object = new DMObject(parentDiffsTable.summaryCategory, diffsTblRow.objectName, product); 
		DMObject existing = enumSets.dmObjects.addIfNotPresent(object);
		if (existing != null) {
			object = existing;
		}
		int objNum = object.getOutputNumber();
		ChangeType chgType = enumSets.getChangeTypeByID(diffsTblRow.change/*nameOrCode*/); 
		java.lang.String nowDt = get_nowDtm();
		int diffsTblRowNum = diffsTblRow.getOutputNumber();
		int objChgNum = DiffsTableRowChange.nextSequenceValue();
		int detailLvlIndic = (advcdTblRowWithNoDetails ? 2 : 1);
		
		DiffsCompMethod diffsCompMethod = parentDiffsTable.diffsCompMethod == null || parentDiffsTable.diffsCompMethod.isEmpty() ? DiffsCompMethod.NO_VALUE : enumSets.getDiffsCompMethodByID(parentDiffsTable.diffsCompMethod);
		String diffsCompCategoryCode = diffsCompMethod == null ? DiffsCompMethodID.NO_VALUE_CODE : DiffsCompCategory.get_DiffsCompCategory(diffsCompMethod.code.diffsCompCategory).getShortCode()/*code*/;
		
		utf8BufferedOS.Write(DiffsDetailedChange.nextSequenceValue()/*DM_CMP_DETAIL_NUM*/).Write('|').Write(diffsTblRowNum/*DIFFS_TABLE_ROW_NUM*/)
		.Write('|').Write(detailLvlIndic/*DIFFS_CHG_DETAIL_LVL_INDIC_FIELD*/).Write('|')
		.Write(objChgNum/*DIFFS_CHG_NUM*/).Write('|').Write(object.owner).Write('|').Write(__N_A__/*DETAIL_NAME*/).Write('|')
		.Write(String.EMPTY/*QUALIFIER*/).Write('|').Write(object.name).Write('|').Write(objNum).Write('|')
		.Write(String.EMPTY/*SUBJECT_NAME*/).Write('|').Write(String.EMPTY/*SUBJECT_TYPE*/).Write('|').Write(String.EMPTY/*SUBJECT_TYPE_NUM*/).Write('|')
		.Write(String.EMPTY/*SUBJECT_QUAL*/).Write('|').Write(String.EMPTY/*SUBJECT_NUM*/).Write('|').Write(product.code/*PRODUCT_CODE*/).Write('|').Write(product.number/*PRODUCT_NUM*/).Write('|')
		.Write(diffsCompCategoryCode/*DIFFS_CMP_MTHD_CAT_CD*/).Write('|').Write(diffsCompMethod.code.diffsCompCategory/*DIFFS_CMP_MTHD_CAT_NUM*/).Write('|')
		.Write(diffsCompMethod.getShortCode()/*code*//*DIFFS_CMP_MTHD_CD*/).Write('|').Write(diffsCompMethod.code.number/*DIFFS_CMP_MTHD_NUM*/).Write('|').Write(DMCompDetailChangedType.N_A.code/*DETAIL_TYPE*/).Write('|')
		.Write(DMCompDetailChangedType.N_A.number/*DETAIL_TYPE_NUM*/).Write('|').Write(chgType.getShortCode()/*code*//*DETAIL_CHG_TYPE*/).Write('|').Write(chgType.code.number/*DETAIL_CHG_TYPE_NUM*/).Write('|')
		.Write(String.EMPTY/*DETAIL_SUB_CHG_TYPE*/).Write('|').Write(String.EMPTY/*DETAIL_SUB_CHG_TYPE_NUM*/).Write('|').Write(0/*DETAIL_CHAIN_PATH_SZ*/).Write('|')
		.Write(0/*DETAIL_PATH_NUM*/).Write('|').Write(String.EMPTY/*/*VALUE_TYPE_FLG*/).Write('|').Write(String.EMPTY/*FROM_TEXT_VALUE*/).Write('|')
		.Write(String.EMPTY/*TO_TEXT_VALUE*/).Write('|').Write(String.EMPTY/*FROM_INTEGER_VALUE*/).Write('|').Write(String.EMPTY/*TO_INTEGER_VALUE*/).Write('|')
		.Write(String.EMPTY/*FROM_DECIMAL_VALUE*/).Write('|').Write(String.EMPTY/*TO_DECIMAL_VALUE*/).Write('|').Write(String.EMPTY/*OTHER_DATA*/).Write('|').Write(String.EMPTY/*OTHER_DATA_VALUE_TYPE_FLG*/).Write('|')
		.Write(String.EMPTY/*DESCRIPTION*/).Write('|').Write('N'/*IS_NEW_INDEX_FLG_FIELD*/).Write('|').Write(parentDiffsTable.getSummaryCategoryShortCode()/*SUMMARY_CAT_CODE*/).Write('|')
		.Write(parentDiffsTable.summaryCategory/*SUMMARY_CAT_NUM*/).Write('|').Write(parentDiffsTable.getOutputNumber()/*DIFFS_TABLE_NUM*/).Write('|').Write(sourceFile/*SOURCE_FILE*/).Write('|').Write(sourceFileNum).Write('|')
		.Write(dmDiffsComparison).Write('|').Write(tag).Write('|').Write(nowDt).Write('\n')
		;		
		
		objChgUtf8BufferedOS.Write(objChgNum/*OBJECT_CHG_NUM*/).Write('|').Write(diffsTblRowNum/*DIFFS_TABLE_ROW_NUM*/).Write('|')
		.Write(detailLvlIndic/*DIFFS_CHG_DETAIL_LVL_INDIC*/).Write('|').Write(object.owner/*OWNER*/).Write('|')
		.Write(object.number/*OBJECT_NUM*/).Write('|').Write(object.name/*OBJECT_NAME*/).Write('|').Write(product.number/*PRODUCT_NUM*/).Write('|').Write(product.code/*PRODUCT_CODE*/).Write('|')
		.Write(parentDiffsTable.summaryCategory/*SUMMARY_CAT_NUM*/).Write('|').Write(parentDiffsTable.getSummaryCategoryShortCode()/*SUMMARY_CAT_CODE*/).Write('|')
		.Write(parentDiffsTable.getOutputNumber()/*DIFFS_TABLE_NUM*/).Write('|').Write(diffsCompMethod.code.number/*DIFFS_CMP_MTHD_NUM*/).Write('|').Write(diffsCompMethod.getShortCode()/*code*//*DIFFS_CMP_MTHD_CD*/).Write('|')
		.Write(diffsCompMethod.code.diffsCompCategory/*DIFFS_CMP_MTHD_CAT_NUM*/).Write('|').Write(diffsCompCategoryCode/*DIFFS_CMP_MTHD_CAT_CD*/).Write('|')
		.Write(sourceFile/*SOURCE_FILE*/).Write('|').Write(sourceFileNum/*SOURCE_FILE_NUM*/).Write('|')
		.Write(dmDiffsComparison/*DM_CHG_COMPARISON*/).Write('|').Write(0/*NUMBER_OF_DETAILS*/).Write('|')
		.Write(tag/*TAG*/).Write('|').Write(nowDt/*CREATED_DT*/).Write('\n')
		;
	}
	static void writeDiffsTableRow(SingleChgDiffsTableRow diffsTblRow, eBSProduct product, DiffsTable parentDiffsTable, 
									String sourceFile, int sourceFileNum, 
									final String dmDiffsComparison, final String tag, 
									final DMCompEnumSets enumSets, 
									final BufferedTextOutputStream objChgUtf8BufferedOS, 
									final BufferedTextOutputStream utf8BufferedOS, 
									string.CharBuffer tempBuffer, 
									String[] checkTextRsltHolder) {
		writeDiffsTableRow__(diffsTblRow, product, parentDiffsTable, 
									sourceFile, sourceFileNum, 
									dmDiffsComparison, tag, 
									enumSets, 
									false/*advcdTblRowWithNoDetails*/, 
									objChgUtf8BufferedOS, 
									utf8BufferedOS, 
									tempBuffer, 
									checkTextRsltHolder);
	}
	
	static void writeDiffsTableObjectChange(final ChangeType chgType, final int detailLvlIndic, 
					final int diffsRowNumber, final int objChgNum, 
					final DiffsDetailedChange[] detailedChgs, 
					final int detailedChgsCount, 
					final DMObject object, 
					eBSProduct product, DiffsTable parentDiffsTable, 
					String sourceFile, int sourceFileNum, 
					final String dmDiffsComparison, final String tag, 
					final DMCompEnumSets enumSets, 
					final BufferedTextOutputStream objChgUtf8BufferedOS, 
					final BufferedTextOutputStream utf8BufferedOS, 
					string.CharBuffer tempBuffer, 
					String[] checkTextRsltHolder) {
		//boolean f = false;
		//if (f == false) return ;
		int objNum = object.getOutputNumber();
		DMCompDetailChangedType detailChgdType = DMCompDetailChangedType.NO_VALUE;
		
		DiffsCompMethod diffsCompMethod = parentDiffsTable.diffsCompMethod == null || parentDiffsTable.diffsCompMethod.isEmpty() ? DiffsCompMethod.NO_VALUE : enumSets.getDiffsCompMethodByID(parentDiffsTable.diffsCompMethod);
		String diffsCompCategoryCode = diffsCompMethod == null ? DiffsCompMethodID.NO_VALUE_CODE : DiffsCompCategory.get_DiffsCompCategory(diffsCompMethod.code.diffsCompCategory).getShortCode()/*code*/;
		
		//string.CharBuffer tempBuffer = null;
		if (tempBuffer == null) {
			tempBuffer = new string.CharBuffer(32);
		}
		if (checkTextRsltHolder == null || checkTextRsltHolder.length < 3) {
			checkTextRsltHolder = new String[3];
		}
		byte checkTextRslt = -1;
		DMCompDetailPath detailPath = DMCompDetailPath.EMPTY, existingDetailPath = null; 
		String detailName = String.EMPTY;
		byte/*boolean*/ isFromToChange = no; //false;
		String qualifier = String.EMPTY;
		String subjectName = String.EMPTY;
		String subjectQualifier = String.EMPTY;
		DMCompDetailChangedType subjectDetailChgdType = DMCompDetailChangedType.NO_VALUE;
		DMObject subject = null, existing = null;
		String subjectNumAsStr = null;
		
		//ICharSequence chgSubTypeNum = null;
		java.lang.String nowDt = get_nowDtm();
		
		int i=0;
		main_loop: 
		for (;i<detailedChgsCount;i++)
		{
			
			DiffsDetailedChange detailedChg = /*diffsTblRow.*/detailedChgs[i];
			detailPath = DMCompDetailPath.EMPTY; 
			checkTextRslt = detailedChg.checkText(checkTextRsltHolder, tempBuffer);
			detailName = String.EMPTY;
			detailChgdType = checkTextRslt < 2 ? DMCompDetailChangedType.NO_VALUE : DMCompDetailChangedType.text;
			ChangeType chgSubType = detailedChg.checkIfEndsWithChangeType(enumSets.changeTypes/*allChangeTypes*/);
			short chkRslt = chgSubType == null ? detailedChg.checkIfFromToChange(enumSets.detailChangedTypes/*allDdtailChangedTypes*/) : NOT_A_FROM_TO_CHANGE_FROM_VAL;
			isFromToChange = no;
			qualifier = null;
			if (chgSubType == null) { //case the detailed change spec does not end with a sub-change-type
//				chgSubTypeNum = String.EMPTY;
				chgSubType = ChangeType.NO_VALUE;
				if (checkTextRslt < 2) { //case the detail change is not of type text
					short ret = detailedChg.checkIfFromToChange(enumSets.detailChangedTypes/*allDdtailChangedTypes*/);
					int pathEnd = detailedChg.size();
					__switch_from_to_chg_chk_block___:
					switch(ret)
					{
					case NOT_A_FROM_TO_CHANGE_FROM_VAL: 
						boolean lessThanTwoSpecItems = pathEnd/*size*/ < 2;
						if (lessThanTwoSpecItems) {
							pathEnd = 0;
							if (pathEnd/*size*/ > 0) {
								detailName = detailedChg.getCleansedFirstSpecItem();
							}
						}
						else {
							pathEnd/*size*/ -= 2;
							detailPath = detailedChg.getDetailPath(pathEnd, true/*failIfNewDetailTypeAndNullSet*/, enumSets.detailChangedTypes/*allDetailChangedTypes*/, tempBuffer);
							existingDetailPath = enumSets.dmCompDetailPaths.addIfNotPresent(detailPath);
							if (existingDetailPath != null) {
								detailPath = existingDetailPath;
							}
							String detailChgdTypeName = detailedChg.getCleansedSpecItem(pathEnd);
							if (detailChgdTypeName.isEmpty()) {
								detailChgdType = DMCompDetailChangedType.NO_VALUE;
							}
							else {
								detailChgdType = enumSets.getDetailChangedTypeByID(detailChgdTypeName/*codeOrName*/);
							}
							detailName = detailedChg.getCleansedLastSpecItem();
						}
						break ;
					case NO_DTL_TYPE_MATCH_FOR_FROM_TO_CHANGE:
						pathEnd -= 3;
						String detailChgdTypeName = detailedChg.getCleansedSpecItem(pathEnd);
						if (detailChgdTypeName.isEmpty()) {
							detailChgdType = DMCompDetailChangedType.NO_VALUE;
						}
						else {
							detailChgdType = enumSets.getDetailChangedTypeByID(detailChgdTypeName/*codeOrName*/);
						}
						detailName = detailChgdType.name;
						isFromToChange = yes;
						break ;
					case NO_SPEC_ITEM_FOR_DTL_OR_NO_CHG_TYPES_TO_MATCH: //NOTE: as there is a non null set object for the match this is more NO_SPEC_ITEM_FOR_DTL
						detailChgdType = DMCompDetailChangedType.NO_VALUE;
						detailName = String.EMPTY;
						isFromToChange = yes;
						pathEnd = 0;
						break ;
					case WEAK_FROM_TO_CHANGE_FROM_VAL: 
						if (pathEnd/*size*/ < 3) { //case weak and the number of spec items is 2
							detailChgdType = DMCompDetailChangedType.NO_VALUE;
							pathEnd = 0;
							detailName = String.EMPTY;
						}
						else { //case weak and detail changed type unresolved
							pathEnd -= 3;
							detailChgdTypeName = detailedChg.getCleansedSpecItem(pathEnd);
							if (detailChgdTypeName.isEmpty()) {
								detailChgdType = DMCompDetailChangedType.NO_VALUE;
							}
							else {
								detailChgdType = enumSets.getDetailChangedTypeByID(detailChgdTypeName/*codeOrName*/);
							}
							detailName = detailChgdType.name;
						}
						isFromToChange = maybe; //maybe to distinguish weak case from the others
						break ;
					default:  
						if (ret < 0) { //case weak and detail changed type is resolved
							isFromToChange = maybe;
							ret *= -1;
						}
						else {
							isFromToChange = yes;
						}
						detailChgdType = enumSets.detailChangedTypes.getByNumber(ret/*detailChangedTypeNumber*/); 
						if (detailChgdType == null) {
							//NOTE: SHOULD NEVER GET HERE...
							throw new IllegalStateException(
							"DMCompOutputUtil::writeDiffsTableRow-1: the system is getting inconsistent and crazDMCompOutputUtil"
							);
						}
						detailName = detailChgdType.name; //BUG-FIX - 2017-05-04 - was missing, causing the detail name to be null and hence the integration into the db to fail with null DETAIL_NAME error
						pathEnd -= 3; //BUG-FIX - 2017-05-03 - was missing
					} //end __switch_from_to_chg_chk_block___
					if (pathEnd > 0) {
						detailPath = detailedChg.getDetailPath(pathEnd, true/*failIfNewDetailTypeAndNullSet*/, enumSets.detailChangedTypes/*allDetailChangedTypes*/, tempBuffer);
						existingDetailPath = enumSets.dmCompDetailPaths.addIfNotPresent(detailPath);
						if (existingDetailPath != null) {
							detailPath = existingDetailPath;
						}
					}
					else {
						detailPath = DMCompDetailPath.EMPTY; 
					}
				}  //end case the detail change is not of type text
				else {
					detailPath = DMCompDetailPath.EMPTY; 
					detailChgdType = DMCompDetailChangedType.text; //DMCompDetailChangedType.NO_VALUE;
					detailName = detailChgdType.name; //String.EMPTY;
				}
			}  //end case the detailed change spec does not end with a sub-change-type
			else {
//				if (tempBuffer == null) {
//					tempBuffer = new string.CharBuffer(10);
//				}
//				else {
//					tempBuffer.resetLimitOnly();
//				}
//				tempBuffer.appendInteger(chgSubType.code.number);
//				chgSubTypeNum = tempBuffer.ToString(true/*createAsciiStringIfPossible*/);
				if (checkTextRslt < 2) { //case the detailed change spec does not start with text keyword 
					int pathEnd = detailedChg.size() - 3;
					if (pathEnd > 0) {
						String specItem = detailedChg.getCleansedSpecItem(detailedChg.size() - 2);
						boolean multiSpecItemQualifiedName = specItem.equals(")");
						if (multiSpecItemQualifiedName) {
							pathEnd -= 2;
							if (pathEnd > -1) {
								if (pathEnd > 0){
									detailPath = detailedChg.getDetailPath(pathEnd, true/*failIfNewDetailTypeAndNullSet*/, enumSets.detailChangedTypes/*allDetailChangedTypes*/, tempBuffer);
									existingDetailPath = enumSets.dmCompDetailPaths.addIfNotPresent(detailPath);
									if (existingDetailPath != null) {
										detailPath = existingDetailPath;
									}
									////System.out.println("DMCompOutputUtil::::::::::::::::::::::::::: pathEnd: " + pathEnd + ", detailPath: \r\n" + detailPath);
								}
								else {
									detailPath = DMCompDetailPath.EMPTY; 
								}
//								int qualifierSpecItemIndex = detailedChg.size() - 3;
//								//tempBuffer.resetLimitOnly();
//								//tempBuffer.append(detailedChg.getCleansedSpecItem(qualifierSpecItemIndex - 1)).append(detailedChg.getCleansedSpecItem(qualifierSpecItemIndex)).appendChar(')');
//								qualifier = detailedChg.getCleansedSpecItem(qualifierSpecItemIndex);
//								detailName = detailedChg.getCleansedSpecItem(qualifierSpecItemIndex - 1);
//								if (!detailName.isEmpty() && detailName.getLastChar() == '(') {
//									detailName = detailName.left(detailName.length() - 1);
//								}
							}
							else {
								detailPath = DMCompDetailPath.EMPTY; 
							}
						} //end if (multiSpecItemQualifiedName)
						else {
							detailPath = detailedChg.getDetailPath(pathEnd, true/*failIfNewDetailTypeAndNullSet*/, enumSets.detailChangedTypes/*allDetailChangedTypes*/, tempBuffer);
							////System.out.println("DMCompOutputUtil - ::  size: " + enumSets.dmCompDetailPaths.size() + ", detailPath: \r\n" + detailPath);
							existingDetailPath = enumSets.dmCompDetailPaths.addIfNotPresent(detailPath);
							if (existingDetailPath != null) {
								detailPath = existingDetailPath;
							}
							////System.out.println("DMCompOutputUtil - ::  size: " + enumSets.dmCompDetailPaths.size() + ", detailPath: \r\n" + detailPath); 
							detailName = detailedChg.getCleansedSpecItem(pathEnd + 1);
						}
						String detailChgdTypeName = detailedChg.getCleansedSpecItem(pathEnd);
						if (detailChgdTypeName.isEmpty()) {
							detailChgdType = DMCompDetailChangedType.NO_VALUE;
						}
						else {
							detailChgdType = enumSets.getDetailChangedTypeByID(detailChgdTypeName/*codeOrName*/);
						}
					}
					else if (pathEnd == 3) {
						detailPath = DMCompDetailPath.EMPTY; 
						String detailChgdTypeName = detailedChg.getCleansedSpecItem(0);
						if (detailChgdTypeName.isEmpty()) {
							detailChgdType = DMCompDetailChangedType.NO_VALUE;
						}
						else {
							detailChgdType = enumSets.getDetailChangedTypeByID(detailChgdTypeName/*codeOrName*/);
						}
						detailName = detailedChg.getCleansedSpecItem(1);
					}
					else {
						detailPath = DMCompDetailPath.EMPTY; 
						pathEnd += 2;
						if (pathEnd == 0) {
							detailChgdType = DMCompDetailChangedType.NO_VALUE;
							detailName = String.EMPTY;
						}
						else {
							String detailChgdTypeName = detailedChg.getCleansedSpecItem(0);
							if (detailChgdTypeName.isEmpty()) {
								detailChgdType = DMCompDetailChangedType.NO_VALUE;
							}
							else {
								detailChgdType = enumSets.getDetailChangedTypeByID(detailChgdTypeName/*codeOrName*/);
							}
							detailName = detailChgdType.name;
						}
					}
				}
				else {
					detailChgdType = DMCompDetailChangedType.text;
					detailName = detailChgdType.name;
				}
			}
			if (qualifier == null) {
				if (!detailName.isEmpty() && detailName.getLastChar() == ')') {
					////System.out.println("DMCompOutputUti:::::::::::::::::::::: - detailName: " + detailName);
					int j = detailName.length() - 2;
					for (;j>-1;j--)
					{
						if (detailName.getChar(j) == '(') break;
					}
					if (j < 0) { //case bad data ==> do as if there were no qualifier!!!
						qualifier = String.EMPTY;
					}
					else {
						qualifier = detailName.substring(j+ 1, detailName.length() - 1);
						detailName = detailName.left(j);
					}
				}
				else {
					qualifier = String.EMPTY;
				}
			}
			subject = null;
			subjectName = String.EMPTY;
			subjectQualifier = String.EMPTY;
			subjectDetailChgdType = DMCompDetailChangedType.NO_VALUE;
			if (detailPath.size() > 1) {
				int nodeOffset = detailPath.nodeOffset(0);
				//parentDiffsTable.summaryCategory != DMCompSummaryCategories.Packages ? 
				subjectDetailChgdType = DMCompDetailChangedType.checkIfDBPhysicalTopObjectType(detailPath.getType(nodeOffset)); 
				boolean isDBPhysicalTopObjectType = subjectDetailChgdType != null;
				if (!isDBPhysicalTopObjectType/*subjectDetailChgdType == null*/) {
					subjectDetailChgdType = enumSets.getDetailChangedTypeByID(detailPath.getType(nodeOffset)/*codeOrName*/);
				}
				byte childObjectType = isDBPhysicalTopObjectType || parentDiffsTable.summaryCategory != DMCompSummaryCategories.Packages ? (byte)-1 : DMChildObjectTypes.getChildObjectTypeNumber(subjectDetailChgdType.code);
				if (childObjectType > (byte)0 || isDBPhysicalTopObjectType/*subjectDetailChgdType.isDBPhysicalTopObjectType()*/) {
					subjectName = detailPath.getName(nodeOffset);
					//int subjectNameLen = subjectName.length();
					/*int chkRslt*/nodeOffset = DMCompDetailPath.checkIfQualifiedName(subjectName);
					if (nodeOffset/*chkRslt*/ > -1) { //case there's a qualifier in the name
						subjectQualifier = subjectName.substring(nodeOffset/*chkRslt*//*indexOfOpeningPrenthseis*/ + 1, subjectName.length() - 1); //BUG-FIX - 2017-05-04 - was using chkRslt as holder of indexOfOpeningPrenthseis in lieu of nodeOffset/*chkRslt*/
						//subjectNameLen = chkRslt/*indexOfOpeningPrenthseis*/ - 1;
						subjectName = subjectName.left((nodeOffset/*chkRslt*//*indexOfOpeningPrenthseis*/)/*subjectNameLen*/);
					} //end case there's a qualifier in the name
					else {
						if (nodeOffset/*chkRslt*/ == 0x80000000) { //case there's the closing parenthesis but no opening parenthesis
							throw new WriteToStreamException(
							"DMCompOutputUtil::writeDiffsTableRow-2: malformed qualified node name (name='" + subjectName + "')"
							);
						}
					}
					if (isDBPhysicalTopObjectType) {
						subject = new DMObject(parentDiffsTable.summaryCategory, subjectName, object.owner, product/*ownerProduct*/);
					}
					else {
						subject = object.newChildObject(subjectName, childObjectType, subjectQualifier);
					}
					existing = enumSets.dmObjects.addIfNotPresent(subject);
					if (existing != null) { //case the subject was already existing
						subject = existing;
						subjectName = subject.name;
					}
				}
				else {
					subjectDetailChgdType = DMCompDetailChangedType.NO_VALUE;
				}
			}
			if (subject != null) {
				tempBuffer.resetLimitOnly();
				tempBuffer.appendInteger(subject.getOutputNumber());
				subjectNumAsStr = tempBuffer.ToString(true/*advcdTblRowWithNoDetails*/);
			}
			else {
				subjectNumAsStr = String.EMPTY;
			}
			String fromTextVal = String.EMPTY;
			String toTextVal = String.EMPTY;
			String fromIntegerVal = String.EMPTY;
			String toIntegerVal = String.EMPTY;
			String fromDecimalVal = String.EMPTY;
			String toDecimalVal = String.EMPTY;
			String otherData = String.EMPTY;
			String valueTypeFlag = __Z__; //String.EMPTY;
			String otherDataValueTypeFlag = __Z__; //String.EMPTY;
			if (isFromToChange != no) {
				//BUG-FIX-2017-06-21 - was using variable which serves to iterate over the detailed changes, as index against the spec items of the defailted changed as well ==> INFINITE loop for AR, for example
				int j = detailedChg.size() - 2; //changed from using variable i as the latter can not be used to iterate over distinct sets
				fromTextVal = detailedChg.getCleansedSpecItem(j, true/*dropFromToOperatorSymbolIfAny*/);
				toTextVal = detailedChg.getCleansedSpecItem(++j);
				valueTypeFlag = workoutValueTypeFlag(detailChgdType.valueType);
				if (valueTypeFlag.getFirstChar() == 'D') {
					valueTypeFlag = confirmDecimalValueTypeFlag(fromTextVal, toTextVal);
				}
				switch(valueTypeFlag.getFirstChar())
				{
				case 'I':
					fromIntegerVal = fromTextVal;
					toIntegerVal = toTextVal;
					fromTextVal = String.EMPTY;
					toTextVal = String.EMPTY;
					break ;
				case 'D':
					fromDecimalVal = fromTextVal;
					toDecimalVal = toTextVal;
					fromTextVal = String.EMPTY;
					toTextVal = String.EMPTY;
					break ;
				}
			}
			else if (detailChgdType == DMCompDetailChangedType.text) { //case the detail is of type text
				valueTypeFlag = __T__;
				if (checkTextRslt == (short)3) {
					otherDataValueTypeFlag = __U__;
					otherData = checkTextRsltHolder[1];
					toTextVal = checkTextRsltHolder[2];
				}
				else if (core.html.SharedStringConstants.href.equals(checkTextRsltHolder[0])) {
					otherDataValueTypeFlag = __U__;
					otherData = checkTextRsltHolder[1];
					//toTextVal = String.EMPTY; //NOTE: already equal to empty string!!!
				}
				else {
					toTextVal = checkTextRsltHolder[1];
//					System.out.println("__FROM_TO_OP_SYMBOL__: '" + __FROM_TO_OP_SYMBOL__ + "', __FROM_TO_OP_SYMBOL__.getChar(3): " + __FROM_TO_OP_SYMBOL__.getChar(3));
					int index = toTextVal.indexOf(__FROM_TO_OP_SYMBOL__);
					if (index > -1) {
						fromTextVal = toTextVal.left(index);
						toTextVal = toTextVal.substring(index + __FROM_TO_OP_SYMBOL__.length(), toTextVal.length());
					}
				}
				if (__Click_here_for_complete_changes__.equals(toTextVal)) {
					if (!otherData.isEmpty()) {
						DMSource dmSource = fromSourceUrl(otherData, true/*forSureSecondarySource*/);
						dmSource.product = product;
						DMSource existingDmSource = enumSets.secondarySources.addIfNotPresent(dmSource);
						if (existingDmSource != null) {
							dmSource = existingDmSource;
							existingDmSource = null;
						}
						tempBuffer.resetLimitOnly();
						tempBuffer.append(dmSource.getOutputNumber()).appendChars(otherData, dmSource.ID.length(), otherData.length());
						otherData = tempBuffer.ToString(true/*createAsciiStringIfPossible*/);
					}
					toTextVal = __Click_here_for_complete_changes_SHORTHAND;
				}
			}
			else if (chgSubType != null) {
				//detailType, detailName and detail path gie the complete view of the detail ==> DO NOTHING HERE!
			}
			else {
				valueTypeFlag = workoutValueTypeFlag(detailChgdType.valueType);
			}
			
			
			tempBuffer.resetLimitOnly();
			if (subjectDetailChgdType.number != 0) {
				tempBuffer.appendInteger(subjectDetailChgdType.number);
			}
			
//			if (object.name.equals("AR_GTA_TRX_UTIL")) {
//				System.out.println("DMCompOutputUti:::::::::::::::::::::: HERE IT IS!!!!! - detailName: '" + detailName + ", i: " + i + ", detailedChgsCount: " + detailedChgsCount + "', detailPath: " + detailPath);
//			}
			
			utf8BufferedOS.Write(detailedChg.getOutputNumber()/*DM_CMP_DETAIL_NUM*/).Write('|')
			.Write(diffsRowNumber/*DIFFS_TABLE_ROW_NUM*/).Write('|').Write(detailLvlIndic/*DIFFS_CHG_DETAIL_LVL_INDIC_FIELD*/).Write('|')
			.Write(objChgNum/*DIFFS_CHG_NUM*/).Write('|').Write(object.owner).Write('|').Write(detailName/*DETAIL_NAME*/).Write('|')
			.Write(qualifier/*QUALIFIER*/).Write('|').Write(object.name/*OBJECT_NAME*/).Write('|').Write(objNum/*OBJECT_NUM*/).Write('|')
			.Write(subjectName/*SUBJECT_NAME*/).Write('|').Write(subjectDetailChgdType.getShortCode()/*code*//*SUBJECT_TYPE*/).Write('|').Write(tempBuffer/*SUBJECT_TYPE_NUM*/).Write('|')
			.Write(subjectQualifier/*SUBJECT_QUAL*/).Write('|').Write(subjectNumAsStr/*SUBJECT_NUM*/).Write('|').Write(product.code/*PRODUCT_CODE*/).Write('|')
			.Write(product.number/*PRODUCT_NUM*/).Write('|').Write(diffsCompCategoryCode/*DIFFS_CMP_MTHD_CAT_CD*/).Write('|')
			.Write(diffsCompMethod.code.diffsCompCategory/*DIFFS_CMP_MTHD_CAT_NUM*/).Write('|').Write(diffsCompMethod.getShortCode()/*code*//*DIFFS_CMP_MTHD_CD*/).Write('|')
			.Write(diffsCompMethod.code.number/*DIFFS_CMP_MTHD_NUM*/).Write('|').Write(detailChgdType.getShortCode()/*code*//*DETAIL_TYPE*/).Write('|')
			.Write(detailChgdType.number/*DETAIL_TYPE_NUM*/).Write('|').Write(chgType.getShortCode()/*code*//*DETAIL_CHG_TYPE*/).Write('|').Write(chgType.code.number/*DETAIL_CHG_TYPE_NUM*/).Write('|')
			.Write(chgSubType.getShortCode()/*code*//*DETAIL_SUB_CHG_TYPE*/).Write('|').Write(chgSubType.code.number/*DETAIL_SUB_CHG_TYPE_NUM*/).Write('|')
			.Write(detailPath.size()/*DETAIL_CHAIN_PATH_SZ*/).Write('|').Write(detailPath.getOutputNumber()/*DETAIL_PATH_NUM*/).Write('|')
			.Write(valueTypeFlag/*/*VALUE_TYPE_FLG*/).Write('|').Write(fromTextVal/*FROM_TEXT_VALUE*/).Write('|')
			.Write(toTextVal/*TO_TEXT_VALUE*/).Write('|').Write(fromIntegerVal/*FROM_INTEGER_VALUE*/).Write('|').Write(toIntegerVal/*TO_INTEGER_VALUE*/).Write('|')
			.Write(fromDecimalVal/*FROM_DECIMAL_VALUE*/).Write('|').Write(toDecimalVal/*TO_DECIMAL_VALUE*/).Write('|').Write(otherData/*OTHER_DATA*/).Write('|')
			.Write(otherDataValueTypeFlag/*OTHER_DATA_VALUE_TYPE_FLG*/).Write('|').Write(String.EMPTY/*DESCRIPTION*/).Write('|').Write('N'/*IS_NEW_INDEX_FLG_FIELD*/).Write('|')
			.Write(parentDiffsTable.getSummaryCategoryShortCode()/*SUMMARY_CAT_CODE*/).Write('|').Write(parentDiffsTable.summaryCategory/*SUMMARY_CAT_NUM*/).Write('|')
			.Write(parentDiffsTable.getOutputNumber()/*DIFFS_TABLE_NUM*/).Write('|').Write(sourceFile/*SOURCE_FILE*/).Write('|').Write(sourceFileNum).Write('|')
			.Write(dmDiffsComparison).Write('|').Write(tag).Write('|').Write(nowDt).Write('\n')
			;			
		} //end main_loop
		
		
		objChgUtf8BufferedOS.Write(objChgNum/*OBJECT_CHG_NUM*/).Write('|').Write(diffsRowNumber/*DIFFS_TABLE_ROW_NUM*/).Write('|')
		.Write(detailLvlIndic/*DIFFS_CHG_DETAIL_LVL_INDIC*/).Write('|').Write(object.owner/*OWNER*/).Write('|')
		.Write(object.number/*OBJECT_NUM*/).Write('|').Write(object.name/*OBJECT_NAME*/).Write('|').Write(product.number/*PRODUCT_NUM*/).Write('|').Write(product.code/*PRODUCT_CODE*/).Write('|')
		.Write(parentDiffsTable.summaryCategory/*SUMMARY_CAT_NUM*/).Write('|').Write(parentDiffsTable.getSummaryCategoryShortCode()/*SUMMARY_CAT_CODE*/).Write('|')
		.Write(parentDiffsTable.getOutputNumber()/*DIFFS_TABLE_NUM*/).Write('|').Write(diffsCompMethod.code.number/*DIFFS_CMP_MTHD_NUM*/).Write('|').Write(diffsCompMethod.getShortCode()/*code*//*DIFFS_CMP_MTHD_CD*/).Write('|')
		.Write(diffsCompMethod.code.diffsCompCategory/*DIFFS_CMP_MTHD_CAT_NUM*/).Write('|').Write(diffsCompCategoryCode/*DIFFS_CMP_MTHD_CAT_CD*/).Write('|')
		.Write(sourceFile/*SOURCE_FILE*/).Write('|').Write(sourceFileNum/*SOURCE_FILE_NUM*/).Write('|')
		.Write(dmDiffsComparison/*DM_CHG_COMPARISON*/).Write('|').Write(detailedChgsCount/*NUMBER_OF_DETAILS*/).Write('|')
		.Write(tag/*TAG*/).Write('|').Write(nowDt/*CREATED_DT*/).Write('\n')
		;
	} 
	
	static void writeDiffsTableRow(final SingleChgDiffsTableRowAdvcd diffsTblRow, final eBSProduct product, 
									final DiffsTable parentDiffsTable, 
									final String sourceFile, final int sourceFileNum, 
									final String dmDiffsComparison, final String tag, 
									final DMCompEnumSets enumSets, 
									final BufferedTextOutputStream objChgUtf8BufferedOS, 
									final BufferedTextOutputStream utf8BufferedOS, 
									string.CharBuffer tempBuffer, 
									String[] checkTextRsltHolder) {
		if (!diffsTblRow.hasDetailedChanges()) {
			writeDiffsTableRow__(diffsTblRow, product, parentDiffsTable, 
									sourceFile, sourceFileNum, 
									dmDiffsComparison, tag, 
									enumSets, 
									true/*advcdTblRowWithNoDetails*/, 
									objChgUtf8BufferedOS, 
									utf8BufferedOS, 
									tempBuffer, 
									checkTextRsltHolder);
			return ;
		}
		DMObject object = new DMObject(parentDiffsTable.summaryCategory, diffsTblRow.objectName, product); 
		DMObject existing = enumSets.dmObjects.addIfNotPresent(object);
		if (existing != null) {
			object = existing;
		}
		ChangeType chgType = enumSets.getChangeTypeByID(diffsTblRow.change/*nameOrCode*/); 
		final int diffsRowNumber = diffsTblRow.getOutputNumber();
		
		final int objChgNum = DiffsTableRowChange.nextSequenceValue();
		final int detailLvlIndic = 3;
		final int detailedChgsCount = diffsTblRow.getNumberOfDetailedChanges();
		
		writeDiffsTableObjectChange(chgType, detailLvlIndic, 
					diffsRowNumber, objChgNum, 
					diffsTblRow.detailedChgs, 
					detailedChgsCount, 
					object, 
					product, parentDiffsTable, 
					sourceFile, sourceFileNum, 
					dmDiffsComparison, tag, 
					enumSets, 
					objChgUtf8BufferedOS, 
					utf8BufferedOS, 
					tempBuffer, 
					checkTextRsltHolder)
					;
	}
	
	private static final boolean __hasDecimalNumIndic(final String numberValueText) {
		final int numberValueTextLen = numberValueText.length();
		for (int i=0;i<numberValueTextLen;i++)
		{
			char ch = numberValueText.getChar(i);
			switch(ch)
			{
			case '.':
			case 'E':
			case 'e':
				return true;
			}
		}
		return false;
	}
	
	static string.Char confirmDecimalValueTypeFlag(final String fromTextVal, final String toTextVal) {
		if (!__hasDecimalNumIndic(fromTextVal) && !__hasDecimalNumIndic(toTextVal)) return __I__;
		return __D__;
	}
	
	static void writeDiffsTableRow(final MultiChgDiffsTableRow diffsTblRow, final eBSProduct product, 
									final DiffsTable parentDiffsTable, 
									final String sourceFile, final int sourceFileNum, 
									final String dmDiffsComparison, final String tag, 
									final DMCompEnumSets enumSets, 
									final BufferedTextOutputStream objChgUtf8BufferedOS, 
									final BufferedTextOutputStream utf8BufferedOS, 
									string.CharBuffer tempBuffer, 
									String[] checkTextRsltHolder) {
		final int diffsRowNumber = diffsTblRow.getOutputNumber(); 
		final int detailLvlIndic = 4;
		final int numberOfChgs = diffsTblRow.getNumberOfChanges();
		for (int i=0;i<numberOfChgs;i++)
		{
			DiffsTableRowChange diffsTblRowChg = diffsTblRow.getTableRowChange(i);
			int objChgNum = diffsTblRowChg.getOutputNumber();
			ChangeType chgType = enumSets.getChangeTypeByID(diffsTblRowChg.type/*nameOrCode*/); 
			int detailedChgsCount = diffsTblRowChg.getNumberOfDetailedChanges();
			DMObject object = new DMObject(parentDiffsTable.summaryCategory, diffsTblRow.objectName, product); 
			DMObject existing = enumSets.dmObjects.addIfNotPresent(object);
			if (existing != null) {
				object = existing;
			}
//			if (object.name.equals("AR_GTA_TRX_UTIL")) {
//				System.out.println("DMCompOutputUti:::::::::::::::::::::: HERE IT IS!!!!!");
//			}
			writeDiffsTableObjectChange(chgType, detailLvlIndic, 
						diffsRowNumber, objChgNum, 
						diffsTblRowChg.detailedChgs, 
						detailedChgsCount, 
						object, 
						product, parentDiffsTable, 
						sourceFile, sourceFileNum, 
						dmDiffsComparison, tag, 
						enumSets, 
						objChgUtf8BufferedOS, 
						utf8BufferedOS, 
						tempBuffer, 
						checkTextRsltHolder)
						;
		}
		
	}
	static void writeDiffsTableRow(final DiffsNewIndex diffsTblRow, final eBSProduct product, 
									final DiffsTable parentDiffsTable, 
									final String sourceFile, int sourceFileNum, 
									final String dmDiffsComparison, final String tag, 
									final DMCompEnumSets enumSets, 
									final BufferedTextOutputStream objChgUtf8BufferedOS, 
									final BufferedTextOutputStream utf8BufferedOS, 
									string.CharBuffer tempBuffer) {
		boolean f = false;
		if (f == false) return ;
		System.out.println("::::::writeDiffsTableRow:DiffsNewIndex - diffsTblRow.objectName: " + diffsTblRow.objectName + ", diffsTblRow.getOutputNumber(): " + diffsTblRow.getOutputNumber());
		DMObject object = new DMObject(parentDiffsTable.summaryCategory, diffsTblRow.objectName, product); 
		DMObject existing = enumSets.dmObjects.addIfNotPresent(object);
		if (existing != null) {
			object = existing;
		}
		System.out.println("::::::writeDiffsTableRow:DiffsNewIndex - diffsTblRow.objectName: " + diffsTblRow.objectName + ", object.name: " + object.name);
		int objNum = object.getOutputNumber();
		ChangeType chgType = enumSets.getChangeTypeByID(diffsTblRow.change/*nameOrCode*/); 
		java.lang.String nowDt = get_nowDtm();
		int diffsTblRowNum = diffsTblRow.getOutputNumber();
		int objChgNum = DiffsTableRowChange.nextSequenceValue();
		final int detailLvlIndic = 3;
		
		DiffsCompMethod diffsCompMethod = parentDiffsTable.diffsCompMethod == null || parentDiffsTable.diffsCompMethod.isEmpty() ? DiffsCompMethod.NO_VALUE : enumSets.getDiffsCompMethodByID(parentDiffsTable.diffsCompMethod);
		String diffsCompCategoryCode = diffsCompMethod == null ? DiffsCompMethodID.NO_VALUE_CODE : DiffsCompCategory.get_DiffsCompCategory(diffsCompMethod.code.diffsCompCategory).getShortCode()/*code*/;
		
		int colsCount = diffsTblRow.getNumberOfColumns();
		if (tempBuffer == null) {
			tempBuffer = new string.CharBuffer((colsCount << 4) + (colsCount << 2));
		}
		else {
			tempBuffer.resetLimitOnly();
			tempBuffer.ensureCanAddNMoreChars((colsCount << 4) + (colsCount << 2));
		}
		diffsTblRow.getColumns(tempBuffer);
		DMObject subject = new DMObject(parentDiffsTable.summaryCategory, diffsTblRow.tableName, object.owner, product); 
		existing = enumSets.dmObjects.addIfNotPresent(subject);
		if (existing != null) {
			subject = existing;
		}
		
		utf8BufferedOS.Write(DiffsDetailedChange.nextSequenceValue()/*DM_CMP_DETAIL_NUM*/).Write('|')
		.Write(diffsTblRowNum/*DIFFS_TABLE_ROW_NUM*/).Write('|').Write(detailLvlIndic/*DIFFS_CHG_DETAIL_LVL_INDIC_FIELD*/).Write('|')
		.Write(objChgNum/*DIFFS_CHG_NUM*/).Write('|').Write(object.owner).Write('|').Write(__N_A__/*DETAIL_NAME*/).Write('|')
		.Write(String.EMPTY/*QUALIFIER*/).Write('|').Write(object.name).Write('|').Write(objNum).Write('|')
		.Write(subject.name/*SUBJECT_NAME*/).Write('|').Write(DMCompDetailChangedType.tab.getShortCode()/*code*//*SUBJECT_TYPE*/).Write('|')
		.Write(DMCompDetailChangedType.tab.number/*SUBJECT_TYPE_NUM*/).Write('|')
		.Write(String.EMPTY/*SUBJECT_QUAL*/).Write('|').Write(subject.getOutputNumber()/*SUBJECT_NUM*/).Write('|')
		.Write(product.code/*PRODUCT_CODE*/).Write('|').Write(product.number/*PRODUCT_NUM*/).Write('|')
		.Write(diffsCompCategoryCode/*DIFFS_CMP_MTHD_CAT_CD*/).Write('|').Write(diffsCompMethod.code.diffsCompCategory/*DIFFS_CMP_MTHD_CAT_NUM*/).Write('|')
		.Write(diffsCompMethod.getShortCode()/*code*//*DIFFS_CMP_MTHD_CD*/).Write('|').Write(diffsCompMethod.code.number/*DIFFS_CMP_MTHD_NUM*/).Write('|')
		.Write(DMCompDetailChangedType.N_A.code/*DETAIL_TYPE*/).Write('|').Write(DMCompDetailChangedType.N_A.number/*DETAIL_TYPE_NUM*/).Write('|')
		.Write(chgType.getShortCode()/*code*//*DETAIL_CHG_TYPE*/).Write('|').Write(chgType.code.number/*DETAIL_CHG_TYPE_NUM*/).Write('|')
		.Write(String.EMPTY/*DETAIL_SUB_CHG_TYPE*/).Write('|').Write(String.EMPTY/*DETAIL_SUB_CHG_TYPE_NUM*/).Write('|').Write(0/*DETAIL_CHAIN_PATH_SZ*/).Write('|')
		.Write(0/*DETAIL_PATH_NUM*/).Write('|').Write(String.EMPTY/*/*VALUE_TYPE_FLG*/).Write('|').Write(String.EMPTY/*FROM_TEXT_VALUE*/).Write('|')
		.Write(tempBuffer/*TO_TEXT_VALUE*/).Write('|').Write(String.EMPTY/*FROM_INTEGER_VALUE*/).Write('|').Write(String.EMPTY/*TO_INTEGER_VALUE*/).Write('|')
		.Write(String.EMPTY/*FROM_DECIMAL_VALUE*/).Write('|').Write(String.EMPTY/*TO_DECIMAL_VALUE*/).Write('|').Write(String.EMPTY/*OTHER_DATA*/).Write('|')
		.Write(String.EMPTY/*OTHER_DATA_VALUE_TYPE_FLG*/).Write('|').Write(String.EMPTY/*DESCRIPTION*/).Write('|')
		.Write('Y'/*IS_NEW_INDEX_FLG_FIELD*/).Write('|').Write(parentDiffsTable.getSummaryCategoryShortCode()/*SUMMARY_CAT_CODE*/).Write('|')
		.Write(parentDiffsTable.summaryCategory/*SUMMARY_CAT_NUM*/).Write('|').Write(parentDiffsTable.getOutputNumber()/*DIFFS_TABLE_NUM*/)
		.Write('|').Write(sourceFile/*SOURCE_FILE*/).Write('|').Write(sourceFileNum).Write('|')
		.Write(dmDiffsComparison).Write('|').Write(tag).Write('|').Write(nowDt).Write('\n')
		;		
		
		objChgUtf8BufferedOS.Write(objChgNum/*OBJECT_CHG_NUM*/).Write('|').Write(diffsTblRowNum/*DIFFS_TABLE_ROW_NUM*/).Write('|')
		.Write(detailLvlIndic/*DIFFS_CHG_DETAIL_LVL_INDIC*/).Write('|').Write(object.owner/*OWNER*/).Write('|')
		.Write(object.number/*OBJECT_NUM*/).Write('|').Write(object.name/*OBJECT_NAME*/).Write('|').Write(product.number/*PRODUCT_NUM*/).Write('|').Write(product.code/*PRODUCT_CODE*/).Write('|')
		.Write(parentDiffsTable.summaryCategory/*SUMMARY_CAT_NUM*/).Write('|').Write(parentDiffsTable.getSummaryCategoryShortCode()/*SUMMARY_CAT_CODE*/).Write('|')
		.Write(parentDiffsTable.getOutputNumber()/*DIFFS_TABLE_NUM*/).Write('|').Write(diffsCompMethod.code.number/*DIFFS_CMP_MTHD_NUM*/).Write('|').Write(diffsCompMethod.getShortCode()/*code*//*DIFFS_CMP_MTHD_CD*/).Write('|')
		.Write(diffsCompMethod.code.diffsCompCategory/*DIFFS_CMP_MTHD_CAT_NUM*/).Write('|').Write(diffsCompCategoryCode/*DIFFS_CMP_MTHD_CAT_CD*/).Write('|')
		.Write(sourceFile/*SOURCE_FILE*/).Write('|').Write(sourceFileNum/*SOURCE_FILE_NUM*/).Write('|')
		.Write(dmDiffsComparison/*DM_CHG_COMPARISON*/).Write('|').Write(2/*NUMBER_OF_DETAILS*/).Write('|') //NOTE: a diffs table ro with two detailed changes has been muted to a new-index object ==> SO set the number of details to 2
		.Write(tag/*TAG*/).Write('|').Write(nowDt/*CREATED_DT*/).Write('\n')
		;
	}
	
	static final String WHEN_FIELD = CString.valueOf(new byte[]{'W', 'H', 'E', 'N'}, true, true);
	static final String DIFFS_OBJ_CHG_NUM_FIELD = CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'O', 'B', 'J', '_', 'C', 'H', 'G', '_', 'N', 'U', 'M'}, true, true);
	static final String CHANGE_FIELD = CString.valueOf(new byte[]{'C', 'H', 'A', 'N', 'G', 'E', }, true, true);
	static final String ERROR_MSG_FIELD = CString.valueOf(new byte[]{'E', 'R', 'R', 'O', 'R', '_', 'M', 'S', 'G'}, true, true);
	static final String DIFFS_COMP_MTHD_FIELD = CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'C', 'O', 'M', 'P'}, true, true);
	
	static void writeDiffsOuputErrorHeader(final BufferedTextOutputStream utf8BufferedOS) { 
		utf8BufferedOS.Write(WHEN_FIELD).Write('|').Write(SUMMARY_NUM_FIELD).Write('|').Write(SUMMARY_CAT_CODE_FIELD).Write('|')
		.Write(SUMMARY_CAT_NUM_FIELD).Write('|').Write(DIFFS_TABLE_NUM_FIELD).Write('|').Write(DIFFS_OBJ_CHG_NUM_FIELD).Write('|')
		.Write(OBJECT_NAME_FIELD).Write('|').Write(OBJECT_NUM_FIELD).Write('|').Write(DIFFS_COMP_MTHD_FIELD).Write('|').Write(CHANGE_FIELD).Write('|')
		.Write(DIFFS_TABLE_ROW_NUM_FIELD).Write('|').Write(SOURCE_FILE_FIELD).Write('|').Write(SOURCE_FILE_NUM_FIELD).Write('|')
		.Write(DM_CHG_COMPARISON_FIELD).Write('|').Write(TAG_FIELD).Write('|').Write(ERROR_MSG_FIELD).write('\n');
	}
	
	private static final string.CharBuffer ensureErrMsgIsInline(final java.lang.String errMsg, string.CharBuffer inlinedRsltBuffer) {
		final int errMsgLen = errMsg.length();
		boolean errMsgWithLnTerminators = false, nextCharMayBeLf = false;;
		int i = 0;
		__check_if_errMsg_contains_ln_terminator_char_loop:
		do 
		{
			if (i < errMsgLen) {
				char ch = errMsg.charAt(i);
				if (nextCharMayBeLf) {
					nextCharMayBeLf = false;
					if (ch == '\n') {
						i++;
						if (i >= errMsgLen) {
							break __check_if_errMsg_contains_ln_terminator_char_loop;
						}
						ch = errMsg.charAt(i);
					}
				}
				if (ch == '\r' || ch == '\n') {
					if (errMsgWithLnTerminators) {
						inlinedRsltBuffer.appendChars('<', 'B', 'R', '>');
					}
					else {
						if (inlinedRsltBuffer == null) {
							inlinedRsltBuffer = new string.CharBuffer(errMsgLen + 6); //assume there will be at least two line terminators
						}
						else {
							inlinedRsltBuffer.resetLimitOnly();
							inlinedRsltBuffer.ensureCanAddNMoreChars(errMsgLen + 6); //assume there will be at least two line terminators
						}
						inlinedRsltBuffer.appendChars(errMsg, 0, i);
						errMsgWithLnTerminators = true;
					}
					nextCharMayBeLf = ch == '\r';
				}
				else if (errMsgWithLnTerminators) {
					inlinedRsltBuffer.appendChar(ch);
				}
				i++;
				continue __check_if_errMsg_contains_ln_terminator_char_loop;
			} //end if (i < errMsgLen)
			break __check_if_errMsg_contains_ln_terminator_char_loop;
		} while (true); //end __check_if_errMsg_contains_ln_terminator_char_loop
		return errMsgWithLnTerminators ? inlinedRsltBuffer : null;
	}
	
	static void writeDiffsTableRowError(final int diffsTableNum, final int diffsTableRowNum, 
									final String objectName, final int objectNum, 
									final int objChgNum, final String diffsCompMethod, final String change, 
									String sourceFile, int sourceFileNum, 
									final String dmDiffsComparison, final String tag, 
									final String summaryCatCode, final int summaryCatNum, 
									/*final */java.lang.String errMsg, 
									final BufferedTextOutputStream utf8BufferedOS, 
									string.CharBuffer tempBufferr) {
		if (errMsg == null) {
			errMsg = "<null>";
		}
//		final int errMsgLen = errMsg.length();
//		boolean errMsgWithLnTerminators = false, nextCharMayBeLf = false;;
//		int i = 0;
//		__check_if_errMsg_contains_ln_terminator_char_loop:
//		do 
//		{
//			if (i < errMsgLen) {
//				char ch = errMsg.charAt(i);
//				if (nextCharMayBeLf) {
//					nextCharMayBeLf = false;
//					if (ch == '\n') {
//						i++;
//						if (i >= errMsgLen) {
//							break __check_if_errMsg_contains_ln_terminator_char_loop;
//						}
//						ch = errMsg.charAt(i);
//					}
//				}
//				if (ch == '\r' || ch == '\n') {
//					if (errMsgWithLnTerminators) {
//						tempBufferr.appendChars('<', 'B', 'R', '>');
//					}
//					else {
//						if (tempBufferr == null) {
//							tempBufferr = new string.CharBuffer(errMsgLen + 6); //assume there will be at least two line terminators
//						}
//						else {
//							tempBufferr.resetLimitOnly();
//							tempBufferr.ensureCanAddNMoreChars(errMsgLen + 6); //assume there will be at least two line terminators
//						}
//						tempBufferr.appendChars(errMsg, 0, i);
//						errMsgWithLnTerminators = true;
//					}
//					nextCharMayBeLf = ch == '\r';
//				}
//				else if (errMsgWithLnTerminators) {
//					tempBufferr.appendChar(ch);
//				}
//				i++;
//				continue __check_if_errMsg_contains_ln_terminator_char_loop;
//			}
//			break __check_if_errMsg_contains_ln_terminator_char_loop;
//		} while (true); //end __check_if_errMsg_contains_ln_terminator_char_loop
		string.CharBuffer inlinedRsltBuffer = ensureErrMsgIsInline(errMsg, tempBufferr);
		if (tempBufferr == null && inlinedRsltBuffer != null) {
			tempBufferr = inlinedRsltBuffer;
		}
		utf8BufferedOS.Write(get_nowDtm()).Write('|').Write(String.EMPTY/*SUMMARY_NUM*/).Write('|').Write(summaryCatCode).Write('|')
		.Write(summaryCatNum).Write('|').Write(diffsTableNum).Write('|').Write(objChgNum).Write('|')
		.Write(objectName).Write('|').Write(objectNum).Write('|').Write(diffsCompMethod).Write('|').Write(change).Write('|')
		.Write(diffsTableRowNum).Write('|').Write(sourceFile).Write('|').Write(sourceFileNum).Write('|')
		.Write(dmDiffsComparison).Write('|').Write(tag).write('|');
		if (inlinedRsltBuffer != null/*errMsgWithLnTerminators*/) {
			utf8BufferedOS.Write(tempBufferr/*ERROR_MSG*/).write('\n');
		}
		else {
			utf8BufferedOS.Write(errMsg).write('\n');
		}
	}
	
	static void writeDiffsTable(final DiffsTable diffsTable, final eBSProduct product, 
									String sourceFile, int sourceFileNum, 
									final String dmDiffsComparison, final String tag, 
									final DMCompEnumSets enumSets, 
									final BufferedTextOutputStream objChgUtf8BufferedOS, 
									final BufferedTextOutputStream dtlChgUtf8BufferedOS, 
									string.CharBuffer tempBuffer, 
									String[] checkTextRsltHolder, 
									final BufferedTextOutputStream errorUtf8BufferedOS) {
		boolean isNewIndex = false;
		final int diffsTblRowsCount = diffsTable.size();
		main_loop: 
		for (int i=0;i<diffsTblRowsCount;i++)
		{
			DiffsTableRow diffsTblRow = diffsTable.diffsRows[i];
			isNewIndex = false;
			try 
			{
				if (diffsTblRow.isMultiChgDiffsTableRow()) {
					MultiChgDiffsTableRow multiChgsDiffsTblRow = diffsTblRow.asMultiChgDiffsTableRow();
					writeDiffsTableRow(multiChgsDiffsTblRow, product, 
										diffsTable, 
										sourceFile, sourceFileNum, 
										dmDiffsComparison, tag, 
										enumSets, 
										objChgUtf8BufferedOS, 
										dtlChgUtf8BufferedOS, 
										tempBuffer, 
										checkTextRsltHolder)
										;
					continue main_loop;
				}
				else if (diffsTblRow.isSingleChgDiffsTableRowAdvcd()) {
					SingleChgDiffsTableRowAdvcd diffsTblRowAdvcd = diffsTblRow.asSingleChgDiffsTableRowAdvcd();
					__switch_summaryCategory_block__:
					switch(diffsTable.summaryCategory)
					{
					case DMCompSummaryCategories.New_Indexes_on_Existing_Tables_$_Columns: 
					case DMCompSummaryCategories.New_Indexes_on_new_Tables_$_Columns: 
						if (diffsTblRowAdvcd.getNumberOfDetailedChanges() == 2) {
							DiffsDetailedChange detailedChg1 = diffsTblRowAdvcd.detailedChgs[0];
							DiffsDetailedChange detailedChg2 = diffsTblRowAdvcd.detailedChgs[1];
							if (detailedChg1.size() == 2 && detailedChg2.size() == 2 && 
									detailedChg1.startsWithTableSubject() && detailedChg2.startsWithColumnSubject()) {
								String tableName = detailedChg1.getCleansedSpecItem(1);
								String[] columns = DiffsNewIndex.splitColumns(detailedChg2.getCleansedSpecItem(1)/*columns*/);
								diffsTblRow = new DiffsNewIndex(diffsTblRowAdvcd.objectName, tableName, columns);
								diffsTable.diffsRows[i] = diffsTblRow;
								break __switch_summaryCategory_block__;
							}
						}
						//NOTE: fall-through on purpose
					default:
						writeDiffsTableRow(diffsTblRowAdvcd, product, 
										diffsTable, 
										sourceFile, sourceFileNum, 
										dmDiffsComparison, tag, 
										enumSets, 
										objChgUtf8BufferedOS, 
										dtlChgUtf8BufferedOS, 
										tempBuffer, 
										checkTextRsltHolder);
						continue main_loop;
					}
				}
				//else NewIndex or base diffs table row, but don't do the ELSE here as an advanced could have become a NewIndex, rather start a new if block!!!
				if (diffsTblRow.isDiffsNewIndex()) {
					DiffsNewIndex diffsNewIndex = diffsTblRow.asDiffsNewIndex();
					writeDiffsTableRow(diffsNewIndex, product, 
										diffsTable, 
										sourceFile, sourceFileNum, 
										dmDiffsComparison, tag, 
										enumSets, 
										objChgUtf8BufferedOS, 
										dtlChgUtf8BufferedOS, 
										tempBuffer);
					continue main_loop;
				}
				 writeDiffsTableRow(diffsTblRow.asSingleChgDiffsTableRow(), product, diffsTable, 
										sourceFile, sourceFileNum, 
										dmDiffsComparison, tag, 
										enumSets, 
										objChgUtf8BufferedOS, 
										dtlChgUtf8BufferedOS, 
										tempBuffer, 
										checkTextRsltHolder);
			}
			catch(Exception ex)
			{
				String objectName = diffsTblRow.objectName;
				int objectNum;
				int objChgNum;
				String change;
				if (diffsTblRow.isMultiChgDiffsTableRow()) {
					MultiChgDiffsTableRow multiChgTblRow = diffsTblRow.asMultiChgDiffsTableRow();
					objectNum = -1;
					DiffsTableRowChange firstTblRowChg = multiChgTblRow.changes[0];
					objChgNum = firstTblRowChg.getOutputNumber();
					change = firstTblRowChg.type;
				}
				else {
					SingleChgDiffsTableRow singleChgTblRow = diffsTblRow.asSingleChgDiffsTableRow();
					objChgNum = singleChgTblRow.getOutputNumber();
					objectNum = -1;
					change = singleChgTblRow.change;
				}
				writeDiffsTableRowError(diffsTable.getOutputNumber(), 
									diffsTblRow.getOutputNumber()/*diffsTableRowNum*/, 
									objectName, objectNum, 
									objChgNum, diffsTable.diffsCompMethod, change, 
									sourceFile, sourceFileNum, 
									dmDiffsComparison, tag, 
									DMCompSummaryCategories.get_CategoryName(diffsTable.summaryCategory), 
									diffsTable.summaryCategory, 
									ex.getMessage(), 
									errorUtf8BufferedOS, 
									tempBuffer);
				ex.printStackTrace();
			}
		} //end main_loop
	}

	/**
	* Writes the supplied data model comparison report to the output files.<br>
	* @param rpt the report to write
	* @param summaryComment the summary comment
	* @param dmDiffsComparison the identifier of the diffs comparison as part of which the report was produced - must not be null
	* @param tag the tag to use - msut not be null
	* @param enumSets the holders of the enums/LOVs - must not be null
	* @param refDataValueUtf8BufferedOS the UTF-8 output stream where to write the new products/ref data
	* @param summaryUtf8BufferedOS the UTF-8 output stream where to write the summary
	* @param objChgUtf8BufferedOS the UTF-8 output stream where to write the object diffs/changes
	* @param dtlChgUtf8BufferedOS the UTF-8 output stream where to write the detailed changes
	* @param errorUtf8BufferedOS the output stream here to write errors
	* @param tempBuffer the temporary buffer to reuse whenever needed - can be null
	* @param checkTextRsltHolder an array of length greater than 2 is expected - can be null; if the length is less than 3, a new array will be created by the method
	*/
	static eBSProduct writeDMComparisonReport(final DataModelComparisonReport rpt, 
									final String summaryComment, 
									final String dmDiffsComparison, final String tag, 
									final DMCompEnumSets enumSets, 
									final BufferedTextOutputStream refDataValueUtf8BufferedOS, 
									final BufferedTextOutputStream summaryUtf8BufferedOS, 
									final BufferedTextOutputStream objChgUtf8BufferedOS, 
									final BufferedTextOutputStream dtlChgUtf8BufferedOS, 
									final BufferedTextOutputStream errorUtf8BufferedOS, 
									string.CharBuffer tempBuffer, 
									String[] checkTextRsltHolder) {
		if (tempBuffer == null) {
			tempBuffer = new string.CharBuffer(64);
		}
		if (checkTextRsltHolder == null || checkTextRsltHolder.length < 3) {
			checkTextRsltHolder = new String[3];
		}
		//final int summaryNumber = rpt.diffsSummary.getOutputNumber();
		final int sourceFileNum = rpt.getSourceFileOutputNumber();
		
		//NOTE: changed from rpt.sourceFile to rpt.getSourceFileCode() on 2017-06-21
		short productNumber = writeProduct(rpt.product, enumSets, rpt.getSourceFileCode()/*OLD: sourceFile*/, sourceFileNum, tag, dmDiffsComparison, refDataValueUtf8BufferedOS);
		if (productNumber < (short)0) { //case the product has not been written because it was already existing
			productNumber *= -1; //restore the true value of the product number
		}
		eBSProduct product = enumSets.ebsProducts.get(productNumber - 1); //BUG-FIX - 2017-05-03 - replaced productNumber with "productNumber - 1" because the index is zero based while te number is one based
		//NOTE: changed from rpt.sourceFile to rpt.getSourceFileCode() on 2017-06-21
		writeSummaryReport(rpt.diffsSummary, product, 
						rpt.getSourceFileCode()/*OLD: sourceFile*/, sourceFileNum, dmDiffsComparison, tag, 
						summaryComment, summaryUtf8BufferedOS, errorUtf8BufferedOS, 
						tempBuffer);
						
		final int diffsTblsCount = rpt.getNumberOfDiffsTables();
		for (int i=0;i<diffsTblsCount;i++)
		{
			DiffsTable diffsTable = rpt.diffsTables[i];
			//NOTE: changed from rpt.sourceFile to rpt.getSourceFileCode() on 2017-06-21
			writeDiffsTable(diffsTable, product, 
									rpt.getSourceFileCode()/*OLD: sourceFile*/, sourceFileNum, 
									dmDiffsComparison, tag, 
									enumSets, 
									objChgUtf8BufferedOS, 
									dtlChgUtf8BufferedOS, 
									tempBuffer, 
									checkTextRsltHolder, 
									errorUtf8BufferedOS)
									;
		}
		return product;				
	}
	/**
	* Completes the writing but does not flush the output streams.
	*/
	static final void completeWriting(final String dmDiffsComparison, 
									final String sourceFile, 
									final int sourceFileNum, 
									final eBSProduct product, 
									/*COMMENTED OUT ON 2017-05-03 BECAUSE KO - THE INFO WAS MEANT TO SERVE WHEN wRITING DMObject instances, WHICH EACH HAS THAT INFO AS ATTRIBUTE 
									final String summaryCatCode, 
									final int summaryCatNum, */
									final String tag, 
									final DMCompEnumSets enumSets, 
									final BufferedTextOutputStream refDataValueUtf8BufferedOS,  
									final BufferedTextOutputStream objectUtf8BufferedOS, 
									final BufferedTextOutputStream detailPathUtf8BufferedOS, 
									final BufferedTextOutputStream srcFilesUtf8BufferedOS, 
									final BufferedTextOutputStream errorUtf8BufferedOS, 
									string.CharBuffer tempBuffer) {
		//*******
		// NOTE: because the new reference data items (except products) are not written to the output stream, they will be written here as part of the completion of the writing 
		//
		//********
		
		////System.out.println(" - completeWriting: enumSets.dmCompDetailPaths.size(): " + enumSets.dmCompDetailPaths.size());
		core.IIterable<DMCompDetailPath> detailPathIter = enumSets.dmCompDetailPaths.iterator();
		while (detailPathIter.next())
		{
			DMCompDetailPath detailPath = detailPathIter.get();
			if (!detailPath.alreadyWritten) {
				writeDetailPath(detailPath, sourceFile, sourceFileNum, dmDiffsComparison, tag, detailPathUtf8BufferedOS);
				detailPath.alreadyWritten = true;
			}
		}
		detailPathIter = null;
		if (tempBuffer == null) {
			tempBuffer = new string.CharBuffer(20);
		}
		core.IIterable<DMObject> objectIter = enumSets.dmObjects.iterator();
		while (objectIter.next())
		{
			DMObject obj = objectIter.get();
			if (!obj.alreadyWritten) { //BUG-FIX-2017-06-21 - avoid creating duplicates by using alreadyWritten, gien that the set is a hash-set
				byte childObjTypeNum = obj.getChildObjectType();
				String childObjTypeCode = obj.isChildObject() ? DMChildObjectTypes.get_ChildObjectTypeCode(childObjTypeNum) : String.EMPTY;
				String childObjModifier = obj.isChildObject() ? obj.getChildObjectModifier() : String.EMPTY;
				String parentObjectName = obj.isChildObject() ? obj.getParentObjectName() : String.EMPTY;
				writeObject(obj.getOutputNumber(), obj.owner, obj.name, 
										obj.getSummaryCategoryShortCode(), 
										obj.summaryCategory/*summaryCatNum*/, 
										product.code, product.number, 
										String.EMPTY/*packageAppCode*/, String.EMPTY/*description*/, 
										obj.isChildObject(), 
										childObjTypeNum, 
										childObjTypeCode, 
										childObjModifier, 
										parentObjectName, 
										sourceFile, sourceFileNum, 
										dmDiffsComparison, tag, 
										objectUtf8BufferedOS, 
										tempBuffer)
										;
				obj.alreadyWritten = true; //since 2017-06-21
			}
		}
		objectIter = null;
		if (enumSets.detailChangedTypes.size() > enumSets.detailChangedTypesStartCount) {
			//DMCompDetailChangedTypes
			final int end = enumSets.detailChangedTypes.size();
			for (int i=enumSets.detailChangedTypesStartCount;i<end;i++)
			{
				DMCompDetailChangedType detailChgdType = enumSets.detailChangedTypes.get/*getByNumber*/(i); //BUG-FIX-2017-06-21 - it is not OK to use method getByNumber
				writeDetailChangedType(detailChgdType, sourceFile, sourceFileNum, tag, dmDiffsComparison, refDataValueUtf8BufferedOS);
			}
			enumSets.detailChangedTypesStartCount = enumSets.detailChangedTypes.size(); //since 2017-06-21 - update for iteration for next file to not create duplicates
		}
		if (enumSets.changeTypes.size() > enumSets.changeTypesStartCount) {
			//DMCompDetailChangedTypes
			final int end = enumSets.changeTypes.size();
			for (int i=enumSets.changeTypesStartCount;i<end;i++)
			{
				ChangeType chgType = enumSets.changeTypes.get(i/*i - 1*/); //BUG-FIX-2017-06-21 - i is zero-based, so should not decrement by one //BUG-FIX - 2017-05-04 - replaced "get(i)" with "get(i - 1)" as get method expects a zero based index (number - 1)
				writeChangeType(chgType, sourceFile, sourceFileNum, tag, dmDiffsComparison, refDataValueUtf8BufferedOS);
			}
			enumSets.changeTypesStartCount = end; //since 2017-06-21 - update for iteration for next file to not create duplicates	
		}
		if (enumSets.diffsCompMethodSet.size() > enumSets.diffsCompMethodsStartCount) {
			final int end = enumSets.diffsCompMethodSet.size();
			for (int i=enumSets.diffsCompMethodsStartCount;i<end;i++)
			{
				DiffsCompMethod diffsCompMthd = enumSets.diffsCompMethodSet.get(i/*i - 1*/); //BUG-FIX-2017-06-21 - i is zero-based, so should not decrement by one //BUG-FIX - 2017-05-04 - replaced "get(i)" with "get(i - 1)" as get method expects a zero based index (number - 1)
				writeDiffsCompMethod(diffsCompMthd, sourceFile, sourceFileNum, tag, dmDiffsComparison, refDataValueUtf8BufferedOS);
			}
			enumSets.diffsCompMethodsStartCount = end; //since 2017-06-21 - update for iteration for next file to not create duplicates	
		}
		int sz = enumSets.secondarySources.size();
		for (;enumSets.writtenSecondarySources<sz;enumSets.writtenSecondarySources++)
		{
			DMSource source = enumSets.secondarySources.get(enumSets.writtenSecondarySources);
			writeSecondarySourceFile(source, dmDiffsComparison, tag, srcFilesUtf8BufferedOS);
		}
		
	}
	
	
	public static final String TAG_CATEGORY = TAG_FIELD;
	
	public static final String DIFFS_COMPARISON_CATEGORY = string.CString.valueOf(new byte[]{'D', 'I', 'F', 'F', 'S', '_', 'C', 'O', 'M', 'P', 'A', 'R', 'I', 'S', 'O', 'N', }, true, true); 
	
	public static final String SOURCE_FILE_CATEGORY = SOURCE_FILE_FIELD; 
	
}