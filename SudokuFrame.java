import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {

 	private static int WINDOW_WIDTH = 800;
 	private static final int WINDOW_HEIGHT = 400;

 	public static JTextArea puzzleArea;
 	public static JTextArea solutionArea;

 	private static JCheckBox checkBox;

 	private void outputSolution(){
 		String result = "";
 		try{
 			String puzzle = puzzleArea.getText();
 			Sudoku sudoku = new Sudoku(puzzle);
 			int solutions = sudoku.solve();
 			if(solutions > 0){
 				result += sudoku.getSolutionText();
			}
 			result += "Solutions: " + solutions + "\n";
 			result += "Elapsed Time: " + sudoku.getElapsed() + "ms";

		} catch(Exception exception){
 			result = "Invalid format";
		}
 		solutionArea.setText(result);
	}

	private void autoOutput(){
 		if(checkBox.isSelected())
 			outputSolution();
	}
	
	public SudokuFrame() {
		super("Sudoku Solver");
		
		// YOUR CODE HERE
		
		// Could do this:
		// setLocationByPlatform(true);

		JPanel mainPanel = new JPanel(new BorderLayout(4,4));

		/* Puzzle Area */
		puzzleArea = new JTextArea(15,20);
		puzzleArea.setBorder(new TitledBorder("Puzzle"));

		puzzleArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				autoOutput();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				autoOutput();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				autoOutput();
			}
		});

		/* Solution Area */
		solutionArea = new JTextArea(15,20);
		solutionArea.setBorder(new TitledBorder("Solution"));
		solutionArea.setEditable(false);


		/* Check Button */
		JButton checkButton = new JButton("Check");

		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				outputSolution();
			}
		});
		/* Autocheck Checkbox */
		checkBox = new JCheckBox("Auto Check");


		/* Bottom Box */
		Box bottomBox = Box.createHorizontalBox();
		bottomBox.add(checkButton);
		bottomBox.add(checkBox);

		mainPanel.add(bottomBox,BorderLayout.SOUTH);
		mainPanel.add(puzzleArea,BorderLayout.CENTER);
		mainPanel.add(solutionArea,BorderLayout.EAST);

		setContentPane(mainPanel);
		setLocationByPlatform(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
	}
	
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

}
