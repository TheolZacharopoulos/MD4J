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

## Examples

### Implementing a Data Manager
The implementation and the integration of a new data manager is straight forward in this framework.
The basic components of a new data manager implementation are 
* the `DataManager` class (proxy) and 
* the `MObject` class (invocation handler).

First, to follow the modularity aspect and the ability to stack data managers together combining their specifications, 
we need to inherit from, at least, the `BasicDataManager` and its `MObject` respectively.
A simple data manager that could be useful is a data manager that introduces immutability to its managed objects.
A `Lockable` data manager should first inherit the `BasicDataManager` to get its field access specification.
The implementation of the `LockableDataManager` is illustrated bellow:

```
public class LockableDataManager extends BasicDataManager {

	public LockableDataManager(Class<?> moSchemaFactoryClass, Schema schema) {
		// Add the Lockable class in order to use it in the managed object.
		super(moSchemaFactoryClass, schema, Lockable.class);
	}

	@Override
	protected MObject createManagedObject(Klass klass, Object... _inits) {
		return new LockableMObject(klass, _inits);
	}
}
```

Additionally, it should add some \textit{locking} mechanism to ensure immutability of its objects.
This is defined in the `Lockable` interface, which is responsible of ensuring the implementation of the specifications. 
Bellow the `Lockable Interface` the specifications of the interface is showed:

```
public interface Lockable {
	void lock();
}
```

Since we have the specifications and the data manager that creates the `Lockable` managed object, we still need the implementation.
The implementation is located in the `MObject` and in this case the `LockableMObject`.

```
public class LockableMObject extends MObject implements Lockable {
	private boolean isLocked = false;

	public LockableMObject(Klass schemaKlass, Object... initializers) {
		super(schemaKlass, initializers);
	}

	public void lock() {
		isLocked = true;
	}

	@Override
	public void _set(String name, Object value) 
	throws NoSuchFieldError, InvalidFieldValueException, NoKeyFieldException {
		if (isLocked) {
	    	throw new IllegalAccessError(
	    		"Cannot change " + name + " of locked object " + schemaKlass.name() + ".");
		}
		super._set(name, value);
	}
}
```

The `LockableMObject`, by extending the `MObject` and implementing the `Lockable` interface, 
inherits the basic functionality of a managed object and gets a specification description respectively.

Its role is to implement the logic of the immutability, which is as simple as it looks.
In order to use this functionality, one needs to create managed objects using this data manager.
An example of how this can be used is shown bellow:

```
final PointFactory lockablePointFactory = lockableFactory.make();
final Point2D lockablePoint = lockablePointFactory.Point2D(1, 2);

// It was mutable until now, now it is locked (immutable).
((Lockable)lockablePoint).lock();

try {
	lockablePoint.x(2); // Should throw here since its immutable.
} catch (IllegalAccessError e) {
	System.out.println("IllegalAccessError: " + e.getMessage());
}
...
```