# Futurice Calculator API
A simple web service to implement a calculator

## Installation
Here is a step-by-step guide on how to deploy the application on an AWS EC2 instance/server. Please note that this installation guide assumes that you have an AWS account and can launch an EC2 instance and connect via SSH to your server.

So go ahead set up a free tier linux micro instance that comes with java, SSh into it and follow the steps below.

### Update packages
```sh
$ sudo yum update
```

### Upgrade java
```sh
$ sudo yum list java*
$ sudo yum install  java-1.8.0
```

### Switch default Jdk to Java 8
```sh
$ sudo /usr/sbin/alternatives --config java
$ sudo /usr/sbin/alternatives --config javac
```

### Install Tomcat:
```sh
$ sudo yum list tomcat*
$ sudo yum install tomcat8
$ sudo yum install tomcat8-admin-webapps
```

### Start Tomcat
```sh
$ sudo service tomcat8 start
```

### Add a tomcat user to enable you get access to the tomcat dashboard
Look for the `tomcat-users.xml` file, it should be in `/usr/share/tomcat8/conf`. If not use the command below to get where tomcat is installed;
```sh
$ whereis tomcat8
```

### Edit the tomcat-users.xml file to add tomcat user
```sh
$ sudo nano tomcat-users.xml
```

### Add this to the tomcat-users.xml file and save the username and password somewhere
```sh
<role rolename="manager-gui"/>
<user username="manager" password="Password1" roles="manager-gui"/>
```

### Find CATALINA_HOME/webapps/manager/META-INF/context.xml file and comment out the Valve section like this.
```sh
<Context antiResourceLocking="false" privileged="true" >
<!--  <Valve className="org.apache.catalina.valves.RemoteAddrValve" allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1|123.123.123.123" /> -->
</Context>
```

### Restart Tomcat
```sh
sudo service tomcat8 restart
```

### Go to Tomcat Manager Dashbaord
To get the dashboard link go to your AWS EC2 instance page, click on your server and below it you will see your `Public DNS (IPv4)`, copy it, paste in your browse and add `:8080/manager/html`, so you have the full link as shown below.
```
http://ec2-54-198-241-55.compute-1.amazonaws.com:8080/manager/html
```
You will be prompted and asked to enter username and password, this is where you'll enter the Tomcat username and password you added to the `tomcat-users.xml` file.

### Deploy the API
Once you have successfully logged into the Tomcat manager dashboard, you will see the `Deploy` section with an option to upload a `war` file. There is a prebuilt `war` file called `futurice-calculator-0.0.1-SNAPSHOT.war` in the `target` folder, clone the entire repo if you haven't, so you can get access to the file, then upload.

### Run the API
Upon successful deployment, you should be able to access the API by going to your `Public DNS (IPv4)` add `futurice-calculator/api/v1/calculus?query=2 * (23/(3*3))- 23 * (2*3)` as shown below on your browser or Postman:
```
http://ec2-54-198-241-55.compute-1.amazonaws.com:8080/futurice-calculator/api/v1/calculus?query=2 * (23/(3*3))- 23 * (2*3)
```
