
import utilities.Templates

String environment = 'release'

// Account Setup Job
def awsAccountSetupJob = job("aws-account-setup-$environment")
Templates.awsAccountSetup(awsAccountSetupJob, "$environment")
// Chef Folder Setup
def chefFolder = folder("chef-setup-$environment")
Templates.chefFolderSetup(chefFolder, "$environment")
// Chef Setup Agent Server
def agentChefSetupJob = job("chef-setup-$environment/agent-$environment")
Templates.agentChefSetup(agentChefSetupJob, "$environment")
