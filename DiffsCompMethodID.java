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
public class DiffsCompMethodID extends string.SubClassableAsciiFullSizeString {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x02083390C1B6928AL;
	
	public static final DiffsCompMethodID NO_VALUE_CODE = new DiffsCompMethodID((short)0, (byte)0, empty_byte_array, false/*isName*/);
	public static final DiffsCompMethodID NO_VALUE_NAME = new DiffsCompMethodID((short)0, (byte)0, empty_byte_array, true/*isName*/);
	
	public final short/*byte*/ number;
	public final byte diffsCompCategory;
	public final boolean isName;
	
	DiffsCompMethodID(final short/*byte*/ number, final byte diffsCompCategory, final byte[] data, final boolean isName) {
		super(data);
		this.number = number;
		this.diffsCompCategory = diffsCompCategory;
		this.isName = isName;
	}
	DiffsCompMethodID(final DiffsCompMethodID id, final boolean altIDType) {
		super(string.CFullSizeStringDataArrayGetter.getDataArray(id));
		this.number = id.number;
		this.diffsCompCategory = id.diffsCompCategory;
		this.isName = altIDType ? !id.isName : id.isName;
	}
	
	DiffsCompMethodID(final short/*byte*/ number, final DiffsCompCategoryID diffsCompCat) {
		super(string.CFullSizeStringDataArrayGetter.getDataArray(diffsCompCat));
		this.diffsCompCategory = diffsCompCat.number;
		this.number = number;
		this.isName = diffsCompCat.isName;
	}

	public final boolean isCode() {return !isName; }

	public final boolean isName() {return isName; }


}