// Main job
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
        it / "properties" / "jenkins.model.BuildDiscarderProperty" {
            strategy {
                "daysToKeep"("3")
                "numToKeep"("-1")
                "artifactDaysToKeep"("-1")
                "artifactNumToKeep"("-1")
            }
        }
        it / "properties" / "com.coravy.hudson.plugins.github.GithubProjectProperty" {
            "projectUrl"("https://github.com/StayNTouch/rover-cloud-formation/")
            displayName()
        }
        it / "properties" / "com.sonyericsson.rebuild.RebuildSettings" {
            "autoRebuild"("false")
            "rebuildDisabled"("false")
        }
    }
}

//base-image-creator
folder("base-image-creator") {
    description "This includes all the job to create the base image."
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
        it / "properties" / "com.sonyericsson.rebuild.RebuildSettings" {
            "autoRebuild"("false")
            "rebuildDisabled"("false")
        }
    }
}
//chef setup

folder("chef-setup") {
    description "This job will replace all template instances with the latest Chef configuration.  We will need one job per env & application type.  The steps it will follow for each template type are: \
    Start new template instance \
    Bootstrap it with Chef Server \
    Execute chef client to configure node (with startup scripts disabled / commented out) \
    Shutdown template instance \
    Take template image \
    Terminate template instance"
    views {
        listView("auth") {
            description("Auth chef setup jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
                name("auth")
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
        listView("general service") {
            description("All general service jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
                names("postfix", "mq-app", "mq", "glusterfs", " agent")
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
                name("ifc")
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
                name("pms")
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
                name("webhook")
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

job("chef-setup/agent") {
    description("Job to deploy agent server.")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify the Github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify the environment name
Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name
Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/chef-setup/general-services/agent/build.sh")
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

job("chef-setup/auth") {
    description()
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name
Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name
Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/chef-setup/auth/auth/build.sh")
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

job("chef-setup/ifc") {
    description()
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name
Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name
Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/chef-setup/ifc/ifc/build.sh")
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

job("chef-setup/mq") {
    description("Job to deploy RabbitMQ servers.")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify the Github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify the environment name
Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name
Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/chef-setup/general-services/mq/build.sh")
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

job("chef-setup/mq-app") {
    description()
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name
Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name
Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/chef-setup/general-services/mq/asg.sh")
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

job("chef-setup/pms") {
    description()
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name
Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name
Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/chef-setup/pms/pms/build.sh")
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

job("chef-setup/postfix") {
    description("""#1. Create template server for postfix

#2. Deploy chef in it

#3. Shutdown the server and create image from it

#4. Use the image to create autoscaling group

#5. Terminate template server""")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name
Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name
Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/chef-setup/general-services/postfix/build.sh")
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

job("chef-setup/webhook") {
    description()
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name
Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name
Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/chef-setup/webhook/webhook/build.sh")
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

job("chef-setup/glusterfs") {
    description("Job to deploy Glusterfs servers.")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify the Github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify the environment name
Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name
Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/chef-setup/general-services/glusterfs/build.sh")
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

// Custvpn service setup.
folder("custvpn") {
    description "Custom VPN for ipsec connection."
}

job("custvpn/custvpn-server-setup") {
    description("""#1. Create server for IPsec VPN \
    #2. Adding customer subnets to the route tables. \
    #2. Deploy chef in it \
    """)
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/custvpn-server/custvpn-server-setup.sh")
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

// Deploy job configuration.
// Deploy Folder
    folder("deploy") {
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
            description("Ui chef deploy jobs")
            filterBuildQueue()
            filterExecutors()
            jobs {
                names("rover-ui-shared", "rover-zest-web")
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
                names("01-create-template-instance-from-chef-template-image-auth", "02-deploy-via-capistrano-to-template-instance-auth", "04-cleanup-auth")
                regex("03-.+-swap-asg-auth.+")
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
                names("01-create-template-instance-from-chef-template-image-ifc", "02-deploy-via-capistrano-to-template-instance-ifc", "04-cleanup-ifc")
                regex("03-.+-swap-asg-ifc.+")
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
                names("01-create-template-instance-from-chef-template-image-pms", "02-deploy-via-capistrano-to-template-instance-pms", "04-cleanup-pms")
                regex("03-.+-swap-asg-pms.+")
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
                names("01-create-template-instance-from-chef-template-image-webhook", "02-deploy-via-capistrano-to-template-instance-webhook", "04-cleanup-webhook")
                regex("03-.+-swap-asg-webhook.+")
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
                name("excavator")
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
// Deploy Jobs

// Auth deploy job.
job("./deploy/01-create-template-instance-from-chef-template-image-auth") {
    description("Creates all template servers required to deploy auth")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/auth/01-create-template-instance-from-chef-template-image-auth/build.sh")
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

job("./deploy/02-deploy-via-capistrano-to-template-instance-auth") {
    description()
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SETUP_NEW_DB", "no", "")
    }
    scm {
        git {
            remote {
                github("StayNTouch/rover-auth", "ssh")
            }
            branch("origin/\$BRANCH")
        }
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("""eval `ssh-agent -s`
echo \$SSH_AGENT_PID > ssh-agent.pid
ssh-add
bundle install
bundle exec cap \$ENVIRONMENT deploy BRANCH=\$BRANCH SETUP_NEW_DB=\$SETUP_NEW_DB""")
        shell("""kill -9 `cat ssh-agent.pid`
echo 'completed'""")
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

job("./deploy/03-A-swap-asg-auth-app") {
    description("This job creates an auto-scaling group for AUTH app server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/auth/03-A-swap-asg-auth-app/build.sh")
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

job("./deploy/03-B-swap-asg-auth-wkr") {
    description("This job creates an auto-scaling group for AUTH wkr server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/auth/03-B-swap-asg-auth-wkr/build.sh")
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

job("./deploy/03-C-swap-asg-auth-ipc") {
    description("This job creates an auto-scaling group for AUTH wkr server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/auth/03-C-swap-asg-auth-ipc/build.sh")
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

job("./deploy/03-D-swap-asg-auth-railsc") {
    description("This job creates an auto-scaling group for AUTH wkr server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/auth/03-D-swap-asg-auth-railsc/build.sh")
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

job("./deploy/04-cleanup-auth") {
    description("This job clean up the deployment AUTH template stack")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/auth/04-cleanup-auth/build.sh")
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

// Ifc deploy Jobs

job("./deploy/01-create-template-instance-from-chef-template-image-ifc") {
    description("Creates all template servers required to deploy ifc")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/ifc/01-create-template-instance-from-chef-template-image-ifc/build.sh")
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

job("./deploy/02-deploy-via-capistrano-to-template-instance-ifc") {
    description()
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SETUP_NEW_DB", "no", "")
    }
    scm {
        git {
            remote {
                github("StayNTouch/rover-ifc", "ssh")
            }
            branch("origin/\$BRANCH")
        }
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("""eval `ssh-agent -s`
echo \$SSH_AGENT_PID > ssh-agent.pid
ssh-add
bundle install
bundle exec cap \$ENVIRONMENT deploy BRANCH=\$BRANCH SETUP_NEW_DB=\$SETUP_NEW_DB""")
        shell("""kill -9 `cat ssh-agent.pid`
echo 'completed'""")
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

job("./deploy/03-A-swap-asg-ifc-app") {
    description("This job creates an auto-scaling group for IFC app server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/ifc/03-A-swap-asg-ifc-app/build.sh")
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

job("./deploy/03-B-swap-asg-ifc-wkr") {
    description("This job creates an auto-scaling group for IFC wkr server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/ifc/03-B-swap-asg-ifc-wkr/build.sh")
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

job("./deploy/03-C-swap-asg-ifc-skd") {
    description("This job creates an auto-scaling group for IFC skd server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/ifc/03-C-swap-asg-ifc-skd/build.sh")
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

job("./deploy/03-D-swap-asg-ifc-ipc") {
    description("This job creates an auto-scaling group for IFC skd server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/ifc/03-D-swap-asg-ifc-ipc/build.sh")
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

job("./deploy/03-E-swap-asg-ifc-railsc") {
    description("This job creates an auto-scaling group for IFC wkr server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/ifc/03-E-swap-asg-ifc-railsc/build.sh")
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

job("./deploy/04-cleanup-ifc") {
    description("This job clean up the deployment IFC template stack")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/ifc/04-cleanup-ifc/build.sh")
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

// pms deploy jobs

job("./deploy/02-deploy-via-capistrano-to-template-instance-pms") {
    description()
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SETUP_NEW_DB", "no", "")
    }
    scm {
        git {
            remote {
                github("StayNTouch/pms", "ssh")
            }
            branch("origin/\$BRANCH")
        }
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("""eval `ssh-agent -s`
echo \$SSH_AGENT_PID > ssh-agent.pid
ssh-add
bundle install --without rmagick
bundle exec cap \$ENVIRONMENT deploy BRANCH=\$BRANCH SETUP_NEW_DB=\$SETUP_NEW_DB""")
        shell("""kill -9 `cat ssh-agent.pid`
echo 'completed'""")
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

job("./deploy/01-create-template-instance-from-chef-template-image-pms") {
    description("Creates all template servers required to deploy pms")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/pms/01-create-template-instance-from-chef-template-image-pms/build.sh")
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

job("./deploy/03-A-swap-asg-pms-app") {
    description("This job creates an auto-scaling group for PMS app server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/pms/03-A-swap-asg-pms-app/build.sh")
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

job("./deploy/03-B-swap-asg-pms-con") {
    description("This job creates an auto-scaling group for PMS con server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/pms/03-B-swap-asg-pms-con/build.sh")
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

job("./deploy/03-C-swap-asg-pms-rsq") {
    description("This job creates an auto-scaling group for PMS rsq server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/pms/03-C-swap-asg-pms-rsq/build.sh")
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

job("./deploy/03-D-swap-asg-pms-wkr") {
    description("This job creates an auto-scaling group for PMS wkr server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/pms/03-D-swap-asg-pms-wkr/build.sh")
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

job("./deploy/03-E-swap-asg-pms-ipc") {
    description("This job creates an auto-scaling group for PMS wkr server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/pms/03-E-swap-asg-pms-ipc/build.sh")
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

job("./deploy/03-F-swap-asg-pms-railsc") {
    description("This job creates an auto-scaling group for PMS wkr server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/pms/03-F-swap-asg-pms-railsc/build.sh")
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

job("./deploy/04-cleanup-pms") {
    description("This job clean up the deployment PMS template stack")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/pms/04-cleanup-pms/build.sh")
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

// Webhook deploy jobs

job("./deploy/01-create-template-instance-from-chef-template-image-webhook") {
    description("Creates all template servers required to deploy ifc")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/webhook/01-create-template-instance-from-chef-template-image-webhook/build.sh")
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

job("./deploy/03-A-swap-asg-webhook-wkr") {
    description("This job creates an auto-scaling group for WEBHOOK app server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/webhook/03-A-swap-asg-webhook-wkr/build.sh")
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

job("./deploy/02-deploy-via-capistrano-to-template-instance-webhook") {
    description()
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SETUP_NEW_DB", "no", "")
    }
    scm {
        git {
            remote {
                github("StayNTouch/rover-webhook", "ssh")
            }
            branch("origin/\${branch}")
        }
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("""eval `ssh-agent -s`
echo \$SSH_AGENT_PID > ssh-agent.pid
ssh-add
bundle install
bundle exec cap \${ENVIRONMENT} deploy BRANCH=\$BRANCH SETUP_NEW_DB=\$SETUP_NEW_DB""")
        shell("""kill -9 `cat ssh-agent.pid`
echo 'completed'""")
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

job("./deploy/03-B-swap-asg-webhook-ipc") {
    description("This job creates an auto-scaling group for WEBHOOK app server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/webhook/03-B-swap-asg-webhook-ipc/build.sh")
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

job("./deploy/03-C-swap-asg-webhook-railsc") {
    description("This job creates an auto-scaling group for WEBHOOK railsc server instances with the latest code")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/webhook/03-C-swap-asg-webhook-railsc/build.sh")
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

job("./deploy/04-cleanup-webhook") {
    description("This job clean up the deployment WEBHOOK template stack")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/webhook/04-cleanup-webhook/build.sh")
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

// Deploy job for excavator

job("./deploy/excavator") {
    description("This job is for deploying the excavator service.")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/excavator/build.sh")
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

// Deploy UI app

job("./deploy/rover-ui-shared") {
    description("Build rover login UI app and sync into login app s3 bucket")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/UI/rover-ui-shared/build.sh")
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
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
            'autoRebuild'('false')
            'rebuildDisabled'('false')
        }
    }
}

job("./deploy/rover-zest-web") {
    description("Build rover login UI app and sync into zest web app s3 bucket")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "awsstage", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/deploy/UI/rover-zest-web/build.sh")
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
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
            'autoRebuild'('false')
            'rebuildDisabled'('false')
        }
    }
}

// Infrastructure setup job

job("infrastructure-setup") {
    description("""This job will configure all AWS services needed for a new or updated environment/site.  The job will run one parent Cloudformation template with nested child templates.  The setup includes:
Account setup

Roles

Permissions

Cloudtrail

Cloud Watch

VPC

Subnets

Routing tables

Security Groups

Aurora MySQL

Aurora PostgreSQL

ElastiCache Memached

ElastiCache Redis

EFS

Load balancers

NAT Gateways

Internet Gateways

Route 53 DNS

Auto Scaling Groups (will be replaced in deploy job)""")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", "")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/infrastructure-setup/build.sh")
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

// rake-task

// Rake task main folder

folder("rake-task") {
    description "This folder includes the jobs for running the rake tasks in all applications."
}

// Rake task sub folders

folder("rake-task/auth") {
    description "This folder includes the job for running the rake tasks in auth."
}

folder("rake-task/ifc") {
    description "This folder includes the job for running the rake tasks in ifc."
}

folder("rake-task/pms") {
    description "This folder includes the job for running the rake tasks in pms."
}

folder("rake-task/webhook") {
    description "This folder includes the job for running the rake tasks in webhook."
}

folder("rake-task/cleanup") {
    description "This folder includes the job for running the rake tasks in webhook."
}

// Rake task jobs

job("./rake-task/auth/auth-rake-task") {
    description("This build is to run the rake task on auth.")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
        stringParam("RAKE_TASK_NAME", "", """Specify your rake task name and its argument if any. No need to specify RAILS_ENV For example: pms:purge_old_overlay_reservations[19]""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/utilities/rake-server/auth/build.sh")
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

job("./rake-task/ifc/ifc-rake-task") {
    description("This build is to run the rake task on ifc.")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
        stringParam("RAKE_TASK_NAME", "", """Specify your rake task name and its argument if any. No need to specify RAILS_ENV For example: pms:purge_old_overlay_reservations[19]""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/utilities/rake-server/ifc/build.sh")
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

job("./rake-task/pms/pms-rake-task") {
    description("This build is to run the rake task on pms.")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
        stringParam("RAKE_TASK_NAME", "", """Specify your rake task name and its argument if any. No need to specify RAILS_ENV For example: pms:purge_old_overlay_reservations[19]""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/utilities/rake-server/pms/build.sh")
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

job("./rake-task/webhook/webhook-rake-task") {
    description("This build is to run the rake task on webhook.")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
        stringParam("RAKE_TASK_NAME", "", """Specify your rake task name and its argument if any. No need to specify RAILS_ENV For example: pms:purge_old_overlay_reservations[19]""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/utilities/rake-server/webhook/build.sh")
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

job("./rake-task/cleanup/clean-up-rake-task-on-failure") {
    description("This build is to clean rake resources upon build failure.")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
        stringParam("APP_TYPE", "", "pms, ifc, auth, webhook")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/utilities/rake-server/cleanup/build.sh")
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

// Restart service job folder

folder("restart-services") {
    description "This folder includes the jobs to restart the services."
}

// Restart Jobs

job("./restart-services/restart-auth-app") {
    description("This job Restart AUTH app auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/auth/restart-auth-app/build.sh")
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

job("./restart-services/restart-auth-ipc") {
    description("This job Restart AUTH ipc auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/auth/restart-auth-ipc/build.sh")
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

job("./restart-services/restart-auth-wkr") {
    description("This job Restart AUTH wkr auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/auth/restart-auth-wkr/build.sh")
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

job("./restart-services/restart-ifc-app") {
    description("This job Restart IFC app auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/ifc/restart-ifc-app/build.sh")
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

job("./restart-services/restart-ifc-ipc") {
    description("This job Restart IFC ipc auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/ifc/restart-ifc-ipc/build.sh")
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

job("./restart-services/restart-ifc-skd") {
    description("This job Restart IFC skd auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/ifc/restart-ifc-skd/build.sh")
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

job("./restart-services/restart-ifc-wkr") {
    description("This job Restart IFC wkr auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/ifc/restart-ifc-wkr/build.sh")
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

job("./restart-services/restart-pms-app") {
    description("This job Restart PMS app auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/pms/restart-pms-app/build.sh")
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

job("./restart-services/restart-pms-con") {
    description("This job Restart PMS app auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/pms/restart-pms-con/build.sh")
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

job("./restart-services/restart-pms-ipc") {
    description("This job Restart PMS ipc auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/pms/restart-pms-ipc/build.sh")
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

job("./restart-services/restart-pms-rsq") {
    description("This job Restart PMS app auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/pms/restart-pms-rsq/build.sh")
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

job("./restart-services/restart-pms-wkr") {
    description("This job Restart PMS wkr auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/pms/restart-pms-wkr/build.sh")
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

job("./restart-services/restart-webhook-ipc") {
    description("This job Restart WEBHOOK ipc auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/webhook/restart-webhook-ipc/build.sh")
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

job("./restart-services/restart-webhook-wkr") {
    description("This job Restart WEBHOOK wkr auto scaling group and instances with the latest ami")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/restart-services/webhook/restart-webhook-wkr/build.sh")
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

// Secret-manager-setup

job("secrets-manager-setup") {
    description("This job will configure all AWS secret manager secrets needed for a new or updated environment/site.")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", "")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/secrets-manager-setup/build.sh")
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
            'projectUrl'('https://github.com/StayNTouch/rover-jenkins-scripts/')
            displayName()
        }
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
            'autoRebuild'('false')
            'rebuildDisabled'('false')
        }
    }
}

// Utilities folder

folder("utilities") {
    description "Running utility jobs."
}

// Utilities jobs

job("./utilities/database-copy-scheduler-from-one-to-another-account") {
    description("""This job will configure a database copy schedule between two accounts

It will accept appropriate parameters like the source and destination account numbers/roles

It configures the job to execute daily at 10:00 pm EST

It will keep the last 2 snapshots

It will load the source and destination CloudFormation templates into the respective accounts""")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify GitHub branch name to deploy")
        stringParam("SRC_ENVIRONMENT", "", """Specify environment name Eg: staging, release, uat""")
        stringParam("DEST_ENVIRONMENT", "", """Specify environment name Eg: staging, release, uat""")
        stringParam("AWS_REGION", "us-east-1", "Specify AWS region name")
        stringParam("SRC_IAM_ROLE", "", "")
        stringParam("DEST_IAM_ROLE", "", "")
        stringParam("S3_PREFIX", "snt-cftemplates/dev", "")
        stringParam("CODEBUCKET", "DEFAULT_BUCKET", "Name of the bucket that contains the lambda functions to deploy. Leave the default value to download the code from the AWS Managed buckets")
        stringParam("CLUSTERNAMEPATTERN", "ALL_CLUSTERS", "Python regex for matching cluster identifiers to backup. Use \\\"ALL_CLUSTERS\\\" to back up every Aurora cluster in the region.")
        stringParam("BACKUPINTERVAL", "24", "Interval for backups in hours. Default is 24")
        stringParam("DESTINATIONACCOUNT", "525402942753", "Destination account number (ProdTest) with no dashes.")
        stringParam("BACKUPSCHEDULE", "0 23", """Specify "minutes hours" in UTC. Backup schedule in Cloudwatch Event cron format. Needs to run at least once for every Interval. The default value runs once every at 6:00 PM EST.""")
        stringParam("RETENTIONDAYS", "2", "Number of days to keep snapshots in retention before deleting them")
        stringParam("LOGLEVEL", "ERROR", "Log level for Lambda functions (DEBUG, INFO, WARN, ERROR, CRITICAL are valid values).")
        stringParam("SNAPSHOTPATTERN", "ALL_SNAPSHOTS", "Python regex for matching cluster identifiers to backup. Use \\\"ALL_SNAPSHOTS\\\" to back up every Aurora cluster in the region.")
        stringParam("SITE", "nova", "")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/database-copy-scheduler/build.sh")
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

job("./utilities/database-disaster-recovery") {
    description("""This job will configure a database copy schedule between two accounts

It will accept appropriate parameters like the source and destination account numbers/roles

It will keep the last 2 snapshots

It will load the source and destination CloudFormation templates into the respective accounts""")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify GitHub branch name to deploy")
        stringParam("ENVIRONMENT", "", """Specify environment name Eg: staging, release, uat""")
        stringParam("AWS_REGION", "us-east-1", "Specify AWS region name")
        stringParam("DEST_AWS_REGION", "us-west-2", "Specify AWS region name")
        stringParam("IAM_ROLE", "", "")
        stringParam("CODEBUCKET", "DEFAULT_BUCKET", "Name of the bucket that contains the lambda functions to deploy. Leave the default value to download the code from the AWS Managed buckets")
        stringParam("CROSSACCOUNTCOPY", "FALSE", "Set to FALSE if your source snapshots are not on a different account")
        stringParam("RETENTIONDAYS", "7", "Number of days to keep snapshots in retention before deleting them")
        stringParam("LOGLEVEL", "ERROR", "Log level for Lambda functions (DEBUG, INFO, WARN, ERROR, CRITICAL are valid values).")
        stringParam("SNAPSHOTPATTERN", "ALL_SNAPSHOTS", "Python regex for matching cluster identifiers to backup. Use \\\"ALL_SNAPSHOTS\\\" to back up every Aurora cluster in the region.")
        stringParam("SITE", "nova", "")
        stringParam("CREATE_SCHEDULE", "no", "")
        stringParam("BACKUPINTERVAL", "1", "")
        stringParam("BACKUPSCHEDULE", "0 23", "")
        stringParam("CLUSTERNAMEPATTERN", "", "")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/database-disaster-recovery/build.sh")
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

job("./utilities/excavator-copy") {
    description("This build is to run the excavator copy process.")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
        stringParam("AWS_REGION", "us-east-1", "Specify AWS region name")
        stringParam("IAM_ROLE", "", "")
        stringParam("EC2_KEY_PAIR", "", "Key pair name")
        stringParam("MINE_NAME", "", "")
        stringParam("MINE_MODEL", "", "")
        stringParam("PMS_MODEL", "", "")
        stringParam("CAP_ENVIRONMENT", "staging", "")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/utilities/excavator/copy.sh")
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

job("./utilities/export-mysql") {
    description("Exports mysql to a compressed dump file")
    keepDependencies(false)
    parameters {
        stringParam("BRANCH", "develop", "Specify github branch name to deploy")
        stringParam("ENVIRONMENT", "staging", """Specify environment name Eg: staging, release, uat""")
        stringParam("SITE", "nova", """Specify site name Eg: nova, ohio""")
        stringParam("AWS_REGION", "us-east-1", "Specify AWS region name")
        stringParam("IAM_ROLE", "", "")
        stringParam("DATABASE", "pms", "")
    }
    disabled(false)
    concurrentBuild(true)
    steps {
        shell("bash ./StayNTouch/utilities/export-mysql.sh")
    }
    publishers {
        archiveArtifacts {
            pattern("*.sql.gz")
            allowEmpty(false)
            onlyIfSuccessful(false)
            fingerprint(false)
            defaultExcludes(true)
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


// Main view StyaNTouch

listView("StayNTouch") {
    description("All root jobs for project A")
    filterBuildQueue()
    filterExecutors()
    jobs {
        names("aws-account-setup", "base-image-creator", "chef-setup", "custvpn", "deploy", "infrastructure-setup", "rake-task", "restart-services", "secrets-manager-setup", "utilities")
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


