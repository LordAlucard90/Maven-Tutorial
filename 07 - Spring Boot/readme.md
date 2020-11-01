# Spring Boot

## Content

- [Intro](#intro)
- [Fat Jar](#fat-jar)
- [Plugins](#plugins)
- [Multi-Module Project](#multi-module-project)

---

## Intro

It is possible to create automatically a spring project using [Spring Initializr](https://start.spring.io/) or
the intellij create project process.

The Spring parent defines all the dependencies of its all sub-modules, this allows not defining the single version
when including them in the current project.

---

## Fat Jar

When a spring project is compiled with maven is generated a fat jar that includes all the needed dependencies and 
a `original.jar` that is the normal jar generated.

---

## Plugins

Spring defines an additional plugin at maven, the spring-boot plugin. 
Its goals are:
- **build-info**: creates a OCI image
- **build-image**: generates a `build-info.properties`
- **help**: shows the help info
- **repackage**: repackages the existing JARs and WARs
- **run**: compiles and runs the application
- **start**: runs an already packaged project in demon mode
- **stop**: stops an application in demon mode

In order to add the build info in the `/actuator/info` endpoint it is possible to set the spring-boot plugin in this way:
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <executions>
        <execution>
            <id>info</id>
            <goals>
                <goal>build-info</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
It is also possible add custom info:
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <executions>
        <execution>
            <id>info</id>
            <goals>
                <goal>build-info</goal>
            </goals>
            <configuration>
                <additionalProperties>
                    <java.version>${java.version}</java.version>
                    <spring.version>${parent.version}</spring.version>
                </additionalProperties>
            </configuration>
        </execution>
    </executions>
</plugin>
```
The properties are showed after the package phase.

Additional info on the git branch/commit/status can be displayed in this endpoint adding another plugin:
```xml
<plugin>
    <groupId>pl.project13.maven</groupId>
    <artifactId>git-commit-id-plugin</artifactId>
    <version>3.0.1</version>
</plugin>
```
and adding in the properties:
```yaml
management.info.git.mode: full
```

---

## Multi-Module Project

In a multi module project spring boot it is possible to add at the parent level the spring boot plugin
but if a module must not be repackaged because for example has not yet classes or whatever, it can be set
in the properties `<spring-boot.repackage.skip>true</spring-boot.repackage.skip>` in order to do not make fail the build.
