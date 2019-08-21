
evaluate(new File(build.getBuildVariables().get('WORKSPACE')+"/src/jobs/Templates.groovy"))

def template = new Templates()
template.ConfigureJobs("release")
