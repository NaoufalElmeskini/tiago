# installation sur le serveur ec2 :
## installer java 17
sudo yum install java-17-amazon-corretto
echo java installed!

## installer maven
wget http://mirror.olnevhost.net/pub/apache/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
tar xvf apache-maven-3.9.9-bin.tar.gz
sudo mkdir /usr/local/apache-maven
sudo mv apache-maven-3.9.9 /usr/local/apache-maven/
rm apache-maven-3.9.9-bin.tar.gz

## globaliser la commande $mvn
sudo ln -sf /usr/local/apache-maven/apache-maven-3.9.9/bin/mvn /usr/bin/mvn
echo maven installed!