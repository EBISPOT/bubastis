package org.nfdi4biodiversity.biodivportal.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

public class Configuration {

  protected String ontologies_path;
  protected String results_path;
  protected String onto_release2;
  protected String onto_release1;
  protected String onto_file1;
  protected String onto_file2;
  protected IRI label_property;
  protected IRI synonym_property;


  public Configuration() {

    try {
      loadConfiguration();
    } catch (IOException e) {
      System.err.println("Error reading configuration file");
      e.printStackTrace();
    }
  }



  // public String getOntologiesPath() {
  // return ontologies_path;
  // }

  public String getResultsPath() {
    return results_path;

  }

  public String getOntologyRelease1() {
    return onto_release1;
  }

  public String getOntologyRelease2() {
    return onto_release2;
  }

  public String getOntologyFile1() {
    return onto_file1;
  }

  public String getOntologyFile2() {
    return onto_file2;
  }

  public IRI getLabelProperty() {
    return label_property;
  }

  public IRI getSynonymProperty() {
    return synonym_property;
  }

  protected void loadConfiguration() throws IOException {

    String config_path = System.getProperty("user.dir") + "/configuration/";

    File files = new File(config_path);
    FilenameFilter filter = new FilenameFilter() {
      public boolean accept(File dir, String name) {
        String lowercaseName = name.toLowerCase();
        if (lowercaseName.endsWith(".properties")) {
          return true;
        } else {
          return false;
        }
      }
    };
    String[] tool_files = files.list(filter);


    if (tool_files.length == 1) {

      FileInputStream fileInput = new FileInputStream(new File(config_path + tool_files[0]));
      Properties properties = new Properties();
      properties.load(fileInput);
      fileInput.close();

      ontologies_path = properties.getProperty("ontologies_path");
      onto_release1 = properties.getProperty("onto_release1");
      onto_release2 = properties.getProperty("onto_release2");

      results_path =
          ontologies_path + properties.getProperty("onto_acronym") + "/" + onto_release2 + "/";

      onto_file1 = ontologies_path + properties.getProperty("onto_acronym") + "/" + onto_release1
          + "/" + properties.getProperty("onto_file");
      onto_file2 = ontologies_path + properties.getProperty("onto_acronym") + "/" + onto_release2
          + "/" + properties.getProperty("onto_file");

      if (properties.containsKey("label_property"))
        label_property = IRI.create(properties.getProperty("label_property"));
      else
        label_property = OWLRDFVocabulary.RDFS_LABEL.getIRI();

      synonym_property = IRI.create(properties.getProperty("synonym_property"));


    } else if (tool_files.length > 1) {
      System.err.println("There are more than one configuration file.");
    } else {
      System.err.println("No configuration file available.");
    }


  }

  public static void main(String[] args) {
    Configuration conf = new Configuration();
    System.out.println(conf.getLabelProperty().toString() + " " + conf.getResultsPath());
  }

}
