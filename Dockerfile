# App Building phase --------
FROM openjdk:8 AS build

RUN mkdir /appbuild
COPY . /appbuild

WORKDIR /appbuild

RUN ./gradlew clean build
# End App Building phase --------

# Container setup --------
FROM openjdk:8-jre-alpine
RUN apk update && apk add --no-cache gcompat

# Creating user
ENV APPLICATION_USER 1033
RUN adduser -D -g '' $APPLICATION_USER

# Giving permissions
RUN mkdir /app
RUN mkdir /app/resources
RUN chown -R $APPLICATION_USER /app
RUN chmod -R 755 /app

# Setting user to use when running the image
USER $APPLICATION_USER

# Copying needed files
COPY --from=build /appbuild/app/build/libs/*.jar /app/
COPY --from=build /appbuild/app/resources/ /app/resources/
WORKDIR /app

# Entrypoint definition
CMD ["sh", "-c", "java -server -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:InitialRAMFraction=2 -XX:MinRAMFraction=2 -XX:MaxRAMFraction=2 -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+UseStringDeduplication -jar liber_api_deployable-0.0.1-all.jar"]
# End Container setup --------