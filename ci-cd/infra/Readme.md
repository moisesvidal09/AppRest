Make sure your docker is configured to use at least, 4 CPUs and 6GB of ram.



Raise the infrastructure by running:

docker-compose up -d --build --force-recreate



It's going to take a couple of minutes due to the size and configurations being done in the images.

After the command displays that the containers are ready, wait at least 3-5 min before trying the next step.



The following infrastructure is now raised in your localhost:

Application	URL	User  	Password
Jenkins	localhost:8080	No user	No password
Sonarqube	localhost:9000	admin	admin
Nexus	localhost:8081	admin	Get from the container**








**execute docker container exec nexus cat nexus-data/admin.password



Open Sonarqube and Nexus service in you browser to configure new passwords as it is a required step for initial configuration.

You can check the details of the infrastructure by consulting the docker-compose.yaml file.

Next, in order to use Jenkins & Sonarqube configure the following:

In Sonarqube:

Go to "Administration" -> "Security " -> "Users" and create a token for your user, store it for later usage.
Select "Configuration" -> "Webhooks" and create a webhook with "name" jenkins, "url" http://jenkins:8080/sonarqube-webhook/ and a secret word of your choice
Select "Configuration" -> "General" and set the "Server base URL" to http://sonarqube:9000
In Jenkins:

Go to "Manage Jenkins" -> "Configure System"
Under "Jenkins Location" -> "Jenkins URL" input http://jenkins:8080/
Under "SonarQube servers" set the "Name" Sonarqube, "Server URL" http://sonarqube:9000 and add a Server authentication token with the token previously stored and ID sonar-token