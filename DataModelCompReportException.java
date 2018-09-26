package zz.ebs.dmcomp;

/**
* Class for reporting data model comparison report errors/exceptions.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public class DataModelCompReportException extends RuntimeException {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x0208035821B6928AL;
	
	/**
	* Constructor.<br> 
	*/
	public DataModelCompReportException() {
		super();
	}
	/**
	* Constructor.<br> 
	*/
	public DataModelCompReportException(final java.lang.String errMsg) {
		super(errMsg);
	}
	/**
	* Constructor.<br> 
	*/
	public DataModelCompReportException(final java.lang.String errMsg, final java.lang.Throwable t) {
		super(errMsg, t);
	}


}