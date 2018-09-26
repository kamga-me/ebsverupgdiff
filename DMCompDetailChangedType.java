package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import string.CString;

/**
* Class for providing support for data model comparison detail changed types.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public class DMCompDetailChangedType implements core.IValueTypes, core.data.type.IValueTypesExt, java.io.Serializable, Comparable<DMCompDetailChangedType> {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x02081E10A1B6928AL;
	
//
//	***NOTE: moved to class DMCompDetailChangedTypes, which is a more suitable place for these constants declarations...
//
//	public static final short NOT_A_FROM_TO_CHANGE_FROM_VAL = -32768;
//	
//	public static final short WEAK_FROM_TO_CHANGE_FROM_VAL = -32767;
//	
//	public static final byte NO_SPEC_ITEM_FOR_DTL_OR_NO_CHG_TYPES_TO_MATCH = 0;
//		
//	public static final byte NO_DTL_TYPE_MATCH_FOR_FROM_TO_CHANGE = -1;
	
	/**
	* Constant for signalling the absence of value, which is also referred to as unspecified.<br>
	*/
	public static final DMCompDetailChangedType NO_VALUE = new DMCompDetailChangedType((short)0/*number*/, String.EMPTY/*code*/, String.EMPTY/*name*/, ANY_VALUE/*valueType*/);
	/**
	* Constant for signalling that detail changed type is not applicable.<br>
	*/
	public static final DMCompDetailChangedType N_A = new DMCompDetailChangedType(DMCompDetailChangedTypes.N_A/*number*/, new string.Code.Code3((byte)'N', (byte)'/', (byte)'A')/*code*/, CString.valueOf(new byte[]{'N', 'o', 't', ' ', 'A', 'p', 'p', 'l', 'i', 'c', 'a', 'b', 'l', 'e'}, true, true)/*name*/, ANY_VALUE/*valueType*/);
	
	/**
	* Empty array.<br>
	*/
	public static final DMCompDetailChangedType[] EMPTY_ARRAY = new DMCompDetailChangedType[0];
	
	/**
	* The number of the data-model detail changed type/feature.<br>
	* 
	* @see DMCompDetailChangedTypes DMCompDetailChangedTypes
	*/
	public final short number;
	/**
	* The code of the data-model detail changed type/feature.<br>
	*/
	public final String code;
	/**
	* The name of the data-model detail changed type/feature.<br>
	*/
	public final String name;
	/**
	* The number of the primary value type of the <code>DMCompDetailChangedType</code>
	*/
	public final byte valueType; //since 2017-04-26
	
	/**
	* Constructor.<br>
	*/
	DMCompDetailChangedType(final short number, final String code, final String name, final byte valueType) {
		super();
		this.number = number;
		this.code = code;
		this.name = name;
		this.valueType = valueType;
	}
	/**
	* Constructor.<br>
	*/
	DMCompDetailChangedType(final short number, final String code, final String name) {
		this(number, code, name, ANY_VALUE/*valueType*/);
	}
	/**
	* Constructor.<br>
	*/
	private DMCompDetailChangedType(final short number, final String code, final byte valueType) {
		this(number, code, code/*name*/, valueType);
	}
	/**
	* Gets the short code of this <code>DMCompDetailChangedType</code>.<br>
	*/
	public final String getShortCode() { //since 2017-06-21
		return DMCompDetailedChgdTypeUtil.getShortCode(number);
	}
	
	/**
	* Compares this <code>DMCompDetailChangedType</code> and that supplied, for number equality.<br>
	*/
	public final boolean equals(DMCompDetailChangedType other) {
		return number == other.number;
	}
	/**
	* Compares this <code>DMCompDetailChangedType</code> and that supplied, for number equality.<br>
	*/
	public final boolean equals(java.lang.Object other) {
		return other instanceof DMCompDetailChangedType && number == ((DMCompDetailChangedType)other).number;
	}
	/**
	* Compares this <code>DMCompDetailChangedType</code> and that supplied, for number order.<br>
	*/
	public final int compareTo(DMCompDetailChangedType other) {
		return code.compare(other.code);
	}
	
	/**
	* Returns the textual representation of this <code>DMCompDetailChangedType</code>.<br>
	*/
	public java.lang.String toString() {
		java.lang.StringBuilder sb = new java.lang.StringBuilder(code.length() + 4 + name.length());
		return sb.append(code).append(": (").append(name).append(')').toString();
	}
	
	private static final DMCompDetailChangedType[] VANILLA_VALUES = new DMCompDetailChangedType[]{
		NO_VALUE /*dummy placeholder*/
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.tab, CString.valueOf(new byte[]{'t', 'a', 'b'}, true, true), CString.valueOf(new byte[]{'T', 'a', 'b', 'l', 'e'}, true, true), STRING/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.col, CString.valueOf(new byte[]{'c', 'o', 'l'}, true, true), CString.valueOf(new byte[]{'C', 'o', 'l', 'u', 'm', 'n'}, true, true), STRING/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.initial_extent, CString.valueOf(new byte[]{'i', 'n', 'i', 't', 'i', 'a', 'l', '_', 'e', 'x', 't', 'e', 'n', 't'}, true, true), CString.valueOf(new byte[]{'I', 'n', 'i', 't', 'a', 'l', ' ', 'E', 'x', 't', 'e', 'n', 't'}, true, true), INTEGER/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.text, CString.valueOf(new byte[]{'t', 'e', 'x', 't'}, true, true), CString.valueOf(new byte[]{'T', 'e', 'x', 't'}, true, true), STRING/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.max_value, CString.valueOf(new byte[]{'m', 'a', 'x', '_', 'v', 'a', 'l', 'u', 'e'}, true, true), CString.valueOf(new byte[]{'M', 'a', 'x', 'i', 'm', 'u', 'm', ' ', 'V', 'a', 'l', 'u', 'e'}, true, true), DECIMAL/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.min_value, CString.valueOf(new byte[]{'m', 'i', 'n', '_', 'v', 'a', 'l', 'u', 'e'}, true, true), CString.valueOf(new byte[]{'M', 'i', 'n', 'i', 'm', 'u', 'm', ' ', 'V', 'a', 'l', 'u', 'e'}, true, true), DECIMAL/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.char_length, CString.valueOf(new byte[]{'c', 'h', 'a', 'r', '_', 'l', 'e', 'n', 'g', 't', 'h'}, true, true), CString.valueOf(new byte[]{'C', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r', ' ', 'L', 'e', 'n', 'g', 't', 'h'}, true, true), INTEGER/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.data_length, CString.valueOf(new byte[]{'d', 'a', 't', 'a', '_', 'l', 'e', 'n', 'g', 't', 'h'}, true, true), CString.valueOf(new byte[]{'D', 'a', 't', 'a', ' ', 'L', 'e', 'n', 'g', 't', 'h'}, true, true), INTEGER/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.proc, CString.valueOf(new byte[]{'p', 'r', 'o', 'c'}, true, true), CString.valueOf(new byte[]{'P', 'r', 'o', 'c', 'e', 'd', 'u', 'r', 'e'}, true, true), STRING/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.func, CString.valueOf(new byte[]{'f', 'u', 'n', 'c'}, true, true), CString.valueOf(new byte[]{'F', 'u', 'n', 'c', 't', 'i', 'o', 'n'}, true, true), STRING/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.owner, CString.valueOf(new byte[]{'o', 'w', 'n', 'e', 'r'}, true, true), CString.valueOf(new byte[]{'O', 'w', 'n', 'e', 'r'}, true, true), STRING/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.arg, CString.valueOf(new byte[]{'a', 'r', 'g'}, true, true), CString.valueOf(new byte[]{'A', 'r', 'g', 'u', 'm', 'e', 'n', 't'}, true, true), STRING/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.data_type, CString.valueOf(new byte[]{'d', 'a', 't', 'a', '_', 't', 'y', 'p', 'e'}, true, true), CString.valueOf(new byte[]{'D', 'a', 't', 'a', ' ', 'T', 'y', 'p', 'e'}, true, true), STRING/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.base_object_type, CString.valueOf(new byte[]{'b', 'a', 's', 'e', '_', 'o', 'b', 'j', 'e', 'c', 't', '_', 't', 'y', 'p', 'e'}, true, true), CString.valueOf(new byte[]{'B', 'a', 's', 'e', ' ', 'O', 'b', 'j', 'e', 'c', 't', ' ', 'T', 'y', 'p', 'e'}, true, true), STRING/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.return_type, CString.valueOf(new byte[]{'r', 'e', 't', 'u', 'r', 'n', '_', 't', 'y', 'p', 'e'}, true, true), CString.valueOf(new byte[]{'R', 'e', 't', 'u', 'r', 'n', ' ', 'T', 'y', 'p', 'e'}, true, true), STRING/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.in_out, CString.valueOf(new byte[]{'i', 'n', '_', 'o', 'u', 't'}, true, true), CString.valueOf(new byte[]{'I', 'n', '/', 'O', 'u', 't'}, true, true), STRING/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.data_precision, CString.valueOf(new byte[]{'d', 'a', 't', 'a', '_', 'p', 'r', 'e', 'c', 'i', 's', 'i', 'o', 'n'}, true, true), CString.valueOf(new byte[]{'D', 'a', 't', 'a', ' ', 'P', 'r', 'e', 'c', 'i', 's', 'i', 'o', 'n'}, true, true), INTEGER/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.data_scale, CString.valueOf(new byte[]{'d', 'a', 't', 'a', '_', 's', 'c', 'a', 'l', 'e'}, true, true), CString.valueOf(new byte[]{'D', 'a', 't', 'a', ' ', 'S', 'c', 'a', 'l', 'e'}, true, true), INTEGER/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.nullable, CString.valueOf(new byte[]{'n', 'u', 'l', 'l', 'a', 'b', 'l', 'e'}, true, true), CString.valueOf(new byte[]{'N', 'u', 'l', 'l', 'a', 'b', 'l', 'e'}, true, true), BOOLEAN/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.default_value, CString.valueOf(new byte[]{'d', 'e', 'f', 'a', 'u', 'l', 't', '_', 'v', 'a', 'l', 'u', 'e'}, true, true), CString.valueOf(new byte[]{'D', 'e', 'f', 'a', 'u', 'l', 't', ' ', 'V', 'a', 'l', 'u', 'e'}, true, true), ANY_VALUE/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.column_position, CString.valueOf(new byte[]{'c', 'o', 'l', 'u', 'm', 'n', '_', 'p', 'o', 's', 'i', 't', 'i', 'o', 'n'}, true, true), CString.valueOf(new byte[]{'C', 'o', 'l', 'u', 'm', 'n', ' ', 'P', 'o', 's', 'i', 't', 'i', 'o', 'n'}, true, true), INTEGER/*valueType*/)		
		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.attr, CString.valueOf(new byte[]{'a', 't', 't', 'r'}, true, true), CString.valueOf(new byte[]{'A', 't', 't', 'r', 'i', 'b', 'u', 't', 'e'}, true, true), STRING/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.attr_type, CString.valueOf(new byte[]{'a', 't', 't', 'r', '_', 't', 'y', 'p', 'e'}, true, true), CString.valueOf(new byte[]{'A', 't', 't', 'r', 'i', 'b', 'u', 't', 'e', ' ', 'T', 'y', 'p', 'e'}, true, true), STRING/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.order_flag, CString.valueOf(new byte[]{'o', 'r', 'd', 'e', 'r', '_', 'f', 'l', 'a', 'g'}, true, true), CString.valueOf(new byte[]{'O', 'r', 'd', 'e', 'r', ' ', 'F', 'l', 'a', 'g'}, true, true), BOOLEAN/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.ini_trans, CString.valueOf(new byte[]{'i', 'n', 'i', '_', 't', 'r', 'a', 'n', 's'}, true, true), CString.valueOf(new byte[]{'I', 'n', 'i', 'T', 'r', 'a', 'n', 's'}, true, true), INTEGER/*valueType*/)		
		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.href, CString.valueOf(string.CFullSizeStringDataArrayGetter.getDataArray(core.html.SharedStringConstants.href)/*new byte[]{'h', 'r', 'e', 'f'}*/, true, true), URI/*valueType*/) //new as of 2017-04-26	
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.length, CString.valueOf(new byte[]{'l', 'e', 'n', 'g', 't', 'h'}, true, true), CString.valueOf(new byte[]{'L', 'e', 'n', 'g', 't', 'h'}, true, true), INTEGER/*valueType*/)		
		
		, null //for default //NOTE: COMMENTED OUT TO BE ABLE TO SHARE arrays with the entry for default_value //new DMCompDetailChangedType(DMCompDetailChangedTypes.default_, CString.valueOf(new byte[]{'d', 'e', 'f', 'a', 'u', 'l', 't'}, true, true), CString.valueOf(new byte[]{'D', 'e', 'f', 'a', 'u', 'l', 't'}, true, true), ANY_VALUE/*valueType*/) 
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.type, CString.valueOf(new byte[]{'t', 'y', 'p', 'e'}, true, true), CString.valueOf(new byte[]{'T', 'y', 'p', 'e'}, true, true), STRING/*valueType*/)		
		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.var, CString.valueOf(new byte[]{'v', 'a', 'r'}, true, true), CString.valueOf(new byte[]{'V', 'a', 'r', 'i', 'a', 'b', 'l', 'e'}, true, true), STRING/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.except, CString.valueOf(new byte[]{'e', 'x', 'c', 'e', 'p', 't'}, true, true), CString.valueOf(new byte[]{'E', 'x', 'c', 'e', 'p', 't', 'i', 'o', 'n'}, true, true), STRING/*valueType*/)		
		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.view, CString.valueOf(new byte[]{'v', 'i', 'e', 'w'}, true, true), CString.valueOf(new byte[]{'V', 'i', 'e', 'w'}, true, true), STRING/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.mview, CString.valueOf(new byte[]{'m', 'v', 'i', 'e', 'w'}, true, true), CString.valueOf(new byte[]{'M', 'a', 't', 'e', 'r', 'i', 'a', 'z', 'e', 'd', ' ', 'V', 'i', 'e', 'w'}, true, true), STRING/*valueType*/)		
		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.column_id, CString.valueOf(new byte[]{'c', 'o', 'l', 'u', 'm', 'n', '_', 'i', 'd'}, true, true), CString.valueOf(new byte[]{'C', 'o', 'l', 'u', 'm', 'n', ' ', 'I', 'd'}, true, true), INTEGER/*valueType*/)		
		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.object_type, CString.valueOf(new byte[]{'o', 'b', 'j', 'e', 'c', 't', '_', 't', 'y', 'p', 'e'}, true, true), CString.valueOf(new byte[]{'O', 'b', 'j', 'e', 'c', 't', ' ', 'T', 'y', 'p', 'e'}, true, true), STRING/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.query, CString.valueOf(new byte[]{'q', 'u', 'e', 'r', 'y'}, true, true), CString.valueOf(new byte[]{'Q', 'u', 'e', 'r', 'y'}, true, true), STRING/*valueType*/)	

		, new DMCompDetailChangedType(DMCompDetailChangedTypes.cache_size, CString.valueOf(new byte[]{'c', 'a', 'c', 'h', 'e', '_', 's', 'i', 'z', 'e'}, true, true), CString.valueOf(new byte[]{'C', 'a', 'c', 'h', 'e', ' ', 'S', 'i', 'z', 'e'}, true, true), INTEGER/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.table_owner, CString.valueOf(new byte[]{'t', 'a', 'b', 'l', 'e', '_', 'o', 'w', 'n', 'e', 'r'}, true, true), CString.valueOf(new byte[]{'T', 'a', 'b', 'l', 'e', ' ', 'O', 'w', 'n', 'e', 'r'}, true, true), STRING/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.index_owner, CString.valueOf(new byte[]{'i', 'n', 'd', 'e', 'x', '_', 'o', 'w', 'n', 'e', 'r'}, true, true), CString.valueOf(new byte[]{'I', 'n', 'd', 'e', 'x', ' ', 'O', 'w', 'n', 'e', 'r'}, true, true), STRING/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.obj, CString.valueOf(new byte[]{'o', 'b', 'j'}, true, true), CString.valueOf(new byte[]{'O', 'b', 'j', 'e', 'c', 't'}, true, true), STRING/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.attr_no, CString.valueOf(new byte[]{'a', 't', 't', 'r', '_', 'n', 'o'}, true, true), CString.valueOf(new byte[]{'A', 't', 't', 'r', 'i', 'b', 'u', 't', 'e', ' ', '#'}, true, true), STRING/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.PARAMETER_LIST, CString.valueOf(new byte[]{'P', 'A', 'R', 'A', 'M', 'E', 'T', 'E', 'R', '_', 'L', 'I', 'S', 'T'}, true, true), CString.valueOf(new byte[]{'P', 'a', 'r', 'a', 'm', 'e', 't', 'e', 'r', ' ', 'L', 'i', 's', 't'}, true, true), INTEGER/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.overload_1, CString.valueOf(new byte[]{'o', 'v', 'e', 'r', 'l', 'o', 'a', 'd', '_', '1'}, true, true), CString.valueOf(new byte[]{'O', 'v', 'e', 'r', 'l', 'o', 'a', 'd', ' ', '#', '1'}, true, true), STRING/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.part, CString.valueOf(new byte[]{'p', 'a', 'r', 't'}, true, true), CString.valueOf(new byte[]{'P', 'a', 'r', 't'}, true, true), STRING/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.overload_2, CString.valueOf(new byte[]{'o', 'v', 'e', 'r', 'l', 'o', 'a', 'd', '_', '2'}, true, true), CString.valueOf(new byte[]{'O', 'v', 'e', 'r', 'l', 'o', 'a', 'd', ' ', '#', '2'}, true, true), STRING/*valueType*/)		
		, new DMCompDetailChangedType(DMCompDetailChangedTypes.here, CString.valueOf(new byte[]{'h', 'e', 'r', 'e'}, true, true), CString.valueOf(new byte[]{'H', 'e', 'r', 'e'}, true, true), STRING/*valueType*/)				
		
	};
	static {
		DMCompDetailChangedType default_value = VANILLA_VALUES[DMCompDetailChangedTypes.default_value];
		VANILLA_VALUES[DMCompDetailChangedTypes.default_] = new DMCompDetailChangedType(DMCompDetailChangedTypes.default_, default_value.code.left(7), default_value.name.left(7), ANY_VALUE/*valueType*/);  
	}
	/**
	* Returns the number of vanilla detail changed types.<br>
	*/
	public static final byte getNumberOfVanillas() {return DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES; }
	
	/**
	* Returns an array holding the vanilla data-model comparison detail changed types/features.<br>
	*/
	public static final DMCompDetailChangedType[] getVanillas() {
		DMCompDetailChangedType[] array = new DMCompDetailChangedType[DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES];
		System.arraycopy(VANILLA_VALUES, 1, array, 0, array.length);
		return array;
	}
	/**
	* Copies the vanilla data-model comparison detail changed types/features into the supplied array.<br>
	*/
	public static final void getVanillas(final DMCompDetailChangedType[] dest, final int destOff) {
		System.arraycopy(VANILLA_VALUES, 1, dest, destOff, DMCompDetailChangedTypes.NUMBER_OF_VANILLA_DTL_CHGD_TYPES);
	}
	
	/**
	* Gets the {@link DMCompDetailChangedType DMCompDetailChangedType} for the supplied number.<br>
	* @throws IndexOutOfBoundsException if <code>number</code> is negative or greater than {@link #getNumberOfVanillas() getNumberOfVanillas()}
	*/
	public static final DMCompDetailChangedType get_DMCompDetailChangedType(final int number) {
		return VANILLA_VALUES[number];
	}
	
	/**
	* 
	*/
	public static final DMCompDetailChangedType getDMCompDetailChangedType(final ICharSequence code, int codeStart, int codeEnd) {
		for (int i=1;i<VANILLA_VALUES.length;i++)
		{
			if (VANILLA_VALUES[i].code.equalsSubString(code, codeStart, codeEnd)) return VANILLA_VALUES[i];
		}
		return null;
	}
	/**
	* 
	*/
	public static final DMCompDetailChangedType getDMCompDetailChangedType(final ICharSequence code) {
		for (int i=1;i<VANILLA_VALUES.length;i++)
		{
			if (VANILLA_VALUES[i].code.equals(code)) return VANILLA_VALUES[i];
		}
		return null;
	}
	/**
	* 
	*/
	public static final DMCompDetailChangedType getDMCompDetailChangedType(final CharSequence code) {
		for (int i=1;i<VANILLA_VALUES.length;i++)
		{
			if (VANILLA_VALUES[i].code.equals(code)) return VANILLA_VALUES[i];
		}
		return null;
	}
	/**
	* Tells if this detail changed type of one of those who denote a database physical top objects such as <code>tab</code> and <code>view</code>.<br>
	*/
	public final boolean isDBPhysicalTopObjectType() {return isDBPhysicalTopObjectType(number); }
	
	/**
	* Tells if the detail changed type whose number is supplied is that of one of those who denote a database physical top objects such as <code>tab</code> and <code>view</code>.<br>
	*/
	public static boolean isDBPhysicalTopObjectType(final short detailChangedTypeNumber) {
		switch(detailChangedTypeNumber)
		{
		case DMCompDetailChangedTypes.tab: 
		case DMCompDetailChangedTypes.view: 
		case DMCompDetailChangedTypes.mview: 
			return true;
		}
		return false; 
	}
	/**
	* Checks if the detail changed type whose number is supplied is that of one of those who denote a database physical top objects such as <code>tab</code> and <code>view</code>.<br>
	*/
	public static DMCompDetailChangedType checkIfDBPhysicalTopObjectType(final ICharSequence detailChangedTypeCode) {
		return tab.equals(detailChangedTypeCode) ? tab :  
				VANILLA_VALUES[DMCompDetailChangedTypes.view].equals(detailChangedTypeCode) ? VANILLA_VALUES[DMCompDetailChangedTypes.view] :  
				VANILLA_VALUES[DMCompDetailChangedTypes.mview].equals(detailChangedTypeCode) ? VANILLA_VALUES[DMCompDetailChangedTypes.mview] : null 
				; 
	}

	/**
	* Constant for <code>text</code> detail changed type.<br>
	*/
	public static final DMCompDetailChangedType text = VANILLA_VALUES[DMCompDetailChangedTypes.text];
	/**
	* Constant for <code>tab</code> detail changed type.<br>
	*/
	public static final DMCompDetailChangedType tab = VANILLA_VALUES[DMCompDetailChangedTypes.tab];
	/**
	* Constant for <code>tab</code> detail changed type.<br>
	*/
	public static final DMCompDetailChangedType col = VANILLA_VALUES[DMCompDetailChangedTypes.col];
	

}