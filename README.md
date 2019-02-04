# TeamGM
Build a roster keeping within the salary cap using a menu-driven application.
<ul><li>Database constructed with MySQL</li>
  <li>Java 8 used to begin the application, connect to the database, create all entities, and run all program methods</li>

application
-----------
<ul><li>Run application to begin the program</li>

<li>Menu contains the logic of the application. The General Manger (user) will from here decide how to build a team.</li></ul>

dao
----------
<ul><li>Dao contains all database queries for players and team data.</li></ul>

entity
---------
<ul><li>Player - An entity of a player, name, including position, depth, and team.</li>
<li>PlayerSalary - An entity of a player's salary, including salary, year, and dead cap if cut.</li>
<li>Team - An entity of a team, including team name, year, available cap space, and the roster of players on that team for that year.</li></ul>
  
options
---------
<ul><li>MenuOptions - An interface used to implement all functions from the menu.</li>
  <li>Helpers - Implements additional needed functions.</li>
  <li>Other Files - Classes that implement MenuOptions.</li></ul>
