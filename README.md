Expectations
-----------------------------------
Log4j 1.x
Dom4j
Apache POI 3.x
taglibs 1.x
Velocity 1.x,
Axis 1.x
Xerces 1.x, Beanshell
Prometheus and Grafana
Dynatrace and Splunk
Open SAML 2.5.x 
Santurio XML Sec 1.x
Spring Batch Admin 1.x

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

    