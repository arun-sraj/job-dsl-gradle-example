String currentDir = new File(".").getAbsolutePath();
println "${currentDir}"

evaluate(readFileFromWorkspace("src/jobs/Templates.groovy"))

def template = new Templates()
template.ConfigureJobs("release")
