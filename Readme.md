# Comparison of different configuration ideas

## XML

```xml

<configuration>
  <object name="procs" handle="" dimension="" className="Procs">
    <object name="netConnector" dimension="" handle="" className="SocketConnector">
      <param name="portNo" index="" value="5002"/>
    </object>
  </object>
  <object name="vir1" handle="" dimension="" className="Vir">
  <object className="RemoteObject" name="remoteBuffer1" handle="" dimension="">
    <param name="gatewayHandle" index="" value="procs/pvcRT"/>
    <param name="remoteObjectName" index="" value="vir/localBuffer1"/>
  </object>
</configuration>
```

### Pros and Cons

+ Pros:
  - Widely used and supported format.
  - Excellent support in IntelliJ and oder Editors.

* Cons:
  - Verbose and can be hard to read.
  - No type checking at editing time aside for the XML schema.
  - Not as flexible as other formats for complex configurations.

## JSON

```json
{
  "procs": {
    "className": "Procs",
    "netConnector": {
      "className": "SocketConnector",
      "portNo": 5002
    }
  },
  "vir1": {
    "className": "Vir",
    "remoteBuffer1": {
      "className": "RemoteObject",
      "gatewayHandle": "procs/pvcRT",
      "remoteObjectName": "vir/localBuffer1"
    }
  }
}
```

### Pros and Cons

+ Pros:
  - Widely used and supported format.
  - Good support in many editors.

- Cons:
  - Even more verbose and can be hard to read and hard to type.
  - No type checking at editing time aside for the JSON schema.
  - Not as flexible as other formats for complex configurations.

# YML

```
--- 
mainConfiguration

procs:
  className: Procs
  netConnector:
    className: SocketConnector
    portNo: 5002
vir1:
  className: Vir
  remoteBuffer1:
    className: RemoteObject
    gatewayHandle: procs/pvcRT
    remoteObjectName: vir/localBuffer1
    
---    
```

### Pros and Cons

- pros:
  - Widely used and supported format.
  - Good support in many editors.
  - Less verbose than XML and JSON.
  - Easier to read and write.
  - Supports multiple configurations in a single file.
- cons:
  - No type checking at editing time aside for the YML schema.
  - Not as flexible as other formats for complex configurations.
  - One false indentation and the configuration breaks.

### pkl (Pickle)

```pkl
amends "Schema.pkl"

configuration = new PVServer {
  children = new {
    new Procs {
      name = "procs"
      children = new {
        for( i : Int in IntSeq(1,3)) {
          new SocketConnector {
            name = "netConnector\(i)"
            portNo = 5000 + i
          }
        }
        new SocketConnector {
          name = "webSocket"
          portNo = 5010
          loggingEnabled = 0
        }
        new ErrorHandler {
          name = "errorHandler"
          hConnector = "netConnector"
        }
      }
    }
  }
}
```

### Pros and Cons

+ Pros:
  - Very flexible and powerful. (e.g. output to json or yaml)
  - Can be type-checked at editing time. Even supports input ranges and constraints.
  - Can be used to generate multiple configurations in a single file.
  - Support for templates, inheritance, loops and if/switch statements.
  - Support for importing files and default configurations.
  - Plugin for IntelliJ available.

- Cons:
  - Not as widely used or supported as XML, JSON or YML.
  - Very new but backed by Apple and other companies.
  - AI highly hallucinates because of not much training data available yet.

### Groovy Config

```groovy
TestSystem.build {
      procs {
        PVConsole("console")
        TLIClassic("tliClassic") {
          rdHandle = handleOf("rd")
          consoleHandle = handleOf("console")
        }
      }
    }
```

### Pros and Cons

+ Pros:
  - Very flexible and powerful.
  - Can be used to generate multiple configurations in a single file.
  - Support for templates, inheritance and loops or if statements.
  - Plugin for IntelliJ available.
- Cons:
  - No type checking yet at editing time, but possible to implement in the future. (current implementation works with overriding invokeMethod)
   
# Conclusion

+ PKL supports most of the features we want to have for our files out of the box.
+ Groovy Config is also a good option, but typeChecking has to be implemented by scanning the Module info and generating a Method for every class
+ XML, JSON and YML can offer some structural support but no type checking during editing time.
