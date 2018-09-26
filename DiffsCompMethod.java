package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import string.CString;

/**
* Unbounded enumeration class for providing support for change types.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public class DiffsCompMethod implements java.io.Serializable, Comparable<DiffsCompMethod> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x0208338EE1B6928AL;
	
	/**
	* Constant for <code>&quot;Object Definition Differences Between 12.1.3 and 12.2.6)&quot;</code> which is a synonym for <code>&quot;Object Definition Differences Between 12.1.3 and 12.2.6&quot;</code>.<br>
	*/
	public static final String Object_Definition_Differences_Between_12_1_3_and_12_2_6_ = CString.valueOf(new byte[]{'O', 'b', 'j', 'e', 'c', 't', ' ', 'D', 'e', 'f', 'i', 'n', 'i', 't', 'i', 'o', 'n', ' ', 'D', 'i', 'f', 'f', 'e', 'r', 'e', 'n', 'c', 'e', 's', ' ', 'B', 'e', 't', 'w', 'e', 'e', 'n', ' ', '1', '2', '.', '1', '.', '3', ' ', 'a', 'n', 'd', ' ', '1', '2', '.', '2', '.', '6', ')'}, true, true);
	
	
	/**
	* Constant for signalling the absence of value, which also referred to as unspecified.<br>
	*/
	public static final DiffsCompMethod NO_VALUE = new DiffsCompMethod(DiffsCompMethodID.NO_VALUE_CODE, DiffsCompMethodID.NO_VALUE_NAME);
	
	/**
	* Empty array.
	*/
	public static final DiffsCompMethod[] EMPTY_ARRAY = new DiffsCompMethod[0];
	
	public final DiffsCompMethodID code;
	public final DiffsCompMethodID name;
	
	DiffsCompMethod(final DiffsCompMethodID code, final DiffsCompMethodID name) {
		super();
		this.code = code;
		this.name = name;
	}
	/**
	* Gets the short code of this <code>DiffsCompMethod</code>.<br>
	*/
	public final String getShortCode() { //since 2017-06-21
		return DiffsCompMethods.getShortCode(code.number);
	}
	/**
	* Compares this <code>DiffsCompMethod</code> to that supplied, for order.
	*/
	public final int compareTo(final DiffsCompMethod other) {
		return code.number == other.code.number ? (byte)0 : 
				code.number < other.code.number ? (byte)-1 : (byte)0;
	}
	/**
	* Compares this <code>DiffsCompMethod</code> to that supplied, for (number) equality.
	*/
	public final boolean equals(final DiffsCompMethod other) {
		return code.number == other.code.number;
	}
	/**
	* Compares this <code>DiffsCompMethod</code> to that supplied, for (number) equality.
	*/
	public final boolean equals(final java.lang.Object other) {
		return other instanceof DiffsCompMethod && code.number == ((DiffsCompMethod)other).code.number;
	}

	/**
	* {@inheritDoc}
	* @return <code>this.code.toString()</code>
	*/
	public java.lang.String toString() {return code.toString(); }
	
	/**
	* Constant for <code>&quot;Object Definition Differences Between 12.1.3 and 12.2.6&quot;</code> change category.<br>
	*/
	public static final DiffsCompMethod Object_Definition_Differences_Between_12_1_3_and_12_2_6 = new DiffsCompMethod(new DiffsCompMethodID(DiffsCompMethods.Object_Definition_Differences_Between_12_1_3_and_12_2_6, DiffsCompCategories.Object_Definition_Differences, new byte[]{'O', 'b', 'j', 'e', 'c', 't', ' ', 'D', 'e', 'f', 'i', 'n', 'i', 't', 'i', 'o', 'n', '_', 'D', 'i', 'f', 'f', 'e', 'r', 'e', 'n', 'c', 'e', 's', '_', 'B', 'e', 't', 'w', 'e', 'e', 'n', '_', '1', '2', '_', '1', '_', '3', '_', 'a', 'n', 'd', '_', '1', '2', '_', '2', '_', '6'}, false/*isName*/), 
																new DiffsCompMethodID(DiffsCompMethods.Object_Definition_Differences_Between_12_1_3_and_12_2_6, DiffsCompCategories.Object_Definition_Differences, new byte[]{'O', 'b', 'j', 'e', 'c', 't', ' ', 'D', 'e', 'f', 'i', 'n', 'i', 't', 'i', 'o', 'n', ' ', 'D', 'i', 'f', 'f', 'e', 'r', 'e', 'n', 'c', 'e', 's', ' ', 'B', 'e', 't', 'w', 'e', 'e', 'n', ' ', '1', '2', '.', '1', '.', '3', ' ', 'a', 'n', 'd', ' ', '1', '2', '.', '2', '.', '6'}, true/*isName*/));
	/**
	* Constant for <code>&quot;Objects Added in 12.2.6&quot;</code> change category.<br>
	*/
	public static final DiffsCompMethod Indexed_Tables_and_Columns = new DiffsCompMethod(new DiffsCompMethodID(DiffsCompMethods.Indexed_Tables_and_Columns, DiffsCompCategory.Indexed_Tables_and_Columns.code), 
																new DiffsCompMethodID(DiffsCompMethods.Indexed_Tables_and_Columns, DiffsCompCategory.Indexed_Tables_and_Columns.name));
	/**
	* Constant for <code>&quot;Indexed Tables & Columns (Note: Either underlying table or columns are not present in 12.1.3)&quot;</code> change category.<br>
	*/
	public static final DiffsCompMethod Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note = new DiffsCompMethod(new DiffsCompMethodID(DiffsCompMethods.Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note, DiffsCompCategories.Indexed_Tables_and_Columns, new byte[]{'I', 'n', 'd', 'e', 'x', 'e', 'd', '_', 'T', 'a', 'b', 'l', 'e', 's', '_', 'a', 'n', 'd', '_', 'C', 'o', 'l', 'u', 'm', 'n', 's', '_', '_', 'w', '_', 'f', 'r', 'o', 'm', '_', '1', '2', '_', '1', '_', '3', '_', 't', 'o', '_', '1', '2', '_', '2', '_', '6', '_', 'N', 'o', 't', 'e'}, false/*isName*/), 
																new DiffsCompMethodID(DiffsCompMethods.Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note, DiffsCompCategories.Indexed_Tables_and_Columns, new byte[]{'I', 'n', 'd', 'e', 'x', 'e', 'd', ' ', 'T', 'a', 'b', 'l', 'e', 's', ' ', '&', ' ', 'C', 'o', 'l', 'u', 'm', 'n', 's', ' ', '(', 'N', 'o', 't', 'e', ':', ' ', 'E', 'i', 't', 'h', 'e', 'r', ' ', 'u', 'n', 'd', 'e', 'r', 'l', 'y', 'i', 'n', 'g', ' ', 't', 'a', 'b', 'l', 'e', ' ', 'o', 'r', ' ', 'c', 'o', 'l', 'u', 'm', 'n', 's', ' ', 'a', 'r', 'e', ' ', 'n', 'o', 't', ' ', 'p', 'r', 'e', 's', 'e', 'n', 't', ' ', 'i', 'n', ' ', '1', '2', '.', '1', '.', '3', ')'}, true/*isName*/));

	/**
	* Constant for <code>&quot;Indexed Tables & Columns (Note: Either underlying table or columns are not present in 12.1.3)&quot;</code> change category.<br>
	*/
	public static final DiffsCompMethod Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note_2 = new DiffsCompMethod(new DiffsCompMethodID(DiffsCompMethods.Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note_2, DiffsCompCategories.Indexed_Tables_and_Columns, new byte[]{'I', 'n', 'd', 'e', 'x', 'e', 'd', '_', 'T', 'a', 'b', 'l', 'e', 's', '_', 'a', 'n', 'd', '_', 'C', 'o', 'l', 'u', 'm', 'n', 's', '_', '_', 'w', '_', 'f', 'r', 'o', 'm', '_', '1', '2', '_', '1', '_', '3', '_', 't', 'o', '_', '1', '2', '_', '2', '_', '6', '_', 'N', 'o', 't', 'e', '_', '2'}, false/*isName*/), 
																new DiffsCompMethodID(DiffsCompMethods.Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note_2, DiffsCompCategories.Indexed_Tables_and_Columns, new byte[]{'I', 'n', 'd', 'e', 'x', 'e', 'd', ' ', 'T', 'a', 'b', 'l', 'e', 's', ' ', '&', ' ', 'C', 'o', 'l', 'u', 'm', 'n', 's', ' ', '(', 'N', 'o', 't', 'e', ':', ' ', 'E', 'v', 'e', 'n', ' ', 't', 'h', 'o', 'u', 'g', 'h', ' ', 't', 'h', 'e', ' ', 'i', 'n', 'd', 'e', 'x', ' ', 'i', 's', ' ', 'a', 'd', 'd', 'e', 'd', ' ', 'i', 'n', ' ', '1', '2', '.', '1', '.', '3', ',', ' ', 'a', 'l', 'l', ' ', 'u', 'n', 'd', 'e', 'r', 'l', 'y', 'i', 'n', 'g', ' ', 't', 'a', 'b', 'l', 'e', 's', ' ', '&', ' ', 'c', 'o', 'l', 'u', 'm', 'n', 's', ' ', 'a', 'r', 'e', ' ', 'p', 'r', 'e', 's', 'e', 'n', 't', ' ', 'i', 'n', ' ', '1', '2', '.', '1', '.', '3', ')'}, true/*isName*/));
																
																
	/**
	* Returns a new array containing the vanilla change categories.
	* @see zz.ebs.dmcomp.DiffsCompMethods#NUMBER_OF_VANILLA_DIFFS_COMP_METHODS NUMBER_OF_VANILLA_DIFFS_COMP_METHODS
	*/															
	public static final DiffsCompMethod[] getVanillas() {
		return new DiffsCompMethod[]{
				Object_Definition_Differences_Between_12_1_3_and_12_2_6, 
				Indexed_Tables_and_Columns, 
				Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note, 
				Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note_2
				}; 
	}
	
	/**
	* Copies the vanilla change categories into the supplied array.
	* @see zz.ebs.dmcomp.DiffsCompMethods#NUMBER_OF_VANILLA_DIFFS_COMP_METHODS NUMBER_OF_VANILLA_DIFFS_COMP_METHODS
	*/
	public static final void getVanillas(final DiffsCompMethod[] dest, int destOff) {
		//return new DiffsCompMethod[]{Object_Definition_Differences_Between_12_1_3_and_12_2_6, Indexed_Tables_and_Columns, Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note}; 
		dest[destOff] = Object_Definition_Differences_Between_12_1_3_and_12_2_6;
		dest[++destOff] = Indexed_Tables_and_Columns;
		dest[++destOff] = Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note;
		dest[++destOff] = Indexed_Tables_and_Columns__w_from_12_1_3_to_12_2_6_Note_2;
	}
	


}