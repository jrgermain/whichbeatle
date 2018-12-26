#!/bin/sh

javac -cp .:./lib/sqlite4java.jar ./src/WhichBeatle.java

cd src

java WhichBeatle $@
