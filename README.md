# Todo:
1. Compress images before upload
2. Change from SellThatThing to SellYourThing
3. Consider firebase
4. Show custom message for users that have not verified their account
5. Verification code should be asked for immediately after registering
6. Implement "send a copy of this to my email"
7. Accept images
8. Add search parameters


---


# Thymeleaf Notes:
1. Show content to users that are authenticated/logged-in: `sec:authorize="isAuthenticated()"`. Add `!` to reverse

2. Show content to users that have the role of `ADMIN`: `sec:authorize="hasRole('ROLE_ADMIN')"`

3. Use `${#authentication.principal.firstName}` to get the firstName of the currently logged-in user. 
Make sure to use `sec:authorize="isAuthenticated()` to make sure the person seeing it is actually logged. Intellij will complain about `firstName` not found

4. To construct texts: `th:text="|Hello, ${#authentication.principal.firstName}|"`

5. To construct links: `th:href="@{|/profile/${#authentication.principal.accountId}|}"`

6. `th:field` overrides `th:id`, `th:name`, and `th:value`


---

# Connect MySql to Heroku
### Phase 1
1. Run `heroku create <websiteName>`

2. Run `heroku addons:create cleardb:ignite -a websiteName`. *cleardb: ignite makes mysql supported. Ignite specifies free version*

3. Run the website in `localhost`, so the mysql database will be created. Make sure all the tables have been created.

4. Export the **local** database by running `mysqldump -u <username> -p <databaseName> > nameOfExportFile.sql`.
    1. Password will be required, you might see a warning about entering password in terminal. The exported files will be used later.

5. Run `heroku run -a websiteName printenv` to see all environment variables. Note the JDBC variables:  `DATABASE_URL`, `DATABASE_USERNAME`, and `DATABASE_PASSWORD`. It will be used in gaining remote access below.

6. Gain remote access into the heroku database by running `mysql -u <username> -p<password> -h <dataSourceUrl>`.
    1. E.g, if your username is `b69c69d69`, and password is `ss69ss69`. For database URL, only use link from **after** `mysql://` up to `.net`. E.g `us-cdbr-west-02.cleardb.net`.

    2. What you will run is: `mysql -u b69c69d69 -pss69ss69 -h us-cdbr-west-02.cleardb.net`. *Note that there's no space between the -p in password*

7. At this point, I should be in mysql mode, connect to the database by running `connect <databaseName>`.
    1. Find `databaseName` by running `SHOW DATABASES;`. It will be in form of `heroku_1s232dsf23232`

8. Run `select @@character_set_database, @@collation_database;`.
    1. Below, I'm assuming I got `utf8` for `character_set_database` and `utf8_general_ci` for `collation_database`

9. Open the sql file you exported earlier. Set the database with `USE <databaseName>;` before any Sql.  Find and replace all values of CHARSET and COLLATE. E.g:
    1. Find `CHARSET=utf8mb4` and replace with `CHARSET=utf8`.

    2. Also, find  `COLLATE=utf8mb4_0900_ai_ci` and replace with `COLLATE=utf8_general_ci`

10. Import the .sql file into the remote database by running (not in sql model): `mysql -u <username> -p<password> -h <datasourceUrl> < <pathOfExportedDb>`. You should get a password warning

11. Verify the shit has been installed. Connect to remote database again (#6, #7) , run `SHOW TABLES;`. The tables shown should be the same as what I had in the local database

### Phase 2
1. Login to heroku on website. To your website dashboard, under deploy, choose github and link the repo/branch you want.

2. In settings -> Config vars, `CLEARDB_DATABASE_URL` is there by default.

3. `SPRING_PROFILES_ACTIVE`  to specify which `.properties` file to use. If I want to use a `application-test.properties` file, the value of `SPRING_PROFILES_ACTIVE` will be `test`.

4. Environment variables can also be set from here. Use in .properties file as `${VARIABLE_NAME}`

5. To use `application-dev.properties` file in `localhost`, top kinda right of screen -> edit configurations -> modify options ->  make sire Add VM options is checked -> in space for VM Options: `-Dspring.profiles.active=dev`

6. To set localhost environment variable: same as above, just make sure Environment Variables is checked


### Notes
1. Environment variables from heroku have a higher priority that .`properties` file, so no need to update `username` and `password` in .`properties` file