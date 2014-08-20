package epsylon.exception;

import epsylon.ast.AbsOpExp;
import epsylon.interfaces.Value;

public class EvaluationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EvaluationException(String message) {
		super(message);
	}

	public EvaluationException(Throwable cause) {
		super(cause);
	}

	public EvaluationException(String message, Throwable cause) {
		super(message, cause);
	}

	public EvaluationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public EvaluationException(AbsOpExp exp, Value... val) {
		super(exp.toString() + buildStringFromArray(val));
	}
	
	private static String buildStringFromArray(Value... val) {
		StringBuilder builder = new StringBuilder(" operator undefined");
		
		if (val.length > 1) {
			builder.append(" between " + val[0]);
			int i = 0;
			for (i++; i < val.length-1; i++) {
				builder.append(", " + val[i]);
			}
			
			builder.append(" and " + val[i]);
		}
		else {
			builder.append(" on " + val[0]);
		}
		
		return builder.toString();
	}

}
