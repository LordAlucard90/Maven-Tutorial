# Release Plugin

## Content

- [Goals](#goals)
- [Prepare](#prepare)
- [Perform](#perform)
- [Rollback](#rollback)
- [DryRun](#dryrun)

---

## Goals

The release plugin has two different goals:
- Prepare Release
    - Checks no uncommited changes
    - Checks no snapshots
    - updates release version
    - run tests
    - tag in version control
    - create next snapshot version
    - commit
- Perform Release
    - check out version control with tag
    - run release goals
    - remove release files
    - checkout master branch 
- Clean Release
    - removes `release.properties`
    - removes backup POMs    
- Update POM Version
    - sets the pom version and the version of its child to a specific version

---

## Prepare

When running the `release:prepare` goal maven will ask to define:
- the new version
- the release tag
- the next version
When finish will be present two new files:
- the pom's release backup
- the release properties
And it will have created a new tag in the chosen version control.

This phase cannot be performed if there are uncommited files.

---

## Perform

When running the `release:perform` goal it will finally perform the release, uploading the new version in the repository.

It will also remove the temporary files created in the prepare phase.


---

## Rollback

The `release:rollback` goal can only be run after the prepare one, it will remove the temporary files created in that
phase and restores the pom version to the previous one. 

It will not remove the tag created in the version control, this operation must be performed manually.

---

## DryRun

It is possible to run `release:prepare -DdryRun=true` in order to prepare the release without really performing it.

With this operation will be crated the files representing the next snapshot version and the next release version,
without changing the actual pom and crating the version control tag.

It is possible to release it as before or delete the changes with `release:clean`.
