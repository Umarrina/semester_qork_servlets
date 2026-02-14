FROM tomcat:9.0.109-jdk21

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/Amirova_Semester_Work_Servlerts-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]