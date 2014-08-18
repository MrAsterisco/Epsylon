package epsylon.parser;

import java.util.LinkedHashSet;
import java.util.Set;

import epsylon.ast.*;
import epsylon.interfaces.Exp;
import epsylon.exception.*;

public class Parser {
	
	private Tokenizer tokenizer;
	
	public Parser(String inputString) {
		this.tokenizer = new Tokenizer(inputString);
	}
	
	public Node parse() {
		tokenizer.next();
		Node tree = new Node(parseForallExp());
		
		if (tokenizer.hasNext()) throw new ParseException("Unexpected token: expecting end of file, found " + tokenizer.tokenString());
		
		return tree;
	}
	
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
	
	private Exp parseImpliesExp() {
		Exp exp1 = parseOr();
		
		if (tokenizer.tokenType() == TokenType.IMPLIES) {
			tokenizer.next();
			Exp exp2 = parseOr();
			return new ImpliesExp(exp1, exp2);
		}
		
		return exp1;
	}
	
	private Exp parseOr() {
		Exp exp1 = parseAnd();
		
		if (tokenizer.tokenType() == TokenType.OR) {
			tokenizer.next();
			Exp exp2 = parseOr();
			return new OrExp(exp1, exp2);
		}
		
		return exp1;
	}
	
	private Exp parseAnd() {
		Exp exp1 = parseContains();
		
		if (tokenizer.tokenType() == TokenType.AND) {
			tokenizer.next();
			Exp exp2 = parseAnd();
			return new AndExp(exp1, exp2);
		}
		
		return exp1;
	}
	
	private Exp parseContains() {
		Exp exp1 = parseEqualDisequal();
		
		if (tokenizer.tokenType() == TokenType.CONTAINS) {
			tokenizer.next();
			Exp exp2 = parseEqualDisequal();
			return new ContainsExp(exp1, exp2);
		}
		
		return exp1;
	}
	
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
	
	private Exp parseCompare() {
		Exp exp1 = parseOper();
		
		Exp exp2 = null;
		
		switch (tokenizer.tokenType()) {
		case LESS:
			tokenizer.next();
			exp2 = parseOper();
			
			return new LthExp(exp1, exp2);
			
		case LESS_EQUAL:
			tokenizer.next();
			exp2 = parseOper();
			
			return new LeqExp(exp1, exp2);
			
		case GREATER:
			tokenizer.next();
			exp2 = parseOper();
			
			return new GthExp(exp1, exp2);
			
		case GREATER_EQUAL:
			tokenizer.next();
			exp2 = parseOper();
			
			return new GthExp(exp1, exp2);

		default:
			break;
		}
		
		return exp1;
	}
	
	private Exp parseOper() {
		Exp exp1 = parseMoreOper();
		
		Exp exp2 = null;
		
		switch (tokenizer.tokenType()) {
		case ADD:
			tokenizer.next();
			exp2 = parseOper();
			
			return new AddExp(exp1, exp2);
			
		case STRICT_OR:
			tokenizer.next();
			exp2 = parseOper();
			
			return new StrictOrExp(exp1, exp2);
			
		case BACKSLASH:
			tokenizer.next();
			exp2 = parseOper();
			
			return new BackslashExp(exp1, exp2);
			
		case SUB:
			tokenizer.next();
			exp2 = parseOper();
			
			return new SubExp(exp1, exp2);

		default:
			break;
		}
		
		return exp1;
	}
	
	private Exp parseMoreOper() {
		Exp exp1 = parseUnaOper();
		
		Exp exp2 = null;
		
		switch (tokenizer.tokenType()) {
		case MUL:
			tokenizer.next();
			exp2 = parseMoreOper();
			
			return new MulExp(exp1, exp2);
			
		case DIV:
			tokenizer.next();
			exp2 = parseMoreOper();
			
			return new DivExp(exp1, exp2);
			
		case PERC:
			tokenizer.next();
			exp2 = parseMoreOper();
			
			return new ModExp(exp1, exp2);
			
		case STRICT_AND:
			tokenizer.next();
			exp2 = parseMoreOper();
			
			return new StrictAndExp(exp1, exp2);

		default:
			break;
		}
		
		return exp1;
	}
	
	private Exp parseUnaOper() {
		switch (tokenizer.tokenType()) {
		case NOT:
			tokenizer.next();
			return new NotExp(parseBase());
			
		case HASH:
			tokenizer.next();
			return new HashExp(parseBase());
			
		case SUB:
			tokenizer.next();
			return new NegExp(parseBase());

		default:
			return parseBase();
		}
	}
	
	private Exp parseBase() {
		Exp exp = null;
		
		switch (tokenizer.tokenType()) {
		case BOOL_LIT:
			exp = new BoolLit(tokenizer.boolValue());
			tokenizer.next();
			return exp;
			
		case NUM_LIT:
			exp = new NumLit(tokenizer.intValue());
			tokenizer.next();
			return exp;
			
		case IDENT:
			exp = new Ident(tokenizer.tokenString());
			tokenizer.next();
			return exp;
			
		case BRA_OPEN:
			if (tokenizer.hasNext()) {
				tokenizer.next();
				exp = parseForallExp();
				checkType(TokenType.BRA_CLOSE);
				tokenizer.next();
				return exp;
			}
			
		case SQUARE_BRA_OPEN:
			if (tokenizer.hasNext()) {
				tokenizer.next();
				exp = parseInterval();
				checkType(TokenType.SQUARE_BRA_CLOSE);
				tokenizer.next();
				return exp;
			}
			
		case CURLY_BRA_OPEN:
			if (tokenizer.hasNext()) {
				tokenizer.next();
				exp = parseSet();
				System.out.println(tokenizer.tokenType() + " " + exp);
				checkType(TokenType.CURLY_BRA_CLOSE);
				tokenizer.next();
				return exp;
			}

		default:
			System.out.println("Token non valido parseBase");
			throw new IllegalTokenException(tokenizer.tokenString());
		}
	}
	
	private IntervalExp parseInterval() {
		Exp exp1 = parseForallExp();
		checkType(TokenType.DOUBLE_DOT);
		tokenizer.next();
		Exp exp2 = parseForallExp();
		return new IntervalExp(exp1, exp2);
	}
	
	private Exp parseSet() {
		if (tokenizer.tokenType() == TokenType.ALL) {
			tokenizer.next();
			return parseAll();
		}
		else {
			System.out.println("Sono nel parseSet con "+ tokenizer.tokenType());
			Exp exp1 = parseForallExp();
			
			switch (tokenizer.tokenType()) {
			case COMMA:
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
				
			case CURLY_BRA_CLOSE:
				return new SetExp(exp1);

			default:
				throw new IllegalTokenException(tokenizer.tokenString());
			}
		}
	}
	
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
	
	private void checkType(TokenType t) {
		if (tokenizer.tokenType() != t) throw new ParseException("Unexpected token! Expecting " + t + ", found " + tokenizer.tokenType() + ".");
	}
}
