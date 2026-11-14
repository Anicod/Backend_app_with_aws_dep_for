This is particularly important because you've already automated Docker deployment with GitHub Actions, and you want to ensure that your EC2 instance can pull the Docker image from ECR without any issues.
when you set up your GitHub Actions workflow, make sure to include steps that authenticate with AWS ECR using the AWS CLI and the credentials you've configured in your AWS CLI profile. This will allow your workflow to push the Docker image to ECR and deploy it to your EC2 instance seamlessly.
when running the container on your EC2 instance, ensure that the security group associated with your EC2 instance allows inbound traffic on the port your application is running (e.g., port 8080). This will enable you to access your application from your browser using the public IP address of your EC2 instance.
When running the container pass the profile as the environment variable to the docker run command, for example:
```bash
sudo docker run -d -p 8080:8080 -e AWS_PROFILE=my-ecr-profile AWS_ACCOUNT_ID.dkr.ecr.AWS_REGION.amazonaws.com/hello-world:latest
## fo DEV
docker run -d \
  --name hello-world-container \
  -e SPRING_PROFILES_ACTIVE=dev \
  -p 8080:8080 \
  hello-world:1.0
## fo PROD
docker run -d \
  --name hello-world-container \
  -e SPRING_PROFILES_ACTIVE=prod \
  -p 8080:8080 \
  hello-world:1.0
```
# Your GitHub Actions workflow can pass the profile to EC2 during deployment:
```declarative
sudo docker run -d \
  --name hello-world-container \
  --restart unless-stopped \
  -e SPRING_PROFILES_ACTIVE=dev \
  -p 8080:8080 \
  "$IMAGE"
```
# For production, use a separate deployment environment with its own protected 
# configuration and credentials. Don't automatically deploy every push to main to production.