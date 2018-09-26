package zz.ebs.dmcomp;

import java.io.OutputStream;
import java.io.FileOutputStream;

import core.io.IOException;
import core.io.encoding.TextOutputStreamBuffer;
import core.io.encoding.UTF8TextOutputStreamBuffer;
import core.io.BufferedTextOutputStream;
import core.io.WriteToStreamException;

import string.ICharSequence;
import string.String;
import string.CString;

import static zz.ebs.dmcomp.DMRptHtml2PsvUtil.DEFAULT_HORIZONS_INPUT_DIR;
import static zz.ebs.dmcomp.DMRptHtml2PsvUtil.DEFAULT_HORIZONS_OUTPUT_DIR;
import static zz.ebs.dmcomp.DMRptHtml2PsvUtil.convertHtmlReportsToPsv;

import static string.util.AsciiStringFactoryInternals.astr;

/**
*
* @author Marc E. KAMGA
* @version 1.0
* @copyright Marc E. KAMGA
*/
final class MichEBSDiffsUtil {

	/**
	* Constant for <code>&quot;MICH_EBS_DIFFS_FROM_1213_TO_1226&quot;</code> 
	*/
	public static final String DEFAULT_OUTPUT_FILE_NAMES_SUFFIX = astr(new byte[]{'M', 'I', 'C', 'H', '_', 'E', 'B', 'S', '_', 'D', 'I', 'F', 'F', 'S', '_', 'F', 'R', 'O', 'M', '_', '1', '2', '1', '3', '_', 'T', 'O', '_', '1', '2', '2', '6'});
	
	
	
	private static final String[] MICH_EBS_DIFFS_V0_FILES = new String[]{
		astr(new byte[]{'G', 'L', '_', 'n', 'o', 'n', 'N', 'Z', 'D', '_', 'd', 'i', 'f', 'f', '.', 'h', 't', 'm', 'l'}) //GL
		, astr(new byte[]{'A', 'R', '_', 'n', 'o', 'n', 'N', 'Z', 'D', '_', 'd', 'i', 'f', 'f', '.', 'h', 't', 'm', 'l'}) //AR
		, astr(new byte[]{'A', 'P', '_', 'n', 'o', 'n', 'N', 'Z', 'D', '_', 'd', 'i', 'f', 'f', '.', 'h', 't', 'm', 'l'}) //AP
		, astr(new byte[]{'P', 'A', '_', 'n', 'o', 'n', 'N', 'Z', 'D', '_', 'd', 'i', 'f', 'f', '.', 'h', 't', 'm', 'l'}) //PA
		, astr(new byte[]{'P', 'O', '_', 'n', 'o', 'n', 'N', 'Z', 'D', '_', 'd', 'i', 'f', 'f', '.', 'h', 't', 'm', 'l'}) //PO
		, astr(new byte[]{'I', 'N', 'V', '_', 'n', 'o', 'n', 'N', 'Z', 'D', '_', 'd', 'i', 'f', 'f', '.', 'h', 't', 'm', 'l'}) //INV
		, astr(new byte[]{'O', 'E', '_', 'n', 'o', 'n', 'N', 'Z', 'D', '_', 'd', 'i', 'f', 'f', '.', 'h', 't', 'm', 'l'}) //OE
		, astr(new byte[]{'B', 'O', 'M', '_', 'n', 'o', 'n', 'N', 'Z', 'D', '_', 'd', 'i', 'f', 'f', '.', 'h', 't', 'm', 'l'}) //BOM
		, astr(new byte[]{'A', 'M', 'S', '_', 'n', 'o', 'n', 'N', 'Z', 'D', '_', 'd', 'i', 'f', 'f', '.', 'h', 't', 'm', 'l'}) //AMS
		, astr(new byte[]{'I', 'E', 'X', '_', 'n', 'o', 'n', 'N', 'Z', 'D', '_', 'd', 'i', 'f', 'f', '.', 'h', 't', 'm', 'l'}) //IEX
		, astr(new byte[]{'O', 'Z', 'F', '_', 'n', 'o', 'n', 'N', 'Z', 'D', '_', 'd', 'i', 'f', 'f', '.', 'h', 't', 'm', 'l'}) //OZF
	};
	
	
	public static final void main(java.lang.String[] args) {
		//NOTE: WILL HANDLEr ARGUMENTS IN A FUTURE VERSION...
		convertHtmlReportsToPsv(MICH_EBS_DIFFS_V0_FILES, DEFAULT_HORIZONS_INPUT_DIR, DEFAULT_HORIZONS_OUTPUT_DIR, DEFAULT_OUTPUT_FILE_NAMES_SUFFIX);
	}
	



}