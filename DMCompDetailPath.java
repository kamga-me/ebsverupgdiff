package zz.ebs.dmcomp;

import string.String;
import string.CString;

//import zz.ebs.eBSProduct;

/**
* Class for providing support for data model comparison detail change path objects.<br>
* 
* @author Marc E. KAMGA
* @version 1.0
* @author Marc E. KAMGA
*/
public class DMCompDetailPath extends Thing implements java.io.Serializable, Comparable<DMCompDetailPath> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x02088706C1000000L;
	
	public static final byte MAX_SZ = 7;
	
	public static final byte TWICE_MAX_SZ = MAX_SZ * 2;
	
	public static final byte TWICE_MAX_SZ_M2 = MAX_SZ * 2 - 2;
	
	/**
	* Constant for zero-size detail path.<br>
	*/
	public static final DMCompDetailPath EMPTY = new DMCompDetailPath(String.EMPTY_ARRAY, (byte)0);
	static {
		EMPTY.number = 0;
	}
	
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
	
	String[] nodes;
	byte sz;
	//int number; //NOTE: inherited from super class as of 2017-04-25
	
	DMCompDetailPath next;
	boolean alreadyWritten; //since 2017-06-21 - added because it is the simpler way for fixing issue of objects written more than once in case of multiple-inputs-into-a-single-output, given that the set of DM detail paths is a hash-set
	
	/**
	* @param nodes the array holding the node details - the length must be <code>sz * 2</code>
	* @param sz the number of nodes that make up the detail path
	*/
	DMCompDetailPath(final String[] nodes, final byte sz) {
		super();
		this.nodes = nodes;
		this.sz = sz;
		this.alreadyWritten = false; //since 2017-06-21
	}
	/**
	* {@inheritDoc}
	* @return {@link #DIFFS_DETAILED_CHANGE_PATH DIFFS_DETAILED_CHANGE_PATH}
	*/
	public final byte getKind() {return DIFFS_DETAILED_CHANGE_PATH; }
	
	/**
	* Tells if this <code>DMCompDetailPath</code> has a size of <code>0</code>.<br>
	*/
	public final boolean isEmpty() {
		return sz == 0;
	}
	
	/**
	* Returns the size of the path.
	* @return the number of nodes that make up the path.
	*/
	public final int size() {return sz; }
	
	/**
	* Gets the detail type of the <code>(nodexIndex + 1)<sup>th</sup></code> node of the path.<br>
	*/
	public final String type(int nodeIndex) {
		return nodes[nodeIndex << 1];
	}
	/**
	* Gets the name of the <code>(nodexIndex + 1)<sup>th</sup></code> node of the path.<br>
	* If the name is qualified, it ends with a suffix that is within parentheses.<br>
	*/
	public final String name(int nodeIndex) {
		return nodes[(nodeIndex << 1) + 1];
	}
	/**
	* Computes the offset of the <code>(nodexIndex + 1)<sup>th</sup></code> node of the path.<br>
	*/
	public final int nodeOffset(int nodeIndex) {
		return nodeIndex << 1;
	}
	/**
	* Gets the detail type of the node at the indicated offset.<br>
	* If the name is qualified, it ends with a suffix that is within parentheses.<br>
	*/
	public final String getType(int nodeOffset) {
		return nodes[nodeOffset];
	}
	/**
	* Gets the name of the node at the indicated offset.<br>
	* If the name is qualified, it ends with a suffix that is within parentheses.<br>
	*/
	public final String getName(int nodeOffset) {
		return nodes[nodeOffset + 1];
	}
	
	/**
	* @return <code>0x80000000</code> if the name ends with right parenthesis but does not have a left parenthesis; <code>-1</code> if the name is not qualified (does not have a qualifier associated with it); the index of the left parenthesis denoting the start of the qualifier, otherwise.
	*/
	public static final int checkIfQualifiedName(String nameNode) {
		if (nameNode.isEmpty()) return -1;
		int j = nameNode.length() - 1;
		if (nameNode.getChar(j) != ')') return -1;
		j--;
		for (;j>=0;j--)
		{
			if (nameNode.getChar(j) == '(') return j; //BUG-FIX - 2017-05-04 - was doing "if (nameNode.getChar(j) != '(') return j;"
		}
		return 0x80000000;
	}
	/**
	* Compares this <code>DMCompDetailPath</code> and that supplied, for order.<br>
	*/
	public final byte compare(final DMCompDetailPath other) {
		if (this == other) return 0;
		return compare(other.nodes, other.sz);
	}
	/**
	* Compares this <code>DMCompDetailPath</code> and that whose nodes are specified, for order.<br>
	*/
	public byte compare(final String[] otherNodes, int otherSz) {
		if (sz != otherSz) {
			return sz < otherSz ? (byte)-1 : (byte)1;
		}
		/*final int end*/otherSz = (sz & 0xFFFF) << 1;
		for (int i=0;i<otherSz/*end*/;i++) //BUG-FIX - 2017-04-05 - replaced "i < sz" with "i < end"
		{
			byte cmp = nodes[i].compare(otherNodes[i]);
			if (cmp != (byte)0) return cmp;
		}
		return 0;
	}
	/**
	* Compares this <code>DMCompDetailPath</code> and that supplied, for order.<br>
	*/
	public int compareTo(final DMCompDetailPath other) {
		return compare(other);
	}
	/**
	* Compares this <code>DMCompDetailPath</code> and that supplied, for equality.<br>
	*/
	public final boolean equals(DMCompDetailPath other) {
		if (this == other) return true;
		return equals(other.nodes, other.sz);
	}
	/**
	* Compares this <code>DMCompDetailPath</code> and that whose nodes are specified, for equality.<br>
	*/
	public boolean equals(String[] otherNodes, int otherSz) {
		if (sz != otherSz) {
			return false;
		}
		/*final int end*/otherSz = (sz & 0xFFFF) << 1;
		for (int i=0;i<otherSz/*end*/;i++) //BUG-FIX - 2017-04-05 - replaced "i < sz" with "i < end"
		{
			if (!nodes[i].equals(otherNodes[i])) return false;
		}
		return true;
	}
	/**
	* Compares this <code>DMCompDetailPath</code> and that supplied, for equality.<br>
	*/
	public boolean equals(java.lang.Object other) {
		return other instanceof DMCompDetailPath && equals((DMCompDetailPath)other);
	}
	
	/**
	* Computes the hash code of this <code>DMCompDetailPath</code>.<br>
	*/
	public int hashCode() {
		int h = 0;
		final int end = (sz & 0xFFFF) << 1;
		for (int i=0;i<end;i++) //BUG-FIX - 2017-04-05 - replaced "i < sz" with "i < end"
		{
			String node = nodes[i];
			int nodeLen = node.length();
			for (int j=0;j<nodeLen;j++)
			{
				h *= 31;
				h += node.getChar(j);
			}
		}
		return h;
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

}