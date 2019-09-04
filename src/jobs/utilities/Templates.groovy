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
  static void chefFolderSetup(def folder, String environment) {
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
  static void deployFolderSetup(def folder, String environment) {
    folder.with {
      description "This job will deploy the template image to the auto scaling group for each template type.  We will need one job per env & application type.  The steps for each template type are: \
      Create template instance from chef template image \
      Deploy via capistrano to template instance (includes gulp, migrations) \
      Shutdown template instance \
      Create new deploy image from template instance \
      Terminate template instance \
      Create a new launch configuration associated with the deploy image (with user-data enabling / uncommenting startup scripts) \
      Create and start a new auto scaling group for the launch configuration \
      Swap load balancer to new auto scaling group in case of app servers \
      Gracefully stop old auto scaling group \
      Allows existing processes to finish gracefully \
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
        listView("excavator") {
            description("excavator deploy jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
                names("excavator-$environment-deploy")
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
              predefinedProp("BRANCH", "$checkoutBranch")
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
              predefinedProp("BRANCH", "$checkoutBranch")
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
              predefinedProp("BRANCH", "$checkoutBranch")
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
              predefinedProp("BRANCH", "$checkoutBranch")
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
  static void pmsDelpoySetup(def job, String environment, String site, String checkoutBranch) {
    job.with {
      description()
      keepDependencies(false)
      blockOn(".*restart-pms.*-$environment", {
        blockLevel("GLOBAL")
        scanQueueFor("DISABLED")
      })
      scm {
        git {
          remote {
            github("StayNTouch/pms", "ssh")
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
          trigger("../deploy/01-create-template-instance-from-chef-template-image-pms") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
        downstreamParameterized {
          trigger("../deploy/02-deploy-via-capistrano-to-template-instance-pms") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
        downstreamParameterized {
          trigger("../deploy/03-A-swap-asg-pms-app,../deploy/03-B-swap-asg-pms-con,../deploy/03-C-swap-asg-pms-rsq,../deploy/03-D-swap-asg-pms-wkr,../deploy/03-E-swap-asg-pms-ipc,../deploy/03-F-swap-asg-pms-railsc") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
        downstreamParameterized {
          trigger("../deploy/04-cleanup-pms") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
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
          'projectUrl'('git@github.com:StayNTouch/pms.git/')
          displayName()
        }
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
          'autoRebuild'('false')
          'rebuildDisabled'('false')
        }
      }
    }
  }

  static void ifcDelpoySetup(def job, String environment, String site, String checkoutBranch) {
    job.with {
      description()
      keepDependencies(false)
      blockOn(".*restart-ifc.*-$environment", {
        blockLevel("GLOBAL")
        scanQueueFor("DISABLED")
      })
      scm {
        git {
          remote {
            github("StayNTouch/rover-ifc", "ssh")
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
          trigger("../deploy/01-create-template-instance-from-chef-template-image-ifc") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
        downstreamParameterized {
          trigger("../deploy/02-deploy-via-capistrano-to-template-instance-ifc") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
        downstreamParameterized {
          trigger("../deploy/03-A-swap-asg-ifc-app,../deploy/03-B-swap-asg-ifc-wkr,../deploy/03-C-swap-asg-ifc-skd,../deploy/03-D-swap-asg-ifc-ipc,../deploy/03-E-swap-asg-ifc-railsc") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
        downstreamParameterized {
          trigger("../deploy/04-cleanup-ifc") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
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
          'projectUrl'('git@github.com:StayNTouch/rover-ifc.git/')
          displayName()
        }
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
          'autoRebuild'('false')
          'rebuildDisabled'('false')
        }
      }
    }
  }
  static void webhookDelpoySetup(def job, String environment, String site, String checkoutBranch) {
    job.with {
      description()
      keepDependencies(false)
      blockOn(".*restart-webhook.*-$environment", {
        blockLevel("GLOBAL")
        scanQueueFor("DISABLED")
      })
      scm {
        git {
          remote {
            github("StayNTouch/rover-webhook", "ssh")
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
          trigger("../deploy/01-create-template-instance-from-chef-template-image-webhook") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
        downstreamParameterized {
          trigger("../deploy/02-deploy-via-capistrano-to-template-instance-webhook") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
        downstreamParameterized {
          trigger("../deploy/03-A-swap-asg-webhook-wkr,../deploy/03-B-swap-asg-webhook-ipc,../deploy/03-C-swap-asg-webhook-railsc") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
            }
          }
        }
        downstreamParameterized {
          trigger("../deploy/04-cleanup-webhook") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
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
          'projectUrl'('git@github.com:StayNTouch/rover-webhook.git/')
          displayName()
        }
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
          'autoRebuild'('false')
          'rebuildDisabled'('false')
        }
      }
    }
  }
  static void excavatorDelpoySetup(def job, String environment, String site, String checkoutBranch) {
    job.with {
      description()
      keepDependencies(false)
      scm {
        git {
          remote {
            github("StayNTouch/rover-excavator", "ssh")
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
          trigger("../deploy/excavator") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
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
          'projectUrl'('git@github.com:StayNTouch/rover-excavator.git/')
          displayName()
        }
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
          'autoRebuild'('false')
          'rebuildDisabled'('false')
        }
      }
    }
  }
  static void zestWebkDelpoySetup(def job, String environment, String site, String checkoutBranch) {
    job.with {
      description("Build rover zest web UI app and sync into zest web app s3 bucket")
      keepDependencies(false)
      scm {
        git {
          remote {
            github("StayNTouch/rover-zest-web", "ssh")
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
          trigger("../deploy/rover-zest-web") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
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
        it / 'properties' / 'com.coravy.hudson.plugins.github.GithubProjectProperty' {
          'projectUrl'('git@github.com:StayNTouch/rover-zest-web.git/')
          displayName()
        }
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
          'autoRebuild'('false')
          'rebuildDisabled'('false')
        }
      }
    }
  }
  static void zestUiDelpoySetup(def job, String environment, String site, String checkoutBranch) {
    job.with {
      description("Build rover login UI app and sync into login app s3 bucket")
      keepDependencies(false)
      scm {
        git {
          remote {
            github("StayNTouch/rover-ui-shared", "ssh")
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
          trigger("../deploy/rover-zest-web") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
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
        it / 'properties' / 'com.coravy.hudson.plugins.github.GithubProjectProperty' {
          'projectUrl'('git@github.com:StayNTouch/rover-ui-shared.git/')
          displayName()
        }
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
          'autoRebuild'('false')
          'rebuildDisabled'('false')
        }
      }
    }
  }
  static void infraDelpoySetup(def job, String environment, String site, String checkoutBranch) {
    job.with {
      description("""
                  This job will configure all AWS services needed for a new or updated environment/site. \
                  The job will run one parent Cloudformation template with nested child templates.  The setup includes: \

                  Account setup \
                  Roles \
                  Permissions \
                  Cloudtrail \
                  Cloud Watch \
                  VPC \
                  Subnets \
                  Routing tables \
                  Security Groups \
                  Aurora MySQL \
                  Aurora PostgreSQL \
                  ElastiCache Memached \
                  ElastiCache Redis \
                  EFS \
                  Load balancers \
                  NAT Gateways \
                  Internet Gateways \
                  Route 53 DNS \
                  Auto Scaling Groups (will be replaced in deploy job) \
                  """)
      keepDependencies(false)
      disabled(false)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("infrastructure-setup") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
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
  static void rakeAuthSetup(def job, String environment, String site, String checkoutBranch) {
    job.with {
      description("Running rake task for auth.")
      keepDependencies(false)
      parameters {
        stringParam("RAKE_TASK_NAME", "", """Here are some examples of values to enter: \
No parameters: \
lhm:cleanup \
Standard Parameters: \
pms:generate_folio_number_for_past_checkout_reservation_bills[HOTELA,HOTELB] \
Standard Parameters With Spaces (wrap with quotes): \
"pms:link_refunds_in_ar_transactions[CODE WITH SPACES]" \
Option Parser Parameters: \
pms:update_future_transactions -- --hotel_code="HS1234" --from_date="2017-08-01" --to_date="2017-11-13""")
      }
      disabled(false)
      concurrentBuild(true)
      steps {
        downstreamParameterized {
          trigger("../rake-task/auth/auth-rake-task") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
              predefinedProp("APP_TYPE", "auth")
            }
          }
        }
      }
      publishers {
        mailer("devops@stayntouch.com release@stayntouch.com", false, true)
        downstreamParameterized {
          trigger("../rake-task/cleanup/clean-up-rake-task-on-failure") {
            condition('UNSTABLE_OR_BETTER')
            parameters {
              currentBuild()
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
  static void rakeIfcSetup(def job, String environment, String site, String checkoutBranch) {
    job.with {
      description("Running rake task for ifc.")
      keepDependencies(false)
      parameters {
        stringParam("RAKE_TASK_NAME", "", """Here are some examples of values to enter: \
No parameters: \
lhm:cleanup \
Standard Parameters: \
pms:generate_folio_number_for_past_checkout_reservation_bills[HOTELA,HOTELB] \
Standard Parameters With Spaces (wrap with quotes): \
"pms:link_refunds_in_ar_transactions[CODE WITH SPACES]" \
Option Parser Parameters: \
pms:update_future_transactions -- --hotel_code="HS1234" --from_date="2017-08-01" --to_date="2017-11-13""")
      }
      disabled(false)
      concurrentBuild(true)
      steps {
        downstreamParameterized {
          trigger("../rake-task/ifc/ifc-rake-task") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
              predefinedProp("APP_TYPE", "ifc")
            }
          }
        }
      }
      publishers {
        downstreamParameterized {
          trigger("../rake-task/cleanup/clean-up-rake-task-on-failure") {
            condition('UNSTABLE_OR_BETTER')
            parameters {
              currentBuild()
            }
          }
        }
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
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
          'autoRebuild'('false')
          'rebuildDisabled'('false')
        }
      }
    }
  }
  static void rakePmsSetup(def job, String environment, String site, String checkoutBranch) {
    job.with {
      description("Running rake task for pms.")
      keepDependencies(false)
      parameters {
        stringParam("RAKE_TASK_NAME", "", """Here are some examples of values to enter:

No parameters:
lhm:cleanup

Standard Parameters:
pms:generate_folio_number_for_past_checkout_reservation_bills[HOTELA,HOTELB]

Standard Parameters With Spaces (wrap with quotes):
"pms:link_refunds_in_ar_transactions[CODE WITH SPACES]"

Option Parser Parameters:
pms:update_future_transactions -- --hotel_code="HS1234" --from_date="2017-08-01" --to_date="2017-11-13""")
      }
      disabled(false)
      concurrentBuild(true)
      steps {
        downstreamParameterized {
          trigger("../rake-task/pms/pms-rake-task") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
              predefinedProp("APP_TYPE", "pms")
            }
          }
        }
      }
      publishers {
        downstreamParameterized {
          trigger("../rake-task/cleanup/clean-up-rake-task-on-failure") {
            condition('UNSTABLE_OR_BETTER')
            parameters {
              currentBuild()
            }
          }
        }
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
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
          'autoRebuild'('false')
          'rebuildDisabled'('false')
        }
      }
    }
  }
  static void rakeWebhookSetup(def job, String environment, String site, String checkoutBranch) {
    job.with {
      description("Running rake task for webhook.")
      keepDependencies(false)
      parameters {
        stringParam("RAKE_TASK_NAME", "", """Here are some examples of values to enter:

No parameters:
lhm:cleanup

Standard Parameters:
pms:generate_folio_number_for_past_checkout_reservation_bills[HOTELA,HOTELB]

Standard Parameters With Spaces (wrap with quotes):
"pms:link_refunds_in_ar_transactions[CODE WITH SPACES]"

Option Parser Parameters:
    pms:update_future_transactions -- --hotel_code="HS1234" --from_date="2017-08-01" --to_date="2017-11-13""")
      }
      disabled(false)
      concurrentBuild(true)
      steps {
        downstreamParameterized {
          trigger("../rake-task/webhook/webhook-rake-task") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
              predefinedProp("ENVIRONMENT", "$environment")
              predefinedProp("SITE", "$site")
              predefinedProp("APP_TYPE", "webhook")
            }
          }
        }
      }
      publishers {
        downstreamParameterized {
          trigger("../rake-task/cleanup/clean-up-rake-task-on-failure") {
            condition('UNSTABLE_OR_BETTER')
            parameters {
              currentBuild()
            }
          }
        }
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
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
          'autoRebuild'('false')
          'rebuildDisabled'('false')
        }
      }
    }
  }

// Restart job setup

  // Deploy Folder
  static void restartFolderSetup(def folder, String environment) {
    folder.with {
      description "jobs to restart all services"
      views {
        listView("auth") {
          description("All auth restart jobs")
          filterBuildQueue()
          filterExecutors()
          jobs {
            regex("restart-auth-.+-$environment")
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
          description("ifc restart jobs")
          filterBuildQueue()
          filterExecutors()
          jobs {
            regex("restart-ifc-.+-$environment")
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
          description("pms restart jobs")
          filterBuildQueue()
          filterExecutors()
          jobs {
            regex("restart-pms-.+-$environment")
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
          description("webhook restart jobs")
          filterBuildQueue()
          filterExecutors()
          jobs {
            regex("restart-webhook-.+-$environment")
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

  static void restartJobSetup( def job, String environment, String site, String checkoutBranch, String app, String type) {
    job.with {
      description()
      keepDependencies(false)
      blockOn(".*$app-$environment-deploy.*", {
        blockLevel("GLOBAL")
        scanQueueFor("DISABLED")
      })
      disabled(false)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("../restart-services/restart-$app-$type") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
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
  static void secretManagerSetup( def job, String environment, String site, String checkoutBranch) {
    job.with {
      description("This job will configure all AWS secret manager secrets needed for a new or updated environment/site.")
      keepDependencies(false)
      disabled(false)
      concurrentBuild(false)
      steps {
        downstreamParameterized {
          trigger("secrets-manager-setup") {
            block {
              buildStepFailure("FAILURE")
              unstable("UNSTABLE")
              failure("FAILURE")
            }
            parameters {
              predefinedProp("BRANCH", "$checkoutBranch")
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

// class close
}

