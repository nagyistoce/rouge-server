#!/bin/bash
####
#
#   Copyright [2011] [ADInfo, Alexandre Denault]
#  
#   Licensed under the Apache License, Version 2.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at
#  
#     http://www.apache.org/licenses/LICENSE-2.0
#  
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.
#
###

#This script is a bit inspired from the Cruisecontrol script.

# Assuming we are running Rouge for the current directory. Change if otherwise.
ROUGE_DIR=`pwd`

LIBS=$ROUGE_DIR/lib
NATIVE_LIBS=$ROUGE_DIR/lib

LAUNCHER="lib/rouge-server-0.1.jar"

OPTIONS="-Xms128m -Xmx256m" 

#--------------------------------------------
# set JAVA_HOME on Mac OSX
#--------------------------------------------
case "`uname`" in
  Darwin*)
    if [ -z "$JAVA_HOME" ] ; then
      JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Home
    fi
    ;;
esac

EXEC="$JAVA_HOME/bin/java $OPTIONS -Djava.library.path=$NATIVE_LIBS -jar $LAUNCHER $@"
echo $EXEC
$EXEC &
echo $! > rouge.pid
