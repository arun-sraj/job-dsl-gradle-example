String currentDir = new File(".").getAbsolutePath();
println "${currentDir}"

evaluate(readFileFromWorkspace("src/jobs/Templates.groovy").toString())

def template = new Templates()
template.ConfigureJobs("release")
