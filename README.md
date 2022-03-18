# ZIO-HTTP integration with Quill

This project demostrate usage of ZIO-HTTP with Quill.

## Instructions
1. Download and install docker.
2. Clone this repo: `git clone 'https://github.com/amitksingh1490/demo-quill.git'`
3. Run the start script: `./start.sh` to start the postgres docker image.
4. Compile and run the examples: `com.example.demoquill.Demoquill'` (or `sbt 'runMain example.module.Main'`)
  ```
  > sbt compile
  [info] compiling 1 Scala source to /.../demo-quill/target/scala-2.13/classes ...
  [info] /.../demo-quill/src/main/scala/com/example/demoquill/service/UserService.scala:31:9: SELECT users.id, users.first_name, users.last_name, users.age, address.owner_fk, address.street, address.zip, address.state FROM user users INNER JOIN address address ON address.owner_fk = CASE WHEN users.id IS NOT NULL THEN users.id ELSE null, '', '', 0 END
  [info]     run {
  [info]         ^
  [info] /.../demo-quill/src/main/scala/com/example/demoquill/service/UserService.scala:41:9: SELECT x.id, x.first_name, x.last_name, x.age FROM user x
  ...
  [success] Total time: 21 s, completed Mar 18, 2022 4:19:26 PM
  ```
4. Run the main class to start the http server:
  ```
  > sbt 'runMain com.example.demoquill.Demoquill'
  [info] welcome to sbt 1.6.1 (AdoptOpenJDK Java 1.8.0_275)
  [info] loading global plugins from /home/alexi/.sbt/1.0/plugins
  [info] Server started on port 8090
  ```
5. Try the various APIs:
  ```
  > curl http://localhost:8090/users
  [{"id":2,"firstName":"Vlad","lastName":"Dracul","age":321},{"id":3,"firstName":"Joe","lastName":"Bloggs","age":20}, ...]
  ```
6. Stop the container `./stop.sh`
