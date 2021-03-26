package estm.lpdsic.xml2uml.dao;

import java.io.Serializable;
import java.util.Vector;

public class Method implements Serializable {
    private String name;
    private Vector<Argument> arguments = new Vector<>(0);
    private String type;
    private String visibility;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(Vector<Argument> arguments) {
        this.arguments = arguments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    /**
     * @return JSON string of Method
     */
    @Override
    public String toString() {
        return "{" +
            (name != null ? "name: \"" + name + "\", " : "") +
            (arguments != null ? "parameters: " + arguments +", " : "") +
            (type != null ? "type: \"" + type + "\", " : "") +
            (visibility != null ? "visibility: \"" + visibility + "\"" : "") +
        "}";
    }
}
