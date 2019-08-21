// Main job
job("aws-account-setup-staging") {
    description()
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

//chef setup

folder("chef-setup-staging") {
    description 'This job will replace all template instances with the latest Chef configuration.  We will need one job per env & application type.  The steps it will follow for each template type are: \
    Start new template instance \
    Bootstrap it with Chef Server \
    Execute chef client to configure node (with startup scripts disabled / commented out) \
    Shutdown template instance \
    Take template image \
    Terminate template instance'
    views {
        listView('auth') {
            description('Auth chef setup jobs')
            filterBuildQueue()
            filterExecutors()
            jobs {
                name('auth-staging')
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
        listView('general service') {
            description('All general service jobs')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('postfix-staging', 'mq-app-staging', 'mq-staging', 'glusterfs-staging', ' agent-staging')
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
        listView('ifc') {
            description('ifc chef setup jobs')
            filterBuildQueue()
            filterExecutors()
            jobs {
                name('ifc-staging')
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
        listView('pms') {
            description('pms chef setup jobs')
            filterBuildQueue()
            filterExecutors()
            jobs {
                name('pms-staging')
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
        listView('webhook') {
            description('webhook chef setup jobs')
            filterBuildQueue()
            filterExecutors()
            jobs {
                name('webhook-staging')
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
job("chef-setup-staging/agent-staging") {
    description("Job for deploying agent service for the staging environment.")
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

job("chef-setup-staging/auth-staging") {
    description("Deploy Chef cookbooks for staging environment using")
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

job("chef-setup-staging/glusterfs-staging") {
    description("Job for deploying glusterfs service for the staging environment.")
    keepDependencies(false)
    disabled(true)
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

job("chef-setup-staging/ifc-staging") {
    description("Deploy Chef cookbooks of staging environment for ifc servers")
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

job("chef-setup-staging/mq-app-staging") {
    description("Deploy Chef cookbooks for staging environment using")
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

job("chef-setup-staging/mq-staging") {
    description("Job for deploying mq service for the staging environment.")
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

job("chef-setup-staging/pms-staging") {
    description("Deploy Chef cookbooks for staging environment using")
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

job("chef-setup-staging/postfix-staging") {
    description("Job for deploying postfix service for the staging environment.")
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

job("chef-setup-staging/webhook-staging") {
    description("Deploy Chef cookbooks for staging environment using")
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
