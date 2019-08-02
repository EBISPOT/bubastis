package uk.ac.ebi.efo.bubastis;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.io.StringWriter;
import java.io.PrintWriter;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.manchestersyntax.renderer.ManchesterOWLSyntaxOWLObjectRendererImpl;
import org.semanticweb.owlapi.manchestersyntax.renderer.ManchesterOWLSyntaxObjectRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;



/*
 * Helper class to store information on a class which can be
 * used to store information about difference between two classes
 * newClassAxiomsSet holds axioms that have been added to this class
 * and deletedClassAxiomsSet holds axioms that have been removed compared with
 * the same class in another ontology
 */


public class OWLClassAxiomsInfo implements Serializable{

	private static final long serialVersionUID = -4287226464192224010L;
	private IRI classIRI;
	private Set<OWLAnnotation> classLabels;
	private Set<OWLClassAxiom> newClassAxiomsSet;
	private Set<OWLClassAxiom> deletedClassAxiomsSet;
	private Set<String> newClassAxiomsAsLabels;
	private Set<String> deletedClassAxiomsAsLabels;

	
	
	//constructor
	public OWLClassAxiomsInfo(IRI classIRI, Set<OWLClassAxiom> newClassAxiomsSet) {
		this.classIRI = classIRI;
		this.newClassAxiomsSet = newClassAxiomsSet;
	}

	
	
	//constructor
	public OWLClassAxiomsInfo(IRI classIRI, Set<OWLClassAxiom> newClassAxiomsSet, 
							  Set<OWLClassAxiom> deletedClassAxiomsSet) {
		this.classIRI = classIRI;
		this.newClassAxiomsSet = newClassAxiomsSet;
		this.deletedClassAxiomsSet = deletedClassAxiomsSet;
	}
	
	
	//constructor
	public OWLClassAxiomsInfo(IRI classIRI, Set<OWLAnnotation> classLabels, Set<OWLClassAxiom> newClassAxiomsSet, 
							  Set<OWLClassAxiom> deletedClassAxiomsSet) {
		this.classIRI = classIRI;
		this.classLabels = classLabels;
		
		this.newClassAxiomsSet = newClassAxiomsSet;
		this.deletedClassAxiomsSet = deletedClassAxiomsSet;
	}
	
	
	//constructor
	public OWLClassAxiomsInfo(IRI classIRI, Set<OWLClassAxiom> newClassAxiomsSet, OWLOntology ontology) {
		this.classIRI = classIRI;
		this.newClassAxiomsSet = newClassAxiomsSet;
		//create the label view of these axioms
		this.newClassAxiomsAsLabels = getAxiomLabels(newClassAxiomsSet, ontology);
		
	}
	
	
	
	//constructor
	public OWLClassAxiomsInfo(IRI classIRI, Set<OWLClassAxiom> newClassAxiomsSet, 
							  Set<OWLClassAxiom> deletedClassAxiomsSet, OWLOntology ontology) {
		this.classIRI = classIRI;
		this.newClassAxiomsSet = newClassAxiomsSet;
		this.deletedClassAxiomsSet = deletedClassAxiomsSet;

        //create shortFormRenderer
        //SimpleLabelProvider renderer = new SimpleLabelProvider();

		//create the label view of these axioms
		this.newClassAxiomsAsLabels = getAxiomLabels(newClassAxiomsSet, ontology);
		this.deletedClassAxiomsAsLabels = getAxiomLabels(deletedClassAxiomsSet, ontology);

	}
	
	
	
	public IRI getIRI(){
		return classIRI;
	}
	
	/** method for returning the rdfs labels of a class as Set of strings rather than OWLAnnotion objects
	 * 
	 * @return the rdfs labels as Set of String objects
	 */
	public Set<String> getLabelsAsString(){
		Set<String> labels = this.getAnnotationsAsStrings(this.classLabels);
		return labels;
	}
	
	
	public Set<OWLAnnotation> getClassLabels(){
		return classLabels;
	}
	
	
	public void setClassLabels(Set<OWLAnnotation> classLabels){
		this.classLabels = classLabels;
	}
	
	
	//get method to return the new class axioms as Axioms
	public Set<OWLClassAxiom> getNewAxioms(){
		return newClassAxiomsSet;
	}
		
	//get method to return the new class axioms with labels
	public Set<String> getNewClassAxiomsAsLabels(){
		return newClassAxiomsAsLabels;
	}
	
	//get method to return the deleted class axioms as Axioms
	public Set<OWLClassAxiom> getDeletedAxioms(){
		return deletedClassAxiomsSet;
	}
	
	//get method to return the deleted class axioms with labels
	public Set<String> getDeletedClassAxiomsAsLabels(){
		return deletedClassAxiomsAsLabels;
	}
	
	
	public String getIRIAsString(){
		String uriAsString = this.classIRI.toString();
		return uriAsString;
	}
	
	//output the axioms as labels, ignoring the URIs
	public void displayClassInfoAsLabels(){
		System.out.println("IRI "+ this.classIRI);
		rdfsLabelWalkthrough(this.classLabels);
		
		//display the added class axioms if there are any
		if (newClassAxiomsAsLabels != null){
			if (!newClassAxiomsAsLabels.isEmpty()){
				setWalkthrough(this.newClassAxiomsAsLabels, "+");
			}
		}	
		
		//display the deleted class axioms if there are any
		if (deletedClassAxiomsAsLabels != null){
			if (!deletedClassAxiomsAsLabels.isEmpty()){
				//display same axioms with labels showing
				setWalkthrough(this.deletedClassAxiomsAsLabels, "-");
			}
		}
	}//end displayClassInfoAsLabels
	
	
	public void displayAllClassInfo(){
		System.out.println("Class ID  "+ this.classIRI);
		rdfsLabelWalkthrough(this.classLabels);
		
		if (newClassAxiomsSet != null){
			if(!newClassAxiomsSet.isEmpty()){
				setWalkthrough(this.newClassAxiomsSet, "+");
				//display same axioms with labels showing
			}
		}
		if (newClassAxiomsAsLabels != null){
			if (!newClassAxiomsAsLabels.isEmpty()){
				System.out.println("As labels");
				setWalkthrough(this.newClassAxiomsAsLabels, "+");
			}
		}	
		if (deletedClassAxiomsSet != null){
			if (!deletedClassAxiomsSet.isEmpty()){
				setWalkthrough(this.deletedClassAxiomsSet, "-");
			}
		}
		if (deletedClassAxiomsAsLabels != null){
			if (!deletedClassAxiomsAsLabels.isEmpty()){
				//display same axioms with labels showing
				System.out.println("As labels");
				setWalkthrough(this.deletedClassAxiomsAsLabels, "-");
			}
		}
	}//end displayAllClassInfo
	
	
	public void rdfsLabelWalkthrough(Set<OWLAnnotation> classLabels) {
		// Create iterator to walk through the elements in the set
	    if (classLabels == null){
	    	System.out.println("-No rdfs label-");
	    }
	    else{
	    	Iterator<OWLAnnotation> it = classLabels.iterator();
	    	while (it.hasNext()) {
	    		// Get element
	    		OWLAnnotation annotation = it.next();
	    		OWLLiteral val = (OWLLiteral) annotation.getValue();
				System.out.println("label: " + val.getLiteral());
	    	}
	    }
	}
	
	
	
	/**
	 * method for returning a set of String objects from a given set of OWLAnnotation objects
	 * useful in particular for displaying purposes
	 * 
	 * @return set of strings derived from the OWLAnnotation objects parameter
	 */
	public Set<String> getAnnotationsAsStrings(Set<OWLAnnotation> classAnnotations){
		
		//initialise the Set for safe returning
		Set<String> annotationsAsString = new HashSet<String>();
		// Create iterator to walk through the elements in the set
	    if (classAnnotations != null){
	    	Iterator<OWLAnnotation> it = classAnnotations.iterator();
	    	while (it.hasNext()) {
	    		// Get element
	    		OWLAnnotation annotation = it.next();

	    		OWLLiteral val = (OWLLiteral) annotation.getValue();
	    		annotationsAsString.add(val.getLiteral());

	    	}
	    }
		return annotationsAsString;
		
	}



	public void setWalkthrough(Set<?> iteratorSet){
		// Create iterator to walk through the elements in the set
	    if (iteratorSet == null){
	    	System.out.println("-Empty-");
	    }
	    else{
	    	Iterator<?> it = iteratorSet.iterator();
	    	while (it.hasNext()) {
	    		// Get element
	    		Object element = it.next();
	    		System.out.println(element);
	    	}
	    }
	}

	public void setWalkthrough(Set<?> iteratorSet, String leadCharacter){
		// Create iterator to walk through the elements in the set
	    if (iteratorSet == null){
	    	System.out.println("-Empty-");
	    }
	    else{
	    	Iterator<?> it = iteratorSet.iterator();
	    	while (it.hasNext()) {
	    		// Get element
	    		Object element = it.next();
	    		System.out.println(leadCharacter + " " + element);
	    	}
	    }
	}


    /**
     * Method to turn a set of class axioms stored as URIs into more readable version
     *
     * @param classAxioms     Set of axioms to turn into readable labels
     * @param ontology        Ontology from which axioms came
     * @return
     */
	public Set<String> getAxiomLabels(Set<OWLClassAxiom> classAxioms, OWLOntology ontology){

		Set<String> axiomsWithLabels = new HashSet<String>();
		// Create iterator to walk through the elements in the set unless there are none
	    if (classAxioms == null){
	    	System.out.println("-Empty-");
	    }
	    else{
	    	Iterator<OWLClassAxiom> it = classAxioms.iterator();
	    	while (it.hasNext()) {
	    		// Get axiom for which we require labels
	    		OWLClassAxiom axiom = it.next();

                SimpleLabelProvider labelProvider = new SimpleLabelProvider(ontology);

                //render it as manchester syntax
				OWLObjectRenderer renManchester = new ManchesterOWLSyntaxOWLObjectRendererImpl();
				renManchester.setShortFormProvider(labelProvider);

                String axiomWithLabels = renManchester.render(axiom);

                //store as a string for doing some regex later
	    		//String axiomWithLabels = axiom.toString();
	    		Set<OWLEntity> refEntities = axiom.getSignature();
	    		
	    		//go through each referenced entity and get label for each of the entities
	    		//referenced in the axiom
	    		Iterator<OWLEntity> it2 = refEntities.iterator();
		    	while (it2.hasNext()) {
		    		//get the OWL entity which is the class ID
		    		OWLEntity e = it2.next();
		    		//get the rdfs label(s) of the class in question, first create IRI for rdfs label
		    		//and create the corresponding OWLAnnotationProperty
		    		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		    		OWLDataFactory df = manager.getOWLDataFactory(); 
					OWLAnnotationProperty label = df.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI()); 
					
		    		Set<OWLAnnotation> entityLabels = new HashSet<>(EntitySearcher.getAnnotations(e,ontology,label));
		    		
		    		//loop through the labels (there could be more than 1)
		    		if (entityLabels != null){
		    			Iterator<OWLAnnotation> it3 = entityLabels.iterator();
		    			while (it3.hasNext()) {
		    				// Get annotations for the class ID
		    				OWLAnnotation annotation = it3.next();

		    				OWLLiteral val = (OWLLiteral) annotation.getValue();

		    				//swap the class ID with the label
		    				String classAsLabel = new String("'" + val.getLiteral() + "'");
		    				axiomWithLabels = axiomWithLabels.replace(e.toString(), classAsLabel);

		    			}
		    			//output the new axiom with labels
			    	}	    		
		    	}
		    	//at this point the pairing would be "axiom" contains the axiom with class IDs
		    	//and axiomWithLables contains the corresponding axiom but with rdfs labels
				//System.out.println("Axiom with labels: " + axiomWithLabels);	
				//add the axiom with labels to the set which will eventually be returned
		    	axiomsWithLabels.add(axiomWithLabels);
	    	}
	    }//end if/else	
	    return axiomsWithLabels;
	    
	}//end method
	



    public class SimpleLabelProvider implements ShortFormProvider{

        //class fields
        OWLOntology ontology;

        //constructor
        public SimpleLabelProvider(OWLOntology ontology){
            this.ontology = ontology;
        }

        @Override
        public String getShortForm(OWLEntity entity) {

            //default for classes without labels is their URI
            String classAsLabel = entity.getIRI().toString();

            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLDataFactory df = manager.getOWLDataFactory();
            OWLAnnotationProperty label = df.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());

            Set<OWLAnnotation> entityLabels = new HashSet<>(EntitySearcher.getAnnotations(entity,ontology,label));

            //loop through the labels (there could be more than 1)
            if (entityLabels != null){
                Iterator<OWLAnnotation> it = entityLabels.iterator();
                while (it.hasNext()) {
                    // Get annotations for the class ID
                    OWLAnnotation annotation = it.next();

                    OWLLiteral val = (OWLLiteral) annotation.getValue();

                    //swap the class ID with the label
                    classAsLabel = new String("'" + val.getLiteral() + "'");


                }
                //output the new axiom with labels
            }

            return classAsLabel;

        }

        @Override
        public void dispose() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

	
}//end of class OWLClassAxiomsInfo
