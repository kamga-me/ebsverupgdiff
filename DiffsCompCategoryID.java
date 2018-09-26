package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import string.CString;

/**
* Unbounded enumeration class for providing support for diffs comparison categories.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public class DiffsCompCategoryID extends string.SubClassableAsciiFullSizeString {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x0208339331B6928AL;
	
	public final byte number;
	public final boolean isName;
	
	DiffsCompCategoryID(final byte number, final byte[] data, final boolean isName) {
		super(data);
		this.number = number;
		this.isName = isName;
	}
	DiffsCompCategoryID(final DiffsCompCategoryID id, final boolean altIDType) {
		super(string.CFullSizeStringDataArrayGetter.getDataArray(id));
		this.number = id.number;
		this.isName = altIDType ? !id.isName : id.isName;
	}

	public final boolean isCode() {return !isName; }

	public final boolean isName() {return isName; }



}