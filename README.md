
[jplorgurl]: https://www.jp-l.org
[appurl]: TODO[https://demos.jp-l.org/jdemo]
[hub]: https://hub.docker.com/r/jplorg/webgrabplus/

TODO: add logo

[JP-L][jplorgurl] Created a simple Java application in a docker container.

Latest release: 0.1 - jdemo - [Changelog](CHANGELOG.md)
# jplorg/jdemo
[![](https://images.microbadger.com/badges/version/jplorg/jdemo.svg)](https://microbadger.com/images/jplorg/jdemo "Get your own version badge on microbadger.com")
[![](https://images.microbadger.com/badges/image/jplorg/jdemo.svg)](https://microbadger.com/images/jplorg/jdemo "Get your own image badge on microbadger.com")
[![Docker Pulls](https://img.shields.io/docker/pulls/jplorg/jdemo.svg)][hub][![Docker Stars](https://img.shields.io/docker/stars/jplorg/jdemo.svg)][hub]
[![Run Status](https://api.shippable.com/projects/5a785a6f30ef310600d83be0/badge?branch=master)](https://app.shippable.com/github/JP-L/jdemo)
[![Coverage Badge](https://api.shippable.com/projects/5a785a6f30ef310600d83be0/coverageBadge?branch=master)](https://app.shippable.com/github/JP-L/jdemo)
[![BCH compliancy](https://bettercodehub.com/edge/badge/JP-L/jdemo)](https://bettercodehub.com)
[![codecov](https://codecov.io/gh/JP-L/jdemo/branch/master/graph/badge.svg?token=RcB4Q6zzbl)](https://codecov.io/gh/JP-L/jdemo)

[![jdemo]][appurl]
JDemo is a simple Java Web application using prevayler for persistency. It's running on Tomcat (including JSF libraries) within a Docker container.

## Quick Start

```
docker run -it --rm \
           -p 8889:8080 \
           -v /var/cache/webapps:/var/cache/webapps \
	   jpl/jdemo
```
You can choose between ,using tags, latest (default, and no tag required or a specific release branch of jdemo. Add one of the tags, if required, to the jplorg/jdemo line of the run/create command in the following format, jplorg/jdemo:release-1.0

#### Tags

+ **latest** : latest release from main branch.

## Donations
Please consider donating a cup of coffee for the developer through paypal using the button below.

[![Donate](https://www.dokuwiki.org/lib/exe/fetch.php?w=220&tok=95f428&media=https%3A%2F%2Fraw.githubusercontent.com%2Ftschinz%2Fdokuwiki_paypal_plugin%2Fmaster%2Flogo.jpg)](https://www.paypal.me/JPLORG/2,50EUR)

## Considerations

* The container is based on Debian. For shell access whilst the container is running do `docker exec -it webgrabplus /bin/bash`.
* Container local time is default set to Europe/Amsterdam. This can be changed using `-e TZ` (where TZ is eg Europe/Berlin).
* Currently the container grabs EPG data at 10:04 and 22:04. This will be configurable in the future.

## Usage

**Parameters**

The parameters are split into two halves, separated by a colon, the left hand side representing the host and the right the container side. 
For example with a port -p external:internal - what this shows is the port mapping from internal to external of the container.
So -p 8080:80 would expose port 80 from inside the container to be accessible from the host's IP on port 8080
http://172.12.x.x:8080 would show you what's running INSIDE the container on port 80.

* `-v /var/cache/webapps` - host path where Prevayler will store its files
* `-e PGID` for GroupID - see below for explanation
* `-e PUID` for UserID - see below for explanation
* `--net=bridge`
* `-e TZ` - for timezone information *eg Europe/London, etc*

**User / Group Identifiers**

When using volumes (`-v` flags) permission issues arise between the host OS and the container. This can be avoided by specifying the user `PUID` and group `PGID`. 
Ensure the volumes directories on the host are read/writable by the container user. The container user is `root` with group `staff`

In this instance `PUID=TODO` and `PGID=TODO`. To find yours use `id user` as below:

```
  $ id <dockeruser>
    uid=103(dockeruser) gid=44(dockergroup) groups=44(dockergroup)
```

## Info

* Shell access whilst the container is running: `docker exec -it jdemo /bin/bash`
* To monitor the logs of the container in realtime: `docker logs -f jdemo`

* container version number 

`docker inspect -f '{{ index .Config.Labels "build_version" }}' jdemo`

* image version number

`docker inspect -f '{{ index .Config.Labels "build_version" }}' jplorg/jdemo`


## Versions

+ **xx.xx.xx:** Initial release.

## Changelog

Please refer to: [CHANGELOG.md](CHANGELOG.md)
