### Build

```
$ ./mvnw clean package -DskipTests=true
```

### Run all jobs

``` 
$ java -jar target/spring-boot-batch-multi-jobs-0.0.1.RELEASE.jar
```

### Run only specified jobs


#### Only job1

```
$ java -jar target/spring-boot-batch-multi-jobs-0.0.1.RELEASE.jar --spring.batch.job.names=job1
```

#### Only job2

```
$ java -jar target/spring-boot-batch-multi-jobs-0.0.1.RELEASE.jar --spring.batch.job.names=job2
```

#### job1 and job2

```
$ java -jar target/spring-boot-batch-multi-jobs-0.0.1.RELEASE.jar --spring.batch.job.names=job1,job2
```