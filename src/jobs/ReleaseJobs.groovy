
import utilities.Templates

// evaluate(readFileFromWorkspace("src/jobs/Templates.groovy"))

// def template = new Templates()
// template.ConfigureJobs("release")
//Templates.ConfigureJobs("release")
def myJob = job('example')
Templates.addMyFeature(myJob)
