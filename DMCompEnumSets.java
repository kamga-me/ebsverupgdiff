package zz.ebs.dmcomp;

import string.String;
import string.CString;

import core.collection.ISet;
import core.collection.SimpleStringSet;

import zz.ebs.eBSProductSet;
import zz.ebs.eBSProduct;

/**
* Class for providing support for sets of sets involved in reading data model comparison diffs reports and converting them to flat files, for integration into a datawarehouse.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public class DMCompEnumSets implements java.io.Serializable  {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x020812BF91B6928AL;

	SimpleStringSet diffsMethods;  //e.g. Object Definition Differences Between 12.1.3 and 12.2.6, Indexed Tables & Columns (Note: Even though the index is added in 12.1.3, all underlying tables & columns are present in 12.1.3), ...
	SimpleStringSet changes; //holds the names change type names (see changeTypes - ChangeTypeSet)
	/**
	* Holds the types of the nodes of the detailed change spec/path.<br>
	*/
	SimpleStringSet detailNodeTypes; //initial_extent, data_type, ...
	SimpleStringSet summaryCategoryDisplayNames;
	eBSProductSet ebsProducts;
	DMCompDetailChangedTypeSet detailChangedTypes; //initial_extent, data_type, ...
	int detailChangedTypesStartCount;
	ChangeCategorySet changeCategories;
	ChangeTypeSet changeTypes;
	int changeTypesStartCount;
	DiffsCompCategorySet diffsCompCategories;
	DiffsCompMethodSet diffsCompMethodSet;
	int diffsCompMethodsStartCount;
	DMObjectHashSet dmObjects;
	DMCompDetailPathHashSet dmCompDetailPaths;
	DMSourceSet secondarySources;
	int writtenSecondarySources; //

	/**
	* Constructor.<br>
	*/
	public DMCompEnumSets() {
		this.diffsMethods = new SimpleStringSet();
		this.changes = new SimpleStringSet();
		this.detailNodeTypes = new SimpleStringSet();
		this.summaryCategoryDisplayNames = new SimpleStringSet();
		this.ebsProducts = new eBSProductSet(true/*initializeWithVanillas*/);
		this.detailChangedTypes = new DMCompDetailChangedTypeSet(true/*initializeWithVanillas*/);
		this.detailChangedTypesStartCount = this.detailChangedTypes.size();
		this.changeCategories = new ChangeCategorySet(true/*initializeWithVanillas*/);
		this.changeTypes = new ChangeTypeSet(true/*initializeWithVanillas*/);
		this.changeTypesStartCount = this.changeTypes.size();
		this.diffsCompCategories = new DiffsCompCategorySet(true/*initializeWithVanillas*/);
		this.diffsCompMethodSet = new DiffsCompMethodSet(true/*initializeWithVanillas*/);
		this.diffsCompMethodsStartCount = diffsCompMethodSet.size();
		this.dmObjects = new DMObjectHashSet();
		this.dmCompDetailPaths = new DMCompDetailPathHashSet();
		this.secondarySources = new DMSourceSet();
		this.writtenSecondarySources = 0;
	}
	/**
	* Constructor.<br>
	*/
	public DMCompEnumSets(final SimpleStringSet diffsMethods, final SimpleStringSet changes, final SimpleStringSet detailNodeTypes, final SimpleStringSet summaryCategoryDisplayNames) {
		this.diffsMethods = diffsMethods == null ? new SimpleStringSet() : diffsMethods;
		this.changes = changes == null ? new SimpleStringSet() : changes;
		this.detailNodeTypes = detailNodeTypes == null ? new SimpleStringSet() : detailNodeTypes;
		this.summaryCategoryDisplayNames = summaryCategoryDisplayNames;
		this.ebsProducts = new eBSProductSet(true/*initializeWithVanillas*/);
		this.detailChangedTypes = new DMCompDetailChangedTypeSet(true/*initializeWithVanillas*/);
		this.detailChangedTypesStartCount = detailChangedTypes.size();
		this.changeCategories = new ChangeCategorySet(true/*initializeWithVanillas*/);
		this.changeTypes = new ChangeTypeSet(true/*initializeWithVanillas*/);
		this.diffsCompCategories = new DiffsCompCategorySet(true/*initializeWithVanillas*/);
		this.diffsCompMethodSet = new DiffsCompMethodSet(true/*initializeWithVanillas*/);
		this.diffsCompMethodsStartCount = diffsCompMethodSet.size();
		this.dmObjects = new DMObjectHashSet();
		this.dmCompDetailPaths = new DMCompDetailPathHashSet();
		this.secondarySources = new DMSourceSet();
		this.writtenSecondarySources = 0;
	}
	/**
	* Constructor.<br>
	*/
	public DMCompEnumSets(final SimpleStringSet diffsMethods, final SimpleStringSet changes, final SimpleStringSet detailNodeTypes) {
		this(diffsMethods, changes, detailNodeTypes, null/*summaryCategoryDisplayNames*/);
	}

	/**
	* Creates a new {@link ChangeType ChangeType}, add it to the set and returns it if none of the existing change types has the specified ID, else returns the existing {@link ChangeType ChangeType} with the specified ID.<br>
	*/
	public final ChangeType getChangeTypeByID(final String nameOrCode) {
		ChangeType chgType = changeTypes.getByName(nameOrCode);
		if (chgType != null) return chgType;
		chgType = changeTypes.getByCode(nameOrCode);
		if (chgType != null) return chgType;
		else if (ChangeType.Attribute_Changes__between_12_1_3_and_12_2_6.equals(nameOrCode)) return ChangeType.Attribute_Changes_between_12_1_3_and_12_2_6;
		else if (nameOrCode.isEmpty()) return ChangeType.NO_VALUE;
//		System.out.println("::::::::::::::getChangeTypeByID-size_before_add: " + changeTypes.size() + ", nameOrCode: " + nameOrCode);
		byte changeTypeCategoryNum = ChangeCategory.workoutCategoryNumber(nameOrCode, true/*isChangeTypeName*/);
		byte[] nameData = new byte[nameOrCode.length()];
		nameOrCode.getChars8(nameData, 0);
		ChangeTypeID typeNameID = new ChangeTypeID((short)(changeTypes.size() + 1), changeTypeCategoryNum, nameData, true/*isName*/);
		ChangeTypeID typeCodeID = new ChangeTypeID(typeNameID, true/*altIDType*/);
		chgType = new ChangeType(typeCodeID, typeNameID);
		changeTypes.add(chgType);
//		System.out.println("::::::::::::::getChangeTypeByID-size_after_add: " + changeTypes.size() + ", nameOrCode: " + nameOrCode);
		return chgType;
	}
	/**
	* Creates a new {@link DiffsCompMethod DiffsCompMethod}, add it to the set and returns it if none of the existing change types has the specified ID, else returns the existing {@link DiffsCompMethod DiffsCompMethod} with the specified ID.<br>
	*/
	public final DiffsCompMethod getDiffsCompMethodByID(final String nameOrCode) {
		DiffsCompMethod mthd = diffsCompMethodSet.getByName(nameOrCode);
		if (mthd != null) return mthd;
		mthd = diffsCompMethodSet.getByCode(nameOrCode);
		if (mthd != null) return mthd;
		else if (DiffsCompMethod.Object_Definition_Differences_Between_12_1_3_and_12_2_6_.equals(nameOrCode)) return DiffsCompMethod.Object_Definition_Differences_Between_12_1_3_and_12_2_6; //since 2017-05-04 - after noticed that in file for PA the method was ending with a dangling closing parenthesis
		byte diffsCompCategory = DiffsCompCategory.workoutDiffsCompCategory(nameOrCode/*compMthdNameOrCode*/, true/*isName*/);
		byte[] nameData = new byte[nameOrCode.length()];
		nameOrCode.getChars8(nameData, 0);
		DiffsCompMethodID typeNameID = new DiffsCompMethodID((short)(diffsCompMethodSet.size() + 1), diffsCompCategory, nameData, true/*isName*/);
		DiffsCompMethodID typeCodeID = new DiffsCompMethodID(typeNameID, true/*altIDType*/);
		mthd = new DiffsCompMethod(typeCodeID, typeNameID);
		diffsCompMethodSet.add(mthd);
		return mthd;
	}

	public DMCompDetailChangedType getDetailChangedTypeByID(final String codeOrName) {
		DMCompDetailChangedType detailChangedType = detailChangedTypes.getByCode(codeOrName);
		if (detailChangedType != null) return detailChangedType;
		detailChangedType = detailChangedTypes.getByName(codeOrName);
		if (detailChangedType != null) return detailChangedType;
//		System.out.println("::::::::::::::getDetailChangedTypeByID-size_before_add: " + detailChangedTypes.size() + ", codeOrName: " + codeOrName);
		detailChangedType = new DMCompDetailChangedType((short)(detailChangedTypes.size() + 1)/*number*/, codeOrName/*code*/, codeOrName/*name*/);
		detailChangedTypes.add(detailChangedType);
//		System.out.println("::::::::::::::getDetailChangedTypeByID-size_after_add: " + detailChangedTypes.size() + ", codeOrName: " + codeOrName);
		return detailChangedType;
	}

}