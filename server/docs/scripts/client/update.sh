#!/bin/bash
rm -rf ./BOOT-INF/classes;
unzip -q terminal-0.0.1-SNAPSHOT.jar.original -d ./BOOT-INF/classes;
zip -qd terminal-0.0.1-SNAPSHOT.jar BOOT-INF/classes/*;
zip -ur terminal-0.0.1-SNAPSHOT.jar ./BOOT-INF/*;