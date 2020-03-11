package com.mikikus.apps;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Paint extends JFrame implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//Declare storage
	DataStore storage = new DataStore();
	
	//Declare Pane
	private JLayeredPane panel = getLayeredPane();
	
	//Declare Window size preferences
	private final static int SIZE_X = 1285; 
	private final static int SIZE_Y = 745; 
	
	//Declare Text Areas + ScrollPane
	JTextArea backArea         = new JTextArea();
	JTextArea textArea         = new JTextArea();
	JTextArea searchArea       = new JTextArea();
	JTextArea SearchBackground = new JTextArea();
	JScrollPane scroll         = new JScrollPane(textArea,   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JScrollPane scrollFind     = new JScrollPane(searchArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	//Declare buttons
	JButton buttonShow      = new JButton("Show") ; JButton buttonAddBook       = new JButton("Add Book");
	JButton buttonDone      = new JButton("Done") ; JButton buttonSave          = new JButton("Save");
	JButton buttonLoad      = new JButton("Load") ; JButton buttonCloseAddField = new JButton("Back");
	JButton buttonCloseShow = new JButton("Back") ; JButton buttonSearchMenu    = new JButton("Search");
	JButton buttonFind      = new JButton("Find") ; JButton buttonCloseSearch   = new JButton("Back");
	JButton buttonClearData = new JButton("Clear");
	
	//Declare radio buttons
	JRadioButton radioName = new JRadioButton("Name");
	JRadioButton radioAuth = new JRadioButton("Author");
	JRadioButton radioPubl = new JRadioButton("Publisher");
	JRadioButton radioID   = new JRadioButton("ID");
	JRadioButton radioYear = new JRadioButton("Year");
	
	//Declare labels + text fields
	JLabel labelName   = new JLabel("Book Title:")   ; JTextField inputName   = new JTextField();
	JLabel labelAuth   = new JLabel("Author Name:")  ; JTextField inputAuth   = new JTextField();
	JLabel labelPubl   = new JLabel("Publisher:")    ; JTextField inputPubl   = new JTextField();
	JLabel labelYear   = new JLabel("Year of issue:"); JTextField inputYear   = new JTextField();
	JLabel labelPage   = new JLabel("Pages:")        ; JTextField inputPage   = new JTextField();
	JLabel labelPrice  = new JLabel("Price:")        ; JTextField inputPrice  = new JTextField();
	JLabel labelBind   = new JLabel("Binding:")      ; JTextField inputBind   = new JTextField();
	JLabel labelSearch = new JLabel("Search by:")    ; JTextField inputSearch = new JTextField();
	
	//Constructor
	public Paint(){
		super("My Shameful Library");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//Window preferences
		setSize(SIZE_X, SIZE_Y);
		setLocation(330,170);
		
		//Background
		add(new Field());	
		
		//Start menu
		showDefaultMenu();
		
		//Loading actions
		loadButtonActions();
		
		setResizable(false);
		setVisible(true);
	}
	
	//Actions for each button
	public void loadButtonActions()  {
		//Action 1: show "Add Book" field
		buttonAddBook.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					showAddTab();
			}
		});
		
		//Action 2: write input data to the file
		buttonDone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dataWrite();
		   }
		});
		
		//Action 3: Save all data
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				try {
					dataSave();
				} catch (IOException e1) {
					System.out.println("SAVE ERROR!" + e1);
				}
			}
		});
		
		//Action 4: Load all data
		buttonLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					dataLoad();
				} catch (ClassNotFoundException | IOException e2) {
					System.out.println("LOAD ERROR!" + e2);
				}
			}
		});
		
		//Action 5: Show all data
		buttonShow.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dataShow();
			}
		});
		
		//Action 6: back from add field
		buttonCloseAddField.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				backFromAddField();
			}
		});
		
		//Action 7: back from "show"
		buttonCloseShow.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				backFromShow();
			}
		});
		
		//Action 8: Search menu
		buttonSearchMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				openSearchTab();
			}
		});
		
		//Action 9: Find book
		buttonFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					searchPerform();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Find error.", "Caution!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		//Action 10: Back from search tab
		buttonCloseSearch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				closeSearch();
			}
		});
		
		//Action 11: Clear all data
		buttonClearData.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(storage.getCount() >= 0) {
					dataClear();
					JOptionPane.showMessageDialog(null, "All data has been deleted.", "Success!(Or not)", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Nothing to delete!", "Caution!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
	}
	
	//Start screen
	public void showDefaultMenu() {
		//Add buttons to the panel
		panel.add(buttonAddBook,    JLayeredPane.PALETTE_LAYER);
		panel.add(buttonShow,       JLayeredPane.PALETTE_LAYER);
		panel.add(buttonSave,       JLayeredPane.PALETTE_LAYER);
		panel.add(buttonLoad,       JLayeredPane.PALETTE_LAYER);
		panel.add(buttonSearchMenu, JLayeredPane.PALETTE_LAYER);
		panel.add(buttonClearData,  JLayeredPane.PALETTE_LAYER);
		
		//Set up buttons
		buttonAddBook.setSize   (100, 100) ; buttonAddBook.setLocation   (100, 20);
		buttonSearchMenu.setSize(100, 100) ; buttonSearchMenu.setLocation(250, 20);
		buttonShow.setSize      (100, 100) ; buttonShow.setLocation      (100, 200);
		buttonSave.setSize      (100, 100) ; buttonSave.setLocation      (100, 380);
		buttonLoad.setSize      (100, 100) ; buttonLoad.setLocation      (100, 560);
		buttonClearData.setSize (100, 100) ; buttonClearData.setLocation (250, 200); 
		
		//Repainting
		revalidate();
		repaint();
	}
	
	//Label fonts
	public void labelFontsLoad() {
		Font fontie = new Font("Verdana", Font.BOLD, 14);
		labelName.setFont(fontie); labelPubl.setFont(fontie); 
		labelAuth.setFont(fontie); labelPrice.setFont(fontie); 
		labelYear.setFont(fontie); labelBind.setFont(fontie); 
		labelPage.setFont(fontie); 
	}
	
	//Action from "Add" button
	public void showAddTab() {
			//Fonts
			labelFontsLoad();
			
			//Remove junk from the screen
			panel.remove(buttonAddBook)  ; panel.remove(buttonShow); 
			panel.remove(buttonLoad)     ; panel.remove(buttonSave);
			panel.remove(textArea)       ; panel.remove(scroll);
			panel.remove(buttonCloseShow); panel.remove(buttonSearchMenu);
			panel.remove(buttonClearData);
			
			//Add text area for background
			backArea.setEditable(false);
			backArea.setBackground(Color.pink);
			backArea.setSize(660, 580); backArea.setLocation(350, 30);
			panel.add(backArea, JLayeredPane.DEFAULT_LAYER);
			
			//Add Labels to the panel
			panel.add(labelName, JLayeredPane.PALETTE_LAYER); panel.add(labelAuth, JLayeredPane.PALETTE_LAYER);
			panel.add(labelPubl, JLayeredPane.PALETTE_LAYER); panel.add(labelYear, JLayeredPane.PALETTE_LAYER);
			panel.add(labelPage, JLayeredPane.PALETTE_LAYER); panel.add(labelPrice, JLayeredPane.PALETTE_LAYER);
			panel.add(labelBind, JLayeredPane.PALETTE_LAYER);
			
			//Set up labels
			labelName.setSize (200, 20); labelName.setLocation (365, 57);
			labelAuth.setSize (200, 20); labelAuth.setLocation (365, 117);
			labelPubl.setSize (200, 20); labelPubl.setLocation (365, 177);
			labelYear.setSize (200, 20); labelYear.setLocation (365, 237);
			labelPage.setSize (200, 20); labelPage.setLocation (365, 297);
			labelPrice.setSize(200, 20); labelPrice.setLocation(365, 357);
			labelBind.setSize (200, 20); labelBind.setLocation (365, 417);
			
			//Add Text Fields to the panel
			panel.add(inputName, JLayeredPane.PALETTE_LAYER); panel.add(inputAuth, JLayeredPane.PALETTE_LAYER);
			panel.add(inputPubl, JLayeredPane.PALETTE_LAYER); panel.add(inputYear, JLayeredPane.PALETTE_LAYER);
			panel.add(inputPage, JLayeredPane.PALETTE_LAYER); panel.add(inputPrice, JLayeredPane.PALETTE_LAYER);
			panel.add(inputBind, JLayeredPane.PALETTE_LAYER); 
			
			//Set up text fields
			inputName.setSize (500, 35); inputName.setLocation (480, 50);	
			inputAuth.setSize (500, 35); inputAuth.setLocation (480, 110);
			inputPubl.setSize (500, 35); inputPubl.setLocation (480, 170);
			inputYear.setSize (500, 35); inputYear.setLocation (480, 230);
			inputPage.setSize (500, 35); inputPage.setLocation (480, 290);
			inputPrice.setSize(500, 35); inputPrice.setLocation(480, 350);
			inputBind.setSize (500, 35); inputBind.setLocation (480, 410);
			
			//Add "Done" button
			panel.add(buttonDone, JLayeredPane.PALETTE_LAYER); panel.add(buttonCloseAddField, JLayeredPane.PALETTE_LAYER);
			
			//Set up for buttons
			buttonDone.setSize         (100, 100); buttonDone.setLocation         (880, 480);
			buttonCloseAddField.setSize(100, 100); buttonCloseAddField.setLocation(680, 480);
			
			//Repainting the screen
			revalidate();
			repaint();
	}
	
	//Action for "Find" button
	public void searchPerform() throws IOException {
		//If there are no books in the library
		if(storage.getCount() < 0){
			JOptionPane.showMessageDialog(null, "No books to check info.\nPlease, add books to the library!", "Oops", JOptionPane.PLAIN_MESSAGE);
		}
		
		//For radioName
		if(radioName.isSelected()) {
			//Updating Search Area
			searchArea.setText("");
			
			//Checking: if there is a match, then show it to a user
			BufferedReader br = null;
			int tempVar = 0;
			try {
				for(int i = 0; i <= storage.getCount(); i++) {
					if(storage.getBooks().get(i).getName().equalsIgnoreCase(inputSearch.getText().trim())) {
						String tempLine = null;
						String showName = "src/main/java/Data/" + i + ".txt";
						br = new BufferedReader(new FileReader(showName.trim()));
						while((tempLine = br.readLine()) != null) {
							searchArea.append(" " + tempLine + "\n");
						}
						searchArea.append("_________________________________________________________________________________\n");
						tempVar++;
						br.close();
					}
				}
				if(tempVar == 0) {
					JOptionPane.showMessageDialog(null, "No results!", "Oops", JOptionPane.PLAIN_MESSAGE);
				}
			} catch(IOException e) {
				System.out.println("Error" + e);
			}
		}
		
		//For radioAuth
		if(radioAuth.isSelected()) {
			//Updating Search Area
			searchArea.setText("");
			
			//Checking: if there is a match, then show it to a user
			BufferedReader br = null;
			int tempVar = 0;
			try {
				for(int i = 0; i <= storage.getCount(); i++) {
					if(storage.getBooks().get(i).getAuthor().equalsIgnoreCase(inputSearch.getText().trim())) {
						String tempLine = null;
						String showName = "src/main/java/Data/" + i + ".txt";
						br = new BufferedReader(new FileReader(showName.trim()));
						while((tempLine = br.readLine()) != null) {
							searchArea.append(" " + tempLine + "\n");
						}
						searchArea.append("_________________________________________________________________________________\n");
						tempVar++;
						br.close();
					}
				}
				if(tempVar == 0) {
					JOptionPane.showMessageDialog(null, "No results!", "Oops", JOptionPane.PLAIN_MESSAGE);
				}
			} catch(IOException e) {
				System.out.println("Error" + e);
			}
		}
		
		//For radioYear
		if(radioYear.isSelected()) {
			//Updating Search Area
			searchArea.setText("");
			
			//Checking: if there is a match, then show it to a user
			BufferedReader br = null;
			int tempVar = 0;
			try {
				for(int i = 0; i <= storage.getCount(); i++) {
					if(storage.getBooks().get(i).getYear().equalsIgnoreCase(inputSearch.getText().trim())) {
						String tempLine = null;
						String showName = "src/main/java/Data/" + i + ".txt";
						br = new BufferedReader(new FileReader(showName.trim()));
						while((tempLine = br.readLine()) != null) {
							searchArea.append(" " + tempLine + "\n");
						}
						searchArea.append("_________________________________________________________________________________\n");
						tempVar++;
						br.close();
					}
				}
				if(tempVar == 0) {
					JOptionPane.showMessageDialog(null, "No results!", "Oops", JOptionPane.PLAIN_MESSAGE);
				}
			} catch(IOException e) {
				System.out.println("Error" + e);
			}
		}
		
		//For radioPubl
		if(radioPubl.isSelected()) {
			//Updating Search Area
			searchArea.setText("");
			
			//Checking: if there is a match, then show it to a user
			BufferedReader br = null;
			int tempVar = 0;
			try {
				for(int i = 0; i <= storage.getCount(); i++) {
					if(storage.getBooks().get(i).getPublisher().equalsIgnoreCase(inputSearch.getText().trim())) {
						String tempLine = null;
						String showName = "src/main/java/Data/" + i + ".txt";
						br = new BufferedReader(new FileReader(showName.trim()));
						while((tempLine = br.readLine()) != null) {
							searchArea.append(" " + tempLine + "\n");
						}
						searchArea.append("_________________________________________________________________________________\n");
						tempVar++;
						br.close();
					}
				}
				if(tempVar == 0) {
					JOptionPane.showMessageDialog(null, "No results!", "Oops", JOptionPane.PLAIN_MESSAGE);
				}
			} catch(IOException e) {
				System.out.println("Error" + e);
			}
		}
		
		//For radioID
		if(radioID.isSelected()) {
			//Updating Search Area
			searchArea.setText("");
			
			//Checking: if there is a match, then show it to a user
			BufferedReader br = null;
			int tempVar = 0;
			try {
				for(int i = 0; i <= storage.getCount(); i++) {
					String tempIdLine = String.format("%d", storage.getBooks().get(i).getID()).trim();
					if(tempIdLine.equalsIgnoreCase(inputSearch.getText().trim())){
						String tempLine = null;
						String showName = "src/main/java/Data/" + i + ".txt";
						br = new BufferedReader(new FileReader(showName.trim()));
						while((tempLine = br.readLine()) != null) {
							searchArea.append(" " + tempLine + "\n");
						}
						searchArea.append("_________________________________________________________________________________\n");
						tempVar++;
						br.close();
					}
				}
				if(tempVar == 0) {
					JOptionPane.showMessageDialog(null, "No results!", "Oops", JOptionPane.PLAIN_MESSAGE);
				}
			} catch(IOException e) {
				System.out.println("Error" + e);
			}
		}
	}
	
	//Action for "Done" button
	public void dataWrite() {
		try{
			if(inputName.getText().trim().length() == 0 || inputAuth.getText().trim().length() == 0 || 
			   inputPubl.getText().trim().length() == 0 || inputYear.getText().trim().length() == 0 || 
			   inputPage.getText().trim().length() == 0 || inputPrice.getText().trim().length() == 0 || 
			   inputBind.getText().trim().length() == 0) 
			{	
				//If some field length is equal to NULL, so user have to fill it in
				JOptionPane.showMessageDialog(null, "Please, fill in all required fields!", "Caution!", JOptionPane.PLAIN_MESSAGE);
			} else {
				storage.countPlusOne();
				//giving input data from text fields to new Books object 
				storage.getBooks().add(new Books(storage.getCount(), inputName.getText(), inputAuth.getText(), inputPubl.getText(), inputYear.getText(), inputPage.getText(), inputPrice.getText(), inputBind.getText()));
				String fileName = "src/main/java/Data/" + storage.getCount() + ".txt";
				File file = new File(fileName.trim());
				if(!file.exists()) {
					file.createNewFile();
				}		
				//Wring to the file 
				PrintWriter pw = new PrintWriter(file);
				pw.println("ID:        " + storage.getCount());
				pw.println("Title:     " + storage.getBooks().get(storage.getCount()).getName().trim());
				pw.println("Author:    " + storage.getBooks().get(storage.getCount()).getAuthor().trim());
				pw.println("Publisher: " + storage.getBooks().get(storage.getCount()).getPublisher().trim());
				pw.println("Year:      " + storage.getBooks().get(storage.getCount()).getYear().trim());
				pw.println("Pages:     " + storage.getBooks().get(storage.getCount()).getPages().trim());
				pw.println("Price:     " + storage.getBooks().get(storage.getCount()).getPrice().trim());			
				pw.println("Binding:   " + storage.getBooks().get(storage.getCount()).getBinding().trim());
				pw.close();
				
				//Say to user that data has been written
				JOptionPane.showMessageDialog(null, "Book has beed added!", "Success!", JOptionPane.PLAIN_MESSAGE);
				
				//removing junk, so then we can go back to menu without it on the screen
				panel.remove(inputName); panel.remove(labelName); 
				panel.remove(inputPubl); panel.remove(labelPubl);
				panel.remove(inputPage); panel.remove(labelPage);
				panel.remove(inputBind); panel.remove(labelBind);
				panel.remove(inputPrice);panel.remove(labelPrice);
				panel.remove(inputAuth); panel.remove(labelAuth);
				panel.remove(inputYear); panel.remove(labelYear);
				panel.remove(buttonDone); panel.remove(backArea);
				panel.remove(buttonCloseAddField);
				
				//set Menu back
				showDefaultMenu();
				
				//Repaint
				revalidate();
				repaint();
			}
		} catch(IOException er) {
				System.out.println("Error" + er);
		} 
	}
	
	//Action for "Show" button
	public void dataShow(){
		//Font for text area
		Font font = new Font("LucidaConsole", Font.BOLD, 14);
		
		//Set up text area
		textArea.setFont(font);
		textArea.setEditable(false);
		
		//Set up for scroll
		scroll.setSize(700, 650);
		scroll.setLocation(450, 30);
		
		//Adding area to panel
		panel.add(scroll, JLayeredPane.PALETTE_LAYER);
		
		//Reading from files, showing on the screen, in the text area
		BufferedReader br = null;
		try {
			for(int i = 0; i <= storage.getCount(); i++) {
				String line = null;
				//String result = null;
				String showName = "src/main/java/Data/" + i + ".txt";
				br = new BufferedReader(new FileReader(showName.trim()));
				while((line = br.readLine()) != null) {
					textArea.append(" " + line + "\n");
				}
				textArea.append("_________________________________________________________________________________\n");
				br.close();
			}
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Operation failed.", "Caution!", JOptionPane.PLAIN_MESSAGE);
		} 
		
		//removing show button and adding "Back", removing "Load" button
		panel.remove(buttonShow)     ; panel.remove(buttonLoad);
		panel.remove(buttonAddBook)  ; panel.remove(buttonSearchMenu);
		panel.remove(buttonClearData);
		
		//Set up close button
		buttonCloseShow.setLocation(100, 200);
		buttonCloseShow.setSize(100, 100);
		
		//Adding close button to the panel
		panel.add(buttonCloseShow) ; 
		
		//repaint
		revalidate();
		repaint();
	}
	
	//Action for "buttonCloseShow" button
	public void backFromShow() {
		//Removing junk
		panel.remove(buttonCloseShow);
		panel.remove(textArea); 
		panel.remove(scroll);
		
		//Clearing text area
		textArea.setText(null);
		
		//Add buttons
		panel.add(buttonShow,       JLayeredPane.PALETTE_LAYER); 
		panel.add(buttonLoad,       JLayeredPane.PALETTE_LAYER);
		panel.add(buttonAddBook,    JLayeredPane.PALETTE_LAYER);
		panel.add(buttonSearchMenu, JLayeredPane.PALETTE_LAYER);
		panel.add(buttonClearData,  JLayeredPane.PALETTE_LAYER);
		
		//Set up buttons 
		buttonShow.setSize     (100, 100) ; buttonShow.setLocation     (100, 200);
		buttonLoad.setSize     (100, 100) ; buttonLoad.setLocation     (100, 560);
		buttonAddBook.setSize  (100, 100) ; buttonAddBook.setLocation  (100, 20);
		buttonClearData.setSize(100, 100) ; buttonClearData.setLocation(250, 200); 
		
		//repaint
		revalidate();
		repaint();
	}
	
	//Action for "buttonCloseAddField" button
	public void backFromAddField() {
		//removing junk, so then we can go back to menu without it on the screen
		panel.remove(inputName); panel.remove(labelName); 
		panel.remove(inputPubl); panel.remove(labelPubl);
		panel.remove(inputPage); panel.remove(labelPage);
		panel.remove(inputBind); panel.remove(labelBind);
		panel.remove(inputPrice);panel.remove(labelPrice);
		panel.remove(inputAuth); panel.remove(labelAuth);
		panel.remove(inputYear); panel.remove(labelYear);
		panel.remove(buttonDone); panel.remove(buttonCloseAddField);
		panel.remove(backArea);
		
		//set Menu back
		showDefaultMenu();
		revalidate();
		repaint();
	}
	
	//Action for "buttonCloseSearch" button
	public void closeSearch() {
		//removing junk
		panel.remove(scrollFind); panel.remove(inputSearch);
		panel.remove(radioAuth) ; panel.remove(radioYear);
		panel.remove(radioPubl) ; panel.remove(radioName);
		panel.remove(radioID);
		
		panel.remove(buttonCloseSearch);
		panel.remove(buttonFind);
		
		//Enable buttons
		buttonShow.setEnabled(true); buttonAddBook.setEnabled(true); 
		buttonLoad.setEnabled(true); buttonSave.setEnabled(true);
		
		
		//Add buttons
		panel.add(buttonSearchMenu, JLayeredPane.PALETTE_LAYER);
		panel.add(buttonClearData,  JLayeredPane.PALETTE_LAYER);
		
		//Set up buttons
		buttonSearchMenu.setSize(100,100)  ; buttonSearchMenu.setLocation(250, 20);
		buttonClearData.setSize (100, 100) ; buttonClearData.setLocation (250, 200); 
		
		//Repaint
		revalidate();
		repaint();
	}
	
	//Action for "Search" button
	public void openSearchTab(){
		//remove junk
		panel.remove(buttonSearchMenu); 
		panel.remove(buttonClearData);
		
		//Deactivate other buttons
		buttonShow.setEnabled(false) ; buttonAddBook.setEnabled(false);
		buttonSave.setEnabled(false) ; buttonLoad.setEnabled(false);
		
		//Add radio buttons
		panel.add(radioName, JLayeredPane.PALETTE_LAYER); panel.add(radioAuth,  JLayeredPane.PALETTE_LAYER);
		panel.add(radioYear, JLayeredPane.PALETTE_LAYER); panel.add(radioPubl,  JLayeredPane.PALETTE_LAYER);
		panel.add(radioID,   JLayeredPane.PALETTE_LAYER); 
		
		//Add buttons
		panel.add(buttonFind,        JLayeredPane.PALETTE_LAYER);
		panel.add(buttonCloseSearch, JLayeredPane.PALETTE_LAYER);
		
		//Add text area and text field
		panel.add(inputSearch, JLayeredPane.PALETTE_LAYER);
		panel.add(scrollFind,  JLayeredPane.PALETTE_LAYER);
		
		//Set up text area and text field
		searchArea.setEditable(false);
		scrollFind.setSize (600, 600); scrollFind.setLocation (500, 85);
		inputSearch.setSize(700, 40) ; inputSearch.setLocation(400, 30);
		
		//Set up radio buttons
		radioName.setSelected(true);
		radioName.setSize  (90, 50); radioName.setLocation (400, 85); 
		radioAuth.setSize  (90, 50); radioAuth.setLocation (400, 135); 
		radioYear.setSize  (90, 50); radioYear.setLocation (400, 185); 
		radioPubl.setSize  (90, 50); radioPubl.setLocation (400, 235); 
		radioID.setSize    (90, 50); radioID.setLocation   (400, 285);
		
		//Set up buttons
		buttonFind.setSize       (90, 90); buttonFind.setLocation       (400, 350);
		buttonCloseSearch.setSize(90, 90); buttonCloseSearch.setLocation(400, 455);
		
		//Group radio buttons
		ButtonGroup group = new ButtonGroup();
		group.add(radioName); group.add(radioAuth);
		group.add(radioYear); group.add(radioPubl);
		group.add(radioID)  ;
		
		//Repaint
		revalidate();
		repaint();
	}
	
	//Restore files from DataStore object 
	public void dataRestore() {
		if(storage.getCount() >= 0) {
			try {
				for(int i = 0; i <= storage.getCount(); i++) {
					String fileName = "src/main/java/Data/" + i + ".txt";
					File file = new File(fileName.trim());
					PrintWriter pw = new PrintWriter(file);
					pw.println("ID       : " + i);
					pw.println("Title    : " + storage.getBooks().get(i).getName());
					pw.println("Author   : " + storage.getBooks().get(i).getAuthor());
					pw.println("Publisher: " + storage.getBooks().get(i).getPublisher());
					pw.println("Year     : " + storage.getBooks().get(i).getYear());
					pw.println("Pages    : " + storage.getBooks().get(i).getPages());
					pw.println("Price    : " + storage.getBooks().get(i).getPrice());
					pw.println("Binding  : " + storage.getBooks().get(i).getBinding());
					pw.close();
				}
			} catch(IOException e) {
				JOptionPane.showMessageDialog(null, "Operation failed!", "Caution!", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	//Clear existing files and data | Also an action for buttonDeleteData
	public void dataClear() {
		//Reset count
		storage.setCount(-1);
		
		//Directory
		String path = "src/main/java/Data/";
		
		//Clear catalog
		File file = new File(path);
		for(File i: file.listFiles()) {
			if(i.isFile()) {
				i.delete();
			}
		}
	}
	
	//Action for "Save" button
	public void dataSave() throws FileNotFoundException, IOException {
		//Serializing our DataStore object
		FileOutputStream fileOutputStream = new FileOutputStream("storage.out");
		ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutputStream);
		objectOutput.writeObject(storage);
		objectOutput.close();
		JOptionPane.showMessageDialog(null, "Data has been saved.", "Success!", JOptionPane.PLAIN_MESSAGE);
	}
	
	//Action for "Load" button
	public void dataLoad() throws FileNotFoundException, IOException, ClassNotFoundException {
		//Clear existing data
		dataClear();
		
		//Load saved earlier data to temp DataStore object 
		FileInputStream fileInputStream = new FileInputStream("storage.out");
		ObjectInputStream objectInput = new ObjectInputStream(fileInputStream);
		DataStore storageRestored = (DataStore) objectInput.readObject();
		objectInput.close();
		
		//Load restored data to out main storage
		storage = new DataStore(storageRestored);
		JOptionPane.showMessageDialog(null, "Data has been loaded.", "Success!", JOptionPane.PLAIN_MESSAGE);
		
		//Restore files
		dataRestore();
	}
}
