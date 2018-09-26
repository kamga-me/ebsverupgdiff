package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import string.CString;

import core.collection.SimpleSet;
import core.collection.SimpleSetExt;

/**
* Class for providing support for sets of change types.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*
*/
public class ChangeTypeSet extends SimpleSetExt<ChangeType> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x0208207641B6928AL;
	
	/**
	* Constructor.<br>
	*/
	public ChangeTypeSet() {
		super();
	}
	/**
	* Constructor.<br>
	*/
	public ChangeTypeSet(final int initialCapacity) {
		super(initialCapacity);
	}
	/**
	* Constructor.<br>
	*/
	public ChangeTypeSet(final boolean initializeWithVanillas) {
		super(initializeWithVanillas ? ChangeTypes.NUMBER_OF_VANILLA_CHANGE_TYPES + 5 : 10);
		if (initializeWithVanillas) {
			ChangeType.getVanillas(array, 0);
			this.size = ChangeTypes.NUMBER_OF_VANILLA_CHANGE_TYPES;
		}
	}
	
	public /*final */void resetWithVanillas() {
		if (array.length < ChangeTypes.NUMBER_OF_VANILLA_CHANGE_TYPES) {
			array = new ChangeType[ChangeTypes.NUMBER_OF_VANILLA_CHANGE_TYPES + 5];
		}
		else if (size > ChangeTypes.NUMBER_OF_VANILLA_CHANGE_TYPES) {
			for (int i=ChangeTypes.NUMBER_OF_VANILLA_CHANGE_TYPES;i<size;i++)
			{
				array[i] = null;
			}
		}
		ChangeType.getVanillas(array, 0);
		this.size = ChangeTypes.NUMBER_OF_VANILLA_CHANGE_TYPES;
	}

	/**
	* {@inheritDoc}
	* @return <code>false</code>
	*/
	public final boolean allowsDuplicate() {return false; }
	/**
	* {@inheritDoc}
	*/
	public boolean isSorted() {
		return false; 
	}
	
	/**
	* {@inheritDoc}
	*/
	protected final ChangeType[] __getEmptyArray() {return ChangeType.EMPTY_ARRAY; }
	/**
	* {@inheritDoc}
	*/
	protected final ChangeType[] __newArray(final int len) {return new ChangeType[len]; }

	/**
	* Gets the {@code ChangeType ChangeType} for the indicated code.<br>
	*/
	public ChangeType getByCode(ICharSequence code, int codeStart, int codeEnd) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equalsSubString(code, codeStart, codeEnd)) return array[i];
		}
		return null;*/
		return super.getByCode(code, codeStart, codeEnd);
	}
	/**
	* Gets the {@code ChangeType ChangeType} for the indicated code.<br>
	*/
	public ChangeType getByCode(ICharSequence code) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equals(code)) return array[i];
		}
		return null;*/
		return super.getByCode(code);
	}
	/**
	* Gets the {@code ChangeType ChangeType} for the indicated code.<br>
	*/
	public ChangeType getByCode(CharSequence code) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equals(code)) return array[i];
		}
		return null;*/
		return super.getByCode(code);
	}
	
	
	protected final String __get_code(final ChangeType e) {return e.code; }
	
	protected final String __get_name(final ChangeType e) {return e.name; }
	

}
