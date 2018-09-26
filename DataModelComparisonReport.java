package zz.ebs.dmcomp;

import string.String;
import string.CString;
/*
import core.html.HTMLTag;
import core.html.HTMLTagNames;
import core.html.HTMLTemplate;
*/

import static zz.ebs.dmcomp.DMCompDetailChangedTypes.NOT_A_FROM_TO_CHANGE_FROM_VAL;
import static zz.ebs.dmcomp.DMCompDetailChangedTypes.WEAK_FROM_TO_CHANGE_FROM_VAL;
import static zz.ebs.dmcomp.DMCompDetailChangedTypes.NO_SPEC_ITEM_FOR_DTL_OR_NO_CHG_TYPES_TO_MATCH;
import static zz.ebs.dmcomp.DMCompDetailChangedTypes.NO_DTL_TYPE_MATCH_FOR_FROM_TO_CHANGE;

/**
* Class for providing support for Oracle eBS data model comparison report objects.<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public class DataModelComparisonReport extends Thing implements core.ITrinaryValues, java.io.Serializable {

	/**The class's serial version UID. */
	public static final long serialVersionUID = 0x0207FDA401000000L;	
	
	/**
	* Constant for <code>&quot;DataModelComparison_12.1.3_12.2.6&quot;</code> 
	*/
	public static final String DATA_MODEL_COMP_12_1_3_12_2_6 = CString.valueOf(new byte[]{'D', 'a', 't', 'a', 'M', 'o', 'd', 'e', 'l', 'C', 'o', 'm', 'p', 'a', 'r', 'i', 's', 'o', 'n', '_', '1', '2', '.', '1', '.', '3', '_', '1', '2', '.', '2', '.', '6'}, true, true);
	
	/**
	* Constant for the short code for <code>&quot;DataModelComparison_12.1.3_12.2.6&quot;</code> 
	*/
	public static final String DATA_MODEL_COMP_12_1_3_12_2_6_SHORT_CODE = string.CString.valueOf(new byte[]{'D', 'M', 'C', 'M', '0'}, true, true);
	
	/**
	* The sequence for this class - didn't create a lock as the class is used in single-thread environments only for now.
	*/
	private static int sequence = 0; //NOTE: 
	
	static final int nextSequenceValue() {
		return ++sequence;
	}
	static final int curentSequenceValue() {
		return sequence;
	}
	
	private static int sourceFileNumSeq;
	/**
	* The sequence for this class - didn't create a lock as the class is used in single-thread environments only for now.
	*/
	static final int nextSourceFileNumSeqValue() {
		return ++sourceFileNumSeq;
	}
	static final int currentSourceFileNumSeqValue() {
		return sourceFileNumSeq;
	}
	
	/**
	* Class for providing support for diffs summary objects.<br>
	*
	* @author Marc E. KAMGA
	* @version 1.0
	* @copyright Marc E. KAMGA
	*/
	public static final class DiffsSummary extends Thing implements java.io.Serializable {

		/**The class's serial version UID. */
		public static final long serialVersionUID = 0x0207FDA541000000L;	

		/**
		* The sequence for this class - didn't create a lock as the class is used in single-thread environments only for now.
		*/
		private static int sequence = 0; //NOTE: 
		
		static final int nextSequenceValue() {
			return ++sequence;
		}
		static final int curentSequenceValue() {
			return sequence;
		}
		/**
		* The sequence for summary lines - didn't create a lock as the class is used in single-thread environments only for now.
		*/
		private static int lineSequence = 0;
		
		static final int nextLineSequenceValue() {
			return ++lineSequence;
		}
		
		int Regular_Tables; 
		int Partitioned_Tables; 
		int Queue_Tables; 
		int Index_Orgnized_Tables; 
		int Global_Temp_Tables; 
		int Indexes; 
		int New_Indexes_on_Existing_Tables_$_Columns; 
		int New_Indexes_on_new_Tables_$_Columns; 
		int other_Index_Changes; 
		int Views; 
		int Sequences; 
		int Materialized_Views; 
		int Materialized_View_Logs; 
		int Advanced_Queues; 
		int Triggers; 
		int Packages; 
		int Types; 
		int Security_Object; 
		
		DiffsSummary() {
			
		}
		
		void setDiffs(final byte summaryCategory, final int diffs) {
			switch(summaryCategory)
			{
			case DMCompSummaryCategories.Regular_Tables: this.Regular_Tables = diffs; return ;
			case DMCompSummaryCategories.Partitioned_Tables: this.Partitioned_Tables = diffs; return ;
			case DMCompSummaryCategories.Queue_Tables: this.Queue_Tables = diffs; return ;
			case DMCompSummaryCategories.Index_Orgnized_Tables: this.Index_Orgnized_Tables = diffs; return ;
			case DMCompSummaryCategories.Global_Temp_Tables: this.Global_Temp_Tables = diffs; return ;
			case DMCompSummaryCategories.Indexes: this.Indexes = diffs; return ;
			case DMCompSummaryCategories.New_Indexes_on_Existing_Tables_$_Columns: this.New_Indexes_on_Existing_Tables_$_Columns = diffs; return ;
			case DMCompSummaryCategories.New_Indexes_on_new_Tables_$_Columns: this.New_Indexes_on_new_Tables_$_Columns = diffs; return ;
			case DMCompSummaryCategories.other_Index_Changes: this.other_Index_Changes = diffs; return ;
			case DMCompSummaryCategories.Views: this.Views = diffs; return ;
			case DMCompSummaryCategories.Sequences: this.Sequences = diffs; return ;
			case DMCompSummaryCategories.Materialized_Views: this.Materialized_Views = diffs; return ;
			case DMCompSummaryCategories.Materialized_View_Logs: this.Materialized_View_Logs = diffs; return ;
			case DMCompSummaryCategories.Advanced_Queues: this.Advanced_Queues = diffs; return ;
			case DMCompSummaryCategories.Triggers: this.Triggers = diffs; return ;
			case DMCompSummaryCategories.Packages: this.Packages = diffs; return ;
			case DMCompSummaryCategories.Types: this.Types = diffs; return ;
			case DMCompSummaryCategories.Security_Object: this.Security_Object = diffs; return ;
			}
		}
		
		public int getDiffs(final byte summaryCategory) {
			switch(summaryCategory)
			{
			case DMCompSummaryCategories.Regular_Tables: return this.Regular_Tables; 
			case DMCompSummaryCategories.Partitioned_Tables: return this.Partitioned_Tables; 
			case DMCompSummaryCategories.Queue_Tables: return this.Queue_Tables; 
			case DMCompSummaryCategories.Index_Orgnized_Tables: return this.Index_Orgnized_Tables; 
			case DMCompSummaryCategories.Global_Temp_Tables: return this.Global_Temp_Tables; 
			case DMCompSummaryCategories.Indexes: return this.Indexes; 
			case DMCompSummaryCategories.New_Indexes_on_Existing_Tables_$_Columns: return this.New_Indexes_on_Existing_Tables_$_Columns; 
			case DMCompSummaryCategories.New_Indexes_on_new_Tables_$_Columns: return this.New_Indexes_on_new_Tables_$_Columns; 
			case DMCompSummaryCategories.other_Index_Changes: return this.other_Index_Changes; 
			case DMCompSummaryCategories.Views: return this.Views; 
			case DMCompSummaryCategories.Sequences: return this.Sequences; 
			case DMCompSummaryCategories.Materialized_Views: return this.Materialized_Views; 
			case DMCompSummaryCategories.Materialized_View_Logs: return this.Materialized_View_Logs; 
			case DMCompSummaryCategories.Advanced_Queues: return this.Advanced_Queues; 
			case DMCompSummaryCategories.Triggers: return this.Triggers; 
			case DMCompSummaryCategories.Packages: return this.Packages; 
			case DMCompSummaryCategories.Types: return this.Types; 
			case DMCompSummaryCategories.Security_Object: return this.Security_Object; 
			}
			
			return -1;
		}
		/**
		* {@inheritDoc}
		*/
		public final byte getKind() {return DIFFS_SUMMARY; }
		
		protected int __getTextBufferInitialCapacity() {return 1024;}
		/**
		* {@inheritDoc}
		*/
		public void getText(final boolean useTabIndentInLieuOfSpaceIndent, final string.CharBuffer outputBuffer) {
			DMCompReportThing2StrUtil.getToYAMLStringChars(this, 0/*indents*/, true/*includeKindInfo*/, useTabIndentInLieuOfSpaceIndent, outputBuffer);
		}
		
		public final int getOutputNumber() {
			return number < 0 ? (number = nextSequenceValue()) : number;
		}
		
	}
	
	/**
	* Class for providing support for diffs detailed change objects.<br>
	*
	* @author Marc E. KAMGA
	* @version 1.0
	* @copyright Marc E. KAMGA
	*/
	public static /*final */class DiffsDetailedChange extends Thing implements java.io.Serializable {

		/**The class's serial version UID. */
		public static final long serialVersionUID = 0x0207FEAD41000000L;
		
//
//	***NOTE: moved to class DMCompDetailChangedTypes, which is a more suitable place for these constants declarations...
//
//		public static final byte NO_SPEC_ITEM_FOR_DTL_OR_NO_CHG_TYPES_TO_MATCH = 0;
//		
//		public static final byte NO_DTL_TYPE_MATCH_FOR_FROM_TO_CHANGE = -1;
		
		/**
		* The sequence for this class - didn't create a lock as the class is used in single-thread environments only for now.
		*/
		private static int sequence = 0; //NOTE: 
		
		static final int nextSequenceValue() {
			return ++sequence;
		}
		static final int curentSequenceValue() {
			return sequence;
		}
		
		/**
		* Empty array.<br>
		*/
		public static final DiffsDetailedChange[] EMPTY_ARRAY = new DiffsDetailedChange[0];
		
		/*protected */String[] spec;
		
		/**
		* Constructor.
		*/
		DiffsDetailedChange() {
			this(String.EMPTY_ARRAY);
		}
		/**
		* Constructor.
		*/
		DiffsDetailedChange(String[] spec) {
			this.spec = spec;
		}
		/**
		* {@inheritDoc}
		*/
		public final byte getKind() {
			return DIFFS_DETAILED_CHANGE;
		}
		
		/**
		* Tells if the detailed change has no spec items.<br>
		*/
		public boolean isEmpty() {return spec.length == 0; }
		
		/**
		* Returns the size of the detailed change.
		*/
		public int size() {return spec.length; }
		
		/**
		* Gets the <code>(i + 1)<sup>th</sup></code> spec item.<br>
		*/
		public final String getSpecItem(int i) {
			return spec[i];
		}
		/**
		* Gets the last spec item.<br>
		*/
		public final String getLastSpecItem() {return spec[spec.length - 1]; }
		/**
		* Gets the first spec item.<br>
		*/
		public final String getFirstSpecItem() {return spec[0]; }
		
		/**
		* Gets the <code>(i + 1)<sup>th</sup></code> spec item, cleanses it and returns the cleansed version of it.<br>
		*/
		public final String getCleansedSpecItem(final int i) {
			return trimSpecItem(spec[i]);
		}
		/**
		* Gets the <code>(i + 1)<sup>th</sup></code> spec item, cleanses it and returns the cleansed version of it.<br>
		*/
		public final String getCleansedSpecItem(final int i, final boolean dropFromToOperatorSymbolIfAny) {
			return trimSpecItem(spec[i], dropFromToOperatorSymbolIfAny);
		}
		/**
		* Gets the last spec item, cleanses it and returns the cleansed version of it.<br>
		*/
		public final String getCleansedLastSpecItem() {return trimSpecItem(spec[size() - 1/*spec.length - 1*/]); }
		/**
		* Gets the first spec item, cleanses it and returns the cleansed version of it.<br>
		*/
		public final String getCleansedFirstSpecItem() {return trimSpecItem(spec[0]); }
		
		/**
		* Tells if the detailed change spec starts with a subject of kind table (tab).
		*/
		public boolean startsWithTableSubject() {
			if (isEmpty()) return false;
			String firstSpecItem = trimSpecItem(spec[0]);
			return firstSpecItem.equals(DMCompDetailChangedType.tab.code) || firstSpecItem.equals(DMCompDetailChangedType.tab.code);
		}
		/**
		* Tells if the detailed change spec starts with a subject of kind column (col).
		*/
		public boolean startsWithColumnSubject() {
			if (isEmpty()) return false;
			String firstSpecItem = trimSpecItem(spec[0]);
			return firstSpecItem.equals(DMCompDetailChangedType.col.code) || firstSpecItem.equals(DMCompDetailChangedType.col.code);
		}
		
		/**
		* Trims the supplied detailed change spec item.<br>
		*/
		public static final String trimSpecItem(final String specItem, final boolean dropFromToOperatorSymbolIfAny) {
			if (specItem.isEmpty()) return String.EMPTY;
			char ch = specItem.getFirstChar();
			int start = 0;
			final int len = specItem.length();
			byte startedWithDash = ch == '-' ? yes : no;
			__outer_switch_case_block_:
			switch(ch)
			{
			case ' ':
			case '\t':
			case '-': 
				__skip_leading_spaces_lop:
				do 
				{
					start++;
					if (start == len) return String.EMPTY;
					ch = specItem.getChar(start);
					if (ch == ' ' || ch == '\t') continue __skip_leading_spaces_lop;
					if (startedWithDash == no && ch == '-') {
						startedWithDash = maybe;
						continue __skip_leading_spaces_lop;
					}
					break __outer_switch_case_block_;
				} while (true); //end __skip_leading_spaces_lop
			}
			int lastIndex = len - 1;
			ch = specItem.getChar(lastIndex);
			if (ch != ' ' && ch != '\t' && (!dropFromToOperatorSymbolIfAny || ch != '>')) {
				return start == 0 ? specItem : specItem.substring(start);
			}
			if (ch != '>') { //case "=>" is to be dropped ==> don't decrement for he for loop to work properly
				lastIndex--;
			}
			__skip_trailing_spaces_loop:
			for (;lastIndex>start;lastIndex--)
			{
				ch = specItem.getChar(lastIndex);
				if (ch != ' ' && ch != '\t') {
					if (dropFromToOperatorSymbolIfAny && ch == '>') {
						int j = lastIndex - 1;
						if (j == start) {
							break __skip_trailing_spaces_loop;
						}
						boolean fullOpSymbolAlready = false;
						__skip_from_to_op_loop___:
						do 
						{
							ch = specItem.getChar(j);
							if (ch == '=') {
								if (!fullOpSymbolAlready) {
									fullOpSymbolAlready = true;
									j--;
									if (j == start) {
										lastIndex = j;
										break __skip_trailing_spaces_loop;
									}
									continue __skip_from_to_op_loop___;
								}
								else {
									lastIndex = j;
									break __skip_trailing_spaces_loop;
								}
							}
							else if (ch == ' ' || ch == '\t') {
								j--;
								if (j == start) {
									if (fullOpSymbolAlready) {
										lastIndex = j + 1;
									}
									break __skip_trailing_spaces_loop;
								}
								continue __skip_from_to_op_loop___;
							}
							else {
								if (fullOpSymbolAlready) {
									lastIndex = j;
								}
								break __skip_trailing_spaces_loop;
							}
						} while (true); //end __skip_from_to_op_loop___
					}
					break __skip_trailing_spaces_loop;
				}
			} 
			return specItem.substring(start, lastIndex + 1);
		}
		/**
		* Trims the supplied detailed change spec item.<br>
		*/
		public static final String trimSpecItem(final String specItem) {
			return trimSpecItem(specItem, false/*dropFromToOperatorSymbolIfAny*/);
		}
		/**
		* Checks if the last spec item actually matches one of the change types contained in the supplied set.<br>
		* @return <code>null</code> if the last spec item matches neither of the change types contained in the supplied set; the matched {@link ChangeType ChangeType}, otherwise.
		*/
		public final ChangeType checkIfEndsWithChangeType(final ChangeTypeSet allChangeTypes) {
			if (spec.length == 0) return null;
			String lastItem = trimSpecItem(spec[spec.length - 1]);
			ChangeType chgType = allChangeTypes.getByName(lastItem);
			if (chgType == null) {
				chgType = allChangeTypes.getByCode(lastItem);
			}
			return chgType;
		}
		/**
		* Checks if the detailed change is a change for a feature from a value to another value.<br>
		* @return {@link #NOT_A_FROM_TO_CHANGE_FROM_VAL NOT_A_FROM_TO_CHANGE_FROM_VAL} if the change is not a change from a value to another; {@link #WEAK_FROM_TO_CHANGE_FROM_VAL WEAK_FROM_TO_CHANGE_FROM_VAL} if the change is looks like a change from a value to another value but the from value is empty and there's no detail changed type; the negated value of the matched detail change type if the change looks like a change from a value to another value but the from value is empty; <code>0</code> if the change denotes a change from a value to another but there is no associated detail changed type either in the spec or in the supplied set; the number of the matched detail changed type for the identified change from a value to another oherwise.
		*/
		public final short checkIfFromToChange(final DMCompDetailChangedTypeSet allDetailChangedTypes) {
			if (spec.length < 2) return NOT_A_FROM_TO_CHANGE_FROM_VAL;
			String prevToLastSpecItm = trimSpecItem(spec[spec.length - 2]);
			int lastIndex = prevToLastSpecItm.length() - 1;
			if (lastIndex/*prevToLastSpecItm.length() - 1*/ < 3 || prevToLastSpecItm.getChar(lastIndex) != '>') return NOT_A_FROM_TO_CHANGE_FROM_VAL;
			char ch = prevToLastSpecItm.getChar(--lastIndex);
			//System.out.println("DiffsDetailedChange::checkIfFromToChange - YEAH!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! - prevToLastSpecItm: " + prevToLastSpecItm + ", lastIndex: " + lastIndex + ", ch; '" + ch + "'");
			if (ch != '=') {
				if (ch != ' ' && ch != '\t') return NOT_A_FROM_TO_CHANGE_FROM_VAL;
				__skip_spaces:
				do 
				{
					lastIndex--;
					if (lastIndex < 0) return NOT_A_FROM_TO_CHANGE_FROM_VAL;
					ch = prevToLastSpecItm.getChar(lastIndex);
					if (ch == ' ' || ch == '\t') {
						continue __skip_spaces;
					}
					else if (ch != '=') return NOT_A_FROM_TO_CHANGE_FROM_VAL;
					break __skip_spaces;
				} while (true); //end __skip_spaces
			}
			lastIndex--;
			if (lastIndex < 0) {
				if (spec.length == 2 || allDetailChangedTypes == null) { //Case there's no spec item for detail type or there is no change types to match
					return WEAK_FROM_TO_CHANGE_FROM_VAL;
				}
				DMCompDetailChangedType detailChgdType = allDetailChangedTypes.getByCode(trimSpecItem(spec[spec.length - 3]));
				return detailChgdType == null ? WEAK_FROM_TO_CHANGE_FROM_VAL : (short)-detailChgdType.number;
				//return WEAK_FROM_TO_CHANGE_FROM_VAL;
			}
			ch = prevToLastSpecItm.getChar(lastIndex);
			if (ch == ' ' || ch == '\t') {
				__skip__spaces:
				do 
				{
					lastIndex--;
					if (lastIndex < 0) return NOT_A_FROM_TO_CHANGE_FROM_VAL;
					ch = prevToLastSpecItm.getChar(lastIndex);
					if (ch == ' ' || ch == '\t') {
						continue __skip__spaces;
					}
					break __skip__spaces;
				} while (true); //end __skip__spaces
			}
			if (spec.length == 2 || allDetailChangedTypes == null) { //Case there's no spec item for detail type or there is no change types to match
				return NO_SPEC_ITEM_FOR_DTL_OR_NO_CHG_TYPES_TO_MATCH; //0; 
			}
			DMCompDetailChangedType detailChgdType = allDetailChangedTypes.getByCode(trimSpecItem(spec[spec.length - 3]));
			return detailChgdType == null ? NO_DTL_TYPE_MATCH_FOR_FROM_TO_CHANGE/*(short)0*/ : detailChgdType.number;
		}
		
		/**
		* @param pathEnd the index of the last spec item that is part of detail path, plus one - must ensure that the value is such that the spec items that make up the leaf node are excluded from the detail path
		* @param allDetailChangedTypes the set holding all the detail changed types - serves to create new detail changed types
		* @param failIfNewDetailTypeAndNullSet 
		* @param tempBuffer the temp character buffer to use whenever needed - may be null
		*/
		public final DMCompDetailPath getDetailPath(final int pathEnd, final boolean failIfNewDetailTypeAndNullSet, final DMCompDetailChangedTypeSet allDetailChangedTypes, string.CharBuffer tempBuffer) {
			if (pathEnd == 0) {
				return DMCompDetailPath.EMPTY;
			}
			String[] nodes = null; //new String[(pathEnd + 1) >>> 1];
			int nodeItemsCount = 0;
			int i=0;
			boolean atLeastOneCLeansed = false;
			String detailType = null;
			main_loop:
			do
			{
				String specItem = trimSpecItem(spec[i]);
				if (!atLeastOneCLeansed && specItem != spec[i]) { //case first cleansed spec item spotted
					nodes = new String[(pathEnd + 1) >>> 1];
					if (i > 0) {
						System.arraycopy(spec, 0, nodes, 0, i);
					}
					atLeastOneCLeansed = true;
				}
				DMCompDetailChangedType detailChgdType = allDetailChangedTypes.getByCode(specItem);
				if (detailChgdType == null) {
					detailChgdType = allDetailChangedTypes.getByName(specItem);
				}
				//System.out.println("DiffsDetailedChange - specItem: " + specItem + ", detailChgdType: " + detailChgdType);
				if (detailChgdType == null) {
					if (allDetailChangedTypes == null) {
						if (failIfNewDetailTypeAndNullSet) return null;
						detailType = specItem;
					}
					else {
						detailChgdType = new DMCompDetailChangedType((short)(allDetailChangedTypes.size() + 1)/*number*/, specItem/*code*/, specItem/*name*/);
						allDetailChangedTypes.add(detailChgdType);
						detailType = detailChgdType.code;
					}
				}
				else {
					detailType = detailChgdType.code;
				}
				//if (detailChgdType != null) {
					i++;
					if (i == pathEnd) {
						if (!atLeastOneCLeansed) {
							nodes = new String[(pathEnd & 0x1) != 0 ? pathEnd + 1 : pathEnd]; //BUG-FIX-2017-06-21 - was computing the array length as follows: "(pathEnd + 1) >>> 1"
							try 
							{
								System.arraycopy(spec, 0, nodes, 0, --i);
							}
							catch(IndexOutOfBoundsException iobe)
							{
								System.out.println("!!!!!!!!!!!!!!!!!::::::::::::::: - i: " + i + ", nodes.length: " +  nodes.length + ", pathEnd: " +  pathEnd + ", this: " + this); //
								throw iobe;
							}
							try 
							{
								nodes[i] = detailType/*detailChgdType.code*/;
								nodes[++i] = String.EMPTY;
							}
							catch(IndexOutOfBoundsException iobe)
							{
								System.out.println("DiffsDetailedChange - i: " + i + ", pathEnd: " + pathEnd + " self: \t\n" + this);
								throw iobe;
							}
							atLeastOneCLeansed = true; //cleansing consists in the normalization of the array holding the nodes - a dummy item was added to make its length even
							break main_loop;
						}
						int finalArrLen = nodeItemsCount + 2;
						if (finalArrLen != nodes.length) {
							String[] nodes_ = new String[finalArrLen];
							System.arraycopy(nodes, 0, nodes_, 0, nodeItemsCount);
							nodes = nodes_;
						}
						nodes[nodeItemsCount] = detailType/*detailChgdType.code*/;
						nodes[++nodeItemsCount] = String.EMPTY;
						break main_loop;
					}
					specItem = trimSpecItem(spec[i]);
					//System.out.println("DiffsDetailedChange - specItem: " + specItem + ", atLeastOneCLeansed: " + atLeastOneCLeansed);
					if (!atLeastOneCLeansed && specItem != spec[i]) { //case first cleansed spec item spotted
						nodes = new String[(pathEnd & 0x1) != 0 ? pathEnd + 1 : pathEnd]; //BUG-FIX-2017-06-21 - was computing the array length as follows: "(pathEnd + 1) >>> 1"
						System.arraycopy(spec, 0, nodes, 0, i - 1);
						atLeastOneCLeansed = true;
					}
					if (!specItem.isEmpty() && specItem.getLastChar() == '(' && i + 2 < pathEnd && trimSpecItem(spec[i + 2]).equals(")")) {
						////System.out.println("DiffsDetailedChange::::::::::::::::::::::::::: - specItem: " + specItem + ", spec[i + 1]: " + spec[i + 1]);
						if (!atLeastOneCLeansed && specItem != spec[i]) { //case first cleansed spec item spotted
							nodes = new String[(pathEnd + 1) >>> 1];
							System.arraycopy(spec, 0, nodes, 0, i - 1);
							atLeastOneCLeansed = true;
						}
						String qualifier = trimSpecItem(spec[i + 1]);
						if (tempBuffer == null) {
							tempBuffer = new string.CharBuffer(specItem.length() + qualifier.length() + 1);
						}
						else {
							tempBuffer.resetLimitOnly();
							tempBuffer.ensureCanAddNMoreChars(specItem.length() + qualifier.length() + 1);
						}
						specItem = tempBuffer.append(specItem).append(qualifier).append(')').ToString(true/*createAsCStringIfPossible*/);
						i += 3;
					}
					else {
						i++;
					}
					if (atLeastOneCLeansed) {
						int minArrLen = nodeItemsCount + 2;
						if (minArrLen > nodes.length) {
							int newLen = nodes.length + (nodes.length >>> 1);
							String[] nodes_ = new String[newLen < minArrLen ? minArrLen : newLen];
							System.arraycopy(nodes, 0, nodes_, 0, nodeItemsCount);
							nodes = nodes_;
						}
						nodes[nodeItemsCount++] = detailType/*detailChgdType.code*/;
						nodes[nodeItemsCount++] = specItem;
					}
					if (i<pathEnd) continue main_loop;
					break main_loop;
				//}
			} while (true); //end main_loop
			//System.out.println("DiffsDetailedChange - pathEnd: " +  pathEnd + ", atLeastOneCLeansed: " + atLeastOneCLeansed);
			return atLeastOneCLeansed ? new DMCompDetailPath(nodes, (byte)(nodes.length >>> 1)) : new DMCompDetailPath(spec, (byte)(pathEnd >>> 1));
		}
		/**
		* @param checkRltHolder the holder of the check result details - must be at least an array of 3 items; msut not be null
		* @param tempBuffer the buffer to ue whenever needed - can be null
		*/
		public final byte checkText(final String[] checkRltHolder, string.CharBuffer tempBuffer) {
			if (spec == null || this.isEmpty()) {
				//checkRltHolder[0] = String.EMPTY;
				return 0; //String.EMPTY_ARRAY; 
			}
			final int sz = this.size();
			String specItem = trimSpecItem(spec[0]);
			if (!DMCompDetailChangedType.text.code.equals(specItem) && !DMCompDetailChangedType.text.name.equals(specItem)) {
				return -1; //null;
			}
			if (sz == 1) {
				checkRltHolder[0] = DMCompDetailChangedType.text.code;
				checkRltHolder[1] = String.EMPTY;
				return 2; //new String[]{DMCompDetailChangedType.text.code, String.EMPTY};
			}
			if (tempBuffer == null) {
				tempBuffer = new string.CharBuffer(128);
			}
			else {
				tempBuffer.resetLimitOnly();
				tempBuffer.ensureCanAddNMoreChars(128);
			}
			String href = null;
			int i = 1;
			__skip_empty_spec_items_loop_:
			do 
			{
				specItem = trimSpecItem(spec[i]);
				if (specItem.isEmpty()) {
					i++;
					if (i == sz) {
						checkRltHolder[0] = DMCompDetailChangedType.text.code;
						checkRltHolder[1] = String.EMPTY;
						return 2; //return new String[]{DMCompDetailChangedType.text.code, String.EMPTY};
					}
					continue __skip_empty_spec_items_loop_;
				}
				break __skip_empty_spec_items_loop_;
			} while (true); //end __skip_empty_spec_items_loop_
			if (core.html.SharedStringConstants.href.equals(specItem)) { //case the first true spec item is href
				i++;
				if (i == sz) {
					checkRltHolder[0] = core.html.SharedStringConstants.href;
					checkRltHolder[1] = String.EMPTY;
					return 2; //return new String[]{core.html.SharedStringConstants.href, String.EMPTY};
				}
				href = trimSpecItem(spec[i]);
				i++;
				if (i == sz) {
					checkRltHolder[0] = core.html.SharedStringConstants.href;
					checkRltHolder[1] = href;
					return 2; //new String[]{core.html.SharedStringConstants.href, href};
				}
				__skip_empty_spec_items_loop:
				do 
				{
					specItem = trimSpecItem(spec[i]);
					if (specItem.isEmpty()) {
						i++;
						if (i == sz) {
							checkRltHolder[0] = core.html.SharedStringConstants.href;
							checkRltHolder[1] = href;
							return 2; //return new String[]{core.html.SharedStringConstants.href, href};
						}
						continue __skip_empty_spec_items_loop;
					}
					break __skip_empty_spec_items_loop;
				} while (true); //end __skip_empty_spec_items_loop
			} //end case the first true spec item is href
			tempBuffer.appendChars(specItem);
			i++;
			main_loop:
			for (;i<sz;i++)
			{
				specItem = trimSpecItem(spec[i]);
				if (specItem.isEmpty()) continue main_loop;
				if (href == null && core.html.SharedStringConstants.href.equals(specItem)) {
					i++;
					if (i == sz) {
						href = String.EMPTY;
						break main_loop;
					}
					href = trimSpecItem(spec[i]);
					continue main_loop;
				}
				tempBuffer.append(' ').appendChars(specItem);
			}
			//return href == null ? new String[]{} : 
			//						new String[]{}
			//						;
			if (href != null) {
				checkRltHolder[0] = core.html.SharedStringConstants.href;
				checkRltHolder[1] = href;
				checkRltHolder[2] = tempBuffer.ToString(true/*createAsCStringIfPossible*/);
				return 3;
			}
			checkRltHolder[0] = DMCompDetailChangedType.text.code;
			checkRltHolder[1] = tempBuffer.ToString(true/*createAsCStringIfPossible*/);
			return 2;
		}
		
		protected int __getTextBufferInitialCapacity() {return 160;}
		/**
		* {@inheritDoc}
		*/
		public void getText(final boolean useTabIndentInLieuOfSpaceIndent, final string.CharBuffer outputBuffer) {
			DMCompReportThing2StrUtil.getToYAMLStringChars(this, 0/*indents*/, true/*includeKindInfo*/, useTabIndentInLieuOfSpaceIndent, outputBuffer);
		}
		
		public final int getOutputNumber() {
			return number < 0 ? (number = nextSequenceValue()) : number;
		}
	}
	
	/**
	* Class for providing support for diffs table rows.<br>
	*
	* @author Marc E. KAMGA
	* @version 1.0
	* @copyright Marc E. KAMGA
	*/
	public static abstract class DiffsTableRow extends Thing implements java.io.Serializable {

		public static final DiffsTableRow[] EMPTY_ARRAY = new DiffsTableRow[0];
		/**
		* The sequence for this class - didn't create a lock as the class is used in single-thread environments only for now.
		*/
		private static int sequence = 0; //NOTE: 
		
		static final int nextSequenceValue() {
			return ++sequence;
		}
		static final int curentSequenceValue() {
			return sequence;
		}

		protected String objectName;
		
		/**
		* Constructor.<br>
		*/
		DiffsTableRow() {
			this(String.EMPTY);
		}
		/**
		* Constructor.<br>
		*/
		DiffsTableRow(String objectName) {
			this.objectName = objectName;
		}
		
		/**
		* Gets the name of the object of the diffs table row.<br>
		*/
		public String getObjectName() {return objectName; }
		
		/**
		* Tells if this <code>DiffsTableRow</code> is multi-change.<br>
		*/
		public abstract boolean isMultiChange();
		/**
		* Returns the number of changes.<br>
		*/
		public abstract int getNumberOfChanges();
		/**
		* Gets the (single/first) change applied on the changed object.<br>
		*/
		public abstract String getChange();
		/**
		* Gets the <code>(i + 1)<sup>th</sup></code> change applied on the changed object.<br>
		*/
		public abstract String getChange(int i);
		
		
		
		/**
		* Tells if this <code>DiffsTableRow</code> has detailed change items.<br>
		*/
		public boolean hasDetailedChanges() {return false; }
		/**
		* Returns the number of detailed changes that this this <code>DiffsTableRow</code> has.<br>
		*/
		public int getNumberOfDetailedChanges() {return 0; }
		/**
		* Gets the <code>(i + 1)<sup>th</sup></code> detailed change.<br>
		*/
		public DiffsDetailedChange getDetailedChange(int i) {
			throw new IndexOutOfBoundsException(
			"DataModelComparisonReport::DiffsTableRow::getDetailedChange-1: index out f bounds(" + i + ")"
			);
		}
		
		/**
		* Tells if this <code>DiffsTableRow</code> is an instance of {@link DiffsNewIndex DiffsNewIndex} class.<br>
		*/
		public boolean isDiffsNewIndex() {return false; }
		/**
		* @throws ClassCastException if this <code>DiffsTableRow</code> is not an instance of {@link DiffsNewIndex DiffsNewIndex} class.<br>
		*/
		public DiffsNewIndex asDiffsNewIndex() {return (DiffsNewIndex)this; }
		
		/**
		* Tells if this <code>DiffsTableRow</code> is an instance of {@link SingleChgDiffsTableRowAdvcd SingleChgDiffsTableRowAdvcd} class.<br>
		*/
		public boolean isSingleChgDiffsTableRowAdvcd() {
			return false;
		}
		/**
		* @throws ClassCastException if this <code>DiffsTableRow</code> is not an instance of {@link SingleChgDiffsTableRowAdvcd SingleChgDiffsTableRowAdvcd} class.<br>
		*/
		public SingleChgDiffsTableRowAdvcd asSingleChgDiffsTableRowAdvcd() {return (SingleChgDiffsTableRowAdvcd)this; }
		
		/**
		* Tells if this <code>DiffsTableRow</code> is an instance of {@link SingleChgDiffsTableRow SingleChgDiffsTableRow} class.<br>
		*/
		public boolean isSingleChgDiffsTableRow() {return false; }
		/**
		* @throws ClassCastException if this <code>DiffsTableRow</code> is not an instance of {@link SingleChgDiffsTableRow SingleChgDiffsTableRow} class.<br>
		*/
		public SingleChgDiffsTableRow asSingleChgDiffsTableRow() {return (SingleChgDiffsTableRow)this; }
		
		
		/**
		* Tells if this <code>DiffsTableRow</code> is an instance of {@link MultiChgDiffsTableRow MultiChgDiffsTableRow} class.<br>
		*/
		public boolean isMultiChgDiffsTableRow() {return false; }
		/**
		* @throws ClassCastException if this <code>DiffsTableRow</code> is not an instance of {@link MultiChgDiffsTableRow MultiChgDiffsTableRow} class.<br>
		*/
		public MultiChgDiffsTableRow asMultiChgDiffsTableRow() {return (MultiChgDiffsTableRow)this; }
		
		
		
		protected int __getTextBufferInitialCapacity() {return 256;}
		/**
		* {@inheritDoc}
		*/
		public void getText(final boolean useTabIndentInLieuOfSpaceIndent, final string.CharBuffer outputBuffer) {
			DMCompReportThing2StrUtil.getToYAMLStringChars(this, 0/*indents*/, true/*includeKindInfo*/, useTabIndentInLieuOfSpaceIndent, outputBuffer);
		}
		
		public final int getOutputNumber() {
			return number < 0 ? (number = nextSequenceValue()) : number;
		}
		
		
	}
	
	/**
	* Class for providing support for diffs table rows.<br>
	*
	* @author Marc E. KAMGA
	* @version 1.0
	* @copyright Marc E. KAMGA
	*/
	public static /*final */class SingleChgDiffsTableRow extends DiffsTableRow {

		/**The class's serial version UID. */
		public static final long serialVersionUID = 0x0207FEAF81000000L;
		
		public static final SingleChgDiffsTableRow[] EMPTY_ARRAY = new SingleChgDiffsTableRow[0];

		protected String change;
		
		/**
		* Constructor.<br>
		*/
		SingleChgDiffsTableRow() {
			this(String.EMPTY);
		}
		/**
		* Constructor.<br>
		*/
		SingleChgDiffsTableRow(String objectName) {
			this(objectName, String.EMPTY);
		}
		/**
		* Constructor.<br>
		*/
		SingleChgDiffsTableRow(String objectName, String change) {
			super(objectName);
			this.change = change;
		}
		
		/**
		* Returns the name of the object that is changed or newly introduced.<br>
		* That object is referred to as the changed object.<br>
		*/
		public String getObjectName() {return objectName; }
		/**
		* {@inheritDoc}
		*/
		public final boolean isMultiChange() {return false; }
		/**
		* {@inheritDoc}
		*/
		public final int getNumberOfChanges() {return 1; }
		/**
		* {@inheritDoc}
		*/
		public final String getChange() {return change; }
		/**
		* {@inheritDoc}
		*/
		public final String getChange(int i) {return change; }
		
		/**
		* {@inheritDoc}
		* @return {@code true}
		*/
		public final boolean isSingleChgDiffsTableRow() {return true; }
		/**
		* {@inheritDoc}
		* @return {@code this}
		*/
		public final SingleChgDiffsTableRow asSingleChgDiffsTableRow() {return this; }
		
		
		protected int __getTextBufferInitialCapacity() {return 256;}
		/**
		* {@inheritDoc}
		*/
		public void getText(final boolean useTabIndentInLieuOfSpaceIndent, final string.CharBuffer outputBuffer) {
			DMCompReportThing2StrUtil.getToYAMLStringChars(this, 0/*indents*/, true/*includeKindInfo*/, useTabIndentInLieuOfSpaceIndent, outputBuffer);
		}
		
	}
	/**
	* Class for providing support for advanced diffs table rows.<br>
	*
	* @author Marc E. KAMGA
	* @version 1.0
	* @copyright Marc E. KAMGA
	*/
	public static /*final */class SingleChgDiffsTableRowAdvcd extends SingleChgDiffsTableRow {

		/**The class's serial version UID. */
		public static final long serialVersionUID = 0x0207FEE471000000L;

		DiffsDetailedChange[] detailedChgs;
		
		/**
		* Constructor.<br>
		*/
		SingleChgDiffsTableRowAdvcd() {
			this(String.EMPTY);
		}
		/**
		* Constructor.<br>
		*/
		SingleChgDiffsTableRowAdvcd(String objectName) {
			this(objectName, String.EMPTY);
		}
		/**
		* Constructor.<br>
		*/
		SingleChgDiffsTableRowAdvcd(String objectName, String change) {
			super(objectName, change);
			this.detailedChgs = DiffsDetailedChange.EMPTY_ARRAY;
		}
		/**
		* Constructor.<br>
		*/
		SingleChgDiffsTableRowAdvcd(final String objectName, final String change, final int anticipatedMinNumOfDetailedChgs) {
			super(objectName, change);
			this.detailedChgs = new DiffsDetailedChange[anticipatedMinNumOfDetailedChgs];
		}
		
		/**
		* {@inheritDoc}
		*/
		public final boolean hasDetailedChanges() {return getNumberOfDetailedChanges() > 0; }
		/**
		* {@inheritDoc}
		*/
		public int getNumberOfDetailedChanges() {return detailedChgs.length; }
		/**
		* {@inheritDoc}
		*/
		public DiffsDetailedChange getDetailedChange(int i) {
			return detailedChgs[i];
		}
		
		/**
		* {@inheritDoc}
		* @return <code>true</code>
		*/
		public final boolean isSingleChgDiffsTableRowAdvcd() {return true; }
		/**
		* {@inheritDoc}
		* @return <code>this</code>
		*/
		public final SingleChgDiffsTableRowAdvcd asSingleChgDiffsTableRowAdvcd() {return this; }
		
		
		protected int __getTextBufferInitialCapacity() {return 512;}
		/**
		* {@inheritDoc}
		*/
		public void getText(final boolean useTabIndentInLieuOfSpaceIndent, final string.CharBuffer outputBuffer) {
			DMCompReportThing2StrUtil.getToYAMLStringChars(this, 0/*indents*/, true/*includeKindInfo*/, useTabIndentInLieuOfSpaceIndent, outputBuffer);
		}
		
	}
	
	/**
	* Class for providing support for diffs table rows.<br>
	*
	* @author Marc E. KAMGA
	* @version 1.0
	* @copyright Marc E. KAMGA
	*/
	public static /*final */class DiffsNewIndex extends SingleChgDiffsTableRow {

		/**The class's serial version UID. */
		public static final long serialVersionUID = 0x0207FEAF81000000L;

		protected String tableName;
		protected String[] columns;
		
		/**
		* Constructor.<br>
		*/
		DiffsNewIndex() {
			this(String.EMPTY);
		}
		/**
		* Constructor.<br>
		*/
		DiffsNewIndex(String objectName) {
			this(objectName, String.EMPTY, String.EMPTY_ARRAY);
		}
		/**
		* Constructor.<br>
		*/
		DiffsNewIndex(String objectName, String tableName, String[] columns) {
			super(objectName);
			this.columns = columns;
			this.tableName = tableName;
		}
		
		/**
		* Returns the number of columns that the new index is comprised of.<br>
		*/
		public int getNumberOfColumns() {
			return columns.length;
		}
		/**
		* Gets the <code>(i + 1)<sup>th</sup></code> column of the new index.
		*/
		public String getColumn(int i) {
			return columns[i];
		}
		
		/**
		* {@inheritDoc}
		* @return <code>true</code>
		*/
		public final boolean isDiffsNewIndex() {return true; }
		/**
		* {@inheritDoc}
		* @return <code>this</code>
		*/
		public final DiffsNewIndex asDiffsNewIndex() {return this; }
		
		
		protected int __getTextBufferInitialCapacity() {return 256;}
		/**
		* {@inheritDoc}
		*/
		public void getText(final boolean useTabIndentInLieuOfSpaceIndent, final string.CharBuffer outputBuffer) {
			DMCompReportThing2StrUtil.getToYAMLStringChars(this, 0/*indents*/, true/*includeKindInfo*/, useTabIndentInLieuOfSpaceIndent, outputBuffer);
		}
		/**
		* Serializes the columns of this new index into the supplied output buffer.<br>
		*/
		public void getColumns(string.CharBuffer outputBuff) {
			final int colsCount = getNumberOfColumns();
			if (colsCount == 0) return;
			outputBuff.ensureCanAddNMoreChars((colsCount << 4) + (colsCount << 2));
			outputBuff.appendChars(DiffsDetailedChange.trimSpecItem(columns[0])); //NOTE: it can happen that the attempt to remove start dash character from the first column didn't succeed ==> try again here...
			for (int i=1;i<colsCount;i++)
			{
				outputBuff.append(',').appendChars(columns[i]);
			}
		}
		
		/**
		* @return <code>null</code> if the supplied serialized string - with comma as separator - is actually the serialized string of a single column name, which of course involves no separator; a {@link String String} array containing the elementary columns that make up the supplied list of index columns, otherwise.
		*/
		public static final String[] splitColumns(final String columns) {
			final int len = columns.length();
			String[] cols = null;
			int colsCount = 0;
			int start = 0, j= 0;
			int i=0;
			String col = null;
			boolean firstColDirtyStattCharAlready = false;
			char ch;
			main_for_loop:
			for (;i<len;i++)
			{
				ch = columns.getChar(i);
				if (ch == ',') {
					if (cols == null) {
						cols = new String[4];
					}
					if (i == start) continue main_for_loop;
					__trim_leading_space_loop:
					do 
					{
						if (start == i) { //case empty string
							start = i + 1;
							continue main_for_loop;
						}
						ch = columns.getChar(start);
						if (ch == ' ' || ch == '\t') {
							start++;
							continue __trim_leading_space_loop;
						}
						else if (!firstColDirtyStattCharAlready && ch == '-' && colsCount == 0) {
							firstColDirtyStattCharAlready = true;
							start++;
							continue __trim_leading_space_loop;
						}
						break __trim_leading_space_loop;
					} while (true); //end __trim_leading_space_loop
					j = i;
					__trim_leading_spaces_loop:
					do 
					{
						j--;
						if (i == start) {
							j++;
							break __trim_leading_spaces_loop;
						}
						ch = columns.getChar(start);
						if (ch == ' ' || ch == '\t') {
							continue __trim_leading_spaces_loop;
						}
						j++;
						break __trim_leading_spaces_loop;
					} while (true); //end __trim_leading_spaces_loop
					col = columns.substring(start, j);
					if (colsCount == 0) {
						cols[0] = col;
						colsCount = 1;
						start = i + 1;
						continue main_for_loop;
					}
					else if (colsCount >= cols.length) {
						int newLen = cols.length + (cols.length >>> 1);
						String[] arr = new String[newLen <= colsCount ? colsCount + 1 : newLen];
						System.arraycopy(cols, 0, arr, 0, colsCount);
						cols = arr;
					}
					cols[colsCount++] = col;
					start = i + 1;
					continue main_for_loop;
				}
			} //end main_for_loop
			if (cols == null) { //NOTE: decision to return null reverted!!!//case single cleansed column name ==> return null to indicate that fact
				return new String[]{columns}; //null;
			}
			__trim_leading_space_loop:
			do 
			{
				if (start == i) { //case empty string
					break __trim_leading_space_loop;
				}
				ch = columns.getChar(start);
				if (ch == ' ' || ch == '\t') {
					start++;
					continue __trim_leading_space_loop;
				}
				else if (!firstColDirtyStattCharAlready && ch == '-' && colsCount == 0) {
					firstColDirtyStattCharAlready = true;
					start++;
					continue __trim_leading_space_loop;
				}
				break __trim_leading_space_loop;
			} while (true); //end __trim_leading_space_loop
			if (start < i) {
				j = i;
				__trim_leading_spaces_loop:
				do 
				{
					j--;
					if (i == start) {
						j++;
						break __trim_leading_spaces_loop;
					}
					ch = columns.getChar(start);
					if (ch == ' ' || ch == '\t') {
						continue __trim_leading_spaces_loop;
					}
					j++;
					break __trim_leading_spaces_loop;
				} while (true); //end __trim_leading_spaces_loop
				if (cols.length != colsCount + 1) {
					String[] cols_ = new String[colsCount + 1];
					System.arraycopy(cols, 0, cols_, 0, colsCount);
					cols_[colsCount] = columns.substring(start, i);
					return cols_;
				}
				cols[colsCount] = columns.substring(start, i);
				return cols;
			}
			else {
				if (colsCount == 0) {
					//NOTE: should never get here, but...
					return new String[]{String.EMPTY}; 
				}
				if (cols.length != colsCount) {
					String[] cols_ = new String[colsCount];
					System.arraycopy(cols, 0, cols_, 0, colsCount);
					return cols_;
				}
				return cols;
			}
		}
		
	}
	
	/**
	* Class for providing support for diffs table row changes.<br>
	*
	* @author Marc E. KAMGA
	* @version 1.0
	* @copyright Marc E. KAMGA
	*
	*/
	public static class DiffsTableRowChange extends Thing implements java.io.Serializable {

		/**The class's serial version UID. */
		public static final long serialVersionUID = 0x02082FA661B6928AL;
		/**
		* The sequence for this class - didn't create a lock as the class is used in single-thread environments only for now.
		*/
		private static int sequence = 0; //NOTE: 
		
		static final int nextSequenceValue() {
			return ++sequence;
		}
		static final int curentSequenceValue() {
			return sequence;
		}
		
		/**
		* Empty array
		*/
		public static final DiffsTableRowChange[] EMPTY_ARRAY = new DiffsTableRowChange[0];
		
		String type;
		DiffsDetailedChange[] detailedChgs;
		
		/**
		* Constructor.<br>
		*/
		DiffsTableRowChange(String type, DiffsDetailedChange[] detailedChgs) {
			this.type = type;
			this.detailedChgs = detailedChgs;
		}
		/**
		* Constructor.<br>
		*/
		DiffsTableRowChange(String type) {
			this(type, DiffsDetailedChange.EMPTY_ARRAY);
		}
		
		
		/**
		* {@inheritDoc}
		*/
		public final boolean hasDetailedChanges() {return getNumberOfDetailedChanges() > 0; }
		/**
		* {@inheritDoc}
		*/
		public int getNumberOfDetailedChanges() {return detailedChgs.length; }
		/**
		* {@inheritDoc}
		*/
		public DiffsDetailedChange getDetailedChange(int i) {
			return detailedChgs[i];
		}
		
		
		/**
		* {@inheritDoc}
		*/
		public final byte getKind() {
			return DIFFS_TABLE_ROW_CHANGE;
		}
		
		protected int __getTextBufferInitialCapacity() {return 256;}
		/**
		* {@inheritDoc}
		*/
		public void getText(final boolean useTabIndentInLieuOfSpaceIndent, final string.CharBuffer outputBuffer) {
			DMCompReportThing2StrUtil.getToYAMLStringChars(this, 0/*indents*/, true/*includeKindInfo*/, useTabIndentInLieuOfSpaceIndent, outputBuffer);
		}
		
		public final int getOutputNumber() {
			return number < 0 ? (number = nextSequenceValue()) : number;
		}
		
	}
	
	
	/**
	* Class for providing support for multi-change diffs table rows.<br>
	*
	* @author Marc E. KAMGA
	* @version 1.0
	* @copyright Marc E. KAMGA
	*/
	public static /*final */class MultiChgDiffsTableRow extends DiffsTableRow {

		/**The class's serial version UID. */
		public static final long serialVersionUID = 0x02082F8E91B6928AL;
		
		public static final MultiChgDiffsTableRow[] EMPTY_ARRAY = new MultiChgDiffsTableRow[0];

		protected DiffsTableRowChange[] changes;
		
		/**
		* Constructor.<br>
		*/
		MultiChgDiffsTableRow() {
			this(String.EMPTY);
		}
		/**
		* Constructor.<br>
		*/
		MultiChgDiffsTableRow(String objectName, DiffsTableRowChange[] changes) {
			super(objectName);
			this.changes = changes;
		}
		/**
		* Constructor.<br>
		*/
		MultiChgDiffsTableRow(String objectName) {
			this(objectName, DiffsTableRowChange.EMPTY_ARRAY);
		}
		
		/**
		* {@inheritDoc}
		* @return <code>true</code>
		*/
		public final boolean isMultiChgDiffsTableRow() {
			return true;
		}
		/**
		* {@inheritDoc}
		* @return <code>this</code>
		*/
		public final MultiChgDiffsTableRow asMultiChgDiffsTableRow() {return this; }
		
		/**
		* {@inheritDoc}
		*/
		public final boolean isMultiChange() {return getNumberOfChanges() > 0; }
		/**
		* {@inheritDoc}
		*/
		public int getNumberOfChanges() {return changes.length; }
		/**
		* {@inheritDoc}
		*/
		public final String getChange() {return changes[0].type; }
		/**
		* {@inheritDoc}
		*/
		public final String getChange(int i) {return changes[i].type; }
		
		/**
		* Gets the <code>(i + 1)<sup>th</sup></code> table-row change.
		*/
		public final DiffsTableRowChange getTableRowChange(int i) {return changes[i]; }
		
		
		protected int __getTextBufferInitialCapacity() {return 160;}
		/**
		* {@inheritDoc}
		*/
		public void getText(final boolean useTabIndentInLieuOfSpaceIndent, final string.CharBuffer outputBuffer) {
			DMCompReportThing2StrUtil.getToYAMLStringChars(this, 0/*indents*/, true/*includeKindInfo*/, useTabIndentInLieuOfSpaceIndent, outputBuffer);
		}
	}
	
	/**
	* Class for providing support for diffs tables.<br>
	*
	* @author Marc E. KAMGA
	* @version 1.0
	* @copyright Marc E. KAMGA
	*/
	public static final class DiffsTable extends Thing implements java.io.Serializable {

		/**The class's serial version UID. */
		public static final long serialVersionUID = 0x0207FEB181000000L;
		
		public static final DiffsTable[] EMPTY_ARRAY = new DiffsTable[0];
		/**
		* The sequence for this class - didn't create a lock as the class is used in single-thread environments only for now.
		*/
		private static int sequence = 0; //NOTE: 
		
		static final int nextSequenceValue() {
			return ++sequence;
		}
		static final int curentSequenceValue() {
			return sequence;
		}
		
		protected byte summaryCategory;
		protected String diffsCompMethod;
		protected DiffsTableRow[] diffsRows;
		
		DiffsTable() {
			this.diffsRows = DiffsTableRow.EMPTY_ARRAY;
		}
		
		DiffsTable(final byte summaryCategory) {
			this.summaryCategory = summaryCategory;
			this.diffsRows = DiffsTableRow.EMPTY_ARRAY;
		}
		
		/**
		* {@inheritDoc}
		*/
		public final byte getKind() {
			return DIFFS_TABLE;
		}
		
		/**
		* Returns the number of the summary category for this diffs table.<br>
		*/
		public final byte getSummaryCategory() {return summaryCategory; }
		/**
		* Returns the number of the summary category for this diffs table.<br>
		*/
		public final String getSummaryCategoryCode() {return DMCompSummaryCategories.get_CategoryName(summaryCategory); }
		/**
		* Gets the code of the summary category of the object.<br>
		*/
		public final String getSummaryCategoryShortCode() {return DMCompSummaryCategoriesUtil.get_ShortCode(summaryCategory); } //since 2017-06-21
		/**
		* Gets the diffs comparison method for this diffs table.<br>
		*/
		public final String getDiffsCompMethod() {
			return diffsCompMethod;
		}
		
		/**
		* Tells if this diffs table is empty, meaning it has no diffs table rows.<br>
		* @return <code>this.size() == 0</code>
		*/
		public final boolean isEmpty() {
			return size() == 0;
		}
		/**
		* Returns the size of this diffs table.<br>
		*/
		public /*final */int size() {
			return diffsRows.length;
		}
		/**
		* Returns the <code>(i + 1)<sup>th</sup></code> diffs table row of this diffs table.
		*/
		public /*final */DiffsTableRow get(int i) {
			return diffsRows[i];
		}
		
		protected int __getTextBufferInitialCapacity() {return 160;}
		/**
		* {@inheritDoc}
		*/
		public void getText(final boolean useTabIndentInLieuOfSpaceIndent, final string.CharBuffer outputBuffer) {
			DMCompReportThing2StrUtil.getToYAMLStringChars(this, 0/*indents*/, true/*includeKindInfo*/, useTabIndentInLieuOfSpaceIndent, outputBuffer);
		}
		
		public final int getOutputNumber() {
			return number < 0 ? (number = nextSequenceValue()) : number;
		}
		
	}
	/**
	* Gets the source file code of the source file of this <code>DataModelComparisonReport</code>.<br>
	*/
	public final String getSourceFileCode() { //since 2017-06-21
//		System.out.println("DataModelComparisonReport::::::::::::: -----sourceFile.endsWith(_nonNZD_diff): " + sourceFile.endsWith(_nonNZD_diff) + ", sourceFile.endsWith(_NZD_diff): " + sourceFile.endsWith(_NZD_diff));
		if (sourceFileCode == null) {
			if (sourceFile.isEmpty()) {
				sourceFileCode = sourceFile;
				return sourceFileCode;
			}
			sourceFileCode = sourceFile.endsWith(_nonNZD_diff) ? sourceFile.left(sourceFile.length() - _nonNZD_diff.length()) : 
								sourceFile.endsWith(_NZD_diff) ? new string.CharBuffer(sourceFile.length() - _NZD_diff.length() + 1).append(sourceFile, 0, sourceFile.length() - _NZD_diff.length()).append('2').ToString(true/*createAsCStringIfPossible*/) : sourceFile;
		}
		return sourceFileCode;
	}
	
	static final String _nonNZD_diff = string.CString.valueOf(new byte[]{'_', 'n', 'o', 'n', 'N', 'Z', 'D', '_', 'd', 'i', 'f', 'f', '.', 'h', 't', 'm', 'l'}, true, true);
	static final String _NZD_diff = string.CString.valueOf(new byte[]{'_', 'N', 'Z', 'D', '_', 'd', 'i', 'f', 'f', '.', 'h', 't', 'm', 'l'}, true, true);

	protected String sourceFile;
	private String sourceFileCode;
	/**
	* The number allocated to the source file.<br>
	*/
	protected int sourceFileNumber;
	protected String product;
	protected final DiffsSummary diffsSummary;
	protected DiffsTable[] diffsTables;

	/**
	* Constructor.<br>
	*/
	/*public */DataModelComparisonReport() {
		super();
		this.diffsSummary = new DiffsSummary();
		this.diffsTables = DiffsTable.EMPTY_ARRAY;
		this.sourceFileNumber = 0;
		this.sourceFile = String.EMPTY;
	}
	
	/**
	* Returns the source file of this <code>DataModelComparisonReport</code>.<br>
	*/
	public final String getSourceFile() {return sourceFile; }
	/**
	* Returns the number of the source file of this <code>DataModelComparisonReport</code>.<br>
	*/
	public final int getSourceFileNumber() {return sourceFileNumber; }
	
	/**
	* Gets the Oracle eBS product which is the subject to data model comparison from previous version to the new current version.<br>
	*/
	public final String getProduct() {return product; }
	/**
	* Gets the diffs summary.<br>
	*/
	public final DiffsSummary getDiffsSummary() {return diffsSummary; }
	/**
	* Tells if this <code>DataModelComparisonReport</code> has at least one diffs table.<br>
	*/
	public final boolean hasDiffsTables() {return getNumberOfDiffsTables() != 0; }
	/**
	* Returns the number of diffs tables that this <code>DataModelComparisonReport</code> has.<br>
	*/
	public /*final */int getNumberOfDiffsTables() {return diffsTables.length; }
	/**
	* Gets the <code>(i + 1)<sup>th</sup></code> diffs table of this <code>DataModelComparisonReport</code>.<br>
	*/
	public /*final */DiffsTable getDiffsTable(int i) {
		return diffsTables[i];
	}
	
	/**
	* {@inheritDoc}
	*/
	public final byte getKind() {
		return DIFFS_COMPARISON_REPORT;
	}
	/**
	* {@inheritDoc}
	*/
	protected int __getTextBufferInitialCapacity() {return 2048;}
	/**
	* {@inheritDoc}
	*/
	public void getText(final boolean useTabIndentInLieuOfSpaceIndent, final string.CharBuffer outputBuffer) {
		DMCompReportThing2StrUtil.getToYAMLStringChars(this, 0/*indents*/, true/*includeKindInfo*/, useTabIndentInLieuOfSpaceIndent, outputBuffer);
	}
	/**
	* Returns the output number of this <code>DataModelComparisonReport</code>.<br>
	*/
	public final int getOutputNumber() {
		return number < 1 ? (number = nextSequenceValue()) : number;
	}
	/**
	* Gets the output number of the source file of this <code>DataModelComparisonReport</code>.<br>
	*/
	public final int getSourceFileOutputNumber() {
		return sourceFileNumber < 1 ? (sourceFileNumber = nextSourceFileNumSeqValue()) : sourceFileNumber;
	}

}