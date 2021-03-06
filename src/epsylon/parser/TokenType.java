package epsylon.parser;

/**
 * Defines all the supported token types.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public enum TokenType {
	NUM_LIT, 
	IDENT, 
	BOOL_LIT,
	NOT,
	SUB,
	HASH,
	BRA_OPEN,
	BRA_CLOSE,
	CURLY_BRA_OPEN,
	CURLY_BRA_CLOSE,
	COMMA,
	ALL,
	IN,
	COLON,
	FORALL,
	EXISTS,
	SQUARE_BRA_OPEN,
	SQUARE_BRA_CLOSE,
	DOUBLE_DOT,
	AND,
	OR,
	IMPLIES,
	CONTAINS,
	ADD,
	MUL,
	DIV,
	PERC,
	LESS_EQUAL,
	LESS,
	GREATER_EQUAL,
	GREATER,
	EQUAL,
	NOT_EQUAL,
	STRICT_AND,
	STRICT_OR,
	BACKSLASH,
	EOF
}
