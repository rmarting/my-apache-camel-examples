# Camel ActiveMQ-WMQ Bridge Project

This project embeds Apache ActiveMQ broker and IBM WMQ Broker together with Apache Camel.

## IBM MQ OSGi Libraries

IBM MQ provides a set of OSGi libraries to be used in an OSGi runtime. This project uses them to
define a IBM MQ Broker.

These libraries are not available in any public Maven Repository so it is needed to install them
manually into a local or remote Maven repository. 

To install IBM MQ OSGi libraries execute the following commands:

	mvn install:install-file -Dfile=com.ibm.mq.osgi.directip_7.5.0.5.jar                    -DartifactId=directip            -Dversion=7.5.0.5 -Dpackaging=jar -DgroupId=com.ibm.mq.osgi
	mvn install:install-file -Dfile=com.ibm.mq.osgi.java_7.5.0.5.jar                        -DartifactId=java                -Dversion=7.5.0.5 -Dpackaging=jar -DgroupId=com.ibm.mq.osgi
	mvn install:install-file -Dfile=com.ibm.msg.client.osgi.commonservices.j2se_7.5.0.5.jar -DartifactId=commonservices.j2se -Dversion=7.5.0.5 -Dpackaging=jar -DgroupId=com.ibm.mq.osgi
	mvn install:install-file -Dfile=com.ibm.msg.client.osgi.jms_7.5.0.5.jar                 -DartifactId=jms                 -Dversion=7.5.0.5 -Dpackaging=jar -DgroupId=com.ibm.mq.osgi
	mvn install:install-file -Dfile=com.ibm.msg.client.osgi.jms.prereq_7.5.0.5.jar          -DartifactId=jms.prereq          -Dversion=7.5.0.5 -Dpackaging=jar -DgroupId=com.ibm.mq.osgi
	mvn install:install-file -Dfile=com.ibm.msg.client.osgi.nls_7.5.0.5.jar                 -DartifactId=nls                 -Dversion=7.5.0.5 -Dpackaging=jar -DgroupId=com.ibm.mq.osgi
	mvn install:install-file -Dfile=com.ibm.msg.client.osgi.wmq_7.5.0.5.jar                 -DartifactId=wmq                 -Dversion=7.5.0.5 -Dpackaging=jar -DgroupId=com.ibm.mq.osgi
	mvn install:install-file -Dfile=com.ibm.msg.client.osgi.wmq.nls_7.5.0.5.jar             -DartifactId=wmq.nls             -Dversion=7.5.0.5 -Dpackaging=jar -DgroupId=com.ibm.mq.osgi
	mvn install:install-file -Dfile=com.ibm.msg.client.osgi.wmq.prereq_7.5.0.5.jar          -DartifactId=wmq.prereq          -Dversion=7.5.0.5 -Dpackaging=jar -DgroupId=com.ibm.mq.osgi

**NOTE**: If you use other version it is possible that the GAV values (name, groupId, artifacId, version) could change. In that case, review the pom.xml and 
previous commands.

## Configure Blueprint Context

The *src/main/resources/OSGI-INF/blueprint/camel-blueprint.xml* includes a configuration section as:

    <cm:property-placeholder id="camel.activemq.blueprint" persistent-id="camel.activemq.blueprint">
        <cm:default-properties>
        	<!-- A-MQ Properties -->
           <cm:property name="broker.url" value="vm://localhost?broker.persistent=false"/>
           <cm:property name="broker.username" value="admin"/>
           <cm:property name="broker.password" value="admin"/>
           <!-- WMQ Properties -->
			<cm:property name="wmq.port" value="1712"/>
			<cm:property name="wmq.host" value="wmq-host(1712)"/>
			<cm:property name="wmq.queue.manager" value="WMQ_QUEUE_MANAGER"/>
			<cm:property name="wmq.channel" value="WMQ_CHANNEL"/>
			<cm:property name="wmq.user" value="WMQ_USER"/>
			<cm:property name="wmq.pwd" value="WMQ_PASSWORD"/>
        </cm:default-properties>
    </cm:property-placeholder>

Before to build the project review the values of the different Messaging Brokers:

For A-MQ:

* *broker.url*: A-MQ broker connection string (vm://localhost, tcp://localhost:61616, ...)
* *broker.username*: User
* *broker.password*: Password

For IBM MQ:

* *wmq.port*: MQ broker port
* *wmq.host*: MQ broker connection string (locahot(port))
* *wmq.queue.manager*: MQ Queue Manager
* *wmq.channel*: MQ Channel
* *wmq.user*: User
* *wmq.pwd*: Password

## Build Project

To build this project use

    mvn install


To run this project

    mvn camel:run


## Deploy into JBoss Fuse Standalone

To run this project on JBoss Fuse shell :
    
* Install dependencies from Fuse shell:
	
	  features:install activemq-camel

* Install the bundle from Fuse shell

      install -s mvn:com.redhat.fuse.demo/camel-amq-wmq/1.0.0-SNAPSHOT


## Deploy into JBoss Fuse Fabric

To deploy Fabric Profile

	mvn fabric8:deploy

From JBoss Karaf Console create a child container and add Fabric profile

	JBossFuse:karaf@root> container-add-profile amq-wmq com.redhat.fuse.demo-camel-amq-wmq

# Related Links

For more general help see the Apache Camel documentation

    http://camel.apache.org/

For more help on ActiveMQ Camel component see the Apache Camel documentation for ActiveMQ

	http://camel.apache.org/activemq.html
