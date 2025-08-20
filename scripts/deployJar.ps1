# usage:
# .\deployJar.ps1 C:\path_to_key\key.ppk ec2-xx-xx.amazonaws.com
####################################
# key format: encryption keys in this project are in different format : some script use .ppk and others .pem
# a simple conversion is necessary to create one from an other (probably .ppk to .pem)
####################################

mvn clean package -DskipTests --file ..\pom.xml
.\pscp.exe -scp -i $env:AWS_PPK_KEY ../target/*.jar ec2-user@$($env:AWS_EC2_URL):~