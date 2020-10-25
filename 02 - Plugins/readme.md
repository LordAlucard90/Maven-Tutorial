# Plugins

## Content

- [Clean Plugin](#clean-plugin)
- [Compiler Plugin](#compiler-plugin)
- [Resources Plugin](#resources-plugin)
- [Surfire Plugin](#surfire-plugin)
- [Jar Plugin](#jar-plugin)
- [Deploy Plugin](#deploy-plugin)
- [Site Plugin](#site-plugin)
- [Other Plugins](#other-plugins)
- [Common Commands](#common-commands)

---

## Clean Plugin

The maven clean plugin is used to remove the generated sources and force them to be regenerated in other stages as compile.

It is possible to force the clean on the other command configuring the pom in this way:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-clean-plugin</artifactId>
            <version>3.1.0</version>
            <executions>
                <execution>
                    <id>auto-clean</id>
                    <phase>initialize</phase>
                    <goals>
                        <goal>clean</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

---

## Compiler Plugin

The maven compile plugin is able to compile the source code for the different java versions.

It can take the property from the pom `<java.version>11</java.version>`.

---

## Resources Plugin

The maven resources plugin is responsible for copying the resources from the project to the target directory.

It is possible to specify in the pom different resources folders but usually is not needed.

---

## Surfire Plugin

The maven surfire plugin is responsible for running the tests.

The most basic configuration is able to find public test classes that end with `Test` and public test methods that start with `test`.

The test reports are stored in the folder `target/surfire-reports`.

---

## Jar Plugin

The maven jar plugin is responsible for the creation of the jar.

It is possible to create an executable jar in this way:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.1.2</version>
            <configuration>
                <archive>
                    <manifest>
                        <addClasspath>true</addClasspath>
                        <mainClass>HelloWorld</mainClass>
                    </manifest>
                </archive>
            </configuration>
        </plugin>
    </plugins>
</build>
```
It is not yet a fat jar, the dependencies are still needed to be present in the same directory.

---

## Deploy Plugin

The maven deploy plugin is used to deploy the artifact to a user defined repository, 
a more detailed section will be present later.

---

## Site Plugin

The maven site plugin generates a web site for the project.

When run it will create a navigable site in the `/target/site` folder with a lot of information like
description, dependencies, information on how to include the project into other projects and so on.

```xml
<build>
    <plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-site-plugin</artifactId>
            <version>3.8.2</version>
        </plugin>
    </plugin>
</build>
```

---

## Other Plugins

The previous plugins are only the core ones but there are much more that can be found in this page: https://maven.apache.org/plugins/index.html.

---

## Common Commands

The main maven syntax is `mvn <plugin>:<goal>` or just `mvn <plugin>`.

it is possible to concatenate plugins in this way: `mvn <plugin_1> <plugin_2>`

