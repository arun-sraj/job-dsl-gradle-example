
evaluate(new File(System.getenv('WORKSPACE')+"/src/jobs/Templates.groovy"))

def template = new Templates()
template.ConfigureJobs("release")
