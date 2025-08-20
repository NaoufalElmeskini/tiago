

## troubleshooting
cloudformation logs in EC2 :
- /var/log/cloud-init-output.log
- /var/log/cloud-output.log

## useful CFN commands:

### create stack :
````powershell
aws cloudformation create-stack --stack-name someUsStack
#    --template-body file://C:\path_to_parent\ec2Instance.yml
#    --parameters ParameterKey=SecurityGroupDescription,ParameterValue=uneDescription
#    --capabilities CAPABILITY_IAM
````

### display stack
````powershell
aws cloudformation describe-stacks --stack-name someStack
````

### update stack:
````powershell
aws cloudformation update-stack --stack-name someStack 
#   --template-url https://s3Url/template.yml
#   --parameters ParameterKey=Maxvalue,ParameterValue=7
````
