package bubastiswebcode;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;



import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import uk.ac.ebi.efo.bubastis.CompareOntologies;
import uk.ac.ebi.efo.bubastis.OntologyChangesBean;
import uk.ac.ebi.efo.bubastis.exceptions.Ontology1LoadException;
import uk.ac.ebi.efo.bubastis.exceptions.Ontology2LoadException;

	/**
	 * Servlet implementation class 
	 */
	public class BubastisDiffResults extends HttpServlet {
		private static final long serialVersionUID = 1L;
		private static final int MAX_POST_SIZE = 150*1024*1024;
		OntologyChangesBean changeBean;
		

		public void doGet (HttpServletRequest req, HttpServletResponse res)
	    							throws ServletException, IOException
	    {				
			try{
			
			//set proxy
			System.setProperty("http.proxyHost", "www-proxy.ebi.ac.uk");
			System.setProperty("http.proxyPort", "3128");
			
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();  
			//kill any old bean information lying around
			this.changeBean = new OntologyChangesBean();
		        
			//determine the ContentType
			//if it is URL encoded then load from URLs
			if(req.getContentType().startsWith("application/x-www-form-urlencoded")){
				System.out.println("ContentType:  application/x-www-form-urlencoded");
				
				String ontology1Location = req.getParameter("ontology1url");
				String ontology2Location = req.getParameter("ontology2url");
				
				//if the first url is empty or null then throw an exception and report the problem
				if(ontology1Location.isEmpty() || ontology1Location == null){
					//if they are both null report it 
					if (ontology2Location.isEmpty() || ontology2Location == null){
					System.out.println("the first and second ontology url could not be found: " + ontology1Location);
					this.changeBean.setErrorCause("Ontology 1 and Ontology 2 could not be found - check the URLs are correct");
					throw new NullPointerException();
					}
					//else just the first one is broken report that
					else{
						System.out.println("the first ontology url could not be found: " + ontology1Location);
						this.changeBean.setErrorCause("Ontology 1 could not be found - check the URL is correct");
						this.changeBean.setOntology2Location("Ontology 2 loading not attempted");
						throw new NullPointerException();
					}
				}
				else if(ontology2Location.isEmpty() || ontology2Location == null){
					System.out.println("the second ontology url could not be found: " + ontology1Location);
					this.changeBean.setErrorCause("Ontology 2 could not be found - check the URL is correct");
					this.changeBean.setOntology1Location("Ontology 1 loading not attempted");
					throw new NullPointerException();
				}
				System.out.println("the first ontology location " + ontology1Location);
				System.out.println("the second ontology location " + ontology2Location);
				
				
				//perform ontology change analysis
				this.performDiff(ontology1Location, ontology2Location, req, res);	
				
			}			
			//otherwise if it is multipart and contains at least one file
			else if(req.getContentType().startsWith("multipart/form-data")){
				System.out.println("ContentType: multipart/form-data");			
				String tempDir = System.getProperty("java.io.tmpdir");
				
				System.out.println("Writing loaded files to: " + tempDir);
				//parse the request object into the multi object
				MultipartRequest multi = new MultipartRequest(req,tempDir,MAX_POST_SIZE, new DefaultFileRenamePolicy());			
				
				//begin test to see which parameters have been passed
				//and to determine which combination of URL and file we are dealing with
				if (multi.getParameter("ontology1url") != null){
					System.out.println("First ontology is URL");
					String ontology1URL = multi.getParameter("ontology1url");
					System.out.println("First ontology location = " + ontology1URL);			
					
					//get ontology2 File if it exists
					File ontologyFile2 = multi.getFile("ontology2file");
					if (ontologyFile2 != null){
						System.out.println("length file ontology2: " + ontologyFile2.length());
						
						
						//now do the diff
						this.performDiff(ontology1URL, ontologyFile2, req, res);
						if(ontologyFile2.exists()){
							boolean deleted = ontologyFile2.delete();
							if(deleted) System.out.println("deleted ontologyFile2");
						}
					}
					else {		
						System.out.println("problem reading file 2");
						this.changeBean.setErrorCause("Ontology 2 could not be found");
						throw new NullPointerException();
					}
					
				}
				else if (multi.getParameter("ontology2url") != null){
					System.out.println("Second ontology is URL");
					String ontology2URL = multi.getParameter("ontology2url");
					System.out.println("Second ontology location = " + ontology2URL);
					
					//get ontology1 File if it exists
					File ontologyFile1 = multi.getFile("ontology1file");
					if (ontologyFile1 != null){
						System.out.println("length file ontology1: " + ontologyFile1.length());

						//now do the diff
						this.performDiff(ontologyFile1, ontology2URL, req, res);
						if(ontologyFile1.exists()){
							boolean deleted = ontologyFile1.delete();
							if(deleted) System.out.println("deleted ontologyFile1");
						}
					}
					else {
						System.out.println("problem reading file 1");
						this.changeBean.setErrorCause("File 1 could not be found");
						throw new NullPointerException();
					}
					
				}
				//otherwise both ontology 1 and 2 URLs are null so assume the input is two files and attempt to load
				else{
					System.out.println("Both inputs are files");
					
					//read ontology file 1
					File ontologyFile1 = multi.getFile("ontology1file");
														
					if (ontologyFile1 != null){
						System.out.println("length ontology1: " + ontologyFile1.length());											
					}														
					else {
						System.out.println("problem reading file 1");
						this.changeBean.setErrorCause("File 1 could not be found");
						throw new NullPointerException();
					}					
					
					//read ontolgoy file 2
					File ontologyFile2 = multi.getFile("ontology2file");
					
					if (ontologyFile2 != null){
						System.out.println("length ontology2: " + ontologyFile2.length());

					}
					else {
						System.out.println("problem reading file 2");
						this.changeBean.setErrorCause("File 2 could not be found");
						throw new NullPointerException();
					}
					
					//perform ontology change analysis
					this.performDiff(ontologyFile1, ontologyFile2, req, res);
						
					//clean up the files just read by deleting from temp
					if(ontologyFile1.exists()){
						boolean deleted = ontologyFile1.delete();
						if(deleted) System.out.println("deleted ontologyFile1");
					}
					if(ontologyFile2.exists()){
						boolean deleted = ontologyFile2.delete();
						if(deleted) System.out.println("deleted ontologyFile2");
					}
				}
					
			}
			else {
				System.out.println("Could not determine content type: " + req.getContentType());				
			}
			
			//now forward control to the jsp file for displaying results
	    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/BubastisResultsPage.jsp");
	       	dispatcher.forward(req,res);
	       	
			}
			catch(UnparsableOntologyException e){
				System.out.println("An error occured trying to parse an ontology: " + e.getMessage());
				
				//write the error to the bean for display purposes
				changeBean.setExceptionDuringDiff(e);
				changeBean.setErrorCause("An error occured trying to parse an ontology.\n" + e.getMessage());
				
				//set changeBean for this request so available to jsp
				req.setAttribute("changeBean", changeBean);
				
				//now forward control to the jsp file for displaying error message
		    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/BubastisErrorPage.jsp");
		       	dispatcher.forward(req,res);
				
			}
			catch(IllegalArgumentException e){
				System.out.println("An error occured trying to retrieve a URI that was not valid: " + e.getMessage());
				
				//write the error to the bean for display purposes
				changeBean.setExceptionDuringDiff(e);
				changeBean.setErrorCause("An error occured trying to parse a URI which was not valid - please check. " + e.getMessage());
				
				//set changeBean for this request so available to jsp
				req.setAttribute("changeBean", changeBean);
				
				//now forward control to the jsp file for displaying error message
		    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/BubastisErrorPage.jsp");
		       	dispatcher.forward(req,res);
			}
			catch(NullPointerException e){
				System.out.println("One of the ontologies was not well formed please check" + e.getMessage());
				
				//write the error to the bean for display purposes
				changeBean.setExceptionDuringDiff(e);
				changeBean.setErrorCause("One of the ontologies was not valid - please check the file locations/URLs enetered.");
				
				//set changeBean for this request so available to jsp
				req.setAttribute("changeBean", changeBean);
				
				//now forward control to the jsp file for displaying error message
		    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/BubastisErrorPage.jsp");
		       	dispatcher.forward(req,res);
			}
			catch(Ontology1LoadException e){
				System.out.println("An error occured loading ontology 1 - catching exception " + e.getMessage());
				//write the error to the bean for display purposes
				changeBean.setExceptionDuringDiff(e);
				changeBean.setErrorCause("An error occured trying to load ontology 1 at " + e.getOntologyLocation());
				
				//set changeBean for this request so available to jsp
				req.setAttribute("changeBean", changeBean);
				
				//now forward control to the jsp file for displaying error message
		    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/BubastisErrorPage.jsp");
		       	dispatcher.forward(req,res);
			}
			catch(Ontology2LoadException e){
				System.out.println("An error occured loading ontology 2 - catching exception " + e.getMessage());
				//write the error to the bean for display purposes
				changeBean.setExceptionDuringDiff(e);
				changeBean.setErrorCause("An error occured trying to load ontology 2 at " + e.getOntologyLocation());
				
				//set changeBean for this request so available to jsp
				req.setAttribute("changeBean", changeBean);
				
				//now forward control to the jsp file for displaying error message
		    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/BubastisErrorPage.jsp");
		       	dispatcher.forward(req,res);
			}
			catch(Exception e){
				System.out.println("An error occured in BubastisDiffResults class: " + e.getMessage());
				System.out.println("class: " + e.getClass());
				
				
				//write the error to the bean for display purposes
				changeBean.setExceptionDuringDiff(e);
				changeBean.setErrorCause(e.getMessage());
				
				//set changeBean for this request so available to jsp
				req.setAttribute("changeBean", changeBean);
				
				//now forward control to the jsp file for displaying error message
		    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/BubastisErrorPage.jsp");
		       	dispatcher.forward(req,res);
				
			}

	    }
		

		/**
		 * perform the diff on two ontologies using the CompareOntologies class
		 * write the output of this diff to a class level bean, changeBean which enables
		 * all results to be obtained
		 * 
		 * @param ontology1Location
		 * @param ontology2Location
		 * @param req
		 * @param res
		 * @throws OWLOntologyCreationException 
		 */
		private void performDiff(String ontology1Location, String ontology2Location, HttpServletRequest req,
				HttpServletResponse res) throws OWLOntologyCreationException {
			
			CompareOntologies bubastis = new CompareOntologies();

			bubastis.doFindAllChanges(ontology1Location, ontology2Location);
				
			//after diff is done, make all data accessible to the jsp which will display it
			this.writeDataToBean(req, res, bubastis, ontology1Location, ontology2Location);	
				


		}

		
		/**
		 * perform the diff on two ontologies loaded from local file using the CompareOntologies class
		 * write the output of this diff to a class level bean, changeBean which enables
		 * all results to be obtained
		 * 
		 * @param ontologyFile1 - the location of the first local ontology file
		 * @param ontologyFile2 - the location of the second local ontology file
		 * @param req
		 * @param res
		 * @throws OWLOntologyCreationException 
		 */
		private void performDiff(File ontologyFile1, File ontologyFile2, HttpServletRequest req,
				HttpServletResponse res) throws OWLOntologyCreationException {
			
			CompareOntologies bubastis = new CompareOntologies();
			bubastis.doFindAllChanges(ontologyFile1, ontologyFile2);
			
			String ontology1Location = ontologyFile1.getName();
			String ontology2Location = ontologyFile2.getName();
			//after diff is done, make all data accessible to the jsp which will display it
			this.writeDataToBean(req, res, bubastis, ontology1Location, ontology2Location);	
		}
		
		
		/**
		 * perform the diff on two ontologies first from URL and the second loaded from local file 
		 * using the CompareOntologies class. Write the output of this diff to a class level bean, 
		 * changeBean which enables all results to be obtained
		 * 
		 * @param ontologyFile1 - the location of the first local ontology file
		 * @param ontologyFile2 - the location of the second local ontology file
		 * @param req
		 * @param res
		 * @throws OWLOntologyCreationException 
		 */
		private void performDiff(String ontology1Location, File ontologyFile2, HttpServletRequest req,
				HttpServletResponse res) throws OWLOntologyCreationException {
			
			CompareOntologies bubastis = new CompareOntologies();
			bubastis.doFindAllChanges(ontology1Location, ontologyFile2);
			
			String ontology2Location = ontologyFile2.getName();
			//after diff is done, make all data accessible to the jsp which will display it
			this.writeDataToBean(req, res, bubastis, ontology1Location, ontology2Location);	
		}
		
		
		/**
		 * perform the diff on two ontologies first from from local file, second from URL
		 * using the CompareOntologies class. Write the output of this diff to a class level bean, 
		 * changeBean which enables all results to be obtained
		 * 
		 * @param ontologyFile1 - the location of the first local ontology file
		 * @param ontologyFile2 - the location of the second local ontology file
		 * @param req
		 * @param res
		 * @throws OWLOntologyCreationException 
		 */
		private void performDiff(File ontologyFile1, String ontology2Location, HttpServletRequest req,
				HttpServletResponse res) throws OWLOntologyCreationException {
			
			CompareOntologies bubastis = new CompareOntologies();
			bubastis.doFindAllChanges(ontologyFile1, ontology2Location);
			
			
			String ontology1Location = ontologyFile1.getName();
			//after diff is done, make all data accessible to the jsp which will display it
			this.writeDataToBean(req, res, bubastis, ontology1Location, ontology2Location);	
		}
		
		
		/**
		 * write all the Bubastis results to class variables so that the
		 * class can be used as a Bean by the jsp pages
		 * 
		 * @param req the HttpServletRequest from the calling webpage
		 * @param res the HttpServletResponse from the calling webpage
		 * @param bubastis 
		 */
		private void writeDataToBean(HttpServletRequest req,
				HttpServletResponse res, CompareOntologies bubastis, 
				String ontology1Location, String ontology2Location) {
			
			this.changeBean = bubastis.getOntologyChangesBean();
			
			/*
			//set the two ontology locations
			changeBean.setOntology1Location(ontology1Location);
			changeBean.setOntology2Location(ontology2Location);
						
			//set the ontology classes with differences, new and deleted classes to the bean
			changeBean.setClassesWithDifferences(bubastis.getClassesWithDifferences());
			changeBean.setNewClasses(bubastis.getNewClasses());
			changeBean.setDeletedClasses(bubastis.getDeletedClasses());
			
			
			//add stats for diff
			changeBean.setNumChangedClasses(bubastis.getSizeChangedClasses());
			changeBean.setNumDeletedClasses(bubastis.getSizeDeletedClasses());
			changeBean.setNumNewClasses(bubastis.getSizeNewClasses());
			
			*/
			
			//finally set the bean to the session to make it available to any calling
			//jsp classes
			//HttpSession session = req.getSession();	
			
			//session.setAttribute("changeBean", changeBean);
			req.setAttribute("changeBean", changeBean);
			

			//make results set available to jsp page
			ArrayList<String> resultsSet = changeBean.getClassesWithDifferencesAsXML();
			ArrayList<String> newClassesSet = changeBean.getNewClassesAsXML();
			ArrayList<String> deletedClassesSet = changeBean.getDeletedClassesAsXML();
			
			//session.setAttribute("resultsSet", resultsSet);	
			req.setAttribute("resultsSet", resultsSet);
			req.setAttribute("newClassesSet", newClassesSet);
			req.setAttribute("deletedClassesSet", deletedClassesSet);

		}

		
			
		public void doPost (HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
		{
			doGet(req, res);
		}
			
		
		 public void copyFile(String fromFileName, String toFileName)
	      throws IOException {
	    File fromFile = new File(fromFileName);
	    File toFile = new File(toFileName);

	    if (!fromFile.exists())
	      throw new IOException("FileCopy: " + "no such source file: "
	          + fromFileName);
	    if (!fromFile.isFile())
	      throw new IOException("FileCopy: " + "can't copy directory: "
	          + fromFileName);
	    if (!fromFile.canRead())
	      throw new IOException("FileCopy: " + "source file is unreadable: "
	          + fromFileName);

	    if (toFile.isDirectory())
	      toFile = new File(toFile, fromFile.getName());

	    FileInputStream from = null;
	    FileOutputStream to = null;
	    try {
	      from = new FileInputStream(fromFile);
	      to = new FileOutputStream(toFile);
	      byte[] buffer = new byte[4096];
	      int bytesRead;

	      while ((bytesRead = from.read(buffer)) != -1)
	        to.write(buffer, 0, bytesRead); // write
	    } finally {
	      if (from != null)
	        try {
	          from.close();
	        } catch (IOException e) {
	          ;
	        }
	      if (to != null)
	        try {
	          to.close();
	        } catch (IOException e) {
	          ;
	        }
	    }
	  }
		 




	}


