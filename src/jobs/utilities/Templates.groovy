package utilities
import jenkins.model.Jenkins
import hudson.model.Job
class Templates {
  static void awsAccountSetup(def job, String environment) {
    job("aws-account-setup-test")
    {
      description("Account serup job for environment test")
    }
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

  static void agentChefSetup(def job, String environment) {
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

  static void authChefSetup(def job, String environment) {
    if(!environment.equals("prodtest"))
      {
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
  }


  static void glusterfsChefSetup(def job, String environment) {
    if(!environment.equals("prodtest"))
      {
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
  }

  static void ifcChefSetup(def job, String environment) {
    if(!environment.equals("prodtest"))
      {
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
  }

  static void mqChefSetup(def job, String environment) {
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
  static void pmsChefSetup(def job, String environment) {
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

  static void postfixChefSetup(def job, String environment) {
    if(!environment.equals("prodtest"))
      {
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
  }

  static void webhookChefSetup(def job, String environment) {
    if(!environment.equals("prodtest"))
      {
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
  }
}
