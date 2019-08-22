
import utilities.Templates

String environment = 'release'

//Account setup job
def awsAccountSetupJob = job("aws-account-setup-$environment")
Templates.awsAccountSetup(awsAccountSetupJob, "$environment")
