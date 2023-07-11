Start Jenkins

```shell
sudo docker-compose up -d jenkins
```

In case of failure
```shell
jenkins-jenkins-1  | java.nio.file.FileSystemException: /tmp/jetty-0_0_0_0-8080-war-_-any-15559936221347960673: No space left on device
jenkins-jenkins-1  |    at java.base/sun.nio.fs.UnixException.translateToIOException(UnixException.java:100)
jenkins-jenkins-1  |    at java.base/sun.nio.fs.UnixException.rethrowAsIOException(UnixException.java:111)
jenkins-jenkins-1  |    at java.base/sun.nio.fs.UnixException.rethrowAsIOException(UnixException.java:116)
jenkins-jenkins-1  |    at java.base/sun.nio.fs.UnixFileSystemProvider.createDirectory(UnixFileSystemProvider.java:389)
jenkins-jenkins-1  |    at java.base/java.nio.file.Files.createDirectory(Files.java:690)
```
docker container prune -f
docker image prune -f

delete folder tools/jenkins/jenkins_data

