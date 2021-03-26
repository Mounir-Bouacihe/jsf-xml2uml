package estm.lpdsic.xml2uml.dao;

import java.io.Serializable;
import java.util.Vector;

public class Diagram implements Serializable {
    private String name;
    private Vector<Class> classes = new Vector<>(1);
    private Vector<Association> associations = new Vector<>(0);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<Class> getClasses() {
        return classes;
    }

    public void setClasses(Vector<Class> classes) {
        this.classes = classes;
    }

    public Vector<Association> getAssociations() {
        return associations;
    }

    public void setAssociations(Vector<Association> associations) {
        this.associations = associations;
    }

    @Override
    public String toString() {
        String jsonDiagram = "{" +
            "name: \"" + name + "\", " +
            (classes.size() > 0 ? "classes: " + classes + ", " : "") +
            (associations.size() > 0 ? "associations: " + associations : "") +
        "}";

        return jsonDiagram.replaceAll(", *}", "}");
    }
}
