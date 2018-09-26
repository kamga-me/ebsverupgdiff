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
public class ChangeCategory implements java.io.Serializable, Comparable<ChangeCategory> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x0208338CB1B6928AL;
	
	/**
	* Empty array.
	*/
	public static final ChangeCategory[] EMPTY_ARRAY = new ChangeCategory[0];
	
	
	public final ChangeCategoryID code;
	public final ChangeCategoryID name;
	
	ChangeCategory(final ChangeCategoryID code, final ChangeCategoryID name) {
		super();
		this.code = code;
		this.name = name;
	}
	ChangeCategory(final ChangeCategoryID code) {
		this(code, new ChangeCategoryID(code, true/*altIDType*/));
	}
	/**
	* Gets the short code of this <code>ChangeType</code>.<br>
	*/
	public final String getShortCode() { //since 2017-06-21
		return ChangeCategories.getShortCode(code.number);
	}
	
	/**
	* Compares this <code>ChangeCategory</code> to that supplied, for order.
	*/
	public final int compareTo(final ChangeCategory other) {
		return code.number == other.code.number ? (byte)0 : 
				code.number < other.code.number ? (byte)-1 : (byte)0;
	}
	/**
	* Compares this <code>ChangeCategory</code> to that supplied, for (number) equality.
	*/
	public final boolean equals(final ChangeCategory other) {
		return code.number == other.code.number;
	}
	/**
	* Compares this <code>ChangeCategory</code> to that supplied, for (number) equality.
	*/
	public final boolean equals(final java.lang.Object other) {
		return other instanceof ChangeCategory && code.number == ((ChangeCategory)other).code.number;
	}

	/**
	* {@inheritDoc}
	* @return <code>this.code.toString()</code>
	*/
	public java.lang.String toString() {return code.toString(); }


	
	/**
	* Constant for <code>Added</code> change category.<br>
	*/
	public static final ChangeCategory Added = new ChangeCategory(new ChangeCategoryID(ChangeCategories.Added, new byte[]{'A', 'd', 'd', 'e', 'd'}, false/*isName*/));
	/**
	* Constant for <code>Removed</code> change category.<br>
	*/
	public static final ChangeCategory Removed = new ChangeCategory(new ChangeCategoryID(ChangeCategories.Removed, new byte[]{'R', 'e', 'm', 'o', 'v', 'e', 'd'}, false/*isName*/));
	/**
	* Constant for <code>Attribute_Changes</code> change category.<br>
	*/
	public static final ChangeCategory Attribute_Changes = new ChangeCategory(new ChangeCategoryID(ChangeCategories.Attribute_Changes, new byte[]{'A', 't', 't', 'r', 'i', 'b', 'u', 't', 'e', '_', 'C', 'h', 'a', 'n', 'g', 'e', 's'}, false/*isName*/), 
																new ChangeCategoryID(ChangeCategories.Attribute_Changes, new byte[]{'A', 't', 't', 'r', 'i', 'b', 'u', 't', 'e', ' ', 'C', 'h', 'a', 'n', 'g', 'e', 's'}, true/*isName*/));
	/**
	* Constant for <code>Objects_Added</code> change category.<br>
	*/
	public static final ChangeCategory Objects_Added = new ChangeCategory(new ChangeCategoryID(ChangeCategories.Objects_Added, new byte[]{'O', 'b', 'j', 'e', 'c', 't', 's', '_', 'A', 'd', 'd', 'e', 'd'}, false/*isName*/), 
																new ChangeCategoryID(ChangeCategories.Objects_Added, new byte[]{'O', 'b', 'j', 'e', 'c', 't', 's', ' ', 'A', 'd', 'd', 'e', 'd'}, true/*isName*/));
	/**
	* Constant for <code>Objects_Removed</code> change category.<br>
	*/
	public static final ChangeCategory Objects_Removed = new ChangeCategory(new ChangeCategoryID(ChangeCategories.Objects_Removed, new byte[]{'O', 'b', 'j', 'e', 'c', 't', 's', '_', 'R', 'e', 'm', 'o', 'v', 'e', 'd'}, false/*isName*/), 
																new ChangeCategoryID(ChangeCategories.Objects_Removed, new byte[]{'O', 'b', 'j', 'e', 'c', 't', 's', ' ', 'R', 'e', 'm', 'o', 'v', 'e', 'd'}, true/*isName*/));

	/**
	* Returns a new array containing the vanilla change categories.
	* @see zz.ebs.dmcomp.ChangeCategories#NUMBER_OF_VANILLA_CHG_CATEGORIES NUMBER_OF_VANILLA_CHG_CATEGORIES
	*/															
	public static final ChangeCategory[] getVanillas() {return new ChangeCategory[]{Added, Removed, Attribute_Changes, Objects_Added, Objects_Removed}; }
	
	/**
	* Copies the vanilla change categories into the supplied array.
	* @see zz.ebs.dmcomp.ChangeCategories#NUMBER_OF_VANILLA_CHG_CATEGORIES NUMBER_OF_VANILLA_CHG_CATEGORIES
	*/
	public static final void getVanillas(final ChangeCategory[] dest, int destOff) {
		//return new ChangeCategory[]{Added, Removed, Attribute_Changes, Objects_Added, Objects_Removed}; 
		dest[destOff++] = Added;
		dest[destOff++] = Removed;
		dest[destOff++] = Attribute_Changes;
		dest[destOff++] = Objects_Added;
		dest[destOff] = Objects_Removed;
	}
	
	/**
	* Works the change type category for the specified change type.
	* @return <code>0</code> if failed to resolve change type category from the specified change type.
	*/
	public static final byte workoutCategoryNumber(final String changeTypeID, final boolean isChangeTypeName) {
		if (changeTypeID.isEmpty()) return 0;
		switch(changeTypeID.getFirstChar())
		{
		case 'A':
			if (changeTypeID.startsWith(Added.code)) {
				return Added.code.number;
			}
			if (isChangeTypeName) {
				if (changeTypeID.startsWith(Attribute_Changes.name) || changeTypeID.startsWith(Attribute_Changes.code)) {
					return Attribute_Changes.name.number;
				}
				else if (ChangeType.Attribute_Changes__between_12_1_3_and_12_2_6.equals(changeTypeID)) {
					return Attribute_Changes.name.number;
				}
			}
			else {
				if (changeTypeID.startsWith(Attribute_Changes.code)) {
					return Attribute_Changes.code.number;
				}
			}
			return 0;
		case 'R':
			if (changeTypeID.startsWith(Removed.code)) {
				return Removed.code.number;
			}
			return  0;
		case 'O':
			if (isChangeTypeName) {
				if (changeTypeID.startsWith(Objects_Added.name) || changeTypeID.startsWith(Objects_Added.code)) {
					return Objects_Added.name.number;
				}
				else if (changeTypeID.startsWith(Objects_Removed.name) || changeTypeID.startsWith(Objects_Removed.code)) {
					return Objects_Removed.name.number;
				}
			}
			else {
				if (changeTypeID.startsWith(Objects_Added.code)) {
					return Objects_Added.code.number;
				}
				else if (changeTypeID.startsWith(Objects_Removed.code)) {
					return Objects_Removed.code.number;
				}
			}
			return 0;
		default:
			return 0;
		}
	}
	

}