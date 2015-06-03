package uk.ac.ebi.efo.bubastis;

import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: malone
 * Date: 22/01/14
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public class DriverTest {

    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void testMainLoadUrlFile() throws Exception {
        System.out.println("test one url and one local file");
        String[] args = new String[] {"-ontology2", "http://www.ebi.ac.uk/efo/efo.owl", "-ontology1", "/Users/malone/Documents/efo_oct.owl", "-output", "/Users/malone/Documents/output_diff.txt"};
        Driver.main(args);
    }


    @Test
    public void testNoChangeDiff() throws Exception {
        System.out.println("test two local files");
        String[] args = new String[] {"-ontology1", "/Users/malone/Documents/efo_oct.owl", "-ontology2", "/Users/malone/Documents/efo_oct.owl", "-output", "/Users/malone/Documents/no_change_diff.txt"};
        Driver.main(args);
    }

    @Test
    public void testTwoFileDiff() throws Exception {
        System.out.println("test two local files with differences");
        String[] args = new String[] {"-ontology1", "/Users/malone/Documents/efo_oct.owl", "-ontology2", "/Users/malone/Documents/EFO_inferred.owl", "-output", "/Users/malone/Documents/output_diff_2.txt", "-format", "xml"};
        Driver.main(args);
    }

    @Test
    public void testXmlOutput() throws Exception {
        System.out.println("test one url and one local file");
        String[] args = new String[] {"-ontology2", "http://www.ebi.ac.uk/efo/efo.owl", "-ontology1", "/Users/malone/Documents/efo_oct.owl", "-output", "/Users/malone/Documents/output_diff.txt", "-format", "xml"};
        Driver.main(args);
    }


}
