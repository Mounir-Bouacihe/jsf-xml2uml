package estm.lpdsic.xml2uml.dao;

import java.io.Serializable;
import java.util.Vector;

public class Class implements Serializable {
    private String name;
    private Vector<Attribute> attributes = new Vector<>(1);
    private Vector<Method> methods = new Vector<>(1);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Vector<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Vector<Method> getMethods() {
        return methods;
    }

    public void setMethods(Vector<Method> methods) {
        this.methods = methods;
    }

    /**
     * @return JSON string of Class
     */
    @Override
    public String toString() {
        return "{" +
            "name: \"" + name + "\", " +
            "key: \"" + name + "\", " +
            (attributes != null ? "properties: " + attributes + ", " : "") +
            (methods != null ? "methods: " + methods : "") +
        "}";
    }
}
