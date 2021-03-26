package estm.lpdsic.xml2uml.dao;

import java.io.Serializable;

public class Argument implements Serializable {
    private String name;
    private String type;

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

    @Override
    public String toString() {
        return "{" +
            (name != null ? "name: \"" + name + "\", " : "") +
            (type != null ? "type: \"" + type + "\"" : "") +
        "}";
    }
}
