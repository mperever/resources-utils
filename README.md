Resources Utils
===============

[![Build Status](https://travis-ci.org/mperever/resources-utils.png?branch=master)](https://travis-ci.org/mperever/resources-utils)

What is it?
-----------

Lightweight utility to read Java resources in different ways. That utility does not use any third-party libraries.

Read a resource as a text:

```java
String sqlScript = ResourcesUtils.asString( "dbscript.sql" );
```

Read a resource as a binary stream:

```java
InputStream certificate = ResourcesUtils.asStream( "certificate.jks" );
```

Read a 'properties' resource:

```java
Properties properties = ResourcesUtils.asProperties( "messages.properties" );
```

Read a resource as a 'URI':

```java
URI appFileUri = ResourcesUtils.asURI( "app.xml" );
```

Get a resource names for specified path:

```java
String[] resourcesNamesForScenario1 = ResourcesUtils.getResourcesNames( "test_data/scenario1" );
```
