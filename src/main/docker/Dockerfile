#/*
# * Copyright (c) 2018 JP-L, https://www.jp-l.org/
# *
# * Permission is hereby granted, free of charge, to any person obtaining
# * a copy of this software and associated documentation files (the
# * "Software"), to deal in the Software without restriction, including
# * without limitation the rights to use, copy, modify, merge, publish,
# * distribute, sublicense, and/or sell copies of the Software, and to
# * permit persons to whom the Software is furnished to do so, subject to
# * the following conditions:
# *
# * The above copyright notice and this permission notice shall be
# * included in all copies or substantial portions of the Software.
#
# * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
# * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
# * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
# * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
# * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
# * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
# * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
#*/
FROM tomcat:9.0.6
MAINTAINER JP-L

LABEL Description="JDemo is a simple Java Web application using prevayler for persistency. It's running on Tomcat (including JSF libraries) within a Docker container."\
      Vendor="JP-L"\
      Version="0.1"

ENV CATALINA_HOME /usr/local/tomcat
ENV TOMCATPATH /usr/local/tomcat/webapps/
ENV CONTEXTPATH /var/cache/webapps/jdemo
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

