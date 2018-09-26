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
public class ChangeCategoryID extends string.SubClassableAsciiFullSizeString {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x0208308F81B6928AL;
	
	public final byte number;
	public final boolean isName;
	
	ChangeCategoryID(final byte number, final byte[] data, final boolean isName) {
		super(data);
		this.number = number;
		this.isName = isName;
	}
	ChangeCategoryID(final ChangeCategoryID id, final boolean altIDType) {
		super(string.CFullSizeStringDataArrayGetter.getDataArray(id));
		this.number = id.number;
		this.isName = altIDType ? !id.isName : id.isName;
	}

	public final boolean isCode() {return !isName; }

	public final boolean isName() {return isName; }



}