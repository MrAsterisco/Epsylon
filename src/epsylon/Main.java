package epsylon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import epsylon.typecheck.Typechecker;
import epsylon.ui.MainWindow;
import epsylon.evaluate.Evaluator;
import epsylon.exception.EvaluationException;
import epsylon.exception.ParseException;
import epsylon.exception.TypecheckException;
import epsylon.interfaces.Type;
import epsylon.interfaces.Value;
import epsylon.parser.Parser;
import epsylon.ast.Node;

/**
 * The Main class of Epsylon.
 * 
 * @author Alessio Moiso
 * @version 1.0
 *
 */
public class Main {
	
	/**
	 * Represents the -gui option in code.
	 */
	private static final String guiOption = "-gui";
	/**
	 * Represent the -o option in code.
	 */
	private static final String outputFileOption = "-o";
	
	/**
	 * Configures the application to behave in the way
	 * specified through the arguments.
	 * 
	 * If the guiOption is found, any other argument is
	 * skipped and a new instance of MainWindow is displayed.
	 * 
	 * Otherwise, if the outputFileOption is contained in args,
	 * the application will wait for a path (any validation check
	 * will be performed later, so we accept any kind of string
	 * not starting with a "-").
	 * 
	 * At last, if other arguments are found, having said that none
	 * of them is allowed to start with a "-", they're treated as a
	 * list of paths from which the application should read. Nevertheless,
	 * considering that Epsylon currently support only one input file,
	 * only the last typed path is considered.
	 * 
	 * @param args The list of arguments passed to the application.
	 */
	public static void main(String[] args) {
		Boolean waitForOutput = false;
		
		String outputPath = null;
		String inputPath = null;
		
		for (String string : args) {
			if (string.startsWith("-")) {
				if (string.equals(guiOption)) {
					MainWindow.main(null);
					return;
				}
				else if (string.equals(outputFileOption)) {
					waitForOutput = true;
				}
				else {
					printHelp();
					return;
				}
			}
			else {
				if (waitForOutput) {
					outputPath = string;
					waitForOutput = false;
				}
				else {
					inputPath = string;
				}
			}
		}
		
		if (waitForOutput) {
			printHelp();
			return;
		}
		
		System.out.println("Epsylon version 1.0(20140904)\n");
		
		String result = null;
		String input = null;
		
		if (inputPath != null) {
			try {
				byte[] encoded = Files.readAllBytes(Paths.get(inputPath));
				input = new String(encoded, Charset.defaultCharset());
			} catch (IOException e) {
				System.err.println("ERROR: Unable to read from " + inputPath + "!");
			}
		}
		else {
			System.out.println("Please type an input below:\n");
			Scanner scanner = new Scanner(System.in);
			StringBuilder builder = new StringBuilder();
			while (scanner.hasNextLine()) {
				builder.append(scanner.nextLine());
			}
			scanner.close();
			input = builder.toString();
		}
		
		try {
			Node parsedTree = parse(input);
			typecheck(parsedTree);
			result = evaluate(parsedTree).toString();
		} catch (RuntimeException e) {
			System.err.println("\nERROR: " + e.getMessage());
		}
		
		if (outputPath != null) {
			try {
				PrintWriter writer = new PrintWriter(outputPath, "UTF-8");
				writer.print(result);
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				System.err.println("ERROR: Unable to write to " + outputPath + "!");
			}
		}
		else {
			System.out.println("Result: " + result);
		}
	}
	
	/**
	 * Prints a small and easy to read help.
	 * 
	 * It is invoked automatically when an invalid
	 * arguments is found.
	 */
	private static void printHelp() {
		System.out.println("Epsylon: invalid option. Supported arguments are: -gui, -o <path-to-output-file>, <path-to-input-file>.");
	}
	
	/**
	 * Instantiates a new Parser and calls its parse method
	 * on a specified input string.
	 * 
	 * @param input The input string on which the Parser should work.
	 * @return A valid Node, if the parsing is successful.
	 * @throws ParseException If the input cannot be parsed.
	 */
	public static Node parse(String input) throws ParseException {
		Parser parser = new Parser(input);
		return parser.parse();
	}
	
	/**
	 * Instantiates a new Typechecker and calls its typecheck method
	 * on a specified tree.
	 * 
	 * @param tree A tree built by an instance of Parser.
	 * @return The resulting Type of the expression, if the typechecking is successful.
	 * @throws TypecheckException If the tree contains a typecheck error.
	 */
	public static Type typecheck(Node tree) throws TypecheckException {
		Typechecker typechecker = new Typechecker();
		return typechecker.typecheck(tree);
	}
	
	/**
	 * Instantiates a new Evaluator and calls its evaluate method
	 * on a specified tree.
	 * 
	 * The evaluation can work even though the tree has not been typechecked.
	 * 
	 * @param tree A tree built by an instance of Parser.
	 * @return The resulting Value of the expression, if the evaluation is successful.
	 * @throws EvaluationException If the tree cannot be evaluated.
	 */
	public static Value evaluate(Node tree) throws EvaluationException {
		Evaluator evaluator = new Evaluator();
		return evaluator.evaluate(tree);
	}
}
