Spring boot, Kotlin, Hibernate, Flyway, MariaDB

### Architecture

We are using MVC from this [reference](https://docs.google.com/spreadsheets/d/1XNUaElLfmv7YfiC9OapcDZGHpXJlMe5vbMJuuD9oy4A/edit#gid=1526999019).

### Pull requests template

Please check out [here](https://github.com/kotlin-dev-studio/vitstore_api/blob/develop/.github/pull_request_template.md).

### Run the application
- Clone project from branch `exmaple-api-app`
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

Swagger API Document

- Available at http://localhost:8080/swagger-ui/

Mailcatcher

> Prevent send mail from development environment to public.

- Available at http://localhost:1080/
