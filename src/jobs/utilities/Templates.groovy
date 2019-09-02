package utilities
import jenkins.model.Jenkins
import hudson.model.Job
class Templates {
  static void awsAccountSetup(def job, String environment, String site, String branch) {
    job.with {
      description("Account serup job for environment $environment")
      keepDependencies(false)
      disabled(false)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("./aws-account-setup") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$branch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
      }
      publishers {
        mailer("devops@stayntouch.com", false, true)
      }
      configure {
        it / "properties" / "jenkins.model.BuildDiscarderProperty" {
        strategy {
            "daysToKeep"("3")
            "numToKeep"("-1")
            "artifactDaysToKeep"("-1")
            "artifactNumToKeep"("-1")
          }
        }
        it / "properties" / "com.sonyericsson.rebuild.RebuildSettings" {
          "autoRebuild"("false")
          "rebuildDisabled"("false")
        }
      }
    }
  }

    //chef setup
  static void chefFolderSetup(def folder, String environment, String site, String branch) {
    folder.with {
      description "This job will replace all template instances with the latest Chef configuration.  We will need one job per env & application type.  The steps it will follow for each template type are: \
      Start new template instance \
      Bootstrap it with Chef Server \
      Execute chef client to configure node (with startup scripts disabled / commented out) \
      Shutdown template instance \
      Take template image \
      Terminate template instance"
      views {
        if(!environment.equals("prodtest"))
        {
          listView("auth") {
            description("Auth chef setup jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
              name("auth-$environment")
            }
            columns {
              status()
              weather()
              name()
              lastSuccess()
              lastFailure()
              lastDuration()
              buildButton()
            }
          }
          listView("ifc") {
            description("ifc chef setup jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
              name("ifc-$environment")
            }
            columns {
              status()
              weather()
              name()
              lastSuccess()
              lastFailure()
              lastDuration()
              buildButton()
            }
          }
          listView("webhook") {
            description("webhook chef setup jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
              name("webhook-$environment")
            }
            columns {
              status()
              weather()
              name()
              lastSuccess()
              lastFailure()
              lastDuration()
              buildButton()
            }
          }
        }
          listView("general service") {
            description("All general service jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
              names("postfix-$environment", "mq-app-$environment", "mq-$environment", "glusterfs-$environment", "agent-$environment")
            }
            columns {
              status()
              weather()
              name()
              lastSuccess()
              lastFailure()
              lastDuration()
              buildButton()
            }
          }

          listView("pms") {
            description("pms chef setup jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
              name("pms-$environment")
            }
            columns {
              status()
              weather()
              name()
              lastSuccess()
              lastFailure()
              lastDuration()
              buildButton()
            }
          }

      }
    }
  }

  static void agentChefSetup(def job, String environment, String site, String branch) {
    job.with {
      description("Job for deploying agent service for the $environment environment.")
      keepDependencies(false)
      disabled(false)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("../chef-setup/agent") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$branch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
      }
      publishers {
        mailer("devops@stayntouch.com", false, true)
      }
      configure {
        it / "properties" / "jenkins.model.BuildDiscarderProperty" {
          strategy {
            "daysToKeep"("3")
            "numToKeep"("-1")
            "artifactDaysToKeep"("-1")
            "artifactNumToKeep"("-1")
          }
        }
        it / "properties" / "com.sonyericsson.rebuild.RebuildSettings" {
          "autoRebuild"("false")
          "rebuildDisabled"("false")
        }
      }
    }
  }

  static void authChefSetup(def job, String environment, String site, String branch) {
    job.with {
      description("Deploy Chef cookbooks for $environment environment.")
      keepDependencies(false)
      disabled(false)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("../chef-setup/auth") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$branch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
      }
      publishers {
        mailer("devops@stayntouch.com", false, true)
      }
      configure {
        it / "properties" / "jenkins.model.BuildDiscarderProperty" {
          strategy {
            "daysToKeep"("3")
            "numToKeep"("-1")
            "artifactDaysToKeep"("-1")
            "artifactNumToKeep"("-1")
          }
        }
        it / "properties" / "com.sonyericsson.rebuild.RebuildSettings" {
          "autoRebuild"("false")
          "rebuildDisabled"("false")
        }
      }
    }
  }


  static void glusterfsChefSetup(def job, String environment, String site, String branch) {
    job.with {
      description("Job for deploying glusterfs service for the $environment environment.")
      keepDependencies(false)
      disabled(true)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("../chef-setup/glusterfs") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$branch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
      }
      publishers {
        mailer("devops@stayntouch.com", false, true)
      }
      configure {
        it / "properties" / "jenkins.model.BuildDiscarderProperty" {
          strategy {
            "daysToKeep"("3")
            "numToKeep"("-1")
            "artifactDaysToKeep"("-1")
            "artifactNumToKeep"("-1")
          }
        }
        it / "properties" / "com.sonyericsson.rebuild.RebuildSettings" {
          "autoRebuild"("false")
          "rebuildDisabled"("false")
        }
      }
    }
  }

  static void ifcChefSetup(def job, String environment, String site, String branch) {
    job.with {
      description("Deploy Chef cookbooks of $environment environment for ifc servers")
      keepDependencies(false)
      disabled(false)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("../chef-setup/ifc") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$branch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
      }
      publishers {
        mailer("devops@stayntouch.com", false, true)
      }
      configure {
        it / "properties" / "jenkins.model.BuildDiscarderProperty" {
          strategy {
            "daysToKeep"("3")
            "numToKeep"("-1")
            "artifactDaysToKeep"("-1")
            "artifactNumToKeep"("-1")
          }
        }
        it / "properties" / "com.sonyericsson.rebuild.RebuildSettings" {
          "autoRebuild"("false")
          "rebuildDisabled"("false")
        }
      }
    }
  }

  static void mqChefSetup(def job, String environment, String site, String branch) {
    job.with {
      description("Job for deploying mq service for the $environment environment.")
      keepDependencies(false)
      disabled(false)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("../chef-setup/mq") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$branch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
      }
      publishers {
        mailer("devops@stayntouch.com", false, true)
      }
      configure {
        it / "properties" / "jenkins.model.BuildDiscarderProperty" {
          strategy {
            "daysToKeep"("3")
            "numToKeep"("-1")
            "artifactDaysToKeep"("-1")
            "artifactNumToKeep"("-1")
          }
        }
        it / "properties" / "com.sonyericsson.rebuild.RebuildSettings" {
          "autoRebuild"("false")
          "rebuildDisabled"("false")
        }
      }
    }
  }
  static void pmsChefSetup(def job, String environment, String site, String branch) {
    job.with {
      description("Deploy Chef cookbooks for $environment environment using")
      keepDependencies(false)
      disabled(false)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("../chef-setup/pms") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$branch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
      }
      publishers {
        mailer("devops@stayntouch.com", false, true)
      }
      configure {
        it / "properties" / "jenkins.model.BuildDiscarderProperty" {
          strategy {
            "daysToKeep"("3")
            "numToKeep"("-1")
            "artifactDaysToKeep"("-1")
            "artifactNumToKeep"("-1")
          }
        }
        it / "properties" / "com.sonyericsson.rebuild.RebuildSettings" {
          "autoRebuild"("false")
          "rebuildDisabled"("false")
        }
      }
    }
  }

  static void postfixChefSetup(def job, String environment, String site, String branch) {
    job.with {
      description("Job for deploying postfix service for the $environment environment.")
      keepDependencies(false)
      disabled(false)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("../chef-setup/postfix") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$branch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
      }
      publishers {
        mailer("devops@stayntouch.com", false, true)
      }
      configure {
        it / "properties" / "jenkins.model.BuildDiscarderProperty" {
          strategy {
            "daysToKeep"("3")
            "numToKeep"("-1")
            "artifactDaysToKeep"("-1")
            "artifactNumToKeep"("-1")
          }
        }
        it / "properties" / "com.sonyericsson.rebuild.RebuildSettings" {
          "autoRebuild"("false")
          "rebuildDisabled"("false")
        }
      }
    }
  }

  static void webhookChefSetup(def job, String environment, String site, String branch) {
    job.with {
      description("Deploy Chef cookbooks for $environment environment using")
      keepDependencies(false)
      disabled(false)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("../chef-setup/webhook") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$branch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
      }
      publishers {
        mailer("devops@stayntouch.com", false, true)
      }
      configure {
        it / "properties" / "jenkins.model.BuildDiscarderProperty" {
          strategy {
            "daysToKeep"("3")
            "numToKeep"("-1")
            "artifactDaysToKeep"("-1")
            "artifactNumToKeep"("-1")
          }
        }
        it / "properties" / "com.sonyericsson.rebuild.RebuildSettings" {
          "autoRebuild"("false")
          "rebuildDisabled"("false")
        }
      }
    }
  }
  static void excavatorCopy (def job, String environment, String site, String branch) {
    job.with {
      description("Deploy Chef cookbooks for $environment environment using")
      keepDependencies(false)
      disabled(false)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("../chef-setup/excavator-copy") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$branch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
      }
      publishers {
        mailer("devops@stayntouch.com", false, true)
      }
      configure {
        it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
          strategy {
            'daysToKeep'('3')
            'numToKeep'('-1')
            'artifactDaysToKeep'('-1')
            'artifactNumToKeep'('-1')
          }
        }
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
          'autoRebuild'('false')
          'rebuildDisabled'('false')
        }
      }
    }
  }
  static void mqAppChefSetup(def job, String environment, String site, String branch) {
    job.with {
      description("Deploy Chef cookbooks for $environment environment")
      keepDependencies(false)
      disabled(false)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("../chef-setup/mq-app") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$branch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
      }
      configure {
        it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
          strategy {
            'daysToKeep'('3')
            'numToKeep'('-1')
            'artifactDaysToKeep'('-1')
            'artifactNumToKeep'('-1')
          }
        }
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
          'autoRebuild'('false')
          'rebuildDisabled'('false')
        }
      }
    }
  }

// Deploy jobs

// Deploy Folder
  static void deployFolderSetup(def folder, String environment, String site, String branch) {
    folder.with {
      description "This job will deploy the template image to the auto scaling group for each template type.  We will need one job per env & application type.  The steps for each template type are: \n \
      Create template instance from chef template image \n \
      Deploy via capistrano to template instance (includes gulp, migrations) \n \
      Shutdown template instance \n \
      Create new deploy image from template instance \n \
      Terminate template instance \n \
      Create a new launch configuration associated with the deploy image (with user-data enabling / uncommenting startup scripts) \n \
      Create and start a new auto scaling group for the launch configuration \n \
      Swap load balancer to new auto scaling group in case of app servers \n \
      Gracefully stop old auto scaling group \n \
      Allows existing processes to finish gracefully \n \
      Instance configured with 2.5 hour termination policy"
      views {
        listView("UI") {
            description("Ui deploy jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
                names("rover-ui-shared-$environment-deploy", "rover-zest-web-$environment-deploy")
            }
            columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        listView("auth") {
            description("All auth deploy jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
                name("auth-$environment-deploy")
            }
            columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        listView("ifc") {
            description("ifc deploy jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
                name("ifc-$environment-deploy")
            }
            columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        listView("pms") {
            description("pms deploy jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
                names("pms-$environment-deploy")
            }
            columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        listView("webhook") {
            description("webhook deploy jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
                names("webhook-$environment-deploy")
            }
            columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
      }
    }
  }
  static void authDelpoySetup(def job, String environment, String site, String checkoutBranch) {
    job.with {
      description()
      keepDependencies(false)
      blockOn(".*restart-auth.*-$environment", {
        blockLevel("GLOBAL")
        scanQueueFor("DISABLED")
      })
      scm {
        git {
          remote {
            github("StayNTouch/rover-auth", "ssh")
          }
          branch("origin/$checkoutBranch")
        }
      }
      disabled(false)
      triggers {
        githubPush()
      }
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("../deploy/01-create-template-instance-from-chef-template-image-auth") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
                predefinedProp("BRANCH", "$branch")
                predefinedProp("ENVIRONMENT", "$environment")
                predefinedProp("SITE", "$site")
              }
          }
        }
        downstreamParameterized {
          trigger("../deploy/02-deploy-via-capistrano-to-template-instance-auth") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
                predefinedProp("BRANCH", "$branch")
                predefinedProp("ENVIRONMENT", "$environment")
                predefinedProp("SITE", "$site")
              }
          }
        }
        downstreamParameterized {
          trigger("../deploy/03-A-swap-asg-auth-app,../deploy/03-B-swap-asg-auth-wkr,../deploy/03-C-swap-asg-auth-ipc,../deploy/03-D-swap-asg-auth-railsc") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
                predefinedProp("BRANCH", "$branch")
                predefinedProp("ENVIRONMENT", "$environment")
                predefinedProp("SITE", "$site")
              }
          }
        }
        downstreamParameterized {
          trigger("../deploy/04-cleanup-auth") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
                predefinedProp("BRANCH", "$branch")
                predefinedProp("ENVIRONMENT", "$environment")
                predefinedProp("SITE", "$site")
              }
          }
        }
      }
      publishers {
        mailer("devops@stayntouch.com release@stayntouch.com", false, true)
      }
      configure {
        it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
          strategy {
            'daysToKeep'('3')
            'numToKeep'('-1')
            'artifactDaysToKeep'('-1')
            'artifactNumToKeep'('-1')
          }
        }
        it / 'properties' / 'com.coravy.hudson.plugins.github.GithubProjectProperty' {
          'projectUrl'('git@github.com:StayNTouch/rover-auth.git/')
          displayName()
        }
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
          'autoRebuild'('false')
          'rebuildDisabled'('false')
        }
      }
    }
  }
// class close
}

