package zz.ebs.dmcomp;

import string.String;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsDetailedChange;
import zz.ebs.dmcomp.DMCompSummaryCategories;
import static zz.ebs.dmcomp.DMCompSummaryCategories.Packages;

import zz.ebs.eBSProduct;
import static zz.ebs.eBSProduct.GL;

final class SomeTests {

	public static final void main(java.lang.String[] args) {
		String[] spec = new string.String[]{String.valueOf("proc".toCharArray(), true), String.valueOf("INSERT_SERIAL_NUMBER".toCharArray(), true), String.valueOf("arg".toCharArray(), true), String.valueOf("P_D_ATTRIBUTE4".toCharArray(), true), String.valueOf("Added in 12.2.6".toCharArray(), true)};
		DiffsDetailedChange detailedCh = new DiffsDetailedChange(spec);
		DMCompDetailChangedTypeSet allDetailChangedTypes =  new DMCompDetailChangedTypeSet(true/*initializeWithVanillas*/);
		DMCompDetailPath detailPath = detailedCh.getDetailPath(2/*pathEnd*/, true/*failIfNewDetailTypeAndNullSet*/, allDetailChangedTypes, null/*tempBuffer*/);
		
		spec = new string.String[]{String.valueOf("initial_extent".toCharArray(), true), String.valueOf("0 =>".toCharArray(), true), String.valueOf("131072".toCharArray(), true)};
		detailedCh = new DiffsDetailedChange(spec);
		short chkRslt = detailedCh.checkIfFromToChange(allDetailChangedTypes/*allDdtailChangedTypes*/);
		System.out.println("SomeTests - detailedChg: " + detailedCh);
		System.out.println("SomeTests - chkRslt: " + chkRslt);
		
		spec = new String[]{String.valueOf("attr".toCharArray(), true), String.valueOf("ITEM_DESC_LANG_OBJ_TBL_TYPE".toCharArray(), true), String.valueOf("attr_type".toCharArray(), true), String.valueOf("INV_EBI_DESC_LANG_OBJ_TBL_TYPE".toCharArray(), true)}; 
		
		
		string.String str = String.valueOf("3 => ".toCharArray(), true);
		string.String trimmed = DiffsDetailedChange.trimSpecItem(str, true/*dropFromToOperatorSymbolIfAny*/);
		System.out.println("str: '" + str + "', trimmed: '" + trimmed + "'");
		
		DMObject obj1 = new DMObject(Packages, String.valueOf("GL_DRM_INTEGRATION_PKG".toCharArray(), true)/*name*/, eBSProduct.GL);
		DMObject obj2 = new DMObject(Packages, String.valueOf("GL_FUSION_TRANSFER_PKG".toCharArray(), true)/*name*/, eBSProduct.GL);
		
		boolean notEq = obj1.summaryCategory != obj2.summaryCategory || 
				obj1.isChildObject() != obj2.isChildObject() || 
					!obj1.name.equalsIgnoreAsciiCase(obj2.name) || 
						!obj1.owner.equalsIgnoreAsciiCase(obj2.owner);
		
		System.out.println("obj1.hashCode: " + obj1.hashCode() + ", obj2.hashCode: " + obj2.hashCode() + ", obj1.equals(obj2): " + obj1.equals(obj2) + ", obj1.compare(obj2): " + obj1.compare(obj2) + ", notEq: " + notEq + ", obj1.name.equalsIgnoreAsciiCase(obj2.name): " + obj1.name.equalsIgnoreAsciiCase(obj2.name));
		System.out.println("obj1: \r\n" + obj1);
		System.out.println("obj2: \r\n" + obj2);
		
	}

}