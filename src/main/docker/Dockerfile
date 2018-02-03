# Use the latest Tomcat container
FROM tomcat:8.5.4
MAINTAINER JP-L

LABEL Description="This image is used to start a Tomcat service"\
      Vendor="JP-L"\
      Version="0.1"

ENV CATALINA_HOME /usr/local/tomcat
ENV TOMCATPATH /usr/local/tomcat/webapps/
ENV CONTEXTPATH /var/cache/webapps/java-gradle-demo
ENV TOMCATUSER root
ENV TOMCATGROUP staff

# Enable remote monitoring
COPY ["setenv.sh","$CATALINA_HOME/bin"]
RUN chmod 740 $CATALINA_HOME/bin/setenv.sh

# Create directories to store application files and logging
RUN mkdir -p $CONTEXTPATH
# Set the owner of the directories
RUN chown $TOMCATUSER:$TOMCATGROUP $CONTEXTPATH
# Set Tomcat data and log directories in a data volume
VOLUME ["$CONTEXTPATH"]

COPY ["*.war","$TOMCATPATH"]

# Expose default ports
EXPOSE 8080 9012 62911

