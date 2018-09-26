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
public class DiffsCompCategorySet extends SimpleSetExt<DiffsCompCategory> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x020833A821B6928AL;
	
	/**
	* Constructor.<br>
	*/
	public DiffsCompCategorySet() {
		super();
	}
	/**
	* Constructor.<br>
	*/
	public DiffsCompCategorySet(final int initialCapacity) {
		super(initialCapacity);
	}
	/**
	* Constructor.<br>
	*/
	public DiffsCompCategorySet(final boolean initializeWithVanillas) {
		super(initializeWithVanillas ? DiffsCompCategories.NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES + 3 : 8);
		if (initializeWithVanillas) {
			DiffsCompCategory.getVanillas(array, 0);
			this.size = DiffsCompCategories.NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES;
		}
	}
	
	public /*final */void resetWithVanillas() {
		if (array.length < DiffsCompCategories.NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES) {
			array = new DiffsCompCategory[DiffsCompCategories.NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES + 3];
		}
		else if (size > DiffsCompCategories.NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES) {
			for (int i=DiffsCompCategories.NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES;i<size;i++)
			{
				array[i] = null;
			}
		}
		DiffsCompCategory.getVanillas(array, 0);
		this.size = DiffsCompCategories.NUMBER_OF_VANILLA_DIFFS_COMP_CATEGORIES;
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
	protected final DiffsCompCategory[] __getEmptyArray() {return DiffsCompCategory.EMPTY_ARRAY; }
	/**
	* {@inheritDoc}
	*/
	protected final DiffsCompCategory[] __newArray(final int len) {return new DiffsCompCategory[len]; }

	/**
	* Gets the {@code DiffsCompCategory DiffsCompCategory} for the indicated code.<br>
	*/
	public DiffsCompCategory getByCode(ICharSequence code, int codeStart, int codeEnd) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equalsSubString(code, codeStart, codeEnd)) return array[i];
		}
		return null;*/
		return super.getByCode(code, codeStart, codeEnd);
	}
	/**
	* Gets the {@code DiffsCompCategory DiffsCompCategory} for the indicated code.<br>
	*/
	public DiffsCompCategory getByCode(ICharSequence code) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equals(code)) return array[i];
		}
		return null;*/
		return super.getByCode(code);
	}
	/**
	* Gets the {@code DiffsCompCategory DiffsCompCategory} for the indicated code.<br>
	*/
	public DiffsCompCategory getByCode(CharSequence code) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equals(code)) return array[i];
		}
		return null;*/
		return super.getByCode(code);
	}
	
	
	protected final String __get_code(final DiffsCompCategory e) {return e.code; }
	
	protected final String __get_name(final DiffsCompCategory e) {return e.name; }
	
	
	/**
	* Checks if the specified diffs comparison method is of a category of this set.<br>
	* @return <code>-1</code> if the indicated diffs comparison method is of neither of the categories of this set; the number of the category to which the indicated diffs comparison method belongs, otherwise.
	*/
	public byte checkCategoryOf(final ICharSequence diffsCompMethod, int start, int end) {
		for (int i=0;i<size;i++)
		{
			DiffsCompCategory cat = array[i];
			if (cat.name.length() >= end - start && cat.name.equalsSubString(diffsCompMethod, start, start + cat.name.length())) {
				return cat.code.number;
			}
			else if (cat.code.length() >= end - start && cat.code.equalsSubString(diffsCompMethod, start, start + cat.code.length())) {
				return cat.code.number;
			}
		}
		return -1;
	}
	/**
	* Checks if the supplied diffs comparison method is of a category of this set.<br>
	* @return <code>-1</code> if the indicated diffs comparison method is of neither of the categories of this set; the number of the category to which the indicated diffs comparison method belongs, otherwise.
	*/
	public byte checkCategoryOf(final ICharSequence diffsCompMethod) {
		return checkCategoryOf(diffsCompMethod, 0, diffsCompMethod.length());
	}
	

}
