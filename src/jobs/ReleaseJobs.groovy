String currentDir = new File(".").getAbsolutePath();
println "${currentDir}"

def sout = new StringBuilder(), serr = new StringBuilder()
def proc = 'ls'.execute()
proc.consumeProcessOutput(sout, serr)
proc.waitForOrKill(1000)
println "out> $sout err> $serr"

evaluate(new File("Templates.groovy"))

def template = new Templates()
template.ConfigureJobs("release")
