package gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		JTextPane txtPaneSource = new JTextPane();
		JScrollPane scrollSource = new JScrollPane(txtPaneSource);
		tabbedPane.addTab("Source", null, scrollSource, null);
		JTextPane txtPaneDest = new JTextPane();
		JScrollPane scrollDest = new JScrollPane(txtPaneDest);
		tabbedPane.addTab("Destination", null, scrollDest, null);
		
		
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnCreate){
				System.out.println("Create");
			}else if(e.getSource() == btnClear){
				System.out.println("Clear");
			}else if(e.getSource() == openItem){
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				fc.setFileFilter(new FileNameExtensionFilter("Text", new String []{"txt"}));
				int returnVal = fc.showOpenDialog(null);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            setText(fc.getSelectedFile());
		        } else {
		        	System.out.println("Open command cancelled by user.");
		        }
			}else if(e.getSource() == saveItem){
				System.out.println("Save");
			}else if(e.getSource() == exitItem	){
				System.exit(0);
			}
		}

		private void setText(File file) {
			
		}
	}
}
