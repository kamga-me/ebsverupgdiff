package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import core.collection.SmallIndexesSmartStringKeyEltSet;

/**
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public final class DMSourceSet extends SmallIndexesSmartStringKeyEltSet<DMSource> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x020DCF3AB1B6928AL;

	/**
	* Constructor.<br>
	*/
	public DMSourceSet() {
		super();
	}
	/**
	* Constructor.<br>
	*/
	public DMSourceSet(final int initialCapacity) {
		super(initialCapacity);
	}
	
	/**
	* {@inheritDoc}
	*/
	protected final DMSource[] __getEmptyArray() {
		return DMSource.EMPTY_ARRAY;
	}
	/**
	* {@inheritDoc}
	*/
	protected final DMSource[] __newArray(final int len) {
		return new DMSource[len];
	}
	
	/**
	* {@inheritDoc}
	*/
	protected final byte compare(final DMSource e1, final DMSource e2) {
		return e1.ID.compare(e2.ID);
	}
	/**
	* {@inheritDoc}
	*/
	protected final boolean equals(final DMSource e1, final DMSource e2) {
		return e1.ID.equals(e2.ID);
	}
	
	/**
	* {@inheritDoc}
	*/
	protected final byte compareElementKeyTo(DMSource e, final String keyComp2) {
		return e.ID.compare(keyComp2);
	}
	/**
	* {@inheritDoc}
	*/
	protected final boolean elementKeyEquals(DMSource e, final String keyComp2) {
		return e.ID.equals(keyComp2);
	}
	/**
	* {@inheritDoc}
	*/
	protected final byte compareElementKeyTo(DMSource e, final ICharSequence keyComp2) {
		return e.ID.compare(keyComp2);
	}
	/**
	* {@inheritDoc}
	*/
	protected final boolean elementKeyEquals(DMSource e, final ICharSequence keyComp2) {
		return e.ID.equals(keyComp2);
	}
	/**
	* {@inheritDoc}
	*/
	protected final byte compareElementKeyTo(DMSource e, final ICharSequence keyComp2, final int keyComp2Start, final int keyComp2End) {
		return e.ID.compareToSubString(keyComp2, keyComp2Start, keyComp2End);
	}
	/**
	* {@inheritDoc}
	*/
	protected final boolean elementKeyEquals(DMSource e, final ICharSequence keyComp2, final int keyComp2Start, final int keyComp2End) {
		return e.ID.equalsSubString(keyComp2, keyComp2Start, keyComp2End);
	}
	/**
	* {@inheritDoc}
	*/
	protected final byte compareElementKeyTo(DMSource e, CharSequence keyComp2) {
		return e.ID.compare(keyComp2);
	}
	/**
	* {@inheritDoc}
	*/
	protected final boolean elementKeyEquals(DMSource e, final CharSequence keyComp2) {
		return e.ID.equals(keyComp2);
	}


}