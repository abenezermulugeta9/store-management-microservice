# Docker Commands

This README provides instructions for common Docker commands. Below are various commands for building, running, and managing Docker containers and images.

```bash
# Build a Docker image
docker build -t imagename:0.0.1 .

# Run a Docker container
docker run -d -p 8080:8080 --name containername image_id(image_name)

# Run a Docker container with environment variable
docker run -d -p 8080:8080 -e ENV_VARIABLE=value --name containername image_id(image_name)

# Clean up Docker (remove all unused resources)
docker system prune -a

# Stop running containers
docker stop container_id1 container_id2

# Delete containers
docker rm container_id1 container_id2

# Delete Docker images
docker rmi image_id1 image_id2

# Docker Compose: Create containers and run services
docker-compose -f docker-compose.yml up -d

# Docker Compose: Stop services and remove containers
docker-compose -f docker-compose.yml down

# Push to Docker Hub
# Login to Docker Hub
docker login

# Push Docker image to Docker Hub
docker push repository/imageName:tag
