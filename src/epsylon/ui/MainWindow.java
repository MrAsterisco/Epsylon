package epsylon.ui;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import epsylon.Main;
import epsylon.ast.Node;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * Defines the Epsylon Graphical User Interface.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class MainWindow {

	private JFrame frmEpsylon;
	
	private Node parsedTree;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmEpsylon.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEpsylon = new JFrame();
		frmEpsylon.setTitle("Epsylon");
		frmEpsylon.setResizable(false);
		frmEpsylon.setBounds(100, 100, 771, 631);
		frmEpsylon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEpsylon.getContentPane().setLayout(null);
		
		JLabel lblInputString = new JLabel("INPUT:");
		lblInputString.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		lblInputString.setForeground(Color.DARK_GRAY);
		lblInputString.setBounds(6, 7, 96, 16);
		frmEpsylon.getContentPane().add(lblInputString);
		
		JLabel lblOutput = new JLabel("OUTPUT:");
		lblOutput.setForeground(Color.DARK_GRAY);
		lblOutput.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		lblOutput.setBounds(6, 227, 96, 16);
		frmEpsylon.getContentPane().add(lblOutput);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 31, 759, 185);
		frmEpsylon.getContentPane().add(scrollPane);
		
		JTextArea inputArea = new JTextArea();
		inputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		scrollPane.setViewportView(inputArea);
		inputArea.getDocument().addDocumentListener(new DocumentListener() {

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	parsedTree = null;
	        }

	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	parsedTree = null;
	        }

	        @Override
	        public void changedUpdate(DocumentEvent arg0) {
	        	parsedTree = null;
	        }
	    });
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 254, 759, 308);
		frmEpsylon.getContentPane().add(scrollPane_1);
		
		JTextArea outputArea = new JTextArea();
		outputArea.setFont(new Font("Monospaced", Font.BOLD, 13));
		scrollPane_1.setViewportView(outputArea);
		
		JButton btnParse = new JButton("Parse");
		btnParse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					parsedTree = Main.parse(inputArea.getText());
					outputArea.append("Parser: operation completed successfully.\n");
				} catch (RuntimeException e2) {
					e2.printStackTrace();
					outputArea.append("Parse Error: " + e2.getMessage() + "\n");
					parsedTree = null;
				}
			}
		});
		btnParse.setBounds(425, 574, 117, 29);
		frmEpsylon.getContentPane().add(btnParse);
		
		JButton btnTypecheck = new JButton("Typecheck");
		btnTypecheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (parsedTree == null) {
					btnParse.doClick();
				}
				
				try {
					if (parsedTree != null) {
						outputArea.append("Typecheck: operation completed successfully. Result type is " + Main.typecheck(parsedTree).toString() + ".\n");
					}
				} catch (RuntimeException e2) {
					outputArea.append("Typecheck Error: " + e2.getMessage() + "\n");
				}
			}
		});
		btnTypecheck.setBounds(536, 574, 117, 29);
		frmEpsylon.getContentPane().add(btnTypecheck);
		
		JButton btnEvaluate = new JButton("Evaluate");
		btnEvaluate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (parsedTree == null) {
					btnParse.doClick();
				}
				
				try {
					if (parsedTree != null) {
						outputArea.append("Evaluator: " + Main.evaluate(parsedTree).toString() + ".\n");
					}
				} catch (RuntimeException e2) {
					outputArea.append("Evaluation Error: " + e2.getMessage() + "\n");
				}
			}
		});
		btnEvaluate.setBounds(648, 574, 117, 29);
		frmEpsylon.getContentPane().add(btnEvaluate);
		
		JButton btnLoad = new JButton("Load from File");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(null);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						byte[] encoded = Files.readAllBytes(file.toPath());
						String parsed = new String(encoded, Charset.defaultCharset());
						inputArea.setText(parsed);
					} catch (IOException e1) {
						outputArea.append("Open File error: unable to open the selected file\n");
					}
				}
			}
		});
		btnLoad.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		btnLoad.setBounds(648, 6, 117, 20);
		frmEpsylon.getContentPane().add(btnLoad);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnQuit.setBounds(6, 574, 117, 29);
		frmEpsylon.getContentPane().add(btnQuit);
	}
}
