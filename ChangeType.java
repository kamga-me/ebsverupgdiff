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
public class ChangeType implements java.io.Serializable, Comparable<ChangeType> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x02083078C1B6928AL;
	
	/**
	* Constant for signalling the absence of value, which also referred to as unspecified.<br>
	*/
	public static final ChangeType NO_VALUE = new ChangeType(ChangeTypeID.NO_VALUE_CODE, ChangeTypeID.NO_VALUE_NAME);
	
	/**
	* Constant for <code>&quot;Attribute Changes  between 12.1.3 and 12.2.6&quot;</code> which is a synonym for <code>&quot;Attribute Changes between 12.1.3 and 12.2.6&quot;</code>.<br>
	*/
	public static final String Attribute_Changes__between_12_1_3_and_12_2_6 = CString.valueOf(new byte[]{'A', 't', 't', 'r', 'i', 'b', 'u', 't', 'e', ' ', 'C', 'h', 'a', 'n', 'g', 'e', 's', ' ', ' ', 'b', 'e', 't', 'w', 'e', 'e', 'n', ' ', '1', '2', '.', '1', '.', '3', ' ', 'a', 'n', 'd', ' ', '1', '2', '.', '2', '.', '6'}, true, true);
	
	
	public static final String __Click_here_for_complete_changes__ = CString.valueOf(new byte[]{'C', 'l', 'i', 'c', 'k', ' ', 'h', 'e', 'r', 'e', ' ', 'f', 'o', 'r', ' ', 'c', 'o', 'm', 'p', 'l', 'e', 't', 'e', ' ', 'c', 'h', 'a', 'n', 'g', 'e', 's'}, true, true);
	public static final String __Click_here_for_complete_changes_SHORTHAND = CString.valueOf(new byte[]{'_', 'C', 'h', '4', 'C', 'C'}, true, true);
	
	/**
	* Empty array.
	*/
	public static final ChangeType[] EMPTY_ARRAY = new ChangeType[0];
	
	public final ChangeTypeID code;
	public final ChangeTypeID name;
	
	ChangeType(final ChangeTypeID code, final ChangeTypeID name) {
		super();
		this.code = code;
		this.name = name;
	}
	
	/**
	* Gets the short code of this <code>ChangeType</code>.<br>
	*/
	public final String getShortCode() { //since 2017-06-21
		return ChangeTypes.getShortCode(code.number);
	}
	
	
	/**
	* Compares this <code>ChangeType</code> to that supplied, for order.
	*/
	public final int compareTo(final ChangeType other) {
		return code.number == other.code.number ? (byte)0 : 
				code.number < other.code.number ? (byte)-1 : (byte)0;
	}
	/**
	* Compares this <code>ChangeType</code> to that supplied, for (number) equality.
	*/
	public final boolean equals(final ChangeType other) {
		return code.number == other.code.number;
	}
	/**
	* Compares this <code>ChangeType</code> to that supplied, for (number) equality.
	*/
	public final boolean equals(final java.lang.Object other) {
		return other instanceof ChangeType && code.number == ((ChangeType)other).code.number;
	}

	/**
	* {@inheritDoc}
	* @return <code>this.code.toString()</code>
	*/
	public java.lang.String toString() {return code.toString(); }

	
	/**
	* Constant for <code>&quot;Added in 12.2.6&quot;</code> change category.<br>
	*/
	public static final ChangeType Added_in_12_2_6 = new ChangeType(new ChangeTypeID(ChangeTypes.Added_in_12_2_6, ChangeCategories.Added, new byte[]{'A', 'd', 'd', 'e', 'd', '_', 'i', 'n', '_', '1', '2', '_', '2', '_', '6'}, false/*isName*/), 
															new ChangeTypeID(ChangeTypes.Added_in_12_2_6, ChangeCategories.Added, new byte[]{'A', 'd', 'd', 'e', 'd', ' ', 'i', 'n', ' ', '1', '2', '.', '2', '.', '6'}, true/*isName*/));
	/**
	* Constant for <code>&quot;Removed in 12.2.6&quot;</code> change category.<br>
	*/
	public static final ChangeType Removed_in_12_2_6 = new ChangeType(new ChangeTypeID(ChangeTypes.Removed_in_12_2_6, ChangeCategories.Removed, new byte[]{'R', 'e', 'm', 'o', 'v', 'e', 'd', '_', 'i', 'n', '_', '1', '2', '_', '2', '_', '6'}, false/*isName*/), 
															new ChangeTypeID(ChangeTypes.Removed_in_12_2_6, ChangeCategories.Removed, new byte[]{'R', 'e', 'm', 'o', 'v', 'e', 'd', ' ', 'i', 'n', ' ', '1', '2', '.', '2', '.', '6'}, true/*isName*/));
	/**
	* Constant for <code>&quot;Attribute_Changes_between_12.1.3 and 12.2.6&quot;</code> change category.<br>
	*/
	public static final ChangeType Attribute_Changes_between_12_1_3_and_12_2_6 = new ChangeType(new ChangeTypeID(ChangeTypes.Attribute_Changes_between_12_1_3_and_12_2_6, ChangeCategories.Attribute_Changes, new byte[]{'A', 't', 't', 'r', 'i', 'b', 'u', 't', 'e', '_', 'C', 'h', 'a', 'n', 'g', 'e', 's', '_', 'b', 'e', 't', 'w', 'e', 'e', 'n', '_', '1', '2', '_', '1', '_', '3', '_', 'a', 'n', 'd', '_', '1', '2', '_', '2', '_', '6'}, false/*isName*/), 
																new ChangeTypeID(ChangeTypes.Attribute_Changes_between_12_1_3_and_12_2_6, ChangeCategories.Attribute_Changes, new byte[]{'A', 't', 't', 'r', 'i', 'b', 'u', 't', 'e', ' ', 'C', 'h', 'a', 'n', 'g', 'e', 's', ' ', 'b', 'e', 't', 'w', 'e', 'e', 'n', ' ', '1', '2', '.', '1', '.', '3', ' ', 'a', 'n', 'd', ' ', '1', '2', '.', '2', '.', '6'}, true/*isName*/));
	/**
	* Constant for <code>&quot;Objects Added in 12.2.6&quot;</code> change category.<br>
	*/
	public static final ChangeType Objects_Added_in_12_2_6 = new ChangeType(new ChangeTypeID(ChangeTypes.Objects_Added_in_12_2_6, ChangeCategories.Objects_Added, new byte[]{'O', 'b', 'j', 'e', 'c', 't', 's', '_', 'A', 'd', 'd', 'e', 'd', '_', 'i', 'n', '_', '1', '2', '_', '2', '_', '6'}, false/*isName*/), 
																new ChangeTypeID(ChangeTypes.Objects_Added_in_12_2_6, ChangeCategories.Objects_Added, new byte[]{'O', 'b', 'j', 'e', 'c', 't', 's', ' ', 'A', 'd', 'd', 'e', 'd', ' ', 'i', 'n', ' ', '1', '2', '.', '2', '.', '6'}, true/*isName*/));
	/**
	* Constant for <code>&quot;Objects Removed in 12.2.6&quot;</code> change category.<br>
	*/
	public static final ChangeType Objects_Removed_in_12_2_6 = new ChangeType(new ChangeTypeID(ChangeTypes.Objects_Removed_in_12_2_6, ChangeCategories.Objects_Removed, new byte[]{'O', 'b', 'j', 'e', 'c', 't', 's', '_', 'R', 'e', 'm', 'o', 'v', 'e', 'd', '_', 'i', 'n', '_', '1', '2', '_', '2', '_', '6'}, false/*isName*/), 
																new ChangeTypeID(ChangeTypes.Objects_Removed_in_12_2_6, ChangeCategories.Objects_Removed, new byte[]{'O', 'b', 'j', 'e', 'c', 't', 's', ' ', 'R', 'e', 'm', 'o', 'v', 'e', 'd', ' ', 'i', 'n', ' ', '1', '2', '.', '2', '.', '6'}, true/*isName*/));

	/**
	* Returns a new array containing the vanilla change categories.
	* @see zz.ebs.dmcomp.ChangeTypes#NUMBER_OF_VANILLA_CHANGE_TYPES NUMBER_OF_VANILLA_CHANGE_TYPES
	*/															
	public static final ChangeType[] getVanillas() {return new ChangeType[]{Added_in_12_2_6, Removed_in_12_2_6, Attribute_Changes_between_12_1_3_and_12_2_6, Objects_Added_in_12_2_6, Objects_Removed_in_12_2_6}; }
	
	/**
	* Copies the vanilla change categories into the supplied array.
	* @see zz.ebs.dmcomp.ChangeTypes#NUMBER_OF_VANILLA_CHANGE_TYPES NUMBER_OF_VANILLA_CHANGE_TYPES
	*/
	public static final void getVanillas(final ChangeType[] dest, int destOff) {
		//return new ChangeType[]{Added_in_12_2_6, Removed_in_12_2_6, Attribute_Changes_between_12_1_3_and_12_2_6, Objects_Added_in_12_2_6, Objects_Removed_in_12_2_6}; 
		dest[destOff++] = Added_in_12_2_6;
		dest[destOff++] = Removed_in_12_2_6;
		dest[destOff++] = Attribute_Changes_between_12_1_3_and_12_2_6;
		dest[destOff++] = Objects_Added_in_12_2_6;
		dest[destOff] = Objects_Removed_in_12_2_6;
	}
	


}