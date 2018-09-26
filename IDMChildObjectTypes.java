package zz.ebs.dmcomp;

/**
* Interface with integer number constants for the child object types.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public interface IDMChildObjectTypes {
	
	/**
	* Constant for <code>Type</code> package child objets.<br>
	*/
	public static final byte PACKAGE_TYPE_OBJECT = 1;
	/**
	* Constant for <code>Procedure</code> package child objets.<br>
	*/
	public static final byte PACKAGE_PROCEDURE_OBJECT = 2;
	/**
	* Constant for <code>Function</code> package child objets.<br>
	*/
	public static final byte PACKAGE_FUNCTION_OBJECT = 3;
	/**
	* Constant for <code>Variable</code> package child objets.<br>
	*/
	public static final byte PACKAGE_VARIABLE_OBJECT = 4;
	/**
	* Constant for <code>Exception</code> package child objets.<br>
	*/
	public static final byte PACKAGE_EXCEPTION_OBJECT = 5; //except

	/**
	* The number of child object types.<br>
	*/
	public static final byte NUMBER_OF_CHILD_OBJECT_TYPES = 5;
	
	/**
	* Constant for signalling that an object is not a child object.<br>
	*/
	public static final byte NOT_A_CHILD_OBJECT_TYPE = 0;

}