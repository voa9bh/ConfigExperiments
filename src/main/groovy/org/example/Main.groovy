package org.example

import groovy.json.JsonGenerator
import groovy.json.JsonOutput
import org.pkl.config.java.CompositeConfig
import org.pkl.config.java.ConfigEvaluator
import org.pkl.config.java.Config
import org.pkl.config.java.JavaType
import org.pkl.core.Evaluator
import org.pkl.core.ModuleSource
import org.pkl.core.PModule
import org.pkl.core.PObject

static void main(String[] args) {
  try (Evaluator evaluator = Evaluator.preconfigured()) {
    PModule config = evaluator.evaluate(ModuleSource.file("/home/voa9bh/temp/ConfigExperiments/src/main/resources/Examples.pkl"))

    println config.toString()

    printConfig(config)
  }
}

void printConfig(PObject config, int indent = 0) {
  println "${" "*indent}<${config.class}> ${config.getClassInfo().qualifiedName}"
  indent += 2
  config.properties.each { Map.Entry<String, Object> property ->
    println "${" "*indent}<${property.value.class}>"
    if(property.value instanceof PObject) {
      println "${" "*indent}${property.value.getClassInfo().qualifiedName}:"
      printConfig((PObject) property.value, indent)
    } else if (property.value instanceof Map) {
      Map map = property.value as Map
      println("${" "*indent}${property.key}: {")
      indent += 2
      map.each { Map.Entry<String, Object> entry ->
        if(entry.value instanceof PObject) {
          printConfig((PObject) entry.value, indent + 2)
        } else {
          println "${" "*(indent + 2)}${entry.key}: ${entry.value}"
        }
      }
      indent -= 2
      println("${" "*indent}}")
    } else {
      println "${" "*indent}${property.key}: ${property.value}"
    }
  }
}