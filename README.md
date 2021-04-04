# XML 2 UML
This is a JSF application for rendering UML class diagram defined with XML, using JS library GoJS.

### Example of XML class diagram (All XML must respect XSD schema.xsd (test.xsd in 1st release ):
<![CDATA[
<diagram name="UML Diagram">
  <class name="Person">
    <attribute name="id" type="String" visibility="private"/>
    <attribute name="firstname" type="String"/>
    <attribute name="lastname" type="String"/>
    <attribute name="address" type="String"/>
    <method name="getFullName" type="String"/>
  </class>
  
  <class name="Teacher"/>
  
  <class name="Student">
    <attribute name="cne" type="String"/>
  </class>
  
  <association type="heritage">
    <from class="Student" role="student"/>
    <to class="Person" />
  </association>
  
  <association type="heritage">
    <from class="Teacher" />
    <to class="Person" />
  </association>
</diagram>
]]>
