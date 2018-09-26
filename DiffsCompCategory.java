package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import string.CString;

/**
* Unbounded enumeration class for providing support for change categories.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public class DiffsCompCategory implements java.io.Serializable, Comparable<DiffsCompCategory> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x0208338901B6928AL;
	
	/**
	* Empty array.
	*/
	public static final DiffsCompCategory[] EMPTY_ARRAY = new DiffsCompCategory[0];
	
	
	public final DiffsCompCategoryID code;
	public final DiffsCompCategoryID name;
	
	DiffsCompCategory(final DiffsCompCategoryID code, final DiffsCompCategoryID name) {
		super();
		this.code = code;
		this.name = name;
	}
	DiffsCompCategory(final DiffsCompCategoryID code) {
		this(code, new DiffsCompCategoryID(code, true/*altIDType*/));
	}
	/**
	* Gets the short code of this <code>DiffsCompCategory</code>.<br>
	*/
	public final String getShortCode() { //since 2017-06-21
		return DiffsCompCategories.getShortCode(code.number);
	}
	/**
	* Compares this <code>DiffsCompCategory</code> to that supplied, for order.
	*/
	public final int compareTo(final DiffsCompCategory other) {
		return code.number == other.code.number ? (byte)0 : 
				code.number < other.code.number ? (byte)-1 : (byte)0;
	}
	/**
	* Compares this <code>DiffsCompCategory</code> to that supplied, for (number) equality.
	*/
	public final boolean equals(final DiffsCompCategory other) {
		return code.number == other.code.number;
	}
	/**
	* Compares this <code>DiffsCompCategory</code> to that supplied, for (number) equality.
	*/
	public final boolean equals(final java.lang.Object other) {
		return other instanceof DiffsCompCategory && code.number == ((DiffsCompCategory)other).code.number;
	}

	/**
	* {@inheritDoc}
	* @return <code>this.code.toString()</code>
	*/
	public java.lang.String toString() {return code.toString(); }

	/**
	* Constant for <code>Object_Definition_Differences</code> change category.<br>
	*/
	public static final DiffsCompCategory Object_Definition_Differences = new DiffsCompCategory(new DiffsCompCategoryID(DiffsCompCategories.Object_Definition_Differences, new byte[]{'O', 'b', 'j', 'e', 'c', 't', '_', 'D', 'e', 'f', 'i', 'n', 'i', 't', 'i', 'o', 'n', '_', 'D', 'i', 'f', 'f', 'e', 'r', 'e', 'n', 'c', 'e', 's', }, false/*isName*/), 
																new DiffsCompCategoryID(DiffsCompCategories.Object_Definition_Differences, new byte[]{'O', 'b', 'j', 'e', 'c', 't', ' ', 'D', 'e', 'f', 'i', 'n', 'i', 't', 'i', 'o', 'n', ' ', 'D', 'i', 'f', 'f', 'e', 'r', 'e', 'n', 'c', 'e', 's', }, true/*isName*/));
	/**
	* Constant for <code>Indexed_Tables_and_Columns</code> change category.<br>
	*/
	public static final DiffsCompCategory Indexed_Tables_and_Columns = new DiffsCompCategory(new DiffsCompCategoryID(DiffsCompCategories.Indexed_Tables_and_Columns, new byte[]{'I', 'n', 'd', 'e', 'x', 'e', 'd', '_', 'T', 'a', 'b', 'l', 'e', 's', '_', 'a', 'n', 'd', '_', 'C', 'o', 'l', 'u', 'm', 'n', 's', }, false/*isName*/), 
																new DiffsCompCategoryID(DiffsCompCategories.Indexed_Tables_and_Columns, new byte[]{'I', 'n', 'd', 'e', 'x', 'e', 'd', ' ', 'T', 'a', 'b', 'l', 'e', 's', ' ', '&', ' ', 'C', 'o', 'l', 'u', 'm', 'n', 's', }, true/*isName*/));

	/**
	* Returns a new array containing the vanilla change categories.
	* @see zz.ebs.dmcomp.DiffsCompCategories#NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES
	*/															
	public static final DiffsCompCategory[] getVanillas() {return new DiffsCompCategory[]{Object_Definition_Differences, Indexed_Tables_and_Columns}; }
	
	/**
	* Copies the vanilla change categories into the supplied array.
	* @see zz.ebs.dmcomp.DiffsCompCategories#NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES
	*/
	public static final void getVanillas(final DiffsCompCategory[] dest, int destOff) {
		//return new DiffsCompCategory[]{Object_Definition_Differences, Indexed_Tables_and_Columns}; 
		dest[destOff++] = Object_Definition_Differences;
		dest[destOff] = Indexed_Tables_and_Columns;
	}
	
	public static final byte workoutDiffsCompCategory(final String compMthdNameOrCode, final boolean isName) {
		//NOTE: if the id is a name in case of no match with names, we try codes
		if (isName) {
			if (Object_Definition_Differences.name.equals(compMthdNameOrCode)) return Object_Definition_Differences.name.number;
			else if (Indexed_Tables_and_Columns.name.equals(compMthdNameOrCode)) return Indexed_Tables_and_Columns.name.number;
		}
		else if (Object_Definition_Differences.code.equals(compMthdNameOrCode)) return Object_Definition_Differences.code.number;
		else if (Indexed_Tables_and_Columns.code.equals(compMthdNameOrCode)) return Indexed_Tables_and_Columns.code.number;
		return 0;
	}
	
	public static DiffsCompCategory get_DiffsCompCategory(final short categoryNum) {
		return categoryNum == Indexed_Tables_and_Columns.name.number ? Indexed_Tables_and_Columns : Object_Definition_Differences;
	}
	
}