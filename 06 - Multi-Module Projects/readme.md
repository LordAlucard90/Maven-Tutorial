# Multi-Module Projects

## Content

- [Structure](#structure)
- [Parent](#parent)
- [First Child Module](#first-child-module)
- [Internal Dependencies](#internal-dependencies)
- [Setting Version](#setting-version)
- [Flatten Plugin](#flatten-plugin)
- [Enforcer Plugin](#enforcer-plugin)
- [BOM](#bom)

---

## Structure

A multi module project is typically composed by a parent POM with the packaging type POM and other sub-module
that are typically JAR, but also POM can be found.

The maven Reactor plugin is responsible to build all the modules of a multi module project.
It will determine the build order of the modules and will run the different lifecycle phases (by default sequentially).

---

## Parent

The parent starting point is:
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>maven.tutorial</groupId>
    <artifactId>multi-parent</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>pom</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>11</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
    </properties>

</project>
```

---

## First Child Module

The sub modules of a module must be specified in the module parent pom's section:
```xml
<modules>
    <module>example-jar</module>
</modules>
```
Then must be created a folder in the main module with that name and in its pom must be placed the parent's coordinates:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>maven.tutorial</groupId>
        <artifactId>multi-parent</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <artifactId>example-jar</artifactId>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.12</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>5.4.21.Final</version>
        </dependency>
    </dependencies>
</project>
```
Now running a lifecycle goal as `clean compile` from the parent (root), it will be executed on the child too.

---

## Internal Dependencies

It is possible to refer from a module to another adding the dependency in the pom:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>maven.tutorial</groupId>
        <artifactId>multi-parent</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <artifactId>example-services</artifactId>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>maven.tutorial</groupId>
            <artifactId>example-entities</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>
</project>
```
In order to compile and build correctly everything can be necessary to run the install phase in order to create the 
dependency in the local repository.

---

## Setting Version

Is is possible to use the revision property in the paren pom to set the same versioning in the parent and in all the sub modules:
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>maven.tutorial</groupId>
    <artifactId>multi-parent</artifactId>
    <version>${revision}</version>
    <packaging>pom</packaging>

    <modules>
        <module>example-entities</module>
        <module>example-services</module>
    </modules>

    <properties>
        <revision>0.0.1-SNAPSHOT</revision>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>11</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
    </properties>

</project>
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>maven.tutorial</groupId>
        <artifactId>multi-parent</artifactId>
        <version>${revision}</version>
    </parent>

    <artifactId>example-entities</artifactId>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.12</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>5.4.21.Final</version>
        </dependency>
    </dependencies>
</project>
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>maven.tutorial</groupId>
        <artifactId>multi-parent</artifactId>
        <version>${revision}</version>
    </parent>

    <artifactId>example-services</artifactId>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>maven.tutorial</groupId>
            <artifactId>example-entities</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>
</project>
```
It is possible to automatically update the cross-dependencies using the project version property:
```xml
<dependency>
    <groupId>maven.tutorial</groupId>
    <artifactId>example-entities</artifactId>
    <version>${project.version}</version>
</dependency>
```

---

## Flatten Plugin

By default, when publishing a maven project with the previous defined properties, these are not overridden in the
installed pom. In order to override this behaviour it is possible to use the Flatten Plugin:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>flatten-maven-plugin</artifactId>
            <version>1.2.5</version>
            <configuration>
                <flattenMode>bom</flattenMode>
            </configuration>
            <executions>
                <execution>
                    <id>flatten</id>
                    <phase>process-resources</phase>
                    <goals>
                        <goal>flatten</goal>
                    </goals>
                </execution>
                <execution>
                    <id>flatten.clean</id>
                    <phase>clean</phase>
                    <goals>
                        <goal>clean</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
The plugin generates a temporary `.flattened-pom.xml` that is used to calculate and replace the properties
that has to be replaced in the main pom.

---

## Enforcer Plugin

The Enforcer plugin is used to set environment variable for the project such as java version or maven version. 
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-enforcer-plugin</artifactId>
    <version>3.0.0-M3</version>
    <executions>
        <execution>
            <id>enforce-env-requirements</id>
            <goals>
                <goal>enforce</goal>
            </goals>
            <configuration>
                <rules>
                    <requireJavaVersion>
                        <version>[11,)</version>
                    </requireJavaVersion>
                    <requireMavenVersion>
                        <version>[3.6,)</version>
                    </requireMavenVersion>
                </rules>
            </configuration>
        </execution>
    </executions>
</plugin>
```

---

## BOM

A Bill of Materials is a special kind of POM used to manage versions' dependencies providing a central place 
where is possible to define and change them.

The main idea is to define all the dependencies needed with the corresponding versions inside the parent
and add the dependencies in the child without the version:
```xml
<dependencyManagement>
   <dependencies>
       <dependency>
           <groupId>org.projectlombok</groupId>
           <artifactId>lombok</artifactId>
           <version>1.18.12</version>
       </dependency>
       <dependency>
           <groupId>org.hibernate</groupId>
           <artifactId>hibernate-core</artifactId>
           <version>5.4.21.Final</version>
       </dependency>
   </dependencies>
</dependencyManagement>
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>maven.tutorial</groupId>
        <artifactId>multi-parent</artifactId>
        <version>${revision}</version>
    </parent>

    <artifactId>example-entities</artifactId>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
        </dependency>
    </dependencies>
</project>
```
