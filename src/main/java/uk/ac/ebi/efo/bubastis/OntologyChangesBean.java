package uk.ac.ebi.efo.bubastis;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang3.StringEscapeUtils;
import org.semanticweb.owlapi.model.IRI;


public class OntologyChangesBean {

  private String ontology1Location = "";
  private String ontology2Location = "";
  private ArrayList<OWLClassAxiomsInfo> classesWithDifferences =
      new ArrayList<OWLClassAxiomsInfo>();
  private ArrayList<OWLClassAxiomsInfo> newClasses = new ArrayList<OWLClassAxiomsInfo>();
  private ArrayList<OWLClassAxiomsInfo> deletedClasses = new ArrayList<OWLClassAxiomsInfo>();
  private ArrayList<String> classesWithDifferencesAsXML = new ArrayList<String>();
  private ArrayList<String> newClassesAsXML = new ArrayList<String>();
  private ArrayList<String> deletedClassesAsXML = new ArrayList<String>();
  private int numChangedClasses;
  private int numNewClasses;
  private int numDeletedClasses;
  private Exception exceptionDuringDiff;
  private String errorCause;


  public void setOntology1Location(String ont1) {
    this.ontology1Location = ont1;
  }


  public void setOntology2Location(String ont2) {
    this.ontology2Location = ont2;
  }


  public String getOntology1Location() {
    return this.ontology1Location;
  }


  public String getOntology2Location() {
    return this.ontology2Location;
  }

  public int getNumChangedClasses() {
    return this.numChangedClasses;
  }

  public int getNumNewClasses() {
    return this.numNewClasses;
  }

  public int getNumDeletedClasses() {
    return this.numDeletedClasses;
  }


  public ArrayList<String> getClassesWithDifferencesAsXML() {
    return this.classesWithDifferencesAsXML;
  }

  public ArrayList<String> getNewClassesAsXML() {
    return this.newClassesAsXML;
  }

  public ArrayList<String> getDeletedClassesAsXML() {
    return this.deletedClassesAsXML;

  }



  /**
   * set the classes with differences variable
   * 
   * @param classesWithDifferences the classesWithDifferences to set
   */
  public void setClassesWithDifferences(ArrayList<OWLClassAxiomsInfo> classesWithDifferences) {
    this.classesWithDifferences = classesWithDifferences;
    this.createClassChangesAsXML();
  }



  /**
   * method for formatting the class changes in xml for output to browser
   */
  public void createClassChangesAsXML() {

    // add open xml tag for this type of change
    this.classesWithDifferencesAsXML.add("<changedClasses>");

    // create the classes with difference as strings which is useful for displaying purposes
    // first loop through each OWLClassAxiomsInfo object which represents a single class and its
    // changes
    for (int i = 0; i < this.classesWithDifferences.size(); i++) {
      OWLClassAxiomsInfo singleChangedClass = this.classesWithDifferences.get(i);

      // add open xml tag for this change
      this.classesWithDifferencesAsXML.add("<changedClass>");

      // first get the class URI
      IRI classIRI = singleChangedClass.getIRI();

      // add class URI
      String changesIRIXML = ("<classIRI>" + classIRI.toString() + "</classIRI>");
      // add to list
      this.classesWithDifferencesAsXML.add(changesIRIXML);

      // add labels
      Set<String> classLabels = new HashSet<String>();
      classLabels = singleChangedClass.getLabelsAsString();

      if (!classLabels.isEmpty() && classLabels != null) {
        Iterator<String> it = classLabels.iterator();
        while (it.hasNext()) {
          String label = it.next();
          // strip out any chevrons that owl-api spits out
          // when there is only a uri as this breaks the xml
          label = label.replaceAll("<", "");
          label = label.replaceAll(">", "");

          // check the label for any reserved xml characters and replace them with correct encoding
          String finalLabel = checkForReservedXMLCharacter(label);

          String labelXML = ("<classLabel>" + finalLabel + "</classLabel>");
          // add to list
          this.classesWithDifferencesAsXML.add(labelXML);
        }
      }

      // add deleted annotations
      Set<String> deletedAnnotations = singleChangedClass.getDeletedAnnotations();
      if (deletedAnnotations != null) {
        Iterator<String> it = deletedAnnotations.iterator();
        while (it.hasNext()) {
          String label = it.next();
          // strip out any chevrons that owl-api spits out
          // when there is only a uri as this breaks the xml
          label = label.replaceAll("<", "");
          label = label.replaceAll(">", "");

          // check the label for any reserved xml characters and replace them with correct encoding
          String finalLabel = checkForReservedXMLCharacter(label);

          String annotationXML = ("<deletedAnnotation>" + finalLabel + "</deletedAnnotation>");
          this.classesWithDifferencesAsXML.add(annotationXML);
        }
      }

      // add new annotations
      Set<String> newAnnotations = singleChangedClass.getNewAnnotations();
      if (newAnnotations != null) {
        Iterator<String> it = newAnnotations.iterator();
        while (it.hasNext()) {
          String label = it.next();
          // strip out any chevrons that owl-api spits out
          // when there is only a uri as this breaks the xml
          label = label.replaceAll("<", "");
          label = label.replaceAll(">", "");

          // check the label for any reserved xml characters and replace them with correct encoding
          String finalLabel = checkForReservedXMLCharacter(label);

          String annotationXML = ("<newAnnotation>" + finalLabel + "</newAnnotation>");
          this.classesWithDifferencesAsXML.add(annotationXML);
        }
      }

      // add deleted axioms
      Set<String> deletedAxioms = singleChangedClass.getDeletedClassAxiomsAsLabels();
      if (deletedAxioms != null) {
        Iterator<String> it = deletedAxioms.iterator();
        while (it.hasNext()) {
          String label = it.next();
          // strip out any chevrons that owl-api spits out
          // when there is only a uri as this breaks the xml
          label = label.replaceAll("<", "");
          label = label.replaceAll(">", "");

          // check the label for any reserved xml characters and replace them with correct encoding
          String finalLabel = checkForReservedXMLCharacter(label);

          String axiomXML = ("<deletedAxiom>" + finalLabel + "</deletedAxiom>");
          this.classesWithDifferencesAsXML.add(axiomXML);
        }
      }


      // add new axioms
      Set<String> newAxioms = singleChangedClass.getNewClassAxiomsAsLabels();
      if (newAxioms != null) {
        Iterator<String> it = newAxioms.iterator();
        while (it.hasNext()) {
          String label = it.next();
          // strip out any chevrons that owl-api spits out
          // when there is only a uri as this breaks the xml
          label = label.replaceAll("<", "");
          label = label.replaceAll(">", "");

          // check the label for any reserved xml characters and replace them with correct encoding
          String finalLabel = checkForReservedXMLCharacter(label);

          String axiomXML = ("<newAxiom>" + finalLabel + "</newAxiom>");
          this.classesWithDifferencesAsXML.add(axiomXML);
        }
      }
      // close xml tag for this change
      this.classesWithDifferencesAsXML.add("</changedClass>");
    } // end for loop going through each class

    // add open xml tag for this type of change
    this.classesWithDifferencesAsXML.add("</changedClasses>");
  }

  /**
   * check for reserved XML characters
   *
   * @param unformattedXml
   * @return formattedXml formatted for xml
   */
  private String checkForReservedXMLCharacter(String unformattedXml) {
    String formattedXml = StringEscapeUtils.escapeXml11(unformattedXml);

    return formattedXml;
  }


  /**
   * method to set the new classes added between two ontology
   * 
   * @param newClasses the new classes to set
   */
  public void setNewClasses(ArrayList<OWLClassAxiomsInfo> newClasses) {
    this.newClasses = newClasses;
    this.createNewClassesAsXML();
  }


  /**
   * method for formatting the new classes in xml for output to browser
   */
  public void createNewClassesAsXML() {

    // add open xml tag for this type of change
    this.newClassesAsXML.add("<newClasses>");

    // create the new classes as strings which is useful for displaying purposes
    // first loop through each OWLClassAxiomsInfo object which represents a single class and its
    // changes
    for (int i = 0; i < this.newClasses.size(); i++) {
      OWLClassAxiomsInfo singleClass = this.newClasses.get(i);

      // add open xml tag for this change
      this.newClassesAsXML.add("<newClass>");

      // first get the class URI
      IRI classIRI = singleClass.getIRI();

      // add class IRI
      String changesIRIXML = ("<classIRI>" + classIRI.toString() + "</classIRI>");
      // add to list
      this.newClassesAsXML.add(changesIRIXML);

      // add labels
      Set<String> classLabels = new HashSet<String>();
      classLabels = singleClass.getLabelsAsString();
      if (!classLabels.isEmpty() && classLabels != null) {
        Iterator<String> it = classLabels.iterator();
        while (it.hasNext()) {
          String label = it.next();
          // strip out any chevrons that owl-api spits out
          // when there is only a uri as this breaks the xml
          label = label.replaceAll("<", "");
          label = label.replaceAll(">", "");

          String labelXML = ("<classLabel>" + label + "</classLabel>");
          // add to list
          this.newClassesAsXML.add(labelXML);
        }
      }



      // add deleted axioms
      Set<String> deletedAxioms = singleClass.getDeletedClassAxiomsAsLabels();
      if (deletedAxioms != null) {
        Iterator<String> it = deletedAxioms.iterator();
        while (it.hasNext()) {
          String label = it.next();
          // strip out any chevrons that owl-api spits out
          // when there is only a uri as this breaks the xml
          label = label.replaceAll("<", "");
          label = label.replaceAll(">", "");

          String axiomXML = ("<deletedAxiom>" + label + "</deletedAxiom>");
          this.newClassesAsXML.add(axiomXML);
        }
      }


      // add new axioms
      Set<String> newAxioms = singleClass.getNewClassAxiomsAsLabels();
      if (newAxioms != null) {
        Iterator<String> it = newAxioms.iterator();
        while (it.hasNext()) {
          String label = it.next();
          // strip out any chevrons that owl-api spits out
          // when there is only a uri as this breaks the xml
          label = label.replaceAll("<", "");
          label = label.replaceAll(">", "");

          String axiomXML = ("<newAxiom>" + label + "</newAxiom>");
          this.newClassesAsXML.add(axiomXML);
        }
      }
      // close xml tag for this new class
      this.newClassesAsXML.add("</newClass>");
    } // end for loop going through each class

    // add closing xml tag for this type of change
    this.newClassesAsXML.add("</newClasses>");
  }


  /**
   * method to set the new classes added between two ontology
   * 
   * @param deletedClasses the deleted classes to set in the bean
   */
  public void setDeletedClasses(ArrayList<OWLClassAxiomsInfo> deletedClasses) {
    this.deletedClasses = deletedClasses;
    this.createDeletedClassesAsXML();
  }


  /**
   * method for formatting the deleted classes in xml for output to browser
   */
  public void createDeletedClassesAsXML() {

    // add open xml tag for this type of change
    this.deletedClassesAsXML.add("<deletedClasses>");

    // create the new classes as strings which is useful for displaying purposes
    // first loop through each OWLClassAxiomsInfo object which represents a single class and its
    // changes
    for (int i = 0; i < this.deletedClasses.size(); i++) {
      OWLClassAxiomsInfo singleClass = this.deletedClasses.get(i);

      // add open xml tag for this change
      this.deletedClassesAsXML.add("<deletedClass>");

      // first get the class URI
      IRI classIRI = singleClass.getIRI();

      // add class IRI
      String changesIRIXML = ("<classIRI>" + classIRI.toString() + "</classIRI>");
      // add to list
      this.deletedClassesAsXML.add(changesIRIXML);

      // add labels
      Set<String> classLabels = singleClass.getLabelsAsString();
      if (classLabels != null) {
        Iterator<String> it = classLabels.iterator();
        while (it.hasNext()) {
          String label = it.next();
          // strip out any chevrons that owl-api spits out
          // when there is only a uri as this breaks the xml
          label = label.replaceAll("<", "");
          label = label.replaceAll(">", "");

          String labelXML = ("<classLabel>" + label + "</classLabel>");
          // add to list
          this.deletedClassesAsXML.add(labelXML);
        }
      }

      // add deleted axioms
      Set<String> deletedAxioms = singleClass.getDeletedClassAxiomsAsLabels();
      if (deletedAxioms != null) {
        Iterator<String> it = deletedAxioms.iterator();
        while (it.hasNext()) {
          String label = it.next();
          // strip out any chevrons that owl-api spits out
          // when there is only a uri as this breaks the xml
          label = label.replaceAll("<", "");
          label = label.replaceAll(">", "");

          String axiomXML = ("<deletedAxiom>" + label + "</deletedAxiom>");
          this.deletedClassesAsXML.add(axiomXML);
        }
      }


      // add new axioms
      Set<String> newAxioms = singleClass.getNewClassAxiomsAsLabels();
      if (newAxioms != null) {
        Iterator<String> it = newAxioms.iterator();
        while (it.hasNext()) {
          String label = it.next();
          // strip out any chevrons that owl-api spits out
          // when there is only a uri as this breaks the xml
          label = label.replaceAll("<", "");
          label = label.replaceAll(">", "");

          String axiomXML = ("<newAxiom>" + label + "</newAxiom>");
          this.deletedClassesAsXML.add(axiomXML);
        }
      }
      // close xml tag for this change
      this.deletedClassesAsXML.add("</deletedClass>");

    } // end for loop going through each class
      // add open xml tag for this type of change
    this.deletedClassesAsXML.add("</deletedClasses>");
  }



  /**
   * @return the classesWithDifferences
   */
  public ArrayList<OWLClassAxiomsInfo> getClassesWithDifferences() {
    return classesWithDifferences;
  }



  /**
   * @return the newClasses
   */
  public ArrayList<OWLClassAxiomsInfo> getNewClasses() {
    return newClasses;
  }



  /**
   * @return the deletedClasses
   */
  public ArrayList<OWLClassAxiomsInfo> getDeletedClasses() {
    return deletedClasses;
  }


  public void setNumChangedClasses(int i) {
    this.numChangedClasses = i;
  }

  public void setNumNewClasses(int i) {
    this.numNewClasses = i;
  }

  public void setNumDeletedClasses(int i) {
    this.numDeletedClasses = i;
  }


  public void setExceptionDuringDiff(Exception exceptionDuringDiff) {
    this.exceptionDuringDiff = exceptionDuringDiff;
  }


  public Exception getExceptionDuringDiff() {
    return exceptionDuringDiff;
  }


  public void setErrorCause(String errorCause) {
    this.errorCause = errorCause;
  }


  public String getErrorCause() {
    return errorCause;
  }


}
