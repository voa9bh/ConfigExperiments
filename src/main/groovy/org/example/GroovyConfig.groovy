package org.example

class GroovyConfig {
  void run() {
    println "Running GroovyConfig..."

    procs("myProcs") {
      netConnector("myNetConnector")

    }
  }

  GroovyConfig procs(String name, Closure closure) {
    println "Defining procs: ${name}"
    closure.delegate = this
    closure.resolveStrategy = Closure.DELEGATE_FIRST
    closure()
  }

  GroovyConfig netConnector(String name, Closure closure=null) {
    println "Defining procs: ${name}"
    if(closure==null) return null
    closure.delegate = this
    closure.resolveStrategy = Closure.DELEGATE_FIRST
    closure()
  }

  GroovyConfig virtual(String name, Closure closure) {
    println "Defining virtual: ${name}"
    closure.delegate = this
    closure.resolveStrategy = Closure.DELEGATE_FIRST
    closure()
  }

  static void main(String[] args) {
    GroovyConfig groovyConfig = new GroovyConfig()
    groovyConfig.run()
  }
}
