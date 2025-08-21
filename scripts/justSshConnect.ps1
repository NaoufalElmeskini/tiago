# se connecter via ssh à l'instance EC2
# usage :
## .\sshConnect.ps1 C:\path_to_my\key.pem ec2-xx-xx.amazonaws.com

####################################
# key format: encryption keys in this project are in different formats: some script use '.ppk' and others '.pem'.
# a simple conversion is necessary to create one from an other (probably .pem to .ppk)
####################################

if (!$env:AWS_SSH_PEM_KEY) {
    Write-Host "/!\ environnement variable not found: 'AWS_SSH_KEY'"
    exit
}

echo "certificate : " $env:AWS_SSH_PEM_KEY

ssh -i $env:AWS_SSH_PEM_KEY ec2-user@$env:AWS_EC2_URL