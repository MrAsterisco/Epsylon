package epsylon.parser;

import epsylon.ast.*;
import epsylon.interfaces.Exp;
import epsylon.exception.*;

/**
 * The Parser class transforms a string in an Abstract Syntax Tree.
 * 
 * Specifically, this class uses a Tokenizer object to parse a given string following the language syntax rules.
 * For each valid token, the corresponding AST is generated and added to its parent node.
 * 
 * The string, taken as parameter during class initialization, must conform to the rules of the language.
 * Passing an invalid string will throw an exception: the type may vary, depending on which object (the Tokenizer or the Parser itself) has thrown the exception.
 * 
 * The only public function is parse() that starts the parsing process. When instantianting this class, the parsing string is required and a dedicated Tokenizer will be automatically created on that string.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class Parser {
	
	/**
	 * The Tokenizer that will be used to split the input string.
	 */
	protected Tokenizer tokenizer;
	
	/**
	 * Instantiates a new Parser with a string.
	 * 
	 * @param inputString The string to be parsed.
	 */
	public Parser(String inputString) {
		this.tokenizer = new Tokenizer(inputString);
	}
	
	/**
	 * Starts the parsing process.
	 * 
	 * @return A valid Abstract Syntax Tree.
	 */
	public Node parse() {
		tokenizer.next();
		Node tree = new Node(parseForallExp());
		
		if (tokenizer.hasNext()) throw new ParseException("Unexpected token: expecting end of file, found " + tokenizer.tokenString());
		
		return tree;
	}
	
	// Parses a Forall/Exists expression.
	private Exp parseForallExp() {
		Boolean isForall = false;
		
		switch (tokenizer.tokenType()) {
		case FORALL:
			isForall = true;
			
		case EXISTS:
			if (tokenizer.hasNext()) {
				tokenizer.next();
				checkType(TokenType.IDENT);
				Ident ident = new Ident(tokenizer.tokenString());
				tokenizer.next();
				checkType(TokenType.IN);
				tokenizer.next();
				Exp exp1 = parseForallExp();
				checkType(TokenType.COLON);
				tokenizer.next();
				Exp exp2 = parseForallExp();
				
				return (isForall)?new ForallExp(ident, exp1, exp2):new ExistsExp(ident, exp1, exp2);
			}
			return null;

		default:
			return parseImpliesExp();
		}	
	}
	
	// Parses an Implies expression.
	private Exp parseImpliesExp() {
		Exp exp1 = parseOr();
		
		if (tokenizer.tokenType() == TokenType.IMPLIES) {
			tokenizer.next();
			Exp exp2 = parseOr();
			return new ImpliesExp(exp1, exp2);
		}
		
		return exp1;
	}
	
	// Parses an Or expression.
	private Exp parseOr() {
		Exp exp1 = parseAnd();
		
		while (tokenizer.tokenType() == TokenType.OR) {
			tokenizer.next();
			Exp exp2 = parseAnd();
			exp1 = new OrExp(exp1, exp2);
		}
		
		return exp1;
	}
	
	// Parses an And expression.
	private Exp parseAnd() {
		Exp exp1 = parseContains();
		
		while (tokenizer.tokenType() == TokenType.AND) {
			tokenizer.next();
			Exp exp2 = parseContains();
			exp1 = new AndExp(exp1, exp2);
		}
		
		return exp1;
	}
	
	// Parses a Contains expression.
	private Exp parseContains() {
		Exp exp1 = parseEqualDisequal();
		
		if (tokenizer.tokenType() == TokenType.CONTAINS) {
			tokenizer.next();
			Exp exp2 = parseEqualDisequal();
			return new ContainsExp(exp1, exp2);
		}
		
		return exp1;
	}
	
	// Parses an equality expression
	private Exp parseEqualDisequal() {
		Exp exp1 = parseCompare();
		
		Boolean isEqual = false;
		switch (tokenizer.tokenType()) {
		case EQUAL:
			isEqual = true;
			
		case NOT_EQUAL:
			tokenizer.next();
			Exp exp2 = parseCompare();
			
			return (isEqual)?new EqExp(exp1, exp2):new NeqExp(exp1, exp2);

		default:
			return exp1;
		}
	}
	
	// Parses a mathematical comparison expression
	private Exp parseCompare() {
		Exp exp1 = parseOper();
		
		Exp exp2 = null;
		
		switch (tokenizer.tokenType()) {
		case LESS: // <
			tokenizer.next();
			exp2 = parseOper();
			
			return new LthExp(exp1, exp2);
			
		case LESS_EQUAL: // <=
			tokenizer.next();
			exp2 = parseOper();
			
			return new LeqExp(exp1, exp2);
			
		case GREATER: // >
			tokenizer.next();
			exp2 = parseOper();
			
			return new GthExp(exp1, exp2);
			
		case GREATER_EQUAL: // >=
			tokenizer.next();
			exp2 = parseOper();
			
			return new GthExp(exp1, exp2);

		default: // Invalid case
			break;
		}
		
		return exp1;
	}
	
	// Parses a standard mathematical expression or an operation between sets.
	private Exp parseOper() {
		Exp exp1 = parseMoreOper();
		
		Exp exp2 = null;
		
		while (tokenizer.tokenType() == TokenType.ADD || tokenizer.tokenType() == TokenType.STRICT_OR || tokenizer.tokenType() == TokenType.BACKSLASH || tokenizer.tokenType() == TokenType.SUB) {
			switch (tokenizer.tokenType()) {
			case ADD: // +
				tokenizer.next();
				exp2 = parseMoreOper();
				
				exp1 = new AddExp(exp1, exp2);
				break;
				
			case STRICT_OR: // |
				tokenizer.next();
				exp2 = parseMoreOper();
				
				exp1 = new StrictOrExp(exp1, exp2);
				break;
				
			case BACKSLASH: // \
				tokenizer.next();
				exp2 = parseMoreOper();
				
				exp1 = new BackslashExp(exp1, exp2);
				break;
				
			case SUB: // -
				tokenizer.next();
				exp2 = parseMoreOper();
				
				exp1 = new SubExp(exp1, exp2);
				break;

			default:
				break;
			}
		}
		
		return exp1;
	}
	
	// Parses a standard mathematical expression or an operation between sets.
	private Exp parseMoreOper() {
		Exp exp1 = parseUnaOper();
		
		Exp exp2 = null;
		
		while (tokenizer.tokenType() == TokenType.MUL || tokenizer.tokenType() == TokenType.DIV || tokenizer.tokenType() == TokenType.PERC || tokenizer.tokenType() == TokenType.STRICT_AND) {
			switch (tokenizer.tokenType()) {
			case MUL: // *
				tokenizer.next();
				exp2 = parseUnaOper();
				
				exp1 = new MulExp(exp1, exp2);
				break;
				
			case DIV: // /
				tokenizer.next();
				exp2 = parseUnaOper();
				
				exp1 = new DivExp(exp1, exp2);
				break;
				
			case PERC: // %
				tokenizer.next();
				exp2 = parseUnaOper();
				
				exp1 = new ModExp(exp1, exp2);
				break;
				
			case STRICT_AND: // &
				tokenizer.next();
				exp2 = parseUnaOper();
				
				exp1 = new StrictAndExp(exp1, exp2);
				break;

			default:
				break;
			}
		}
		
		return exp1;
	}
	
	// Parses an unary operation.
	private Exp parseUnaOper() {
		switch (tokenizer.tokenType()) {
		case NOT: // !
			tokenizer.next();
			return new NotExp(parseBase());
			
		case HASH: // #
			tokenizer.next();
			return new HashExp(parseBase());
			
		case SUB: // -
			tokenizer.next();
			return new NegExp(parseBase());

		default:
			return parseBase();
		}
	}
	
	// Parses a basic expression.
	private Exp parseBase() {
		Exp exp = null;
		
		switch (tokenizer.tokenType()) {
		case BOOL_LIT: // Boolean
			exp = new BoolLit(tokenizer.boolValue());
			tokenizer.next();
			return exp;
			
		case NUM_LIT: // Numeric
			exp = new NumLit(tokenizer.intValue());
			tokenizer.next();
			return exp;
			
		case IDENT: // Identifier
			exp = new Ident(tokenizer.tokenString());
			tokenizer.next();
			return exp;
			
		case BRA_OPEN: // (
			if (tokenizer.hasNext()) {
				tokenizer.next();
				exp = parseForallExp();
				checkType(TokenType.BRA_CLOSE);
				tokenizer.next();
				return exp;
			}
			
		case SQUARE_BRA_OPEN: // [
			if (tokenizer.hasNext()) {
				tokenizer.next();
				exp = parseInterval();
				checkType(TokenType.SQUARE_BRA_CLOSE);
				tokenizer.next();
				return exp;
			}
			
		case CURLY_BRA_OPEN: // {
			if (tokenizer.hasNext()) {
				tokenizer.next();
				exp = parseSet();
				checkType(TokenType.CURLY_BRA_CLOSE);
				tokenizer.next();
				return exp;
			}

		default: // A known token has been found in an invalid location.
			throw new IllegalTokenException(tokenizer.tokenString());
		}
	}
	
	// Parses an interval [n..n]
	private IntervalExp parseInterval() {
		Exp exp1 = parseForallExp();
		checkType(TokenType.DOUBLE_DOT);
		tokenizer.next();
		Exp exp2 = parseForallExp();
		return new IntervalExp(exp1, exp2);
	}
	
	// Parses a Set {n,...,n} or a Set operation (All or Forall)
	private Exp parseSet() {
		if (tokenizer.tokenType() == TokenType.ALL) {
			tokenizer.next();
			return parseAll();
		}
		else {
			Exp exp1 = parseForallExp();
			
			switch (tokenizer.tokenType()) {
			case COMMA: // ,
				if (tokenizer.hasNext()) {
					tokenizer.next();
					SetExp set = new SetExp();
					set.add(exp1);
					set.add(parseForallExp());
					while (tokenizer.tokenType() == TokenType.COMMA) {
						tokenizer.next();
						set.add(parseForallExp());
					}
					return set;
				}
			
			case FORALL:
				if (tokenizer.hasNext()) {
					tokenizer.next();
					checkType(TokenType.IDENT);
					Ident ident = new Ident(tokenizer.tokenString());
					tokenizer.next();
					checkType(TokenType.IN);
					tokenizer.next();
					return new ForallSetExp(exp1, ident, parseForallExp());
				}
				
			case CURLY_BRA_CLOSE: // }
				return new SetExp(exp1);

			default:
				throw new IllegalTokenException(tokenizer.tokenString());
			}
		}
	}
	
	// Parses an All expression
	private AllSetExp parseAll() {
		Exp ident = parseForallExp();
		
		if (ident instanceof Ident) {
			checkType(TokenType.IN);
			tokenizer.next();
			Exp exp1 = parseForallExp();
			checkType(TokenType.COLON);
			tokenizer.next();
			Exp exp2 = parseForallExp();
			return new AllSetExp((Ident)ident, exp1, exp2);
		}
		
		throw new IllegalTokenException(tokenizer.tokenString());
	}
	
	// Checks whether the current token type is identical to the given type. Throws a ParseException if this check fails.
	private void checkType(TokenType t) {
		if (tokenizer.tokenType() != t) throw new ParseException("Unexpected token! Expecting " + t + ", found " + tokenizer.tokenType() + ".");
	}
}
