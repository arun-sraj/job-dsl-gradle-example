evaluate(new File("./JobTemplates.groovy"))

def template = new JobTemplates()
template.ConfigureJobs("release")
