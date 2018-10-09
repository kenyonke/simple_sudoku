import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class sudoku2 extends JFrame implements ActionListener {
	Thread thread;
	private JTextArea status;
	private String[][] data = new String[9][9];
	private BPanel buttonPanel;
	private NumPanel display;

	public sudoku2() {
		// common setting
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		this.setBounds((int) width / 4, (int) height / 4, (int) width / 2, (int) height / 2);
		this.setTitle("Sodoku");

		display = new NumPanel();
		status = new JTextArea((int) width / 275, (int) height / 3);
		status.setFont(new java.awt.Font("Dialog", 1, 15));
		status.setBackground(Color.lightGray);
		JScrollPane roll = new JScrollPane(status); 								// add roll on textArea status
		roll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	// do not show horizontal scroll bar
		roll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);		// do not show vertical scroll bar
		status.setCaretPosition(status.getText().length()); 						// update roll automatically

		// add all panels into container
		Container c = this.getContentPane();
		buttonPanel = new BPanel();
		buttonPanel.interrupt.addActionListener(this);
		buttonPanel.load.addActionListener(this);
		buttonPanel.quit.addActionListener(this);
		buttonPanel.run.addActionListener(this);
		buttonPanel.clear.addActionListener(this);
		c.add(buttonPanel, BorderLayout.NORTH);
		c.add(roll, BorderLayout.SOUTH);
		c.add(display, BorderLayout.CENTER);

	}

	public static void main(String args[]) {
		JFrame frm = new sudoku2();
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		unlockCell();
		// load button action
		if (e.getSource().equals(buttonPanel.load)) {
			try {
				// get File
				File file = getTxtFile();
				String filePath = file.getAbsolutePath();

				// get the array data from the file and display it into gui
				data = getData(filePath);
				this.status.append("loading" + " " + file.getName() + "\n");

				// check whether the data is 9*9
				if (!checkData(data)) {
					this.status
							.append("Error: The selected file is not 9*9 enough, please select another file." + "\n");
					data = resetData(data);
				}

				// send data on numPanel
				this.display.setDate(data);

				// check whether any identical candidates exist
				if (checkRed(data, display)) {
					this.status.append("Error: The selected file can not be solved." + "\n");
					data = resetData(data);
				}

			} catch (NullPointerException ec) {
				this.status.append("No file is found!" + "\n");
			}
		}

		// clear button action
		if (e.getSource().equals(buttonPanel.clear)) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					this.data[i][j] = "";
				}
			}
			this.display.setDate(data);
			this.status.append("clear..." + "\n");
		}

		// quit button action
		if (e.getSource().equals(buttonPanel.quit)) {
			System.exit(0);
		}

		// run button action
		if (e.getSource().equals(buttonPanel.run)) {
			//get data on numPanal
			data = getTextData();
			
			//check whether any identical candidates exist
			if (checkRed(data, display)) {
				this.status.append("Error: This Sudoku can not be solved." + "\n");
				data = resetData(data);
			}
			
			// if no file is loaded, show tips
			if (!checkData(data)) {
				this.status.append("Please load another file or type again!" + "\n");
			} else {
				this.status.append("working..." + "\n");
				//add thread
				Operate ope = new Operate(data, display, status);
				thread = new Thread(ope);
				//start operating
				thread.start();
			}
		}

		// interrupt button action
		if (e.getSource().equals(buttonPanel.interrupt)) {
			unlockCell();
			// if no file is loaded, show tips
			if (!checkData(data)) {
				this.status.append("Please load a file!" + "\n");
			} else {
				this.status.append("Interrupt! " + "\n");
				thread.interrupt();
			}
			status.setCaretPosition(status.getText().length());
		}

	}

	// return File user choose
	private File getTxtFile() {
		// get the file path of the chose "txt" file
		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		// use "txt" filter so the user can only use "txt" file.
		FileNameExtensionFilter filter = new FileNameExtensionFilter("txt file", "txt");
		chooser.addChoosableFileFilter(filter);
		chooser.showOpenDialog(this);
		File file = chooser.getSelectedFile();
		return file;
	}

	public String[][] getData(String filePath) {
		String line;
		String[][] data = new String[9][9];
		Scanner file = null;
		try {
			file = new Scanner(new File(filePath));
			// load data into data[9][9]
			for (int i = 0; i < 9; i++) {
				// if the file has next line
				if (file.hasNext()) {
					line = file.next().replaceAll("[^0-9]", "=");// change chars which are not number into "=";
					// if the length of a line is less than 9, load all of them.
					if (line.length() < 9) {
						for (int j = 0; j < line.length(); j++) {
							if (String.valueOf(line.charAt(j)).equals("=")) {
								data[i][j] = "";
							} else {
								data[i][j] = String.valueOf(line.charAt(j));
							}

						}
					}
					// is the length is more than 9, only load 9 of them.
					else {
						for (int j = 0; j < 9; j++) {
							if (String.valueOf(line.charAt(j)).equals("=")) {
								data[i][j] = "";
							} else {
								data[i][j] = String.valueOf(line.charAt(j));
							}
						}
					}
				}
				// if file doesn't have next line, get out of the loop
				else {
					break;
				}
			}
		}
		// Exception
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StringIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			file.close();
		}
		return data;
	}

	// reset a array
	public String[][] resetData(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = null;
			}
		}
		return data;
	}

	// return false if a null was found in array
	public boolean checkData(String[][] data) {
		// check every data in array[9][9]
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// if any data is found, return false instantly
				if (data[i][j] == null) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkRed(String data[][], NumPanel display) {
		boolean red = false;

		Pattern pattern = Pattern.compile("[0-9]*");
		// check rows
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// only check 1 long candidate
				if ((data[i][j].length() == 1) && pattern.matcher(data[i][j]).matches()) {
					// loop this row
					for (int jj = 0; jj < 9; jj++) {
						// excludes itself
						if (jj != j) {
							if (data[i][jj].length() == 1) {
								// if identical candidates exist
								if (data[i][j].equals(data[i][jj])) {
									// set red backgrounds
									display.textField[i][j].setBackground(Color.red);
									display.textField[i][jj].setBackground(Color.red);
									red = true;
								}
							}
						}
					}
				}
			}
		}

		// check columns
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 9; i++) {
				// only check 1 long candidate
				if (data[i][j].length() == 1) {
					// loop this column
					for (int ii = 0; ii < 9; ii++) {
						// excludes itself
						if (ii != i) {
							if (data[ii][j].length() == 1 && pattern.matcher(data[i][j]).matches()) {
								// if identical candidates exist
								if (data[i][j].equals(data[ii][j])) {
									display.textField[i][j].setBackground(Color.red);
									display.textField[ii][j].setBackground(Color.red);
									red = true;
								}
							}
						}
					}
				}
			}
		}

		// check boxes
		for (int boxRow = 0; boxRow < 3; boxRow++) {
			for (int boxCol = 0; boxCol < 3; boxCol++) {

				// one box
				for (int i = boxRow * 3; i < (boxRow + 1) * 3; i++) {
					for (int j = boxCol * 3; j < (boxCol + 1) * 3; j++) {
						// only check 1 long candidate
						if (data[i][j].length() == 1 && pattern.matcher(data[i][j]).matches()) {
							// loop this box
							for (int ii = boxRow * 3; ii < (boxRow + 1) * 3; ii++) {
								for (int jj = boxCol * 3; jj < (boxCol + 1) * 3; jj++) {
									// excludes itself
									if ((ii == i && jj == j) == false) {
										// if identical candidates exist
										if (data[i][j].equals(data[ii][jj])) {
											// set red backgrounds
											display.textField[i][j].setBackground(Color.red);
											display.textField[ii][jj].setBackground(Color.red);
											red = true;
										}
									}

								}
							}
						}
					}
				}
			}
		}

		// return true if identical candidates appear, otherwise return false
		return red;
	}

	// unlock cells so the user can type
	private void unlockCell() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.display.textField[i][j].setEditable(true);
			}
		}
	}

	// get data from textFileds on NumPanel
	private String[][] getTextData() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				data[i][j] = display.textField[i][j].getText();
			}
		}
		return data;
	}
}
