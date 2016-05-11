# JavaManagedData

JavaMD is a Managed Data implementation in Java.

## Influence
In their study on [managed data](http://www.cs.utexas.edu/~wcook/Drafts/2012/ensodata.pdf), 
Cook et al. presented the main idea of managed data, 
while using a show case of it in a Ruby implementation. 
As a use case, they presented the [Enso](https://github.com/enso-lang/enso) project, which is a Ruby implementation of managed data.

JavaMD is an extension of their work; we implement managed data in Java using the Java reflection API and dynamic proxies. 
Although proxies in static programming languages can not implement the full range of managed data, 
Java provides a strong implementation of the MOP, which can be used though the Java Reflection API. 

## Installation
The project is built with maven.

### Run tests
`mvn test`

### Build the (jar) library
`mvn clean package`

## Overview
TODO

## Example
TODO
