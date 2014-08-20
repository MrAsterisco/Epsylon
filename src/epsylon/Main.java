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
import epsylon.interfaces.Type;
import epsylon.interfaces.Value;
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
			if (string.startsWith("-")) {
				if (string.equals(guiOption)) {
					useUI = true;
					MainWindow.main(null);
					break;
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
		
		if (!useUI) {
			if (waitForOutput) {
				printHelp();
				return;
			}
			
			System.out.println("Epsylon version 1.0(20140820)\n");
			
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
				while (scanner.hasNext()) {
					builder.append(scanner.next());
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
	}
	
	private static void printHelp() {
		System.out.println("Epsylon: invalid option. Supported arguments are: -gui, -o <path-to-output-file>, <path-to-input-file>.");
	}
	
	public static Node parse(String input) {
		Parser parser = new Parser(input);
		return parser.parse();
	}
	
	public static Type typecheck(Node tree) {
		Typechecker typechecker = new Typechecker();
		return typechecker.typecheck(tree);
	}
	
	public static Value evaluate(Node tree) {
		Evaluator evaluator = new Evaluator();
		return evaluator.evaluate(tree);
	}
}
