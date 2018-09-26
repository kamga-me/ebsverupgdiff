package zz.ebs.dmcomp;

import string.String;
import string.ICharSequence;

/**
* Utility class with static methods that are handy in dealing with child object types.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public class DMChildObjectTypes implements IDMChildObjectTypes {

	
	public static final String PACKAGE_TYPE_OBJECT_CODE = DMCompDetailChangedType.get_DMCompDetailChangedType(DMCompDetailChangedTypes.type).code;
	
	public static final String PACKAGE_PROCEDURE_OBJECT_CODE = DMCompDetailChangedType.get_DMCompDetailChangedType(DMCompDetailChangedTypes.proc).code;
	
	public static final String PACKAGE_FUNCTION_OBJECT_CODE = DMCompDetailChangedType.get_DMCompDetailChangedType(DMCompDetailChangedTypes.func).code;

	public static final String PACKAGE_VARIABLE_OBJECT_CODE = DMCompDetailChangedType.get_DMCompDetailChangedType(DMCompDetailChangedTypes.var).code;
	
	public static final String PACKAGE_EXCEPTION_OBJECT_CODE = DMCompDetailChangedType.get_DMCompDetailChangedType(DMCompDetailChangedTypes.except).code;
	
	public static final byte getChildObjectTypeNumber(final String code) {
		//return PACKAGE_PROCEDURE_OBJECT_CODE.equals(code) ? PACKAGE_PROCEDURE_OBJECT : 
		//			PACKAGE_FUNCTION_OBJECT_CODE.equals(code) ? PACKAGE_FUNCTION_OBJECT : 
		//				PACKAGE_TYPE_OBJECT_CODE.equals(code) ? PACKAGE_TYPE_OBJECT : (byte)-1;
		byte cmp = PACKAGE_PROCEDURE_OBJECT_CODE.compare(code);
		if (cmp >= (byte)0) {
			if (cmp == (byte)0) return PACKAGE_PROCEDURE_OBJECT;
			else if (PACKAGE_FUNCTION_OBJECT_CODE.equals(code)) return PACKAGE_FUNCTION_OBJECT;
			else if (PACKAGE_EXCEPTION_OBJECT_CODE.equals(code)) return PACKAGE_EXCEPTION_OBJECT;
			else if (code.startsWith(PACKAGE_EXCEPTION_OBJECT_CODE)) return PACKAGE_EXCEPTION_OBJECT;
			else if (code.length() > 2) {
				if (code.getFirstChar() == 'e' && code.getChar(1) == 'x' && code.getChar(2) == 'c') {
					return PACKAGE_EXCEPTION_OBJECT;
				}
			}
		}
		else {
			if (PACKAGE_TYPE_OBJECT_CODE.equals(code)) return PACKAGE_TYPE_OBJECT;
			else if (PACKAGE_VARIABLE_OBJECT_CODE.equals(code)) return PACKAGE_VARIABLE_OBJECT;
			else if (code.startsWith(PACKAGE_VARIABLE_OBJECT_CODE)) return PACKAGE_VARIABLE_OBJECT;
		}
		return -1;
	}
	
	private static final String __getChildObjectTypeCode(final int childTypeObjNumber, final boolean assumeValidNumber) {
		if (childTypeObjNumber >= PACKAGE_FUNCTION_OBJECT/*3*/) {
			return childTypeObjNumber == PACKAGE_FUNCTION_OBJECT ? PACKAGE_FUNCTION_OBJECT_CODE :  
						childTypeObjNumber == PACKAGE_VARIABLE_OBJECT ? PACKAGE_VARIABLE_OBJECT_CODE :  
						!assumeValidNumber ? 
							(childTypeObjNumber == PACKAGE_EXCEPTION_OBJECT ? PACKAGE_EXCEPTION_OBJECT_CODE :  null) : 
							PACKAGE_EXCEPTION_OBJECT_CODE
						;
		}
		else {
			return childTypeObjNumber == PACKAGE_PROCEDURE_OBJECT ? PACKAGE_PROCEDURE_OBJECT_CODE :   
						!assumeValidNumber ? 
								(childTypeObjNumber == PACKAGE_TYPE_OBJECT ? PACKAGE_TYPE_OBJECT_CODE :  null) : 
								PACKAGE_TYPE_OBJECT_CODE
						;
		}
	}
	public static final String getChildObjectTypeCode(final int childTypeObjNumber) {
		return __getChildObjectTypeCode(childTypeObjNumber, false/*assumeValidNumber*/);
	}
	public static final String get_ChildObjectTypeCode(final int childTypeObjNumber) {
		return __getChildObjectTypeCode(childTypeObjNumber, true/*assumeValidNumber*/);
	}

}