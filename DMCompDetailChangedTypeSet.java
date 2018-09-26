package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import string.CString;

import core.collection.SimpleSet;
import core.collection.SimpleSetExt; 

/**
* Class for providing support for sets of detail changed types.<br>
* 
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public class DMCompDetailChangedTypeSet extends SimpleSetExt<DMCompDetailChangedType> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x0208207641B6928AL;
	
	/**
	* Constructor.<br>
	*/
	public DMCompDetailChangedTypeSet() {
		super();
	}
	/**
	* Constructor.<br>
	*/
	public DMCompDetailChangedTypeSet(final int initialCapacity) {
		super(initialCapacity);
	}
	/**
	* Constructor.<br>
	*/
	public DMCompDetailChangedTypeSet(final boolean initializeWithVanillas) {
		super(initializeWithVanillas ? DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES + 5 : 10);
		if (initializeWithVanillas) {
			DMCompDetailChangedType.getVanillas(array, 0);
			this.size = DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES;
		}
	}
	
	final void resetWithVanillas() {
		if (array.length < DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES) {
			array = new DMCompDetailChangedType[DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES + 5];
		}
		else if (size > DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES) {
			for (int i=DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES;i<size;i++)
			{
				array[i] = null;
			}
		}
		DMCompDetailChangedType.getVanillas(array, 0);
		this.size = DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES;
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
	protected final DMCompDetailChangedType[] __getEmptyArray() {return DMCompDetailChangedType.EMPTY_ARRAY; }
	/**
	* {@inheritDoc}
	*/
	protected final DMCompDetailChangedType[] __newArray(final int len) {return new DMCompDetailChangedType[len]; }

	/**
	* Gets the {@code DMCompDetailChangedType DMCompDetailChangedType} for the indicated code.<br>
	*/
	public DMCompDetailChangedType getByCode(final ICharSequence code, final int codeStart, final int codeEnd) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equalsSubString(code, codeStart, codeEnd)) return array[i];
		}
		return null;*/
		return super.getByCode(code, codeStart, codeEnd);
	}
	/**
	* Gets the {@code DMCompDetailChangedType DMCompDetailChangedType} for the indicated code.<br>
	*/
	public DMCompDetailChangedType getByCode(final ICharSequence code) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equals(code)) return array[i];
		}
		return null;*/
		return super.getByCode(code);
	}
	/**
	* Gets the {@code DMCompDetailChangedType DMCompDetailChangedType} for the indicated code.<br>
	*/
	public DMCompDetailChangedType getByCode(final CharSequence code) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equals(code)) return array[i];
		}
		return null;*/
		return super.getByCode(code);
	}
	/**
	* Gets the {@link DMCompDetailChangedType DMCompDetailChangedType} for the supplied detail changed type number.<br>
	* @return <code>null</code> if none of the detail changed types contained in this set has its number equal to that supplied and the supplied number is not that of a vanilla detail changed types; the {@link DMCompDetailChangedType DMCompDetailChangedType} for the supplied detail changed type number, otherwise. 
	*/
	public DMCompDetailChangedType getByNumber(final int detailChangedTypeNumber) {
		if (detailChangedTypeNumber <= DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES) {
			return DMCompDetailChangedType.get_DMCompDetailChangedType(detailChangedTypeNumber);
		}
		int lastIndex = size - 1;
		for (;lastIndex >= DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES/*OLD KO: > -1*/;lastIndex--)
		{
			if (array[lastIndex].number == detailChangedTypeNumber) {
				return array[lastIndex];
			}
		}
		return null;
	}
	
	
	protected final String __get_code(final DMCompDetailChangedType e) {return e.code; }
	
	protected final String __get_name(final DMCompDetailChangedType e) {return e.name; }
	

}
