package estm.lpdsic.xml2uml.ui;

import estm.lpdsic.xml2uml.model.AssociationItem;
import estm.lpdsic.xml2uml.model.Diagram;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

@FacesComponent("DiagramComponent")
public class DiagramComponent extends UIComponentBase implements Serializable {
    public static boolean resourceIncluded(FacesContext context, String resource) throws IOException {
        UIViewRoot viewRoot = context.getViewRoot();

        // Check head
        for (UIComponent uiComponent : viewRoot.getComponentResources(context, "head")) {
            if (resource.equals(uiComponent.getAttributes().get("name"))) {
                return true;
            }
        }

        // Check head
        for (UIComponent uiComponent : viewRoot.getComponentResources(context, "body")) {
            if (resource.equals(uiComponent.getAttributes().get("name"))) {
                return true;
            }
        }

        // Check head
        for (UIComponent uiComponent : viewRoot.getComponentResources(context, "form")) {
            if (resource.equals(uiComponent.getAttributes().get("name"))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getFamily() {
        return "ui.faces.component";
    }

    @Override
    public void encodeBegin(FacesContext context) throws NullPointerException, IOException {
        Diagram diagram;
        if (context == null || (diagram = ((Diagram) getAttributes().get("diagram"))) == null)
            throw new NullPointerException();

        String id = Objects.requireNonNullElse((String) getAttributes().get("id"), "diagram");

        ResponseWriter writer = context.getResponseWriter();

        if (!resourceIncluded(context, "assets/go.min.js")) {
            writer.startElement("script", null);
            writer.writeAttribute("type", "text/javascript", null);
            writer.writeAttribute("src", "assets/go.min.js", null);
            writer.endElement("script");
        }

        if (!resourceIncluded(context, "assets/goClassDiagramModel.js")) {
            writer.startElement("script", null);
            writer.writeAttribute("type", "text/javascript", null);
            writer.writeAttribute("src", "assets/goClassDiagramModel.js", null);
            writer.endElement("script");
        }

        writer.startElement("div", this);
        writer.writeAttribute("id", id, null);
        writer.endElement("div");

        writer.startElement("script", this);
        writer.writeAttribute("type", "text/javascript", null);

        StringBuffer jsObject = new StringBuffer();

        jsObject.append("{name: `").append(diagram.getName()).append("`, classes: [");
        // classes
        diagram.getClasses().forEach(c -> {
            jsObject.append("{name: `").append(c.getName()).append("`,");
            jsObject.append("key: `").append(c.getName()).append("`, attributes: [");
            c.getAttributes().forEach(attr -> {
                jsObject.append("{name: `").append(attr.getName()).append("`, type: `").append(attr.getType())
                        .append("`, visibility: `").append(attr.getVisibility()).append("`");
                if (attr.getDefaultValue() != null)
                    jsObject.append(", default: `").append(attr.getDefaultValue()).append("`");
                jsObject.append("},");
            });

            jsObject.append("], methods: [");
            c.getMethods().forEach(method -> {
                jsObject.append("{name: `").append(method.getName()).append("`, type: `").append(method.getType())
                        .append("`, visibility: `").append(method.getVisibility()).append("`, arguments: [");
                method.getArguments().forEach(arg -> jsObject.append("{name: `").append(arg.getName()).append("`, type: `").append("`},"));
                jsObject.append("]");
                jsObject.append("},");
            });
            jsObject.append("]},");
        });
        jsObject.append("], associations: [");
        diagram.getAssociations().forEach(asc -> {
            AssociationItem i;
            jsObject.append("{relationship: `").append(asc.getType()).append("`,from: `").append((i = asc.getFrom()).getClassName())
                    .append("`, fromCardinality: `").append(Objects.requireNonNullElse(i.getCardinality(), ""))
                    .append("`, fromRole: `").append(Objects.requireNonNullElse(i.getRole(), ""))
                    .append("`, to: `").append((i = asc.getTo()).getClassName())
                    .append("`, toCardinality: `").append(Objects.requireNonNullElse(i.getCardinality(), ""))
                    .append("`, toRole: `").append(Objects.requireNonNullElse(i.getRole(), ""))
                    .append("`},");
        });
        jsObject.append("]}");

        writer.writeText("diagram = "
                        + jsObject.toString().replaceAll(", *}", "}")
                        .replaceAll(", *]", "]"),
                null
        );
        writer.writeText(";init('" + id + "', diagram.classes, diagram.associations);", null);
        writer.endElement("script");
    }
}
