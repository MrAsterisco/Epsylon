package epsylon;

import epsylon.typecheck.Typechecker;
import epsylon.ui.MainWindow;
import epsylon.interfaces.Type;
import epsylon.parser.Parser;
import epsylon.ast.Node;

public class Main {

	private static final String guiOption = "-gui";
	private static final String outputFileOption = "-o";
	
	public static Boolean useUI = false;
	
	public static void main(String[] args) {
		Boolean waitForOutput = false;
		
		String outputPath = null;
		String inputPath = null;
		
		for (String string : args) {
			if (string.equals(guiOption)) {
				useUI = true;
				MainWindow.main(null);
				break;
			}
			else if (string.equals(outputFileOption)) {
				waitForOutput = true;
			}
			else if (!string.startsWith("-")) {
				if (waitForOutput) {
					outputPath = string;
					waitForOutput = false;
				}
				else {
					inputPath = string;
				}
			}
		}
		
		if (!useUI) {
			if (inputPath != null) { // Leggo da file
				
			}
			else { // Leggo da Standard Input
				
			}
		}
	}
	
	public static Node parse(String input) {
		Parser parser = new Parser(input);
		return parser.parse();
	}
	
	public static Type typecheck(Node tree) {
		Typechecker typechecker = new Typechecker();
		return typechecker.typecheck(tree);
	}
	
	public static String evaulate(String input) {
		return null;
	}
	
	private static void presentError(String message) {
		if (!useUI) {
			System.out.println("ERROR: " + message);
		}
		else {
			
		}
	}
}
