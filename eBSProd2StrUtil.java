package zz.ebs;

import static core.IOperatingSystemConstants.LINE_TERMINATOR;

final class eBSProd2StrUtil {

	
	public static final void getToYAMLStringChars(final eBSProduct prod, /*final */int indents, final boolean useTabInLieuOfSpace, final string.CharBuffer outputBuff) {
		if (indents < 0) {
			indents = 0;
		}
		outputBuff.AppendSpaces(indents, useTabInLieuOfSpace).append("number: ").append(prod.number).append(LINE_TERMINATOR)
						.AppendSpaces(indents, useTabInLieuOfSpace).append("code: ").append(prod.code).append(LINE_TERMINATOR)
						.AppendSpaces(indents, useTabInLieuOfSpace).append("name: ").append(prod.name).append(LINE_TERMINATOR);
		if (prod.comment != null && !prod.comment.isEmpty()) {
			outputBuff.AppendSpaces(indents).append("comment: ").append(prod.comment).append(LINE_TERMINATOR);
		}
	}


}