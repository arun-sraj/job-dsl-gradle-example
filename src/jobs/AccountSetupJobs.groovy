job("aws-account-setup") {
    description("""This job will configure the AWS account
""")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("SITE", "nova", "Specify SNT site name")
        stringParam("ENVIRONMENT", "", "")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/aws-account-setup/build.sh")
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
            'projectUrl'('https://github.com/StayNTouch/rover-cloud-formation/')
            displayName()
        }
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
            'autoRebuild'('false')
            'rebuildDisabled'('false')
        }
    }
}

job("aws-account-setup-prodeu") {
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

job("aws-account-setup-prodtest") {
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

job("aws-account-setup-production") {
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

job("aws-account-setup-release") {
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

job("aws-account-setup-sandbox") {
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

job("aws-account-setup-uat") {
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
