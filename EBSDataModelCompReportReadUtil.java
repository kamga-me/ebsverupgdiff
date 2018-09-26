package zz.ebs.dmcomp;

import string.ICharSequence;
import string.String;
import string.CString;
import string.CharBuffer;
import core.html.HTMLTags;
import core.html.HTMLTag;
import core.html.HTMLTemplate;
import core.html.CHTMLTemplate;
import core.html.UHTMLTemplate;
import core.html.HTMLException;
import core.html.HTMLMessageTemplateReadUtil;
import core.html.HTMLAttribute;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

import core.io.encoding.IUnicodeEncodingConstants;
import core.io.encoding.UTF8ReadSingleCpUtil;
import core.io.encoding.ReadFromStreamException;

import core.html.HTMLTemplate.AttributeIterator;
import core.html.HTMLTemplate.Tag;
import core.html.HTMLTemplate.TagIterator;

import core.collection.SimpleStringSet;

import zz.ebs.dmcomp.DataModelComparisonReport.DiffsTable;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsTableRow;
import zz.ebs.dmcomp.DataModelComparisonReport.SingleChgDiffsTableRow;
import zz.ebs.dmcomp.DataModelComparisonReport.SingleChgDiffsTableRowAdvcd;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsNewIndex;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsDetailedChange;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsTableRowChange;
import zz.ebs.dmcomp.DataModelComparisonReport.MultiChgDiffsTableRow;

/**
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
public class EBSDataModelCompReportReadUtil implements IUnicodeEncodingConstants {

	/**
	* Constant for <code<&quot;Product:&quot;</code>.
	*/
	static final String ProductValueTag = CString.valueOf(new byte[]{'P', 'r', 'o', 'd', 'u', 'c', 't', ':'}, true, true);

	static final byte NAME_ATTRIB_FLAG = 1;
	static final byte ID_ATTRIB_FLAG = 2;
	static final byte NAME_AND_ID_ATTRIB_FLAGS = NAME_ATTRIB_FLAG | ID_ATTRIB_FLAG;

	/**
	* Reads the HTML template on which the supplied UTF-8 input stream is opened.<br>
	*/
	public static HTMLTemplate readHTMLTemplate(final java.io.InputStream utf8InputStream, final byte acceptedParamTypes, final boolean documentFragmentAllowed, CharBuffer buffer) {
		return HTMLMessageTemplateReadUtil.readHTMLTemplate(utf8InputStream, acceptedParamTypes, documentFragmentAllowed, buffer);
	}
	/**
	* Reads the HTML template on which the supplied UTF-8 input stream is opened.<br>
	*/
	public static HTMLTemplate readHTMLTemplate(final java.io.InputStream utf8InputStream, final byte acceptedParamTypes, final boolean documentFragmentAllowed) {
		return readHTMLTemplate(utf8InputStream, acceptedParamTypes, documentFragmentAllowed, null);
	}

	/**
	* @param tempBuffer the temporary buffer to reuse whenever necessary - to use for esample in building the full file path; can/may be null
	*/
	public static DataModelComparisonReport readDataModelComparisonReport(final string.String reportFileDir, final string.String reportFileName, final byte acceptedParamTypes, final boolean documentFragmentAllowed, DMCompEnumSets enumSets, string.CharBuffer tempBuffer) {
		java.io.InputStream fis = null;
		//java.lang.StringBuilder reportFilePath = new java.lang.StringBuilder(reportFileDir.length() + 1 + reportFileName.length());
		if (tempBuffer == null) {
			tempBuffer = new string.CharBuffer(reportFileDir.length() + 1 + reportFileName.length());
		}
		else {
			tempBuffer.ensureCanAddNMoreChars(reportFileDir.length() + 1 + reportFileName.length());
			tempBuffer.resetLimitOnly();
		}
		if (reportFileDir.charAt(reportFileDir.length() - 1) != java.io.File.separatorChar) {
			tempBuffer/*reportFilePath*/.append(reportFileDir).append(java.io.File.separatorChar).append(reportFileName);
		}
		else {
			tempBuffer/*reportFilePath*/.append(reportFileDir).append(reportFileName);
		}
		try
		{
			fis = new java.io.BufferedInputStream(new java.io.FileInputStream(tempBuffer/*reportFilePath*/.toString()));
			return readDataModelComparisonReport(fis/*utf8Inputstream*/, acceptedParamTypes, documentFragmentAllowed, reportFileDir, reportFileName, enumSets);
		}
		catch(java.io.IOException ioe)
		{
			throw new HTMLException(ioe.getMessage() + java.lang.System.lineSeparator() +
			"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-1: I/O error while trying to read an eBS data model comparison report (" + tempBuffer/*reportFilePath*/ + ")"
			, ioe
			);
		}
	}
	/**
	* 
	*/
	public static DataModelComparisonReport readDataModelComparisonReport(final string.String reportFileDir, final string.String reportFileName, final byte acceptedParamTypes, final boolean documentFragmentAllowed, DMCompEnumSets enumSets) {
		return readDataModelComparisonReport(reportFileDir, reportFileName, acceptedParamTypes, documentFragmentAllowed, enumSets, null/*tempBuffer*/);
	}
	/**
	* Constant for <code>&quot;differences found&quot;</code>.
	*/
	static final String __differences_found__ = CString.valueOf(new byte[]{'d', 'i', 'f', 'f', 'e', 'r', 'e', 'n', 'c', 'e', 's', ' ', 'f', 'o', 'u', 'n', 'd'}, true, true);

	private static final int extractSummaryDiffs(ICharSequence htmlTmplt, int start, int end, int currentTagsCount) {
		//System.out.println("htmlTmplt.subSequence(start, end): '" + htmlTmplt.subSequence(start, end) + '\'');
		char ch = 0;
		__skipLEading_spaces_loop:
		do
		{
			if (start >= end) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::extractSummaryDiffs-1: bad data model comparison report file - invalid diffs (tagIndex=" + (currentTagsCount - 1) + ")"
				);
			}
			switch(htmlTmplt.getChar(start))
			{
			case ' ':
			case '\t':
			case '\r':
			case '\n':
				start++;
				continue __skipLEading_spaces_loop;
			case '(':
				__skip_inner_spaces_loop:
				do
				{
					start++;
					if (start >= end) {
						throw new DataModelCompReportException(
						"EBSDataModelCompReportReadUtil::extractSummaryDiffs-3: bad data model comparison report file - html/xhtml template object in illegal state (tagIndex=" + (currentTagsCount - 1) + ")"
						);
					}
					ch = htmlTmplt.getChar(start);
					switch(ch)
					{
					case ' ':
					case '\t':
					case '\r':
					case '\n':
						continue __skip_inner_spaces_loop;
					default:
						if (ch < '0' || ch > '9') {
							throw new DataModelCompReportException(
							"EBSDataModelCompReportReadUtil::extractSummaryDiffs-4: bad data model comparison report file - decimal digit expected (offset=" + start + ", tagIndex=" + (currentTagsCount - 1) + ")"
							);
						}
						break __skipLEading_spaces_loop;
					}
				} while (true); //end __skip_inner_spaces_loop
			}
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractSummaryDiffs-2: bad data model comparison report file - invalid diffs, opening parenthesis expected (tagIndex=" + (currentTagsCount - 1) + ", offset: " + start + ")"
			);
		} while (true); //end __skipLEading_spaces_loop
		//System.out.println("htmlTmplt.subSequence(start, end): '" + htmlTmplt.subSequence(start, end) + '\'');
		int diffs = (ch - '0');
		byte cnt = 1;
		__diffs_loop:
		do
		{
			start++;
			if (start >= end) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::extractSummaryDiffs-5: bad data model comparison report file - invalid diffs (offset=" + start + ", tagIndex=" + (currentTagsCount - 1) + ")"
				);
			}
			ch = htmlTmplt.getChar(start);
			if (ch < '0' || ch > '9') {
				__skip_trailing_spaces_loop:
				do
				{
					switch(ch)
					{
					case ' ':
					case '\t':
					case '\n':
					case '\r':
						start++;
						if (start >= end) {
							throw new DataModelCompReportException(
							"EBSDataModelCompReportReadUtil::extractSummaryDiffs-6: bad data model comparison report file - invalid diffs (offset=" + start + ", tagIndex=" + (currentTagsCount - 1) + ")"
							);
						}
						ch = htmlTmplt.getChar(start);
						continue __skip_trailing_spaces_loop;
					}
					break __skip_trailing_spaces_loop;
				} while (true); //end __skip_trailing_spaces_loop
				//System.out.println("htmlTmplt.subSequence(start, end): '" + htmlTmplt.subSequence(start, end) + '\'');
				if (end - start < __differences_found__.length() + 1 || !__differences_found__.equalsSubString(htmlTmplt, start, start + __differences_found__.length())) {
					throw new DataModelCompReportException(
					"EBSDataModelCompReportReadUtil::extractSummaryDiffs-28: bad data model comparison report file - diffs string value of invalid pattern (offset=" + start + ", tagIndex=" + (currentTagsCount - 1) + ")"
					);
				}
				start += __differences_found__.length();
				if (htmlTmplt.getChar(start) != ')') {
					throw new DataModelCompReportException(
					"EBSDataModelCompReportReadUtil::extractSummaryDiffs-7: bad data model comparison report file - invalid diffs, closing parenthesis expected (offset=" + start + ", tagIndex=" + (currentTagsCount - 1) + ")"
					);
				}
				return diffs;
			} //end if (ch < '0' || ch > '9')
			else if (cnt < (byte)9) {
				diffs = (diffs << 3) + (diffs << 1);
				diffs += ch - '0';
				cnt++;
				continue __diffs_loop;
			}
			else if (cnt == (byte)9 && (diffs < 0x7FFFFFFF / 10 || (diffs == 0x7FFFFFFF / 10 && ch < '8'))) {
				diffs = (diffs << 3) + (diffs << 1);
				diffs += ch - '0';
				cnt++;
				continue __diffs_loop;
			}
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractSummaryDiffs-8: bad data model comparison report file - invalid diffs, value too big (offset=" + start + ", tagIndex=" + (currentTagsCount - 1) + ")"
			);
		} while (true); //end __diffs_loop

	}

	static final byte extractSummaryCategory(ICharSequence htmlTmplt, int start, int end, int currentTagsCount) {
		//System.out.println("htmlTmplt.subSequence(start, end): '" + htmlTmplt.subSequence(start, end) + '\'');
		__skip_leading_spaces:
		do
		{
			if (start >= end) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::extractSummaryCategory-1: bad data model comparison report file - empty/blank summary category name/code (offset=" + start + ", tagIndex=" + (currentTagsCount - 1) + ")"
				);
			}
			switch(htmlTmplt.getChar(start))
			{
			case ' ': case '\t': case '\r': case '\n':
				start++;
				continue __skip_leading_spaces;
			}
			break __skip_leading_spaces;
		} while (true); //end __skip_leading_spaces
		//System.out.println("htmlTmplt.subSequence(start, end): '" + htmlTmplt.subSequence(start, end) + "'");
		end--;
		__skip_trailing_spaces:
		do
		{
			if (end < start) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::extractSummaryCategory-2: bad data model comparison report file - empty/blank summary category name/code (offset=" + start + ", tagIndex=" + (currentTagsCount - 1) + ")"
				);
			}
			switch(htmlTmplt.getChar(start))
			{
			case ' ': case '\t': case '\r': case '\n':
				end--;
				continue __skip_trailing_spaces;
			}
			end++;
			break __skip_trailing_spaces;
		} while (true); //end __skip_trailing_spaces
		byte cat = DMCompSummaryCategories.getCategoryNumber(htmlTmplt, start, end);
		if (cat < (byte)0) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractSummaryCategory-10: bad data model comparison report file - invalid summary category name/code (offset=" + start + ", tagIndex=" + (currentTagsCount - 1) + ")"
			);
		}
		return cat;
	}

	private static final void fetchClosingTRStraight(final TagIterator tagIter, final int currentTagsCount) {
		if (!tagIter.next()) {
			throw_UnexpectedEndOfTagList("fetchClosingTRStraight", 1/*errNum*/, currentTagsCount);
		}
		Tag tag = tagIter.currentTag();
		if (tag.getTagNumber() != HTMLTags.tr || !tag.isClosingTag()) {
			throw_TagExpectedError("fetchClosingTRStraight", 2/*errNum*/, currentTagsCount, "tr", tag, true/*closingExpected*/, "");
		}
	}
	
	static final String __extractHref(final Tag tagWithHRef) {
		//System.out.println("IN __extractHref - tag: " + tagWithHRef);
		HTMLTemplate msgTmplt = tagWithHRef.getSource();
		AttributeIterator attrIter = tagWithHRef.getAttributes();
		__main_loop:
		do
		{
			if (!attrIter.next()) {
				return null;
			}
			if (!core.html.SharedStringConstants.href.equalsSubStrIgnoreAsciiCase(msgTmplt, attrIter.attribNameStart(), attrIter.attribNameEnd())) {
				continue __main_loop;
			}
			return msgTmplt.substring(attrIter.attribValueStart(), attrIter.attribValueEnd());
		} while (true); //end __main_loop
	}
	
	static DataModelComparisonReport readDataModelComparisonReport(final java.io.InputStream utf8Inputstream, final byte acceptedParamTypes, final boolean documentFragmentAllowed, final string.String reportFileDir, final string.String reportFileName, DMCompEnumSets enumSets) {
		HTMLTemplate htmlTmplt = readHTMLTemplate(utf8Inputstream, acceptedParamTypes, documentFragmentAllowed);
		int tagsCount = 0;
		Tag tag = null;
		TagIterator tagIter = htmlTmplt.tagIterator();
		if (tagIter.next()) {
			tagsCount++;
			tag = tagIter.currentTag();
			if (tag.getHTMLTag() != HTMLTag.DOCTYPE) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-24: bad data model comparison report file - the report file must start with a OCTYPE tag (tagIndex=" + (tagsCount - 1) + ")"
				);
			}
			else if (!tagIter.next()) {
				throw_UnexpectedEndOfTagList(25/*errNum*/, tagsCount);
			}
			tagsCount++;
			tag = tagIter.currentTag();
			if (tag.getTagNumber() != HTMLTags.html) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-26: bad data model comparison report file - html tag is expected after DOCTYPE tag (tagIndex=" + (tagsCount - 1) + ")"
				);
			}
			else if (!tagIter.next()) {
				throw_UnexpectedEndOfTagList(27/*errNum*/, tagsCount);
			}
			tagsCount++;
			tag = tagIter.currentTag();
			if (tag.getTagNumber() == HTMLTags.head) {
				skip_head_loop:
				do
				{
					if (!tagIter.next()) {
						//should never get here, otherwise method readHTMLTemplate would have fire an exception...
						throw_UnexpectedEndOfTagList(13/*errNum*/, tagsCount);
					}
					tagsCount++;
					tag = tagIter.currentTag();
					if (tag.getTagNumber() == HTMLTags.head) {
						if (!tag.isClosingTag()) {
							//should never get here, otherwise method readHTMLTemplate would have fire an exception...
							throw new DataModelCompReportException(
							"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-2: bad data model comparison report file - cannot nest a head tag into another head tag (tagIndex=" + (tagsCount - 1) + ")"
							);
						}
						break skip_head_loop;
					}
				} while (true); //end skip_head_loop
			}
			else {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-1: bad data model comparison report file - the report file must have a  head tag (tagIndex=" + (tagsCount - 1) + ")"
				);
			}
		}
		else {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-3: bad data model comparison report file - empty/blank html file (tagIndex=" + (tagsCount - 1) + ")"
			);
		}
		if (!tagIter.next()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-4: bad data model comparison report file - html/xhtml template object in illegal state (tagIndex=" + (tagsCount - 1) + ")"
			);
		}
		tagsCount++;
		if (tagIter.currentTag().getTagNumber() != HTMLTags.body) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-5: bad data model comparison report file - body tag expected in the report file (tagIndex=" + (tagsCount - 1) + ")"
			);
		}
		if (!tagIter.next()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-6: bad data model comparison report file - html/xhtml template object in illegal state (tagIndex=" + (tagsCount - 1) + ")"
			);
		}
		tagsCount++;
		if ((tag = tagIter.currentTag()).getTagNumber() != HTMLTags.p) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-7: bad data model comparison report file - body tag expected in the report file (tagIndex=" + (tagsCount - 1) + ")"
			);
		}
		int start = tag.end() + 1;
		if (!tagIter.next()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-8: bad data model comparison report file - html/xhtml template object in illegal state (tagIndex=" + (tagsCount - 1) + ")"
			);
		}
		tagsCount++;
		if ((tag = tagIter.currentTag()).getTagNumber() != HTMLTags.p) {
//			System.out.println("tag: " + tag);
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-9: bad data model comparison report file - p tag expected in the report file (tagIndex=" + (tagsCount - 1) + ")"
			);
		}
		else if (!tag.isClosingTag()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-10: bad data model comparison report file - closing p tag expected in the report file (tagIndex=" + (tagsCount - 1) + ")"
			);
		}
		DataModelComparisonReport report = new DataModelComparisonReport();
		report.sourceFile = reportFileName;
		report.product = extractProduct(htmlTmplt, start, tag.start());


		if (!tagIter.next()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-11: bad data model comparison report file - html/xhtml template object in illegal state (tagIndex=" + (tagsCount - 1) + ")"
			);
		}
		tagsCount++;
		if ((tag = tagIter.currentTag()).getTagNumber() != HTMLTags.ul) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-12: bad data model comparison report file - ul tag expected in the report file (tagIndex=" + (tagsCount - 1) + ")"
			);
		}
		byte summaryCategory = -1, parentSummaryCat = -1;
		int diffs = -1;
		byte subCatMarkAlready = -1;
		__diffsSummary_loop:
		do
		{
			if (!tagIter.next()) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-13: bad data model comparison report file - html/xhtml template object in illegal state (tagIndex=" + (tagsCount - 1) + ")"
				);
			}
			tagsCount++;
			tag = tagIter.currentTag();
			switch(tag.getTagNumber())
			{
			case HTMLTags.li:
				if (!tagIter.next()) {
					throw new DataModelCompReportException(
					"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-14: bad data model comparison report file - html/xhtml template object in illegal state (tagIndex=" + (tagsCount - 1) + ")"
					);
				}
				tagsCount++;
				tag = tagIter.currentTag();
				if (tag.getTagNumber() != HTMLTags.a || tag.isClosingTag()) {
					throw_TagExpectedError(15/*errNum*/, tagsCount, "a"/*expectedTag*/, tag, false/*closingExpected*/, ""/*forField*/);
				}
				start = tag.end() + 1; //note: tag.end() is the index of the last character of the tag which is '>'
				if (!tagIter.next()) {
					throw new DataModelCompReportException(
					"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-31: bad data model comparison report file - html/xhtml template object in illegal state (tagIndex=" + (tagsCount - 1) + ")"
					);
				}
				tagsCount++;
				tag = tagIter.currentTag();
				if (tag.getTagNumber() != HTMLTags.a || !tag.isClosingTag()) {
					throw_TagExpectedError(32/*errNum*/, tagsCount, "a"/*expectedTag*/, tag, true/*closingExpected*/, ""/*forField*/);
				}
				summaryCategory = extractSummaryCategory(htmlTmplt, start, tag.start(), tagsCount);
				if (!tagIter.next()) {
					throw new DataModelCompReportException(
					"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-16: bad data model comparison report file - parent summary category mismatch (tagIndex=" + (tagsCount - 1) + ")"
					);
				}
				if (parentSummaryCat > -1) {
					if (parentSummaryCat != DMCompSummaryCategories.getParentCategoryNumber(summaryCategory)) {
						throw new DataModelCompReportException(
						"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-21: bad data model comparison report file - parent summary category expected (tagIndex=" + (tagsCount - 1) + ")"
						);
					}
				}
				else if (subCatMarkAlready == (byte)0) { //case a parent summary category is expected
					parentSummaryCat = DMCompSummaryCategories.getParentCategoryNumber(summaryCategory);
					if (parentSummaryCat < 0) {
						throw new DataModelCompReportException(
						"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-22: bad data model comparison report file - html/xhtml template object with invalid/unknown summary sub-category (tagIndex=" + (tagsCount - 1) + ")"
						);
					}
				}
				tagsCount++;
				tag = tagIter.currentTag();
				if (tag.getTagNumber() != HTMLTags.font || tag.isClosingTag()) {
					throw_TagExpectedError(17/*errNum*/, tagsCount, "font"/*expectedTag*/, tag, false/*closingExpected*/, ""/*forField*/);
				}
				start = tag.end() + 1; //note: tag.end() is the index of the last character of the tag which is '>'
				if (!tagIter.next()) {
					throw new DataModelCompReportException(
					"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-18: bad data model comparison report file - html/xhtml template object in illegal state (tagIndex=" + (tagsCount - 1) + ")"
					);
				}
				tagsCount++;
				tag = tagIter.currentTag();
				if (tag.getTagNumber() != HTMLTags.font || !tag.isClosingTag()) {
					throw_TagExpectedError(19/*errNum*/, tagsCount, "a"/*expectedTag*/, tag, true/*closingExpected*/, ""/*forField*/);
				}
				diffs = extractSummaryDiffs(htmlTmplt, start, tag.start(), tagsCount);
				report.diffsSummary.setDiffs(summaryCategory, diffs);
				if (!tagIter.next()) {
					throw new DataModelCompReportException(
					"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-18: bad data model comparison report file - html/xhtml template object in illegal state (tagIndex=" + (tagsCount - 1) + ")"
					);
				}
				tagsCount++;
				tag = tagIter.currentTag();
				if (tag.getTagNumber() != HTMLTags.li || !tag.isClosingTag()) {
					if (tag.getTagNumber() != HTMLTags.ul) {
						throw_TagExpectedError(30/*errNum*/, tagsCount, "a"/*expectedTag*/, tag, true/*closingExpected*/, ""/*forField*/);
					}
					else if (tag.isClosingTag()) {
						continue __diffsSummary_loop;
					}
				}
				else {
					continue __diffsSummary_loop;
				}
				//FALL-THROUGH ON PURPOSE for the new ul tag to be handled just below
			case HTMLTags.ul:
				if (tag.isClosingTag()) {
					if (subCatMarkAlready < (byte)0) { //case sub-cat never before
						//parentSummaryCat = -1;
						break __diffsSummary_loop;
					}
					else if (subCatMarkAlready != (byte)0) { //case sub-cat closed
						break __diffsSummary_loop;
					}
					//skip the closing tag of the parent categories, now that all the sub-categories are handled...
					if (!tagIter.next()) {
						throw_UnexpectedEndOfTagList(33/*errNum*/, tagsCount);
					}
					tagsCount++;
					tag = tagIter.currentTag();
					if (tag.getTagNumber() != HTMLTags.li || !tag.isClosingTag()) {
						throw_TagExpectedError(34/*errNum*/, tagsCount, "li"/*expectedTag*/, tag, true/*closingExpected*/, ""/*forField*/);
					}
					subCatMarkAlready = 1; //mark sub-cat as closed
					parentSummaryCat = -1;
					continue __diffsSummary_loop;
				}
				else {
					if (subCatMarkAlready == (byte)0) { //case sub-sub-cat
						throw new DataModelCompReportException(
						"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-23: bad data model comparison report file - sub-sub-category not supported (tagIndex=" + (tagsCount - 1) + ")"
						);
					}
					subCatMarkAlready = 0; //mark sub-cat as started
					continue __diffsSummary_loop;
				}
			default:
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-20: bad data model comparison report file - html/xhtml template object in illegal state (tagIndex=" + (tagsCount - 1) + ")"
				);
			}
		} while (true); //end __diffsSummary_loop

		if (enumSets == null) {
			enumSets = new DMCompEnumSets();
		}
		String changeType = null;
		byte presentAttribs = 0; //1 for name and 2 for id
		String name = null, id = null;
		final int[] tagsCnt = new int[1];
		int diffsTableRowsCount = 0;
		DiffsTable[] diffsTables = new DiffsTable[10];
		DiffsTable diffsTbl = null;
		int diffsTablesCount = 0;
		DiffsTableRow[] diffsTableRows = new DiffsTableRow[10];
		SingleChgDiffsTableRow diffsTableRow = null;
		SingleChgDiffsTableRowAdvcd diffsTableRowAdvcd = null;
		DiffsDetailedChange diffsDtlChg = null;
		DiffsNewIndex diffsNewIndex = null;
		DiffsTableRowChange diffsTblRowChange = null;
		MultiChgDiffsTableRow multiChgDiffsTblRow = null;
		int currMultiChgDiffsTblRowSz = 0;
		String objectName = null, change = null, tempStr_ = null;
		String detailedNodeType = null, detailedNodeName = null;
		String[] detailedChgSpec = String.EMPTY_ARRAY;
		int detailedChgSpecSz = 0, diffsDetailedChangesCount = 0;
		int end_ = 0;
		String href = null;
		diffs_tables_loop:
		do
		{
			//loop until the first span tag relating to a diffs table is reached
			__skip_non_diffs_tables_loop:
			do
			{
				//WHAT If ENd Is REACHED???!!!
				if (!tagIter.next()) {
					throw_UnexpectedEndOfTagList(35/*errNum*/, tagsCount);
				}
				tag = tagIter.currentTag();
				//////System.out.println("EBSDataModelCompReportReadUtil - tag: " + tag);
				if (tag.getTagNumber() !=  HTMLTags.span) {
					if (tag.getTagNumber() ==  HTMLTags.body) {
						if (!tag.isClosingTag()) {
							throw_TagExpectedError(1010/*errNum*/, tagsCount, "body"/*expectedTag*/, tag, true/*closingExpected*/, ""/*forField*/);
						}
						//NOTE: don't bother fetching html closing tag
						if (diffsTablesCount == 0) {
							report.diffsTables = DiffsTable.EMPTY_ARRAY;
						}
						else {
							report.diffsTables = new DiffsTable[diffsTablesCount];
							System.arraycopy(diffsTables, 0, report.diffsTables, 0, diffsTablesCount);
						}
						return report;
					}
					continue __skip_non_diffs_tables_loop;
				}
				if (!tagIter.next()) {
					throw_UnexpectedEndOfTagList(1001/*errNum*/, tagsCount);
				}
				tag = tagIter.currentTag();
				if (tag.getTagNumber() !=  HTMLTags.a || tag.isClosingTag()) {
					throw_TagExpectedError(1004/*errNum*/, tagsCount, "a"/*expectedTag*/, tag, false/*closingExpected*/, ""/*forField*/);
				}
				break __skip_non_diffs_tables_loop;
			} while (true); //end __skip_non_diffs_tables_loop
			//changeType = __checkDiffsTableHeader(tagIter, htmlTmplt, enumSets);
			presentAttribs = 0;
			AttributeIterator attribIter = tag.getAttributes();
			__search_for_name_and_id_loop:
			do
			{
				if (!attribIter.next()) {
					throw new DataModelCompReportException(
					"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-36: bad data model comparison report file - the tag must have at least name and id attributes (tagIndex=" + (tagsCount - 1) + ", tag: " + tag + ")"
					);
				}
				if (HTMLAttribute.name.equalsSubStrIgnoreAsciiCase(htmlTmplt, attribIter.attribNameStart(), attribIter.attribNameEnd())) {
					if ((presentAttribs & NAME_ATTRIB_FLAG) != 0) {
						throw new DataModelCompReportException(
						"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-37: bad data model comparison report file - tag with duplicate name attribute (tagIndex=" + (tagsCount - 1) + ", tag: " + tag + ")"
						);
					}
					else if (attribIter.htmlBooleanAttribute()) {
						throw new DataModelCompReportException(
						"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-38: bad data model comparison report file - tag with name attribute of type boolean attribute (tagIndex=" + (tagsCount - 1) + ", tag: " + tag + ", offset=" + attribIter.attribNameStart() + ")"
						);
					}
					name = htmlTmplt.substring(attribIter.attribValueStart(), attribIter.attribValueEnd());
					presentAttribs |= NAME_ATTRIB_FLAG;
					if((presentAttribs & NAME_AND_ID_ATTRIB_FLAGS) == NAME_AND_ID_ATTRIB_FLAGS) {
						break __search_for_name_and_id_loop;
					}
				}
				else if (HTMLAttribute.id.equalsSubStrIgnoreAsciiCase(htmlTmplt, attribIter.attribNameStart(), attribIter.attribNameEnd())) {
					if ((presentAttribs & ID_ATTRIB_FLAG) != 0) {
						throw new DataModelCompReportException(
						"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-39: bad data model comparison report file - tag with duplicate name attribute (tagIndex=" + (tagsCount - 1) + ", tag: " + tag + ")"
						);
					}
					else if (attribIter.htmlBooleanAttribute()) {
						throw new DataModelCompReportException(
						"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-40: bad data model comparison report file - tag with id attribute of type boolean attribute (tagIndex=" + (tagsCount - 1) + ", tag: " + tag + ", offset=" + attribIter.attribNameStart() + ")"
						);
					}
					id = htmlTmplt.substring(attribIter.attribValueStart(), attribIter.attribValueEnd());
					presentAttribs |= ID_ATTRIB_FLAG;
					if((presentAttribs & NAME_AND_ID_ATTRIB_FLAGS) == NAME_AND_ID_ATTRIB_FLAGS) {
						break __search_for_name_and_id_loop;
					}
				}
			} while (true); //end __search_for_name_and_id_loop
			summaryCategory = DMCompSummaryCategories.getCategoryNumber(name);
			if (summaryCategory < (byte)0) {
				summaryCategory = DMCompSummaryCategories.getCategoryNumber(id);
				if (summaryCategory < (byte)0) {
					throw new DataModelCompReportException(
					"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-41: bad data model comparison report file - diffs table name/id is not a valid summary category name (tagIndex=" + (tagsCount - 1) + ", tag: " + tag + ", name=" + name + ", id=" + id + ")"
					);
				}
			}
			start  = tag.end() + 1; //the start of the text that recalls the diffs for the summary category

			if (!tagIter.next()) {
				throw_UnexpectedEndOfTagList(1003/*errNum*/, tagsCount);
			}
			tag = tagIter.currentTag();
			if (tag.getTagNumber() != HTMLTags.a || !tag.isClosingTag()) {
				throw_TagExpectedError(1004/*errNum*/, tagsCount, "a"/*expectedTag*/, tag, true/*closingExpected*/, ""/*forField*/);
			}
			else if (start >= tag.start()) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-44: bad data model comparison report file - span element with empty text value (tagIndex=" + (tagsCount - 1) + ", tag: "+ tag + ", offet=" + start + ")"
				);
			}
			__skip_leading_spaces_loop:
			do
			{
				switch(htmlTmplt.getChar(start))
				{
				case ' ':
				case '\t':
				case '\r':
				case '\n':
				case '\f':
					start++;
					continue __skip_leading_spaces_loop;
				}
				break __skip_leading_spaces_loop;
			} while (true); //end __skip_leading_spaces_loop
			if (!tagIter.next()) {
				throw_UnexpectedEndOfTagList(42/*errNum*/, tagsCount);
			}
			tag = tagIter.currentTag();
			if (tag.getTagNumber() != HTMLTags.span || !tag.isClosingTag()) {
				throw_TagExpectedError(43/*errNum*/, tagsCount, "span"/*expectedTag*/, tag, true/*closingExpected*/, ""/*forField*/);
			}
			String summaryCatName = DMCompSummaryCategories.get_CategoryName(summaryCategory);
			if (tag.end() - start < summaryCatName.length() + 23) { //must start with summaryCatName + ": (", must contain a recall of the value of diffs and must end with " differences found)"
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-45: bad data model comparison report file - diffs table with badly formatted header (tagIndex=" + (tagsCount - 1) + ", tag: "+ tag + ", offet=" + start + ")"
				);
			}
			else if (!summaryCatName.equalsSubString(htmlTmplt, start, start + summaryCatName.length())) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-46: bad data model comparison report file - diffs table with badly not preperly tagged (tagIndex=" + (tagsCount - 1) + ", tag: "+ tag + ", offet=" + start + ", summaryCatName; " + summaryCatName +  ")"
				);
			}
			start += summaryCatName.length();
			if (htmlTmplt.getChar(start) != ':') {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-47: bad data model comparison report file - diffs table with badly formatted header (tagIndex=" + (tagsCount - 1) + ", tag: "+ tag + ", offet=" + start + ")"
				);
			}
			start++;
			diffs = extractSummaryDiffs(htmlTmplt, start, tag.end(), tagsCount);
			if (report.diffsSummary.getDiffs(summaryCategory) != diffs) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-48: bad data model comparison report file - diffs summary inconsistency (tagIndex=" + (tagsCount - 1) + ", tag: "+ tag + ", offet=" + start + ")"
				);
			}
			diffsTbl = new DiffsTable(summaryCategory);
			diffsTables = ensureCanAddOneMoreTable(diffsTables, diffsTablesCount);
			diffsTables[diffsTablesCount++] = diffsTbl;
			if (!tagIter.next()) {
				throw_UnexpectedEndOfTagList(49/*errNum*/, tagsCount);
			}
			tagsCount++;
			tag = tagIter.currentTag();
			if (tag.getTagNumber() != HTMLTags.table || tag.isClosingTag()) {
				throw_TagExpectedError(50/*errNum*/, tagsCount, "table"/*expectedTag*/, tag, false/*closingExpected*/, ""/*forField*/);
			}
			if (!tagIter.next()) {
				throw_UnexpectedEndOfTagList(51/*errNum*/, tagsCount);
			}
			tagsCount++;
			tag = tagIter.currentTag();
			if (tag.getTagNumber() != HTMLTags.tr || tag.isClosingTag()) {
				//whet if closing table tag???
				if (tag.getTagNumber() == HTMLTags.table || tag.isClosingTag()) { //case closing table tag
					//MKA
				}
				throw_TagExpectedError(52/*errNum*/, tagsCount, "tr"/*expectedTag*/, tag, false/*closingExpected*/, ""/*forField*/);
			}
			tagsCnt[0] = tagsCount;
			summaryCatName = extractDiffsTableHeaderField(tagIter, htmlTmplt, tagsCnt); //the display name of the summary category
			diffsTbl.diffsCompMethod = extractDiffsTableHeaderField(tagIter, htmlTmplt, tagsCnt);
			tempStr_ = enumSets.diffsMethods.addIfNotPresent(diffsTbl.diffsCompMethod);
			if (tempStr_ != null) {
				diffsTbl.diffsCompMethod = tempStr_;
			}
			tagsCount = tagsCnt[0];
			//**NOTE: previous comment is KO: needs to force the fecth of he closing tr follwing the extraction of the two header field names! WILL NOT UNCOMMENT THE COMMENT BUT CALL method fetchClosingTRStraight
			//*****COMMENTED THE BELOW CODE OUT BECAUSE THIS IS DONE At THE BEGINING Of THE LOOP
//			if (!tagIter.next()) {
//				throw_UnexpectedEndOfTagList(53/*errNum*/, tagsCount);
//			}
//			tagsCount++;
//			tag = tagIter.currentTag();
//			if (tag.getTagNumber() != HTMLTags.tr || !tag.isClosingTag()) {
//				throw_TagExpectedError(54/*errNum*/, tagsCount, "tr"/*expectedTag*/, tag, true/*closingExpected*/, ""/*forField*/);
//			}
			fetchClosingTRStraight(tagIter, tagsCount);
			tagsCount++;

			diffsTableRowsCount = 0;
			__diffsTableRows_loop:
			do
			{
				if (!tagIter.next()) {
					throw_UnexpectedEndOfTagList(55/*errNum*/, tagsCount);
				}
				tagsCount++;
				tag = tagIter.currentTag();
				//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport:::__diffsTableRows_loop - tag: " + tag + ", tagsCount: " + tagsCount + ", diffsTableRowsCount: " + diffsTableRowsCount);
				/*will force ensure closing tr follows the second closing td, as a valid tr must be comprised of two tds, instead of testing the cosing tr here
				if (tag.getTagNumber() == HTMLTags.tr && tag.isClosingTag()) {
					
				}*/
				if (tag.getTagNumber() != HTMLTags.tr) {
					if (tag.getTagNumber() == HTMLTags.table && tag.isClosingTag()) { //case end of the diffs table
						//OLD: break __diffsTableRows_loop;
						if (diffsTableRowsCount == 0) {
							diffsTbl.diffsRows = DiffsTableRow.EMPTY_ARRAY;
						}
						else {
							diffsTbl.diffsRows = new DiffsTableRow[diffsTableRowsCount];
							System.arraycopy(diffsTableRows, 0, diffsTbl.diffsRows, 0, diffsTableRowsCount);
						}
						continue diffs_tables_loop;
					}
					throw new DataModelCompReportException(
					"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-56: bad data model comparison report file - opening tr tag or closing table tag expected (tagIndex=" + (tagsCount - 1) + ", tag: "+ tag + ", offset=" + tag.start() + ")"
					);
				}
				else if (tag.isClosingTag()) {
					throw new DataModelCompReportException(
					"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-57: bad data model comparison report file - unexpected tr tag (tagIndex=" + (tagsCount - 1) + ", tag: "+ tag + ", offset=" + tag.start() + ", diffsTableRowsCount: " + diffsTableRowsCount + ")"
					);
				}
				//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport - about to start fetching objectName");
				//field objectName
				if (!tagIter.next()) {
					throw_UnexpectedEndOfTagList(58/*errNum*/, tagsCount);
				}
				tagsCount++;
				tag = tagIter.currentTag();
				if (tag.getTagNumber() != HTMLTags.td || tag.isClosingTag()) {
					throw_TagExpectedError(59/*errNum*/, tagsCount, "td"/*expectedTag*/, tag, false/*closingExpected*/, " for object-name"/*forField*/);
				}
				start = tag.end() + 1;
				if (!tagIter.next()) {
					throw_UnexpectedEndOfTagList(60/*errNum*/, tagsCount);
				}
				tagsCount++;
				tag = tagIter.currentTag();
				if (tag.getTagNumber() != HTMLTags.td || !tag.isClosingTag()) {
					throw_TagExpectedError(59/*errNum*/, tagsCount, "td"/*expectedTag*/, tag, true/*closingExpected*/, " for object-name"/*forField*/);
				}
				objectName = extractStringValue(htmlTmplt, start, tag.start()); //BUG-FIX-2017-04-21 - was using tag.end() as end index, which was KO
				//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport - objectName fetched (" + objectName + ")");

				//change
				if (!tagIter.next()) {
					throw_UnexpectedEndOfTagList(60/*errNum*/, tagsCount);
				}
				tagsCount++;
				tag = tagIter.currentTag();
				if (tag.getTagNumber() != HTMLTags.td || tag.isClosingTag()) {
					throw_TagExpectedError(61/*errNum*/, tagsCount, "td"/*expectedTag*/, tag, false/*closingExpected*/, " for change field"/*forField*/);
				}
				//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport - opening tag td following object name fetched");
				start = tag.end() + 1;
				if (!tagIter.next()) {
					throw_UnexpectedEndOfTagList(62/*errNum*/, tagsCount);
				}
				tagsCount++;
				tag = tagIter.currentTag();
				//int end_;
				end_ = tag.start(); //BUG-FIX-2017-04-29 - moved here to fix the bug were end was less than start, causing IndexOutOfBoundsException exception...
				if (tag.getTagNumber() == HTMLTags.br) {
					//end_ = tag.start(); //BUG-FIX-2017-04-29 - moved just above "if (tag.getTagNumber() == HTMLTags.br)" as if closing td was not preceeded by br, the value of end_ was an old value less than the value of local variable start, hence causing IndexOutOfBoundsException
					skip_all_brs_loop: 
					do
					{
						if (!tagIter.next()) {
							throw_TagExpectedError(1012/*errNum*/, tagsCount, "td"/*expectedTag*/, tag, true/*closingExpected*/, " for change"/*forField*/);
						}
						tagsCount++;
						tag = tagIter.currentTag();
					} while (tag.getTagNumber() == HTMLTags.br); //end skip_all_brs_loop
				}
				
				if (tag.getTagNumber() == HTMLTags.td) {
					if (!tag.isClosingTag()) {
						throw_TagExpectedError(63/*errNum*/, tagsCount, "td"/*expectedTag*/, tag, true/*closingExpected*/, " for change"/*forField*/);
					}
					//else: case end of current diffs table row is reached
					diffsTableRow = new SingleChgDiffsTableRow(objectName, extractStringValue(htmlTmplt, start, end_)); //BUG-FIX-2017-04-22 - tag.start() was including <br> tags in the change value =+> introduced local variable end_//BUG-FIX-2017-04-21 - was using tag.end() as end index, which was KO
					change = enumSets.changes.addIfNotPresent(diffsTableRow.change);
					if (change != null) { //case the change was present in the set
						diffsTableRow.change = change;
					}
					//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport - change fetched (" + diffsTableRow.change + ")");
					diffsTableRows = ensureCanAddOneMoreTableRow(diffsTableRows, diffsTableRowsCount);
					diffsTableRows[diffsTableRowsCount++] = diffsTableRow;
					fetchClosingTRStraight(tagIter, tagsCount);
					tagsCount++;
					//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport - end of simple diffs table row");
					continue __diffsTableRows_loop;
				}
				else if (tag.getTagNumber() == HTMLTags.font) {
					change_loop:
					do
					{
						//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport:::__diffsTableRows_loop::change_loop - tag: " + tag + ", tagsCount: " + tagsCount + ", diffsTableRowsCount: " + diffsTableRowsCount);
						if (tag.isClosingTag()) {
							throw_TagExpectedError(64/*errNum*/, tagsCount, "font"/*expectedTag*/, tag, false/*closingExpected*/, " for change"/*forField*/);
						}
						start = tag.end() + 1;
						if (!tagIter.next()) {
							throw_UnexpectedEndOfTagList(65/*errNum*/, tagsCount);
						}
						tagsCount++;
						tag = tagIter.currentTag();
						if (tag.getTagNumber() != HTMLTags.font || !tag.isClosingTag()) {
							throw_TagExpectedError(66/*errNum*/, tagsCount, "font"/*expectedTag*/, tag, true/*closingExpected*/, " for change"/*forField*/);
						}
						change = extractStringValue(htmlTmplt, start, tag.start()); //BUG-FIX-2017-04-21 - was using tag.end() as end index, which was KO
						tempStr_ = enumSets.changes.addIfNotPresent(change);
						if (tempStr_ != null) { //case the change was present in the set
							change = tempStr_;
						}
						if (currMultiChgDiffsTblRowSz == 0/*multiChgDiffsTblRow == null*/) { //case not multi-change diffs table row
							diffsTableRowAdvcd = new SingleChgDiffsTableRowAdvcd(objectName, change, 4/*anticipatedMinNumOfDetailedChgs*/);
							diffsTableRows = ensureCanAddOneMoreTableRow(diffsTableRows, diffsTableRowsCount);
							diffsTableRows[diffsTableRowsCount++] = diffsTableRowAdvcd;
						}
						else {
							diffsTblRowChange = new DiffsTableRowChange(change); //BUG-FIX-2017-04-21 - was passing diffsTableRowAdvcd.change while diffsTableRowAdvcd is null instead of passing local variable change
							diffsTblRowChange.detailedChgs = new DiffsDetailedChange[4];
							multiChgDiffsTblRow.changes = ensureCanAddOneMoreTableRowChg(multiChgDiffsTblRow.changes, currMultiChgDiffsTblRowSz);
							multiChgDiffsTblRow.changes[currMultiChgDiffsTblRowSz++] = diffsTblRowChange;
						}
						diffsDetailedChangesCount = 0;
						if (!tagIter.next()) {
							throw_UnexpectedEndOfTagList(67/*errNum*/, tagsCount);
						}
						tagsCount++;
						tag = tagIter.currentTag();
						if (tag.getTagNumber() == HTMLTags.br) {//case there are details
							detailedChgSpecSz = 0;
							skip_all_the_brs_in_the_row_loop: //BUG-FIX-2017-04-21 - in case of AR_nonNZD_diff.html file, two consecutive <br>s right after the <font> element which gives the change type ==> i added the loop for all the consecutive <br>s afer element <font> to be counted as one <br>...
							do 
							{
								if (!tagIter.next()) {
									throw_UnexpectedEndOfTagList(68/*errNum*/, tagsCount);
								}
								tagsCount++;
								tag = tagIter.currentTag();
								switch(tag.getTagNumber()) 
								{
								case HTMLTags.br: 
									continue skip_all_the_brs_in_the_row_loop;
								case HTMLTags.td: 
									if (!tag.isClosingTag()) {
										throw_TagExpectedError(73/*errNum*/, tagsCount, "td"/*expectedTag*/, tag, true/*closingExpected*/, ""/*forField*/);
									}
									if (currMultiChgDiffsTblRowSz == 0/*multiChgDiffsTblRow == null*/) { //case not multi-change diffs table row
										diffsTableRowAdvcd.detailedChgs = DiffsDetailedChange.EMPTY_ARRAY;
									}
									else {
										diffsTblRowChange.detailedChgs = DiffsDetailedChange.EMPTY_ARRAY;
										
										//trim the array of diffsTblRowChange and make it final
										DiffsTableRowChange[] changes = new DiffsTableRowChange[currMultiChgDiffsTblRowSz];
										System.arraycopy(multiChgDiffsTblRow.changes, 0, changes, 0, changes.length);
										multiChgDiffsTblRow.changes = changes;

										currMultiChgDiffsTblRowSz = 0; //reset to 0 for next diffs table row iteration to work properly
									}
									fetchClosingTRStraight(tagIter, tagsCount);
									tagsCount++;
									//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport:::__diffsTableRows_loop::change_loop::end_of_diffs_table_row_change::no_detailed_changes_for_the_change - tagsCount: " + tagsCount + ", diffsTableRowsCount: " + diffsTableRowsCount);
									continue __diffsTableRows_loop;
								default:
									break skip_all_the_brs_in_the_row_loop;
								}
							} while (true); //end skip_all_the_brs_in_the_row_loop
							__details_loop:
							do
							{
								//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport:::__diffsTableRows_loop::change_loop::__details_loop - tag: " + tag + ", tagsCount: " + tagsCount + ", diffsTableRowsCount: " + diffsTableRowsCount);
								if (tag.getTagNumber() == HTMLTags.strong) {
									if (tag.isClosingTag()) {
										throw_TagExpectedError(69/*errNum*/, tagsCount, "strong"/*expectedTag*/, tag, false/*closingExpected*/, ""/*forField*/);
									}
									start = tag.end() + 1;
									if (!tagIter.next()) {
										throw_UnexpectedEndOfTagList(70/*errNum*/, tagsCount);
									}
									tagsCount++;
									tag = tagIter.currentTag();
									if (tag.getTagNumber() != HTMLTags.strong || !tag.isClosingTag()) {
										throw_TagExpectedError(71/*errNum*/, tagsCount, "strong"/*expectedTag*/, tag, true/*closingExpected*/, ""/*forField*/);
									}
									detailedNodeType = extractStringValue(htmlTmplt, start, tag.start()); //BUG-FIX-2017-04-21 - was using tag.end() as end index, which was KO
									tempStr_ = enumSets.detailNodeTypes.addIfNotPresent(detailedNodeType);
									if (tempStr_ != null) {
										detailedNodeType = tempStr_;
									}
									detailedChgSpec = ensureCanAddNMoreDetailedChgSpecItems(2, detailedChgSpec, detailedChgSpecSz);
									detailedChgSpec[detailedChgSpecSz++] = detailedNodeType;
									//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport:::__diffsTableRows_loop::change_loop::__details_loop: strong element fetched - detailedNodeType: " + detailedNodeType + ", tagsCount: " + tagsCount + ", diffsTableRowsCount: " + diffsTableRowsCount);
									//int end_;
									//get the spec parts until <br /> or </td> is reached
									detailed_chg_spec_nodes_loop:
									do
									{
										//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport:::__diffsTableRows_loop::change_loop::__details_loop::detailed_chg_spec_nodes_loop - tag: " + tag + ", tagsCount: " + tagsCount + ", diffsTableRowsCount: " + diffsTableRowsCount);
										start = tag.end() + 1;
										if (!tagIter.next()) {
											throw_UnexpectedEndOfTagList(72/*errNum*/, tagsCount);
										}
										tagsCount++;
										tag = tagIter.currentTag();
										if (tag.getTagNumber() == HTMLTags.font) {
											if (tag.isClosingTag()) {
												throw_TagExpectedError(73/*errNum*/, tagsCount, "font"/*expectedTag*/, tag, false/*closingExpected*/, ""/*forField*/);
											}
										}
										else if (tag.getTagNumber() == HTMLTags.a) {
											if (tag.isClosingTag()) {
												throw_TagExpectedError(1006/*errNum*/, tagsCount, "a"/*expectedTag*/, tag, false/*closingExpected*/, ""/*forField*/);
											} 
										}
										else if (tag.getTagNumber() == HTMLTags.br) {
											tempStr_ = extractStringValue(htmlTmplt, start, tag.start()); //BUG-FIX-2017-04-21 - was using tag.end() as end index, which was KO
											String[] spec_;
											if (!tempStr_.isEmpty()) {
												if (enumSets.changes.contains(tempStr_)) {
													tempStr_ = enumSets.changes.get(tempStr_);
												}
												spec_ = new String[detailedChgSpecSz + 1];
												System.arraycopy(detailedChgSpec, 0, spec_, 0, detailedChgSpecSz);
												spec_[detailedChgSpecSz] = tempStr_;
											}
											else {
												spec_ = new String[detailedChgSpecSz];
												System.arraycopy(detailedChgSpec, 0, spec_, 0, detailedChgSpecSz);
											}
											detailedChgSpecSz = 0; //set to 0 for next time
											//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport:::__diffsTableRows_loop::change_loop::__details_loop::detailed_chg_spec_nodes_loop::end_of_detailed_change - tag: " + tag + ", tagsCount: " + tagsCount + ", diffsTableRowsCount: " + diffsTableRowsCount + ", spec_.length: " + spec_.length + ", tempStr_: '" + tempStr_ + "'");
											diffsDtlChg = new DiffsDetailedChange(spec_);
											if (!tagIter.next()) {
												throw_UnexpectedEndOfTagList(72/*errNum*/, tagsCount);
											}
											tagsCount++;
											tag = tagIter.currentTag();
											if (tag.getTagNumber() == HTMLTags.br) { //case there's 2 consecutive <br> ==> must create a new DiffsTableRow with a new change value but for the same object???!!!

												if (!tagIter.next()) {
													throw_UnexpectedEndOfTagList(1007/*errNum*/, tagsCount);
												}
												tagsCount++;
												tag = tagIter.currentTag();
												if (tag.getTagNumber() == HTMLTags.strong) { //case double <br> is followed by <strong> ==> do same as if there were just one <br>
													//NOTE: we have this case with file GL_nonNZD.html

													//NOTE: the detailed change/spec is ready and can be added the advanced diffs table row
													if (currMultiChgDiffsTblRowSz == 0/*diffsTableRowAdvcd != null*/) { //case not multi-change diffs table row
														diffsTableRowAdvcd.detailedChgs = ensureCanAddOneMoreDetailedChange(diffsTableRowAdvcd.detailedChgs, diffsDetailedChangesCount);
														diffsTableRowAdvcd.detailedChgs[diffsDetailedChangesCount++] = diffsDtlChg;
													}
													else { //case multi-change diffs table-row
														diffsTblRowChange.detailedChgs = ensureCanAddOneMoreDetailedChange(diffsTblRowChange.detailedChgs, diffsDetailedChangesCount);
														diffsTblRowChange.detailedChgs[diffsDetailedChangesCount++] = diffsDtlChg;
													}

													continue __details_loop;

												}
												else if (tag.getTagNumber() != HTMLTags.font) {
													throw_TagExpectedError(1008/*errNum*/, tagsCount, "font"/*expectedTag*/, tag, false/*closingExpected*/, ""/*forField*/);
												}
												//
												//** NOTE: a multi change diffs table row (MultiChgDiffsTableRow) is preferred to several instances of single change diffs table row
												//
												if (currMultiChgDiffsTblRowSz == 0/*multiChgDiffsTblRow == null*/) {
													//make the list of detailed change spec trimmed and final - don't forget to add the last detailed change spec
													DiffsDetailedChange[] detailedChgs = new DiffsDetailedChange[diffsDetailedChangesCount + 1];
													System.arraycopy(diffsTableRowAdvcd.detailedChgs, 0, detailedChgs, 0, diffsDetailedChangesCount);
													detailedChgs[diffsDetailedChangesCount] = diffsDtlChg;
													//diffsTableRowAdvcd.detailedChgs = detailedChgs;
													//diffsTbl.diffsRows = new DiffsTableRow[diffsTableRowsCount];
													//System.arraycopy(diffsTableRows, 0, diffsTbl.diffsRows, 0, diffsTableRowsCount);
													//ADD CODE HERE

													//create a DiffsTableRowChange that will hold the detailed change specs of the current diffsTableRowAdvcd
													diffsTblRowChange = new DiffsTableRowChange(diffsTableRowAdvcd.change/*type*/, detailedChgs);
													multiChgDiffsTblRow = new MultiChgDiffsTableRow(diffsTableRowAdvcd.objectName); //create a MultiChgDiffsTableRow that will replace the current diffsTableRowAdvcd
													multiChgDiffsTblRow.changes = new DiffsTableRowChange[4];
													multiChgDiffsTblRow.changes[0] = diffsTblRowChange;
													currMultiChgDiffsTblRowSz = 1;
													//substitute multiChgDiffsTblRow for diffsTableRowAdvcd
													diffsTableRows[diffsTableRowsCount - 1] = multiChgDiffsTblRow; //diffsTableRowAdvcd;
													diffsTableRowAdvcd = null;
												}
												else {
													//make the list of detailed change spec trimmed and final - don't forget to add the last detailed change spec
													DiffsDetailedChange[] detailedChgs = new DiffsDetailedChange[diffsDetailedChangesCount + 1];
													System.arraycopy(diffsTblRowChange.detailedChgs, 0, detailedChgs, 0, diffsDetailedChangesCount);
													detailedChgs[diffsDetailedChangesCount] = diffsDtlChg;
													diffsTblRowChange.detailedChgs = detailedChgs;

													//NOTE: no need to add diffsTblRowChange to multiChgDiffsTblRow, as it was already done when the former was created

												}
												//MUST GO TO change_loop
												continue change_loop;
											}  //end case there's 2 consecutive <br>
											else if (tag.getTagNumber() == HTMLTags.td) {
												if (!tag.isClosingTag()) {
													throw_TagExpectedError(1011/*errNum*/, tagsCount, "td", tag, true/*closingExpected*/, "");
												}
												
												if (currMultiChgDiffsTblRowSz == 0/*multiChgDiffsTblRow == null*/) {
													//make the list of detailed change spec trimmed and final - don't forget to add the last detailed change spec
													DiffsDetailedChange[] detailedChgs = new DiffsDetailedChange[diffsDetailedChangesCount + 1];
													System.arraycopy(diffsTableRowAdvcd.detailedChgs, 0, detailedChgs, 0, diffsDetailedChangesCount);
													detailedChgs[diffsDetailedChangesCount] = diffsDtlChg;
													diffsTableRowAdvcd.detailedChgs = detailedChgs;
												}
												else {
													//make the list of detailed change spec trimmed and final - don't forget to add the last detailed change spec
													DiffsDetailedChange[] detailedChgs = new DiffsDetailedChange[diffsDetailedChangesCount + 1];
													System.arraycopy(diffsTblRowChange.detailedChgs, 0, detailedChgs, 0, diffsDetailedChangesCount);
													detailedChgs[diffsDetailedChangesCount] = diffsDtlChg;
													diffsTblRowChange.detailedChgs = detailedChgs;

													//trim the array of diffsTblRowChange and make it final
													DiffsTableRowChange[] changes = new DiffsTableRowChange[currMultiChgDiffsTblRowSz];
													System.arraycopy(multiChgDiffsTblRow.changes, 0, changes, 0, changes.length);
													multiChgDiffsTblRow.changes = changes;

													currMultiChgDiffsTblRowSz = 0; //reset to 0 for next diffs table row iteration to work properly
												}
												fetchClosingTRStraight(tagIter, tagsCount);
												tagsCount++;
												//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport:::__diffsTableRows_loop::change_loop::__details_loop::detailed_chg_spec_nodes_loop::end_of_diffs_table_row_change - tagsCount: " + tagsCount + ", diffsTableRowsCount: " + diffsTableRowsCount);
												continue __diffsTableRows_loop;
												
											}
											else if (tag.getTagNumber() == HTMLTags.strong) {
												//NOTE: the detailed change/spec is ready and can be added the advanced diffs table row
												if (currMultiChgDiffsTblRowSz == 0/*diffsTableRowAdvcd != null*/) { //case not multi-change diffs table row
													diffsTableRowAdvcd.detailedChgs = ensureCanAddOneMoreDetailedChange(diffsTableRowAdvcd.detailedChgs, diffsDetailedChangesCount);
													diffsTableRowAdvcd.detailedChgs[diffsDetailedChangesCount++] = diffsDtlChg;
												}
												else { //case multi-change diffs table-row
													diffsTblRowChange.detailedChgs = ensureCanAddOneMoreDetailedChange(diffsTblRowChange.detailedChgs, diffsDetailedChangesCount);
													diffsTblRowChange.detailedChgs[diffsDetailedChangesCount++] = diffsDtlChg;
												}
												continue __details_loop; 
											}
											//NOTE: the detailed change/spec is ready and can be added the advanced diffs table row
											if (currMultiChgDiffsTblRowSz == 0/*diffsTableRowAdvcd != null*/) { //case not multi-change diffs table row
												diffsTableRowAdvcd.detailedChgs = ensureCanAddOneMoreDetailedChange(diffsTableRowAdvcd.detailedChgs, diffsDetailedChangesCount);
												diffsTableRowAdvcd.detailedChgs[diffsDetailedChangesCount++] = diffsDtlChg;
											}
											else { //case multi-change diffs table-row
												diffsTblRowChange.detailedChgs = ensureCanAddOneMoreDetailedChange(diffsTblRowChange.detailedChgs, diffsDetailedChangesCount);
												diffsTblRowChange.detailedChgs[diffsDetailedChangesCount++] = diffsDtlChg;
											}
											if (!tagIter.next()) {
												throw_UnexpectedEndOfTagList(1007/*errNum*/, tagsCount);
											}
											tagsCount++;
											tag = tagIter.currentTag();
											//COMMENTED OUT BECAUSE DONE BY LOOP __details_loop
											//if (tag.getTagNumber() != HTMLTags.strong) { //case double <br> is followed by <strong> ==> do same as if there were just one <br>
											//	throw_TagExpectedError(1008/*errNum*/, tagsCount, "strong"/*expectedTag*/, tag, false/*closingExpected*/, ""/*forField*/);
											//}
											continue __details_loop;
										}
										else if (tag.getTagNumber() == HTMLTags.td) {
											//ADD CODE HERE
											if (!tag.isClosingTag()) {
												throw_TagExpectedError(1008/*errNum*/, tagsCount, "td"/*expectedTag*/, tag, true/*closingExpected*/, ""/*forField*/);
											}
											//else: case end of current diffs table row is reached
											tempStr_ = extractStringValue(htmlTmplt, start, tag.start()); //BUG-FIX-2017-04-21 - was using tag.end() as end index, which was KO
											if (enumSets.changes.contains(tempStr_)) {
												tempStr_ = enumSets.changes.get(tempStr_);
											}
											String[] spec_;
											if (!tempStr_.isEmpty()) {
												spec_ = new String[detailedChgSpecSz + 1];
												System.arraycopy(detailedChgSpec, 0, spec_, 0, detailedChgSpecSz);
												spec_[detailedChgSpecSz] = tempStr_;
											}
											else {
												spec_ = new String[detailedChgSpecSz];
												System.arraycopy(detailedChgSpec, 0, spec_, 0, detailedChgSpecSz);
											}
											detailedChgSpecSz = 0; //set to 0 for next time
											diffsDtlChg = new DiffsDetailedChange(spec_);
											if (currMultiChgDiffsTblRowSz == 0/*multiChgDiffsTblRow == null*/) {
												//make the list of detailed change spec trimmed and final - don't forget to add the last detailed change spec
												DiffsDetailedChange[] detailedChgs = new DiffsDetailedChange[diffsDetailedChangesCount + 1];
												System.arraycopy(diffsTableRowAdvcd.detailedChgs, 0, detailedChgs, 0, diffsDetailedChangesCount);
												detailedChgs[diffsDetailedChangesCount] = diffsDtlChg;
												diffsTableRowAdvcd.detailedChgs = detailedChgs;
											}
											else {
												//make the list of detailed change spec trimmed and final - don't forget to add the last detailed change spec
												DiffsDetailedChange[] detailedChgs = new DiffsDetailedChange[diffsDetailedChangesCount + 1];
												System.arraycopy(diffsTblRowChange.detailedChgs, 0, detailedChgs, 0, diffsDetailedChangesCount);
												detailedChgs[diffsDetailedChangesCount] = diffsDtlChg;
												diffsTblRowChange.detailedChgs = detailedChgs;

												//trim the array of diffsTblRowChange and make it final
												DiffsTableRowChange[] changes = new DiffsTableRowChange[currMultiChgDiffsTblRowSz];
												System.arraycopy(multiChgDiffsTblRow.changes, 0, changes, 0, changes.length);
												multiChgDiffsTblRow.changes = changes;

												currMultiChgDiffsTblRowSz = 0; //reset to 0 for next diffs table row iteration to work properly
											}
											fetchClosingTRStraight(tagIter, tagsCount);
											tagsCount++;
											continue __diffsTableRows_loop;
										}
										else {
											throw new DataModelCompReportException(
											"EBSDataModelCompReportReadUtil::readDataModelComparisonReport-1009: bad data model comparison report file - unexpected tag for the data model comparison (tagIndex=" + (tagsCount - 1) +  ", tag=" + tag  + ')'
											);
										}
										if (htmlTmplt.getChar(start) == '-') {
											start++;
										}
										end_ = tag.start();
										if (htmlTmplt.getChar(end_ - 1) == ':') {
											end_--;
										}
										detailedNodeName = extractStringValue(htmlTmplt, start, end_, true/*ignoreLeadingDash*/); //BUG-FIX-2017-04-21 - was using tag.end() as end index, which was KO
										detailedChgSpec = ensureCanAddNMoreDetailedChgSpecItems(tag.getTagNumber() == HTMLTags.a ? 4 : 2, detailedChgSpec, detailedChgSpecSz);
										detailedChgSpec[detailedChgSpecSz++] = detailedNodeName;
										//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport:::__diffsTableRows_loop::change_loop::__details_loop::detailed_chg_spec_nodes_loop - tag: " + tag + ", tagsCount: " + tagsCount + ", diffsTableRowsCount: " + diffsTableRowsCount + ", detailedNodeName: '" + detailedNodeName + "'");
										
										start = tag.end() + 1;
										if (!tagIter.next()) {
											throw_UnexpectedEndOfTagList(74/*errNum*/, tagsCount);
										}
										tagsCount++;
										Tag prevOpenedTag = tag;
										tag = tagIter.currentTag();
										if (tag.getTagNumber() != prevOpenedTag.getTagNumber()/*HTMLTags.font*/ || !tag.isClosingTag()) {
											throw_TagExpectedError(75/*errNum*/, tagsCount, prevOpenedTag.getHTMLTag().toString()/*expectedTag*/, tag, true/*closingExpected*/, ""/*forField*/);
										}
										
										if (prevOpenedTag.getTagNumber() == HTMLTags.a) {
											href = __extractHref(prevOpenedTag/*tagWithHRef*/);
											if (href != null && !href.isEmpty()) {
												//NOTE: add virtual href node; RECALL: capacity check is done above ==> no need to do it again here
												detailedChgSpec[detailedChgSpecSz++] = core.html.SharedStringConstants.href;
												detailedChgSpec[detailedChgSpecSz++] = href;
											}
										}
										
										if (htmlTmplt.getChar(start) == ':') {
											start++;
										}
										end_ = tag.start();
										if (htmlTmplt.getChar(end_ - 1) == ':') {
											end_--;
										}
										detailedNodeType = extractStringValue(htmlTmplt, start, end_); //BUG-FIX-2017-04-21 - was using tag.end() as end index, which was KO
										tempStr_ = enumSets.detailNodeTypes.addIfNotPresent(detailedNodeType);
										if (tempStr_ != null) {
											detailedNodeType = tempStr_;
										}
										detailedChgSpec = ensureCanAddNMoreDetailedChgSpecItems(2, detailedChgSpec, detailedChgSpecSz);
										detailedChgSpec[detailedChgSpecSz++] = detailedNodeType;
										//////System.out.println("EBSDataModelCompReportReadUtil::readDataModelComparisonReport:::__diffsTableRows_loop::change_loop::__details_loop::detailed_chg_spec_nodes_loop - tag: " + tag + ", tagsCount: " + tagsCount + ", diffsTableRowsCount: " + diffsTableRowsCount + ", detailedNodeType: " + detailedNodeType);
									} while (true); //end detailed_chg_spec_nodes_loop
									//detailedNodeName = String.EMPTY; //initialize...
								} //end if (tag.getTagNumber() == HTMLTags.strong)
							} while (true); //end __details_loop
						} //end case there are details
						else { //case the supposed multi-change row is empty ==> no detailed change
							if (tag.getTagNumber() != HTMLTags.td || !tag.isClosingTag()) {//case there are details
								throw_TagExpectedError(1005/*errNum*/, tagsCount, "td"/*expectedTag*/, tag, true/*closingExpected*/, " for change"/*forField*/);
							}
							//else: end of current diffs table ro is reached
							if (currMultiChgDiffsTblRowSz == 0/*multiChgDiffsTblRow == null*/) {
								diffsTableRowAdvcd.detailedChgs = DiffsDetailedChange.EMPTY_ARRAY;
							}
							else {
								diffsTblRowChange.detailedChgs = DiffsDetailedChange.EMPTY_ARRAY;

								//trim the array of diffsTblRowChange and make it final
								DiffsTableRowChange[] changes = new DiffsTableRowChange[currMultiChgDiffsTblRowSz];
								System.arraycopy(multiChgDiffsTblRow.changes, 0, changes, 0, changes.length);
								multiChgDiffsTblRow.changes = changes;

								currMultiChgDiffsTblRowSz = 0; //reset to 0 for next diffs table row iteration to work properly
							}
							fetchClosingTRStraight(tagIter, tagsCount);
							tagsCount++;
							continue __diffsTableRows_loop; //OLD KO: break change_loop;
						}
					} while (true); //end change_loop
				}
			} while (true); //end __diffsTableRows_loop

		} while (true); //end diffs_tables_loop

		//return null;
	}

	private static void throw_UnexpectedEndOfTagList(java.lang.String mthdName, int errNum, int tagsCount) {
		throw new DataModelCompReportException(
		"EBSDataModelCompReportReadUtil::" + (mthdName == null || mthdName.isEmpty() ? "readDataModelComparisonReport" : mthdName) + "-" + errNum + ": bad data model comparison report file - end of tags list reached unexpectedly (tagIndex=" + (tagsCount - 1) + ')'
		);
	}
	private static void throw_UnexpectedEndOfTagList(int errNum, int tagsCount) {
		throw_UnexpectedEndOfTagList(null, errNum, tagsCount);
	}

	private static final void throw_TagExpectedError(java.lang.String mthdName, int errNum, int tagsCount, java.lang.String expectedTag, Tag tag, boolean closingExpected, java.lang.String forField) {
		throw new DataModelCompReportException(
		"EBSDataModelCompReportReadUtil::" + (mthdName == null || mthdName.isEmpty() ? "readDataModelComparisonReport" : mthdName) + "-" + errNum + ": bad data model comparison report file - " + expectedTag + " " + (closingExpected ? "closing" : "opening") + " tag expected for " + forField + " (tagIndex=" + (tagsCount - 1) + ", tag: "+ tag + ", offset=" + tag.start() + ")"
		);
	}
	private static final void throw_TagExpectedError(int errNum, int tagsCount, java.lang.String expectedTag, Tag tag, boolean closingExpected, java.lang.String forField) {
		throw_TagExpectedError(null, errNum, tagsCount, expectedTag, tag, closingExpected, forField);
	}

	private static final DiffsTableRowChange[] ensureCanAddOneMoreTableRowChg(final DiffsTableRowChange[] changes, final int changesCount) {
		if (changesCount >= changes.length) {
			int newLen = changes.length + (changes.length >>> 1);
			DiffsTableRowChange[] arr = new DiffsTableRowChange[newLen <= changesCount ? changesCount + 1 : newLen];
			System.arraycopy(changes, 0, arr, 0, changesCount);
			return arr;
		}
		return changes;
	}

	private static final DiffsTableRow[] ensureCanAddOneMoreTableRow(final DiffsTableRow[] diffsTableRows, final int diffsTableRowsCount) {
		if (diffsTableRowsCount >= diffsTableRows.length) {
			int newLen = diffsTableRows.length + (diffsTableRows.length >>> 1);
			DiffsTableRow[] arr = new DiffsTableRow[newLen <= diffsTableRowsCount ? diffsTableRowsCount + 1 : newLen];
			System.arraycopy(diffsTableRows, 0, arr, 0, diffsTableRowsCount);
			return arr;
		}
		return diffsTableRows;
	}
	private static final DiffsTable[] ensureCanAddOneMoreTable(final DiffsTable[] diffsTables, final int diffsTablesCount) {
		if (diffsTablesCount >= diffsTables.length) {
			int newLen = diffsTables.length + (diffsTables.length >>> 1);
			DiffsTable[] arr = new DiffsTable[newLen <= diffsTablesCount ? diffsTablesCount + 1 : newLen];
			System.arraycopy(diffsTables, 0, arr, 0, diffsTablesCount);
			return arr;
		}
		return diffsTables;
	}
	private static final DiffsDetailedChange[] ensureCanAddOneMoreDetailedChange(final DiffsDetailedChange[] diffsDetailedChanges, final int diffsDetailedChangesCount) {
		if (diffsDetailedChangesCount >= diffsDetailedChanges.length) {
			int newLen = diffsDetailedChanges.length + (diffsDetailedChanges.length >>> 1);
			DiffsDetailedChange[] arr = new DiffsDetailedChange[newLen <= diffsDetailedChangesCount ? diffsDetailedChangesCount + 1 : newLen];
			System.arraycopy(diffsDetailedChanges, 0, arr, 0, diffsDetailedChangesCount);
			return arr;
		}
		return diffsDetailedChanges;
	}

	private static final String[] ensureCanAddOneMoreDetailedChgSpecItem(final String[] detailedChgSpec, final int detailedChgSpecSz) {
		return ensureCanAddNMoreDetailedChgSpecItems(1, detailedChgSpec, detailedChgSpecSz);
	}
	private static final String[] ensureCanAddNMoreDetailedChgSpecItems(int n, final String[] detailedChgSpec, final int detailedChgSpecSz) {
		n += detailedChgSpecSz;
		if (n/*minCapacity*/ > detailedChgSpec.length) {
			int newLen = detailedChgSpec.length + (detailedChgSpec.length >>> 1);
			String[] arr = new String[newLen < n/*minCapacity*/ ? n/*minCapacity*/ : newLen];
			System.arraycopy(detailedChgSpec, 0, arr, 0, detailedChgSpecSz);
			return arr;
		}
		return detailedChgSpec;
	}

	private static final String extractDiffsTableHeaderField(final TagIterator tagIter, final HTMLTemplate htmlTmplt, final int[] tagsCount) {
		if (!tagIter.next()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-1: bad data model comparison report file - end of tags list reached unexpectedly (tagIndex=" + (tagsCount[0] - 1) + ")"
			);
		}
		tagsCount[0]++;
		Tag tag = tagIter.currentTag();
		if (tag.getTagNumber() != HTMLTags.th || tag.isClosingTag()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-2: bad data model comparison report file - th opening tag expected (tagIndex=" + (tagsCount[0] - 1) + ", tag: "+ tag + ", offset=" + tag.start() + ")"
			);
		}
		if (!tagIter.next()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-3: bad data model comparison report file - end of tags list reached unexpectedly (tagIndex=" + (tagsCount[0] - 1) + ")"
			);
		}
		tagsCount[0]++;
		tag = tagIter.currentTag();
		if (tag.getTagNumber() != HTMLTags.a || tag.isClosingTag()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-4: bad data model comparison report file - a opening tag expected (tagIndex=" + (tagsCount[0] - 1) + ", tag: "+ tag + ", offset=" + tag.start() + ")"
			);
		}
		int start = tag.end() + 1; //start index of the field value
		if (!tagIter.next()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-5: bad data model comparison report file - end of tags list reached unexpectedly (tagIndex=" + (tagsCount[0] - 1) + ")"
			);
		}
		String fieldValue;
		tagsCount[0]++;
		tag = tagIter.currentTag();
		if (tag.getTagNumber() == HTMLTags.strong) {
			start = tag.end() + 1; //start index of the field value
			if (!tagIter.next()) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-13: bad data model comparison report file - end of tags list reached unexpectedly (tagIndex=" + (tagsCount[0] - 1) + ")"
				);
			}
			tagsCount[0]++;
			tag = tagIter.currentTag();
			if (tag.getTagNumber() != HTMLTags.strong || !tag.isClosingTag()) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-14: bad data model comparison report file - strong closing tag expected (tagIndex=" + (tagsCount[0] - 1) + ", tag: "+ tag + ", offset=" + tag.start() + ")"
				);
			}
			fieldValue = extractStringValue(htmlTmplt, start, tag.start()); //holds the display name of the summary category
			if (!tagIter.next()) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-15: bad data model comparison report file - end of tags list reached unexpectedly (tagIndex=" + (tagsCount[0] - 1) + ")"
				);
			}
			tagsCount[0]++;
			tag = tagIter.currentTag();
			if (tag.getTagNumber() != HTMLTags.span || tag.isClosingTag()) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-16: bad data model comparison report file - span opening tag expected (tagIndex=" + (tagsCount[0] - 1) + ", tag: "+ tag + ", offset=" + tag.start() + ")"
				);
			}
		}
		else {
			if (tag.getTagNumber() != HTMLTags.span || tag.isClosingTag()) {
				throw new DataModelCompReportException(
				"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-6: bad data model comparison report file - span opening tag expected (tagIndex=" + (tagsCount[0] - 1) + ", tag: "+ tag + ", offset=" + tag.start() + ")"
				);
			}
			fieldValue = extractStringValue(htmlTmplt, start, tag.start()); //holds the display name of the summary category
		}
		if (!tagIter.next()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-7: bad data model comparison report file - end of tags list reached unexpectedly (tagIndex=" + (tagsCount[0] - 1) + ")"
			);
		}
		tagsCount[0]++;
		tag = tagIter.currentTag();
		if (tag.getTagNumber() != HTMLTags.span || !tag.isClosingTag()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-8: bad data model comparison report file - span closing tag expected (tagIndex=" + (tagsCount[0] - 1) + ", tag: "+ tag + ", offset=" + tag.start() + ")"
			);
		}
		if (!tagIter.next()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-9: bad data model comparison report file - end of tags list reached unexpectedly (tagIndex=" + (tagsCount[0] - 1) + ")"
			);
		}
		tagsCount[0]++;
		tag = tagIter.currentTag();
		if (tag.getTagNumber() != HTMLTags.a || !tag.isClosingTag()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-10: bad data model comparison report file - a closing tag expected (tagIndex=" + (tagsCount[0] - 1) + ", tag: "+ tag + ", offset=" + tag.start() + ")"
			);
		}
		if (!tagIter.next()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-11: bad data model comparison report file - end of tags list reached unexpectedly (tagIndex=" + (tagsCount[0] - 1) + ")"
			);
		}
		tagsCount[0]++;
		tag = tagIter.currentTag();
		if (tag.getTagNumber() != HTMLTags.th || !tag.isClosingTag()) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractDiffsTableHeaderField-12: bad data model comparison report file - th closing tag expected (tagIndex=" + (tagsCount[0] - 1) + ", tag: "+ tag + ", offset=" + tag.start() + ")"
			);
		}
		return fieldValue;
	}

	private static final String extractStringValue(final HTMLTemplate htmlTmplt, int start, int end) { //NOTE: introduced argument ignoreLeadingDash on 2017-04-22 because i could figure out why the leading dash for some values were not detected before entering this method!!
		return extractStringValue(htmlTmplt, start, end, false/*ignoreLeadingDash*/); 
	}
	private static final String extractStringValue(final HTMLTemplate htmlTmplt, int start, int end, final boolean ignoreLeadingDash) { //NOTE: introduced argument ignoreLeadingDash on 2017-04-22 because i could figure out why the leading dash for some values were not detected before entering this method!!
		if (start == end) return String.EMPTY;
		__skip_leading_ws_chars_loop:
		do
		{
			switch(htmlTmplt.getChar(start))
			{
			case ' ':
			case '\t':
			case '\r':
			case '\n':
			case '\f':
				start++;
				if (start >= end) return String .EMPTY;
				continue __skip_leading_ws_chars_loop;
			}
			if (ignoreLeadingDash && htmlTmplt.getChar(start) == '-') {
				start++;
			}
			break __skip_leading_ws_chars_loop;
		} while (true); //end __skip_ws_chars_loop
		end--;
		__skip_trailing_ws_chars_loop:
		do
		{
			switch(htmlTmplt.getChar(end))
			{
			case ' ':
			case '\t':
			case '\r':
			case '\n':
			case '\f':
				end--;
				if (end < start) return String .EMPTY;
				continue __skip_trailing_ws_chars_loop;
			}
			return htmlTmplt.substring(start, end + 1);
		} while (true); //end __skip_ws_chars_loop
	}

	private static final String extractProduct(HTMLTemplate htmlTmplt, /*final */int productTextStart, int productTextEnd) {
		//System.out.println("htmlTmplt.substring(productTextStart, productTextEnd): " + htmlTmplt.substring(productTextStart, productTextEnd));
		__skip_trailing_ws_loop:
		do
		{
			productTextEnd--;
			if (productTextEnd >= productTextStart) {
				switch(htmlTmplt.getChar(productTextEnd))
				{
				case ' ':
				case '\t':
				case '\r':
				case '\n':
					continue __skip_trailing_ws_loop;
				}
			}
			productTextEnd++;
			break __skip_trailing_ws_loop;
		} while (true);
		//System.out.println("htmlTmplt.substring(productTextStart, productTextEnd): " + htmlTmplt.substring(productTextStart, productTextEnd));
		__skip_leading_ws_loop:
		do
		{
			if (productTextStart < productTextEnd) {
				switch(htmlTmplt.getChar(productTextStart))
				{
				case ' ':
				case '\t':
				case '\r':
				case '\n':
					productTextStart++;
					continue __skip_leading_ws_loop;
				}
			}
			break __skip_leading_ws_loop;
		} while (true); //end __skip_leading_ws_loop
		//System.out.println("htmlTmplt.substring(productTextStart, productTextEnd): " + htmlTmplt.substring(productTextStart, productTextEnd));
		if (productTextEnd - productTextStart <= ProductValueTag.length() || !ProductValueTag.equalsSubString(htmlTmplt, productTextStart, productTextStart + ProductValueTag.length())) {
			throw new DataModelCompReportException(
			"EBSDataModelCompReportReadUtil::extractProduct-11: bad data model comparison report file - product value tag expected in the report file (" + ProductValueTag + ")"
			);
		}
		productTextStart += ProductValueTag.length();
		__skip_inner_space_loop:
		do
		{
			switch(htmlTmplt.getChar(productTextStart))
			{
			case ' ':
			case '\t':
			case '\r':
			case '\n':
				productTextStart++;
				if (productTextStart >= productTextEnd) {
					throw new DataModelCompReportException(
					"EBSDataModelCompReportReadUtil::extractProduct-12: bad data model comparison report file - product field expected in the report file"
					);
				}
				continue __skip_inner_space_loop;
			}
			break __skip_inner_space_loop;
		} while (true);
		return htmlTmplt.substring(productTextStart, productTextEnd);
	}

	//static final String __is_removed_in_12_2_6 = CString.valueOf(new byte[]{' ', 'i', 's', ' ', 'r', 'e', 'm', 'o', 'v', 'e', 'd', ' ', 'i', 'n', ' ', '1', '2', '.', '2', '.', '6'}, true, true);

	public static final void main(java.lang.String[] args) { //AR_nonNZD_diff //AP_nonNZD_diff //INV_nonNZD_diff //GL_nonNZD //PA_nonNZD_diff //PO_nonNZD_diff.html //OE_nonNZD_diff
		java.lang.String filePath = "C:\\Users\\hp\\Documents\\kme\\michelin\\oracle\\12.1.3_12.2.6\\PO_nonNZD_diff.html"; //"C:\\Users\\hp\\Documents\\kme\\michelin\\oracle\\12.1.3_12.2.6\\PA_nonNZD_diff.html"; //"C:\\Users\\hp\\Documents\\kme\\michelin\\oracle\\12.1.3_12.2.6\\AR_nonNZD_diff.html"; //"C:\\Users\\hp\\Documents\\kme\\michelin\\oracle\\12.1.3_12.2.6\\AD_NZD_diff.html";
		java.io.InputStream fis = null;
		try
		{
			fis = new java.io.BufferedInputStream(new FileInputStream(filePath));
			HTMLTemplate tmpl = readHTMLTemplate(fis, HTMLTemplate.NO_PARAMS_ACCEPTED_AT_ALL, false/*documentFragmentAllowed*/);
			TagIterator iter = tmpl.tagIterator();
			//while (iter.next())
			//{
			//	System.out.println(iter.currentTag()/*.getHTMLTag()*//* + ", tagStart: " + iter.currentTag().start() + ", end: " + iter.currentTag().end() + ", tagNameStart: " + iter.currentTag().nameStart()*/);
			//}
			//System.out.println(tmpl);
			fis = new java.io.BufferedInputStream(new FileInputStream(filePath));
			DataModelComparisonReport rpt = readDataModelComparisonReport(fis, HTMLTemplate.NO_PARAMS_ACCEPTED_AT_ALL, false/*documentFragmentAllowed*/, String.EMPTY/*""*/, null, null);
			System.out.println(rpt);
		}
		catch(java.io.IOException ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage() + java.lang.System.lineSeparator() +
			"EBSDataModelCompReportReadUtil::main-1: I/O error "
			, ex
			);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}




}