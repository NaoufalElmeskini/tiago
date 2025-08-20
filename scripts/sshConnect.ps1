# se connecter via ssh à l'instance EC2
# usage :
## .\sshConnect.ps1 C:\path_to_my\key.pem ec2-xx-xx.amazonaws.com

####################################
# key format: encryption keys in this project are in different formats: some script use '.ppk' and others '.pem'.
# a simple conversion is necessary to create one from an other (probably .ppk to .pem)
####################################
param(
    [string]$pemCertificate,
    [string]$server
)

if (!$pemCertificate) {
    Write-Host "/!\ you didn't insert .pem certificate"
    exit
}
if (!$server) {
    Write-Host "/!\ you didn't insert server"
    exit
}

echo "certificate : " $pemCertificate

ssh -i $pemCertificate ec2-user@$server