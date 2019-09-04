# Jenkins Job DSL Gradle Example

An example [Job DSL](https://github.com/jenkinsci/job-dsl-plugin) project that uses Gradle for building and testing. Check out [this presentation](https://www.youtube.com/watch?v=SSK_JaBacE0) for a walkthrough of this example (starts around 14:00).

## File structure

    .
    ├── src
    │   ├── jobs                # DSL script files
    │   ├── main
    │   │   ├── groovy          # support classes
    │   │   └── resources
    │   │       └── idea.gdsl   # IDE support for IDEA
    │   ├── scripts             # scripts to use with "readFileFromWorkspace"
    │   └── test
    │       └── groovy          # specs
    └── build.gradle            # build file

## Seed Job

You can create the example seed job via the Rest API Runner (see below) using the pattern `jobs/seed.groovy`.

Or manually create a job with the same structure:

* Invoke Gradle script
   * Use Gradle Wrapper: `true`
   * Tasks: `clean test`
* Process Job DSLs
   * DSL Scripts: `src/jobs/**/*Jobs.groovy`
   * Additional classpath: `src/main/groovy`
* Publish JUnit test result report
   * Test report XMLs: `build/test-results/**/*.xml`

Note that starting with Job DSL 1.60 the "Additional classpath" setting is not available when
[Job DSL script security](https://github.com/jenkinsci/job-dsl-plugin/wiki/Script-Security) is enabled.

## Other Resources

* [Job DSL Playground](http://job-dsl.herokuapp.com/) - App for debugging Job DSL scripts.
* [Job DSL API Viewer](https://jenkinsci.github.io/job-dsl-plugin/) - Full Job DSL syntax reference.
* [Job DSL REST Example](https://github.com/sheehan/job-dsl-rest-example) - Example that shows how to make Job DSL updates via the Jenkins REST API. Originally part of this repo, later split off.

#### Gradle Plugins
Plugins that automatically apply the functionality laid out in this example repo.
* [Gradle Job DSL Support Plugin](https://github.com/AOEpeople/gradle-jenkins-job-dsl-plugin) - Plugin for easy management of Jenkins Job DSL scripts with Gradle.
* [Gradle Jenkins Job DSL Plugin](https://github.com/heremaps/gradle-jenkins-jobdsl-plugin) - Plugin to manage Jenkins Job DSL projects in a Gradle project.
