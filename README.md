# Student Management System

Console-based application built with **Core Java**, **JDBC** and **MySQL**.

## Concepts demonstrated
- OOP: encapsulation (`Student`), separation of concerns (model / DAO / connection / UI)
- JDBC: `DriverManager`, `PreparedStatement`, `ResultSet`, try-with-resources
- SQL: INSERT, SELECT, UPDATE, DELETE, `LIKE` search, constraints (PK, UNIQUE, NOT NULL)
- Input validation and error handling

## Files
| File | Purpose |
|---|---|
| `schema.sql` | Creates `student_db` and `students` table with sample rows |
| `DBConnection.java` | Opens JDBC connections |
| `Student.java` | Model class |
| `StudentDAO.java` | CRUD + search SQL |
| `Main.java` | Console menu |

## Setup
1. Install JDK 8+ and MySQL.
2. Run `schema.sql` in MySQL Workbench or: `mysql -u root -p < schema.sql`
3. Download the MySQL Connector/J jar (e.g. `mysql-connector-j-8.x.jar`).
4. Set your DB password in `DBConnection.java` (default user `root`, password `root`),
   or set env vars `DB_URL`, `DB_USER`, `DB_PASSWORD`.

## Run in Eclipse
1. File > New > Java Project, copy the 4 `.java` files into `src`.
2. Right-click project > Build Path > Add External JARs > select the Connector/J jar.
3. Run `Main.java`.

## Run from terminal
```
javac *.java
java -cp .:mysql-connector-j-8.x.jar Main        # Linux/Mac
java -cp .;mysql-connector-j-8.x.jar Main        # Windows
```

## Ideas to extend
- Add a `courses` table with a foreign key and JOIN queries
- Add login with hashed passwords
- Export records to CSV
