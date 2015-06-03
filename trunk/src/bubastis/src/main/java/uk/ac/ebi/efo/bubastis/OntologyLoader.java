/*
 * OntologyLoader class created by James Malone 2008
 * Class to load an OWL ontology into Java + helper methods
 * Requires OWLAPI libraries
 */

package uk.ac.ebi.efo.bubastis;



import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.model.*;

import java.util.Set;


public class OntologyLoader {
	
	//class fields--------------------------------------------------
	private OWLOntologyManager manager;
	private IRI physicalIRI;
	
	//constructor method-------------------------------------------
    public OntologyLoader(OWLOntologyManager manager, IRI loadIRI){
        this.manager = manager;
        this.physicalIRI = loadIRI;
    }

    public OWLOntology loadOntology () throws OWLOntologyCreationException{
        //Now ask the manager to load the ontology
        System.out.println("physical IRI " + this.physicalIRI);
        OWLOntology ontology = this.manager.loadOntology(this.physicalIRI);

        //       loadOntologyFromOntologyDocument(this.physicalIRI);
        System.out.println("loaded ontology 1 in loader ");
        return ontology;

    }
	
	//Method to print out to console all of the classes which are referenced in the ontology
	public void printAllClassNames(OWLOntology ontology){
		int i = 0; 
		for(OWLClass cls : ontology.getClassesInSignature()) {
			System.out.println("Class Name: " + cls);
			
			//print out rdfs label for the class too
			//Firstly, create data factory from the manager then get the IRI for rdfs:label 
			OWLDataFactory df = this.manager.getOWLDataFactory(); 
			OWLAnnotationProperty label = df.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI()); 
			
			//now get all annotations that are rdfs:labels on the class
			for(OWLAnnotation annotation : cls.getAnnotations(ontology, label)) {				
				OWLLiteral val = (OWLLiteral) annotation.getValue();
				System.out.println(val.getLiteral());
			}

			i++;
		}
		System.out.println("TOTAL CLASSES LOADED: " + i);
	}


	

	/*
	 * method for checking the presence of an annotation property for each class
	 * example usage:
	 * 	 checkAnnotationProperty(ontology, OWLRDFVocabulary.RDFS_LABEL.getIRI());
	 *   URI uri = URI.create("http://www.ebi.ac.uk/efo/definition_editor");
	 *   checkAnnotationProperty(ontology, uri);
	 */
	public void checkAnnotationProperty(OWLOntology ontology, IRI propertyType){
			
		//for every class that exists in the ontology
		for(OWLClass cls : ontology.getClassesInSignature()) {
			
			//print out rdfs label for the class too
			//Firstly, create data factory from the manager then get the IRI for rdfs:label 
			OWLDataFactory df = this.manager.getOWLDataFactory(); 
			OWLAnnotationProperty property = df.getOWLAnnotationProperty(propertyType); 
		

			//try to get property for the class
			Set<OWLAnnotation> annot = cls.getAnnotations(ontology, property);
			//if the class does not have this property display warning
			if(annot.size() == 0){		
				System.out.println("Warning: class '" + cls + "' has no property type " + propertyType);
			}
		}
	}

	
}


	
	
	