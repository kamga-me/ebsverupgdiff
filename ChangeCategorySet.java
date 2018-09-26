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
public class ChangeCategorySet extends SimpleSetExt<ChangeCategory> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x0208207641B6928AL;
	
	/**
	* Constructor.<br>
	*/
	public ChangeCategorySet() {
		super();
	}
	/**
	* Constructor.<br>
	*/
	public ChangeCategorySet(final int initialCapacity) {
		super(initialCapacity);
	}
	/**
	* Constructor.<br>
	*/
	public ChangeCategorySet(final boolean initializeWithVanillas) {
		super(initializeWithVanillas ? ChangeCategories.NUMBER_OF_VANILLA_CHG_CATEGORIES + 5 : 10);
		if (initializeWithVanillas) {
			ChangeCategory.getVanillas(array, 0);
			this.size = ChangeCategories.NUMBER_OF_VANILLA_CHG_CATEGORIES;
		}
	}
	
	public /*final */void resetWithVanillas() {
		if (array.length < ChangeCategories.NUMBER_OF_VANILLA_CHG_CATEGORIES) {
			array = new ChangeCategory[ChangeCategories.NUMBER_OF_VANILLA_CHG_CATEGORIES + 5];
		}
		else if (size > ChangeCategories.NUMBER_OF_VANILLA_CHG_CATEGORIES) {
			for (int i=ChangeCategories.NUMBER_OF_VANILLA_CHG_CATEGORIES;i<size;i++)
			{
				array[i] = null;
			}
		}
		ChangeCategory.getVanillas(array, 0);
		this.size = ChangeCategories.NUMBER_OF_VANILLA_CHG_CATEGORIES;
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
	protected final ChangeCategory[] __getEmptyArray() {return ChangeCategory.EMPTY_ARRAY; }
	/**
	* {@inheritDoc}
	*/
	protected final ChangeCategory[] __newArray(final int len) {return new ChangeCategory[len]; }

	/**
	* Gets the {@code ChangeCategory ChangeCategory} for the indicated code.<br>
	*/
	public ChangeCategory getByCode(ICharSequence code, int codeStart, int codeEnd) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equalsSubString(code, codeStart, codeEnd)) return array[i];
		}
		return null;*/
		return super.getByCode(code, codeStart, codeEnd);
	}
	/**
	* Gets the {@code ChangeCategory ChangeCategory} for the indicated code.<br>
	*/
	public ChangeCategory getByCode(ICharSequence code) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equals(code)) return array[i];
		}
		return null;*/
		return super.getByCode(code);
	}
	/**
	* Gets the {@code ChangeCategory ChangeCategory} for the indicated code.<br>
	*/
	public ChangeCategory getByCode(CharSequence code) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equals(code)) return array[i];
		}
		return null;*/
		return super.getByCode(code);
	}
	
	
	protected final String __get_code(final ChangeCategory e) {return e.code; }
	
	protected final String __get_name(final ChangeCategory e) {return e.name; }
	
	/**
	* Checks if the specified change type is of a category of this set.<br>
	* @return <code>-1</code> if the indicated change type is of neither of the categories of this set; the number of the category to which the indicated change type belongs, otherwise.
	*/
	public byte checkCategoryOf(final ICharSequence changeType, int start, int end) {
		for (int i=0;i<size;i++)
		{
			ChangeCategory cat = array[i];
			if (cat.name.length() >= end - start && cat.name.equalsSubString(changeType, start, start + cat.name.length())) {
				return cat.code.number;
			}
			else if (cat.code.length() >= end - start && cat.code.equalsSubString(changeType, start, start + cat.code.length())) {
				return cat.code.number;
			}
		}
		return -1;
	}
	/**
	* Checks if the supplied change type is of a category of this set.<br>
	* @return <code>-1</code> if the indicated change type is of neither of the categories of this set; the number of the category to which the indicated change type belongs, otherwise.
	*/
	public byte checkCategoryOf(final ICharSequence changeType) {
		return checkCategoryOf(changeType, 0, changeType.length());
	}
	

}
