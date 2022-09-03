# Todo:

1. Compress images before upload
2. Change from SellThatThing to SellYourThing
3. Consider firebase, planet scale
4. Show custom message for users that have not verified their account
5. Implement "send a copy of this to my email"
6. Accept images
7. Log in should not be required to reply to a message
8. Facebook/google login
9. Remove request params, find way to show messages without it
10. .and().requiresChannel().anyRequest().requiresSecure();
11. Use a content security policy for Spring Boot XSS protection
12. After login, always redirect to previous page
13. Add location to register page
14. Pagination
15. Problem: When an admin deletes an account, the user still remains logged in
    1. Fix: On each request, check if the user exists in the db. If not, force logout the user and invalidate
       the session https://stackoverflow.com/a/38295610/18902234
16. Secure end points with @PreAuthorize
17. Add logging to all Service classes

---

# Thymeleaf Notes:

1. Anything between two square bracket is valid thymeleaf syntax. `[[${userEmail}]]`

2. Show content to users that are authenticated/logged-in: `sec:authorize="isAuthenticated()"`. Add `!` to reverse

3. Show content to users that have the role of `ADMIN`: `sec:authorize="hasRole('ROLE_ADMIN')"`

4. Use `${#authentication.principal.firstName}` to get the firstName of the currently logged-in user.
   Make sure to use `sec:authorize="isAuthenticated()` to make sure the person seeing it is actually logged. Intellij will complain about `firstName`
   not found

5. To construct texts: `th:text="|Hello, ${#authentication.principal.firstName}|"`

6. To construct links: `th:href="@{|/profile/${#authentication.principal.accountId}|}"`

7. `th:field` overrides `th:id`, `th:name`, and `th:value`

8.
    1. To pass only string from view to controller. Name attribute is important:

           <form th:action="@{/process}"  method="post">
              <input type="text" name="stringName"/>
              <input type="submit" />
           </form>

    2. And in the controller:

           @PostMapping("/process")
           public String process(@RequestParam String stringName) {
               return "hehe";
           }

9. To show messages in url without Request params, using SessionAttributes:
    1. In the Controller, create a method to return a HashMap (or anything really) and annotate with `@ModelAttribute("customName")` like:

           @ModelAttribute("customName")
           public HashMap<String, Boolean> customName() {
               return new HashMap<>();
           }
       The code above allows that instance of HashMap to be used in this model

    2. Annotate the controller class with `@SessionAttribute("customName")`. The annotation tells spring to treat `customName` as a session attribute.

    3. To use in a mapping, add it to the parameters. E.g:

           @PostMapping("/url")
           public String updateAccount(@ModelAttribute("customName") HashMap<String, Boolean> message) {
                 // update, delete and do what you want here. 
           }
       The value of customName will be stored in `message`.

    4. Anything that is stored in `message`, either in the above Mapping or in any Mapping (that has the parameters) - will be available for use in
       anywhere that uses this controller

    5. To use in thymeleaf, assuming customName was message and one of the key value pair is in form `<String, Boolean>`:

           <div class="w-50" th:if="${message.get('updateStatus') == true}">
               <div class="alert alert-success">Account updated successfully</div>
           </div>

           <div class="w-50" th:if="${message.get('updateStatus') == false}">
               <div class="alert alert-danger">Update failed, try again</div>
           </div>

           <th:block th:if="${message.remove('updateStatus')}"></th:block>
       If the key is not removed, it will show everytime.

    6. To use the attribute globally, in all models/views, create a ControllerAdvice class like:

           @ControllerAdvice
           public class GlobalControllerAdvice {
               @ModelAttribute("message")
               public HashMap<String, Boolean> message() {
                   return new HashMap<>(1);
               }
           }

       And remove the method that was declared in Step 1

    7. Don't forget to mark the Controller class you want to use it with. Refer to step 2

# Thymeleaf + Jquery

1. When there's a table, and inside the table, there's a button. The button triggers a modal to be opened.
   To send data from button click to modal, use jquery, like in `admin-view-account.html` and `script.js`

    1. Add your modal normally. Give it and id, e.g `modalId`. Disable the default bootstrap modal opener by removing the
       bootstrap attributes from the `<a>` or `<button>` tag. Give the tag the link to fetch the data from:

           <a th:href="@{|/admin/findId/${account.accountId}|}" class="btn btn-primary">A hehe button</a>

       The href will be used to make a get request in jquery. The result of the get request will be sent to the modal
    2. In script file, using jquery, assuming id of the modal is `modalId`:

           $('document').ready(function () {
               $('.table .btn').on('click', function (event) { // on click of table or/and button
                   event.preventDefault(); // prevents the link from opening
                   const href = $(this).attr('href');// get the href assosicated with that button
                   $.get(href, function (account) {// make a get request, response will be stored in `account`
                       $('#firstName').val(account.firstName);// store the value of account.firstName in the field with id firstName
                       $('#lastName').val(account.lastName);// same as above
                   });
                   $('#modalId').modal('show') // show the modal
              })
           })

    3. And in `AdminController`:

           @GetMapping("/findId/{id}")
           @ResponseBody
           public HashMap<String, String> getById(@PathVariable Long id) {
               Account account = accountService.findByAccountId(id);
               HashMap<String, String> accountInfo = new HashMap<>(2);
               accountInfo.put("firstName", account.getFirstName());
               accountInfo.put("lastName", account.getLastName());
               return accountInfo;
           }
       `@ResponseBody` is used so `accountInfo` can be serialized back into Mr JSON.

    4. They key of the hashmap, the `name` attribute given to the input field and the "key" in `.val(account.firstName);`
       must be the same. E.g. For firstName to be filled properly, following the jquery and GetMapping
        1. The key in accountInfo should be `firstName`.
        2. ~~The name attribute in the input tag where it would be filled should also be `firstName`~~
        3. ID can be anything, I just used firstName because copy and paste is easy

2. Change text of a button: ` $('#buttonId').html('New Text')`

3. Change form action:  ` $('#formId').attr('action', 'NewActionUrl');`

# Java Notes:

1. Records automatically create private final fields, getters, AllArgsConstructor, toString, equals
   and hashcode

2. Records can have methods, including static methods

3. Static fields are also allowed. Instance variables are not allowed

4. Records can implement interfaces

---

# Spring boot MVC notes

1. Get the url of the website. `https://www.title.com` returns well, `https://www.title.com`

       String websiteUrl = ServletUriComponentsBuilder.fromRequestUri(request)
              .replacePath(null)
              .build()
              .toUriString();

2. To check if a user is Authenticated:

       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       if (!(auth instanceof AnonymousAuthenticationToken)) { 
           // user is authenticated
           Account authAccount = (Account) auth.getPrincipal();
           userFirstName = authAccount.getFirstName();
       }

3. Flash Attributes will be removed immediately the redirected page has been rendered

---

# Connect MySql to Heroku

### Phase 1

1. Run `heroku create <websiteName>`

2. Run `heroku addons:create cleardb:ignite -a websiteName`. *cleardb: ignite makes mysql supported. Ignite specifies free version*

3. Run the website in `localhost`, so the mysql database will be created. Make sure all the tables have been created.

4. Export the **local** database by running `mysqldump -u <username> -p <databaseName> > nameOfExportFile.sql`.
    1. Password will be required, you might see a warning about entering password in terminal. The exported files will be used later.

5. Run `heroku run -a websiteName printenv` to see all environment variables. Note the JDBC variables:  `DATABASE_URL`, `DATABASE_USERNAME`,
   and `DATABASE_PASSWORD`. It will be used in gaining remote access below.

6. Gain remote access into the heroku database by running `mysql -u <username> -p<password> -h <dataSourceUrl>`.
    1. E.g, if your username is `b69c69d69`, and password is `ss69ss69`. For database URL, only use link from **after** `mysql://` up to `.net`.
       E.g `us-cdbr-west-02.cleardb.net`.

    2. What you will run is: `mysql -u b69c69d69 -pss69ss69 -h us-cdbr-west-02.cleardb.net`. *Note that there's no space between the -p in password*

7. At this point, I should be in mysql mode, connect to the database by running `connect <databaseName>`.
    1. Find `databaseName` by running `SHOW DATABASES;`. It will be in form of `heroku_1s232dsf23232`

8. Run `select @@character_set_database, @@collation_database;`.
    1. Below, I'm assuming I got `utf8` for `character_set_database` and `utf8_general_ci` for `collation_database`

9. Open the sql file you exported earlier. Set the database with `USE <databaseName>;` before any Sql. Find and replace all values of CHARSET and
   COLLATE. E.g:
    1. Find `CHARSET=utf8mb4` and replace with `CHARSET=utf8`.

    2. Also, find  `COLLATE=utf8mb4_0900_ai_ci` and replace with `COLLATE=utf8_general_ci`

10. Import the .sql file into the remote database by running (not in sql
    model): `mysql -u <username> -p<password> -h <datasourceUrl> < <pathOfExportedDb>`. You should get a password warning

11. Verify the shit has been installed. Connect to remote database again (#6, #7) , run `SHOW TABLES;`. The tables shown should be the same as what I
    had in the local database

### Phase 2

1. Login to heroku on website. To your website dashboard, under deploy, choose GitHub and link the repo/branch you want.

2. In settings -> Config vars, `CLEARDB_DATABASE_URL` is there by default.

3. `SPRING_PROFILES_ACTIVE`  to specify which `.properties` file to use. If I want to use a `application-test.properties` file, the value
   of `SPRING_PROFILES_ACTIVE` will be `test`.

4. Environment variables can also be set from here. Use in .properties file as `${VARIABLE_NAME}`

5. To use `application-dev.properties` file in `localhost`, top kinda right of screen -> edit configurations -> modify options ->  make sire Add VM
   options is checked -> in space for VM Options: `-Dspring.profiles.active=dev`

6. To set localhost environment variable: same as above, just make sure Environment Variables is checked

### Notes

1. Environment variables from heroku have a higher priority that .`properties` file, so no need to update `username` and `password` in .`properties`
   file