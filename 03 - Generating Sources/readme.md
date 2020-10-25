# Generating Sources

## Content

- [XSD To Java](#xsd-to-java)
- [JSON To Java](#json-to-java)

---

## XSD To Java

It is possible to generate automatically java classes starting from a xml configuration like this:
```xml
<?xml version="1.0"?>
<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:jaxb="http://java.sun.com/xml/ns/jaxb" jaxb:version="2.0">

    <!--This tells JAXB what package to create the Java classes in-->
    <xsd:annotation>
        <xsd:appinfo>
            <jaxb:schemaBindings>
                <jaxb:package name="maven.tutorial"/>
            </jaxb:schemaBindings>
        </xsd:appinfo>
    </xsd:annotation>

    <xsd:complexType name="Example">
        <xsd:sequence>
            <xsd:element name="id" type="xsd:long"/>
            <xsd:element name="example" type="xsd:string"/>
        </xsd:sequence>
    </xsd:complexType>

    <xsd:complexType name="CreateExampleRequest">
        <xsd:complexContent>
            <xsd:extension base="Example">
                <xsd:attribute name="apikey" type="xsd:string"/>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>

</xsd:schema>
```

Adding the needed dependencies to the pom:
```xml
<dependencies>
    <dependency>
        <groupId>javax.xml.bind</groupId>
        <artifactId>jaxb-api</artifactId>
        <version>${jaxb.version}</version>
    </dependency>
    <dependency>
        <groupId>com.sun.xml.bind</groupId>
        <artifactId>jaxb-core</artifactId>
        <version>${jaxb.version}</version>
    </dependency>
    <dependency>
        <groupId>com.sun.xml.bind</groupId>
        <artifactId>jaxb-impl</artifactId>
        <version>${jaxb.version}</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.jvnet.jaxb2.maven2</groupId>
            <artifactId>maven-jaxb2-plugin</artifactId>
            <version>0.14.0</version>
            <executions>
                <execution>
                    <goals>
                        <goal>generate</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
It is possible after a compile/package find the generates java sources in the folder `target/generated-sources/xjc`
and added to the final jar.

---

## JSON to Java

it is also possible to generate java classes from a json specification like:
```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "type": "object",
  "properties": {
    "id": {
      "type": "number"
    },
    "example": {
      "type": "string"
    }
  },
  "required": [ "example"]
}
```
The needed dependencies are:
```xml
<dependencies>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.10</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.11.2</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.jsonschema2pojo</groupId>
            <artifactId>jsonschema2pojo-maven-plugin</artifactId>
            <version>0.5.1</version>
            <configuration>
                <sourceDirectory>${basedir}/src/main/resources/schema</sourceDirectory>
                <targetPackage>maven.tutorial.model</targetPackage>
                <useCommonsLang3>true</useCommonsLang3>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>generate</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
The generated classes are in the directory `target/java-gen`. The name of the class is generated from the json filename.

