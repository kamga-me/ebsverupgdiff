package zz.ebs;

import string.ICharSequence;
import string.String;
import static string.util.AsciiStringFactoryInternals.astr;

/**
* Class for providing support for the products for which the diffs-comparison is performed.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public final class eBSProduct extends core.Thing implements java.io.Serializable, Comparable<eBSProduct> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x020820D3E1B6928AL;
	
	public static final eBSProduct NO_PRODUCT = new eBSProduct((byte)0, String.EMPTY, String.EMPTY);
	/**
	* Empty array.
	*/
	public static final eBSProduct[] EMPTY_ARRAY = new eBSProduct[0];
	
	/**
	* The number of the product.
	*/
	public final short number;
	/**
	* The code of the product.
	*/
	public final String code;
	/**
	* The name/label of the product.
	*/
	public final String name;
	
	public final String comment;
	
	/**
	* Constructor.<br>
	*/
	/*public */eBSProduct(final short number, final String code, final String name, String comment) {
		super();
		this.number = number;
		this.code = code;
		this.name = name;
		this.comment = comment;
	}
	/**
	* Constructor.<br>
	*/
	/*public */eBSProduct(final short number, final String code, final String name) {
		this(number, code, name, String.EMPTY);
	}
	
	/**
	* @param productQCode the product code as it shows in the html report supplied by Oracle (<code> + ": " + <name>).<br>
	* @param outputArr array of length greater than <code>1</code> meant to hold the result
	*/
	public static final void getComponents(String productQCode, String[] outputArr) {
		throw new UnsupportedOperationException(
		"eBSProduct::getComponents-1: the method has yet to be effectively coded"
		);
	}
	
	
	/**
	* Compares this <code>eBSProduct</code> and that supplied, for number equality.<br>
	*/
	public final boolean equals(eBSProduct other) {
		return number == other.number;
	}
	/**
	* Compares this <code>eBSProduct</code> and that supplied, for number equality.<br>
	*/
	public final boolean equals(java.lang.Object other) {
		return other instanceof eBSProduct && number == ((eBSProduct)other).number;
	}
	/**
	* Compares this <code>eBSProduct</code> and that supplied, for number order.<br>
	*/
	public final int compareTo(eBSProduct other) {
		return code.compare(other.code);
	}
	
	/**
	* Returns the textual representation of this <code>eBSProduct</code>.<br>
	*/
	public java.lang.String toString() {
		java.lang.StringBuilder sb = new java.lang.StringBuilder(code.length() + 4 + name.length());
		return sb.append(code).append(": (").append(name).append(')').toString();
	}
	/**
	* Copies the detailed textual representation of this <code>eBSProduct</code>, into the supplied character buffer.<br>
	*/
	public void getText(int tabIndents, final string.CharBuffer outputBuffer) {
		eBSProd2StrUtil.getToYAMLStringChars(this, tabIndents, true/*useTabInLieuOfSpace*/, outputBuffer);
	}
	/**
	* Copies the detailed textual representation of this <code>eBSProduct</code>, into the supplied character buffer.<br>
	*/
	public final void getText(final string.CharBuffer outputBuffer) {
		getText(0/*tabIndents*/, outputBuffer);
	}
	
	private static final eBSProduct[] VANILLA_PRODUCTS = {
		NO_PRODUCT/*dummy/placeholder*/
		, new eBSProduct(eBSProducts.GL, astr('G', 'L'), astr(new byte[]{'G', 'e', 'n', 'e', 'r', 'a', 'l', ' ', 'L', 'e', 'd', 'g', 'e', 'r'})) 
		, new eBSProduct(eBSProducts.AR, astr('A', 'R'), astr(new byte[]{'R', 'e', 'c', 'e', 'i', 'v', 'a', 'b', 'l', 'e', 's'})) 
		, new eBSProduct(eBSProducts.AP, astr('A', 'P'), astr(new byte[]{'P', 'a', 'y', 'a', 'b', 'l', 'e', 's'})) 
		, new eBSProduct(eBSProducts.PA, astr('P', 'A'), astr(new byte[]{'P', 'r', 'o', 'j', 'e', 'c', 't', 's'})) 
		, new eBSProduct(eBSProducts.BOM, astr('B', 'O', 'M'), astr(new byte[]{'B', 'i', 'l', 'l', 's', ' ', 'o', 'f', ' ', 'M', 'a', 't', 'e', 'r', 'i', 'a', 'l'})) 
		, new eBSProduct(eBSProducts.INV, astr('I', 'N', 'V'), astr(new byte[]{'I', 'n', 'v', 'e', 'n', 't', 'o', 'r', 'y'})) 
		, new eBSProduct(eBSProducts.OE, astr('O', 'E'), astr(new byte[]{'O', 'r', 'd', 'e', 'r', ' ', 'E', 'n', 't', 'r', 'y'})) 
		, new eBSProduct(eBSProducts.MRP, astr('M', 'R', 'P'), astr(new byte[]{'M', 'a', 's', 't', 'e', 'r', ' ', 'S', 'c', 'h', 'e', 'd', 'u', 'l', 'i', 'n', 'g', '/', 'M', 'R', 'P'})) 
		, new eBSProduct(eBSProducts.OZF, astr('O', 'Z', 'F'), astr(new byte[]{'T', 'r', 'a', 'd', 'e', ' ', 'M', 'a', 'n', 'a', 'g', 'e', 'm', 'e', 'n', 't'})) 
		, new eBSProduct(eBSProducts.PO, astr('P', 'O'), astr(new byte[]{'P', 'u', 'r', 'c', 'h', 'a', 's', 'i', 'n', 'g'})) 
		
		, new eBSProduct(eBSProducts.AMS, astr('A', 'M', 'S'), astr(new byte[]{'M', 'a', 'r', 'k', 'e', 't', 'i', 'n', 'g'})) 
		, new eBSProduct(eBSProducts.IEX, astr('I', 'E', 'X'), astr(new byte[]{'C', 'o', 'l', 'l', 'e', 'c', 't', 'i', 'o', 'n', 's'})) 
		
		, new eBSProduct(eBSProducts.QP, astr('Q', 'P'), astr(new byte[]{'A', 'd', 'v', 'a', 'n', 'c', 'e', 'd', ' ', 'P', 'r', 'i', 'c', 'i', 'n', 'g'})) 
		, new eBSProduct(eBSProducts.XLA, astr('X', 'L', 'A'), astr(new byte[]{'S', 'u', 'b', 'l', 'e', 'd', 'g', 'e', 'r', ' ', 'A', 'c', 'c', 'o', 'u', 'n', 't', 'i', 'n', 'g'})) 
	};
	
	
	public static final byte getNumberOfVanillas() {return eBSProducts.NUMBER_OF_VANILLA_PRODUCTS; }
	
	/**
	* Returns an array holding the vanilla data-model comparison detail changed types/features.<br>
	*/
	public static final eBSProduct[] getVanillas() {
		eBSProduct[] array = new eBSProduct[eBSProducts.NUMBER_OF_VANILLA_PRODUCTS];
		System.arraycopy(VANILLA_PRODUCTS, 1, array, 0, array.length);
		return array;
	}
	/**
	* Copies the vanilla data-model comparison detail changed types/features into the supplied array.<br>
	*/
	public static final void getVanillas(eBSProduct[] dest, int destOff) {
		System.arraycopy(VANILLA_PRODUCTS, 1, dest, destOff, eBSProducts.NUMBER_OF_VANILLA_PRODUCTS);
	}
	
	/**
	*
	*/
	public static final eBSProduct get_eBSProduct(final int number) {
		return VANILLA_PRODUCTS[number];
	}
	
	/**
	* 
	*/
	public static final eBSProduct getEBSProduct(final ICharSequence code, int codeStart, int codeEnd) {
		for (int i=1;i<VANILLA_PRODUCTS.length;i++)
		{
			if (VANILLA_PRODUCTS[i].code.equalsSubString(code, codeStart, codeEnd)) return VANILLA_PRODUCTS[i];
		}
		return null;
	}
	/**
	* 
	*/
	public static final eBSProduct getEBSProduct(final ICharSequence code) {
		for (int i=1;i<VANILLA_PRODUCTS.length;i++)
		{
			if (VANILLA_PRODUCTS[i].code.equals(code)) return VANILLA_PRODUCTS[i];
		}
		return null;
	}
	/**
	* 
	*/
	public static final eBSProduct getEBSProduct(final CharSequence code) {
		for (int i=1;i<VANILLA_PRODUCTS.length;i++)
		{
			if (VANILLA_PRODUCTS[i].code.equals(code)) return VANILLA_PRODUCTS[i];
		}
		return null;
	}

	public static final eBSProduct new_NonVanillaProduct(final short number, final String code, final String name) {
		return new eBSProduct(number, code, name);
	}
	
	/**
	* Constant for <code>GL</code> product.<br>
	*/
	public static final eBSProduct GL = VANILLA_PRODUCTS[eBSProducts.GL];

}