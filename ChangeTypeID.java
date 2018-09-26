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
public class ChangeTypeID extends string.SubClassableAsciiFullSizeString {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x0208302D71B6928AL;
	
	public static final ChangeTypeID NO_VALUE_CODE = new ChangeTypeID((short)0, (byte)0, empty_byte_array, false/*isName*/);
	public static final ChangeTypeID NO_VALUE_NAME = new ChangeTypeID((short)0, (byte)0, empty_byte_array, true/*isName*/);
	
	public final short/*byte*/ number; //NOTE: type changed to short on 2017-Apr-18
	public final byte changeCategory;
	public final boolean isName;
	
	ChangeTypeID(final short/*byte*/ number, final byte changeCategory, final byte[] data, final boolean isName) {
		super(data);
		this.number = number;
		this.changeCategory = changeCategory;
		this.isName = isName;
	}
	ChangeTypeID(final ChangeTypeID id, final boolean altIDType) {
		super(string.CFullSizeStringDataArrayGetter.getDataArray(id));
		this.number = id.number;
		this.changeCategory = id.changeCategory;
		this.isName = altIDType ? !id.isName : id.isName;
	}

	public final boolean isCode() {return !isName; }

	public final boolean isName() {return isName; }


}