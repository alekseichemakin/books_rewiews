./mvnw clean package -DskipTests
cp target/books_rewiews-0.0.1-SNAPSHOT.jar docker
docker-compose -f docker/docker-compose.yml down
docker-compose -f docker/docker-compose.yml up