<h1>Pokemon Database</h1>
<p>This was the final project of TCSS 445 - Database Systems Design at UWT</p>
<p>I choose Pokemon as the content of the database to allow for some very complex queries. You can see examples of some of these queries at the end of the file src/MySQL/Pokemon_Database_Init.sql</p>
<p>Sometime in the future, I plan to make this a more user-friendly JS web application. Being able to query all Pokemon like this can be useful for people who play the mainline Pokemon games a lot.</p>

<pre>
Running the Project:

Step 1: Install and set up a MySQL server.

Step 2: Run 'src/MySQL/Pokemon_Database_Init.sql' in MySQL to initialize the database.

Step 3: Navigate to 'db_config.txt' and enter the URL of your MySQL server, and the Username and Password of a user on that server.

	**The URL must start with 'jdbc:mysql:' and end with 'POKEMON_DB'**

	**If your MySQL server is a local server, you should only need to change the localhost port of the existing URL**

	**Leading and Trailing whitespace is trimmed off when parsing the file, if there is whitespace at the beginning or end of your username or password, it will not properly parse**

	**Please maintain the current format of the config file, the ':=' sequence is used when parsing to see where the URL/Username/Password begins**

Step 4: Run the project in InteliJ, or compile the project to a jar file in InteliJ

	**A file named 'log.txt' is generated when MySQL errors occur. If the application is acting strange, check to see if that file was generated**
 </pre>
