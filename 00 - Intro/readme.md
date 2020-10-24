# Intro

## Content

- [Java Compile Process](#java-compile-process)
- [Maven Basics](#maven-basics)
- [Adding Dependencies](#adding-dependencies)

---

## Java Compile Process

Starting from the source files (`.java`, `.groovy`, `.scala`, `.kt`) can be created 
compiled class files (`.class`) that can be run by the JVM runtime.

Class files can be packet with a zip compression into `.jar`, `.war` or `.ear` files. 

Wars and Ears are complete applications that can be deployed to application servers.

Fat jars are jars containing all the dependency needed, including embedded application servers.
Can be deployed as stand alone applications or used in combination with a docker images.

The main commands to compile, run and package a java application are:
```shell script
# compile
javac HelloWorld.java
# run
java HelloWorld 
# package
jar cf hello.jar HelloWorld.class
# run a package
java -classpath hello.jar HelloWorld
```
Unzipping the jar file the content is:
```text
.
├── HelloWorld.class
└── META-INF
    └── MANIFEST.MF
``` 
for the moment the content of the manifest is just:
```text
Manifest-Version: 1.0
Created-By: 11.0.8 (Ubuntu)
```
If and external library is used is needed:
```shell script
javac -classpath ./lib/* HelloWorld.java
java -classpath ./lib/*:./ HelloWorld 
``` 

---

## Maven Basics

Maven uses a xml pom file to manage all the dependencies and info for a project:
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>maven.tutorial</groupId>
    <artifactId>hello-world</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>11</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
    </properties>
</project>
```

The `mvn package` command automatically generates a jar file in a `target/` folder.
In this case the jar is empty because the sources are not places in the right place.

The content of the jar is:
```text
.
└── META-INF
    ├── MANIFEST.MF
    └── maven
        └── maven.tutorial
            └── into
                ├── pom.properties
                └── pom.xml
```
The info contained in the manifest is:
```text
Manifest-Version: 1.0
Archiver-Version: Plexus Archiver
Created-By: Apache Maven 3.6.3
Built-By: LordAlucard90
Build-Jdk: 11.0.7
```

can be used the `mvn clean` command to clean up the generated sources in the target folder.

The correct sources for a Maven project are places in the folder: `./src/main/java`.

Running again the command `mvn clean package` after that the HelloWorld.java file has been copied in the right folder,
the generated jar will contain the compiled sources:
```text
.
├── HelloWorld.class
└── META-INF
    ├── MANIFEST.MF
    └── maven
        └── maven.tutorial
            └── into
                ├── pom.properties
                └── pom.xml

```

---

## Adding Dependencies

In order to add a dependency it is enough to add it in the pom:
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>maven.tutorial</groupId>
    <artifactId>into</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>11</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.10</version>
        </dependency>
    </dependencies>

</project>
```

When packaging the external dependency is not automatically included in the jar.
