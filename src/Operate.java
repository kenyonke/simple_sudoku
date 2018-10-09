import java.util.Arrays;

import javax.swing.JTextArea;

public class Operate implements Runnable {
	private String[][] data = new String[9][9];
	private NumPanel numPanel;
	private JTextArea status;
	
	public Operate(String[][] data,NumPanel numPanel, JTextArea status) {
		this.data = data;
		this.numPanel = numPanel;
		this.status = status;
	}

	// Update candidate lists if a new single number appears
	public String[][] delete(String[][] data) throws InterruptedException {
		//loop all cells
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (data[i][j].length() == 1) {
					// one box
					for (int boxRow = 3 * (i / 3); boxRow < 3 * ((i / 3) + 1); boxRow++) {
						for (int boxCol = 3 * (j / 3); boxCol < 3 * ((j / 3) + 1); boxCol++) {
							if ((boxRow == i && boxCol == j) == false) {
								data[boxRow][boxCol] = data[boxRow][boxCol].replace(data[i][j], "");
							}
						}
					}

					// one row
					for (int col = 0; col < 9; col++) {
						if (col != j) {
							data[i][col] = data[i][col].replace(data[i][j], "");
						}
					}

					// one column
					for (int row = 0; row < 9; row++) {
						if (row != i) {
							data[row][j] = data[row][j].replace(data[i][j], "");
						}
					}

				}
			}
		}

		return data;
	}

	// Update candidate lists until no new single numbers appear
	public String[][] single(String[][] data) throws InterruptedException {
		String[][] data2 = new String[9][9];
		
		while (true) {
			data = delete(data);
			numPanel.setDate(data);
			Thread.sleep(150);
			if (arrayEquals(data, data2)) {
				break;
			} else {
				data2 = delete(data);
			}
		}
		return data;
	}

	/*When a candidate number appears just once in an area (row, column or box),
	 *that number goes into the square.
	 */
	public String[][] singleSquare(String[][] data) throws InterruptedException {
		boolean boxJudge;
		boolean rowJudge;
		boolean colJudge;
		// one box
		for (int boxRow = 0; boxRow < 3; boxRow++) {
			for (int boxCol = 0; boxCol < 3; boxCol++) {
				for (int i = boxRow * 3; i < (boxRow + 1) * 3; i++) {
					for (int j = boxCol * 3; j < (boxCol + 1) * 3; j++) {
						//the single candidate should have more than 1 number.
						if (data[i][j].length() != 1) {
							boxJudge = true;
							for (int index = 0; index < data[i][j].length(); index++) {
								for (int ii = boxRow * 3; ii < (boxRow + 1) * 3; ii++) {
									for (int jj = boxCol * 3; jj < (boxCol + 1) * 3; jj++) {
										//excludes itself
										if ((ii == i && jj == j) == false) {
											//if other candidates contain this number, it is not suitable for single square rule
											if (data[ii][jj].contains(String.valueOf(data[i][j].charAt(index)))) {
												boxJudge = false;
											}
										}
									}
								}
								//if this number is suitable for single square rule, this candidate must be the number
								if (boxJudge) {
									data[i][j] = String.valueOf(data[i][j].charAt(index));
									numPanel.setDate(data);
									Thread.sleep(300);
								}
							}
						}

					}
				}
			}
		}

		// one row
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				//the single candidate should have more than 1 number.
				if (data[i][j].length() != 1) {
					rowJudge = true;
					for (int index = 0; index < data[i][j].length(); index++) {
						for (int col = 0; col < 9; col++) {
							//excludes itself
							if (col != j) {
								//if other candidates contain this number, it is not suitable for single square rule
								if (data[i][col].contains(String.valueOf(data[i][j].charAt(index)))) {
									rowJudge = false;
								}
							}
						}
						//if this number is suitable for single square rule, this candidate must be the number
						if (rowJudge) {
							data[i][j] = String.valueOf(data[i][j].charAt(index));
							numPanel.setDate(data);
							Thread.sleep(300);
						}
					}
				}
			}
		}

		// one column
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 9; i++) {
				//the single candidate should have more than 1 number.
				if (data[i][j].length() != 1) {
					colJudge = true;
					for (int index = 0; index < data[i][j].length(); index++) {
						for (int row = 0; row < 9; row++) {
							//excludes itself
							if (row != i) {
								//if other candidates contain this number, it is not suitable for single square rule
								if (data[row][j].contains(String.valueOf(data[i][j].charAt(index)))) {
									colJudge = false;
								}
							}
						}
						//if this number is suitable for single square rule, this candidate must be the number
						if (colJudge) {
							data[i][j] = String.valueOf(data[i][j].charAt(index));
							numPanel.setDate(data);
							Thread.sleep(300);
						}
					}
				}
			}
		}

		return data;
	}

	/*When a candidate number only appears in one row or column of a box 
	 *the box 'claims' that number within the entire row or column.
	 */
	public String[][] claiming(String data[][]) throws InterruptedException {
		//choose every boxes
		for (int boxRow = 0; boxRow < 3; boxRow++) {
			for (int boxCol = 0; boxCol < 3; boxCol++) {
				for (int i = boxRow * 3; i < (boxRow + 1) * 3; i++) {
					for (int j = boxCol * 3; j < (boxCol + 1) * 3; j++) {
						
						//the claiming candidate should have more than 1 number.
						if (data[i][j].length() != 1) {
							for (int index = 0; index < data[i][j].length(); index++) {
								boolean rowJudge = true;
								boolean colJudge = true;
								
								//loop this box
								for (int ii = boxRow * 3; ii < (boxRow + 1) * 3; ii++) {
									for (int jj = boxCol * 3; jj < (boxCol + 1) * 3; jj++) {

										// loop its row
										if (ii != i) {
											if (data[ii][jj].contains(String.valueOf(data[i][j].charAt(index)))) {
												rowJudge = false;
											}
										}

										// loop its column
										if (jj != j) {
											if (data[ii][jj].contains(String.valueOf(data[i][j].charAt(index)))) {
												colJudge = false;
											}
										}
									}
								}
								
								/*If this number is suitable for claiming rule in its row,
								delete this number from candidates which is not in its box.*/
								if (rowJudge) {
									for (int rowIndex = 0; rowIndex < 9; rowIndex++) {
										if (rowIndex < ((j / 3) * 3) || rowIndex >= (((j / 3) + 1) * 3)) {
											data[i][rowIndex] = data[i][rowIndex]
													.replace(String.valueOf(data[i][j].charAt(index)), "");
										}
									}
									numPanel.setDate(data);
									Thread.sleep(300);
								}
								
								/*If this number is suitable for claiming rule in its column,
								delete this number from candidates which is not in its box.*/
								if (colJudge) {
									for (int colIndex = 0; colIndex < 9; colIndex++) {
										if (colIndex < ((i / 3) * 3) || colIndex >= (((i / 3) + 1) * 3)) {
											data[colIndex][j] = data[colIndex][j]
													.replace(String.valueOf(data[i][j].charAt(index)), "");
										}
									}
									numPanel.setDate(data);
									Thread.sleep(300);
								}

							}
						}
					}
				}
			}
		}
		
		//update every candidates
		single(data);
		return data;
	}

	/*When two squares in the same area (row, column or box) have identical 
	 *two-number candidate lists, you can remove both numbers from other 
	 *candidate lists in that area.
	 */
	public String[][] pairs(String data[][]) throws InterruptedException {
		//boxes
		for (int boxRow = 0; boxRow < 3; boxRow++) {
			for (int boxCol = 0; boxCol < 3; boxCol++) {

				for (int i = boxRow * 3; i < (boxRow + 1) * 3; i++) {
					for (int j = boxCol * 3; j < (boxCol + 1) * 3; j++) {
						
						//the pair candidate should have 2 numbers
						if (data[i][j].length() == 2) {
							for (int ii = boxRow * 3; ii < (boxRow + 1) * 3; ii++) {
								for (int jj = boxCol * 3; jj < (boxCol + 1) * 3; jj++) {

									//excludes itself
									if ((ii == i && jj == j) == false) {
										//if pair candidates exist
										if (data[i][j].equals(data[ii][jj])) {
											//loop this box
											for (int r = boxRow * 3; r < (boxRow + 1) * 3; r++) {
												for (int c = boxCol * 3; c < (boxCol + 1) * 3; c++) {
													//exclude pair candidates
													if ((r == i && c == j) == false) {
														if ((r == ii && c == jj) == false) {
															// delete this pair number from other candidates on the box
															for (int index = 0; index < data[i][j].length(); index++) {
																data[r][c] = data[r][c].replace(
																		String.valueOf(data[i][j].charAt(index)), "");
															}
															
														}
													}
												}
											}
										}
									}

								}
							}
						}

					}
				}
			}
		}

		//rows
		for (int i = 0; i < 9; i++) {
			for (int col = 0; col < 5; col++) {
				
				//the pair candidate should have 2 numbers
				if (data[i][col].length() == 2) {
					for (int col2 = 0; col2 < 9; col2++) {
						
						//excludes itself
						if (col != col2) {
							//if pair candidates exist
							if (data[i][col].equals(data[i][col2])) {
								//loop this row
								for (int c = 0; c < 9; c++) {
									//exclude pair candidates
									if ((c != col2)) {
										if (c != col) {
											// delete this pair number from other candidates on this row
											for (int index = 0; index < data[i][col].length(); index++) {
												data[i][c] = data[i][c]
														.replace(String.valueOf(data[i][col].charAt(index)), "");
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		//columns
		for (int j = 0; j < 9; j++) {
			for (int row = 0; row < 5; row++) {
				
				//the pair candidate should have 2 numbers
				if (data[row][j].length() == 2) {
					for (int row2 = 0; row2 < 9; row2++) {
						//excludes itself
						if (row != row2) {
							//if pair candidates exist
							if (data[row][j].equals(data[row2][j])) {
								//loop this column
								for (int r = 0; r < 9; r++) {
									//exclude pair candidates
									if (r != row) {
										if (r != row2) {
											// delete this pair number from other candidates on this column
											for (int index = 0; index < data[row][j].length(); index++) {
												data[r][j] = data[r][j]
														.replace(String.valueOf(data[row][j].charAt(index)), "");
											}
										}
									}
								}
							}
						}
					}

				}
			}
		}
		//send data to numPanel
		numPanel.setDate(data);
		//thread sleep 300ms
		Thread.sleep(300);
		//update data
		single(data);
		return data;
	}
	
	// remove repeated char in a string
	public String removeRepeatNum(String num) {
		String newNum = "";
		for (int index = 0; index < num.length(); index++) {
			if (!newNum.contains(String.valueOf(num.charAt(index)))) {
				newNum = newNum + String.valueOf(num.charAt(index));
			}
		}
		return newNum;
	}

	//triples rule
	public String[][] triple(String[][] data) throws InterruptedException {
		String firstNum;
		String secondNum;
		String thirdNum;
		String tripleNum;
		// one box
		for (int boxRow = 0; boxRow < 3; boxRow++) {
			for (int boxCol = 0; boxCol < 3; boxCol++) {
				
				//try to find first candidate
				for (int i = boxRow * 3; i < (boxRow + 1) * 3; i++) {
					for (int j = boxCol * 3; j < (boxCol + 1) * 3; j++) {
						if (data[i][j].length() <= 3 && data[i][j].length() > 1) {
							firstNum = data[i][j];
							
							//try to find second candidate
							for (int ii = boxRow * 3; ii < (boxRow + 1) * 3; ii++) {
								for (int jj = boxCol * 3; jj < (boxCol + 1) * 3; jj++) {
									if ((ii == i && jj == j) == false) {
										if (data[ii][jj].length() <= 3 && data[ii][jj].length() > 1) {
											secondNum = data[ii][jj];
											
											//try to find third candidate
											for (int iii = boxRow * 3; iii < (boxRow + 1) * 3; iii++) {
												for (int jjj = boxCol * 3; jjj < (boxCol + 1) * 3; jjj++) {
													if (((iii == i && jjj == j) || (iii == ii && jjj == jj)) == false) {
														if (data[iii][jjj].length() <= 3
																&& data[iii][jjj].length() > 1) {
															thirdNum = data[iii][jjj];
															
															//remove repeated numbers on merge string
															tripleNum = removeRepeatNum(
																	firstNum + secondNum + thirdNum);
															
															//if the merge string is suitable for triple rule
															if (tripleNum.length() == 3) {
																//loop this box
																for (int row = boxRow * 3; row < (boxRow + 1)
																		* 3; row++) {
																	for (int col = boxCol * 3; col < (boxCol + 1)
																			* 3; col++) {
																		//exclude themselves
																		if (((row == i && col == j)|| (row == ii && col == jj)|| (row == iii && col == jjj)) == false) {
																			for (int index = 0; index < tripleNum.length(); index++) {
																				data[row][col] = data[row][col].replace(String.valueOf(tripleNum.charAt(index)),"");
																			}
																		}

																	}
																}
															}
														}
													}

												}
											}
										}
									}

								}
							}
						}
					}
				}
			}
		}

		// one row
		//try to find first candidate
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (data[i][j].length() <= 3) {
					firstNum = data[i][j];
					
					//try to find second candidate
					for (int jj = 0; jj < 9; jj++) {
						if (data[i][jj].length() <= 3) {
							if (j != jj) {
								secondNum = data[i][jj];
								
								//try to find third candidate
								for (int jjj = 0; jjj < 9; jjj++) {
									if (data[i][jjj].length() <= 3) {
										if (jjj != j && jjj != jj) {
											thirdNum = data[i][jjj];
											
											//remove repeated numbers on merge string
											tripleNum = removeRepeatNum(firstNum + secondNum + thirdNum);
											
											//if the merge string is suitable for triple rule
											if (tripleNum.length() == 3) {
												for (int col = 0; col < 9; col++) {
													//exclude themselves
													if (((col == j) || (col == jj) || (col == jjj)) == false) {
														for (int index = 0; index < tripleNum.length(); index++) {
															data[i][col] = data[i][col].replace(
																	String.valueOf(tripleNum.charAt(index)), "");
														}
													}
													
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		// one column
		//try to find first candidate
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 9; i++) {
				if (data[i][j].length() <= 3) {
					firstNum = data[i][j];
					
					//try to find second candidate
					for (int ii = 0; ii < 9; ii++) {
						if (data[ii][j].length() <= 3) {
							if (i != ii) {
								secondNum = data[ii][j];

								for (int iii = 0; iii < 9; iii++) {
									if (data[iii][j].length() <= 3) {
										if (iii == i && iii != i) {
											thirdNum = data[iii][j];
											
											//remove repeated numbers on merge string
											tripleNum = removeRepeatNum(firstNum + secondNum + thirdNum);
											
											//if the merge string is suitable for triple rule
											if (tripleNum.length() == 3) {
												for (int row = 0; row < 9; row++) {
													//exclude themselves
													if (((row == i) || (row == ii) || (row == iii)) == false) {
														for (int index = 0; index < tripleNum.length(); index++) {
															data[row][j] = data[row][j].replace(
																	String.valueOf(tripleNum.charAt(index)), "");
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		numPanel.setDate(data);
		Thread.sleep(300);
		single(data);
		return data;
	}
	
	//list all possible possible numbers of all the cells
	public String[][] pencillingIn(String[][] data) throws InterruptedException {
		String num = "";
		String num2 = "";
		//data2 array is only used for store output data
		String[][] data2 = new String[9][9];

		// copy array
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				//if this cell is space, add "0" on it.
				if (data[i][j].equals("")) {
					data[i][j] = "0";
				}
				data2[i][j] = data[i][j];
			}
		}

		//loop every box
		for (int boxRow = 0; boxRow < 3; boxRow++) {
			for (int boxCol = 0; boxCol < 3; boxCol++) {

				// calculate possible numbers on one box
				num = "123456789";
				for (int ii = boxRow * 3; ii < (boxRow + 1) * 3; ii++) {
					for (int jj = boxCol * 3; jj < (boxCol + 1) * 3; jj++) {
						num = num.replace(data[ii][jj], "");
					}
				}
				
				//one box
				for (int i = boxRow * 3; i < (boxRow + 1) * 3; i++) {
					for (int j = boxCol * 3; j < (boxCol + 1) * 3; j++) {

						// only try to find possible candidates on space cells
						if (data[i][j].equals("0")) {
							num2 = num;
							// delete solved single candidates on row
							for (int row = 0; row < 9; row++) {
								num2 = num2.replace(data[row][j], "");
							}
							// delete solved single candidates on column
							for (int column = 0; column < 9; column++) {
								num2 = num2.replace(data[i][column], "");
							}
							//store output array into data2
							data2[i][j] = num2;
						}
					}
				}
			}
		}
		
		//update array until no new single candidate appears
		data2 = single(data2);

		return data2;
	}

	// return true if two arrays are equal
	public boolean arrayEquals(String[][] a, String b[][]) {
		//compare their length
		if (a.length != b.length) {
			return false;
		} else {
			for (int i = 0; i < a.length; i++) {
				if (a[i].length != b[i].length) {
					return false;
				} else {
					//compare their elements
					if (Arrays.equals(a[i], b[i]) == false) {
						return false;
					}
				}
			}
		}
		return true;
	}

	// update status by checking whether stuck appears
	private void checkStuck(String data[][],JTextArea status) {
		this.status.setCaretPosition(status.getText().length());
		//if the final array contains cell whose length is longer than 2, report stuck in textArea. If not, report finish.
		boolean judge = true;
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				//if the final array contains cell whose length is longer than 2, judge is false
				if(data[i][j].length()>1 || data[i][j].length()==0) {
					judge = false;
					break;
					}
			}
			if(judge ==false) {break;}
		}
		if(judge) {status.append("finish..."+"\n"); unlockCell();}
		else {status.append("stuck..."+"\n"); unlockCell();}
	}
	
	//lock cells so the user can not type
	private void lockCell() {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				this.numPanel.textField[i][j].setEditable(false);
			}
		}
	}
	
	//unlock cells so the user can type
	private void unlockCell() {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				this.numPanel.textField[i][j].setEditable(true);
			}
		}
	}
	
	@Override
	public void run() {
		String[][] solve = new String[9][9];
		lockCell();
		try {
			data = pencillingIn(data);
			//loop every methods until data never change
			while (true) {
				if (arrayEquals(data, solve)) {
					break;
				} else {
					data = singleSquare(data);
					data = claiming(data);
					data = pairs(data);
					data = triple(data);

					solve = singleSquare(data);
					solve = claiming(solve);
					solve = pairs(solve);
					solve = triple(solve);
				}
			}
			//check whether stuck happened
			checkStuck(data,status);
		} catch (InterruptedException ie) {
			Thread.interrupted();
		} catch (Exception e) {
			e.printStackTrace();
		}
		status.setCaretPosition(status.getText().length());
	}
}
