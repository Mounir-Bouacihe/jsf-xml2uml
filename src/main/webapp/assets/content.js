function init(diagramDivId, nodes, links) {
    const $ = go.GraphObject.make;

    const myDiagram = $(go.Diagram, diagramDivId, {
        "undoManager.isEnabled": true,
        layout: $(go.TreeLayout, {
            angle: 90,
            path: go.TreeLayout.PathSource,
            setsPortSpot: false,
            setsChildPortSpot: false,
            arrangement: go.TreeLayout.ArrangementHorizontal
        })
    });

    function convertVisibility(v) {
        switch (v) {
            case "public": return "+";
            case "private": return "-";
            case "protected": return "#";
            case "package": return "~";
            default: return v;
        }
    }

    // the item template for properties
    const propertyTemplate =
        $(go.Panel, "Horizontal",
            // property visibility/access
            $(go.TextBlock,
                {isMultiline: false, editable: true, width: 12},
                new go.Binding("text", "visibility", convertVisibility)),
            // property name, underlined if scope=="class" to indicate static property
            $(go.TextBlock,
                {isMultiline: false, editable: true},
                new go.Binding("text", "name").makeTwoWay(),
                new go.Binding("isUnderline", "scope", function (s) {
                    return s[0] === 'c'
                })),
            // property type, if known
            $(go.TextBlock, "",
                new go.Binding("text", "type", function (t) {
                    return (t ? ": " : ": void");
                })),
            $(go.TextBlock,
                {isMultiline: false, editable: true},
                new go.Binding("text", "type").makeTwoWay()),
            // property default value, if any
            $(go.TextBlock,
                {isMultiline: false, editable: false},
                new go.Binding("text", "default", function (s) {
                    return s ? " = " + s : "";
                }))
        );

    // the item template for methods
    const methodTemplate =
        $(go.Panel, "Horizontal",
            // method visibility/access
            $(go.TextBlock,
                {isMultiline: false, editable: false, width: 12},
                new go.Binding("text", "visibility", convertVisibility)),
            // method name, underlined if scope=="class" to indicate static method
            $(go.TextBlock,
                {isMultiline: false, editable: true},
                new go.Binding("text", "name").makeTwoWay(),
                new go.Binding("isUnderline", "scope", function (s) {
                    return s[0] === 'c'
                })),
            // method parameters
            $(go.TextBlock, "()",
                // this does not permit adding/editing/removing of parameters via in-place edits
                new go.Binding("text", "parameters", function (parr) {
                    let s = "(";
                    for (let i = 0; i < parr.length; i++) {
                        let param = parr[i];
                        if (i > 0) s += ", ";
                        s += param.name + ": " + param.type;
                    }
                    return s + ")";
                })),
            // method return type, if any
            $(go.TextBlock, "",
                new go.Binding("text", "type", function (t) {
                    return (t ? ": " : "");
                })),
            $(go.TextBlock,
                {isMultiline: false, editable: true},
                new go.Binding("text", "type").makeTwoWay())
        );

    // this simple template does not have any buttons to permit adding or
    // removing properties or methods, but it could!
    myDiagram.nodeTemplate = $(go.Node, "Auto",
        {
            locationSpot: go.Spot.Center,
            fromSpot: go.Spot.AllSides,
            toSpot: go.Spot.AllSides
        },
        $(go.Shape, {fill: "lightyellow"}),
        $(go.Panel, "Table",
            {defaultRowSeparatorStroke: "black"},
            // header
            $(go.TextBlock,
                {
                    row: 0, columnSpan: 2, margin: 3, alignment: go.Spot.Center,
                    font: "bold 12pt sans-serif",
                    isMultiline: false, editable: true
                },
                new go.Binding("text", "key").makeTwoWay()),
            // properties
            $(go.TextBlock, "Properties",
                {row: 1, font: "italic 10pt sans-serif"},
                new go.Binding("visible", "visible", function (v) {
                    return !v;
                }).ofObject("PROPERTIES")),
            $(go.Panel, "Vertical", {name: "PROPERTIES"},
                new go.Binding("itemArray", "properties"),
                {
                    row: 1, margin: 3, stretch: go.GraphObject.Fill,
                    defaultAlignment: go.Spot.Left, background: "lightyellow",
                    itemTemplate: propertyTemplate
                }
            ),
            $("PanelExpanderButton", "PROPERTIES",
                {row: 1, column: 1, alignment: go.Spot.TopRight, visible: false},
                new go.Binding("visible", "properties", function (arr) {
                    return arr.length > 0;
                })),
            // methods
            $(go.TextBlock, "Methods",
                {row: 2, font: "italic 10pt sans-serif"},
                new go.Binding("visible", "visible", function (v) {
                    return !v;
                }).ofObject("METHODS")),
            $(go.Panel, "Vertical", {name: "METHODS"},
                new go.Binding("itemArray", "methods"),
                {
                    row: 2, margin: 3, stretch: go.GraphObject.Fill,
                    defaultAlignment: go.Spot.Left, background: "lightyellow",
                    itemTemplate: methodTemplate
                }
            ),
            $("PanelExpanderButton", "METHODS",
                {row: 2, column: 1, alignment: go.Spot.TopRight, visible: false},
                new go.Binding("visible", "methods", function (arr) {
                    return arr.length > 0;
                }))
        )
    );

    function convertIsTreeLink(r) {
        return true;
    }

    function convertFromArrow(r) {
        switch (r) {
            case "heritage":
                return "";
            case "composition":
                return "";
            default:
                return "";
        }
    }

    function convertToArrow(r) {
        switch (r) {
            case "heritage":
                return "Triangle";
            case "aggregation": case "composition":
                return "StretchedDiamond";
            default:
                return "";
        }
    }

    function fillFromArrow(r) {
        return r === "aggregation" ? "black" : "white";
    }

    function fillToArrow(r) {
        return r === "composition" ? "black" : "white";
    }

    myDiagram.linkTemplate = $(go.Link, {routing: go.Link.Orthogonal},
        new go.Binding("isLayoutPositioned", "relationship", convertIsTreeLink),
        $(go.Shape),
        $(go.Shape, {scale: 1.3}, new go.Binding("fill", "relationship", fillFromArrow), new go.Binding("fromArrow", "relationship", convertFromArrow)),
        $(go.TextBlock,  new go.Binding("text", "fromCardinality"), { segmentIndex: 0, segmentOffset: new go.Point(NaN, -20), segmentOrientation: go.Link.None }),
        $(go.TextBlock,  new go.Binding("text", "fromRole"), { segmentIndex: 0, segmentOffset: new go.Point(NaN, 20), segmentOrientation: go.Link.None }),
        $(go.TextBlock, new go.Binding("text", "toCardinality"), { segmentIndex: -1, segmentOffset: new go.Point(NaN, -20), segmentOrientation: go.Link.None }),
        $(go.Shape, {scale: 1.3}, new go.Binding("fill", "relationship", fillToArrow), new go.Binding("toArrow", "relationship", convertToArrow)),
        $(go.TextBlock, new go.Binding("text", "toRole"), { segmentIndex: -1, segmentOffset: new go.Point(NaN, 20), segmentOrientation: go.Link.None }),
    );

    myDiagram.model = $(go.GraphLinksModel, {
        copiesArrays: false,
        copiesArrayObjects: false,
        nodeDataArray: nodes,
        linkDataArray: links
    });
}

const diagramTemplate = {
    classes: [
        {
            key: "Person",
            name: "Person",
            properties: [
                {name: "name", type: "String", visibility: "public"},
                {name: "birth", type: "Date", visibility: "protected"}
            ],
            methods: [
                {name: "getCurrentAge", type: "int", visibility: "public"}
            ]
        },
        {
            key: "Student",
            name: "Student",
            properties: [
                {name: "classes", type: "List<Course>", visibility: "public"}
            ],
            methods: [
                {name: "attend", parameters: [{name: "class", type: "Course"}], visibility: "private"},
                {name: "sleep", visibility: "private"}
            ]
        },
        {
            key: "Teacher",
            name: "Teacher",
            properties: [
                {name: "classes", type: "List<Course>", visibility: "public"}
            ],
            methods: [
                {name: "teach", parameters: [{name: "class", type: "Course"}], visibility: "private"}
            ]
        },
        {
            key: "Course",
            name: "Course",
            properties: [
                {name: "name", type: "String", visibility: "public"},
                {name: "description", type: "String", visibility: "public"},
                {name: "professor", type: "Professor", visibility: "public"},
                {name: "location", type: "String", visibility: "public"},
                {name: "times", type: "List<Time>", visibility: "public"},
                {name: "prerequisites", type: "List<Course>", visibility: "public"},
                {name: "students", type: "List<Student>", visibility: "public"}
            ]
        },
        {
            key: "QCM",
            name: "QCM",
            properties: [
                {name: "questions", type: "List<String>", visibility: "private"},
                {name: "answers", type: "List<String>", visibility: "private"},
                {name: "professor", type: "Professor", visibility: "public"}
            ],
            methods: [
                {name: "getNote", visibility: "public", type: "int"}
            ]
        }
    ],
    associations: [
        {from: "Student", to: "Person", relationship: "heritage",
            fromCardinality: "1..n", toCardinality: "n..1"},
        {from: "Teacher", to: "Person", relationship: "heritage",
            fromRole: "role1", toRole: "role2"},
        {from: "Course", to: "Teacher", relationship: "aggregation"},
        {from: "QCM", to: "Teacher", relationship: "composition"},
    ]
}