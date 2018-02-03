# Demo application

Latest release: 0.1 - Demo application on Tomcat 9 - [Changelog](CHANGELOG.md)

A Demo application running on Tomcat (including JSF libraries) within a Docker container.

###Shippable CI Status
[![Run Status](https://api.shippable.com/projects/57c090ab6682410e00b33829/badge?branch=master)](https://app.shippable.com/projects/57c090ab6682410e00b33829)
[![Coverage Badge](https://api.shippable.com/projects/57c090ab6682410e00b33829/coverageBadge?branch=master)](https://app.shippable.com/projects/57c090ab6682410e00b33829)

###Code Quality Status
[![BCH compliancy](https://bettercodehub.com/edge/badge/JP-L/java-gradle-demo)](https://bettercodehub.com)
[![codecov](https://codecov.io/gh/JP-L/java-gradle-demo/branch/master/graph/badge.svg?token=RcB4Q6zzbl)](https://codecov.io/gh/JP-L/java-gradle-demo)

## Quick Start

Run Tomcat docker image:
     `docker run -it --rm \
           -p 8889:8080 \
           -v /var/cache/webapps:/var/cache/webapps \
	   <container-name>`


## Considerations

TODO

## Changelog

Please refer to: [CHANGELOG.md](CHANGELOG.md)

