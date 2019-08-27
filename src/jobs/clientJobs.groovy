
import utilities.Templates
import groovy.transform.ToString

def environmentlist = [release:[branch: "develop", site: "nova"], uat:[branch: "develop", site: "nova"], prodtest:[branch: "develop", site: "nova"], awsstage:[branch: "develop", site: "nova"], prod:[branch: "develop", site: "nova"], prodeu:[branch: "develop", site: "eufr"]]

for (environment in environmentlist) {
  // Account Setup Job
  def awsAccountSetupJob = job("aws-account-setup-$environment.key")
  Templates.awsAccountSetup(awsAccountSetupJob, "$environment.key")

  // Chef Folder Setup
  def chefFolder = folder("chef-setup-$environment.key")
  Templates.chefFolderSetup(chefFolder, "$environment.key")

  // Chef Setup Agent Server
  def agentChefSetupJob = job("chef-setup-$environment.key/agent-$environment.key")
  Templates.agentChefSetup(agentChefSetupJob, "$environment.key")

  // Chef Setup auth Server
  if(!environment.equals("prodtest"))
  {
    def authChefSetupJob = job("chef-setup-$environment.key/auth-$environment.key")
    Templates.authChefSetup(authChefSetupJob, "$environment.key")
  }

  // Chef Setup glusterfs Server
  if(!environment.equals("prodtest"))
  {
    def glusterfsChefSetupJob = job("chef-setup-$environment.key/glusterfs-$environment.key")
    Templates.glusterfsChefSetup(glusterfsChefSetupJob, "$environment.key")
  }

  // Chef Setup ifc Server
  if(!environment.equals("prodtest"))
  {
    def ifcChefSetupJob = job("chef-setup-$environment.key/ifc-$environment.key")
    Templates.ifcChefSetup(ifcChefSetupJob, "$environment.key")
  }

  // Chef Setup mq Server
  def mqChefSetupJob = job("chef-setup-$environment.key/mq-$environment.key")
  Templates.mqChefSetup(mqChefSetupJob, "$environment.key")

  // Chef Setup pms Server
  def pmsChefSetupJob = job("chef-setup-$environment.key/pms-$environment.key")
  Templates.pmsChefSetup(pmsChefSetupJob, "$environment.key")

  // Chef Setup postfix Server
  if(!environment.equals("prodtest"))
  {
    def postfixChefSetupJob = job("chef-setup-$environment.key/postfix-$environment.key")
    Templates.postfixChefSetup(postfixChefSetupJob, "$environment.key")
  }

  // Chef Setup webhook Server
  if(!environment.equals("prodtest"))
  {
    def webhookChefSetupJob = job("chef-setup-$environment.key/webhook-$environment.key")
    Templates.webhookChefSetup(webhookChefSetupJob, "$environment.key")
  }

  listView("$environment.key") {
      description("Jobs for the $environment.key environment")
      filterBuildQueue()
      filterExecutors()
      jobs {
          names("aws-account-setup-$environment.key", "chef-setup-$environment.key")
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
