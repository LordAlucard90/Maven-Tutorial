# Basics

## Content

- [Coordinates](#coordinates)
- [Repositories](#repositories)
- [Wagon](#wagon)
- [Project Object Model](#project-object-model)
- [Dependencies](#dependencies)
- [Standard Directory Layout](#standard-directory-layout)
- [Build LifeCycles](#build-lifecycles)
- [Wrapper](#wrapper)
- [Archetypes](#archetypes)

---

## Coordinates

The maven coordinated include:
- **groupId**: the organization revers domain
- **artifactId**: the project name
- **version**: the version

GroupId and version can be inherited by the parent pom.

Version example `1.2.3-4-beta`:
- Major version `1`
- Minor version `2`
- Patch version `3`
- Build number `4`
- Qualifier `beta`

the `SNAPSHOT` version is used to identify a not stable version.

---

## Repositories

Repositories are locations where project artifacts are stores:
- **Local**: local filesystem
- **Central**: the main Maven community repositories (https://repo1.maven.org/maven2/)
- **Remote**: other public or private remote locations

Remote projects are downloaded and stored in local repositories under `~/.m2/repository/`.

It is possible to browse maven projects at https://mvnrepository.com/.

---

## Wagon

Maven Wagon is an abstract unified Maven layer API used for communications 
with maven repositories.

---

## Project Object Model

Maven Project Object Model (POM) is a xml file which describes a Maven Project.
It follows the rules described in `maven-4.0.0.xsd` file.

Poms can inherit properties from other POMs.

In intellij it is possible to right click on the pom then `Maven` and `Show Effective POM` to see 
how in the end is the effective pom generated. 

---

## Dependencies

A dependency is a artifact from which the project depends on. 

A dependency can bring in a lot of other dependencies on which it depends on, circular dependencies are not allowed.

The dependency management allows to specify the specific artifact version to use.

The dependency mediation helps to determine which dependency use when multiple are found.

It is also possible to exclude or make optional specific dependencies.

The dependency scope defines the scope of each dependency:
- **Compile**: default, available on all classpath, propagated to downstream projects
- **Provided**: as compile but must be provided by the JDK
- **Runtime**: only required at runtime
- **Test**: only available on test classpath, not transitive
- **System**: as provided but jar is added explicitly in the system
- **Import**: import dependencies of POM.

Dependencies are managed by the Maven Dependency Plugin, som goarl are:
- `dependency:tree`: shows all dependency tree
- `dependency:go-offline`: resolve all dependency, prepares to go offline
- `dependency:purge-local-repository`: clears all artifacts from local repository 
- `dependency:sources`: get the sources from all the dependencies

---

## Standard Directory Layout

The standard directory layout is:
- src
    - main
        - java: code
        - resources: configurations and static files
    - test
        - java: tests code
        - resources: configurations and static files for tests

More documentation can be found here: https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html.

---

## Build LifeCycles

Maven Lifecycle is divided by steps called **phases**, to each phase can belong one or more plugins.
All the work made by maven is actually made by its plugins divided in the different phases.

The high level lifecycles are:
- **Clean**: cleans the generated artifacts
- **Validate**: verifies that the project is correct
- **Compile**: compiles the source code
- **Test**: compiles test source code
- **Package**: package compiled files into the packaging type
- **Verify**: runs integration tests
- **Install**: install to local maven repository
- **Deploy**: deploys to shared maven repository


---

## Wrapper

The maven wrapper allows to create local files that can be shared throws a version control system to ensure a 
common maven version when a project is used by different developers and systems.

The command to create them is:
```shell script
mvn -N io.takari:maven:wrapper -Dmaven=3.6.0
```

---

## Archetypes

Maven archetypes are templates for maven projects, a full description can be found here: http://maven.apache.org/guides/introduction/introduction-to-archetypes.html.

The command to generate a `maven-archetype-simple` is:
```shell script
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-simple -DarchetypeVersion=1.4
```
During the generation is it necessary to enter the maven coordinates and the package.

The generated sources are:
```text
.
├── pom.xml
└── src
    ├── main
    │   └── java
    │       └── maven
    │           └── tutorial
    │               └── App.java
    ├── site
    │   └── site.xml
    └── test
        └── java
            └── maven
                └── tutorial
                    └── AppTest.java
```
Where the sources represent a simple hello world.

The same process is integrated already in IntelliJ while creating a new project or module from Maven.

A list of archetypes is here: https://maven.apache.org/archetypes/.
