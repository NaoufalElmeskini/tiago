# Prérequis : variables d'environnements [AWS_CLIENT_ID, AWS_CLIENT_SECRET, AWS_USER_POOL_ID]
#
# Usage :
##$ .\cognitoSignUp.ps1 mon@email monPassword
#############################################
param(
    [string]$username,    # Nom d'utilisateur (e-mail)
    [string]$password     # Mot de passe
)

if (!$username -Or !$password) {
    Write-Host "/!\\ username and password required"
    exit
}

# Concaténation du username et de l'ID client
$userClientConcat = "$username$env:AWS_CLIENT_ID"

# Calcul de la clé HMAC-SHA256 et encodage en base64
$hmac = New-Object System.Security.Cryptography.HMACSHA256
$hmac.Key = [System.Text.Encoding]::UTF8.GetBytes($env:AWS_CLIENT_SECRET)

$hash = $hmac.ComputeHash([System.Text.Encoding]::UTF8.GetBytes($userClientConcat))
$secretHash = [System.Convert]::ToBase64String($hash)

# Exécution de la commande Sign-Up
aws cognito-idp sign-up `
    --client-id $env:AWS_CLIENT_ID `
    --username $username `
    --password $password `
    --user-attributes Name=email,Value=7dfq70c@mydefipet.live Name=name,Value=someName `
    --secret-hash $secretHash

# Confirmation de l'inscription afin d'éviter l'action manuelle
aws cognito-idp admin-confirm-sign-up `
    --user-pool-id $env:AWS_USER_POOL_ID `
    --username $username