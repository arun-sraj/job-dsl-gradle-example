
import utilities.Templates

String environment = 'release'

// Account setup job
def awsAccountSetupJob = job("aws-account-setup-$environment")
Templates.awsAccountSetup(awsAccountSetupJob, "$environment")
// Chef Folder setup
def chefFolder = folder("chef-setup-$environment")
Templates.chefFolderSetup(chefFolder, "$environment")
