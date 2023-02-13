package minidb.xmlParser;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import constants.constants;

/**
 * A abstract class with specfic set of methods to avoid repitition of code.
 * @param path
 */
public abstract class XMLFiles {
    protected File xmlFile;
    private static Transformer transformer instance = null;

    public XMLFiles(String path) {
        try {
            xmlFile = new File(path);
            new File(constants.DB_DIR_PATH).mkdir(); // create `db` directory if it doesn't exist
            boolean NoFileFound = xmlFile.createNewFile();
            load(NoFileFound);
        } catch (ParserConfigurationException | SAXException | IOException err) {
            System.out.println(err);
            err.printStackTrace();
        }
    }

    private void load(boolean NoFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        if (NoFile) {
            doc = docBuilder.newDocument();
            createFile(); // abstract method to create the file
        } else {
            doc = docBuilder.parse(xmlFile);
        }
    }



    /**
     * Call this method to update the XML file.
     */
    protected void updateFile() {
        try {
            Transformer transformer = getTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            DOMSource source = new DOMSource(this.doc);
            StreamResult result = new StreamResult(this.xmlFile);
            transformer.transform(source, result);
            print("Updated;");

        } catch (TransformerException err) {
            err.printStackTrace();
        }
    }
    public void getTransformer(){
        if(instance!=null){
            return this.instance;
        }
        instance = TransformerFactory.newInstance().newTransformer();
        return this.instance;
    }

    protected void print(String x) {
        System.out.println(x);
    }
}

