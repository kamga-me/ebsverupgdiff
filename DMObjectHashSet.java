package zz.ebs.dmcomp;

import string.String;
import string.CString;

import core.collection.HashSet;
import core.collection.LinkedElementsHashSet;

/**
* Class for providing support for hash-sets of {@link DMObject DMObject} elements.<br>
* 
* @author Marc E. KAMGA
* @version 1.0
* @author Marc E. KAMGA
*/
public /*final */class DMObjectHashSet extends LinkedElementsHashSet<DMObject> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x02088B7471B6928AL;
	
	/**
	* Constructor.<br>
	*/
	protected DMObjectHashSet(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	/**
	* Constructor.<br>
	*/
	public DMObjectHashSet(final int initialCapacity) {
		this(initialCapacity, 0.75f);
	}
	/**
	* Constructor.<br>
	*/
	public DMObjectHashSet() {
		this(DEFAULT_TABLE_SIZE);
	}
	
	/**
	* {@inheritDoc}
	*/
	protected final DMObject[] __newElementArray(int len) {
		return new DMObject[len]; 
	}
	/**
	* {@inheritDoc}
	*/
	protected final DMObject __getElement(final DMObject from) {return from;}
	/**
	* {@inheritDoc}
	*/
	protected final int __getHashCodeForEltOf(DMObject entry) {return entry.hashCode(); }
	/**
	* {@inheritDoc}
	*/
	protected final DMObject __getNextEntry(final DMObject entry) {return entry.next; }
	/**
	* {@inheritDoc}
	*/
	protected final DMObject __newEntryFor(final DMObject e) {return e; }
	/**
	* {@inheritDoc}
	*/
	protected final void __setNextEntry(final DMObject entry, final DMObject next) {
		entry.next = next;
	}
	/**
	* {@inheritDoc}
	*/
	protected final void __removeNextEntry(final DMObject entry, final DMObject prev) {
		super.__removeNextEntry(entry, prev);
	}
	
	/**
	* {@inheritDoc}
	*/
	protected final boolean __isBeyondThreshold(int len) {
		return len > 64;
	}
	/**
	* {@inheritDoc}
	*/
	protected final boolean __isNotBeyondThreshold(int len) {
		return len < 65; 
	}
	/**
	* {@inheritDoc}
	*/
	protected final boolean __isBeforeThreshold(int len) {
		return len < 64;
	}
	/**
	* {@inheritDoc}
	* @return <code>64</code>
	*/
	protected final int __getThreshold() {
		return 64;
	}
	
	/**
	* {@inheritDoc}
	*/
	public final void add(DMObject obj) {
		super.add(obj);
	}
	/**
	* {@inheritDoc}
	*/
	public final DMObject addIfNotPresent(DMObject obj) {
		return super.addIfNotPresent(obj);
	}
	/**
	* {@inheritDoc}
	*/
	public final boolean contains(DMObject obj) {
		return super.contains(obj);
	}
	/**
	* {@inheritDoc}
	*/
	public final DMObject remove(final DMObject obj) {
		return super.remove(obj);
	}
	

}