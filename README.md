# tpricing
pricing challenge



# install
### using mvn wrapper (pre-requisite: java installed)
<code>./mvnw clean install</code>

<code>java -jar target/pricing-0.0.1-SNAPSHOT.jar</code>

### using docker (pre-requisite: docker installed)
build docker image (it takes some time until layers download)
<code>docker build -t tpricing:1.0 .</code>

check that docker image was built
<code> docker images                 
REPOSITORY                           TAG        IMAGE ID       CREATED         SIZE
tpricing                             1.0        2a19a7189e3b   4 minutes ago   357MB
</code>

run container as demon
<code>docker run -p 8080:8080 --name tpricing -d tpricing:1.0</code>

check container is running
<code>docker ps
CONTAINER ID   IMAGE          COMMAND                  CREATED          STATUS          PORTS                                       NAMES
6c94bf4697c6   tpricing:1.0   "sh -c 'java $JAVA_O…"   17 seconds ago   Up 16 seconds   0.0.0.0:8080->8080/tcp, :::8080->8080/tcp   tpricing
</code>



# swagger3
API usage documentation at

http://localhost:8080/swagger-ui/index.html




# arquitecture
### sequence view
![Screenshot](https://github.com/paguerre3/tpricing/blob/main/design/seq-diagram.png?raw=true)

### package view
![Screenshot](https://github.com/paguerre3/tpricing/blob/main/design/pckge-diagram.png?raw=true)

### diagram entity relationship view
![Screenshot](https://github.com/paguerre3/tpricing/blob/main/design/der-diagram.png?raw=true)

### implementation view
![Screenshot](https://github.com/paguerre3/tpricing/blob/main/design/impl-img.png?raw=true)





# h2-console
H2 database in memory console at

http://localhost:8080/h2-console




# license
Copyright 2021, paguerre3

Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License. You may obtain a copy of the
License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
