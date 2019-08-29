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

// Main view StyaNTouch

listView("StayNTouch") {
    description("All root jobs for project A")
    filterBuildQueue()
    filterExecutors()
    jobs {
        names("aws-account-setup", "base-image-creator", "chef-setup", "custvpn", "deploy")
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


