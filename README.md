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
