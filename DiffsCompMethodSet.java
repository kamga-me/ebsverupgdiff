package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import string.CString;

import core.collection.SimpleSet;
import core.collection.SimpleSetExt;

/**
* 
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*
*/
public class DiffsCompMethodSet extends SimpleSetExt<DiffsCompMethod> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x020833A981B6928AL;
	
	/**
	* Constructor.<br>
	*/
	public DiffsCompMethodSet() {
		super();
	}
	/**
	* Constructor.<br>
	*/
	public DiffsCompMethodSet(final int initialCapacity) {
		super(initialCapacity);
	}
	/**
	* Constructor.<br>
	*/
	public DiffsCompMethodSet(final boolean initializeWithVanillas) {
		super(initializeWithVanillas ? DiffsCompMethods.NUMBER_OF_VANILLA_DIFFS_COMP_METHODS + 5 : 10);
		if (initializeWithVanillas) {
			DiffsCompMethod.getVanillas(array, 0);
			this.size = DiffsCompMethods.NUMBER_OF_VANILLA_DIFFS_COMP_METHODS;
		}
	}
	
	public /*final */void resetWithVanillas() {
		if (array.length < DiffsCompMethods.NUMBER_OF_VANILLA_DIFFS_COMP_METHODS) {
			array = new DiffsCompMethod[DiffsCompMethods.NUMBER_OF_VANILLA_DIFFS_COMP_METHODS + 5];
		}
		else if (size > DiffsCompMethods.NUMBER_OF_VANILLA_DIFFS_COMP_METHODS) {
			for (int i=DiffsCompMethods.NUMBER_OF_VANILLA_DIFFS_COMP_METHODS;i<size;i++)
			{
				array[i] = null;
			}
		}
		DiffsCompMethod.getVanillas(array, 0);
		this.size = DiffsCompMethods.NUMBER_OF_VANILLA_DIFFS_COMP_METHODS;
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
	protected final DiffsCompMethod[] __getEmptyArray() {return DiffsCompMethod.EMPTY_ARRAY; }
	/**
	* {@inheritDoc}
	*/
	protected final DiffsCompMethod[] __newArray(final int len) {return new DiffsCompMethod[len]; }

	/**
	* Gets the {@code DiffsCompMethod DiffsCompMethod} for the indicated code.<br>
	*/
	public DiffsCompMethod getByCode(ICharSequence code, int codeStart, int codeEnd) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equalsSubString(code, codeStart, codeEnd)) return array[i];
		}
		return null;*/
		return super.getByCode(code, codeStart, codeEnd);
	}
	/**
	* Gets the {@code DiffsCompMethod DiffsCompMethod} for the indicated code.<br>
	*/
	public DiffsCompMethod getByCode(ICharSequence code) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equals(code)) return array[i];
		}
		return null;*/
		return super.getByCode(code);
	}
	/**
	* Gets the {@code DiffsCompMethod DiffsCompMethod} for the indicated code.<br>
	*/
	public DiffsCompMethod getByCode(CharSequence code) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equals(code)) return array[i];
		}
		return null;*/
		return super.getByCode(code);
	}
	
	
	protected final String __get_code(final DiffsCompMethod e) {return e.code; }
	
	protected final String __get_name(final DiffsCompMethod e) {return e.name; }
	

}
