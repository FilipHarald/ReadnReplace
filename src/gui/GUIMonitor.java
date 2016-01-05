package gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

/**
 * The GUI for assignment 4
 */
public class GUIMonitor
{
	/**
	 * These are the components you need to handle.
	 * You have to add listeners and/or code
	 */
	private JFrame frame;				// The Main window
	private JMenu fileMenu;				// The menu
	private JMenuItem openItem;			// File - open
	private JMenuItem saveItem;			// File - save as
	private JMenuItem exitItem;			// File - exit
	private JTextField txtFind;			// Input string to find
	private JTextField txtReplace; 		// Input string to replace
	private JCheckBox chkNotify;		// User notification choise
	private JLabel lblInfo;				// Hidden after file selected
	private JButton btnCreate;			// Start copying
	private JButton btnClear;			// Removes dest. file and removes marks
	private JLabel lblChanges;			// Label telling number of replacements
	
	private JTextArea txtAreaSource;
	private JTextArea txtAreaDest;
	
	private Controller controller;
	
	/**
	 * Constructor
	 */
	public GUIMonitor()
	{
	}
	
	/**
	 * Starts the application
	 */
	public void Start()
	{
		controller = new Controller(this);
		frame = new JFrame();
		frame.setBounds(0, 0, 714,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setTitle("Text File Copier - with Find and Replace");
		InitializeGUI();					// Fill in components
		frame.setVisible(true);
		frame.setResizable(false);			// Prevent user from change size
		frame.setLocationRelativeTo(null);	// Start middle screen
	}
	
	/**
	 * Sets up the GUI with components
	 */
	private void InitializeGUI()
	{
		ButtonListener BL = new ButtonListener();
		
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open Source File");
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		saveItem = new JMenuItem("Save Destination File As"); 
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveItem.setEnabled(false);
		exitItem = new JMenuItem("Exit");
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		openItem.addActionListener(BL);
		saveItem.addActionListener(BL);
		exitItem.addActionListener(BL);
		JMenuBar  bar = new JMenuBar();
		frame.setJMenuBar(bar);
		bar.add(fileMenu);
		
		JPanel pnlFind = new JPanel();
		pnlFind.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Find and Replace"));
		pnlFind.setBounds(12, 32, 436, 122);
		pnlFind.setLayout(null);
		frame.add(pnlFind);
		JLabel lab1 = new JLabel("Find:");
		lab1.setBounds(7, 30, 80, 13);
		pnlFind.add(lab1);
		JLabel lab2 = new JLabel("Replace with:");
		lab2.setBounds(7, 63, 80, 13);
		pnlFind.add(lab2);
		
		txtFind = new JTextField();
		txtFind.setBounds(88, 23, 327, 20);
		pnlFind.add(txtFind);
		txtReplace = new JTextField();
		txtReplace.setBounds(88, 60, 327, 20);
		pnlFind.add(txtReplace);
		chkNotify = new JCheckBox("Notify user on every match");
		chkNotify.setBounds(88, 87, 180, 17);
		pnlFind.add(chkNotify);
		
		lblInfo = new JLabel("Select Source File..");
		lblInfo.setBounds(485, 42, 120, 13);
		frame.add(lblInfo);
		
		
		btnCreate = new JButton("Copy to Destination");
		btnCreate.setBounds(465, 119, 230, 23);
		btnCreate.addActionListener(BL);
		frame.add(btnCreate);
		btnClear = new JButton("Clear dest. and remove marks");
		btnClear.setBounds(465, 151, 230, 23);
		btnClear.addActionListener(BL);
		frame.add(btnClear);
		
		lblChanges = new JLabel("No. of Replacements:");
		lblChanges.setBounds(279, 161, 200, 13);
		frame.add(lblChanges);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 170, 653, 359);
		frame.add(tabbedPane);
		txtAreaSource = new JTextArea();
		txtAreaSource.setHighlighter(new DefaultHighlighter());
		JScrollPane scrollSource = new JScrollPane(txtAreaSource);
		tabbedPane.addTab("Source", null, scrollSource, null);
		txtAreaDest = new JTextArea();
		txtAreaDest.setHighlighter(new DefaultHighlighter());
		JScrollPane scrollDest = new JScrollPane(txtAreaDest);
		tabbedPane.addTab("Destination", null, scrollDest, null);
		btnClear.setEnabled(false);
		
	}
	
	private class ButtonListener implements ActionListener {
		private Consumer<? super String> line;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnCreate){
				System.out.println("Create");
				controller.startThreads(
						txtFind.getText(),
						txtReplace.getText(), 
						chkNotify.isSelected(),
						txtAreaSource.getText());
				btnClear.setEnabled(true);
				
			}else if(e.getSource() == btnClear){
				System.out.println("Clear");
				txtAreaSource.getHighlighter().removeAllHighlights();
				txtAreaDest.setText("");
				saveItem.setEnabled(false);
				btnClear.setEnabled(false);
				
			}else if(e.getSource() == openItem){
				System.out.println("Open");
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				fc.setFileFilter(new FileNameExtensionFilter("Text", new String []{"txt"}));
				int returnVal = fc.showOpenDialog(null);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            setSourceText(Paths.get(fc.getSelectedFile().getAbsolutePath()));
		            saveItem.setEnabled(true);
		        } else {
		        	System.out.println("Open command cancelled by user.");
		        }
		        
			}else if(e.getSource() == saveItem){
				System.out.println("Save");
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				fc.setFileFilter(new FileNameExtensionFilter("Text", new String []{"txt"}));
				int returnVal = fc.showSaveDialog(null);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            setSourceText(Paths.get(fc.getSelectedFile().getAbsolutePath()));
		            saveItem.setEnabled(false);
		        } else {
		        	System.out.println("Save command cancelled by user.");
		        }
		        
			}else if(e.getSource() == exitItem	){
				System.exit(0);
			}
		}

		private void setSourceText(Path path) {
			txtAreaSource.setText("");
			try {
				Files.lines(path).forEach(line -> {
					txtAreaSource.append(line + "\n");
				});
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}

	public void highlight(int i, int j) {
		try {
			DefaultHighlighter.DefaultHighlightPainter highlightPainter = 
					new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
			txtAreaSource.getHighlighter().addHighlight(i, j, highlightPainter);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void setReplaceCounter(int counts){
		lblChanges.setText("No. of Replacements: " + counts);
	}
}
