#!/bin/bash
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
# 
# Start two tomcat containers.
#

HOST=http://localhost
CONTEXT_PATH=jdemo
CONTAINER_PORT_1=8080

# sleep a couple of seconds to let the second container start
sleep 30

# Verify if the application deployed correctly
echo "URL TO TEST: " $HOST:$CONTAINER_PORT_1/$CONTEXT_PATH/
if wget $HOST:$CONTAINER_PORT_1/$CONTEXT_PATH/ --timeout 30 -O - 2>/dev/null; then
        echo "1- TEST SUCCEEDED";
else
        echo "1- TEST FAILED";
fi

exit 0;
