# Testing

## Content

- [Pojo](#pojo)
- [JUint 5](#junit-5)
- [Other Frameworks And Languages](#other-frameworks-and-languages)
- [Test Reports](#test-reports)
- [Integration Tests](#integration-tests)
- [Coverage](#coverage)
- [SpotBugs](#spotbugs)

---

## Pojo

The Surfire plugin recognises public tests classes ending with `Test` and public void methods starting with `test` as
test to run:
```java
public class HelloWorldPojoTest {
    public void testHello() {
        var hello = new HelloWorld();
        assert "Hello World!".equals(hello.getHello());
    }
}
```

---

## JUnit 5

To run the JUnit tests it is necessary to add the dependencies: 
```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.6.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.22.0</version>
        </plugin>
    </plugins>
</build>
```
The test example is:
```java
class HelloWorldJUnit5Test {
    @Test
    void getHelloTest() {
        var hello = new HelloWorld();
        assertEquals("Hello World!", hello.getHello());
    }

}
```

---

## Other Frameworks And Languages

I'll only document the last JUnit release, the 5 one.

Other tests frameworks and languages in this tutorial are:
- JUnit4
- Groovy
- Spock
- TestNG

---

## Test Reports

In order to generate test reports it is necessary to define a reporting section in the pom with the Surfire Report plugin:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>${maven-surefire-plugin.version}</version>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-site-plugin</artifactId>
            <version>3.8.2</version>
        </plugin>
    </plugins>
</build>

<reporting>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-report-plugin</artifactId>
            <version>2.22.2</version>
        </plugin>
    </plugins>
</reporting>
```
It is possible to see the reports in the site plugin, the report can be then found in the generated site
under `/target/site/index.html` in the reports section.

---

## Integration Tests

The integration tests needs to have in the class name an `IT` prefix or suffix,
the necessary plugin is:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-failsafe-plugin</artifactId>
            <version>2.22.0</version>
            <executions>
                <execution>
                    <goals>
                        <goal>integration-test</goal>
                        <goal>verify</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
Now it is possible to run the integration tests in the verify phase.

---

## Coverage

It is possible to automatically calculate the tests' coverage using the Jacoco plugin:
```xml
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>${maven-surefire-plugin.version}</version>
        <configuration>
            <!--suppress UnresolvedMavenProperty -->
            <argLine>${surefireArgLine}</argLine>
        </configuration>
    </plugin>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-failsafe-plugin</artifactId>
        <version>3.0.0-M3</version>
        <configuration>
            <!--suppress UnresolvedMavenProperty -->
            <argLine>${failsafeArgLine}</argLine>
        </configuration>
        <executions>
            <execution>
                <goals>
                    <goal>integration-test</goal>
                    <goal>verify</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
    <plugin>
        <groupId>org.jacoco</groupId>
        <artifactId>jacoco-maven-plugin</artifactId>
        <version>0.8.5</version>
        <executions>
            <execution>
                <id>pre-unit-test</id>
                <goals>
                    <goal>prepare-agent</goal>
                </goals>
                <configuration>
                    <destFile>${project.build.directory}/coverage-reports/jacoco-ut.exec</destFile>
                    <propertyName>surefireArgLine</propertyName>
                </configuration>
            </execution>
            <execution>
                <id>post-unit-test</id>
                <phase>test</phase>
                <goals>
                    <goal>report</goal>
                </goals>
                <configuration>
                    <dataFile>${project.build.directory}/coverage-reports/jacoco-ut.exec</dataFile>
                    <outputDirectory>${project.reporting.outputDirectory}/jacoco-ut</outputDirectory>
                </configuration>
            </execution>
            <execution>
                <id>pre-integration-test</id>
                <phase>pre-integration-test</phase>
                <goals>
                    <goal>prepare-agent</goal>
                </goals>
                <configuration>
                    <destFile>${project.build.directory}/coverage-reports/jacoco-it.exec</destFile>
                    <propertyName>failsafeArgLine</propertyName>
                </configuration>
            </execution>
            <execution>
                <id>post-integration-test</id>
                <phase>post-integration-test</phase>
                <goals>
                    <goal>report</goal>
                </goals>
                <configuration>
                    <dataFile>${project.build.directory}/coverage-reports/jacoco-it.exec</dataFile>
                    <outputDirectory>${project.reporting.outputDirectory}/jacoco-it</outputDirectory>
                </configuration>
            </execution>
        </executions>
    </plugin>
</plugins>
```
In the verify phase are then generated the jacoco tests reports in the `/target/site` folder.

---

## SpotBugs

There is a useful plugin called SpotBugs that helps to identify bug in the code, the dependency can be included
in the plugins section and in the reports one in order to be displayed in the site:
```xml
<plugin>
    <groupId>com.github.spotbugs</groupId>
    <artifactId>spotbugs-maven-plugin</artifactId>
    <version>3.1.9</version>
</plugin>
```
