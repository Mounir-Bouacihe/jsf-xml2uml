package estm.lpdsic.xml2uml.beans;

import estm.lpdsic.xml2uml.dao.XMLDiagramHandler;
import org.xml.sax.SAXException;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Paths;

@Named(value = "xmlUtil")
@SessionScoped
public class Util implements Serializable {
    private final SAXParser saxParser;
    private Part uploadedFile;
    private String diagramJsObjectString;
    private String selectedFileName;
    private String errorsHTML;

    public Util() throws ParserConfigurationException, SAXException {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        parserFactory.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(getClass().getResource("/test.xsd")));
        saxParser = parserFactory.newSAXParser();
    }

    public void upload() {
        try {
            File selectedFile = Paths.get(uploadedFile.getSubmittedFileName()).toFile();
            selectedFileName = selectedFile.getName();

            InputStream input = uploadedFile.getInputStream();
            XMLDiagramHandler diagramHandler = new XMLDiagramHandler();

            saxParser.parse(input, diagramHandler);

            diagramJsObjectString = diagramHandler.getDiagram().toString();
            errorsHTML = diagramHandler.getErrors();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDiagramJsObjectString() {
        return diagramJsObjectString;
    }

    public String getErrorsHTML() {
        return errorsHTML;
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
        upload();
    }

    public void setDiagramJsObjectString(String diagramJsObjectString) {
        this.diagramJsObjectString = diagramJsObjectString;
    }

    public void setSelectedFileName(String selectedFileName) {
        this.selectedFileName = selectedFileName;
    }

    public String getSelectedFileName() {
        return selectedFileName;
    }
}
