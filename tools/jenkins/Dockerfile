FROM jenkins/jenkins:lts
USER root
RUN apt-get update && \
apt-get -y install apt-transport-https \
    ca-certificates \
    curl \
    gnupg2 \
    software-properties-common

RUN curl -fsSL https://download.docker.com/linux/debian/gpg > /tmp/dkey; apt-key add /tmp/dkey

RUN add-apt-repository "deb [arch=arm64] https://download.docker.com/linux/debian $(lsb_release -cs) stable" && \
    apt-get update && \
    apt-get -y install docker-ce
RUN apt-get install -y docker-ce
RUN usermod -a -G docker jenkins
USER jenkins
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
ENV JENKINS_USER admin
ENV JENKINS_PASS admin
COPY plugins.txt /usr/share/jenkins/ref/

COPY default-user.groovy /usr/share/jenkins/ref/init.groovy.d/

WORKDIR /usr/share/jenkins/ref
ENV JENKINS_PLUGIN_MANAGER_VERSION 2.12.11
RUN curl -o jenkins-plugin-manager.jar -fsSL https://github.com/jenkinsci/plugin-installation-manager-tool/releases/download/${JENKINS_PLUGIN_MANAGER_VERSION}/jenkins-plugin-manager-${JENKINS_PLUGIN_MANAGER_VERSION}.jar
RUN java -jar jenkins-plugin-manager.jar -f /usr/share/jenkins/ref/plugins.txt --verbose
