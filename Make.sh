#!/bin/sh
export CLASSPATH=$CLASSPATH:./lib
java -jar ./lib/ant/ant-launcher.jar -file build.xml
sleep 10
