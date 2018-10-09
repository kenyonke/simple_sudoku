import java.awt.Color;
import java.awt.GridLayout;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

public class NumPanel extends JPanel{
	    public JTextField[][] textField = new JTextField[9][9];
	    
	    public NumPanel(){
	    	this.setLayout(new GridLayout(9,9));
		    for(int i=0;i<9;i++) {
			      for(int j=0;j<9;j++){
			    	  textField[i][j] = new JTextField();
			    	  textField[i][j].setFont(new java.awt.Font("Dialog", 1, 20));
			    	  //Set gray border
			    	  textField[i][j].setBorder(new MatteBorder(1, 1, 1, 1, Color.gray));
			    	  if(i==2 || i==5) {
			    		  textField[i][j].setBorder(new MatteBorder(1, 1, 5, 1, Color.gray));
			    	  }
			    	  if(j==2 || j==5) {
			    		  textField[i][j].setBorder(new MatteBorder(1, 1, 1, 5, Color.gray));
			    	  }
			    	  if((i==2 && j==2) || (i==2 && j==5) || (i==5 && j==2) || (i==5 && j==5)) {
			    		  textField[i][j].setBorder(new MatteBorder(1, 1, 5, 5, Color.gray));
			    	  }
			    	  textField[i][j].setText("");
			    	  //add a textField into NumPanel
			          this.add(textField[i][j]);
			      }
		    }
	    }
	    
	    //set an array data into textField and update its color
	    public void setDate(String[][] data) {
	    	for(int i=0; i<9; i++) {
	    		for(int j=0; j<9; j++) {
	    			if(data[i][j].equals("123456789")) {
	    				this.textField[i][j].setText("");
	    			} else {
	    			this.textField[i][j].setText(data[i][j]);	//set one data
	    			//checkYellow(data, this);
	    			}
	    			setColor(data[i][j], data, i, j);
	    		}
	    	}
	    }    
	    
		//update color of a cell
		private void setColor(String cell, String[][] cells, int cellRow, int cellCol){
	    	boolean yellow = true;
		    boolean red = false;
		    Pattern pattern = Pattern.compile("[0-9]*"); 
		    
		    //if the input is a number, judge its color
		    if(pattern.matcher(cell).matches()) {
		    	//if the number in cell is not single, the cell must not be yellow 
		    	if(cell.length()!=1) {
		    		yellow = false;
		    	}
		    	else {
		    		//one row
		    		for(int j=0; j<9; j++){
		    			if(j != cellCol) {
		    				//if this row has a same single number as this cell, it must be red
		    				if(cell.equals(cells[cellRow][j])) {
		    					yellow = false;
		    					red = true;
		    					break;
		    				}
		    			}
		    		}
		    	
		    		//one column
		    		for(int i=0; i<9; i++) {
		    			if(i != cellRow) {
		    				//if this column has a same single number as this cell, it must be red
		    				if(cell.equals(cells[i][cellCol])){
		    					yellow = false;
		    					red = true;
		    					break;
		    				}
		    			}
		    		}
		    	
		    		//one box
		    		for(int i = (cellRow/3)*3; i < ((cellRow/3)+1)*3; i++) {
		    			for(int j = (cellCol/3)*3; j < ((cellCol/3)+1)*3; j++) {
		    				if(!((i == cellRow) && (j == cellCol))) {
		    					//if this column has a same single number as this cell, it must be red
		    					if(cell.equals(cells[i][j])) {
		    						yellow = false;
		    						red = true;
		    						break;
		    					}
		    				}
		    			}
		    		}
		    	}
		    	
		    
		    	//set color
		    	if(yellow) {
		    		this.textField[cellRow][cellCol].setBackground(Color.yellow);
		    	}
		    	if(red) {
		    		this.textField[cellRow][cellCol].setBackground(Color.red);
		    	}
		    	if(!(yellow || red)) {
		    		this.textField[cellRow][cellCol].setBackground(Color.white);
		    	}
		    }
		    //if the input is not a number, it should be white
		    else {
		    	this.textField[cellRow][cellCol].setBackground(Color.white);
		    }
	    }

}