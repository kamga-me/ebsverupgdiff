package zz.ebs.dmcomp;

import string.String;
import string.CString;

import core.collection.HashSet;
import core.collection.LinkedElementsHashSet;

/**
* Class for providing support for hash-sets of {@link DMCompDetailPath DMCompDetailPath} elements.<br>
* 
* @author Marc E. KAMGA
* @version 1.0
* @author Marc E. KAMGA
*/
public /*final */class DMCompDetailPathHashSet extends LinkedElementsHashSet<DMCompDetailPath> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x02088B7181B6928AL;
	
	/**
	* Constructor.<br>
	*/
	protected DMCompDetailPathHashSet(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	/**
	* Constructor.<br>
	*/
	public DMCompDetailPathHashSet(final int initialCapacity) {
		this(initialCapacity, 0.75f);
	}
	/**
	* Constructor.<br>
	*/
	public DMCompDetailPathHashSet() {
		this(DEFAULT_TABLE_SIZE);
	}
	
	/**
	* {@inheritDoc}
	*/
	protected final DMCompDetailPath[] __newElementArray(int len) {
		return new DMCompDetailPath[len]; 
	}
	/**
	* {@inheritDoc}
	*/
	protected final DMCompDetailPath __getElement(final DMCompDetailPath from) {return from;}
	/**
	* {@inheritDoc}
	*/
	protected final int __getHashCodeForEltOf(DMCompDetailPath entry) {return entry.hashCode(); }
	/**
	* {@inheritDoc}
	*/
	protected final DMCompDetailPath __getNextEntry(final DMCompDetailPath entry) {return entry.next; }
	/**
	* {@inheritDoc}
	*/
	protected final DMCompDetailPath __newEntryFor(final DMCompDetailPath e) {return e; }
	/**
	* {@inheritDoc}
	*/
	protected final void __setNextEntry(final DMCompDetailPath entry, final DMCompDetailPath next) {
		entry.next = next;
	}
	/**
	* {@inheritDoc}
	*/
	protected final void __removeNextEntry(final DMCompDetailPath entry, final DMCompDetailPath prev) {
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
	public final void add(final DMCompDetailPath obj) {
		super.add(obj);
	}
	/**
	* {@inheritDoc}
	*/
	public final DMCompDetailPath addIfNotPresent(final DMCompDetailPath obj) {
		return super.addIfNotPresent(obj);
	}
	/**
	* {@inheritDoc}
	*/
	public final boolean contains(final DMCompDetailPath obj) {
		return super.contains(obj);
	}
	/**
	* {@inheritDoc}
	*/
	public final DMCompDetailPath remove(final DMCompDetailPath obj) {
		return super.remove(obj);
	}
	

}