package zz.ebs.dmcomp;

import string.String;
import string.CString;

import zz.ebs.eBSProduct;

/**
* Class for providing support for data model (compared) objects.<br>
* 
* @author Marc E. KAMGA
* @version 1.0
* @author Marc E. KAMGA
*/
public class DMObject extends Thing implements IDMChildObjectTypes, java.io.Serializable, Comparable<DMObject> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x0208869381000000L;
	
	public static final DMObject[] EMPTY_ARRAY = new DMObject[0];
	
	/**
	* The sequence for this class - didn't create a lock as the class is used in single-thread environments only for now.
	*/
	private static int sequence = 0; //NOTE: 
	
	static final int nextSequenceValue() {
		return ++sequence;
	}
	static final int curentSequenceValue() {
		return sequence;
	}
	
	protected String owner;
	protected String name;
	protected byte summaryCategory;
	protected eBSProduct ownerProduct;
	//protected int objectNumber; //NOTE: reuse field number inherited from super class as of 2017-04-25
	/*protected */DMObject next;
	boolean alreadyWritten; //since 2017-06-21 - added because it is the simpler way for fixing issue of objects written more than once in case of multiple-inputs-into-a-single-output, given that the set of DM objects is a hash-set
	
	/**
	* Constructor.<br>
	*/
	protected DMObject() {
		this(String.EMPTY);
	}
	/**
	* Constructor.<br>
	*/
	protected DMObject(final String name) {
		this.owner = String.EMPTY;
		this.name = name;
		this.alreadyWritten = false; //since 2017-06-21
	}
	/**
	* Constructor.<br>
	*/
	protected DMObject(String name, eBSProduct ownerProduct) {
		this(name, ownerProduct.code, ownerProduct);
	}
	/**
	* Constructor.<br>
	*/
	protected DMObject(String name, String owner, eBSProduct ownerProduct) {
		this.name = name;
		this.owner = owner;
		this.ownerProduct = ownerProduct;
		this.alreadyWritten = false; //since 2017-06-21
	}
	protected DMObject(byte summaryCategory, String name, String owner, eBSProduct ownerProduct) {
		this.summaryCategory = summaryCategory;
		this.name = name;
		this.owner = owner;
		this.ownerProduct = ownerProduct;
		this.alreadyWritten = false; //since 2017-06-21
	}
	/**
	* Constructor.<br>
	*/
	protected DMObject(byte summaryCategory, String name, eBSProduct ownerProduct) {
		this(summaryCategory, name, ownerProduct.code, ownerProduct);
	}
	/**
	* Gets the name of the object.<br>
	*/
	public String getName() {return name; }
	/**
	* Gets the owner of the object.<br>
	*/
	public String getOwner() {return owner; }
	/**
	* Gets the owner product of this <code>DMObject</code>.
	*/
	public eBSProduct getOwnerProduct() {return ownerProduct; }
	/**
	* Gets the number of the summary category of the object.<br>
	*/
	public byte getSummaryCategory() {return summaryCategory; }
	/**
	* Gets the code of the summary category of the object.<br>
	*/
	public String getSummaryCategoryCode() {return DMCompSummaryCategories.get_CategoryName(summaryCategory); } //since 2017-05-03
	/**
	* Gets the code of the summary category of the object.<br>
	*/
	public final String getSummaryCategoryShortCode() {return DMCompSummaryCategoriesUtil.get_ShortCode(summaryCategory); } //since 2017-06-21
	
	/**
	* Gets the number of the object.<br>
	*/
	public int getNumber() {
		return number; //objectNumber; 
	}
	
	/**
	* Tells if this <code>DMObject</code> is a child object.<br>
	*/
	public boolean isChildObject() {
		return false;
	}
	/**
	* Returns the number of the child object type of this <code>DMObject</code>.<br>
	*/
	public byte getChildObjectType() {
		return NOT_A_CHILD_OBJECT_TYPE;
	}
	/**
	* Returns the name of the parent object of this object, if any.
	*/
	public String getParentObjectName() {return String.EMPTY; }
	/**
	* Returns the parent object of this <code>DMObject</code>, if any.
	*/
	public DMObject getParentObject() {return null; }
	
	/**
	* Gets the modifier/qualifier of the child object.<br>
	* @return empty string if the child object does not have a modifier/qualifier; the modifier/qualifier of the child object, otherwise.
	*/
	public String getChildObjectModifier() {
		return String.EMPTY;
	}
	
	/**
	* Compares this <code>DMObject</code> and that supplied, for order.
	*/
	public byte compare(final DMObject other) {
		if (this == other) return 0;
		byte cmp = name.compareIgnoreAsciiCase(other.name);
		if (cmp != (byte)0) {
			return cmp;
		}
		else if (isChildObject()) {
			if (!other.isChildObject()) {
				cmp = owner.compareIgnoreAsciiCase(other.owner);
				if (cmp != (byte)0) {
					return cmp;
				}
				return 1;
			}
			cmp = owner.compareIgnoreAsciiCase(other.owner);
			if (cmp != (byte)0) {
				return cmp;
			}
			else if (summaryCategory != other.summaryCategory) {
				return summaryCategory < other.summaryCategory ? (byte)-1 : (byte)1;
			}
			cmp = getChildObjectModifier().compareIgnoreAsciiCase(other.getChildObjectModifier());
			if (cmp != (byte)0) {
				return cmp;
			}
			cmp = getChildObjectType(); //
			if (cmp/*thisChildObjectType*/ != other.getChildObjectType()) {
				return cmp/*thisChildObjectType*/ < other.getChildObjectType() ? (byte)-1 : (byte)1;
			}
			return getParentObjectName().compareIgnoreAsciiCase(other.getParentObjectName());
		}
		else if (other.isChildObject()) {
			cmp = owner.compareIgnoreAsciiCase(other.owner);
			if (cmp != (byte)0) {
				return cmp;
			}
			else if (summaryCategory != other.summaryCategory) {
				return summaryCategory < other.summaryCategory ? (byte)-1 : (byte)1;
			}
			return -1;
		}
		cmp = owner.compareIgnoreAsciiCase(other.owner);
		if (cmp != (byte)0) {
			return cmp;
		}
		else if (summaryCategory != other.summaryCategory) {
			return summaryCategory < other.summaryCategory ? (byte)-1 : (byte)1;
		}
		return 0;
	}
	/**
	* Compares this <code>DMObject</code> and that supplied, for order.
	*/
	public final int compareTo(final DMObject other) {
		return compare(other);
	}
	/**
	* Compares this <code>DMObject</code> and that supplied, for equality.
	*/
	public boolean equals(final DMObject other) {
		if (this == other) return true;
		if (summaryCategory != other.summaryCategory || 
				isChildObject() != other.isChildObject() || 
					!name.equalsIgnoreAsciiCase(other.name) || 
						!owner.equalsIgnoreAsciiCase(other.owner)) {
			return false;
		}
		else if (!isChildObject()) {
			return true;
		}
		return getChildObjectType() == other.getChildObjectType() && 
					getParentObjectName().equalsIgnoreAsciiCase(other.getParentObjectName()) && 
					getChildObjectModifier().equalsIgnoreAsciiCase(other.getChildObjectModifier())
					;
	}
	/**
	* Compares this <code>DMObject</code> and that supplied, for equality.
	*/
	public boolean equals(java.lang.Object other) {
		return other instanceof DMObject && equals((DMObject)other);
	}
	/**
	* Computes the hash code of this <code>DMObject</code>.<br>
	*/
	public int hashCode() {
		return name.hashCode() ^ owner.hashCode();
	}
	/**
	* {@inheritDoc}
	*/
	public final int getOutputNumber() {
		return (number < 0 ? (number = nextSequenceValue()) : number);
	}
	
	/**
	* {@inheritDoc}
	*/
	public void getText(final boolean useTabIndentInLieuOfSpaceIndent, final string.CharBuffer outputBuffer) {
		DMCompReportThing2StrUtil.getToYAMLStringChars(this, 0/*indents*/, true/*includeKindInfo*/, useTabIndentInLieuOfSpaceIndent, outputBuffer);
	}
	
	/*public */DMObject newChildObject(final String name, final byte childObjType, final String childObjectModifier) {
		return new DMChildObject(name, childObjType, childObjectModifier == null ? String.EMPTY : childObjectModifier);
	}
	/*public */DMObject newChildObject(final String name, final byte childObjType) {
		return new DMChildObject(name, childObjType);
	}
	
	/**
	* Nested class for providing support for child objects.<br>
	*
	* @author Marc E. KAMGA
	* @version 1.0
	* @copyright Marc E. KAMGA
	*/
	public /*static */class DMChildObject extends DMObject {

		/**The class's serial version UID. */
		public static final long serialVersionUID = 0x0209533171B6928AL;
		
		protected byte childObjType;
		protected String childObjectModifier;
		
		/**
		* Constructor.<br>
		*/
		DMChildObject(final String name, final byte childObjType, String childObjectModifier) {
			super(name);
			this.childObjType = childObjType;
			this.owner = DMObject.this.owner;
			this.summaryCategory = DMObject.this.summaryCategory;
			this.ownerProduct = DMObject.this.ownerProduct;
			this.childObjectModifier = childObjectModifier;
		}
		/**
		* Constructor.<br>
		*/
		DMChildObject(final String name, final byte childObjType) {
			this(name, childObjType, String.EMPTY);
		}
		
		/**
		* {@inheritDoc}
		* @return <code>true</code>
		*/
		public final boolean isChildObject() {
			return true;
		}
		/**
		* {@inheritDoc}
		*/
		public byte getChildObjectType() {
			return childObjType;
		}
		/**
		* {@inheritDoc}
		*/
		public String getChildObjectModifier() {return childObjectModifier; }
		/**
		* Returns the name of the parent object of this child object.
		*/
		public final String getParentObjectName() {return DMObject.this.getName(); }
		/**
		* Returns the parent object of this child <code>DMObject</code>.
		*/
		public final DMObject getParentObject() {return DMObject.this; }
		
	}

}