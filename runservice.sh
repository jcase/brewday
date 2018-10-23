#/usr/bin/env bash
export CLASSPATH=$(cat brewday-service/brewday_service_classpath.txt)
java com.novust.brewday.BrewDayService -f dev.properties

