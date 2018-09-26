package zz.ebs.dmcomp;

import zz.ebs.dmcomp.DataModelComparisonReport.DiffsSummary;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsDetailedChange;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsTableRow;
import zz.ebs.dmcomp.DataModelComparisonReport.SingleChgDiffsTableRow;
import zz.ebs.dmcomp.DataModelComparisonReport.SingleChgDiffsTableRowAdvcd;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsNewIndex;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsTableRowChange;
import zz.ebs.dmcomp.DataModelComparisonReport.MultiChgDiffsTableRow;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsTable;

import static core.IOperatingSystemConstants.LINE_TERMINATOR;


final class DMCompReportThing2StrUtil {
	
	static final java.lang.String getKindCode(final byte kind) {
		switch(kind)
		{
		case Thing.DIFFS_COMPARISON_REPORT: return "DIFFS_COMPARISON_REPORT"; 
		case Thing.DIFFS_SUMMARY: return "DIFFS_SUMMARY"; 
		case Thing.DIFFS_TABLE: return "DIFFS_TABLE"; 
		case Thing.DIFFS_TABLE_ROW: return "DIFFS_TABLE_ROW"; 
		case Thing.DIFFS_TABLE_ROW_CHANGE: return "DIFFS_TABLE_ROW_CHANGE"; 
		case Thing.DIFFS_DETAILED_CHANGE: return "DIFFS_DETAILED_CHANGE"; 
		case Thing.DIFFS_DETAILED_CHANGE_PATH: return "DIFFS_DETAILED_CHANGE_PATH"; 
		case Thing.DIFFS_OBJECT: return "DIFFS_OBJECT"; 
		}
		return java.lang.String.valueOf(kind);
	}
	
	static final void getKindInfoYAMLStringChars(final int indents, final byte kind, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("kind: ").append(getKindCode(kind)).append(LINE_TERMINATOR);
	}

	public static final void getToYAMLStringChars(final DiffsSummary thing, /*final */int indents, final boolean includeKindInfo, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		if (indents < 0) {
			indents = 0;
		}
		if (includeKindInfo) {
			getKindInfoYAMLStringChars(indents, thing.getKind(), useTabInLieuOfSpace, outputBuff);
		}
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("Regular_Tables: ").append(thing.Regular_Tables).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Partitioned_Tables: ").append(thing.Partitioned_Tables).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Queue_Tables: ").append(thing.Queue_Tables).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Index_Orgnized_Tables: ").append(thing.Index_Orgnized_Tables).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Global_Temp_Tables: ").append(thing.Global_Temp_Tables).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Indexes: ").append(thing.Indexes).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("New_Indexes_on_Existing_Tables_$_Columns: ").append(thing.New_Indexes_on_Existing_Tables_$_Columns).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("New_Indexes_on_new_Tables_$_Columns: ").append(thing.New_Indexes_on_new_Tables_$_Columns).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("other_Index_Changes: ").append(thing.other_Index_Changes).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Views: ").append(thing.Views).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Sequences: ").append(thing.Sequences).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Materialized_Views: ").append(thing.Materialized_Views).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Materialized_View_Logs: ").append(thing.Materialized_View_Logs).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Advanced_Queues: ").append(thing.Advanced_Queues).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Triggers: ").append(thing.Triggers).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Packages: ").append(thing.Packages).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Types: ").append(thing.Types).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("Security_Object: ").append(thing.Security_Object).append(LINE_TERMINATOR)
		;
	}
	
	public static final void getToYAMLStringChars(final DMCompDetailPath thing, /*final */int indents, final boolean includeKindInfo, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		if (indents < 0) {
			indents = 0;
		}
		if (includeKindInfo) {
			getKindInfoYAMLStringChars(indents, thing.getKind(), useTabInLieuOfSpace, outputBuff);
		}
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("size: ").append(thing.sz).append(LINE_TERMINATOR);
		if (thing.sz == 0) {
			outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("nodes: []").append(LINE_TERMINATOR); 
			return ;
		}
		else if (thing.sz == 1) {
			int indentsP1 = indents + 1;
			int indentsP2 = indents + 2;
			outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("nodes: ")
			.AppendSpaces(indentsP1, useTabInLieuOfSpace).append("- ").append(LINE_TERMINATOR)
			.AppendSpaces(indentsP2, useTabInLieuOfSpace).append("type: ").append(thing.nodes[0]).append(LINE_TERMINATOR)
			.AppendSpaces(indentsP2, useTabInLieuOfSpace).append("name: ").append(thing.nodes[1]).append(LINE_TERMINATOR);
			return ;
		}
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("nodes: ").append(LINE_TERMINATOR); 
		int indentsP1 = indents + 1;
		int indentsP2 = indents + 2;
		
		final int end = ((thing.sz & 0xFF) << 1);
		int i = 0;
		for (;i<end;i++)
		{
			outputBuff.AppendSpaces(indentsP1, useTabInLieuOfSpace).append("- ").append(LINE_TERMINATOR)
			.AppendSpaces(indentsP2, useTabInLieuOfSpace).append("type: ").append(thing.nodes[i]).append(LINE_TERMINATOR)
			.AppendSpaces(indentsP2, useTabInLieuOfSpace).append("name: ").append(thing.nodes[++i]).append(LINE_TERMINATOR);
		}
	}

	public static final void getToYAMLStringChars(final DiffsDetailedChange thing, /*final */int indents, final boolean includeKindInfo, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		if (indents < 0) {
			indents = 0;
		}
		if (thing.spec.length == 0) {
			if (includeKindInfo) {
				getKindInfoYAMLStringChars(indents, thing.getKind(), useTabInLieuOfSpace, outputBuff);
				outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).appendChars("detail: ");
			}
			else {
				outputBuff.AppendSpaces(indents, useTabInLieuOfSpace);
			}
			outputBuff.append('[', ']').appendChars(LINE_TERMINATOR);
			return ;
		}
		if (includeKindInfo) {
			getKindInfoYAMLStringChars(indents, thing.getKind(), useTabInLieuOfSpace, outputBuff);
			outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("detail: "); 
		}
		else {
			outputBuff.AppendSpaces(indents, useTabInLieuOfSpace);
		}
		outputBuff.append('[', '\'').append(thing.spec[0]).appendChar('\'');
		for (int i=1;i<thing.spec.length;i++)
		{
			outputBuff.append(',', ' ', '\'').append(thing.spec[i]).appendChar('\'');
		}
		outputBuff.append(']').appendChars(LINE_TERMINATOR);
	}
	
	public static final void getToYAMLStringChars(final DiffsTableRow thing, /*final */int indents, final boolean includeKindInfo, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		//System.out.println("in getToYAMLStringChars for " + thing.getClass().getName() + ", thing.isSingleChgDiffsTableRowAdvcd(): " + thing.isSingleChgDiffsTableRowAdvcd());
		if (thing.isSingleChgDiffsTableRowAdvcd()) {
			getToYAMLStringChars(thing.asSingleChgDiffsTableRowAdvcd(), indents, includeKindInfo, useTabInLieuOfSpace, outputBuff);
		}
		else if (thing.isDiffsNewIndex()) {
			getToYAMLStringChars(thing.asDiffsNewIndex(), indents, includeKindInfo, useTabInLieuOfSpace, outputBuff);
		}
		else if (thing.isSingleChgDiffsTableRow()) {
			getToYAMLStringChars(thing.asSingleChgDiffsTableRow(), indents, includeKindInfo, useTabInLieuOfSpace, outputBuff);
		}
		else if (thing.isMultiChgDiffsTableRow()) {
			getToYAMLStringChars(thing.asMultiChgDiffsTableRow(), indents, includeKindInfo, useTabInLieuOfSpace, outputBuff);
		}
	}
	
	public static final void getToYAMLStringChars(final SingleChgDiffsTableRow thing, /*final */int indents, final boolean includeKindInfo, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		if (indents < 0) {
			indents = 0;
		}
		if (includeKindInfo) {
			getKindInfoYAMLStringChars(indents, thing.getKind(), useTabInLieuOfSpace, outputBuff);
		}
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("objectName: ").append(thing.objectName).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("change: ").append(thing.change).appendChars(LINE_TERMINATOR);
	}
	
	public static final void getToYAMLStringChars(final SingleChgDiffsTableRowAdvcd thing, /*final */int indents, final boolean includeKindInfo, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		//System.out.println("getToYAMLStringChars for SingleChgDiffsTableRowAdvcd - detailedChgs: " + thing.detailedChgs.length);
		if (indents < 0) {
			indents = 0;
		}
		if (includeKindInfo) {
			getKindInfoYAMLStringChars(indents, thing.getKind(), useTabInLieuOfSpace, outputBuff);
		}
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("objectName: ").append(thing.objectName).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("change: ").append(thing.change).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("detailedChanges: ").appendChars(LINE_TERMINATOR);
		int indentsP1 = indents < 0 ? 1 : indents + 1;
		//int indentsP2 = indentsP1 + 1;
		for (int i=0;i<thing.detailedChgs.length;i++)
		{
			outputBuff.AppendSpaces(indentsP1, useTabInLieuOfSpace).appendChars('-', ' ');
			getToYAMLStringChars(thing.detailedChgs[i], 0/*indents*/, false/*includeKindInfo*/, true/*useTabInLieuOfSpace*/, outputBuff);
		}
	}
	public static final void getToYAMLStringChars(final DiffsTableRowChange thing, /*final */int indents, final boolean includeKindInfo, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		if (indents < 0) {
			indents = 0;
		}
		if (includeKindInfo) {
			getKindInfoYAMLStringChars(indents, thing.getKind(), useTabInLieuOfSpace, outputBuff);
		}
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("change: ").append(thing.type).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("detailedChanges: ").appendChars(LINE_TERMINATOR);
		int indentsP1 = indents < 0 ? 1 : indents + 1;
		//int indentsP2 = indentsP1 + 1;
		for (int i=0;i<thing.detailedChgs.length;i++)
		{
			outputBuff.AppendSpaces(indentsP1, useTabInLieuOfSpace).appendChars('-', ' ');
			getToYAMLStringChars(thing.detailedChgs[i], 0/*indents*/, false/*includeKindInfo*/, true/*useTabInLieuOfSpace*/, outputBuff);
		}
	}
	public static final void getToYAMLStringChars(final DiffsNewIndex thing, /*final */int indents, final boolean includeKindInfo, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		if (indents < 0) {
			indents = 0;
		}
		if (includeKindInfo) {
			getKindInfoYAMLStringChars(indents, thing.getKind(), useTabInLieuOfSpace, outputBuff);
		}
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("objectName: ").append(thing.objectName).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("change: ").append(thing.change).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("tableName: ").append(thing.tableName).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("columns: ").appendChars(LINE_TERMINATOR);
		int indentsP1 = indents < 0 ? 1 : indents + 1;
		for (int i=0;i<thing.columns.length;i++)
		{
			outputBuff.AppendSpaces(indentsP1, useTabInLieuOfSpace).append('-', ' ').append(thing.columns[i]).appendChars(LINE_TERMINATOR);
		}
		
	}
	public static final void getToYAMLStringChars(final MultiChgDiffsTableRow thing, /*final */int indents, final boolean includeKindInfo, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		if (indents < 0) {
			indents = 0;
		}
		if (includeKindInfo) {
			getKindInfoYAMLStringChars(indents, thing.getKind(), useTabInLieuOfSpace, outputBuff);
		}
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("objectName: ").append(thing.objectName).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("changes: ").appendChars(LINE_TERMINATOR);
		int indentsP1 = indents < 0 ? 1 : indents + 1;
		int indentsP2 = indentsP1 + 1;
		for (int i=0;i<thing.changes.length;i++)
		{
			outputBuff.AppendSpaces(indentsP1, useTabInLieuOfSpace).append("- ").appendChars(LINE_TERMINATOR);
			getToYAMLStringChars(thing.changes[i], indentsP2, false/*includeKindInfo*/, useTabInLieuOfSpace, outputBuff);
		}
	}
	
	public static final void getToYAMLStringChars(final DiffsTable thing, /*final */int indents, final boolean includeKindInfo, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		if (indents < 0) {
			indents = 0;
		}
		if (includeKindInfo) {
			getKindInfoYAMLStringChars(indents, thing.getKind(), useTabInLieuOfSpace, outputBuff);
		}
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("summaryCategory: ").append(DMCompSummaryCategories.get_CategoryName(thing.summaryCategory)).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("diffsCompMethod: ").append(thing.diffsCompMethod).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("diffsRows: ").appendChars(LINE_TERMINATOR);
		int indentsP1 = indents < 0 ? 1 : indents + 1;
		int indentsP2 = indentsP1 + 1;
		for (int i=0;i<thing.diffsRows.length;i++)
		{
			//System.out.println("thing.diffsRows[" + i + "].class: " + thing.diffsRows[i].getClass().getName());
			outputBuff.AppendSpaces(indentsP1, useTabInLieuOfSpace).append("- ").appendChars(LINE_TERMINATOR);
			getToYAMLStringChars(thing.diffsRows[i], indentsP2, false/*includeKindInfo*/, useTabInLieuOfSpace, outputBuff);
		}
	}
	public static final void getToYAMLStringChars(final DataModelComparisonReport thing, /*final */int indents, final boolean includeKindInfo, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		if (indents < 0) {
			indents = 0;
		}
		if (includeKindInfo) {
			getKindInfoYAMLStringChars(indents, thing.getKind(), useTabInLieuOfSpace, outputBuff);
		}
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("sourceFile: ").append(thing.sourceFile == null ? "" : thing.sourceFile.toString()).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("product: ").append(thing.product).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("diffsSummary: ").append(LINE_TERMINATOR);
		int indentsP1 = indents + 1;
		getToYAMLStringChars(thing.diffsSummary, indentsP1, false/*includeKindInfo*/, useTabInLieuOfSpace, outputBuff);
		int indentsP2 = indents + 2;
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("diffsTables: ").append(LINE_TERMINATOR);
		for (int i=0;i<thing.diffsTables.length;i++)
		{
			outputBuff.AppendSpaces(indentsP1, useTabInLieuOfSpace).append("- ").append(LINE_TERMINATOR);
			getToYAMLStringChars(thing.diffsTables[i], indentsP2, false/*includeKindInfo*/, useTabInLieuOfSpace, outputBuff);
		}
	}
	public static final void getToYAMLStringChars(final DMObject thing, /*final */int indents, final boolean includeKindInfo, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		if (indents < 0) {
			indents = 0;
		}
		if (includeKindInfo) {
			getKindInfoYAMLStringChars(indents, thing.getKind(), useTabInLieuOfSpace, outputBuff);
		}
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("owner: ").append(thing.owner).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("name: ").append(thing.name).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("number: ").append(thing.getOutputNumber()).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("summaryCategory: ").append(DMCompSummaryCategories.get_CategoryName(thing.summaryCategory)).append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("product: ").append(thing.ownerProduct.code).append('<').append(thing.ownerProduct.name).append('>').append(LINE_TERMINATOR)
		.AppendSpaces(indents, useTabInLieuOfSpace).append("isChildObject: ").append(thing.isChildObject()).append(LINE_TERMINATOR)
		;
		if (thing.isChildObject()) {
			outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("parentObjectName: ").append(thing.getParentObjectName()).append(LINE_TERMINATOR);
			DMObject parentObj = thing.getParentObject();
			outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("parentObjectNumber: ").append(parentObj.getOutputNumber()).append(LINE_TERMINATOR);
			outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("childObjectTypeCode: ").append(DMChildObjectTypes.get_ChildObjectTypeCode(thing.getChildObjectType())).append(LINE_TERMINATOR)
			.AppendSpaces(indents, useTabInLieuOfSpace).append("childObjectTypeNumber: ").append(thing.getChildObjectType()).append(LINE_TERMINATOR)
			;
		}
	}

}