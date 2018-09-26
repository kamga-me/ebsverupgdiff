package zz.ebs;

/**
* Utility class with constants and static methods that are handy in dealing with Oracle eBS products/modules supported in vanilla.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*
*/
public final class eBSProducts {

	/**Constant for <code>General Ledger</code> .*/public static final byte GL = 1; 
	/**Constant for <code>Receivables</code> .*/public static final byte AR = 2; 
	/**Constant for <code>Payables</code> .*/public static final byte AP = 3; 
	/**Constant for <code>Projects</code> .*/public static final byte PA = 4; 
	/**Constant for <code>Bills of Material</code> .*/public static final byte BOM = 5; 
	/**Constant for <code>Inventory</code> .*/public static final byte INV = 6; 
	/**Constant for <code>Order Entry</code> .*/public static final byte OE = 7; 
	/**Constant for <code>Master Scheduling/MRP</code> .*/public static final byte MRP = 8; 
	/**Constant for <code>Trade Management</code> .*/public static final byte OZF = 9; 
	/**Constant for <code>Purchasing</code> .*/public static final byte PO = 10; 
	/**Constant for <code>Marketing</code> .*/public static final byte AMS = 11; 
	/**Constant for <code>Collections</code> .*/public static final byte IEX = 12; 
	
	/**Constant for <code>Advanced Pricing</code> .*/public static final byte QP = 13; //since 2017-06-28
	/**Constant for <code>Subledger Accounting</code> .*/public static final byte XLA = 14; //since 2017-06-28
	
	/**
	* The number of vanilla products.<br>
	*/
	public static final byte NUMBER_OF_VANILLA_PRODUCTS = XLA;


}