Expectations
-----------------------------------
Log4j 1.x
Dom4j
Apache POI 3.x
taglibs 1.x
Velocity 1.x,
Prometheus and Grafana
Dynatrace and Splunk

Lab Setup
--------------------------------------
    JDK 1.8
    Eclipse latest / STS latest
    Maven
    Apache Tomcat
    MySQL

Case Study - Budget Tracker
--------------------------------------

    1. A user is expected to record his transactions (spendings/earnings)
    2. A statement has to be generated based on the transactions made in a givne period of time.

Case Study - ERM App
--------------------------------------

    1. An employee has empId,fullName,joinDate,basic,ra,ta,netPay
    2. CRUD on employee entity and the hra and ta are 5 adn 3 percent of basic.

Log4j
--------------------------------------

    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
    </dependency>

                                              consoleAppender       ---layout formatted msgs---> On Console
    APP <-MSG->  rootLogger         <--MSG->  fileAppender          ---layout formatted msgs---> into a file
                    |-logger1                 smtpAppender          ---layout formatted msgs---> mail the messages
                    |-logger2
                    |-logger3

    log4j.properties

        # initialize root logger with level ERROR for stdout and fout
        log4j.rootLogger=ERROR,stdout,fout

        # add a ConsoleAppender to the logger stdout to write to the console
        log4j.appender.stdout=org.apache.log4j.ConsoleAppender
        log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
        # use a simple message format
        log4j.appender.stdout.layout.ConversionPattern=%m%n

        # add a FileAppender to the logger fout
        log4j.appender.fout=org.apache.log4j.FileAppender
        # create a log file
        log4j.appender.fout.File=crawl.log
        log4j.appender.fout.layout=org.apache.log4j.PatternLayout
        # use a more detailed message pattern
        log4j.appender.fout.layout.ConversionPattern=%p\t%d{ISO8601}\t%r\t%c\t[%t]\t%m%n

    Loging Levels

        ALL
        TRACE
        DEBUG
        INFO
        WARN
        ERROR
        FATAL
        OFF
    
    Logger class methods
        getRootLogger()
        getLogger("loggerCategory")
        getLogger(Class)

    Logger instance methods
        log(msg,level)
        fatal(msg)
        error(msg)
        info(msg)
        debug(msg) ....etc


XML
--------------------------------------------------------------------------

    eXtended Markup Language

    is used for tranporting semi-formated data across platforms.

    emps.txt
    empid,name,sal,doj
    101,Vamsy,45600,2020-01-01
    102,Suma,45600,2020-01-01
    103,Komal,45600,2020-01-01
    104,Naveen,45600,2020-01-01

    emps.xml

    <emps>
        <emp empid="101">
            <name>Vamsy</name>
            <sal>45000</sal>
            <doj>2020-01-01</doj>
        </emp>
        <emp empid="102">
            <name>Suma</name>
            <sal>45000</sal>
            <doj>2020-01-01</doj>
        </emp>
        <emp empid="103">
            <name>Koaml</name>
            <sal>45000</sal>
            <doj>2020-01-01</doj>
        </emp>
        <emp empid="104">
            <name>Naveen</name>
            <sal>45000</sal>
            <doj>2020-01-01</doj>
        </emp>
    </emps>

DOM4j
--------------------------------------------------------------------------

    <dependency>
        <groupId>dom4j</groupId>
        <artifactId>dom4j</artifactId>
        <version>1.6.1</version>
    </dependency>
    <dependency>
        <groupId>jaxen</groupId>
        <artifactId>jaxen</artifactId>
        <version>1.2.0</version>
    </dependency>


    Document ??? Represents the entire XML document. A Document object is often referred to as a DOM tree.
    Element ??? Represents an XML element. Element object has methods to manipulate its child elements, text, attributes, and namespaces.
    Attribute ??? Represents an attribute of an element. Attribute has method to get and set the value of attribute. It has parent and attribute type.
    Node ??? Represents Element, Attribute, or ProcessingInstruction.

    SAXReader.read(xmlSource)() ??? Build the DOM4J document from an XML source.
    Document.getRootElement() ??? Get the root element of an XML document.
    Element.node(index) ??? Get the XML node at a particular index in an element.
    Element.attributes() ??? Get all the attributes of an element.
    Node.valueOf(@Name) ??? Get the values of an attribute with the given name of an element.

Apache POI
------------------------------------------------------------------------

    is an apache open source library to write Office documents like Excel workbooks, Word Documents or
    PowerPoint preentations using java.

    <dependency>  
        <groupId>org.apache.poi</groupId>  
        <artifactId>poi</artifactId>  
        <version>3.9</version>  
    </dependency>  

Velocity
------------------------------------------------------------------
<dependency>
    <groupId>org.apache.velocity</groupId>
    <artifactId>velocity</artifactId>
    <version>1.7</version>
</dependency>
<dependency>
     <groupId>org.apache.velocity</groupId>
     <artifactId>velocity-tools</artifactId>
     <version>2.0</version>
</dependency>

Product Performence
        |
    --------------------------------------
    |                                   |
    Application Code                    Infra
     Data Strucutres
     Alogirthms
     Implementation

            time complexity                 Networks
            space complexity                Operating System

        Prometheus and Grafana                 Dynatrace and Splunk

        Prometheus      is performence monitoring server

                Prometheus Client
                Micrometrics
                Parametrics 
                Spring Boot Actuator ...etc

                the data collected here is called time series data.

        Grafan          is a data visualization tool for performence metrics.

        Dynatrace       is a network and system monitoring tool.

                        applied on a clusturs of VPNs .

        Splunk          is a adjective tool for system performence metric monitoring.


    Axis                is a SOAP WebService Server and Client.

                            Simple Object Access Protocol Web Services.
                            XML is media of communciation.

    Open SAML           is a standard security protocol over xml for soap webservices.

Spring Batch Admin

    is a UI tool to manage the job created on spring batch.

    org.springramewrk.com:org-springframework-starter-batch-admin-manager

    assuming that the srver context is at 9000

    http://localhost:9000/spring-batch-admin/

        will trigger a dashboard.
            feature
                - creating a new job
                - shceduling a job
                - pausing a job
                - starting a job ahead of its schedule time
                - deleting a job.

    is declared 'end-of-life' on the 11 month of 2017 year.

    this proejct is being moved intoa new project 'spring-attic'

    https://github.com/spring-attic/spring-batch-admin.git

    git clone https://github.com/spring-attic/spring-batch-admin.git

    using menu 'existing mavne project'  this into eclispe or sts and
    'run as -> run on server'.

