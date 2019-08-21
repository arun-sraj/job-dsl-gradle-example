
evaluate(new File(${workspace}+"/src/jobs/Templates.groovy"))

def template = new Templates()
template.ConfigureJobs("release")
