#!/bin/bash
# This script will give you H2 database shell

java -cp lib/h2*.jar org.h2.tools.Shell -url 'jdbc:h2:~/.georent-java/db;AUTO_SERVER=TRUE;USER=goddenis;PASSWORD=goddenis' -user goddenis -password goddenis