Spring boot, Kotlin, Hibernate, Flyway, MariaDB

### Run the application
- Clone project from repository `git clone git@github.com:kotlin-dev-studio/vitstore_api.git`
- Build gradle in `build.gradle.kts`
  
- Spring boot configuration:

```shell
cp src/main/resources/application-dev.yml.example src/main/resources/application-dev.yml
cp src/main/resources/application-test.yml.example src/main/resources/application-test.yml
```

- Start mariadb container
> Make sure stop mysql server on your machine!
> Required: Docker >= 19.x.x, docker-compose >= 1.25.x
```shell
# Start docker container
cd docker
docker-compose up

# Stop docker container
docker-compose down
```
- Generate migration file
> Rule: All the migration scripts must follow a particular naming convention - V<VERSION_NUMBER>__<NAME>.sql in the folder /src/main/resources/db/migration/. 
>
> Checkout the [Official Flyway documentation](https://flywaydb.org/documentation/concepts/migrations.html) to learn more about naming convention.

Eg. `V1__CreateTableUsers.sql`
```mysql
CREATE TABLE IF NOT EXISTS users(
    id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username varchar(100) NOT NULL, 
    first_name varchar(50) NOT NULL,
    last_name varchar(50) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- SSH into docker container
```shell
docker exec -it vitstore_db mysql -u root -p # input password from application.yml

# Dev environment
use vitstore_dev;
select * from users;
select * from flyway_schema_history;
```

Klint

- Configuration file `.editorconfig` [reference](https://github.com/pinterest/ktlint#editorconfig)

- Command check and format:

```shell
./gradlew ktlintCheck
./gradlew ktlintFormat
```
