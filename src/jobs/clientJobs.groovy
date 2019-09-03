
import utilities.Templates
import groovy.transform.ToString

def environmentlist = [release:[branch: "develop", site: "nova"], uat:[branch: "develop", site: "nova"], prodtest:[branch: "develop", site: "nova"], awsstage:[branch: "develop", site: "nova"], prod:[branch: "production", site: "nova"], prodeu:[branch: "production", site: "eufr"]]

for (environment in environmentlist) {
  // Account Setup Job
  def awsAccountSetupJob = job("aws-account-setup-$environment.key")
  Templates.awsAccountSetup(awsAccountSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Folder Setup
  def chefFolder = folder("chef-setup-$environment.key")
  Templates.chefFolderSetup(chefFolder, "$environment.key")

  // Chef Setup Agent Server
  def agentChefSetupJob = job("chef-setup-$environment.key/agent-$environment.key")
  Templates.agentChefSetup(agentChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Setup mq Server
  def mqChefSetupJob = job("chef-setup-$environment.key/mq-$environment.key")
  Templates.mqChefSetup(mqChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Setup mq Server
  def mqAppChefSetupJob = job("chef-setup-$environment.key/mq-app-$environment.key")
  Templates.mqAppChefSetup(mqAppChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Setup pms Server
  def pmsChefSetupJob = job("chef-setup-$environment.key/pms-$environment.key")
  Templates.pmsChefSetup(pmsChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Deployment folder
  def deployFolder = folder("deploy-$environment.key")
  Templates.deployFolderSetup(deployFolder, "$environment.key")

  // Infra deploy job
  def infraDelpoyJob = job("infrastructure-setup-$environment.key")
  Templates.infraDelpoySetup(infraDelpoyJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Rake Folder Setup
  folder("rake-task-$environment.key")
  {
    description "This folder includes the jobs for running the rake tasks in all applications for the environment $environment.key."
  }

  // Pms rake job

  def rakePmsJob = job("rake-task-$environment.key/run-rake-task-pms-$environment.key")
  Templates.rakePmsSetup(rakePmsJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Restart Folder Setup
  def restartFolder = folder("restart-services-$environment.key")
  Templates.restartFolderSetup(restartFolder, "$environment.key")

  // Chef Setup auth Server
  if(!environment.key.equals("prodtest"))
  {
    def authChefSetupJob = job("chef-setup-$environment.key/auth-$environment.key")
    Templates.authChefSetup(authChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Setup glusterfs Server

    def glusterfsChefSetupJob = job("chef-setup-$environment.key/glusterfs-$environment.key")
    Templates.glusterfsChefSetup(glusterfsChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Setup ifc Server
    def ifcChefSetupJob = job("chef-setup-$environment.key/ifc-$environment.key")
    Templates.ifcChefSetup(ifcChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Setup postfix Server
    def postfixChefSetupJob = job("chef-setup-$environment.key/postfix-$environment.key")
    Templates.postfixChefSetup(postfixChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Setup webhook Server
    def webhookChefSetupJob = job("chef-setup-$environment.key/webhook-$environment.key")
    Templates.webhookChefSetup(webhookChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Setup excavator-copy service
    def excavatorCopyJob = job("chef-setup-$environment.key/excavator-copy-$environment.key")
    Templates.excavatorCopy(excavatorCopyJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Auth deploy job
    def authDeployJob = job("deploy-$environment.key/auth-$environment.key-deploy")
    Templates.authDelpoySetup(authDeployJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Pms deploy job
    def pmsDeployJob = job("deploy-$environment.key/pms-$environment.key-deploy")
    Templates.pmsDelpoySetup(pmsDeployJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Ifc deploy job
    def ifcDeployJob = job("deploy-$environment.key/ifc-$environment.key-deploy")
    Templates.ifcDelpoySetup(ifcDeployJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Webhook deploy job
    def webhookDeployJob = job("deploy-$environment.key/webhook-$environment.key-deploy")
    Templates.webhookDelpoySetup(webhookDeployJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // excavator deploy job
    def excavatorDelpoyJob = job("deploy-$environment.key/excavator-$environment.key-deploy")
    Templates.excavatorDelpoySetup(excavatorDelpoyJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Zest Web deploy job
    def zestWebkDelpoyJob = job("deploy-$environment.key/rover-zest-web-$environment.key-deploy")
    Templates.zestWebkDelpoySetup(zestWebkDelpoyJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Zest Ui deploy job
    def zestUiDelpoyJob = job("deploy-$environment.key/rover-ui-shared-$environment.key-deploy")
    Templates.zestUiDelpoySetup(zestUiDelpoyJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Auth rake job

    def rakeAuthJob = job("rake-task-$environment.key/run-rake-task-auth-$environment.key")
    Templates.rakeAuthSetup(rakeAuthJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Ifc rake job

    def rakeIfcJob = job("rake-task-$environment.key/run-rake-task-ifc-$environment.key")
    Templates.rakeIfcSetup(rakeIfcJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Webhook rake job

    def rakeWebhookJob = job("rake-task-$environment.key/run-rake-task-webhook-$environment.key")
    Templates.rakeWebhookSetup(rakeWebhookJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))
}

  if(!environment.key.equals("prodtest"))
  {
    def restartAuthAppJob = job("restart-services-$environment.key/restart-auth-app-$environment.key")
    Templates.restartJobSetup(restartAuthAppJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "auth", "app")

    def restartAuthIpcJob = job("restart-services-$environment.key/restart-auth-ipc-$environment.key")
    Templates.restartJobSetup(restartAuthIpcJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "auth", "ipc")

    def restartAuthWkrJob = job("restart-services-$environment.key/restart-auth-wkr-$environment.key")
    Templates.restartJobSetup(restartAuthWkrJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "auth", "wkr")

    def restartIfcAppJob = job("restart-services-$environment.key/restart-ifc-app-$environment.key")
    Templates.restartJobSetup(restartIfcAppJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "ifc", "app")

    def restartIfcIpcJob = job("restart-services-$environment.key/restart-ifc-ipc-$environment.key")
    Templates.restartJobSetup(restartIfcIpcJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "ifc", "ipc")

    def restartIfcSkdJob = job("restart-services-$environment.key/restart-ifc-skd-$environment.key")
    Templates.restartJobSetup(restartIfcSkdJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "ifc", "skd")

    def restartIfcWkrJob = job("restart-services-$environment.key/restart-ifc-wkr-$environment.key")
    Templates.restartJobSetup(restartIfcWkrJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "ifc", "wkr")

    def restartPmsAppJob = job("restart-services-$environment.key/restart-pms-app-$environment.key")
    Templates.restartJobSetup(restartPmsAppJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "pms", "app")

    def restartPmsConJob = job("restart-services-$environment.key/restart-pms-con-$environment.key")
    Templates.restartJobSetup(restartPmsConJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "pms", "con")

    def restartPmsIpcJob = job("restart-services-$environment.key/restart-pms-ipc-$environment.key")
    Templates.restartJobSetup(restartPmsIpcJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "pms", "ipc")

    def restartPmsRsqJob = job("restart-services-$environment.key/restart-pms-rsq-$environment.key")
    Templates.restartJobSetup(restartPmsRsqJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "pms", "rsq")

    def restartPmsWkrJob = job("restart-services-$environment.key/restart-pms-wkr-$environment.key")
    Templates.restartJobSetup(restartPmsWkrJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "pms", "wkr")

    def restartWebhookIpcJob = job("restart-services-$environment.key/restart-webhook-ipc-$environment.key")
    Templates.restartJobSetup(restartWebhookIpcJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "webhook", "ipc")

    def restartWebhookWkrJob = job("restart-services-$environment.key/restart-webhook-wkr-$environment.key")
    Templates.restartJobSetup(restartWebhookWkrJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "webhook", "wkr")
  }
  else {

    def restartPmsAppJob = job("restart-services-$environment.key/restart-pms-app-$environment.key")
    Templates.restartJobSetup(restartPmsAppJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "pms", "app")

    def restartPmsRsqJob = job("restart-services-$environment.key/restart-pms-rsq-$environment.key")
    Templates.restartJobSetup(restartPmsRsqJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'), "pms", "rsq")

  }

// Main list view
  listView("$environment.key") {
      description("Jobs for the $environment.key environment")
      filterBuildQueue()
      filterExecutors()
      jobs {
          names("aws-account-setup-$environment.key", "chef-setup-$environment.key", "deploy-$environment.key", "infrastructure-setup-$environment.key", "rake-task-$environment.key", "restart-services-$environment.key")
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
