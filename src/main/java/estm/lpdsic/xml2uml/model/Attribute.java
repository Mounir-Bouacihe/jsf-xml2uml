package estm.lpdsic.xml2uml.model;

import java.io.Serializable;

public class Attribute implements Serializable {
    private String name;
    private String type;
    private String defaultValue;
    private String visibility;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    /**
     * @return JSON string
     */
    @Override
    public String toString() {
        return "{" +
            "name: \"" + name + "\", "+
            (type != null ? "type: \"" + type + "\", " : "") +
            (defaultValue != null ? "default: \"" + defaultValue + "\", " : "") +
            (visibility != null ? "visibility: \"" + visibility + "\"" : "") +
        "}";
    }
}
