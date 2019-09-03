
import utilities.Templates
import groovy.transform.ToString

def environmentlist = [release:[branch: "develop", site: "nova"], uat:[branch: "develop", site: "nova"], prodtest:[branch: "develop", site: "nova"], awsstage:[branch: "develop", site: "nova"], prod:[branch: "production", site: "nova"], prodeu:[branch: "production", site: "eufr"]]

for (environment in environmentlist) {
  // Account Setup Job
  def awsAccountSetupJob = job("aws-account-setup-$environment.key")
  Templates.awsAccountSetup(awsAccountSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Folder Setup
  def chefFolder = folder("chef-setup-$environment.key")
  Templates.chefFolderSetup(chefFolder, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Setup Agent Server
  def agentChefSetupJob = job("chef-setup-$environment.key/agent-$environment.key")
  Templates.agentChefSetup(agentChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Setup auth Server
  if(!environment.equals("prodtest"))
  {
    def authChefSetupJob = job("chef-setup-$environment.key/auth-$environment.key")
    Templates.authChefSetup(authChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }

  // Chef Setup glusterfs Server
  if(!environment.equals("prodtest"))
  {
    def glusterfsChefSetupJob = job("chef-setup-$environment.key/glusterfs-$environment.key")
    Templates.glusterfsChefSetup(glusterfsChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }

  // Chef Setup ifc Server
  if(!environment.equals("prodtest"))
  {
    def ifcChefSetupJob = job("chef-setup-$environment.key/ifc-$environment.key")
    Templates.ifcChefSetup(ifcChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }

  // Chef Setup mq Server
  def mqChefSetupJob = job("chef-setup-$environment.key/mq-$environment.key")
  Templates.mqChefSetup(mqChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Setup mq Server
  def mqAppChefSetupJob = job("chef-setup-$environment.key/mq-app-$environment.key")
  Templates.mqAppChefSetup(mqAppChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Setup pms Server
  def pmsChefSetupJob = job("chef-setup-$environment.key/pms-$environment.key")
  Templates.pmsChefSetup(pmsChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Chef Setup postfix Server
  if(!environment.equals("prodtest"))
  {
    def postfixChefSetupJob = job("chef-setup-$environment.key/postfix-$environment.key")
    Templates.postfixChefSetup(postfixChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }

  // Chef Setup webhook Server
  if(!environment.equals("prodtest"))
  {
    def webhookChefSetupJob = job("chef-setup-$environment.key/webhook-$environment.key")
    Templates.webhookChefSetup(webhookChefSetupJob, "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }
  // Chef Setup excavator-copy service
  if(!environment.equals("prodtest"))
  {
    def excavatorCopyJob = job("chef-setup-$environment.key/excavator-copy-$environment.key")
    Templates.excavatorCopy(excavatorCopyJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }

// Deployment

  // Deployment folder
  def deployFolder = folder("deploy-$environment.key")
  Templates.deployFolderSetup(deployFolder, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Auth deploy job
  if(!environment.equals("prodtest"))
  {
    def authDeployJob = job("deploy-$environment.key/auth-$environment.key-deploy")
    Templates.authDelpoySetup(authDeployJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }

  // Pms deploy job
  if(!environment.equals("prodtest"))
  {
    def pmsDeployJob = job("deploy-$environment.key/pms-$environment.key-deploy")
    Templates.pmsDelpoySetup(pmsDeployJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }

  // Ifc deploy job
  if(!environment.equals("prodtest"))
  {
    def ifcDeployJob = job("deploy-$environment.key/ifc-$environment.key-deploy")
    Templates.ifcDelpoySetup(ifcDeployJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }

  // Webhook deploy job
  if(!environment.equals("prodtest"))
  {
    def webhookDeployJob = job("deploy-$environment.key/webhook-$environment.key-deploy")
    Templates.webhookDelpoySetup(webhookDeployJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }

  // excavator deploy job
  if(!environment.equals("prodtest"))
  {
    def excavatorDelpoyJob = job("deploy-$environment.key/excavator-$environment.key-deploy")
    Templates.excavatorDelpoySetup(excavatorDelpoyJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }

  // Zest Web deploy job
  if(!environment.equals("prodtest"))
  {
    def zestWebkDelpoyJob = job("deploy-$environment.key/zest-web-$environment.key-deploy")
    Templates.zestWebkDelpoySetup(zestWebkDelpoyJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }

  // Zest Ui deploy job
  if(!environment.equals("prodtest"))
  {
    def zestUiDelpoyJob = job("deploy-$environment.key/zest-ui-$environment.key-deploy")
    Templates.zestUiDelpoySetup(zestUiDelpoyJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }

  // Infra deploy job
  def infraDelpoyJob = job("infrastructure-setup-$environment.key")
  Templates.infraDelpoySetup(infraDelpoyJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  // Rake setup

  // Rake Folder Setup
  def rakeFolder = folder("rake-task-$environment.key")
  Templates.rakeFolderSetup(rakeFolder, "$environment.key", environment.value.get('site'), environment.value.get('branch'))

  if(!environment.equals("prodtest"))
  {
    def rakeAuthJob = job("rake-task-$environment.key/run-rake-task-auth-$environment.key")
    Templates.rakeAuthSetup(rakeAuthJob , "$environment.key", environment.value.get('site'), environment.value.get('branch'))
  }

  listView("$environment.key") {
      description("Jobs for the $environment.key environment")
      filterBuildQueue()
      filterExecutors()
      jobs {
          names("aws-account-setup-$environment.key", "chef-setup-$environment.key", "deploy-$environment.key", "infrastructure-setup-$environment.key", "rake-task-$environment.key")
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
