
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

folder("base-image-creator") {
    description 'This includes all the job to create the base image.'
}

job("base-image-creator/ubuntu-16-04-base-image-creator") {
    description("This job will create the patched image of latest ubuntu 16.04 os.")
    keepDependencies(false)
    parameters {
        stringParam("IMAGE_ID", "", "To ask the job to use specific AMI. if not specified then it will take the latest ubuntu 16.04.")
        stringParam("EC2_KEY_PAIR", "ChefWorkStationJenkinsSlave", "")
        stringParam("INSTANCE_TYPE", "t2.small", "")
        stringParam("OTHER_COMMANDS", "", "The commands (other than sudo apt-get update and sudo apt-get upgrade) that should be executed during the instance patch process")
        stringParam("AWS_REGION", "us-east-1", "")
    }
    disabled(false)
    concurrentBuild(false)
    steps {
        shell("bash ./StayNTouch/base-image-creator/create-base-image.sh")
    }
    configure {
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
            'autoRebuild'('false')
            'rebuildDisabled'('false')
        }
    }
}
