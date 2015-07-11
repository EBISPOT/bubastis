package uk.ac.ebi.efo.bubastis;

import java.io.File;
import java.io.IOException;
import java.util.*;


import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import uk.ac.ebi.efo.bubastis.exceptions.Ontology1LoadException;
import uk.ac.ebi.efo.bubastis.exceptions.Ontology2LoadException;
import uk.ac.manchester.cs.owl.owlapi.OWLClassAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLClassExpressionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;


/**
 * @author James Malone
 * @version 1.02
 *          <p/>
 *          Use the CompareOntologies class and subsequent doFindAllChanges methods to invoke a diff
 *          between two ontologies.
 *          Diff results can be output to xml file using the writeDiffAsXMLFile method
 */
public class CompareOntologies {

    //class fields--------------------------------------------------
    private ArrayList<OWLClassAxiomsInfo> classesWithDifferences = new ArrayList<OWLClassAxiomsInfo>();
    private ArrayList<OWLClassAxiomsInfo> newClasses = new ArrayList<OWLClassAxiomsInfo>();
    private ArrayList<OWLClassAxiomsInfo> deletedClasses = new ArrayList<OWLClassAxiomsInfo>();
    private String lineSeparator = System.getProperty("line.separator");
    public OntologyChangesBean changeBean = new OntologyChangesBean();


    /**
     * default constructor
     */
    public void compareOntologies() {
    }


    /**
     * Perform diff on two ontologies supplying just two ontology locations as strings.
     *
     * @param ontology1Location - location of first ontology - the older ontology in most cases
     * @param ontology2Location - location of second ontology - the newer ontology in most cases
     * @throws OWLOntologyCreationException
     */
    public void doFindAllChanges(String ontology1Location, String ontology2Location) throws Ontology1LoadException, Ontology2LoadException {

        //Create 2 OWLOntologyManager which manages a set of ontologies
        //An ontology is unique within an ontology manager.
        //To load multiple copies of an ontology, multiple managers are required.
        OWLOntologyManager manager1 = OWLManager.createOWLOntologyManager();
        OWLOntologyManager manager2 = OWLManager.createOWLOntologyManager();
        OWLOntology ontology1;
        OWLOntology ontology2;


        //load ontology1 from URL using the OntologyLoader class
        //can also load file in form for example: "file:/H://experimentalfactors.owl"
        //attempt to create IRIs
        IRI ontology1IRI = IRI.create(ontology1Location);
        IRI ontology2IRI = IRI.create(ontology2Location);

        //load ontology 1
        try {
            OntologyLoader loader1 = new OntologyLoader(manager1, ontology1IRI);
            ontology1 = loader1.loadOntology();
        } catch (Exception e) {
            Ontology1LoadException loadException = new Ontology1LoadException();
            loadException.setOntologyLocation(ontology1Location);
            throw loadException;
        }
        System.out.println("loading ontology 1 complete");
        changeBean.setOntology1Location(ontology1Location);

        //now load ontology2 from URL using the OntologyLoader class
        //can also load file in form for example: "file:/H://ontologyname.owl"
        try {
            OntologyLoader loader2 = new OntologyLoader(manager2, ontology2IRI);
            ontology2 = loader2.loadOntology();
        } catch (Exception e) {
            Ontology2LoadException loadException = new Ontology2LoadException();
            loadException.setOntologyLocation(ontology2Location);
            throw loadException;
        }
        changeBean.setOntology2Location(ontology2Location);
        System.out.println("loading ontology 2 complete");

        //now chain to doFindAllChanges with new parameters
        this.doFindAllChanges(manager1, manager2,
                ontology1, ontology2);

    }


    /**
     * Perform diff on two ontologies passed as files
     *
     * @param ontologyFile1 - first ontology to be read from file
     * @param ontologyFile2 - second ontology to be read from file
     * @throws Ontology2LoadException
     * @throws Ontology1LoadException
     * @throws OWLOntologyCreationException
     */
    public void doFindAllChanges(File ontologyFile1, File ontologyFile2) throws Ontology1LoadException, Ontology2LoadException {
        //Create 2 OWLOntologyManager which manages a set of ontologies
        //An ontology is unique within an ontology manager.
        //To load multiple copies of an ontology, multiple managers are required.
        OWLOntologyManager manager1 = OWLManager.createOWLOntologyManager();
        OWLOntologyManager manager2 = OWLManager.createOWLOntologyManager();
        OWLOntology ontology1;
        OWLOntology ontology2;

        System.out.println("trying load now");

        //load ontology 1 from file
        try {
            ontology1 = manager1.loadOntologyFromOntologyDocument(ontologyFile1);
        } catch (Exception e) {
            Ontology1LoadException loadException = new Ontology1LoadException();
            loadException.setOntologyLocation(ontologyFile1.toString());
            throw loadException;
        }
        System.out.println("loading ontology 1 complete");
        //set locations of ontologies for later displaying
        changeBean.setOntology1Location(ontologyFile1.toString());

        //load ontology 2 from file
        try {
            ontology2 = manager2.loadOntologyFromOntologyDocument(ontologyFile2);
        } catch (Exception e) {
            Ontology2LoadException loadException = new Ontology2LoadException();
            loadException.setOntologyLocation(ontologyFile2.toString());
            throw loadException;
        }
        System.out.println("loading ontology 2 complete");
        //set locations of ontologies for later displaying
        changeBean.setOntology2Location(ontologyFile2.toString());

        //now chain to doFindAllChanges with new parameters
        this.doFindAllChanges(manager1, manager2,
                ontology1, ontology2);

    }


    /**
     * Perform diff on two ontologies, first passed as a URL, second as a file
     *
     * @param ontology1Location - first ontology to be read from URL
     * @param ontologyFile2     - second ontology to be read from file
     * @throws OWLOntologyCreationException
     */
    public void doFindAllChanges(String ontology1Location, File ontologyFile2) throws OWLOntologyCreationException {
        //set locations of ontologies for later displaying
        changeBean.setOntology1Location(ontology1Location);
        changeBean.setOntology2Location(ontologyFile2.toString());

        //Create 2 OWLOntologyManager which manages a set of ontologies
        //An ontology is unique within an ontology manager.
        //To load multiple copies of an ontology, multiple managers are required.
        OWLOntologyManager manager1 = OWLManager.createOWLOntologyManager();
        OWLOntologyManager manager2 = OWLManager.createOWLOntologyManager();

        System.out.println("trying load now");


        //load ontology1 from URL using the OntologyLoader class
        //can also load file in form for example: "file:/H://experimentalfactors.owl"
        //attempt to create IRI
        IRI ontology1IRI = IRI.create(ontology1Location);

        //load ontology 1
        OntologyLoader loader = new OntologyLoader(manager1, ontology1IRI);
        OWLOntology ontology1 = loader.loadOntology();
        System.out.println("loading ontology 1 complete");
        changeBean.setOntology1Location(ontology1Location);

        //load ontology 2 from file
        OWLOntology ontology2 = manager2.loadOntologyFromOntologyDocument(ontologyFile2);
        System.out.println("loading ontology 2 complete");
        changeBean.setOntology2Location(ontologyFile2.toString());

        //now chain to doFindAllChanges with new parameters
        this.doFindAllChanges(manager1, manager2,
                ontology1, ontology2);


    }


    /**
     * Perform diff on two ontologies, first passed as a URL, second as a file
     *
     * @param ontologyFile1     - first ontology to be read from file
     * @param ontology2Location - second ontology to be read from URL
     * @throws OWLOntologyCreationException
     */
    public void doFindAllChanges(File ontologyFile1, String ontology2Location) throws OWLOntologyCreationException {
        //set locations of ontologies for later displaying
        changeBean.setOntology1Location(ontologyFile1.toString());
        changeBean.setOntology2Location(ontology2Location);

        //Create 2 OWLOntologyManager which manages a set of ontologies
        //An ontology is unique within an ontology manager.
        //To load multiple copies of an ontology, multiple managers are required.
        OWLOntologyManager manager1 = OWLManager.createOWLOntologyManager();
        OWLOntologyManager manager2 = OWLManager.createOWLOntologyManager();

        System.out.println("trying load now");


        //load ontology 1 from file
        OWLOntology ontology1 = manager1.loadOntologyFromOntologyDocument(ontologyFile1);
        System.out.println("loading ontology 1 complete");
        changeBean.setOntology1Location(ontologyFile1.toString());

        //load ontology2 from URL using the OntologyLoader class
        //can also load file in form for example: "file:/H://experimentalfactors.owl"
        //attempt to create IRI
        IRI ontology2IRI = IRI.create(ontology2Location);

        //load ontology 2
        OntologyLoader loader = new OntologyLoader(manager2, ontology2IRI);
        OWLOntology ontology2 = loader.loadOntology();
        System.out.println("loading ontology 2 complete");
        changeBean.setOntology2Location(ontology2Location);

        //now chain to doFindAllChanges with new parameters
        this.doFindAllChanges(manager1, manager2,
                ontology1, ontology2);

    }


    /**
     * Perform diff on two ontologies supplying the two OWLOntologyManager classes and
     * the two OWLOntology classes which have the ontologies pre-loaded
     *
     * @param manager1 ontology manager with the first ontology (ont1) loaded
     * @param manager2 ontology manager with the second ontology (ont2) loaded
     * @param ont1     first ontology to be compared (the older ontology in most cases)
     * @param ont2     second ontology to compare to ont1 (the newer ontology in most cases)
     */
    public void doFindAllChanges(OWLOntologyManager manager1, OWLOntologyManager manager2,
                                 OWLOntology ont1, OWLOntology ont2) {

        this.classesWithDifferences = compareAllClassAxioms(manager1, ont1, manager2, ont2);
        this.newClasses = findNewClasses(manager1, ont1, manager2, ont2);
        this.setDeletedClasses(findDeletedClasses(manager1, ont1, manager2, ont2));

        outputSummaryToConsole(ont1, ont2);
        writeDataToBean();

    }


    /**
     * Method for walking through all the classes in a given ontology and
     * comparing with classes in another given ontology and identifying
     * differences in axioms between any two identical classes - identical here
     * to mean having same class URI
     *
     * @param manager1 ontology manager with the first ontology (ont1) loaded
     * @param manager2 ontology manager with the first ontology (ont2) loaded
     * @param ont1     first ontology to be compared (the older ontology in most cases)
     * @param ont2     second ontology to compare to ont1 (the newer ontology in most cases)
     */
    public ArrayList<OWLClassAxiomsInfo> compareAllClassAxioms(OWLOntologyManager manager1,
                                                               OWLOntology ont1, OWLOntologyManager manager2, OWLOntology ont2) {

        ArrayList<OWLClassAxiomsInfo> classDifferences = new ArrayList<OWLClassAxiomsInfo>();
        //get all classes from first ontology and walk through them
        for (OWLClass ont1Class : ont1.getClassesInSignature()) {

            //try to find the same class from the 2nd ontology
            OWLClass ont2Class = manager2.getOWLDataFactory().getOWLClass(ont1Class.getIRI());

            //get all the axioms from this 1st ontology class
            Set<OWLClassAxiom> ont1ClassAxiomsSet = ont1.getAxioms(ont1Class);
            //get all the axioms from this 2nd ontology class
            Set<OWLClassAxiom> ont2ClassAxiomsSet = ont2.getAxioms(ont2Class);



            //not sure why these are here? JM
            //OWLClass o = new OWLClassImpl();
            //o.getObjectPropertiesInSignature();

            //now compare the two sets of axioms to see if they are different
            boolean setEqual = ont1ClassAxiomsSet.equals(ont2ClassAxiomsSet);

            //if they ARE different then calculate the changes
            if (!setEqual) {
                //calculate new axioms added to ontology 2 with respect to ontology 1
                //i.e. those axioms which appear in ontology 2  but not ontology 1
                Set<OWLClassAxiom> newAxioms = new HashSet<OWLClassAxiom>(ont2ClassAxiomsSet);
                newAxioms.removeAll(ont1ClassAxiomsSet);
                //calculate axioms that have been deleted from ontology 1 with respect to ontology 2
                //i.e. those axioms which appear in ontology 1 but not ontology 2
                Set<OWLClassAxiom> deletedAxioms = new HashSet<OWLClassAxiom>(ont1ClassAxiomsSet);
                deletedAxioms.removeAll(ont2ClassAxiomsSet);

                //create information for the new class
                OWLClassAxiomsInfo tempDiffs = new OWLClassAxiomsInfo(ont1Class.getIRI(), newAxioms, deletedAxioms, ont1);

                //get the rdfs label(s) of the class in question, first create IRI for rdfs label
                //and create the corresponding OWLAnnotationProperty
                OWLDataFactory df = manager1.getOWLDataFactory();
                OWLAnnotationProperty label = df.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());

                Set<OWLAnnotation> classLabels = ont1Class.getAnnotations(ont1, label);
                //add rdfs labels info to the class information
                tempDiffs.setClassLabels(classLabels);

                classDifferences.add(tempDiffs);
            }
        }
        return classDifferences;
    }//end method compareAllClassAxioms


    /**
     * method to find classes that have been deleted, that is classes that appear
     * in ontology 1 but not in ontology 2. It chains to the findNewClasses and
     * simply reverses the order, i.e. classes in ontology 1 but not in ontology 2
     *
     * @param manager1 ontology manager with the first ontology (ont1) loaded
     * @param manager2 ontology manager with the first ontology (ont2) loaded
     * @param ont1     first ontology to be compared (the older ontology in most cases)
     * @param ont2     second ontology to compare to ont1 (the newer ontology in most cases)
     */
    public ArrayList<OWLClassAxiomsInfo> findDeletedClasses(OWLOntologyManager manager1,
                                                            OWLOntology ont1, OWLOntologyManager manager2, OWLOntology ont2) {

        ArrayList<OWLClassAxiomsInfo> deletedClasses = new ArrayList<OWLClassAxiomsInfo>();

        deletedClasses = findNewClasses(manager2, ont2, manager1, ont1);

        return deletedClasses;
    }


    /**
     * method to find classes that are 'new', that is classes that appear
     * in ontology 2 but not in ontology 1
     *
     * @param manager1 ontology manager with the first ontology (ont1) loaded
     * @param manager2 ontology manager with the first ontology (ont2) loaded
     * @param ont1     first ontology to be compared (the older ontology in most cases)
     * @param ont2     second ontology to compare to ont1 (the newer ontology in most cases)
     */
    public ArrayList<OWLClassAxiomsInfo> findNewClasses(OWLOntologyManager manager1,
                                                        OWLOntology ont1, OWLOntologyManager manager2, OWLOntology ont2) {

        ArrayList<OWLClassAxiomsInfo> newClasses = new ArrayList<OWLClassAxiomsInfo>();
        //get all classes from 2nd ontology and walk through them
        for (OWLClass ont2Class : ont2.getClassesInSignature()) {
            //if there is no reference in ontology 1 to the class in ontoloy 2 then it's new
            if (!ont1.containsClassInSignature(ont2Class.getIRI())) {
                Set<OWLClassAxiom> newClassAxiomsSet = ont2.getAxioms(ont2Class);

                //create information for the new class
                OWLClassAxiomsInfo tempNewClass = new OWLClassAxiomsInfo(ont2Class.getIRI(), newClassAxiomsSet, ont2);

                //get the rdfs label(s) of the class in question, first create IRI for rdfs label
                //and create the corresponding OWLAnnotationProperty
                OWLDataFactory df = manager1.getOWLDataFactory();
                OWLAnnotationProperty label = df.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());

                Set<OWLAnnotation> classLabels = ont2Class.getAnnotations(ont2, label);

                //add rdfs labels info to the class information
                tempNewClass.setClassLabels(classLabels);

                newClasses.add(tempNewClass);
                //System.out.println("!!New class!!: " + ont2Class);
                //tempNewClass.displayClassInfo();
            }
        }
        return newClasses;





    }
    //end method findNewClasses


    /**
     * write diffs as an XML file which includes a link to the xsl transform file for rendering the xml
     * if no value or null is entered for pathToXSLT parameter then default xslt file name is written
     *
     * @param filePath
     * @param pathToXSLT
     */
    public void writeDiffAsXMLFile(String filePath, String pathToXSLT) {

        //if the paramater passed is null or empty then use default value
        if (pathToXSLT.isEmpty() || pathToXSLT == null) {
            pathToXSLT = "bubastis_style_info.xslt";
        }

        try {
            //iterate through list, writing to file
            XMLRenderer xmlRenderer = new XMLRenderer();
            xmlRenderer.writeDiffAsXMLFile(filePath, this.changeBean, pathToXSLT);

        } catch (IOException e) {
            System.out.println("An error occurred when attempt to write the diff as XML to a file at: " + filePath);
            e.printStackTrace();
        }

    }

    /**
     * write diffs as an XML file which includes a link to the xsl transform file for rendering the xml
     * if no value or null is entered for pathToXSLT parameter then default xslt file name is written
     *
     * @param filePath Location of file to write results to
     */
    public void writeDiffAsXMLFile(String filePath) {


        try {
            //iterate through list, writing to file
            XMLRenderer xmlRenderer = new XMLRenderer();
            xmlRenderer.writeDiffAsXMLFile(filePath, this.changeBean);

        } catch (IOException e) {
            System.out.println("An error occurred when attempt to write the diff as XML to a file at: " + filePath);
            e.printStackTrace();
        }

    }


    public void writeDiffAsTextFile(String filePath) {


        try {
            //iterate through list, writing to file
            OntologyFileWriter writer = new OntologyFileWriter();
            writer.writeDiffToFile(filePath, this.changeBean);

        } catch (IOException e) {
            System.out.println("An IO error occurred when attempt to write the diff a text to a file at: " + filePath);
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("An error occurred when attempt to write the diff a text to a file at: " + filePath);
            e.printStackTrace();
        }

    }


    /**
     * write all the Bubastis results to class variables so that the
     * class can be used as a Bean by the jsp pages
     */
    private void writeDataToBean() {

        //set the ontology classes with differences, new and deleted classes to the bean
        changeBean.setClassesWithDifferences(this.getClassesWithDifferences());
        changeBean.setNewClasses(this.getNewClasses());
        changeBean.setDeletedClasses(this.getDeletedClasses());

        //add stats for diff
        changeBean.setNumChangedClasses(this.getSizeChangedClasses());
        changeBean.setNumDeletedClasses(this.getSizeDeletedClasses());
        changeBean.setNumNewClasses(this.getSizeNewClasses());
    }


    /**
     * get method to retrieve information about all the classes with differences
     *
     * @return list of classes with information about differences
     */
    public ArrayList<OWLClassAxiomsInfo> getClassesWithDifferences() {
        return classesWithDifferences;
    }


    /**
     * set method, setting information about all the classes with differences
     *
     * @param changedClasses list of information about classes with differences
     */
    public void setClassesWithDifferences(ArrayList<OWLClassAxiomsInfo> changedClasses) {
        this.classesWithDifferences = changedClasses;
    }


    /**
     * get method to retrieve information about all the classes that are new to second ontology
     *
     * @return list of classes with information about the classes new to the second ontology
     */
    public ArrayList<OWLClassAxiomsInfo> getNewClasses() {
        return newClasses;
    }


    /**
     * set method, setting information about all the classes that are new to second ontology
     *
     * @param newClasses list of information about classes new to the second ontology
     */
    public void setNewClasses(ArrayList<OWLClassAxiomsInfo> newClasses) {
        this.newClasses = newClasses;
    }


    /**
     * convenience method to print information on diff to console
     *
     * @param ont1 - first ontology to be read from file
     * @param ont2 - second ontology to be read from URL
     */
    public void outputSummaryToConsole(OWLOntology ont1, OWLOntology ont2) {
        //output summary
        System.out.println();
        System.out.println("@Ontology Change Summary");
        System.out.println("@First ontology IRI: " + ont1.getOntologyID().getOntologyIRI().toString());
        System.out.println("@Second ontology IRI: " + ont2.getOntologyID().getOntologyIRI().toString());
        System.out.println("@Number of classes changed: " + classesWithDifferences.size());
        System.out.println("@Number of classes added: " + newClasses.size());
        System.out.println("@Number of classes deleted: " + deletedClasses.size());

        if (!classesWithDifferences.isEmpty()) {
            System.out.println("\n@Classes modified from previous" + lineSeparator);
            //iterate through and display to screen
            Iterator<OWLClassAxiomsInfo> i = classesWithDifferences.iterator();
            while (i.hasNext()) {
                OWLClassAxiomsInfo classAxiomSet = i.next();
                //if the output is write URIs print them
                System.out.println("---");
                System.out.print("Class changed: ");

                //output labels
                classAxiomSet.displayClassInfoAsLabels();
            }
            System.out.println();
        }

        if (!newClasses.isEmpty()) {
            System.out.println("@New Classes" + lineSeparator);
            //iterate through and display to screen
            Iterator<OWLClassAxiomsInfo> i = newClasses.iterator();
            while (i.hasNext()) {
                OWLClassAxiomsInfo classAxiomSet = i.next();
                System.out.println("---");
                System.out.print("!New class!: ");

                classAxiomSet.displayClassInfoAsLabels();

                System.out.println();
            }
        }

        if (!deletedClasses.isEmpty()) {
            System.out.println("@Deleted Classes" + lineSeparator);
            //iterate through and display to screen
            Iterator<OWLClassAxiomsInfo> i = deletedClasses.iterator();
            while (i.hasNext()) {
                OWLClassAxiomsInfo classAxiomSet = i.next();
                System.out.println("---");
                System.out.print("!Deleted class!: ");

                classAxiomSet.displayClassInfoAsLabels();

                System.out.println();
            }
        }
    }//end outputSummaryToConsole


    /**
     * Store all of the classes that have been deleted between versions
     *
     * @param deletedClasses the deletedClasses to set
     */
    public void setDeletedClasses(ArrayList<OWLClassAxiomsInfo> deletedClasses) {
        this.deletedClasses = deletedClasses;
    }


    /**
     * Get all of the classes that have been deleted between versions
     *
     * @return the deletedClasses
     */
    public ArrayList<OWLClassAxiomsInfo> getDeletedClasses() {
        return deletedClasses;
    }


    /**
     * Return the number (count) of added classes found during the diff
     *
     * @return the number of classes added between versions
     */
    public int getSizeChangedClasses() {
        return classesWithDifferences.size();
    }


    /**
     * Return the number (count) of the new classes found during the diff
     *
     * @return the number of classes new to the second ontology
     */
    public int getSizeNewClasses() {
        return newClasses.size();
    }


    /**
     * Return the number (count) of the deleted classes found during the diff
     *
     * @return number of classes deleted between versions
     */
    public int getSizeDeletedClasses() {
        return deletedClasses.size();
    }


    /**
     * Get the ontology change bean which stores information on the diff
     *
     * @return the bean with all the ontology changes
     */
    public OntologyChangesBean getOntologyChangesBean() {
        return this.changeBean;
    }

}
