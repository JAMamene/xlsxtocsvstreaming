
# XLSX CSV Streaming test
This is a proof of concept for an efficient import of an xlsx file.
It converts the file to a csv one in a streaming fashion and then reads it as a csv in a streaming fashion but with (relative) resilience to interruptions. 

## Java Program
### Installation
The installation requires to have a working maven installation

    mvn clean install
### Execution
Minimal execution

    mvn exec:java -Dexec.args="-i  <xlsx filename in resources folder>"

The available argument is (required) :

     -i,--input <filename>       xlsx input file
