package ReadFile;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.apache.commons.lang.StringUtils; // for String Operation
import org.apache.commons.io.FilenameUtils;
import java.awt.Font;


@SuppressWarnings("serial")
public class mainForm extends JFrame {

	//public String srcDir = "/Users/tandhy/Documents/workspace/CS673_F13/fileToRead";
	//public String extToRead = "java";
	public String strToWrite = "";
	private JPanel contentPane;
	private JTextField txtPath;
	JLabel lblChooseFolderPath = new JLabel("Choose Folder Path");
	JButton cmdGenerate = new JButton("Generate File");
	JCheckBox chkLOC = new JCheckBox("Total Line of Code");
	JCheckBox chkFunction = new JCheckBox("Total Functions");
	JCheckBox chkComment = new JCheckBox("Check Comments");
	private JTextField txtExtension;
	private final JLabel lblOutputFolder = new JLabel("Output Folder");
	private final JTextField txtOutput = new JTextField();
	private final JLabel lblReport = new JLabel("lblReport");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainForm frame = new mainForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*******************************************************
	 * function name 	: createForm
	 * @author tandhy
	 * create date		: 10.15.2013
	 * purpose			: initial create form and component
	 * feature			: none
	 *******************************************************/
	public void createForm()
	{
		setTitle("Read Comment and Line");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblChooseFolderPath.setBounds(6, 30, 136, 16);
		contentPane.add(lblChooseFolderPath);
		
		txtPath = new JTextField();
		txtPath.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtPath.setText("/Users/tandhy/Documents/workspace/CS673_F13/fileToRead");
		txtPath.setBounds(134, 24, 500, 28);
		contentPane.add(txtPath);
		txtPath.setColumns(10);

		txtExtension = new JTextField();
		txtExtension.setText("java");
		txtExtension.setBounds(134, 92, 134, 28);
		contentPane.add(txtExtension);
		txtExtension.setColumns(10);

		cmdGenerate.setBounds(515, 93, 117, 29);
		contentPane.add(cmdGenerate);
		
		chkLOC.setBounds(6, 139, 147, 23);
		contentPane.add(chkLOC);
		
		chkFunction.setBounds(159, 139, 130, 23);
		contentPane.add(chkFunction);
		
		chkComment.setBounds(301, 139, 143, 23);
		contentPane.add(chkComment);
		
		
		JLabel lblExtensionToCheck = new JLabel("Extension to Check");
		lblExtensionToCheck.setBounds(6, 98, 136, 16);
		contentPane.add(lblExtensionToCheck);
		lblOutputFolder.setBounds(6, 64, 117, 16);
		
		contentPane.add(lblOutputFolder);
		txtOutput.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtOutput.setText("/Users/tandhy/Documents/workspace/CS673_F13/codeAnalysisDoc/");
		txtOutput.setBounds(134, 58, 500, 28);
		
		contentPane.add(txtOutput);
		lblReport.setBounds(6, 174, 500, 50);
		lblReport.setText("");
		contentPane.add(lblReport);

		cmdGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try 
				{
					// call readFile
					//String stringToWrite = readFile("/Applications/MAMP/htdocs/test/test4.txt");
					if(!txtPath.getText().isEmpty() && !txtExtension.getText().isEmpty())
					{
						strToWrite = "";
						callListAllFiles(txtPath.getText(),txtExtension.getText(),txtOutput.getText());
						// write to fil
						//writeToFile(stringToWrite);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}	
	
	/*******************************************************
	 * function name 	: writeToFile
	 * author			: tandhy
	 * create date		: 10.16.2013
	 * purpose			: List All Files Recursively
	 * feature			: none
	 * @throws IOException 
	 *******************************************************/
	public void writeToFile(String pathToWrite,String strToWrite,String filenameToWrite)
	{
		try 
		{
		    FileOutputStream fos = new FileOutputStream(pathToWrite + filenameToWrite + ".txt");
		    Writer out = new OutputStreamWriter(fos, "UTF8");
		    out.write(strToWrite);
		    out.close();
		    //System.out.println(filenameToWrite + ".txt written");
		} catch (IOException e) {
		    e.printStackTrace();
		}		
	}
	
	/*******************************************************
	 * function name 	: listAllFilesInFolder
	 * author			: tandhy
	 * create date		: 10.16.2013
	 * purpose			: List All Files Recursively
	 * feature			: none
	 * @throws IOException 
	 *******************************************************/
	public void callListAllFiles(String srcDirectory, String extToRead, String pathToWrite) throws IOException
	{
		final File folder = new File(srcDirectory);
		listAllFilesInFolder(folder,extToRead, pathToWrite,chkComment.isSelected());		
	}
	
	/*******************************************************
	 * function name 	: listAllFilesInFolder
	 * author			: tandhy
	 * create date		: 10.16.2013
	 * purpose			: List All Files Recursively
	 * feature			: none
	 * @throws IOException 
	 *******************************************************/
	public void listAllFilesInFolder(final File folder, String extToRead, String pathToWrite,boolean includeComment) throws IOException 
	{
		//String strToWrite = "";
		int fileCounter = 0;
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	listAllFilesInFolder(fileEntry,extToRead,pathToWrite,includeComment);
	        } else {
	        	// only check predefine extension file
	        	String extension = FilenameUtils.getExtension(fileEntry.getName());
	        	if(extension.contains(extToRead)){
	        		// read the file until finish iteration then save to file
	        		if(includeComment)
	        		{
		        		writeToFile(pathToWrite,readFile(fileEntry.getPath(),FilenameUtils.removeExtension(fileEntry.getName()), extension,includeComment),FilenameUtils.removeExtension(fileEntry.getName()));
	        		}
	        		else
	        		{
		        		//writeToFile(pathToWrite,readFile(fileEntry.getPath(),FilenameUtils.removeExtension(fileEntry.getName()), extension,includeComment),FilenameUtils.removeExtension(fileEntry.getName()));
	        			strToWrite += readFile( fileEntry.getPath(), FilenameUtils.removeExtension(fileEntry.getName()), extension, includeComment );

	        		}
	        		// here is write to File
	        		fileCounter++;
	        	}
	        }
	    }
		// after iteration, write strToWrite to file
		if(!includeComment) 
			writeToFile(pathToWrite, strToWrite, extToRead + "-summary");
		
		//if( fileCounter != 0 ) lblReport.setText("Total File Written : " + fileCounter);
	}
	
	/*******************************************************
	 * function name 	: readFile
	 * author			: tandhy
	 * create date		: 10.15.2013
	 * purpose			: read single file 
	 * feature			: catch IOException and send to output
	 * 					  can read java and html file
	 *******************************************************/
	//public String readFile(String fileName, String fileType) throws IOException
	public String readFile(String fullPathFileName, String fileName, String fileType, boolean includeComment) throws IOException
	{
		//String fileName = "/Users/tandhy/Dropbox/BU/rencana kuliah fall13.txt";
		String line = null, savedFile = "", headerFile = "", fileToWrite = "";
		String needle1 = "";
		String needle2 = "";
		String functionName = "function name";
		
		boolean saveToReadFile = false;
		boolean endComment = false;
		int totalLineInFile = 0, totalLineOfComment = 0, totalFunction = 0, totalEmptyLine = 0;
		
		// check filename type
		if(fileType.contains("java") || fileType.contains("js")){
			needle1 = "/**";
			needle2 = "*/";
		}else{
			needle1 = "<!--";
			needle2 = "-->";
		}
		
		try	
		{	
			// open a file and assigned to reader
			BufferedReader reader = new BufferedReader(new FileReader(fullPathFileName));
			while ((line = reader.readLine()) != null) {
				// this block where the check routine falls
				if(line.contains(needle1)) // check whether needle1 found in line or not
				{
					if(line.contains(needle2))// check whether needle2 found in line or not
					{
						// this is a one line comment, do not need to save
						saveToReadFile = false;
						endComment = false;
					}
					else  // this is not a one line comment, set status to saveToReadFile to True
					{
						saveToReadFile = true;
						endComment = false;
					}
				}
				else // check whether the saveToReadFile is 1. if 1, the line is still one sets with the previous
				{
					// check whether needle2 is IN the line or NOT
					if(line.contains(needle2))
					{
						// if needle2 is IN the line, this is mean that this is the end of the comment.
						saveToReadFile = true;
						endComment = true;
					}
					else
					{
						// if needle2 is NOT IN the line
						if(saveToReadFile) // if the line is continuous comment, save the line
						{
							saveToReadFile = true;
						}
						else // do not save the line
						{
							saveToReadFile = false;
						}
					}
				}
				
				 // check whether line will be saved or not
				if(saveToReadFile)
				{
					// save the line. replace tab space and trim spaces in the beginning of line
					savedFile += StringUtils.stripStart(line.replaceAll("\t",""), " ") + '\n';
					
					totalLineOfComment++; // counting total line of comment
					
					if(endComment) // check whether the line is the end of comment or NOT. 
					{
						// the line IS the end of comment, assign endComment to false and savetoReadFile to false
						endComment = false;
						saveToReadFile = false;
						savedFile = savedFile + '\n'; // separate each comment with empty line
					}
				}
				
				// here is command for checking total number of function
				// check whether the line contain "function name" or not. If so, increase the number of function
				if(line.isEmpty())
				{
					totalEmptyLine++;
				}
				
				// here is command for checking function name
				if(line.toLowerCase().contains(functionName))
				{
					// this is a function
					totalFunction++;
				}
				
			    //System.out.println(line);
				totalLineInFile++; // count total line of file
			} // end of while
						
			// merge information file and savedFile
			//headerFile = "Total Line : " + totalLineInFile + '\n';
			headerFile += "Filename : " + fileName + "." + fileType + '\n';
			//headerFile += "---------------------\n";
			headerFile += "Total Function : " + totalFunction + '\n';
			//headerFile += "Total Line of Comment : " + totalLineOfComment + '\n';
			//headerFile += "Total Line of Empty : " + totalEmptyLine + '\n';
			headerFile += "Total Line of Code : " + (totalLineInFile-totalLineOfComment-totalEmptyLine) + '\n';
			headerFile += "-------------------------------\n";
			
			fileToWrite = headerFile; //+ savedFile;
			if(includeComment) fileToWrite += savedFile;
		}

		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return fileToWrite;
	}

	/**
	 * Create the frame.
	 */
	public mainForm() {
		txtOutput.setColumns(10);
		createForm();
	}
}
