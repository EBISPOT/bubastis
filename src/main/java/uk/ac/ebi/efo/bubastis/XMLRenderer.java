package uk.ac.ebi.efo.bubastis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class XMLRenderer {
	
	
	/**
	 * default constructor
	 */
	public XMLRenderer(){
		
	}
	
	/**
	 * get the diff results as an XML object
	 * 
	 * @param filePath path of file to write XML to 
	 * @param changeBean the bean which holds information on the diff results
	 * @throws IOException 
	 */
	public void writeDiffAsXMLFile(String filePath, OntologyChangesBean changeBean) throws IOException{

		//open stream to write to
		File file = new File(filePath);
		FileWriter fstream = new FileWriter(file, false);
		
		//write xml header
		this.writeXMLHeader(fstream, changeBean);
			
		//write changed classes as xml
		ArrayList<String> changedClasses = changeBean.getClassesWithDifferencesAsXML();
		this.writeListAsXML(fstream, changedClasses);
		
		//write new classes as xml
		ArrayList<String> newClasses = changeBean.getNewClassesAsXML();
		this.writeListAsXML(fstream, newClasses);
		
		//write deleted classes as xml
		ArrayList<String> deletedClasses = changeBean.getDeletedClassesAsXML();
		this.writeListAsXML(fstream, deletedClasses);
		
		this.writeXMLClosingTags(fstream);
		
		//Close the output stream
		fstream.close();
		
	}


    public void writeDiffAsXMLFile(String filePath, OntologyChangesBean changeBean, String pathToXSLT) throws IOException{

        //open stream to write to
        File file = new File(filePath);
        FileWriter fstream = new FileWriter(file, false);

        //write xml header
        this.writeXMLHeaderWithXSLTPath(fstream, changeBean, pathToXSLT);

        //write changed classes as xml
        ArrayList<String> changedClasses = changeBean.getClassesWithDifferencesAsXML();
        this.writeListAsXML(fstream, changedClasses);

        //write new classes as xml
        ArrayList<String> newClasses = changeBean.getNewClassesAsXML();
        this.writeListAsXML(fstream, newClasses);

        //write deleted classes as xml
        ArrayList<String> deletedClasses = changeBean.getDeletedClassesAsXML();
        this.writeListAsXML(fstream, deletedClasses);

        this.writeXMLClosingTags(fstream);

        //Close the output stream
        fstream.close();


    }

    private void writeXMLHeaderWithXSLTPath(FileWriter fs, OntologyChangesBean changeBean, String pathToXSLT) throws IOException{

        fs.write("<?xml version=\"1.0\"?>");
        fs.write("<?xml-stylesheet type=\"text/xsl\" href=\"" + pathToXSLT + "\"?>\n");
        fs.write("\n");
        fs.write("<diffReport>\n");

        fs.write("<diffSummary>\n");
        fs.write("<numberChangedClasses>\n" +
                changeBean.getNumChangedClasses() + "\n" +
                "</numberChangedClasses>\n");
        fs.write("<numberNewClasses>\n" +
                changeBean.getNumNewClasses() + "\n" +
                "</numberNewClasses>\n");
        fs.write("<numberDeletedClasses>\n" +
                changeBean.getNumDeletedClasses() + "\n" +
                "</numberDeletedClasses>\n");
        fs.write("</diffSummary>\n");

    }


    /**
	 * finish the xml properly
	 * 
	 * @param fs file stream to write to
	 * @throws IOException 
	 */
	private void writeXMLClosingTags(FileWriter fs) throws IOException {
		fs.write("</diffReport>");
		
	}

	/**
	 * add header to the xml file
	 * 
	 * @param fs filestream to write to
	 * @throws IOException
	 */
	public void writeXMLHeader(FileWriter fs, OntologyChangesBean changeBean) throws IOException{
		
		fs.write("<?xml version=\"1.0\"?>");
		fs.write("\n");	
		fs.write("<diffReport>\n");
		
		fs.write("<diffSummary>\n");
		fs.write("<numberChangedClasses>\n" +
				changeBean.getNumChangedClasses() + "\n" +
				"</numberChangedClasses>\n"); 
		fs.write("<numberNewClasses>\n" +
				changeBean.getNumNewClasses() + "\n" +
				"</numberNewClasses>\n"); 
		fs.write("<numberDeletedClasses>\n" +
				changeBean.getNumDeletedClasses() + "\n" +
				"</numberDeletedClasses>\n");			
		fs.write("</diffSummary>\n");
	}
	
	
	/**
	 * method to write the report on the new classes as xml
	 * 
	 * @param fs file stream to write to
	 * @param list the classes that are new to the second ontology
	 * @throws IOException 
	 */
	private void writeListAsXML(FileWriter fs, ArrayList<String> list) throws IOException {
		
		for (String line : list) {
			fs.write(line);
			fs.write("\n");
		}
		
	}



}
