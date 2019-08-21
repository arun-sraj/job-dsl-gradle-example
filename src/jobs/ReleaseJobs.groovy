String currentDir = new File(".").getAbsolutePath();
println "${currentDir}"

evaluate(new File("./Templates.groovy"))

def template = new Templates()
template.ConfigureJobs("release")
