package estm.lpdsic.xml2uml.model;

import java.io.Serializable;

public class AssociationItem implements Serializable {
    private String className;
    private String cardinality;
    private String role;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCardinality() {
        return cardinality;
    }

    public void setCardinality(String cardinality) {
        this.cardinality = cardinality;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
