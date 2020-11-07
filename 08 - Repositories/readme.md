# Repositories 

## Content

- [Repository](#repository)
- [Maven Settings](#maven-settings)
- [Manually Install Dependencies](#manually-install-dependencies)
- [Repository Authentication](#repository-authentication)
- [Nexus](#nexus)
- [Build Profiles](#build-profiles)

---

## Repository

A Repository is composed by:
- **id**: unique
- **name**: the name
- **url**: repository url
- **layout**: legacy or default
- **releases**: policy for releases download
- **snapshots**: policu for snapshots download

The releases and snapshot policies are:
- **enable**: true/false
- **updatePolicy**: always / daily / interval:<minures> / never
- **checksumPolicy**: ignore / fail / warn

It is possible to update the repository reference in the main `settings.xml` place in the in the main `settings.xml` 
placed in the `.m2` directory in the uses home.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

    <mirrors>
        <mirror>
            <id>UK</id>
            <name>UK Central</name>
            <url>http://uk.maven.org/maven2</url>
            <mirrorOf>central</mirrorOf>
        </mirror>
    </mirrors>
</settings>
```

---

## Maven Settings

It is possible to setup user specific settings in `<user_home>/.m2/settings.xml` or global ones in `/conf/settings.xml`.

The different settings elements are:
- **localRepository**: overrides the location of the local repository
- **interactiveMode**: interactive / batch
- **usePluginRegistry**: for maven 2
- **offline**: if true will not tru to connect remote repositories
- **pluginGroups**: allows plugin goals abbreviation
- **servers**: used to set user credentials for maven servers
- **mirrors**: mirrors repositories
- **proxies**: proxy info
- **profiles**: build profiles
- **activeProfiles**: active build profiles

It is possible to set up a profile that includes external repositories and set it as active for all the projects, 
so it is not necessary to specify it in the different poms:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

    <profiles>
        <profile>
            <id>jboss</id>
            <repositories>
                <repository>
                    <id>redhat-ga</id>
                    <url>https://maven.repository.redhat.com/ga/</url>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                </repository>
            </repositories>
        </profile>
    </profiles>

    <activeProfiles>
        <activeProfile>jboss</activeProfile>
    </activeProfiles>
</settings>
```

---

## Manually Install Dependencies

It is not possible to find all the dependencies in public repositories, some of them are protected by licensing
and must be downloaded manually. It is possible to install this kind of dependencies in the local repository
with the command:
```shell script
mvn install:install-file -Dfile=<path_to_file> -DgroupId=<group_id> -DartifactId=<artifac_id> -Dversion=<version> -Dpackaging=<pakaging_type>
```

---

## Repository Authentication

It is possible to set a master password in the maven repository with the command:
```shell script
mvn --encrypt-master-password
```
Then it is possible to store it in the `settings-security.xml` file:
```xml
<settingsSecurity>
    <master>{master_password}</master>
</settingsSecurity>
```
the password can also be stored in a external file.

Now it is possible to create encrypted passwords using the master-password:
```shell script
mvn --encrypt-password
```
Finally, it is possible to use the generated encrypted password in the auth info for an external repository:
```xml
<servers>
    <server>
        <id>external-repository</id>
        <username>the_username</username>
        <password>{generated_password}</password>
        <configuration>
            <!-- the server configuration -->
        </configuration>
    </server>
</servers>
```

---

## Nexus

Nexus is usually used by companies to store internal artifacts, it supports many formats:
- maven
- npm
- Docker
- etc.. 

A handy nexus feature is that it can act as a proxy for other Maven Repositories, basically allows to have only one
remote repository that manages many other local and remote repositories. 

#### Install 

It is possible to download and run a local nexus image in this way:
```shell script
# pull image
docker pull sonatype/nexus3
# create
docker run -d -p 8081:8081 --name nexus sonatype/nexus3
# Start (second time)
docker start nexus
# stop
docker stop --time=120 nexus
# commands to retrieve the admin password
docker exec -ti nexus bash
cat /nexus-data/admin.password
exit
```
The nexus wen interface is available at http://localhost:8081/, 
the user name is admin and the password is the one retrieved in the steps before.

#### Setup Repository

In order to create a new repository the steps are:
1. `login`
1. `settings icon`
1. `Repository` > `Repositories` > `Create repository`
    - `type`: `mavec2 (hosed)`
    - `name`: `nexus-snapshot`
    - `Version policy`: `Snapshot`
1. `Create`

#### Setup Deployment

Now it is necessary to update the pom configuration:
```xml
<repositories>
    <repository>
        <id>nexus-snapshot</id>
        <url>http://localhost:8081/repository/nexus-snapshot/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>

<distributionManagement>
    <snapshotRepository>
        <id>nexus-snapshot</id>
        <url>http://localhost:8081/repository/nexus-snapshot/</url>
    </snapshotRepository>
</distributionManagement>
```
and the setting with the nexus credentials.

After running `mvn deploy`, it is possible to find the uploaded artifact in `Main Page` > `Browse` > `nexus-snapshot`.

The configuration of the release repository follows the same steps.

#### Group Repository

While creating a new repository it is possible to set it as group, in this case it is possible to configure it as:
- release
- snapshot
- central
- etc..

The repository in this way becomes a virtual repositories and it is possible to add only one repository configuration
and manage all the dependencies and deploy process there.

## Build Profiles

Build profiles allow to specify sets o build configuration values, more than one profile can be active at the same time,
but it is not possible to define the priority. They allow to change the build depending on the operative system 
or to activate optional plugins and os on. 

It is possible to define build profile:
-  pom, `mvn package -S <settings_file>`
- user, `~/.m2/settings.xml`
- global, `<maven_home>/conf/settings,sml`

An example of a profile for the nexus release is:
```xml
<profiles>
    <profile>
        <id>nexus-local</id>
        <distributionManagement>
            <snapshotRepository>
                <id>nexus-snapshot</id>
                <url>http://localhost:8081/repository/nexus-snapshot/</url>
            </snapshotRepository>
            <repository>
                <id>nexus-release</id>
                <url>http://localhost:8081/repository/nexus-release/</url>
            </repository>
        </distributionManagement>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
    </profile>
</profiles>
```
From the command line it is possible to:
- see the current active profiles: `mvn help:active-profiles`
- activate a profile: `-P<profile>`
- deactivate a profile: `-P \!<profile>`
- deactivate a profile and activate another: `-P \!<profile>.<profile>`

Profiles can be used to set environment variable when, for example test or integration tests, are run locally or
in a remote machine.
