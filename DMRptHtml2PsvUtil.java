package zz.ebs.dmcomp;

import java.io.OutputStream;
import java.io.FileOutputStream;

import core.io.IOException;
import core.io.encoding.TextOutputStreamBuffer;
import core.io.encoding.UTF8TextOutputStreamBuffer;
import core.io.BufferedTextOutputStream;
import core.io.WriteToStreamException;
import static core.io.BufferedTextOutputStreamUtil.newBufferedTextOutputStream;

import string.ICharSequence;
import string.String;
import string.CString;

import zz.ebs.eBSProductSet;
import zz.ebs.eBSProduct;

import zz.ebs.dmcomp.DataModelComparisonReport.DiffsSummary;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsDetailedChange;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsTableRow;
import zz.ebs.dmcomp.DataModelComparisonReport.SingleChgDiffsTableRow;
import zz.ebs.dmcomp.DataModelComparisonReport.SingleChgDiffsTableRowAdvcd;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsNewIndex;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsTableRowChange;
import zz.ebs.dmcomp.DataModelComparisonReport.MultiChgDiffsTableRow;
import zz.ebs.dmcomp.DataModelComparisonReport.DiffsTable;

import static zz.ebs.dmcomp.DataModelComparisonReport.DATA_MODEL_COMP_12_1_3_12_2_6;
import static zz.ebs.dmcomp.DataModelComparisonReport.DATA_MODEL_COMP_12_1_3_12_2_6_SHORT_CODE;

import static zz.ebs.dmcomp.DMCompSummaryCategories.NUMBER_OF_SUMMARY_CATEGORIES;

import static core.IOperatingSystemConstants.LINE_TERMINATOR;
import static core.IOperatingSystemConstants.fileNameComponentSep;

import static zz.ebs.dmcomp.ChangeType.Attribute_Changes__between_12_1_3_and_12_2_6;

import static zz.ebs.dmcomp.DMCompDetailChangedTypes.NOT_A_FROM_TO_CHANGE_FROM_VAL;
import static zz.ebs.dmcomp.DMCompDetailChangedTypes.WEAK_FROM_TO_CHANGE_FROM_VAL;
import static zz.ebs.dmcomp.DMCompDetailChangedTypes.NO_SPEC_ITEM_FOR_DTL_OR_NO_CHG_TYPES_TO_MATCH;
import static zz.ebs.dmcomp.DMCompDetailChangedTypes.NO_DTL_TYPE_MATCH_FOR_FROM_TO_CHANGE;

import core.IValueTypes;
import core.data.type.IValueTypesExt;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;

import core.html.HTMLTemplate;

import static zz.ebs.dmcomp.EBSDataModelCompReportReadUtil.readDataModelComparisonReport;

import static zz.ebs.dmcomp.DMCompOutputUtil.get_nowDtm;
import static zz.ebs.dmcomp.DMCompOutputUtil.writeDMComparisonReport;
import static zz.ebs.dmcomp.DMCompOutputUtil.writeSourceFile;
import static zz.ebs.dmcomp.DMCompOutputUtil.completeWriting;
import static zz.ebs.dmcomp.DMCompOutputUtil.writeDiffsOuputErrorHeader;
import static zz.ebs.dmcomp.DMCompOutputUtil.writeRefDataHeader;
import static zz.ebs.dmcomp.DMCompOutputUtil.writeSourceFileHeader;
import static zz.ebs.dmcomp.DMCompOutputUtil.writeSummaryHeader;
import static zz.ebs.dmcomp.DMCompOutputUtil.writeObjectHeader;
import static zz.ebs.dmcomp.DMCompOutputUtil.writeDiffsTblObjectChangeHeader;
import static zz.ebs.dmcomp.DMCompOutputUtil.writeDetailPathHeader;
import static zz.ebs.dmcomp.DMCompOutputUtil.writeDiffsDetailHeader;
import static zz.ebs.dmcomp.DMCompOutputUtil.writeDiffsOuputTranscoHeader;
import static zz.ebs.dmcomp.DMCompOutputUtil.writeDiffsOuputTransco;

import core.logging.ILogLevels;
import core.logging.Logger;

import java.util.zip.GZIPOutputStream;

import static zz.ebs.dmcomp.IDMCompDiffsPSVFileTypes.SOURCES_FILE; 
import static zz.ebs.dmcomp.IDMCompDiffsPSVFileTypes.REF_DATA_FILE; 
import static zz.ebs.dmcomp.IDMCompDiffsPSVFileTypes.SUMMARY_FILE; 
import static zz.ebs.dmcomp.IDMCompDiffsPSVFileTypes.OBJECTS_FILE; 
import static zz.ebs.dmcomp.IDMCompDiffsPSVFileTypes.OBJECT_CHANGES_FILE; 
import static zz.ebs.dmcomp.IDMCompDiffsPSVFileTypes.DETAIL_PATHS_FILE; 
import static zz.ebs.dmcomp.IDMCompDiffsPSVFileTypes.DETAILS_FILE; 
import static zz.ebs.dmcomp.IDMCompDiffsPSVFileTypes.ERRORS_FILE; 
import static zz.ebs.dmcomp.IDMCompDiffsPSVFileTypes.NON_SHARED_DATA_FILES; 
import static zz.ebs.dmcomp.IDMCompDiffsPSVFileTypes.NO_FILES_SPECIFIED; 

import static core.io.FilePathFactory.newFilePath;

import static zz.ebs.dmcomp.DMCompOutputUtil.TAG_CATEGORY;
import static zz.ebs.dmcomp.DMCompOutputUtil.SOURCE_FILE_CATEGORY;
import static zz.ebs.dmcomp.DMCompOutputUtil.DIFFS_COMPARISON_CATEGORY;


/**
* Utility class with static methods for generating data model comparison reports data for load into Horizons BI database, from data model diffs comparison html report files<br>
*
* @author Marc E. KAMGA
* @version 1.0
* @copyrigt Marc E. KAMGA
*
*/
public final class DMRptHtml2PsvUtil {
	
	
	public static final byte FLUSHES_AND_CLOSES_WRITE_STARTED = 0;
	public static final byte FLUSHES_AND_CLOSES_NON_SHARED_RIGHT_AFTER = 1;
	public static final byte FLUSHES_AND_CLOSES_ALL_RIGHT_AFTER = 2;
	public static final byte FLUSHES_AND_CLOSES_WRITE_NOT_STARTED = (byte)0x80;
	
	static final byte FOR_SURE_FLUSHES_AND_CLOSES_NON_SHARED_RIGHT_AFTER = FLUSHES_AND_CLOSES_NON_SHARED_RIGHT_AFTER | FLUSHES_AND_CLOSES_ALL_RIGHT_AFTER;

	
	static final core.io.FilePath/*string.String*/ DEFAULT_DMCOMP_HOME_DIR;
	static final core.io.FilePath/*string.String*/ DEFAULT_HORIZONS_OUTPUT_DIR;
	static final core.io.FilePath/*string.String*/ DEFAULT_HORIZONS_INPUT_DIR;
	static {
		string.CharBuffer defaultHomeDir = new string.CharBuffer(80); //homeDir
		java.lang.String homeDir = java.lang.System.getenv("ecosystem.horizons.dataModelCompHome");
		if (homeDir != null && !homeDir.isEmpty()) {
			defaultHomeDir.appendChars(homeDir);
			if (homeDir.charAt(defaultHomeDir.length() - 1) != fileNameComponentSep) {
				defaultHomeDir.appendChar(fileNameComponentSep);
			}
		}
		else {
			defaultHomeDir.appendChars("C:\\Users\\hp\\Documents\\kme\\michelin\\");
		}
		DEFAULT_DMCOMP_HOME_DIR = defaultHomeDir.onlyHasCStringChars() ? 
										newFilePath(defaultHomeDir.to_Char8Array(), false/*onlyNameSafeChars*/, defaultHomeDir.onlyHasAsciiChars(), true/*trustworthyCharArray*/) : 
										newFilePath(defaultHomeDir.toCharArray(), false/*onlyNameSafeChars*/, true/*trustworthyCharArray*/)
										; //OLD: defaultHomeDir.ToString(true/*createAsciiStrIfPossible*/);
		defaultHomeDir.append("horizons").append(fileNameComponentSep).append("output").appendChar(fileNameComponentSep);
		DEFAULT_HORIZONS_OUTPUT_DIR = defaultHomeDir.onlyHasCStringChars() ? 
										newFilePath(defaultHomeDir.to_Char8Array(), false/*onlyNameSafeChars*/, defaultHomeDir.onlyHasAsciiChars(), true/*trustworthyCharArray*/) : 
										newFilePath(defaultHomeDir.toCharArray(), false/*onlyNameSafeChars*/, true/*trustworthyCharArray*/)
										; //OLD: defaultHomeDir.ToString(true/*createAsciiStrIfPossible*/);
		defaultHomeDir.setLimit(DEFAULT_DMCOMP_HOME_DIR.length());
		defaultHomeDir.append("oracle").append(fileNameComponentSep).append("12.1.3_12.2.6").appendChar(fileNameComponentSep);
		DEFAULT_HORIZONS_INPUT_DIR = defaultHomeDir.onlyHasCStringChars() ? 
										newFilePath(defaultHomeDir.to_Char8Array(), false/*onlyNameSafeChars*/, defaultHomeDir.onlyHasAsciiChars(), true/*trustworthyCharArray*/) : 
										newFilePath(defaultHomeDir.toCharArray(), false/*onlyNameSafeChars*/, true/*trustworthyCharArray*/)
										; 
	}
	
	static final Logger getLogger() {
		return null;
	}
	/**
	* The default initial capacity.<br>
	*/
	public static final short DEFAULT_INITIAL_CAPACITY = core.util.SysPropUtil.getNonNegativeShortIntSysProp("zz.ebs.dmcomp.output.stream.buffer.initialCapacity", (short)4096, getLogger()); //1024;
	/**
	* 
	*/
	public static final boolean DEFAULT_AUTO_FLUSH_OUTPUT_STEAMS = core.util.SysPropUtil.getBooleanSysProp("zz.ebs.dmcomp.output.stream.autoFlushStreams", true, getLogger()); //1024;

	
	
	/**
	* Structure for regrouping the arguments needed by method {@link #convertHtmlReportToPsvDataFile(string.String,string.String,string.String,string.String,string.String,string.String,string.String,string.String,BufferedTextOutputStream,BufferedTextOutputStream,BufferedTextOutputStream,string.CharBuffer,DMCompEnumSets) convertHtmlReportToPsvDataFile()} 
	*
	* @author Marc E. KAMGA
	* @version 1.0 
	* @copyright Marc E. KAMGA
	*/
	public static class DMRptHtml2PsvUtilArgs implements IDMCompDiffsPSVFileTypes, java.io.Serializable {

		/**The class's serial version UID. */
		public static final long serialVersionUID = 0x0209CA90E1B6928AL;
		
		public String dmDiffsComparison; 
		public String summaryComment; 
		public String tag; 
		public String longTag; //since 2017-06-21
		public string.String inputHtmlFileDir; 
		public string.String inputHtmlFileName; 
		public string.String outputPsvDataFilesDir; 
		public string.String refDataOutputPsvDataFileName; 
		public string.String summaryOutputPsvDataFileName; 
		public string.String objectsOutputPsvDataFileName; 
		public string.String detailPathsOutputPsvDataFileName; 
		public string.String detailsOutputPsvDataFileName; 
		public string.String srcFilesOutputPsvDataFileName;
		public string.String objChgsOutputPsvDataFileName;
		public string.String transcoOutputPsvDataFileName;
		public string.String errorsOutputPsvDataFileName; 
		protected BufferedTextOutputStream refDataUtf8BufferedOS; 
		protected BufferedTextOutputStream objectsUtf8BufferedOS; 
		protected BufferedTextOutputStream errorUtf8BufferedOS; 
		protected string.CharBuffer tempBuffer; 
		protected DMCompEnumSets enumSets;
		protected int buffersInitialCapacity;
		protected int outputAutoFlushSize;
		protected boolean outptStreamsAutoFlush;
		
		transient eBSProduct product;
		final transient String[] checkTextRsltHolder = new String[3];
		
		BufferedTextOutputStream summaryOutStream;
		BufferedTextOutputStream detailPathsOutStream;
		BufferedTextOutputStream detailsOutStream;
		
		BufferedTextOutputStream sourceFilesOutStream;
		BufferedTextOutputStream tblObjChangesOutStream;
		
		BufferedTextOutputStream transcoUtf8BufferedOS;
		
		byte flushed;
		
		/**
		* @see IDMCompDiffsPSVFileTypes IDMCompDiffsPSVFileTypes
		*/
		public byte filesToGZip;
		
		transient boolean outputStreamsInitialized;
		boolean multipleInputsIntoSingleOutput;
		
		/**
		* Constructor.<br>
		*/
		public DMRptHtml2PsvUtilArgs(final String inputHtmlFileDir, final String inputHtmlFileName, final int buffersInitialCapacity, final boolean outptStreamsAutoFlush) {
			super();
			this.buffersInitialCapacity = buffersInitialCapacity;
			this.outputAutoFlushSize = outptStreamsAutoFlush ? buffersInitialCapacity : 0x7FFFFFFF; 
			this.outptStreamsAutoFlush = outptStreamsAutoFlush;
			this.summaryComment = String.EMPTY;
			this.tag = String.EMPTY;
			this.longTag = String.EMPTY; //since 2017-06-21
			this.dmDiffsComparison = String.EMPTY;
			this.flushed = FLUSHES_AND_CLOSES_WRITE_NOT_STARTED;
			this.inputHtmlFileDir = inputHtmlFileDir;
			this.inputHtmlFileName = inputHtmlFileName;
			this.filesToGZip = 0;
			this.outputStreamsInitialized = false;
			this.multipleInputsIntoSingleOutput = false;
		}
		/**
		* Constructor.<br>
		*/
		public DMRptHtml2PsvUtilArgs(final String inputHtmlFileName, final int buffersInitialCapacity, final boolean outptStreamsAutoFlush) {
			this(null, inputHtmlFileName, buffersInitialCapacity, outptStreamsAutoFlush);
		}
		/**
		* Constructor.<br>
		*/
		public DMRptHtml2PsvUtilArgs(final int buffersInitialCapacity, final boolean outptStreamsAutoFlush) {
			this(null, buffersInitialCapacity, outptStreamsAutoFlush);
		}
		/**
		* Constructor.<br>
		*/
		public DMRptHtml2PsvUtilArgs(final String inputHtmlFileName) {
			this(null, inputHtmlFileName, DEFAULT_INITIAL_CAPACITY, DEFAULT_AUTO_FLUSH_OUTPUT_STEAMS);
		}
		
		/**
		* Default constructor.<br>
		*/
		public DMRptHtml2PsvUtilArgs() {
			this(DEFAULT_INITIAL_CAPACITY, DEFAULT_AUTO_FLUSH_OUTPUT_STEAMS/*outptStreamsAutoFlush*/);
		}
		
		/**
		* Constructor.<br>
		*/
		public DMRptHtml2PsvUtilArgs(final String inputHtmlFileDir, final String inputHtmlFileName, final int buffersInitialCapacity, final int outptStreamsAutoFlushSize) {
			super();
			this.buffersInitialCapacity = buffersInitialCapacity;
			this.outputAutoFlushSize = outptStreamsAutoFlushSize; 
			this.outptStreamsAutoFlush = (outptStreamsAutoFlushSize > -1 && outptStreamsAutoFlushSize < 0x7FFFFFFF);
			this.summaryComment = String.EMPTY;
			this.tag = String.EMPTY;
			this.longTag = String.EMPTY; //since 2017-06-21
			this.dmDiffsComparison = String.EMPTY;
			this.flushed = FLUSHES_AND_CLOSES_WRITE_NOT_STARTED;
			this.inputHtmlFileDir = inputHtmlFileDir;
			this.inputHtmlFileName = inputHtmlFileName;
			this.outputStreamsInitialized = false;
			this.multipleInputsIntoSingleOutput = false;
		}
		/**
		* Constructor.<br>
		*/
		public DMRptHtml2PsvUtilArgs(final String inputHtmlFileName, final int buffersInitialCapacity, final int outptStreamsAutoFlushSize) {
			this(null, inputHtmlFileName, buffersInitialCapacity, outptStreamsAutoFlushSize);
		}
		/**
		* Constructor.<br>
		*/
		public DMRptHtml2PsvUtilArgs(final int buffersInitialCapacity, final int outptStreamsAutoFlushSize) {
			this(null, buffersInitialCapacity, outptStreamsAutoFlushSize);
		}
		/**
		* Constructor.<br>
		*/
		public DMRptHtml2PsvUtilArgs(final string.String inputHtmlFileDir, 
											final string.String outputPsvDataFilesDir, 
											final string.String outputFileNamesSuffix, 
											final boolean outptStreamsAutoFlush, 
											final boolean multipleInputsIntoSingleOutput, 
											final int buffersInitialCapacity) {
			super();
			this.buffersInitialCapacity = buffersInitialCapacity;
			this.outputAutoFlushSize = outptStreamsAutoFlush ? buffersInitialCapacity : 0x7FFFFFFF; 
			this.outptStreamsAutoFlush = outptStreamsAutoFlush;
			this.summaryComment = String.EMPTY;
			this.tag = String.EMPTY;
			this.longTag = String.EMPTY; //since 2017-06-21
			this.dmDiffsComparison = String.EMPTY;
			this.flushed = FLUSHES_AND_CLOSES_WRITE_NOT_STARTED;
			this.inputHtmlFileDir = inputHtmlFileDir;
			this.outputPsvDataFilesDir = outputPsvDataFilesDir;
			this.filesToGZip = 0;
			setOuputFileNames(outputFileNamesSuffix);
			this.outputStreamsInitialized = false;
			this.multipleInputsIntoSingleOutput = multipleInputsIntoSingleOutput;
		}
		public DMRptHtml2PsvUtilArgs(final string.String inputHtmlFileDir, 
											final string.String outputPsvDataFilesDir, 
											final string.String outputFileNamesSuffix, 
											final boolean outptStreamsAutoFlush, 
											final int buffersInitialCapacity) {
			this(inputHtmlFileDir, outputPsvDataFilesDir, outputFileNamesSuffix, outptStreamsAutoFlush, false/*multipleInputsIntoSingleOutput*/, buffersInitialCapacity);
		}
		public DMRptHtml2PsvUtilArgs(final string.String inputHtmlFileDir, 
											final string.String outputPsvDataFilesDir, 
											final string.String outputFileNamesSuffix, 
											final boolean multipleInputsIntoSingleOutput) {
			this(inputHtmlFileDir, outputPsvDataFilesDir, outputFileNamesSuffix, DEFAULT_AUTO_FLUSH_OUTPUT_STEAMS, multipleInputsIntoSingleOutput, DEFAULT_INITIAL_CAPACITY);
		}
		public DMRptHtml2PsvUtilArgs(final string.String inputHtmlFileDir, 
											final string.String outputPsvDataFilesDir, 
											final string.String outputFileNamesSuffix) {
			this(inputHtmlFileDir, outputPsvDataFilesDir, outputFileNamesSuffix, DEFAULT_AUTO_FLUSH_OUTPUT_STEAMS, DEFAULT_INITIAL_CAPACITY);
		}
				
			
		final void setOuputFileNames(final string.String outputFileNamesSuffix) {
			if (tempBuffer == null) {
				tempBuffer = new string.CharBuffer(32);
			}
			else {
				tempBuffer.resetLimitOnly();
			}
			tempBuffer.append(outputFileNamesSuffix).appendChar('.');
			final int prefixLen = tempBuffer.length();
			tempBuffer.appendChars("refdata");
			refDataOutputPsvDataFileName = tempBuffer.ToString(true/*createAsciiStrIfPossible*/); 
			tempBuffer.setLimit(prefixLen);
			tempBuffer.appendChars("summary");
			summaryOutputPsvDataFileName = tempBuffer.ToString(true/*createAsciiStrIfPossible*/);  
			tempBuffer.setLimit(prefixLen);
			tempBuffer.appendChars("objects");
			objectsOutputPsvDataFileName = tempBuffer.ToString(true/*createAsciiStrIfPossible*/);  
			tempBuffer.setLimit(prefixLen);
			tempBuffer.appendChars("detailpaths");
			detailPathsOutputPsvDataFileName = tempBuffer.ToString(true/*createAsciiStrIfPossible*/);  
			tempBuffer.setLimit(prefixLen);
			tempBuffer.appendChars("details");
			detailsOutputPsvDataFileName = tempBuffer.ToString(true/*createAsciiStrIfPossible*/);  
			tempBuffer.setLimit(prefixLen);
			tempBuffer.appendChars("sources");
			srcFilesOutputPsvDataFileName = tempBuffer.ToString(true/*createAsciiStrIfPossible*/); 
			tempBuffer.setLimit(prefixLen);
			tempBuffer.appendChars("objchanges");
			objChgsOutputPsvDataFileName = tempBuffer.ToString(true/*createAsciiStrIfPossible*/); 
			tempBuffer.setLimit(prefixLen);
			tempBuffer.appendChars("err");
			errorsOutputPsvDataFileName = tempBuffer.ToString(true/*createAsciiStrIfPossible*/);  
			
			tempBuffer.setLimit(prefixLen);
			tempBuffer.appendChars("transco");
			transcoOutputPsvDataFileName = tempBuffer.ToString(true/*createAsciiStrIfPossible*/);  
			
		}
		
		/**
		* @see #FLUSHES_AND_CLOSES_ALL_RIGHT_AFTER FLUSHES_AND_CLOSES_ALL_RIGHT_AFTER
		* @see #FLUSHES_AND_CLOSES_NON_SHARED_RIGHT_AFTER FLUSHES_AND_CLOSES_NON_SHARED_RIGHT_AFTER
		* @see #FLUSHES_AND_CLOSES_WRITE_NOT_STARTED FLUSHES_AND_CLOSES_WRITE_NOT_STARTED
		*/
		public final byte flushed() {return flushed; }
		
		
		public void closeAllOutputStreams() {
			__flushesAndClosesOutputStream(refDataUtf8BufferedOS);
			__flushesAndClosesOutputStream(sourceFilesOutStream);
			__flushesAndClosesOutputStream(objectsUtf8BufferedOS);
			__flushesAndClosesOutputStream(summaryOutStream);
			__flushesAndClosesOutputStream(detailPathsOutStream);
			__flushesAndClosesOutputStream(tblObjChangesOutStream);
			__flushesAndClosesOutputStream(detailsOutStream);
			__flushesAndClosesOutputStream(transcoUtf8BufferedOS);
			__flushesAndClosesOutputStream(errorUtf8BufferedOS);
		}
		
	}
	
	private static final void __flushesAndClosesOutputStream(final BufferedTextOutputStream outputStream) {
		try 
		{
			outputStream.flush();
		}
		catch(Exception ex)
		{
			//INGORE
		}
		finally
		{
			try {outputStream.close();} catch(Exception e) {/*IGNORE*/ }
		}
	}
	
	/**
	* Converts the data model comparison report html file specified as input file argument to a <code>.psv</code> data file.<br>
	* @param flushesAndCloseOutputStreamsRightAfter flags giving the indication as to whether the output streams must be flushed and closed upon the completion of the conversion  - see {@link #FLUSHES_AND_CLOSES_NON_SHARED_RIGHT_AFTER FLUSHES_AND_CLOSES_NON_SHARED_RIGHT_AFTER} and {@link #FLUSHES_AND_CLOSES_ALL_RIGHT_AFTER FLUSHES_AND_CLOSES_ALL_RIGHT_AFTER}
	*/
	public static final void convertHtmlReportToPsvDataFile(final DMRptHtml2PsvUtilArgs args, final byte flushesAndCloseOutputStreamsRightAfter, final byte filesToGZip) {
		DataModelComparisonReport rpt = null;
		if (args.enumSets == null) {
			args.enumSets = new DMCompEnumSets();
		}
		if (args.tempBuffer == null) {
			args.tempBuffer = new string.CharBuffer(128);
		}
		else {
			args.tempBuffer.resetLimitOnly();
		}
		if (!args.multipleInputsIntoSingleOutput || !args.outputStreamsInitialized) {
//			System.out.println("args.multipleInputsIntoSingleOutput: " + args.multipleInputsIntoSingleOutput);
			if (args.inputHtmlFileDir == null || args.inputHtmlFileDir.isEmpty()) {
				args.tempBuffer.resetLimitOnly();
				args.inputHtmlFileDir = args.tempBuffer.append(DEFAULT_DMCOMP_HOME_DIR).append("oracle").append(fileNameComponentSep).append("12.1.3_12.2.6").append(fileNameComponentSep).ToString(true/*createAsciiStrIfPossible*/);
			}
			try 
			{
				rpt = readDataModelComparisonReport(args.inputHtmlFileDir, args.inputHtmlFileName, HTMLTemplate.NO_PARAMS_ACCEPTED_AT_ALL/*acceptedParamTypes*/, false/*documentFragmentAllowed*/, args.enumSets, args.tempBuffer);
	//			System.out.println(":::::::::::::::::::: - " + args.inputHtmlFileName + ", rpt.sourceFile: " + rpt.sourceFile);
				if (rpt.sourceFile == null || rpt.sourceFile.isEmpty()) { //since 2017-06-21
					rpt.sourceFile = args.inputHtmlFileName;
				}
			}
			catch(Exception ex)
			{
				throw new RuntimeException(ex.getMessage() + java.lang.System.lineSeparator() + 
				"DMRptHtml2PsvUtil::convertHtmlReportToPsvDataFile-1: error while trying to read the input report html file (" + args.inputHtmlFileName + ")"
				, ex
				);
			}
	//		System.out.println("rpt: \r\n" + rpt);
			if (args.outputPsvDataFilesDir == null || args.outputPsvDataFilesDir.isEmpty()) {
				args.tempBuffer.resetLimitOnly();
				args.outputPsvDataFilesDir = args.tempBuffer.append(DEFAULT_DMCOMP_HOME_DIR).append("horizons").append(fileNameComponentSep).append("output").append(fileNameComponentSep).ToString(true/*createAsciiStrIfPossible*/);
			}
			final String sepBetweenPatAndName = args.outputPsvDataFilesDir.charAt(args.outputPsvDataFilesDir.length() - 1) == fileNameComponentSep ? String.EMPTY : string.Char.valueOf(fileNameComponentSep);
			final int outputFileNameStart = sepBetweenPatAndName.isEmpty()/*filesDirEndsWithSep*/ ? args.outputPsvDataFilesDir.length() + 1 : args.outputPsvDataFilesDir.length() + 2;
			//string.CharBuffer outputFilePath = new string.CharBuffer(args.outputPsvDataFilesDir.length() + 31);
			args.tempBuffer/*outputFilePath*/.ensureCanAddNMoreChars(args.outputPsvDataFilesDir.length() + 31);
			args.tempBuffer/*outputFilePath*/.resetLimitOnly();
			
			string.CharBuffer tempBuff = null;
			
			//open summary file for output
			if (args.summaryOutputPsvDataFileName == null || args.summaryOutputPsvDataFileName.isEmpty()) {
				tempBuff = new string.CharBuffer(40);
				args.summaryOutputPsvDataFileName = tempBuff.append(args.inputHtmlFileName).append(".summary").ToString(true/*createAsciiStrIfPossible*/);
			}
			
			args.tempBuffer/*outputFilePath*/.append(args.outputPsvDataFilesDir).append(sepBetweenPatAndName).appendChars(args.summaryOutputPsvDataFileName);
			OutputStream fos = null;
			try 
			{
				fos = new java.io.FileOutputStream(args.tempBuffer/*outputFilePath*/.toString());
				if ((filesToGZip & SUMMARY_FILE) != 0) {
					fos = new GZIPOutputStream(fos);
				}
				UTF8TextOutputStreamBuffer outputStreaBuf = new UTF8TextOutputStreamBuffer(args.buffersInitialCapacity);
				args.summaryOutStream = newBufferedTextOutputStream(fos, outputStreaBuf, args.outputAutoFlushSize);
				writeSummaryHeader(args.summaryOutStream);
			}
			catch(java.io.IOException ioe)
			{
				throw new RuntimeException(ioe.getMessage() + java.lang.System.lineSeparator() + 
				"DMRptHtml2PsvUtil::convertHtmlReportToPsvDataFile-2: I/O error while trying to open an output .psv file (" + args.tempBuffer/*outputFilePath*/ + ")"
				, ioe
				);
			}
			
			//open detailPath file for output
			args.tempBuffer/*outputFilePath*/.setLimit(outputFileNameStart);
			if (args.detailPathsOutputPsvDataFileName == null || args.detailPathsOutputPsvDataFileName.isEmpty()) {
				if (tempBuff == null) {
					tempBuff = new string.CharBuffer(40);
				}
				else {
					tempBuff.resetLimitOnly();
				}
				args.detailPathsOutputPsvDataFileName = tempBuff.append(args.inputHtmlFileName).append(".detailpaths").ToString(true/*createAsciiStrIfPossible*/);
			}
			args.tempBuffer/*outputFilePath*/.appendChars(args.detailPathsOutputPsvDataFileName);
			try 
			{
				fos = new java.io.FileOutputStream(args.tempBuffer/*outputFilePath*/.toString());
				if ((filesToGZip & DETAIL_PATHS_FILE) != 0) {
					fos = new GZIPOutputStream(fos);
				}
				UTF8TextOutputStreamBuffer outputStreaBuf = new UTF8TextOutputStreamBuffer(args.buffersInitialCapacity);
				args.detailPathsOutStream = newBufferedTextOutputStream(fos, outputStreaBuf, args.outputAutoFlushSize);
				writeDetailPathHeader(args.detailPathsOutStream);
			}
			catch(java.io.IOException ioe)
			{
				throw new RuntimeException(ioe.getMessage() + java.lang.System.lineSeparator() + 
				"DMRptHtml2PsvUtil::convertHtmlReportToPsvDataFile-3: I/O error while trying to open an output .psv file (" + args.tempBuffer/*outputFilePath*/ + ")"
				, ioe
				);
			}
			
			//open detail file for output
			args.tempBuffer/*outputFilePath*/.setLimit(outputFileNameStart);
			if (args.detailsOutputPsvDataFileName == null || args.detailsOutputPsvDataFileName.isEmpty()) {
				if (tempBuff == null) {
					tempBuff = new string.CharBuffer(40);
				}
				else {
					tempBuff.resetLimitOnly();
				}
				args.detailsOutputPsvDataFileName = tempBuff.append(args.inputHtmlFileName).append(".details").ToString(true/*createAsciiStrIfPossible*/);
			}
			args.tempBuffer/*outputFilePath*/.appendChars(args.detailsOutputPsvDataFileName);
			try 
			{
				fos = new java.io.FileOutputStream(args.tempBuffer/*outputFilePath*/.toString());
				if ((filesToGZip & DETAILS_FILE) != 0) {
					fos = new GZIPOutputStream(fos);
				}
				UTF8TextOutputStreamBuffer outputStreaBuf = new UTF8TextOutputStreamBuffer(args.buffersInitialCapacity);
				args.detailsOutStream = newBufferedTextOutputStream(fos, outputStreaBuf, /*filesToGZip ?  -1 :  */args.outputAutoFlushSize);
				writeDiffsDetailHeader(args.detailsOutStream);
			}
			catch(java.io.IOException ioe)
			{
				throw new RuntimeException(ioe.getMessage() + java.lang.System.lineSeparator() + 
				"DMRptHtml2PsvUtil::convertHtmlReportToPsvDataFile-4: I/O error while trying to open an output .psv file (" + args.tempBuffer/*outputFilePath*/ + ")"
				, ioe
				);
			}
			//
			//open object-changes file for output
			args.tempBuffer/*outputFilePath*/.setLimit(outputFileNameStart);
			if (args.objChgsOutputPsvDataFileName == null || args.objChgsOutputPsvDataFileName.isEmpty()) {
				if (tempBuff == null) {
					tempBuff = new string.CharBuffer(40);
				}
				else {
					tempBuff.resetLimitOnly();
				}
				args.objChgsOutputPsvDataFileName = tempBuff.append(args.inputHtmlFileName).append(".objchanges").ToString(true/*createAsciiStrIfPossible*/);
			}
			args.tempBuffer/*outputFilePath*/.appendChars(args.objChgsOutputPsvDataFileName);
			try 
			{
				fos = new java.io.FileOutputStream(args.tempBuffer/*outputFilePath*/.toString());
				if ((filesToGZip & OBJECT_CHANGES_FILE) != 0) {
					fos = new GZIPOutputStream(fos);
				}
				UTF8TextOutputStreamBuffer outputStreaBuf = new UTF8TextOutputStreamBuffer(args.buffersInitialCapacity);
				args.tblObjChangesOutStream = newBufferedTextOutputStream(fos, outputStreaBuf, args.outputAutoFlushSize);
				writeDiffsTblObjectChangeHeader(args.tblObjChangesOutStream);
			}
			catch(java.io.IOException ioe)
			{
				throw new RuntimeException(ioe.getMessage() + java.lang.System.lineSeparator() + 
				"DMRptHtml2PsvUtil::convertHtmlReportToPsvDataFile-4: I/O error while trying to open an output .psv file (" + args.tempBuffer/*outputFilePath*/ + ")"
				, ioe
				);
			}
			//
			//open source-files file for output
			args.tempBuffer/*outputFilePath*/.setLimit(outputFileNameStart);
			if (args.srcFilesOutputPsvDataFileName == null || args.srcFilesOutputPsvDataFileName.isEmpty()) {
				if (tempBuff == null) {
					tempBuff = new string.CharBuffer(40);
				}
				else {
					tempBuff.resetLimitOnly();
				}
				args.srcFilesOutputPsvDataFileName = tempBuff.append(args.inputHtmlFileName).append(".sources").ToString(true/*createAsciiStrIfPossible*/);
			}
			args.tempBuffer/*outputFilePath*/.appendChars(args.srcFilesOutputPsvDataFileName);
			try 
			{
				fos = new java.io.FileOutputStream(args.tempBuffer/*outputFilePath*/.toString());
				if ((filesToGZip & SOURCES_FILE) != 0) {
					fos = new GZIPOutputStream(fos);
				}
				UTF8TextOutputStreamBuffer outputStreaBuf = new UTF8TextOutputStreamBuffer(args.buffersInitialCapacity);
				args.sourceFilesOutStream = newBufferedTextOutputStream(fos, outputStreaBuf, args.outputAutoFlushSize);
				writeSourceFileHeader(args.sourceFilesOutStream);
			}
			catch(java.io.IOException ioe)
			{
				throw new RuntimeException(ioe.getMessage() + java.lang.System.lineSeparator() + 
				"DMRptHtml2PsvUtil::convertHtmlReportToPsvDataFile-4: I/O error while trying to open an output .psv file (" + args.tempBuffer/*outputFilePath*/ + ")"
				, ioe
				);
			}
			
			if (args.refDataUtf8BufferedOS == null) {
				//open ref data file for output
				args.tempBuffer/*outputFilePath*/.setLimit(outputFileNameStart);
				if (args.refDataOutputPsvDataFileName == null || args.refDataOutputPsvDataFileName.isEmpty()) {
					if (tempBuff == null) {
						tempBuff = new string.CharBuffer(40);
					}
					else {
						tempBuff.resetLimitOnly();
					}
					args.refDataOutputPsvDataFileName = tempBuff.append(args.inputHtmlFileName).append(".refdata").ToString(true/*createAsciiStrIfPossible*/);
				}
				args.tempBuffer/*outputFilePath*/.appendChars(args.refDataOutputPsvDataFileName);
				try 
				{
					fos = new java.io.FileOutputStream(args.tempBuffer/*outputFilePath*/.toString());
					if ((filesToGZip & REF_DATA_FILE) != 0) {
						fos = new GZIPOutputStream(fos);
					}
					UTF8TextOutputStreamBuffer outputStreaBuf = new UTF8TextOutputStreamBuffer(args.buffersInitialCapacity);
					args.refDataUtf8BufferedOS = newBufferedTextOutputStream(fos, outputStreaBuf, args.outputAutoFlushSize);
					writeRefDataHeader(args.refDataUtf8BufferedOS);
				}
				catch(java.io.IOException ioe)
				{
					throw new RuntimeException(ioe.getMessage() + java.lang.System.lineSeparator() + 
					"DMRptHtml2PsvUtil::convertHtmlReportToPsvDataFile-5: I/O error while trying to open an output .psv file (" + args.tempBuffer/*outputFilePath*/ + ")"
					, ioe
					);
				}
			}
			
			if (args.objectsUtf8BufferedOS == null) {
				//open objects file for output
				args.tempBuffer/*outputFilePath*/.setLimit(outputFileNameStart);
				if (args.objectsOutputPsvDataFileName == null || args.objectsOutputPsvDataFileName.isEmpty()) {
					if (tempBuff == null) {
						tempBuff = new string.CharBuffer(40);
					}
					else {
						tempBuff.resetLimitOnly();
					}
					args.objectsOutputPsvDataFileName = tempBuff.append(args.inputHtmlFileName).append(".objects").ToString(true/*createAsciiStrIfPossible*/);
				}
				args.tempBuffer/*outputFilePath*/.appendChars(args.objectsOutputPsvDataFileName);
				try 
				{
					fos = new java.io.FileOutputStream(args.tempBuffer/*outputFilePath*/.toString());
					if ((filesToGZip & OBJECTS_FILE) != 0) {
						fos = new GZIPOutputStream(fos);
					}
					UTF8TextOutputStreamBuffer outputStreaBuf = new UTF8TextOutputStreamBuffer(args.buffersInitialCapacity);
					args.objectsUtf8BufferedOS = newBufferedTextOutputStream(fos, outputStreaBuf, args.outputAutoFlushSize);
					writeObjectHeader(args.objectsUtf8BufferedOS);
				}
				catch(java.io.IOException ioe)
				{
					throw new RuntimeException(ioe.getMessage() + java.lang.System.lineSeparator() + 
					"DMRptHtml2PsvUtil::convertHtmlReportToPsvDataFile-6: I/O error while trying to open an output .psv file (" + args.tempBuffer/*outputFilePath*/ + ")"
					, ioe
					);
				}
			}
			if (args.errorUtf8BufferedOS == null) {
				//open errors file for output
				args.tempBuffer/*outputFilePath*/.setLimit(outputFileNameStart);
				if (args.errorsOutputPsvDataFileName == null || args.errorsOutputPsvDataFileName.isEmpty()) {
					if (tempBuff == null) {
						tempBuff = new string.CharBuffer(40);
					}
					else {
						tempBuff.resetLimitOnly();
					}
					args.errorsOutputPsvDataFileName = tempBuff.append(args.inputHtmlFileName).append(".err").ToString(true/*createAsciiStrIfPossible*/);
				}
				args.tempBuffer/*outputFilePath*/.appendChars(args.errorsOutputPsvDataFileName);
				try 
				{
					fos = new java.io.FileOutputStream(args.tempBuffer/*outputFilePath*/.toString());
					if ((filesToGZip & ERRORS_FILE) != 0) {
						fos = new GZIPOutputStream(fos);
					}
					UTF8TextOutputStreamBuffer outputStreaBuf = new UTF8TextOutputStreamBuffer(args.buffersInitialCapacity);
					args.errorUtf8BufferedOS = newBufferedTextOutputStream(fos, outputStreaBuf, args.outputAutoFlushSize);
					writeDiffsOuputErrorHeader(args.errorUtf8BufferedOS);
				}
				catch(java.io.IOException ioe)
				{
					throw new RuntimeException(ioe.getMessage() + java.lang.System.lineSeparator() + 
					"DMRptHtml2PsvUtil::convertHtmlReportToPsvDataFile-7: I/O error while trying to open an output .psv file (" + args.tempBuffer/*outputFilePath*/ + ")"
					, ioe
					);
				}
			}
			
			if (args.transcoUtf8BufferedOS == null) {
				//open errors file for output
				args.tempBuffer/*outputFilePath*/.setLimit(outputFileNameStart);
				if (args.transcoOutputPsvDataFileName == null || args.transcoOutputPsvDataFileName.isEmpty()) {
					if (tempBuff == null) {
						tempBuff = new string.CharBuffer(40);
					}
					else {
						tempBuff.resetLimitOnly();
					}
					args.transcoOutputPsvDataFileName = tempBuff.append(args.inputHtmlFileName).append(".transco").ToString(true/*createAsciiStrIfPossible*/);
				}
				args.tempBuffer/*outputFilePath*/.appendChars(args.transcoOutputPsvDataFileName);
				try 
				{
					fos = new java.io.FileOutputStream(args.tempBuffer/*outputFilePath*/.toString());
					UTF8TextOutputStreamBuffer outputStreaBuf = new UTF8TextOutputStreamBuffer(args.buffersInitialCapacity);
					args.transcoUtf8BufferedOS = newBufferedTextOutputStream(fos, outputStreaBuf, args.outputAutoFlushSize);
					writeDiffsOuputTranscoHeader(args.transcoUtf8BufferedOS);
				}
				catch(java.io.IOException ioe)
				{
					throw new RuntimeException(ioe.getMessage() + java.lang.System.lineSeparator() + 
					"DMRptHtml2PsvUtil::convertHtmlReportToPsvDataFile-8: I/O error while trying to open an output .psv file (" + args.tempBuffer/*outputFilePath*/ + ")"
					, ioe
					);
				}
			}
			tempBuff = null;
			args.outputStreamsInitialized = true;
		}
		else {
			try 
			{
				rpt = readDataModelComparisonReport(args.inputHtmlFileDir, args.inputHtmlFileName, HTMLTemplate.NO_PARAMS_ACCEPTED_AT_ALL/*acceptedParamTypes*/, false/*documentFragmentAllowed*/, args.enumSets, args.tempBuffer);
	//			System.out.println(":::::::::::::::::::: - " + args.inputHtmlFileName + ", rpt.sourceFile: " + rpt.sourceFile);
				if (rpt.sourceFile == null || rpt.sourceFile.isEmpty()) { //since 2017-06-21
					rpt.sourceFile = args.inputHtmlFileName;
				}
			}
			catch(Exception ex)
			{
				throw new RuntimeException(ex.getMessage() + java.lang.System.lineSeparator() + 
				"DMRptHtml2PsvUtil::convertHtmlReportToPsvDataFile-9: error while trying to read the input report html file (" + args.inputHtmlFileName + ")"
				, ex
				);
			}
		}
		
		if (args.dmDiffsComparison == null || args.dmDiffsComparison.isEmpty()) {
			args.dmDiffsComparison = DATA_MODEL_COMP_12_1_3_12_2_6_SHORT_CODE; //OLD: DATA_MODEL_COMP_12_1_3_12_2_6; //changed to DATA_MODEL_COMP_12_1_3_12_2_6_SHORT_CODE on 2017-06-21
		}
		if (args.tag == null || args.tag.isEmpty()) {
			if (args.longTag == null || args.longTag.isEmpty()) {
				args.tempBuffer.resetLimitOnly();
				args.tempBuffer.append("HORIZONS-DMCOMP-").append(get_nowDtm()).append('-').append(args.dmDiffsComparison).appendChars("###");
				args.tag = new string.Code((byte)'T', (byte)'#');
				args.longTag = args.tempBuffer.ToString(true/*createAsciiStrIfPossible*/);
			}
			else {
				args.tag = args.longTag;
			}
		}
		else if (args.longTag == null || args.longTag.isEmpty()) {
			args.longTag = args.tag;
		}
		if (args.summaryComment == null) {
			args.summaryComment = String.EMPTY;
		}
		if ((args.flushed & FLUSHES_AND_CLOSES_WRITE_NOT_STARTED) != 0) {
			args.flushed = FLUSHES_AND_CLOSES_WRITE_STARTED;
		}
		
		writeDiffsOuputTransco(DIFFS_COMPARISON_CATEGORY, args.dmDiffsComparison, DATA_MODEL_COMP_12_1_3_12_2_6, args.transcoUtf8BufferedOS);
		writeDiffsOuputTransco(TAG_CATEGORY, args.tag, args.longTag, args.transcoUtf8BufferedOS);
		writeDiffsOuputTransco(SOURCE_FILE_CATEGORY, rpt.getSourceFileCode(), rpt.sourceFile, args.transcoUtf8BufferedOS);
		
//		System.out.println(":::::::::::::::::: rpt.sourceFile: " + rpt.sourceFile + ", rpt.sourceFileCode: " + rpt.getSourceFileCode());
		args.product = writeDMComparisonReport(rpt, 
									args.summaryComment, 
									args.dmDiffsComparison, 
									args.tag, 
									args.enumSets, 
									args.refDataUtf8BufferedOS, 
									args.summaryOutStream, 
									args.tblObjChangesOutStream, 
									args.detailsOutStream, 
									args.errorUtf8BufferedOS, 
									args.tempBuffer, 
									args.checkTextRsltHolder);
									
		writeSourceFile(args.inputHtmlFileName/*sourceFile*/, 
						rpt.sourceFileNumber, 
						args.dmDiffsComparison, 
						args.product.code, 
						args.product.number, 
						args.tag, 
						args.sourceFilesOutStream);
									
		//NOTE: changed from args.inputHtmlFileName to rpt.getSourceFileCode() on 2017-06-21
		completeWriting(args.dmDiffsComparison, 
									rpt.getSourceFileCode()/*args.inputHtmlFileName*//*sourceFile*/, 
									rpt.sourceFileNumber, 
									args.product, 
									args.tag, 
									args.enumSets, 
									args.refDataUtf8BufferedOS,  
									args.objectsUtf8BufferedOS, 
									args.detailPathsOutStream, 
									args.sourceFilesOutStream, 
									args.errorUtf8BufferedOS, 
									args.tempBuffer);
		if ((flushesAndCloseOutputStreamsRightAfter & FOR_SURE_FLUSHES_AND_CLOSES_NON_SHARED_RIGHT_AFTER) != 0) {
			__flushesAndClosesOutputStream(args.summaryOutStream);
			__flushesAndClosesOutputStream(args.detailPathsOutStream);
			__flushesAndClosesOutputStream(args.detailsOutStream);
			
			__flushesAndClosesOutputStream(args.sourceFilesOutStream);
			__flushesAndClosesOutputStream(args.tblObjChangesOutStream);
			
			args.summaryOutStream = null;
			args.detailPathsOutStream = null;
			args.detailsOutStream = null;
			
			args.sourceFilesOutStream = null;
			args.tblObjChangesOutStream = null;
		}
		
		if ((flushesAndCloseOutputStreamsRightAfter & FLUSHES_AND_CLOSES_ALL_RIGHT_AFTER) == FLUSHES_AND_CLOSES_ALL_RIGHT_AFTER) {
			__flushesAndClosesOutputStream(args.refDataUtf8BufferedOS);
			__flushesAndClosesOutputStream(args.objectsUtf8BufferedOS);
			__flushesAndClosesOutputStream(args.errorUtf8BufferedOS);
			
			args.refDataUtf8BufferedOS = null;
			args.objectsUtf8BufferedOS = null;
			args.errorUtf8BufferedOS = null;
		}
		
		args.flushed = flushesAndCloseOutputStreamsRightAfter;
	}
	/**
	* Converts the data model comparison report html file specified as input file argument to a <code>.psv</code> data file.<br>
	* @param flushesAndCloseOutputStreamsRightAfter flags giving the indication as to whether the output streams must be flushed and closed upon the completion of the conversion  - see {@link #FLUSHES_AND_CLOSES_NON_SHARED_RIGHT_AFTER FLUSHES_AND_CLOSES_NON_SHARED_RIGHT_AFTER} and {@link #FLUSHES_AND_CLOSES_ALL_RIGHT_AFTER FLUSHES_AND_CLOSES_ALL_RIGHT_AFTER}
	*/
	public static final void convertHtmlReportToPsvDataFile(final string.String inputHtmlFileName, final byte flushesAndCloseOutputStreamsRightAfter, final byte filesToGZip) {
		convertHtmlReportToPsvDataFile(new DMRptHtml2PsvUtilArgs(inputHtmlFileName)/*args*/, flushesAndCloseOutputStreamsRightAfter, filesToGZip);
	}
	/**
	* Converts the data model comparison report html file specified as input file argument to a <code>.psv</code> data file.<br>
	* @param flushesAndCloseOutputStreamsRightAfter flag giving the indication as to whether all the output streams must be flushed and closed upon the completion of the conversion  - see {@link #FLUSHES_AND_CLOSES_NON_SHARED_RIGHT_AFTER FLUSHES_AND_CLOSES_NON_SHARED_RIGHT_AFTER} and {@link #FLUSHES_AND_CLOSES_ALL_RIGHT_AFTER FLUSHES_AND_CLOSES_ALL_RIGHT_AFTER}
	*/
	public static final void convertHtmlReportToPsvDataFile(final string.String inputHtmlFileName, final boolean flushesAndCloseOutputStreamsRightAfter, final byte filesToGZip) {
		convertHtmlReportToPsvDataFile(new DMRptHtml2PsvUtilArgs(inputHtmlFileName)/*args*/, flushesAndCloseOutputStreamsRightAfter ? FLUSHES_AND_CLOSES_ALL_RIGHT_AFTER : (byte)0, filesToGZip);
	}
	
	private static final void printSequenceCurrentValues() {
		System.out.println();
		System.out.println("DataModelComparisonReport: " + DataModelComparisonReport.curentSequenceValue());
		System.out.println("DataModelComparisonReport - source: " + DataModelComparisonReport.currentSourceFileNumSeqValue());
		System.out.println("DiffsSummary: " + DiffsSummary.curentSequenceValue());
		System.out.println("DiffsDetailedChange: " + DiffsDetailedChange.curentSequenceValue());
		System.out.println("DiffsTableRow: " + DiffsTableRow.curentSequenceValue());
		System.out.println("DiffsTableRowChange: " + DiffsTableRowChange.curentSequenceValue());
		System.out.println("DiffsTable: " + DiffsTable.curentSequenceValue());
		System.out.println("DMCompDetailPath: " + DMCompDetailPath.curentSequenceValue());
		System.out.println("DMObject: " + DMObject.curentSequenceValue());
	}
	
	/*private */static final void convertHtmlReportsToPsv(final string.String[] htmlRptFileNames, final string.String inputHtmlFilesDir, 
													final string.String outputPsvDataFilesDir, final string.String outputPsvFileNamesSuffix) {
		DMRptHtml2PsvUtilArgs args = new DMRptHtml2PsvUtilArgs(inputHtmlFilesDir, 
											outputPsvDataFilesDir, 
											outputPsvFileNamesSuffix, 
											true/*multipleInputsIntoSingleOutput*/);
		for (int i=0;i<htmlRptFileNames.length;i++)
		{
			args.inputHtmlFileName = htmlRptFileNames[i];
			convertHtmlReportToPsvDataFile(args, (byte)0/*flushesAndCloseOutputStreamsRightAfter*/, NO_FILES_SPECIFIED/*filesToGZip*/);
		}
		if (htmlRptFileNames.length != 0) {
			args.closeAllOutputStreams();
		}
	}
	
	public static final void main(java.lang.String[] args) {
		System.out.println("DEFAULT_DMCOMP_HOME_DIR: " + DEFAULT_DMCOMP_HOME_DIR);
		System.out.println("DEFAULT_HORIZONS_OUTPUT_DIR: " + DEFAULT_HORIZONS_OUTPUT_DIR);
		
		string.String inputHtmlFileName = new string.CharBuffer(32).append(args.length > 0 && !args[0].isEmpty() ? args[0] : "BOM_nonNZD_diff.html").ToString(true/*createAsciiStrIfPossible*/); //INV_nonNZD_diff //GL_nonNZD_diff //PA_nonNZD_diff // PO_nonNZD_diff(?) //AR_nonNZD_diff(?)
		System.out.println("inputHtmlFileName: " + inputHtmlFileName);
		
		byte filesToGZip = NO_FILES_SPECIFIED;
		if (args.length > 1 && (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("Y") || args[1].equalsIgnoreCase("1"))) {
			filesToGZip = NON_SHARED_DATA_FILES;
		}
		//
		
		convertHtmlReportToPsvDataFile(inputHtmlFileName, true/*flushesAndCloseOutputStreamsRightAfter*/, filesToGZip/*filesToGZip*/);
		printSequenceCurrentValues();
	}


}