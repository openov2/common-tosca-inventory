#
# Copyright  2017 ZTE Corporation.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

DIRNAME=`dirname $0`
RUNHOME=`cd $DIRNAME/; pwd`
echo @RUNHOME@ $RUNHOME
echo @JAVA_HOME@ $JAVA_HOME
JAVA="$JAVA_HOME/bin/java"
echo @JAVA@ $JAVA
main_path=$RUNHOME
cd $main_path/war
JAVA_OPTS="-Ddb.username=inventory"
JAVA_OPTS="$JAVA_OPTS -Ddb.password=inventory"
JAVA_OPTS="$JAVA_OPTS -Ddb.url=jdbc:mysql://127.0.0.1:3306/inventory"

#JAVA_OPTS="-Xms50m -Xmx128m"
#port=8312
#JAVA_OPTS="$JAVA_OPTS -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=$port,server=y,suspend=n"
echo @JAVA_OPTS@ $JAVA_OPTS
"$JAVA" $JAVA_OPTS  -cp . org.springframework.boot.loader.WarLauncher

