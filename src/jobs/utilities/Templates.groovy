package utilities
class Templates {
  static void awsAccountSetup(def job, String environment) {
      job.with {
          description('Arbitrary feature $environment')
          keepDependencies(false)
          disabled(false)
          concurrentBuild(false)
          steps {
              downstreamParameterized {
                  trigger("") {
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
  }
    // job("aws-account-setup-${environment}") {
    //     description()
    //     keepDependencies(false)
    //     disabled(false)
    //     concurrentBuild(false)
    //     steps {
    //         downstreamParameterized {
    //             trigger("") {
    //                 block {
    //                     buildStepFailure("FAILURE")
    //                     unstable("UNSTABLE")
    //                     failure("FAILURE")
    //                 }
    //             }
    //         }
    //     }
    //     publishers {
    //         mailer("devops@stayntouch.com", false, true)
    //     }
    //     configure {
    //         it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
    //             strategy {
    //                 'daysToKeep'('3')
    //                 'numToKeep'('-1')
    //                 'artifactDaysToKeep'('-1')
    //                 'artifactNumToKeep'('-1')
    //             }
    //         }
    //         it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
    //             'autoRebuild'('false')
    //             'rebuildDisabled'('false')
    //         }
    //     }
    // }

    // //chef setup

    // folder("chef-setup-${environment}") {
    //     description 'This job will replace all template instances with the latest Chef configuration.  We will need one job per env & application type.  The steps it will follow for each template type are: \
    //     Start new template instance \
    //     Bootstrap it with Chef Server \
    //     Execute chef client to configure node (with startup scripts disabled / commented out) \
    //     Shutdown template instance \
    //     Take template image \
    //     Terminate template instance'
    //     views {
    //         listView('auth') {
    //             description('Auth chef setup jobs')
    //             filterBuildQueue()
    //             filterExecutors()
    //             jobs {
    //                 name('auth-${environment}')
    //             }
    //             columns {
    //                 status()
    //                 weather()
    //                 name()
    //                 lastSuccess()
    //                 lastFailure()
    //                 lastDuration()
    //                 buildButton()
    //             }
    //         }
    //         listView('general service') {
    //             description('All general service jobs')
    //             filterBuildQueue()
    //             filterExecutors()
    //             jobs {
    //                 names('postfix-${environment}', 'mq-app-${environment}', 'mq-${environment}', 'glusterfs-${environment}', ' agent-${environment}')
    //             }
    //             columns {
    //                 status()
    //                 weather()
    //                 name()
    //                 lastSuccess()
    //                 lastFailure()
    //                 lastDuration()
    //                 buildButton()
    //             }
    //         }
    //         listView('ifc') {
    //             description('ifc chef setup jobs')
    //             filterBuildQueue()
    //             filterExecutors()
    //             jobs {
    //                 name('ifc-${environment}')
    //             }
    //             columns {
    //                 status()
    //                 weather()
    //                 name()
    //                 lastSuccess()
    //                 lastFailure()
    //                 lastDuration()
    //                 buildButton()
    //             }
    //         }
    //         listView('pms') {
    //             description('pms chef setup jobs')
    //             filterBuildQueue()
    //             filterExecutors()
    //             jobs {
    //                 name('pms-${environment}')
    //             }
    //             columns {
    //                 status()
    //                 weather()
    //                 name()
    //                 lastSuccess()
    //                 lastFailure()
    //                 lastDuration()
    //                 buildButton()
    //             }
    //         }
    //         listView('webhook') {
    //             description('webhook chef setup jobs')
    //             filterBuildQueue()
    //             filterExecutors()
    //             jobs {
    //                 name('webhook-${environment}')
    //             }
    //             columns {
    //                 status()
    //                 weather()
    //                 name()
    //                 lastSuccess()
    //                 lastFailure()
    //                 lastDuration()
    //                 buildButton()
    //             }
    //         }
    //     }
    // }
    // job("chef-setup-${environment}/agent-${environment}") {
    //     description("Job for deploying agent service for the staging environment.")
    //     keepDependencies(false)
    //     disabled(false)
    //     concurrentBuild(false)
    //     steps {
    //         downstreamParameterized {
    //             trigger("") {
    //                 block {
    //                     buildStepFailure("FAILURE")
    //                     unstable("UNSTABLE")
    //                     failure("FAILURE")
    //                 }
    //             }
    //         }
    //     }
    //     publishers {
    //         mailer("devops@stayntouch.com", false, true)
    //     }
    //     configure {
    //         it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
    //             strategy {
    //                 'daysToKeep'('3')
    //                 'numToKeep'('-1')
    //                 'artifactDaysToKeep'('-1')
    //                 'artifactNumToKeep'('-1')
    //             }
    //         }
    //         it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
    //             'autoRebuild'('false')
    //             'rebuildDisabled'('false')
    //         }
    //     }
    // }

    // job("chef-setup-${environment}/auth-${environment}") {
    //     description("Deploy Chef cookbooks for staging environment using")
    //     keepDependencies(false)
    //     disabled(false)
    //     concurrentBuild(false)
    //     steps {
    //         downstreamParameterized {
    //             trigger("../chef-setup/auth") {
    //                 block {
    //                     buildStepFailure("FAILURE")
    //                     unstable("UNSTABLE")
    //                     failure("FAILURE")
    //                 }
    //             }
    //         }
    //     }
    //     publishers {
    //         mailer("devops@stayntouch.com", false, true)
    //     }
    //     configure {
    //         it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
    //             strategy {
    //                 'daysToKeep'('3')
    //                 'numToKeep'('-1')
    //                 'artifactDaysToKeep'('-1')
    //                 'artifactNumToKeep'('-1')
    //             }
    //         }
    //         it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
    //             'autoRebuild'('false')
    //             'rebuildDisabled'('false')
    //         }
    //     }
    // }

    // job("chef-setup-${environment}/glusterfs-${environment}") {
    //     description("Job for deploying glusterfs service for the staging environment.")
    //     keepDependencies(false)
    //     disabled(true)
    //     concurrentBuild(false)
    //     steps {
    //         downstreamParameterized {
    //             trigger("") {
    //                 block {
    //                     buildStepFailure("FAILURE")
    //                     unstable("UNSTABLE")
    //                     failure("FAILURE")
    //                 }
    //             }
    //         }
    //     }
    //     publishers {
    //         mailer("devops@stayntouch.com", false, true)
    //     }
    //     configure {
    //         it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
    //             strategy {
    //                 'daysToKeep'('3')
    //                 'numToKeep'('-1')
    //                 'artifactDaysToKeep'('-1')
    //                 'artifactNumToKeep'('-1')
    //             }
    //         }
    //         it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
    //             'autoRebuild'('false')
    //             'rebuildDisabled'('false')
    //         }
    //     }
    // }

    // job("chef-setup-${environment}/ifc-${environment}") {
    //     description("Deploy Chef cookbooks of staging environment for ifc servers")
    //     keepDependencies(false)
    //     disabled(false)
    //     concurrentBuild(false)
    //     steps {
    //         downstreamParameterized {
    //             trigger("") {
    //                 block {
    //                     buildStepFailure("FAILURE")
    //                     unstable("UNSTABLE")
    //                     failure("FAILURE")
    //                 }
    //             }
    //         }
    //     }
    //     publishers {
    //         mailer("devops@stayntouch.com", false, true)
    //     }
    //     configure {
    //         it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
    //             strategy {
    //                 'daysToKeep'('3')
    //                 'numToKeep'('-1')
    //                 'artifactDaysToKeep'('-1')
    //                 'artifactNumToKeep'('-1')
    //             }
    //         }
    //         it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
    //             'autoRebuild'('false')
    //             'rebuildDisabled'('false')
    //         }
    //     }
    // }

    // job("chef-setup-${environment}/mq-app-${environment}") {
    //     description("Deploy Chef cookbooks for staging environment using")
    //     keepDependencies(false)
    //     disabled(false)
    //     concurrentBuild(false)
    //     steps {
    //         downstreamParameterized {
    //             trigger("") {
    //                 block {
    //                     buildStepFailure("FAILURE")
    //                     unstable("UNSTABLE")
    //                     failure("FAILURE")
    //                 }
    //             }
    //         }
    //     }
    //     configure {
    //         it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
    //             strategy {
    //                 'daysToKeep'('3')
    //                 'numToKeep'('-1')
    //                 'artifactDaysToKeep'('-1')
    //                 'artifactNumToKeep'('-1')
    //             }
    //         }
    //         it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
    //             'autoRebuild'('false')
    //             'rebuildDisabled'('false')
    //         }
    //     }
    // }

    // job("chef-setup-${environment}/mq-${environment}") {
    //     description("Job for deploying mq service for the staging environment.")
    //     keepDependencies(false)
    //     disabled(false)
    //     concurrentBuild(false)
    //     steps {
    //         downstreamParameterized {
    //             trigger("") {
    //                 block {
    //                     buildStepFailure("FAILURE")
    //                     unstable("UNSTABLE")
    //                     failure("FAILURE")
    //                 }
    //             }
    //         }
    //     }
    //     publishers {
    //         mailer("devops@stayntouch.com", false, true)
    //     }
    //     configure {
    //         it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
    //             strategy {
    //                 'daysToKeep'('3')
    //                 'numToKeep'('-1')
    //                 'artifactDaysToKeep'('-1')
    //                 'artifactNumToKeep'('-1')
    //             }
    //         }
    //         it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
    //             'autoRebuild'('false')
    //             'rebuildDisabled'('false')
    //         }
    //     }
    // }

    // job("chef-setup-${environment}/pms-${environment}") {
    //     description("Deploy Chef cookbooks for staging environment using")
    //     keepDependencies(false)
    //     disabled(false)
    //     concurrentBuild(false)
    //     steps {
    //         downstreamParameterized {
    //             trigger("") {
    //                 block {
    //                     buildStepFailure("FAILURE")
    //                     unstable("UNSTABLE")
    //                     failure("FAILURE")
    //                 }
    //             }
    //         }
    //     }
    //     publishers {
    //         mailer("devops@stayntouch.com", false, true)
    //     }
    //     configure {
    //         it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
    //             strategy {
    //                 'daysToKeep'('3')
    //                 'numToKeep'('-1')
    //                 'artifactDaysToKeep'('-1')
    //                 'artifactNumToKeep'('-1')
    //             }
    //         }
    //         it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
    //             'autoRebuild'('false')
    //             'rebuildDisabled'('false')
    //         }
    //     }
    // }

    // job("chef-setup-${environment}/postfix-${environment}") {
    //     description("Job for deploying postfix service for the staging environment.")
    //     keepDependencies(false)
    //     disabled(false)
    //     concurrentBuild(false)
    //     steps {
    //         downstreamParameterized {
    //             trigger("") {
    //                 block {
    //                     buildStepFailure("FAILURE")
    //                     unstable("UNSTABLE")
    //                     failure("FAILURE")
    //                 }
    //             }
    //         }
    //     }
    //     publishers {
    //         mailer("devops@stayntouch.com", false, true)
    //     }
    //     configure {
    //         it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
    //             strategy {
    //                 'daysToKeep'('3')
    //                 'numToKeep'('-1')
    //                 'artifactDaysToKeep'('-1')
    //                 'artifactNumToKeep'('-1')
    //             }
    //         }
    //         it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
    //             'autoRebuild'('false')
    //             'rebuildDisabled'('false')
    //         }
    //     }
    // }

    // job("chef-setup-${environment}/webhook-${environment}") {
    //     description("Deploy Chef cookbooks for staging environment using")
    //     keepDependencies(false)
    //     disabled(false)
    //     concurrentBuild(false)
    //     steps {
    //         downstreamParameterized {
    //             trigger("") {
    //                 block {
    //                     buildStepFailure("FAILURE")
    //                     unstable("UNSTABLE")
    //                     failure("FAILURE")
    //                 }
    //             }
    //         }
    //     }
    //     publishers {
    //         mailer("devops@stayntouch.com", false, true)
    //     }
    //     configure {
    //         it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
    //             strategy {
    //                 'daysToKeep'('3')
    //                 'numToKeep'('-1')
    //                 'artifactDaysToKeep'('-1')
    //                 'artifactNumToKeep'('-1')
    //             }
    //         }
    //         it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
    //             'autoRebuild'('false')
    //             'rebuildDisabled'('false')
    //         }
    //     }
    // }

//   }
// }
