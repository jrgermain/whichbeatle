#!/bin/sh

javac -cp .:./lib/*.jar ./src/WhichBeatle.java

cd src

java WhichBeatle $@
