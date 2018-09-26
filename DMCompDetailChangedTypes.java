package zz.ebs.dmcomp;

/**
* Utility class with constants and static methods that are handy in dealing with vanilla data-model comparison changed types, which are also referred to as change/changed features or detailed change node types.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public final class DMCompDetailChangedTypes {
	
	
	/**Constant for <code>Not Applicable</code> vanilla detail changed type - not really a detail changed type.*/public static final byte N_A = -1; //since 2017-05-05  

	/**Constant for <code>Table</code> vanilla detail changed type.*/public static final byte tab = 1; 
	/**Constant for <code>Column</code> vanilla detail changed type.*/public static final byte col = 2; 
	/**Constant for <code>Inital Extent</code> vanilla detail changed type.*/public static final byte initial_extent = 3; 
	/**Constant for <code>Text</code> vanilla detail changed type.*/public static final byte text = 4; 
	/**Constant for <code>Maximum Value</code> vanilla detail changed type.*/public static final byte max_value = 5; 
	/**Constant for <code>Minimum Value</code> vanilla detail changed type.*/public static final byte min_value = 6; 
	/**Constant for <code>Character Length</code> vanilla detail changed type.*/public static final byte char_length = 7; 
	/**Constant for <code>Data Length</code> vanilla detail changed type.*/public static final byte data_length = 8; 
	/**Constant for <code>Procedure</code> vanilla detail changed type.*/public static final byte proc = 9; 
	/**Constant for <code>Function</code> vanilla detail changed type.*/public static final byte func = 10; 
	/**Constant for <code>Owner</code> vanilla detail changed type.*/public static final byte owner = 11; 
	/**Constant for <code>Argument</code> vanilla detail changed type.*/public static final byte arg = 12; 
	/**Constant for <code>Data Type</code> vanilla detail changed type.*/public static final byte data_type = 13; 
	/**Constant for <code>Base Object Type</code> vanilla detail changed type.*/public static final byte base_object_type = 14; 
	/**Constant for <code>Return Type</code> vanilla detail changed type.*/public static final byte return_type = 15; 
	/**Constant for <code>In/Out</code> vanilla detail changed type.*/public static final byte in_out = 16; 
	/**Constant for <code>Data Precision</code> vanilla detail changed type.*/public static final byte data_precision = 17; 
	/**Constant for <code>Data Scale</code> vanilla detail changed type.*/public static final byte data_scale = 18; 
	/**Constant for <code>Nullable</code> vanilla detail changed type.*/public static final byte nullable = 19; 
	/**Constant for <code>Default Value</code> vanilla detail changed type.*/public static final byte default_value = 20; 
	/**Constant for <code>Column Position</code> vanilla detail changed type.*/public static final byte column_position = 21; 
	/**Constant for <code>Attribute</code> vanilla detail changed type.*/public static final byte attr = 22; 
	/**Constant for <code>Attribute Type</code> vanilla detail changed type.*/public static final byte attr_type = 23; 
	/**Constant for <code>Order Flag</code> vanilla detail changed type.*/public static final byte order_flag = 24; 
	/**Constant for <code>IniTrans</code> vanilla detail changed type.*/public static final byte ini_trans = 25; 
	/**Constant for <code>href</code> vanilla virtual/dummy detail changed type.*/public static final byte href = 26; //since 2017-04-26
	/**Constant for <code>Length</code> vanilla detail changed type.*/public static final byte length = 27; 
	/**Constant for <code>Default</code> vanilla detail changed type - alternative to {@link #default_value default_value}.*/public static final byte default_ = 28; //since 2017-04-27 
	/**Constant for <code>Type</code> vanilla detail changed type - not in the analysed data but added as kind TYPE is a valid kind for child objects of an Oracle package in the same as are FUNCTION and PROCEDURE kinds.*/public static final byte type = 29; //since 2017-04-27  
	/**Constant for <code>Variable</code> vanilla detail changed type - not in the analysed data but added as kind VARIABLE is a valid kind for child objects of an Oracle package in the same as are FUNCTION and PROCEDURE kinds.*/public static final byte var = 30; //since 2017-04-27  
	/**Constant for <code>Exception</code> vanilla detail changed type - not in the analysed data but added as kind EXCEPTION is a valid kind for child objects of an Oracle package in the same as are FUNCTION and PROCEDURE kinds.*/public static final byte except = 31; //since 2017-04-27  
	/**Constant for <code>View</code> vanilla detail changed type - not in the analysed data but added.*/public static final byte view = 32; //since 2017-04-28  
	/**Constant for <code>Materialized View</code> vanilla detail changed type - not in the analysed data but added.*/public static final byte mview = 33; //since 2017-04-28  
	
	/**Constant for <code>Column Id</code> vanilla detail changed type - Oracle's equivalent for column_position; it is strange sometimes oracle use column_position for column_id.*/public static final byte column_id = 34; //since 2017-05-04  
	
	/**Constant for <code>Object Type</code> vanilla detail changed type.*/public static final byte object_type = 35; 
	/**Constant for <code>Query</code> vanilla detail changed type.*/public static final byte query = 36; 
	
	
	/**Constant for <code>Cache Size</code> vanilla detail changed type.*/public static final byte cache_size = 37; 
	/**Constant for <code>Table Owner</code> vanilla detail changed type.*/public static final byte table_owner = 38; 
	/**Constant for <code>Index Owner</code> vanilla detail changed type.*/public static final byte index_owner = 39; 
	/**Constant for <code>Object</code> vanilla detail changed type.*/public static final byte obj = 40; 
	/**Constant for <code>Attribute #</code> vanilla detail changed type.*/public static final byte attr_no = 41; 
	/**Constant for <code>Parameter List</code> vanilla detail changed type.*/public static final byte PARAMETER_LIST = 42; 
	/**Constant for <code>Overload #1</code> vanilla detail changed type.*/public static final byte overload_1 = 43; 
	/**Constant for <code>Part</code> vanilla detail changed type.*/public static final byte part = 44; 
	/**Constant for <code>Overload #2</code> vanilla detail changed type.*/public static final byte overload_2 = 45; 
	/**Constant for <code>Here</code> vanilla detail changed type.*/public static final byte here = 46; 
	
	/**
	* The number of vanilla detail changed types.<br>
	*/
	public static final byte NUMBER_OF_VANILLA_DTL_CHGD_TYPES = 46;
	
	
	public static final short NOT_A_FROM_TO_CHANGE_FROM_VAL = -32768;
	
	public static final short WEAK_FROM_TO_CHANGE_FROM_VAL = -32767;
	
	public static final byte NO_SPEC_ITEM_FOR_DTL_OR_NO_CHG_TYPES_TO_MATCH = 0;
		
	public static final byte NO_DTL_TYPE_MATCH_FOR_FROM_TO_CHANGE = -1;
		
	
}