package epsylon.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static epsylon.parser.TokenType.*;

public class Tokenizer implements Iterator<TokenType> {
	// symbol table mapping keywords and symbols to their token types
	private static Map<String, TokenType> map = new HashMap<>();
	static {
		map.put("not", NOT);
		map.put("-", SUB);
		map.put("#", HASH);
		map.put("(", BRA_OPEN);
		map.put(")", BRA_CLOSE);
		map.put("{", CURLY_BRA_OPEN);
		map.put("}", CURLY_BRA_CLOSE);
		map.put(",", COMMA);
		map.put("all", ALL);
		map.put("in", IN);
		map.put(":", COLON);
		map.put("forall", FORALL);
		map.put("exists", EXISTS);
		map.put("[", SQUARE_BRA_OPEN);
		map.put("]", SQUARE_BRA_CLOSE);
		map.put("..", DOUBLE_DOT);
		map.put("and", AND);
		map.put("or", OR);
		map.put("implies", IMPLIES);
		map.put("contains", CONTAINS);
		map.put("+", ADD);
		map.put("*", MUL);
		map.put("/", DIV);
		map.put("%", PERC);
		map.put("<=", LESS_EQUAL);
		map.put("<", LESS);
		map.put(">=", GREATER_EQUAL);
		map.put(">", GREATER);
		map.put("==", EQUAL);
		map.put("!=", NOT_EQUAL);
		map.put("&", STRICT_AND);
		map.put("|", STRICT_OR);
		map.put("\\", BACKSLASH);
	}
	
	public Tokenizer(String inputString) {
		this.scanner = new Scanner(inputString);
	}
	
	// reads the standard input and skip white spaces
	private Scanner scanner;
	// tries to match a sequence of tokens
	final private Matcher matcher = Pattern.compile("not|-|#|\\(|\\)|\\{|\\}|,|all|in|:|forall|exists|\\[|\\]|\\.\\.|and|or|implies|contains|\\+|\\*|/|\\\\|%|<=|<|>=|>|==|!=|&|\\||(?<IDENT>[a-zA-Z][a-zA-Z0-9]*)|(?<BOOL>(true|false))|(?<NUM>[0-9]+)").matcher("");
	// the (inclusive) index of the first character of the portion of region
	// that still has to be matched
	private int start;
	// the type of the token, if it has been matched
	private TokenType tokenType;
	// the current region of the matcher
	private String region;
	// the integer value of the token, if the token type is NUM
	private int intValue;
	// the Boolean value of the token, if the token type is BOOL
	private boolean boolValue;

	// returns the type of the matched token
	private TokenType type(String st) {
		if (matcher.group("NUM") != null) {
			intValue = Integer.parseInt(st);
			return tokenType = NUM_LIT;
		}
		if (matcher.group("IDENT") != null && matcher.group("IDENT").length() > 0)
			return tokenType = IDENT;
		// the token must necessarily be in the symbol table
		tokenType = map.get(st);
		assert tokenType != null;
		if (tokenType == BOOL_LIT)
			boolValue = Boolean.parseBoolean(st);
		return tokenType;
	}

	@Override
	public boolean hasNext() {
		return tokenType != EOF;
	}

	@Override
	public TokenType next() {
		if (start == matcher.regionEnd()) { // a new region has to be read
			if (!scanner.hasNext()) { // no more tokens
				if (tokenType == EOF) { // tries to read past EOF
					tokenType = null;
					throw new NoSuchElementException();
				}
				return tokenType = EOF;
			}
			// changes the region
			region = scanner.next();
			start = 0;
			matcher.reset(region);
		}
		// updates the start index of the region in the matcher
		matcher.region(start, matcher.regionEnd());
		if (matcher.lookingAt()) { // a token matches from the start index of
									// the region
			start = matcher.end(); // advances the start index by skipping the
									// matched token
			return tokenType = type(matcher.group());
		} else { // an unrecognized token has been read
			// advances the start index by skipping the unrecognized token
			start = matcher.find() ? matcher.start() : matcher.regionEnd();
			String unknownToken = region // recovers the unrecognized token
					.substring(matcher.regionStart(), start);
			tokenType = null;
			throw new UnknownTokenException(unknownToken);
		}
	}

	public TokenType tokenType() {
		if (tokenType == null)
			throw new IllegalStateException("NULL token type found.");
		return tokenType;
	}

	public String tokenString() {
		if (tokenType == EOF)
			return "end of file";
		else
			return matcher.group();
	}

	public int intValue() {
		if (tokenType != NUM_LIT)
			throw new IllegalStateException("intValue called on a non-integer expression.");
		return intValue;
	}

	public boolean boolValue() {
		if (tokenType != BOOL_LIT)
			throw new IllegalStateException("boolValue called on a non-bool expression.");
		return boolValue;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
