package estm.lpdsic.xml2uml.model;

import java.io.Serializable;

public class Association implements Serializable {
    private String type;
    private AssociationItem from, to;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AssociationItem getFrom() {
        return from;
    }

    public void setFrom(AssociationItem from) {
        this.from = from;
    }

    public AssociationItem getTo() {
        return to;
    }

    public void setTo(AssociationItem to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "{" +
            "relationship: \"" + type + "\", " +
            "from: \"" + from.getClassName() + "\", " +
            (from.getRole() != null ? "fromRole: \"" + from.getRole() + "\", " : "") +
            (from.getCardinality() != null ? "fromCardinality: \"" + from.getCardinality() + "\", " : "") +
            "to: \"" + to.getClassName() + "\", " +
            (to.getRole() != null ? "toRole: \"" + to.getRole() + "\", " : "") +
            (to.getCardinality()!= null ? "toCardinality: \"" + to.getCardinality() + "\"" : "") +
        "}";
    }
}
