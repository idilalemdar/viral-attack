#!/bin/bash

mkdir -p "executables"

cd server || exit
./mvnw clean package

cd .. || exit
mv server/target/server-0.0.1-SNAPSHOT.war executables/server_program24.war

cd client || exit
./mvnw clean package

cd .. || exit
mv client/target/client-0.0.1-SNAPSHOT.jar executables/client_program24.jar
