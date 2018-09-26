package zz.ebs.dmcomp;

import string.String;
import string.CString;

import zz.ebs.eBSProduct;

/**
* Class for providing support for data model sources.<br>
* 
* @author Marc E. KAMGA
* @version 1.0
* @author Marc E. KAMGA
*/
public class DMSource extends Thing implements IDMChildObjectTypes, java.io.Serializable, Comparable<DMSource> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x020DCF07E1B6928AL;
	
	/**
	* Empty array.<br>
	*/
	public static final DMSource[] EMPTY_ARRAY = new DMSource[0];
	
	/**
	* Constant for <code>file</code> source type.<br>
	*/
	public static final byte FILE_SOURCE = 1;
	/**
	* Constant for <code>url</code> source type.<br>
	*/
	public static final byte URL_SOURCE = 2;
	/**
	* Constant for <code>database-table</code> source type.<br>
	*/
	public static final byte DB_TABLE_SOURCE = 3;

	/**
	* The identifier of the source.<br> 
	*/
	public final String ID;
	public final boolean isPrimarySource;
	public final byte sourceType;
	/**
	* The associated product: the product of the report where a reference to the secondary source was first spotted.<br>
	*/
	eBSProduct product;
	
	/**
	* Constructor.<br>
	*/
	DMSource(final String ID, final byte sourceType, final boolean isSecondarySource) {
		super();
		this.ID = ID;
		this.isPrimarySource = !isSecondarySource;
		this.sourceType = sourceType;
		this.product = eBSProduct.NO_PRODUCT;
	}
	/**
	* Constructor.<br>
	*/
	DMSource(final String ID, final byte sourceType) {
		this(ID, sourceType, false/*isSecondarySource*/);
	}
	/**
	* Constructor.<br>
	*/
	DMSource(final String ID) {
		this(ID, FILE_SOURCE/*sourceType*/);
	}
	/**
	* Compares this <code>DMSource</code> and that supplied, for identifier order.
	*/
	public final byte compare(final DMSource other) {
		return ID.compare(other.ID);
	}
	/**
	* Compares this <code>DMSource</code> and that supplied, for identifier order.
	*/
	public final int compareTo(final DMSource other) {
		return compare(other);
	}
	/**
	* Compares this <code>DMSource</code> and that supplied, for identifier order.
	*/
	public final byte compare(final core.Thing other) {
		if (!(other instanceof DMSource)) {
			return super.compare(other);
		}
		return compare((DMSource)other);
	}
	/**
	* Compares this <code>DMSource</code> and that supplied, for identifier equality.
	*/
	public final boolean equals(final DMSource other) {
		return ID.equals(other.ID);
	}
	/**
	* Compares this <code>DMSource</code> and that supplied, for identifier equality.
	*/
	public final boolean equals(final core.Thing other) {
		return other instanceof DMSource && equals((DMSource)other);
	}
	/**
	* Compares this <code>DMSource</code> and that supplied, for identifier equality.
	*/
	public final boolean equals(final java.lang.Object other) {
		return other instanceof DMSource && equals((DMSource)other);
	}
	/**
	* Computes the hash code of this <code>DMSource</code>.<br>
	*/
	public int hashCode() {
		return ID.hashCode();
	}
	
	public final byte getSourceType() {
		return sourceType;
	}
	
	/**
	* Tells if the source is a file.<br>
	* @return (sourceType == FILE_SOURCE || sourceType == URL_SOURCE)
	*/
	public boolean isFile() {
		return sourceType == FILE_SOURCE || sourceType == URL_SOURCE; 
	}
	
	/**
	* {@inheritDoc}
	*/
	public void getText(boolean useTabIndentInLieuOfSpaceIndent, string.CharBuffer outputBuffer) {
		outputBuffer.appendChars(ID);
	}

	/**
	* Gets the output number of the source.<br>
	*/
	public final int getOutputNumber() {
		return number < 1 ? (number = DataModelComparisonReport.nextSourceFileNumSeqValue()) : number;
	}

	static final DMSource fromSourceUrl(final String sourceURL, final boolean forSureSecondarySource) {
		int index = sourceURL.lastIndexOf('#');
		return index < 0 ? new DMSource(sourceURL, URL_SOURCE, forSureSecondarySource/*isSecondarySource*/) : 
				new DMSource(sourceURL.left(index), URL_SOURCE, true/*isSecondarySource*/)
				;
	}

}