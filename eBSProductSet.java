package zz.ebs;

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
public class eBSProductSet extends SimpleSetExt<eBSProduct> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x0208207641B6928AL;
	
	/**
	* Constructor.<br>
	*/
	public eBSProductSet() {
		super();
	}
	/**
	* Constructor.<br>
	*/
	public eBSProductSet(final int initialCapacity) {
		super(initialCapacity);
	}
	/**
	* Constructor.<br>
	*/
	public eBSProductSet(final boolean initializeWithVanillas) {
		super(initializeWithVanillas ? eBSProducts.NUMBER_OF_VANILLA_PRODUCTS + 5 : 10);
		if (initializeWithVanillas) {
			eBSProduct.getVanillas(array, 0);
			this.size = eBSProducts.NUMBER_OF_VANILLA_PRODUCTS;
		}
	}
	
	public /*final */void resetWithVanillas() {
		if (array.length < eBSProducts.NUMBER_OF_VANILLA_PRODUCTS) {
			array = new eBSProduct[eBSProducts.NUMBER_OF_VANILLA_PRODUCTS + 5];
		}
		else if (size > eBSProducts.NUMBER_OF_VANILLA_PRODUCTS) {
			for (int i=eBSProducts.NUMBER_OF_VANILLA_PRODUCTS;i<size;i++)
			{
				array[i] = null;
			}
		}
		eBSProduct.getVanillas(array, 0);
		this.size = eBSProducts.NUMBER_OF_VANILLA_PRODUCTS;
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
	protected final eBSProduct[] __getEmptyArray() {return eBSProduct.EMPTY_ARRAY; }
	/**
	* {@inheritDoc}
	*/
	protected final eBSProduct[] __newArray(final int len) {return new eBSProduct[len]; }

	/**
	* Gets the {@code eBSProduct eBSProduct} for the indicated code.<br>
	*/
	public eBSProduct getByCode(ICharSequence code, int codeStart, int codeEnd) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equalsSubString(code, codeStart, codeEnd)) return array[i];
		}
		return null;*/
		return super.getByCode(code, codeStart, codeEnd);
	}
	/**
	* Gets the {@code eBSProduct eBSProduct} for the indicated code.<br>
	*/
	public eBSProduct getByCode(ICharSequence code) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equals(code)) return array[i];
		}
		return null;*/
		return super.getByCode(code);
	}
	/**
	* Gets the {@code eBSProduct eBSProduct} for the indicated code.<br>
	*/
	public eBSProduct getByCode(CharSequence code) {
		/*for (int i=0;i<size;i++)
		{
			if (array[i].code.equals(code)) return array[i];
		}
		return null;*/
		return super.getByCode(code);
	}
	
	
	protected final String __get_code(final eBSProduct e) {return e.code; }
	
	protected final String __get_name(final eBSProduct e) {return e.name; }
	

}
