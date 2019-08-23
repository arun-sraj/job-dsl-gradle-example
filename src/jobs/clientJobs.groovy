
import utilities.Templates

def environmentlist = ["release", "uat", "prodtest"]
for (environment in environmentlist) {
  // Account Setup Job
  def awsAccountSetupJob = job("aws-account-setup-$environment")
  Templates.awsAccountSetup(awsAccountSetupJob, "$environment")

  // Chef Folder Setup
  def chefFolder = folder("chef-setup-$environment")
  Templates.chefFolderSetup(chefFolder, "$environment")

  // Chef Setup Agent Server
  def agentChefSetupJob = job("chef-setup-$environment/agent-$environment")
  Templates.agentChefSetup(agentChefSetupJob, "$environment")

  // Chef Setup auth Server
  def authChefSetupJob = job("chef-setup-$environment/auth-$environment")
  Templates.authChefSetup(authChefSetupJob, "$environment")

  // Chef Setup glusterfs Server
  def glusterfsChefSetupJob = job("chef-setup-$environment/glusterfs-$environment")
  Templates.glusterfsChefSetup(glusterfsChefSetupJob, "$environment")

  // Chef Setup ifc Server
  def ifcChefSetupJob = job("chef-setup-$environment/ifc-$environment")
  Templates.ifcChefSetup(ifcChefSetupJob, "$environment")

  // Chef Setup mq Server
  def mqChefSetupJob = job("chef-setup-$environment/mq-$environment")
  Templates.mqChefSetup(mqChefSetupJob, "$environment")

  // Chef Setup pms Server
  def pmsChefSetupJob = job("chef-setup-$environment/pms-$environment")
  Templates.pmsChefSetup(pmsChefSetupJob, "$environment")

  // Chef Setup postfix Server
  def postfixChefSetupJob = job("chef-setup-$environment/postfix-$environment")
  Templates.postfixChefSetup(postfixChefSetupJob, "$environment")

  // Chef Setup webhook Server
  def webhookChefSetupJob = job("chef-setup-$environment/webhook-$environment")
  Templates.webhookChefSetup(webhookChefSetupJob, "$environment")

  listView("$environment") {
      description("Jobs for the $environment environment")
      filterBuildQueue()
      filterExecutors()
      jobs {
          names("aws-account-setup-$environment", "chef-setup-$environment")
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
