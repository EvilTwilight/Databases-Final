import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

import javax.swing.*;

public class swingContainer {
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   private JLabel msglabel;
   
   JFrame outputFrame;
   JTextField userInput;
   JLabel tellUserWhatKeyword;
  
   JRadioButton option1;
   JRadioButton option2;
   JRadioButton option3;
   JRadioButton option4;
   JRadioButton option5;
   
   public swingContainer(){
      prepareGUI();
   }

   private void prepareGUI(){
      mainFrame = new JFrame("Cohort 3");
      mainFrame.setSize(600,600);
      mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
      //mainFrame.setUndecorated(true); //this fullscreens it
      mainFrame.getContentPane().setBackground(new java.awt.Color(189, 223, 239));
      
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      
      
      
      // Main panel to define the layout. We are using GridBagLayout
      JPanel mainPanel = new JPanel();
      mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
      mainPanel.setOpaque(false);
      mainPanel.setBackground(new java.awt.Color(189, 223, 239));
      
      headerLabel = new JLabel("", JLabel.CENTER);   
      
      statusLabel = new JLabel("",JLabel.CENTER);    
      statusLabel.setSize(350,100);
      
      controlPanel = new JPanel(new GridBagLayout());
      controlPanel.setBackground(new java.awt.Color(189, 223, 239));
      
      // Constraints for the layout
      GridBagConstraints constr = new GridBagConstraints();
      constr.insets = new Insets(5, 5, 5, 5);     
      constr.anchor = GridBagConstraints.CENTER;
      
      //tell the user what keyword to type in
      tellUserWhatKeyword = new JLabel("");
      
      constr.gridx=1;
      constr.gridy=0;
      //constr.weightx = 1.0;
      controlPanel.add(tellUserWhatKeyword, constr);
      
      userInput = new JTextField(20);
      JLabel userInputLabel = new JLabel("Input your keyword(s): ");
      
      constr.weightx = 0;
      constr.gridx=0;constr.gridy=1;
      constr.anchor = GridBagConstraints.EAST;
      controlPanel.add(userInputLabel, constr);  
      constr.gridx=1; constr.gridy=1;
      constr.anchor = GridBagConstraints.CENTER;
      controlPanel.add(userInput, constr);
      
      //Query 1 is number 5 on the proposal
      option1 = new JRadioButton("Query 1");
      JLabel option1Label = new JLabel("Find a movie with the highest score using an actor's name.");
      
      constr.gridx=0;constr.gridy=2;
      controlPanel.add(option1Label, constr);
      constr.gridx=1; constr.gridy=2;
      controlPanel.add(option1, constr);
      
      option2 = new JRadioButton("Query 2");
      JLabel option2Label = new JLabel("Find a movie with a keyword in the title.");
      
      constr.gridx=0;constr.gridy=3;
      controlPanel.add(option2Label, constr);
      constr.gridx=1; constr.gridy=3;
      controlPanel.add(option2, constr);
      
      option3 = new JRadioButton("Query 3");
      JLabel option3Label = new JLabel("Ten most popular actors.");
      
      constr.gridx=0;constr.gridy=4;
      controlPanel.add(option3Label, constr);
      constr.gridx=1; constr.gridy=4;
      controlPanel.add(option3, constr);
      
      option4 = new JRadioButton("Query 4");
      JLabel option4Label = new JLabel("Get group of actors that have performed in the most movies.");
      
      constr.gridx=0;constr.gridy=5;
      controlPanel.add(option4Label, constr);
      constr.gridx=1; constr.gridy=5;
      controlPanel.add(option4, constr);
      
      //our query
      option5 = new JRadioButton("Query 5");
      JLabel option5Label = new JLabel("Our Query: ");
      
      constr.gridx=0;constr.gridy=6;
      controlPanel.add(option5Label, constr);
      constr.gridx=1; constr.gridy=6;
      controlPanel.add(option5, constr);
      
      JButton okButton = new JButton("Query the database");
      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            //statusLabel.setText("A Frame shown to the user.");
        	
        	if(option1.isSelected()) {
        		try {
					Driver.firstGet(userInput.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
            outputFrame.setVisible(true);
         }
      });
      constr.anchor = GridBagConstraints.CENTER;
      constr.gridx=1;constr.gridy=7;
      controlPanel.add(okButton, constr);
      

      
      //on the output screen
      msglabel = new JLabel("", JLabel.CENTER);

      JPanel headingPanel = new JPanel();
      headingPanel.setBackground(new java.awt.Color(189, 223, 239));
      
      headerLabel.setBackground(new java.awt.Color(189, 223, 239));
      statusLabel.setBackground(new java.awt.Color(189, 223, 239));
      
      option1.setBackground(Color.white);
      option2.setBackground(Color.white);
      option3.setBackground(Color.white);
      option4.setBackground(Color.white);
      option5.setBackground(Color.white);
      
      headingPanel.add(headerLabel);
      headingPanel.add(statusLabel);
      
      mainPanel.add(headingPanel);
      mainPanel.add(controlPanel);
      
      mainFrame.add(mainPanel);
      mainFrame.setVisible(true);  
      
      //Buttongroup
      ButtonGroup group = new ButtonGroup();
      group.add(option1);
      group.add(option2);
      group.add(option3);
      group.add(option4);
      group.add(option5);
      
      //Listeners
      option1.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent event) {
    	    	tellUserWhatKeyword.setText("Type in an actor's name.");
    	    	userInput.setText("");
    	    }
      	});
      option2.addActionListener(new ActionListener() {
	  	    public void actionPerformed(ActionEvent event) {
	  	    	tellUserWhatKeyword.setText("Type in a keyword.");
	  	    	userInput.setText("");
	  	    }
    	});
      option3.addActionListener(new ActionListener() {
	  	    public void actionPerformed(ActionEvent event) {
	  	    	tellUserWhatKeyword.setText("");
	  	    	userInput.setText("");
	  	    }
    	});
      option4.addActionListener(new ActionListener() {
	  	    public void actionPerformed(ActionEvent event) {
	  	    	tellUserWhatKeyword.setText("");
	  	    	userInput.setText("");
	  	    }
      });
      option5.addActionListener(new ActionListener() {
	  	    public void actionPerformed(ActionEvent event) {
	  	    	tellUserWhatKeyword.setText("");
	  	    	userInput.setText("");
	  	    }
      });
      
   }
   void showJFrameDemo(){
      headerLabel.setText("Databases Final");   
      outputFrame = new JFrame();
      outputFrame.setBackground(new java.awt.Color(189, 223, 239));
      outputFrame.setSize(500, 500);
      outputFrame.setLayout(new FlowLayout());    
      msglabel.setText("Output is: ");
      outputFrame.add(msglabel);
      
      outputFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
        	 outputFrame.dispose();
         }        
      });    

      mainFrame.setVisible(true);  
   }
}