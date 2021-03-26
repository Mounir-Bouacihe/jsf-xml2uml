package estm.lpdsic.xml2uml.dao;

import java.util.Vector;

public class Test {
    public static void main(String[] args) {
        System.out.println(beautiful("diagram: " + getTestDiagram().toString().replaceAll(" *, *", ",")));
    }

    public static Diagram getTestDiagram() {
        Class c = new Class();
        c.setName("Class1");

        Vector<Attribute> attributes = new Vector<>(1);
        Attribute attribute = new Attribute();
        attribute.setName("attr1");
        attribute.setType("String");
        attribute.setVisibility("private");
        attributes.add(attribute);
        c.setAttributes(attributes);

        Vector<Method> methods = new Vector<>(1);
        Method method = new Method();
        method.setName("method");
        Argument argument = new Argument();
        Vector<Argument> arguments = new Vector<>();
        argument.setName("name");
        argument.setType("String");
        arguments.add(argument);
        method.setArguments(arguments);
        method.setVisibility("public");
        methods.add(method);
        c.setMethods(methods);

        Diagram diagram = new Diagram();
        diagram.setName("Diagram");
        Vector<Class> classes = new Vector<>(1);
        classes.add(c);
        diagram.setClasses(classes);

        return diagram;
    }

    public static String beautiful(String input) {
        int tabCount = 0;

        StringBuilder inputBuilder = new StringBuilder();
        char[] inputChar = input.toCharArray();

        for (int i = 0; i < inputChar.length; i++) {
            String charI = String.valueOf(inputChar[i]);
            if (charI.equals("}") || charI.equals("]")) {
                tabCount--;
                if (!String.valueOf(inputChar[i - 1]).equals("[") && !String.valueOf(inputChar[i - 1]).equals("{"))
                    inputBuilder.append(newLine(tabCount));
            }
            inputBuilder.append(charI);

            if (charI.equals("{") || charI.equals("[")) {
                tabCount++;
                if (String.valueOf(inputChar[i + 1]).equals("]") || String.valueOf(inputChar[i + 1]).equals("}"))
                    continue;

                inputBuilder.append(newLine(tabCount));
            }

            if (charI.equals(",")) {
                inputBuilder.append(newLine(tabCount));
            }
        }

        return inputBuilder.toString();
    }

    private static String newLine(int tabCount) {
        StringBuilder builder = new StringBuilder();

        builder.append("\n");
        for (int j = 0; j < tabCount; j++)
            builder.append("    ");

        return builder.toString();
    }
}
