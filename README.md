# Backend_app_with_aws_dep_for
configure automatic deployment using GitHub Actions so that pushing code to GitHub builds and deploys your application without manually uploading the JAR each time.


##### mannual deployment steps #########

Do the following to create an Ubuntu Amazon EC2 instance.

Create an Ubuntu Amazon EC2 instance
Sign in to the AWS Management Console and open the Amazon EC2 console at https://console.aws.amazon.com/ec2/.

Verify that the appropriate Region is selected.

Choose Launch Instance.

Complete the following fields:

Name – Type a name for the instance.

Application and OS Images (Amazon Machine Image) – Select Ubuntu.

Instance type – Select t2.large.

Key pair login – Create your own key pair.

Network settings – Keep the default.

Configure storage – Increase the volume to 256 GiB.

Advanced settings – Keep the default.

Launch the instance and SSH into it.

Do the following:

Select Instances in the left navigation, then select the instance ID.

Choose Connect in the top-right.

Choose SSH client and follow the instructions on the screen.

Open a terminal and navigate to the downloaded .pem file (likely in ~/Downloads).

The first time you follow these procedures, you will receive the message "The authenticity of host (…) can't be established." Type yes.

Install system libraries to build the Amazon Kinesis Video Streams Edge Agent onto the instance.


wget -O- https://apt.corretto.aws/corretto.key | sudo apt-key add -
sudo add-apt-repository 'deb https://apt.corretto.aws stable main'

sudo apt-get update

sudo apt-get install -y gcc libssl-dev libcurl4-openssl-dev liblog4cplus-dev \
libgstreamer1.0-dev libgstreamer-plugins-base1.0-dev \
gstreamer1.0-plugins-base-apps gstreamer1.0-plugins-bad \
gstreamer1.0-plugins-good gstreamer1.0-tools \
unzip java-11-amazon-corretto-jdk maven
Important
If you see a screen telling you that some services need to be restarted, press Enter to select Ok.

if do the code change redeploy follow the below the steps
code change-->build jar-->upload jar-->restart the service
http://13.201.75.146:8080/hello
######run locally########
spring-boot:run
http://localhost:8081/hello
##### Automatic deployment steps #########
Configure GitHub Actions secrets
GitHub Actions needs your EC2 connection details. We'll store them as encrypted repository secrets instead of hardcoding them in the workflow.
Open your repository on GitHub and go to:
Settings → Secrets and variables → Actions → New repository secret
add the following secrets:
Name: EC2_HOST
Value: <your EC2 public IP address>
Name: EC2_USER
Value: <your EC2 username, e.g., ubuntu>
Name: EC2_KEY
Value: <your private key content, i.e., the content of your .pem file>  
Private key (ec2_deploy_key) - Keep this SECRET, only you/GitHub has it
Private key (ec2_deploy_key) - Keep this SECRET, only you/GitHub has it
Connect to your EC2 instance
ssh -i your-existing-ec2-key.pem ubuntu@your-ec2-ip-address
Add the public key to EC2
mkdir -p ~/.ssh
cat >> ~/.ssh/authorized_keys << 'EOF'
<PASTE YOUR PUBLIC KEY HERE>
EOF
Then set permissions:
chmod 600 ~/.ssh/authorized_keys
chmod 700 ~/.ssh
ssh-keygen -p -N "" -m pem -f ec2_deploy_key (this will generate a new private key in PEM format)
cat ec2_deploy_key
Copy everything from -----BEGIN RSA PRIVATE KEY----- to -----END RSA PRIVATE KEY----- (the entire block).
go to your GitHub repository, click on Settings → Secrets and variables → Actions → New repository secret, and add a new secret with the name EC2_KEY and paste the private key content you copied earlier.
1. IntelliJ IDEA
   Edit your Java 17 code
2. GitHub repository
   Push changes to the main branch
3. GitHub Actions
   Build JAR → connect securely → deploy
4. Your AWS EC2 instance
   Replace JAR and restart the application
   GitHub Actions for the workflow.
Maven to build the Spring Boot JAR with Java 17.
SSH to securely connect to your existing Ubuntu EC2 instance.
systemd to keep your application running and restart it after deployment.
http://13.201.75.146:8080/hello
###### start with docker intially run locally then push to ECR and run on EC2 ########
###### run locally using docker ########
docker build -t spring-boot-app .
docker run -p 8081:8081 spring-boot-app
http://localhost:8080/hello
065986019270.dkr.ecr.ap-south-1.amazonaws.com/hello-world
##### Create an Amazon ECR repository
Make sure you're in the same AWS region as your EC2 instance.
Select Create repository.
Visibility: Private.
Repository name: hello-world.
Keep image tag mutability set to Mutable for this initial learning setup, or use immutable version tags later.
Click Create repository.
### Step 3.2: Give EC2 permission to pull images
Step 3.2: Give EC2 permission to pull images
In the AWS console:
1. Open IAM → Roles → Create role.
2. Trusted entity type: AWS service.
3. Use case: EC2.
4. Attach the AWS managed policy AmazonEC2ContainerRegistryReadOnly.
5. Name the role EC2-ECR-Pull-Role.
6. Create the role.
7. Open EC2 → Instances → select your instance → Actions → Security → Modify IAM role.
8. Attach EC2-ECR-Pull-Role.

Phase 4 — Prepare EC2 to run Docker
   Connect from your Mac:
ssh -i /Users/aniketraj/Desktop/aws_deploy/mcp-server.pem ubuntu@13.201.75.146
Install Docker
sudo apt update
sudo apt install -y docker.io
enable Docker to start on boot
sudo systemctl enable --now docker
verify Docker is running
sudo docker --version
sudo docker ps
Stop the old Java service
sudo systemctl disable --now hello-world
**This does not delete your JAR or your service configuration. It just stops the old service and prevents it from starting automatically.
set remote origin to ECR
export AWS_REGION=YOUR_AWS_REGION
then login
aws ecr get-login-password --region "$AWS_REGION" \
| sudo docker login \
--username AWS \
--password-stdin \
AWS_ACCOUNT_ID.dkr.ecr.AWS_REGION.amazonaws.com
Phase 5 — Push your Docker image to ECR and deploy it to EC2
Configure AWS CLI on your Mac
## Verify that you have the AWS CLI installed on your Mac:
    aws --version
If you already have an AWS CLI profile with the necessary ECR permissions, use it.
install AWS CLI v2 on your Mac if you don't have it already.
if you don't have an AWS CLI profile with the necessary ECR permissions, create one.
# aws configure --profile my-ecr-profile
      Enter the values when prompted:
      - AWS Access Key ID: your IAM access key
      - AWS Secret Access Key: your IAM secret key
      - Default region name: your AWS region, e.g. ap-south-1
      - Default output format: json
# if u have not profile then create one using the below command
aws configure --profile my-ecr-profile
create IAM user with ECR permissions
# steps for creating an IAM user with ECR permissions:
      1. Open the IAM console at https://console.aws.amazon.com/iam/.
      2. In the navigation pane, choose Users and then choose Add user.
      3. Type a user name for the new user. This is the sign-in name for AWS.
      4. Select Programmatic access for Access type.
      5. Choose Next: Permissions.
      6. Choose Attach existing policies directly.
      7. In the search box, type AmazonEC2ContainerRegistryFullAccess.
      8. Select the AmazonEC2ContainerRegistryFullAccess policy.
      9. Choose Next: Tags (optional) and then choose Next: Review.
      10. Choose Create user.

aws configure set aws_access_key_id YOUR_ACCESS_KEY_ID --profile my-ecr-profile
aws configure set aws_secret_access_key YOUR_SECRET_ACCESS_KEY --profile my-ecr-profile
### run the following command to verify that your AWS CLI profile is working correctly:
aws sts get-caller-identity --profile my-ecr-profile
### run the following in mac terminal to push your Docker image to ECR and deploy it to EC2:
Build your Docker image
docker build -t hello-world .
Tag your Docker image for ECR
docker tag hello-world:latest AWS_ACCOUNT_ID.dkr.ecr.AWS_REGION.amazonaws.com/hello-world:latest
   **** Important: Replace AWS_ACCOUNT_ID and AWS_REGION with your actual AWS account ID and region. ****
   ### if error occurs while pushing the image to ECR, run the following command to login to ECR:
   aws ecr get-login-password --region AWS_REGION | docker login --username AWS --password-stdin AWS_ACCOUNT_ID.dkr.ecr.AWS_REGION.amazonaws.com
   # verify that region and AWS account ID are correct, then run the docker push command again.
      echo $AWS_ACCOUNT_ID
      echo $AWS_REGION
## veify ur ECR repository
aws ecr describe-repositories \
--repository-names hello-world \
--region "$AWS_REGION"
## if it not exist, create it
      aws ecr create-repository \
      --repository-name hello-world \
      --region "$AWS_REGION"
## else
      authenticate Docker to your Amazon ECR registry
      aws ecr get-login-password --region "$AWS_REGION" | \
      docker login --username AWS --password-stdin \
      "$AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com"
# if login successful, you will see a message: "Login Succeeded"
      Tag and push your image
      docker tag hello-world:1.0 \
      "$AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/hello-world:1.0"
      docker push AWS_ACCOUNT_ID.dkr.ecr.AWS_REGION.amazonaws.com/hello-world:latest
# Verify that AWS has received the image:
        aws ecr describe-images \
        --repository-name hello-world \
        --region "$AWS_REGION"
Run your Docker image on EC2
### run the following command to SSH into your EC2 instance:
ssh -i /path/to/your-key.pem ubuntu@YOUR_EC2_PUBLIC_IP
### run the following command to pull your Docker image from ECR and run it on EC2
sudo docker run -d -p 8080:8080 AWS_ACCOUNT_ID.dkr.ecr.AWS_REGION.amazonaws.com/hello-world:latest
### run the following command to verify that your Docker container is running on EC2
sudo docker ps
### run the following command to verify that your application is accessible from your browser
http://13.201.75.146:8080/hello

### Automate the deployment process with GitHub Actions


