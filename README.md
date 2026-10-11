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