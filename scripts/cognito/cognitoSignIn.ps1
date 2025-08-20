# Prérequis : variables d'environnements [AWS_CLIENT_ID, AWS_CLIENT_SECRET]
#
# Usage :
##$ .\cognitoSignIn.ps1 monUserName monPassword
#############################################
param(
    [string]$username,
    [string]$password
)

if (!$username -Or !$password) {
    Write-Host "/!\ username and password required"
    exit
}

# Concaténation du username et de l'ID client
$userClientConcat = "$username$env:AWS_CLIENT_ID"

# Calcul de la clé HMAC-SHA256 et encodage en base64
$hmac = New-Object System.Security.Cryptography.HMACSHA256
$hmac.Key = [System.Text.Encoding]::UTF8.GetBytes($env:AWS_CLIENT_SECRET)

$hash = $hmac.ComputeHash([System.Text.Encoding]::UTF8.GetBytes($userClientConcat))
$secretHash = [System.Convert]::ToBase64String($hash)

aws cognito-idp initiate-auth --auth-flow USER_PASSWORD_AUTH --client-id $env:AWS_CLIENT_ID --auth-parameters USERNAME=$username,PASSWORD=$password,SECRET_HASH=$secretHash
