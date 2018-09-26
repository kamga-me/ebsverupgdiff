package zz.ebs.dmcomp;

import string.String;
import string.CString;
/*
import core.html.HTMLTag;
import core.html.HTMLTagNames;
import core.html.HTMLTemplate;
*/

/**
* Base class for providing support for Oracle eBS data model comparison report things/stuffs.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public abstract class Thing extends core.Thing implements java.io.Serializable {
	
	/**
	* Constant for things of kind <code>diffs-comparison-report</code>
	*/
	public static final byte DIFFS_COMPARISON_REPORT = 1;
	/**
	* Constant for things of kind <code>diffs-summary</code>
	*/
	public static final byte DIFFS_SUMMARY = 2;
	/**
	* Constant for things of kind <code>diffs-table</code>
	*/
	public static final byte DIFFS_TABLE = 3;
	/**
	* Constant for things of kind <code>diffs-object</code>
	*/
	public static final byte DIFFS_OBJECT = 4;
	/**
	* Constant for things of kind <code>diffs-table-row</code>
	*/
	public static final byte DIFFS_TABLE_ROW = 5;
	/**
	* Constant for things of kind <code>diffs-table-row-change</code>
	*/
	public static final byte DIFFS_TABLE_ROW_CHANGE = 6;
	/**
	* Constant for things of kind <code>diffs-detailed-change</code>
	*/
	public static final byte DIFFS_DETAILED_CHANGE = 7;
	/**
	* Constant for things of kind <code>diffs-detailed-change-path</code>
	*/
	public static final byte DIFFS_DETAILED_CHANGE_PATH = 8;
	/**
	* Constant for things of kind <code>diffs-source</code>
	*/
	public static final byte DIFFS_SOURCE = 9; //since 2017-06-21
	
	/**
	* Meant to holds the number allocated to this <code>Thing</code> - very useful when genrating .psv file data.<br>
	*/
	int number;

	/**
	* Constructor.<br>
	*/
	protected Thing() {
		super();
		this.number = -1; //-1 for not yet set
	}
	/**
	* Gets the kind of this thing.<br>
	* @return {@link #DIFFS_COMPARISON_REPORT DIFFS_COMPARISON_REPORT}, {@link #DIFFS_SUMMARY DIFFS_SUMMARY}, {@link #DIFFS_TABLE DIFFS_TABLE}, {@link #DIFFS_TABLE_ROW DIFFS_TABLE_ROW}, {@link #DIFFS_TABLE_ROW_CHANGE DIFFS_TABLE_ROW_CHANGE}, {@link #DIFFS_DETAILED_CHANGE DIFFS_DETAILED_CHANGE}, {@link #DIFFS_SOURCE DIFFS_SOURCE} or <code>0</code> (for unspecified)
	*/
	public byte getKind() {return 0; }
	
	/**
	* Copies the textual representation of this <code<Thing</code> into the supplied output character buffer.<br>
	*/
	public abstract void getText(boolean useTabIndentInLieuOfSpaceIndent, string.CharBuffer outputBuffer);
	
	protected int __getTextBufferInitialCapacity() {return 128;}
	
	/**
	* Returns the textual representation of this <code>
	*/
	public java.lang.String toString() {
		string.CharBuffer buffer = new string.CharBuffer(__getTextBufferInitialCapacity());
		getText(true/*useTabIndentInLieuOfSpaceIndent*/, buffer);
		return buffer.toString();
	}
	/**
	* Returns the output number of this <code>Thing</code>.
	*/
	public int getOutputNumber() {return number; }


}