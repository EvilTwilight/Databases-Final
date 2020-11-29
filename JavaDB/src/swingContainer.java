import java.awt.*;
import java.awt.event.*;
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
   
   JScrollPane jScrollPane;
   JPanel outputPanel;
   
   int fontSize = 25;
   
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
      userInput.setFont(new Font("Arial", Font.PLAIN, fontSize));
      JLabel userInputLabel = new JLabel("Input your keyword(s): ");
      userInputLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
      
      constr.weightx = 0;
      constr.gridx=0;constr.gridy=1;
      constr.anchor = GridBagConstraints.EAST;
      controlPanel.add(userInputLabel, constr);  
      constr.gridx=1; constr.gridy=1;
      constr.anchor = GridBagConstraints.CENTER;
      controlPanel.add(userInput, constr);
      
      //Query 1 is number 5 on the proposal
      option1 = new JRadioButton("Query 1");
      option1.setFont(new Font("Arial", Font.PLAIN, fontSize));
      JLabel option1Label = new JLabel("Find a movie with the highest score using an actor's name.");
      option1Label.setFont(new Font("Arial", Font.PLAIN, fontSize));
      
      constr.gridx=0;constr.gridy=2;
      controlPanel.add(option1Label, constr);
      constr.gridx=1; constr.gridy=2;
      controlPanel.add(option1, constr);
      
      option2 = new JRadioButton("Query 2");
      option2.setFont(new Font("Arial", Font.PLAIN, fontSize));
      JLabel option2Label = new JLabel("Find a movie with a keyword in the title.");
      option2Label.setFont(new Font("Arial", Font.PLAIN, fontSize));
      
      constr.gridx=0;constr.gridy=3;
      controlPanel.add(option2Label, constr);
      constr.gridx=1; constr.gridy=3;
      controlPanel.add(option2, constr);
      
      option3 = new JRadioButton("Query 3");
      option3.setFont(new Font("Arial", Font.PLAIN, fontSize));
      JLabel option3Label = new JLabel("Ten most popular actors.");
      option3Label.setFont(new Font("Arial", Font.PLAIN, fontSize));
      
      constr.gridx=0;constr.gridy=4;
      controlPanel.add(option3Label, constr);
      constr.gridx=1; constr.gridy=4;
      controlPanel.add(option3, constr);
      
      option4 = new JRadioButton("Query 4");
      option4.setFont(new Font("Arial", Font.PLAIN, fontSize));
      JLabel option4Label = new JLabel("Get group of actors that have performed in the most movies.");
      option4Label.setFont(new Font("Arial", Font.PLAIN, fontSize));
      
      constr.gridx=0;constr.gridy=5;
      controlPanel.add(option4Label, constr);
      constr.gridx=1; constr.gridy=5;
      controlPanel.add(option4, constr);
      
      //our query
      option5 = new JRadioButton("Query 5");
      option5.setFont(new Font("Arial", Font.PLAIN, fontSize));
      JLabel option5Label = new JLabel("Our Query: ");
      option5Label.setFont(new Font("Arial", Font.PLAIN, fontSize));
      
      constr.gridx=0;constr.gridy=6;
      controlPanel.add(option5Label, constr);
      constr.gridx=1; constr.gridy=6;
      controlPanel.add(option5, constr);
      
      JButton okButton = new JButton("Query the database");
      okButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
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
		 
		if(option2.isSelected()) {
        		try {
					Driver.secondGet(userInput.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
        	}
		 
		if(option3.isSelected()) {
        		try {
					Driver.thirdGet();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
        	}
		
		if(option4.isSelected()) {
	    		try {
					Driver.fourthGet();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	    	}
		 
        	if(option5.isSelected()) {
        		try {
					Driver.fifthGet();
				} catch (Exception e1) {
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
      
      headerLabel.setText("Databases Final");   
      headerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
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
   
   int yCounter = 1;
   void createOutputFrame(){
         
      yCounter = 1;
      
      outputFrame = new JFrame();
      outputFrame.setBackground(new java.awt.Color(189, 223, 239));
      outputFrame.setSize(700, 700);
      
      //outputFrame.setLayout(new FlowLayout());    
      
      outputFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
        	 outputFrame.dispose();
         }        
      });
      
      outputPanel = new JPanel();
      outputPanel.setLayout(new GridBagLayout());
      outputPanel.setBackground(new java.awt.Color(189, 223, 239));
           
      jScrollPane = new JScrollPane(outputPanel);
      jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      jScrollPane.setSize(700,700);
      jScrollPane.setBackground(new java.awt.Color(189, 223, 239));
      JScrollBar bar = jScrollPane.getVerticalScrollBar();
      bar.setPreferredSize(new Dimension(30, 0));
              
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridy = 0;
      gbc.gridx = 0;
      msglabel.setText("The output is: ");
      msglabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
      
      outputPanel.add(msglabel,gbc);
      outputFrame.add(jScrollPane);
   }
   
   public void addOutputToGui(String outputLine){
      yCounter++;
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridy = yCounter;
      gbc.gridx = 0;
      JLabel newOutputLabel = new JLabel();
      newOutputLabel.setText(outputLine);
      newOutputLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
      outputPanel.add(newOutputLabel,gbc);
       
      //outputFrame.add(jScrollPane);
      outputFrame.setVisible(true);  
   }
}
