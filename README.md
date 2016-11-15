# Spring Cloud Task with [Cloud Foundry V3 API](http://v3-apidocs.cloudfoundry.org/)

Run Spring Cloud Task as a Cloud Foundry's short lived task

```
cf install-plugin https://github.com/cloudfoundry/v3-cli-plugin/releases/download/0.6.5/v3-cli-plugin.osx
./mvnw clean package -DskipTests=true
jar -uvf target/cf-spring-batch-demo-0.0.1-SNAPSHOT.jar Procfile 
cf v3-push hello-batch -b java_buildpack_offline -p target/cf-spring-batch-demo-0.0.1-SNAPSHOT.jar
cf create-service p-mysql 100mb task-db
cf v3-bind-service hello-batch task-db 
cf v3-run-task hello-batch hello ".java-buildpack/open_jdk_jre/bin/java org.springframework.boot.loader.JarLauncher"
```

Tested with

* Pivotal Cloud Foundry: 1.8
* CC API Version: 2.58.0

## Run Task

Run task 1st

``` console
t$ cf v3-run-task hello-batch hello ".java-buildpack/open_jdk_jre/bin/java org.springframework.boot.loader.JarLauncher"
OK

Running task hello on app hello-batch...

Tailing logs for app hello-batch...

2016-10-28T04:05:25.83+0900 [APP/TASK/hello/0]OUT Creating container
2016-10-28T04:05:26.17+0900 [APP/TASK/hello/0]OUT Successfully created container
2016-10-28T04:05:28.95+0900 [APP/TASK/hello/0]OUT   .   ____          _            __ _ _
2016-10-28T04:05:28.95+0900 [APP/TASK/hello/0]OUT  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
2016-10-28T04:05:28.95+0900 [APP/TASK/hello/0]OUT ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
2016-10-28T04:05:28.95+0900 [APP/TASK/hello/0]OUT  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
2016-10-28T04:05:28.95+0900 [APP/TASK/hello/0]OUT   '  |____| .__|_| |_|_| |_\__, | / / / /
2016-10-28T04:05:28.95+0900 [APP/TASK/hello/0]OUT  =========|_|==============|___/=/_/_/_/
2016-10-28T04:05:28.96+0900 [APP/TASK/hello/0]OUT  :: Spring Boot ::        (v1.4.1.RELEASE)
2016-10-28T04:05:29.08+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:29.081  INFO 7 --- [           main] pertySourceApplicationContextInitializer : Adding 'cloud' PropertySource to ApplicationContext
2016-10-28T04:05:29.15+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:29.153  INFO 7 --- [           main] nfigurationApplicationContextInitializer : Adding cloud service auto-reconfiguration to ApplicationContext
2016-10-28T04:05:29.16+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:29.167  INFO 7 --- [           main] c.example.CfSpringBatchDemoApplication   : Starting CfSpringBatchDemoApplication on 5d341a7e-7843-41bc-9956-f0834d64681c with PID 7 (/home/vcap/app/BOOT-INF/classes started by vcap in /home/vcap/app)
2016-10-28T04:05:29.16+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:29.167  INFO 7 --- [           main] c.example.CfSpringBatchDemoApplication   : The following profiles are active: cloud
2016-10-28T04:05:29.22+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:29.221  INFO 7 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@1698c449: startup date [Thu Oct 27 19:05:29 UTC 2016]; root of context hierarchy
2016-10-28T04:05:29.74+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:29.747  INFO 7 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Overriding bean definition for bean 'transactionManager' with a different definition: replacing [Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=org.springframework.cloud.task.configuration.SimpleTaskConfiguration; factoryMethodName=transactionManager; initMethodName=null; destroyMethodName=(inferred); defined in org.springframework.cloud.task.configuration.SimpleTaskConfiguration] with [Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration; factoryMethodName=transactionManager; initMethodName=null; destroyMethodName=(inferred); defined in class path resource [org/springframework/batch/core/configuration/annotation/SimpleBatchConfiguration.class]]
2016-10-28T04:05:29.94+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:29.943  INFO 7 --- [           main] urceCloudServiceBeanFactoryPostProcessor : Auto-reconfiguring beans of type javax.sql.DataSource
2016-10-28T04:05:29.96+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:29.959  INFO 7 --- [           main] o.c.r.o.s.c.s.r.PooledDataSourceCreator  : Found Tomcat high-performance connection pool on the classpath. Using it for DataSource connection pooling.
2016-10-28T04:05:29.98+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:29.982  INFO 7 --- [           main] urceCloudServiceBeanFactoryPostProcessor : Reconfigured bean dataSource into singleton service connector org.apache.tomcat.jdbc.pool.DataSource@39aeed2f{ConnectionPool[defaultAutoCommit=null; defaultReadOnly=null; defaultTransactionIsolation=-1; defaultCatalog=null; driverClassName=com.mysql.jdbc.Driver; maxActive=4; maxIdle=100; minIdle=0; initialSize=0; maxWait=30000; testOnBorrow=true; testOnReturn=false; timeBetweenEvictionRunsMillis=5000; numTestsPerEvictionRun=0; minEvictableIdleTimeMillis=60000; testWhileIdle=false; testOnConnect=false; password=********; url=jdbc:mysql://10.0.0.67:3306/cf_66990a51_8d77_45c0_945d_080cebcbae28?user=zgIL9jqODuKqlTzq&password=H9LYOXGNRP9ENOJa; username=null; validationQuery=/* ping */ SELECT 1; validationQueryTimeout=-1; validatorClassName=null; validationInterval=3000; accessToUnderlyingConnectionAllowed=true; removeAbandoned=false; removeAbandonedTimeout=60; logAbandoned=false; connectionProperties=null; initSQL=null; jdbcInterceptors=null; jmxEnabled=true; fairQueue=true; useEquals=true; abandonWhenPercentageFull=0; maxAge=0; useLock=false; dataSource=null; dataSourceJNDI=null; suspectTimeout=0; alternateUsernameAllowed=false; commitOnReturn=false; rollbackOnReturn=false; useDisposableConnectionFacade=true; logValidationErrors=false; propagateInterruptState=false; ignoreExceptionOnPreLoad=false; }
2016-10-28T04:05:29.99+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:29.993  WARN 7 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.stepScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
2016-10-28T04:05:30.00+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:30.003  WARN 7 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.jobScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
2016-10-28T04:05:30.05+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:30.054  INFO 7 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.cloud.task.batch.configuration.TaskBatchAutoConfiguration' of type [class org.springframework.cloud.task.batch.configuration.TaskBatchAutoConfiguration$$EnhancerBySpringCGLIB$$90e50f6a] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2016-10-28T04:05:30.06+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:30.061  INFO 7 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration' of type [class org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration$$EnhancerBySpringCGLIB$$b7563de] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2016-10-28T04:05:30.10+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:30.106  INFO 7 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.cloud.task.batch.listener.BatchEventAutoConfiguration' of type [class org.springframework.cloud.task.batch.listener.BatchEventAutoConfiguration$$EnhancerBySpringCGLIB$$d93b196d] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2016-10-28T04:05:30.26+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:30.261  WARN 7 --- [           main] o.a.tomcat.jdbc.pool.ConnectionPool      : maxIdle is larger than maxActive, setting maxIdle to: 4
2016-10-28T04:05:30.57+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:30.575 DEBUG 7 --- [           main] o.s.c.t.c.SimpleTaskConfiguration        : Using org.springframework.cloud.task.configuration.DefaultTaskConfigurer TaskConfigurer
2016-10-28T04:05:30.68+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:30.683 DEBUG 7 --- [           main] o.s.c.t.r.s.TaskRepositoryInitializer    : Initializing task schema for mysql database
2016-10-28T04:05:30.68+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:30.687  INFO 7 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executing SQL script from class path resource [org/springframework/cloud/task/schema-mysql.sql]
2016-10-28T04:05:31.20+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:31.203  INFO 7 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executed SQL script from class path resource [org/springframework/cloud/task/schema-mysql.sql] in 516 ms.
2016-10-28T04:05:31.35+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:31.350  INFO 7 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executing SQL script from class path resource [org/springframework/batch/core/schema-mysql.sql]
2016-10-28T04:05:32.55+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:32.555  INFO 7 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executed SQL script from class path resource [org/springframework/batch/core/schema-mysql.sql] in 1204 ms.
2016-10-28T04:05:32.64+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:32.641  INFO 7 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2016-10-28T04:05:32.64+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:32.646  INFO 7 --- [           main] o.s.c.support.DefaultLifecycleProcessor  : Starting beans in phase 0
2016-10-28T04:05:32.66+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:32.666 DEBUG 7 --- [           main] o.s.c.t.r.support.SimpleTaskRepository   : Creating: TaskExecution{executionId=1, exitCode=null, taskName='hellobatch:cloud', startTime=Thu Oct 27 19:05:32 UTC 2016, endTime=null, exitMessage='null', arguments=[]}
2016-10-28T04:05:32.67+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:32.674  INFO 7 --- [           main] o.s.b.a.b.JobLauncherCommandLineRunner   : Running default command line with: []
2016-10-28T04:05:32.68+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:32.679  INFO 7 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: MYSQL
2016-10-28T04:05:32.80+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:32.804  INFO 7 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2016-10-28T04:05:32.88+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:32.882  INFO 7 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{run.id=1}]
2016-10-28T04:05:32.89+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:32.896  INFO 7 --- [           main] o.s.c.t.b.l.TaskBatchExecutionListener   : The job execution id 1 was run within the task execution 1
2016-10-28T04:05:32.93+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:32.929  INFO 7 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
2016-10-28T04:05:32.95+0900 [APP/TASK/hello/0]OUT Tasklet has run
2016-10-28T04:05:33.00+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:33.006  INFO 7 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step2]
2016-10-28T04:05:33.02+0900 [APP/TASK/hello/0]OUT >> -1
2016-10-28T04:05:33.02+0900 [APP/TASK/hello/0]OUT >> -2
2016-10-28T04:05:33.02+0900 [APP/TASK/hello/0]OUT >> -3
2016-10-28T04:05:33.03+0900 [APP/TASK/hello/0]OUT >> -4
2016-10-28T04:05:33.03+0900 [APP/TASK/hello/0]OUT >> -5
2016-10-28T04:05:33.03+0900 [APP/TASK/hello/0]OUT >> -6
2016-10-28T04:05:33.08+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:33.081  INFO 7 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{run.id=1}] and the following status: [COMPLETED]
2016-10-28T04:05:33.08+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:33.085 DEBUG 7 --- [           main] o.s.c.t.r.support.SimpleTaskRepository   : Updating: TaskExecution with executionId=1 with the following {exitCode=0, endTime=Thu Oct 27 19:05:33 UTC 2016, exitMessage='null'}
2016-10-28T04:05:33.10+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:33.102  INFO 7 --- [           main] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@1698c449: startup date [Thu Oct 27 19:05:29 UTC 2016]; root of context hierarchy
2016-10-28T04:05:33.10+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:33.104  INFO 7 --- [           main] o.s.c.support.DefaultLifecycleProcessor  : Stopping beans in phase 0
2016-10-28T04:05:33.10+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:33.105  INFO 7 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
2016-10-28T04:05:33.10+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:05:33.106  INFO 7 --- [           main] c.example.CfSpringBatchDemoApplication   : Started CfSpringBatchDemoApplication in 4.67 seconds (JVM running for 5.136)
2016-10-28T04:05:33.11+0900 [APP/TASK/hello/0]OUT Exit status 0
2016-10-28T04:05:33.16+0900 [APP/TASK/hello/0]OUT Destroying container
Task 5d341a7e-7843-41bc-9956-f0834d64681c successfully completed.
```

Run task 2nd

``` console
$ cf v3-run-task hello-batch hello ".java-buildpack/open_jdk_jre/bin/java org.springframework.boot.loader.JarLauncher"
OK

Running task hello on app hello-batch...

Tailing logs for app hello-batch...

2016-10-28T04:06:12.12+0900 [APP/TASK/hello/0]OUT Creating container
2016-10-28T04:06:12.40+0900 [APP/TASK/hello/0]OUT Successfully created container
2016-10-28T04:06:15.12+0900 [APP/TASK/hello/0]OUT   .   ____          _            __ _ _
2016-10-28T04:06:15.12+0900 [APP/TASK/hello/0]OUT  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
2016-10-28T04:06:15.12+0900 [APP/TASK/hello/0]OUT ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
2016-10-28T04:06:15.12+0900 [APP/TASK/hello/0]OUT  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
2016-10-28T04:06:15.12+0900 [APP/TASK/hello/0]OUT   '  |____| .__|_| |_|_| |_\__, | / / / /
2016-10-28T04:06:15.12+0900 [APP/TASK/hello/0]OUT  =========|_|==============|___/=/_/_/_/
2016-10-28T04:06:15.12+0900 [APP/TASK/hello/0]OUT  :: Spring Boot ::        (v1.4.1.RELEASE)
2016-10-28T04:06:15.22+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:15.226  INFO 7 --- [           main] pertySourceApplicationContextInitializer : Adding 'cloud' PropertySource to ApplicationContext
2016-10-28T04:06:15.31+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:15.316  INFO 7 --- [           main] nfigurationApplicationContextInitializer : Adding cloud service auto-reconfiguration to ApplicationContext
2016-10-28T04:06:15.33+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:15.331  INFO 7 --- [           main] c.example.CfSpringBatchDemoApplication   : Starting CfSpringBatchDemoApplication on bc565763-7d38-4722-b76e-9fdb2083f861 with PID 7 (/home/vcap/app/BOOT-INF/classes started by vcap in /home/vcap/app)
2016-10-28T04:06:15.33+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:15.331  INFO 7 --- [           main] c.example.CfSpringBatchDemoApplication   : The following profiles are active: cloud
2016-10-28T04:06:15.38+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:15.386  INFO 7 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@1698c449: startup date [Thu Oct 27 19:06:15 UTC 2016]; root of context hierarchy
2016-10-28T04:06:15.90+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:15.901  INFO 7 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Overriding bean definition for bean 'transactionManager' with a different definition: replacing [Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=org.springframework.cloud.task.configuration.SimpleTaskConfiguration; factoryMethodName=transactionManager; initMethodName=null; destroyMethodName=(inferred); defined in org.springframework.cloud.task.configuration.SimpleTaskConfiguration] with [Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration; factoryMethodName=transactionManager; initMethodName=null; destroyMethodName=(inferred); defined in class path resource [org/springframework/batch/core/configuration/annotation/SimpleBatchConfiguration.class]]
2016-10-28T04:06:16.10+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:16.109  INFO 7 --- [           main] urceCloudServiceBeanFactoryPostProcessor : Auto-reconfiguring beans of type javax.sql.DataSource
2016-10-28T04:06:16.12+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:16.126  INFO 7 --- [           main] o.c.r.o.s.c.s.r.PooledDataSourceCreator  : Found Tomcat high-performance connection pool on the classpath. Using it for DataSource connection pooling.
2016-10-28T04:06:16.15+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:16.150  INFO 7 --- [           main] urceCloudServiceBeanFactoryPostProcessor : Reconfigured bean dataSource into singleton service connector org.apache.tomcat.jdbc.pool.DataSource@39aeed2f{ConnectionPool[defaultAutoCommit=null; defaultReadOnly=null; defaultTransactionIsolation=-1; defaultCatalog=null; driverClassName=com.mysql.jdbc.Driver; maxActive=4; maxIdle=100; minIdle=0; initialSize=0; maxWait=30000; testOnBorrow=true; testOnReturn=false; timeBetweenEvictionRunsMillis=5000; numTestsPerEvictionRun=0; minEvictableIdleTimeMillis=60000; testWhileIdle=false; testOnConnect=false; password=********; url=jdbc:mysql://10.0.0.67:3306/cf_66990a51_8d77_45c0_945d_080cebcbae28?user=zgIL9jqODuKqlTzq&password=H9LYOXGNRP9ENOJa; username=null; validationQuery=/* ping */ SELECT 1; validationQueryTimeout=-1; validatorClassName=null; validationInterval=3000; accessToUnderlyingConnectionAllowed=true; removeAbandoned=false; removeAbandonedTimeout=60; logAbandoned=false; connectionProperties=null; initSQL=null; jdbcInterceptors=null; jmxEnabled=true; fairQueue=true; useEquals=true; abandonWhenPercentageFull=0; maxAge=0; useLock=false; dataSource=null; dataSourceJNDI=null; suspectTimeout=0; alternateUsernameAllowed=false; commitOnReturn=false; rollbackOnReturn=false; useDisposableConnectionFacade=true; logValidationErrors=false; propagateInterruptState=false; ignoreExceptionOnPreLoad=false; }
2016-10-28T04:06:16.16+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:16.161  WARN 7 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.stepScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
2016-10-28T04:06:16.17+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:16.171  WARN 7 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.jobScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
2016-10-28T04:06:16.22+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:16.220  INFO 7 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.cloud.task.batch.configuration.TaskBatchAutoConfiguration' of type [class org.springframework.cloud.task.batch.configuration.TaskBatchAutoConfiguration$$EnhancerBySpringCGLIB$$90e50f6a] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2016-10-28T04:06:16.22+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:16.227  INFO 7 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration' of type [class org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration$$EnhancerBySpringCGLIB$$b7563de] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2016-10-28T04:06:16.27+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:16.274  INFO 7 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.cloud.task.batch.listener.BatchEventAutoConfiguration' of type [class org.springframework.cloud.task.batch.listener.BatchEventAutoConfiguration$$EnhancerBySpringCGLIB$$d93b196d] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2016-10-28T04:06:16.42+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:16.428  WARN 7 --- [           main] o.a.tomcat.jdbc.pool.ConnectionPool      : maxIdle is larger than maxActive, setting maxIdle to: 4
2016-10-28T04:06:16.72+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:16.723 DEBUG 7 --- [           main] o.s.c.t.c.SimpleTaskConfiguration        : Using org.springframework.cloud.task.configuration.DefaultTaskConfigurer TaskConfigurer
2016-10-28T04:06:16.76+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:16.764 DEBUG 7 --- [           main] o.s.c.t.r.s.TaskRepositoryInitializer    : Initializing task schema for mysql database
2016-10-28T04:06:16.76+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:16.768  INFO 7 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executing SQL script from class path resource [org/springframework/cloud/task/schema-mysql.sql]
2016-10-28T04:06:16.87+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:16.871  INFO 7 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executed SQL script from class path resource [org/springframework/cloud/task/schema-mysql.sql] in 103 ms.
2016-10-28T04:06:17.11+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:17.110  INFO 7 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executing SQL script from class path resource [org/springframework/batch/core/schema-mysql.sql]
2016-10-28T04:06:17.38+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:17.389  INFO 7 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executed SQL script from class path resource [org/springframework/batch/core/schema-mysql.sql] in 279 ms.
2016-10-28T04:06:17.51+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:17.516  INFO 7 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2016-10-28T04:06:17.52+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:17.522  INFO 7 --- [           main] o.s.c.support.DefaultLifecycleProcessor  : Starting beans in phase 0
2016-10-28T04:06:17.54+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:17.542 DEBUG 7 --- [           main] o.s.c.t.r.support.SimpleTaskRepository   : Creating: TaskExecution{executionId=2, exitCode=null, taskName='hellobatch:cloud', startTime=Thu Oct 27 19:06:17 UTC 2016, endTime=null, exitMessage='null', arguments=[]}
2016-10-28T04:06:17.55+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:17.550  INFO 7 --- [           main] o.s.b.a.b.JobLauncherCommandLineRunner   : Running default command line with: []
2016-10-28T04:06:17.55+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:17.556  INFO 7 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: MYSQL
2016-10-28T04:06:17.68+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:17.686  INFO 7 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2016-10-28T04:06:17.80+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:17.805  INFO 7 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{run.id=2}]
2016-10-28T04:06:17.81+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:17.818  INFO 7 --- [           main] o.s.c.t.b.l.TaskBatchExecutionListener   : The job execution id 2 was run within the task execution 2
2016-10-28T04:06:17.84+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:17.849  INFO 7 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
2016-10-28T04:06:17.87+0900 [APP/TASK/hello/0]OUT Tasklet has run
2016-10-28T04:06:17.93+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:17.936  INFO 7 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step2]
2016-10-28T04:06:17.95+0900 [APP/TASK/hello/0]OUT >> -1
2016-10-28T04:06:17.95+0900 [APP/TASK/hello/0]OUT >> -2
2016-10-28T04:06:17.95+0900 [APP/TASK/hello/0]OUT >> -3
2016-10-28T04:06:17.96+0900 [APP/TASK/hello/0]OUT >> -4
2016-10-28T04:06:17.96+0900 [APP/TASK/hello/0]OUT >> -5
2016-10-28T04:06:17.96+0900 [APP/TASK/hello/0]OUT >> -6
2016-10-28T04:06:18.01+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:18.014  INFO 7 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{run.id=2}] and the following status: [COMPLETED]
2016-10-28T04:06:18.02+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:18.020 DEBUG 7 --- [           main] o.s.c.t.r.support.SimpleTaskRepository   : Updating: TaskExecution with executionId=2 with the following {exitCode=0, endTime=Thu Oct 27 19:06:18 UTC 2016, exitMessage='null'}
2016-10-28T04:06:18.02+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:18.026  INFO 7 --- [           main] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@1698c449: startup date [Thu Oct 27 19:06:15 UTC 2016]; root of context hierarchy
2016-10-28T04:06:18.02+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:18.028  INFO 7 --- [           main] o.s.c.support.DefaultLifecycleProcessor  : Stopping beans in phase 0
2016-10-28T04:06:18.02+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:18.029  INFO 7 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
2016-10-28T04:06:18.03+0900 [APP/TASK/hello/0]OUT 2016-10-27 19:06:18.029  INFO 7 --- [           main] c.example.CfSpringBatchDemoApplication   : Started CfSpringBatchDemoApplication in 3.45 seconds (JVM running for 3.912)
2016-10-28T04:06:18.04+0900 [APP/TASK/hello/0]OUT Exit status 0
2016-10-28T04:06:18.06+0900 [APP/TASK/hello/0]OUT Destroying container
Task bc565763-7d38-4722-b76e-9fdb2083f861 successfully completed.
```

## Delete Task


``` console
$ cf curl /v3/service_bindings
{
   "pagination": {
      "total_results": 1,
      "total_pages": 1,
      "first": {
         "href": "/v3/service_bindings?page=1&per_page=50"
      },
      "last": {
         "href": "/v3/service_bindings?page=1&per_page=50"
      },
      "next": null,
      "previous": null
   },
   "resources": [
      {
         "guid": "88684788-de01-41c7-bb27-d399c266350a",
         "type": "app",
         "data": {
            "credentials": {
               "redacted_message": "[PRIVATE DATA HIDDEN IN LISTS]"
            },
            "syslog_drain_url": null,
            "volume_mounts": []
         },
         "created_at": "2016-10-27T16:13:13Z",
         "updated_at": null,
         "links": {
            "self": {
               "href": "/v3/service_bindings/88684788-de01-41c7-bb27-d399c266350a"
            },
            "service_instance": {
               "href": "/v2/service_instances/1fe43b24-c117-4fe6-9ec1-aaee382309c3"
            },
            "app": {
               "href": "/v3/apps/d03d63d0-e5ce-4a06-adb2-e8646a2ada8d"
            }
         }
      }
   ]
}
$ cf curl -X DELETE /v3/service_bindings/88684788-de01-41c7-bb27-d399c266350a
## cf curl -X DELETE `cf curl /v3/service_bindings | jq  -r .resources[0].links.self.href`

$ cf v3-delete hello-batch
Deleting app hello-batch...
OK

```
