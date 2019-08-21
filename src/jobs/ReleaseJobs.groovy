
evaluate(new File(env.WORKSPACE+"/src/jobs/Templates.groovy"))

def template = new Templates()
template.ConfigureJobs("release")
