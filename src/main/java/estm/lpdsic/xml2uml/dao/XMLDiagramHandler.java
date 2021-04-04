package estm.lpdsic.xml2uml.dao;

import estm.lpdsic.xml2uml.model.Class;
import estm.lpdsic.xml2uml.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLDiagramHandler extends DefaultHandler {
    private Diagram diagram;
    private Class aClass;
    private Attribute attribute;
    private Method method;
    private Argument argument;
    private Association association;
    private AssociationItem associationItem;
    private StringBuffer errors;

    @Override
    public void startDocument() throws SAXException {
        diagram = new Diagram();
        errors = new StringBuffer();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "diagram" -> {
                if (attributes.getLength() > 0) diagram.setName(attributes.getValue(0));
            }
            case "class" -> {
                aClass = new Class();
                aClass.setName(attributes.getValue(0));
            }
            case "attribute" -> {
                attribute = new Attribute();
                for (int i = 0; i < attributes.getLength(); i++) {
                    switch (attributes.getQName(i)) {
                        case "name" -> attribute.setName(attributes.getValue(i));
                        case "type" -> attribute.setType(attributes.getValue(i));
                        case "default" -> attribute.setDefaultValue(attributes.getValue(i));
                        case "visibility" -> attribute.setVisibility(attributes.getValue(i));
                    }
                }
            }
            case "method" -> {
                method = new Method();
                for (int i = 0; i < attributes.getLength(); i++) {
                    switch (attributes.getQName(i)) {
                        case "name" -> method.setName(attributes.getValue(i));
                        case "type" -> method.setType(attributes.getValue(i));
                        case "visibility" -> method.setVisibility(attributes.getValue(i));
                    }
                }
            }
            case "argument" -> {
                argument = new Argument();
                for (int i = 0; i < attributes.getLength(); i++) {
                    switch (attributes.getQName(i)) {
                        case "name" -> argument.setName(attributes.getValue(i));
                        case "type" -> argument.setType(attributes.getValue(i));
                    }
                }
            }
            case "association" -> {
                association = new Association();
                association.setType(attributes.getValue(0));
            }
            case "from", "to" -> {
                associationItem = new AssociationItem();
                for (int i = 0; i < attributes.getLength(); i++) {
                    switch (attributes.getQName(i)) {
                        case "class" -> associationItem.setClassName(attributes.getValue(i));
                        case "cardinality" -> associationItem.setCardinality(attributes.getValue(i));
                        case "role" -> associationItem.setRole(attributes.getValue(i));
                    }
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "class" -> diagram.getClasses().add(aClass);
            case "attribute" -> aClass.getAttributes().add(attribute);
            case "method" -> aClass.getMethods().add(method);
            case "argument" -> method.getArguments().add(argument);
            case "association" -> diagram.getAssociations().add(association);
            case "from" -> association.setFrom(associationItem);
            case "to" -> association.setTo(associationItem);
        }
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        errors.append("<div class='warning'>");
        errors.append("<b>at Line").append(e.getLineNumber()).append("<b>: ").append(e.getMessage());
        errors.append("</div>");
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        errors.append("<li class='error'>");
        errors.append("<b>at Line").append(e.getLineNumber()).append("</b>: ").append(e.getMessage());
        errors.append("</li>");
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        error(e);
    }

    public Diagram getDiagram() {
        return diagram;
    }

    public String getErrors() {
        String allErrors = errors.toString();
        return allErrors.length() > 0 ? "<ul class='errors'>" + allErrors + "</ul>" : null;
    }
}
