package uk.ac.ebi.efo.bubastis.exceptions;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class Ontology2LoadException extends OWLOntologyCreationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2593967113082537923L;
	private String ontologyLocation;
	
	public void setOntologyLocation(String ontologyLocation) {
		this.ontologyLocation = ontologyLocation;
	}
	public String getOntologyLocation() {
		return ontologyLocation;
	}
	

}
