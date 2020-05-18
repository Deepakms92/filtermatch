FROM openjdk:8-jre-alpine

# App exposed ports
EXPOSE 8085

# Create underprivileged user for running app
RUN adduser -D -h /app app
# Create the application workdir
WORKDIR /app
# Run as underprivileged user
USER app

# Add Application and dependencies
COPY target/filtermatch-0.0.1-SNAPSHOT.jar .
COPY target/classes/application.properties .


# Start Application on container startup
ENTRYPOINT ["/sbin/tini", "--"]
CMD ["java", "-Djava.security.egd=file:/dev/./urandom","-jar", "filtermatch-0.0.1-SNAPSHOT.jar"]