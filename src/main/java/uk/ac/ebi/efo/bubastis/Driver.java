package uk.ac.ebi.efo.bubastis;


import org.apache.commons.cli.*;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.File;


/**
 * Created with IntelliJ IDEA.
 * User: local_admin
 * Date: 19/11/13
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
class Driver {

    public static void main(String[] args){

        Object ontologyObject1 = new Object();
        Object ontologyObject2 = new Object();

        //create options
        Options options = new Options();

        //locations of the two ontologies are required
        Option ontology1 = new Option("ontology1", true, "ontology 1 location");
        ontology1.setRequired(true);
        Option ontology2 = new Option("ontology2", true, "ontology 2 location");
        ontology2.setRequired(true);
        Option outputFile = new Option("output", true, "output file location");
        Option outputFormat = new Option("format", true, "output format");
        Option xsltPath = new Option("xslt", true, "location of xslt for xml output");

        options.addOption(ontology1);
        options.addOption(ontology2);
        options.addOption(outputFile);
        options.addOption(outputFormat);
        options.addOption(xsltPath);

        //parse arguments and do appropriate diff
        try {

        //create command line parser
        CommandLineParser parser = new GnuParser();

            // parse the command line arguments
            CommandLine line = parser.parse( options, args );

            //for ontology 1 work out if this is a file or a url
            if( line.hasOption( "ontology1" ) ) {
                String ontology1location = line.getOptionValue("ontology1");

                //is ontology 1 a file location
                File file = new File(ontology1location);
                if (file.exists()) {
                    System.out.println("ontology 1 is a file " + ontology1location);
                    ontologyObject1 = new File(ontology1location);
                }
                //if not perhaps a url
                else{
                    UrlValidator urlValidator = new UrlValidator();
                    if(urlValidator.isValid(ontology1location)){
                        System.out.println("ontology 1 is a URL " + ontology1location);
                        ontologyObject1 = ontology1location;
                    }
                    else{
                        throw new ParseException("Ontology 1 is neither a file nor a URL");
                    }
                }
            }

            //for ontology 2 work out if this is a file or a url
            if( line.hasOption( "ontology2" ) ) {
                String ontology2location = line.getOptionValue("ontology2");

                //is ontology 1 a file location
                File file = new File(ontology2location);
                if (file.exists()) {
                    System.out.println("ontology 2 is a file " + ontology2location);
                    ontologyObject2 = new File(ontology2location);
                }
                //if not perhaps a url
                else{
                    UrlValidator urlValidator = new UrlValidator();
                    if(urlValidator.isValid(ontology2location)){
                        System.out.println("ontology 2 is a URL " + ontology2location);
                        ontologyObject2 = ontology2location;
                    }
                    else{
                        throw new ParseException("Ontology 2 is neither a file nor a URL");
                    }
                }
            }

        //do diff
        CompareOntologies comparer = new CompareOntologies();
        if (ontologyObject1 instanceof String){
            System.out.println("Ontology1 is a string");
            if (ontologyObject2 instanceof String){
                //do diff with strings
                comparer.doFindAllChanges(ontologyObject1.toString(), ontologyObject2.toString());
            }
            else{
               //do diff with second ontology as file
                comparer.doFindAllChanges(ontologyObject1.toString(), new File(ontologyObject2.toString()));
            }
        }
        //ontology 1 is a file
        else{

            if (ontologyObject2 instanceof String){
                //do diff with ontology 1 as file, ontology 2 as url
                comparer.doFindAllChanges(new File(ontologyObject1.toString()), ontologyObject2.toString());
            }
            else{
                //do diff with both files
                comparer.doFindAllChanges(new File(ontologyObject1.toString()), new File(ontologyObject2.toString()));


            }
        }


        //write results if a save file location was provided
        if( line.hasOption( "output" ) ) {
            String outputLocation = line.getOptionValue("output");

            //if a format was provided
            if( line.hasOption( "format" ) ) {
                String diffFormat = line.getOptionValue("format").toLowerCase();

                //and the format was xml
                if(diffFormat.matches("xml")){

                    //if an xslt location was provided
                    if( line.hasOption( "xslt" ) ) {
                        String xsltLocation = line.getOptionValue("xslt");

                        //write xml results with xslt location to file
                        comparer.writeDiffAsXMLFile(outputLocation, xsltLocation);
                    }
                    else{
                        //write results without xslt location
                        comparer.writeDiffAsXMLFile(outputLocation);

                    }
                }
            }
            //otherwise use default rendering Manchester syntax in plain text
            else{
                comparer.writeDiffAsTextFile(outputLocation);

            }

        }


        }
        catch( ParseException exp ) {
            // oops, something went wrong
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
        catch(Exception e ){
            System.err.println("Performing diff failed.  Reason: " + e.getMessage());
        }

    }




}
