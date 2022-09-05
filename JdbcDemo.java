// importthe packages to create jdbc application

import java.sql.*;
import java.util.Scanner;

public class JdbcDemo{
    // Set the jdbc driver and  and database url
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // static final String DB_URL = "jdbc:mysql://localhost/collegedb?allowPublicKeyRetrieval=true&useSSL=false";
    static final String DB_URL = "jdbc:mysql://localhost?useSSL=false";
    Scanner scanner = new Scanner(System.in);

    // Database credentials
    static final String USER = "root";
    static final String PASS = "admin";
    public static void insert_course(CreateTable course, String record)
    {
        course.insert("(name)", record);
    }
    public static void insert_faculty(CreateTable faculty, String record)
    {
        faculty.insert("(name, age, email)", record);
    }
    public static void insert_student(CreateTable student, String record)
    {
        student.insert("(name, ta, phone)", record);
    }
    public static void insert_owned_by_sc(CreateTable owned_by_sc, String record)
    {
        owned_by_sc.insert("(cid, rno)", record);
    }
    public static void insert_owned_by_fc(CreateTable owned_by_fc, String record)
    {
        owned_by_fc.insert("(cid, fid)", record);
    }   

    public static void main(String[] args)
    {
        Connection conn = null;
        Statement stmt = null;
        String faculty_schema ="",student_schema ="",course_schema = "",owned_by_sc_schema = "",owned_by_fc_schema = "";
        Scanner scanner = new Scanner(System.in);

        // Step 2 Connecting to the database
        try{
            //step 2a : Register jdbc driver
            Class.forName(JDBC_DRIVER);
            // step 2b :Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // step 2c: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            // Step 3 : Query to database

            CreateDatabase db = new CreateDatabase("collegedb");
            int status = db.load_database(stmt);

            if(status == 1)
            {
                course_schema = "(" +
                    "cid INT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(30) NOT NULL UNIQUE," +
                    "PRIMARY KEY (cid)" +
                ")";
                student_schema = "(" +
                    "rno INT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(30) NOT NULL," +
                    "ta INT," +
                    "phone CHAR(10) NOT NULL UNIQUE," +
                    "PRIMARY KEY (rno)" +
                ")";
                faculty_schema = "(" +
                    "fid INT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(30) NOT NULL," +
                    "age INT NOT NULL," +
                    "email VARCHAR(40) NOT NULL UNIQUE," +
                    "PRIMARY KEY (fid)" +
                ")";
                owned_by_fc_schema = "(" +
                    "cid INT NOT NULL," +
                    "fid INT NOT NULL," +
                    "PRIMARY KEY (cid,fid)" +
                ")";
                owned_by_sc_schema = "(" +
                    "cid INT NOT NULL," +
                    "rno INT NOT NULL," +
                    "PRIMARY KEY (cid,rno)" +
                ")";
            }
            CreateTable course = new CreateTable("Course", course_schema, stmt, status);
            CreateTable student = new CreateTable("Student", student_schema, stmt, status);
            CreateTable faculty = new CreateTable("Faculty", faculty_schema, stmt, status);
            CreateTable owned_by_sc = new CreateTable("Owned_by_sc", owned_by_sc_schema, stmt, status);
            CreateTable owned_by_fc = new CreateTable("Owned_by_fc", owned_by_fc_schema, stmt, status);

            if(status == 1)
            {
                student.foreign_constraint("ta", "cid", "Course", 1);
                owned_by_sc.foreign_constraint("rno", "rno", "Student", 2);
                owned_by_sc.foreign_constraint("cid", "cid", "Course", 3);
                owned_by_fc.foreign_constraint("cid", "cid", "Course", 4);
                owned_by_fc.foreign_constraint("fid", "fid", "Faculty", 5);
                // insert data into the faculty
                insert_faculty(faculty, "('Amit Chattopadhyay', 38, 'amit@iiitb.ac.in')");
                insert_faculty(faculty, "('Pradeesha Ashok', 40, 'pradeesha@iiitb.ac.in')");
                insert_faculty(faculty, "('Chandrashekar Ramanathan', 42, 'rc@iiitb.ac.in')");
                insert_faculty(faculty, "('Uttam Kumar', 36, 'uttam@iiitb.ac.in')");
                insert_faculty(faculty, "('Bidisha Choudhary', 35, 'bidisha@iiitb.ac.in')");
                insert_faculty(faculty, "('Vinod Reddy', 44, 'vinod@iiitb.ac.in')");
                // insert data into course
                insert_course(course, "('Computational Topology')");
                insert_course(course, "('DBMS')");
                insert_course(course, "('Maths')");
                insert_course(course, "('Signal Processing')");
                insert_course(course, "('Algorithm Design')");
                insert_course(course, "('History of ideas')");
                // insert data into the student
                insert_student(student, "('Mukul Gupta', 1, 1234567890)");
                insert_student(student, "('Rudransh Dixit', NULL, 2345678901)");
                insert_student(student, "('Pushpang Patel', 5, 3456789012)");
                insert_student(student, "('Aryan Bhatt', NULL, 4567890123)");
                insert_student(student, "('Darpan Singh', 2, 5678901234)");
                insert_student(student, "('Shashank Shekhar', NULL, 6789012345)");
                // linking data of student and course
                insert_owned_by_sc(owned_by_sc, "(1, 1)");
                insert_owned_by_sc(owned_by_sc, "(1, 2)");
                insert_owned_by_sc(owned_by_sc, "(1, 5)");
                insert_owned_by_sc(owned_by_sc, "(1, 6)");
                insert_owned_by_sc(owned_by_sc, "(2, 1)");
                insert_owned_by_sc(owned_by_sc, "(2, 2)");
                insert_owned_by_sc(owned_by_sc, "(2, 5)");
                insert_owned_by_sc(owned_by_sc, "(2, 6)");
                insert_owned_by_sc(owned_by_sc, "(3, 3)");
                insert_owned_by_sc(owned_by_sc, "(3, 4)");
                insert_owned_by_sc(owned_by_sc, "(4, 1)");
                insert_owned_by_sc(owned_by_sc, "(4, 5)");
                insert_owned_by_sc(owned_by_sc, "(4, 3)");
                insert_owned_by_sc(owned_by_sc, "(5, 6)");
                insert_owned_by_sc(owned_by_sc, "(6, 1)");
                insert_owned_by_sc(owned_by_sc, "(6, 2)");
                insert_owned_by_sc(owned_by_sc, "(6, 3)");
                insert_owned_by_sc(owned_by_sc, "(6, 4)");
                insert_owned_by_sc(owned_by_sc, "(6, 5)");
                insert_owned_by_sc(owned_by_sc, "(6, 6)");
                // linking data of faculty and course
                insert_owned_by_fc(owned_by_fc, "(1, 1)");
                insert_owned_by_fc(owned_by_fc, "(2, 3)");
                insert_owned_by_fc(owned_by_fc, "(2, 4)");
                insert_owned_by_fc(owned_by_fc, "(3, 1)");
                insert_owned_by_fc(owned_by_fc, "(3, 2)");
                insert_owned_by_fc(owned_by_fc, "(4, 6)");
                insert_owned_by_fc(owned_by_fc, "(5, 2)");
                insert_owned_by_fc(owned_by_fc, "(6, 5)");
            }
            String sql = "";
            while(true)
            {
                System.out.println("Choose any of the following option for your query:");
                System.out.println("1. -> Add new Faculty");
                System.out.println("2. -> Add new Student");
                System.out.println("3. -> Add new Course");
                System.out.println("4. -> Assign Teaching Assistant");
                System.out.println("5. -> Retrieve Faculty information");
                System.out.println("6. -> Retrieve Student information");
                System.out.println("7. -> Retrieve Course information");
                System.out.println("8. -> Update Student information");
                System.out.println("9. -> Remove a Student from college");
                System.out.println("10. -> Drop the college database");
                System.out.println("0. -> Exit");
                int option = scanner.nextInt();
                if(option == 1)
                {
                    // sql = "insert into Faculty(name, age) values('abc', 12)";
                    System.out.println("Please Enter the name of faculty...");
                    String c = scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.println("Please Enter the age of faculty...");
                    int age = scanner.nextInt();
                    System.out.println("Please Enter the email of faculty...");
                    String e = scanner.nextLine();
                    String email = scanner.nextLine();
                    insert_faculty(faculty, "('" + name + "', " + age +", '" + email + "')");
                    System.out.println("Please enter the course that faculty teaches...");
                    int flag = 1;
                    sql = "select fid from Faculty where email='"+email+"'";
                    try{
                        ResultSet rs = stmt.executeQuery(sql);
                        int fid = 0;
                        int count = 0;
                        if(rs.next())
                        {
                            fid = rs.getInt("fid");
                        }
                        while(flag == 1 || count == 0)
                        {
                            System.out.println("Press 1 to add the course that faculty teaches or 0 to exit..");
                            flag = scanner.nextInt();
                            if((flag == 0) && count>0)
                            {
                                break;
                            }
                            System.out.println("Please enter the course id..");
                            int cid = scanner.nextInt();
                            insert_owned_by_fc(owned_by_fc, "(" + cid + ", " + fid + ")");
                            count++;
                        }
                        System.out.println("Faculty Added Successfully");
                    }
                    catch(SQLException se)
                    {
                        se.printStackTrace();
                    }
                }
                else if(option == 2)
                {
                    System.out.println("Please Enter the name of student...");
                    String n = scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.println("Enter the course id for which you want to assign this student as a TA...");
                    System.out.println("Enter 0 if you don't want to assign this student as a TA..");
                    int cid = scanner.nextInt();
                    System.out.println("Please Enter the phone number...");
                    String p = scanner.nextLine();
                    String phn = scanner.nextLine();
                    if(cid == 0)
                    {
                        insert_student(student, "('" + name + "', NULL, '" + phn + "')");
                    }
                    else{
                        insert_student(student, "('" + name + "', " + cid + ", '" + phn + "')");
                    }
                    System.out.println("Student should enroll in atleast 1 course ...");
                    int flag = 1;
                    int count = 0;
                    sql = "select rno from Student where phone="+phn;
                    ResultSet rs = stmt.executeQuery(sql);
                    int rno = 0;
                    if(rs.next())
                    {
                        rno = rs.getInt("rno");
                    }
                    while(flag == 1 || count==0)
                    {
                        System.out.println("Press 1 to enroll or 0 to exit..");
                        flag = scanner.nextInt();
                        if((flag == 0) && count>0)
                        {
                            break;
                        }
                        System.out.println("Please enter the course id..");
                        int link = scanner.nextInt();
                        insert_owned_by_sc(owned_by_sc, "(" + link + ", " + rno + ")");
                        count++;
                    }
                    System.out.println("Student Added Successfully");                
                }
                else if(option == 3)
                {
                    System.out.println("Enter the course you want to introduce...");
                    String n = scanner.nextLine();
                    String name = scanner.nextLine();
                    insert_course(course, "('" + name + "')");
                    System.out.println("Please Enter the faculty id who will teach this course...");
                    int fid = scanner.nextInt();
                    sql = "select cid from Course where name='" + name + "'";
                    try{
                        ResultSet rs = stmt.executeQuery(sql);
                        if(rs.next())
                        {
                            int cid = rs.getInt("cid");
                            insert_owned_by_fc(owned_by_fc, "(" + cid + ", " + fid + ")");
                        }
                    }
                    catch(SQLException se)
                    {
                        se.printStackTrace();
                    }
                    System.out.println("Course Added Successfully");
                }
                else if(option == 4)
                {
                    System.out.println("Enter the student roll number who you want to assigned as a TA...");
                    int rno = scanner.nextInt();
                    System.out.println("Enter the course id ...");
                    System.out.println("/* NOTE: A student can be a TA of atmost 1 course, if this student is already a TA and we assign this student as a TA for the new course then the student will be a TA for the new course only */");
                    int cid = scanner.nextInt();
                    sql = "update Student set ta=" + cid + " where rno=" + rno;
                    try{
                        stmt.executeUpdate(sql);
                        System.out.println("Ta assigned successfully");
                    }
                    catch(SQLException se)
                    {
                        System.out.println("Ta can't be added successfully");
                    }
                }
                else if(option == 5)
                {
                    System.out.println("Please enter the faculty id...");
                    System.out.println("PLease enter 0 if you want to see all faculty information...");
                    int fid = scanner.nextInt();
                    if(fid == 0)
                    {
                        sql = "select * from Faculty";
                        try{
                            ResultSet rs = stmt.executeQuery(sql);
                            while(rs.next())
                            {
                                int id = rs.getInt("fid");
                                String name = rs.getString("name");
                                int age = rs.getInt("age");
                                String email = rs.getString("email");
                                System.out.println("fid: " + id);
                                System.out.println("name: " + name);
                                System.out.println("age: " + age);
                                System.out.println("email " + email);
                                System.out.println("Following courses are taught by the faculty ....");
                                String sql2 = "select c.name from Course c where c.cid in (select o.cid from Owned_by_fc o where o.fid=" + id + ")";
                                Statement stmt1 = conn.createStatement();
                                ResultSet rs2 = stmt1.executeQuery(sql2);
                            
                                while(rs2.next())
                                {
                                    String crs = rs2.getString("name");
                                    System.out.println(crs);
                                }
                    
                            }
                        }
                        catch(SQLException se)
                        {
                            se.printStackTrace();
                            System.out.println("Can't retrieve faculty information");
                        }
                    }
                    else{
                        sql = "select * from Faculty where fid=" + fid;
                        ResultSet rs = stmt.executeQuery(sql);
                        try{
                            if(rs.next())
                            {
                                int id = rs.getInt("fid");
                                String name = rs.getString("name");
                                int age = rs.getInt("age");
                                String email = rs.getString("email");
                                System.out.println("fid: " + id);
                                System.out.println("name: " + name);
                                System.out.println("age: " + age);
                                System.out.println("email " + email);
                                System.out.println("Following courses are taught by the faculty ....");
                                String sql2 = "select c.name from Course c where c.cid in (select o.cid from Owned_by_fc o where o.fid=" + id + ")";
                                Statement stmt1 = conn.createStatement();
                                ResultSet rs2 = stmt1.executeQuery(sql2);
                                while(rs2.next())
                                {
                                    String crs = rs2.getString("name");
                                    System.out.println(crs);
                                }
                            }                        
                        }
                        catch(SQLException se)
                        {
                            se.printStackTrace();
                            System.out.println("Can't retrieve faculty information");
                        }                    
                    }
                }
                else if(option == 6)
                {
                    System.out.println("Enter the roll number you want to retrieve information ...");
                    System.out.println("Press 0 if you want to retrieve all student information");
                    int rno = scanner.nextInt();
                    if(rno == 0)
                    {
                        sql = "select * from Student";
                        try{
                            ResultSet rs = stmt.executeQuery(sql);
                            while(rs.next())
                            {
                                int id = rs.getInt("rno");
                                String name = rs.getString("name");
                                String phone = rs.getString("phone");
                                System.out.println("Roll no.: " + id);
                                System.out.println("name: " + name);
                                System.out.println("phone no.: " + phone);
                                System.out.println("Following courses are taken by the student ....");
                                String sql2 = "select c.name from Course c where c.cid in (select o.cid from Owned_by_sc o where o.rno=" + id + ")";
                                Statement stmt1 = conn.createStatement();
                                ResultSet rs2 = stmt1.executeQuery(sql2);
                                while(rs2.next())
                                {
                                    String crs = rs2.getString("name");
                                    System.out.println(crs);
                                }
                            }
                        }
                        catch(SQLException se)
                        {
                            System.out.println("Can't retrieve student information");
                            se.printStackTrace();
                        }
                    }
                    else{
                        sql = "select * from Student where rno=" + rno;
                        ResultSet rs = stmt.executeQuery(sql);
                        try{
                            while(rs.next())
                            {
                                int id = rs.getInt("rno");
                                String name = rs.getString("name");
                                String phone = rs.getString("phone");
                                System.out.println("fid: " + id);
                                System.out.println("name: " + name);
                                System.out.println("phone no.: " + phone);
                                System.out.println("Following courses are taken by the student ....");
                                String sql2 = "select c.name from Course c where c.cid in (select o.cid from Owned_by_sc o where o.rno=" + id + ")";
                                Statement stmt1 = conn.createStatement();
                                ResultSet rs2 = stmt1.executeQuery(sql2);
                                while(rs2.next())
                                {
                                    String crs = rs2.getString("name");
                                    System.out.println(crs);
                                }                            
                            }                        
                        }
                        catch(SQLException se)
                        {
                            System.out.println("Can't retrieve Student information");
                        }                    
                    }                
                }
                else if(option == 7)
                {
                    System.out.println("Enter the course id of course or enter 0 for retrieving all the courses");
                    int cid = scanner.nextInt();
                    if(cid == 0)
                    {
                        sql = "select * from Course";
                        try{
                            ResultSet rs = stmt.executeQuery(sql);
                            while(rs.next())
                            {
                                int id = rs.getInt("cid");
                                String name = rs.getString("name");
                                System.out.println("Course id: " + id);
                                System.out.println("Course name: " + name);
                                String sql2 = "select f.name from Faculty f where f.fid in (select o.fid from Owned_by_fc o where o.cid =" + id + ")";
                                Statement stmt1 = conn.createStatement();
                                ResultSet rs2 = stmt1.executeQuery(sql2);
                                System.out.println("Faculty teaching " + name + " course");
                                while(rs2.next())
                                {
                                    String f_name = rs2.getString("name");
                                    System.out.println(f_name);
                                }
                            }
                        } catch(SQLException se)
                        {
                            System.out.println("Can't Show course information");
                        }
                    }
                    else{
                        sql = "select * from Course where cid=" + cid;
                        try{
                            ResultSet rs = stmt.executeQuery(sql);
                            if(rs.next())
                            {
                                int id = rs.getInt("cid");
                                String name = rs.getString("name");
                                System.out.println("Course id: " + id);
                                System.out.println("Course name: " + name);
                                String sql2 = "select f.name from Faculty f where f.fid in (select o.fid from Owned_by_fc o where o.cid =" + id + ")";
                                Statement stmt1 = conn.createStatement();
                                ResultSet rs2 = stmt1.executeQuery(sql2);
                                System.out.println("Faculty teaching " + name + " course");
                                while(rs2.next())
                                {
                                    String f_name = rs2.getString("name");
                                    System.out.println(f_name);
                                }
                            }
                        } catch(SQLException se)
                        {
                            se.printStackTrace();
                            System.out.println("Can't Show course information");
                        }
                    }
                }
                else if(option == 8)
                {
                    System.out.println("Enter the roll no. of student you want to update");
                    int rno = scanner.nextInt();
                    System.out.println("Which of the following you want to update:");
                    System.out.println("1. -> Name");
                    System.out.println("2. -> Phone no.");
                    System.out.println("3. -> Enroll/Drop the course");
                    int id = scanner.nextInt();
                    if(id == 1)
                    {
                        System.out.println("Enter the new name");
                        String n = scanner.nextLine();
                        String new_name = scanner.nextLine();
                        sql = "update Student set name = '" + new_name + "' where rno=" + rno;
                        // update Student set name = 'mukul' where rno=1;
                        try{
                            stmt.executeUpdate(sql);
                        }
                        catch(SQLException se)
                        {
                            se.printStackTrace();
                        }
                    }
                    else if(id == 2)
                    {
                        System.out.println("Enter the new phone number...");
                        String n = scanner.nextLine();
                        String phone = scanner.nextLine();
                        sql = "update Student set phone='" + phone + "' where rno=" + rno;
                        try{
                            stmt.executeUpdate(sql);
                        }
                        catch(SQLException se)
                        {
                            se.printStackTrace();
                        }
                    }
                    else if(id == 3)
                    {
                        System.out.println("1. -> Enroll");
                        System.out.println("2. -> Drop");
                        int id2 = scanner.nextInt();
                        if(id2 == 1)
                        {
                            int flag = 1;
                            while(flag == 1)
                            {
                                System.out.println("Press 1 to enroll or 0 to exit..");
                                flag = scanner.nextInt();
                                if(flag == 0)
                                {
                                    break;
                                }
                                System.out.println("Please enter the course id..");
                                int link = scanner.nextInt();
                                insert_owned_by_sc(owned_by_sc, "(" + link + ", " + rno + ")");
                                System.out.println("Enrolled Successfully");
                            }
                        }
                        else if(id2 == 2)
                        {
                            // DELETE FROM table_name WHERE condition;
                            // sql = "DELETE from Owned_by_fc where rno=" + rno;
                            System.out.println("Please enter the course id..");
                            int link = scanner.nextInt();
                            sql = "DELETE from Owned_by_sc where cid=" + link +" AND " + "rno=" + rno;
                            try{
                                stmt.executeUpdate(sql);
                                System.out.println("Dropped Successfully");
                            }
                            catch(SQLException se)
                            {
                                se.printStackTrace();
                            }
                        }
                        else
                        {
                            System.out.println("Please choose the valid option");
                        }
                    }
                    else
                    {
                        System.out.println("Please choose the valid option");
                    }
                }
                else if(option == 9)
                {
                    System.out.println("Enter the roll no. of the student you want to remove...");
                    int rno = scanner.nextInt();
                    sql = " DELETE from Owned_by_sc where rno=" + rno;
                    try{
                        stmt.executeUpdate(sql);
                    }
                    catch(SQLException se)
                    {
                        se.printStackTrace();
                    }
                    String sql2 = " DELETE from Student where rno=" + rno;
                    try{
                        stmt.executeUpdate(sql2);
                    }
                    catch(SQLException s)
                    {
                        s.printStackTrace();
                    }
                }
                else if(option == 10)
                {
                    sql = "drop database collegedb";
                    try{
                        stmt.execute(sql);
                    }
                    catch(SQLException se)
                    {
                        se.printStackTrace();
                    }
                    break;
                }
                else{
                    if(option == 0)
                    {
                        break;
                    }
                    else{
                        System.out.println("Invalid option Choosen");
                        break;
                    }
                }
            }
            stmt.close();
            conn.close();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if(stmt!=null)
                {
                    stmt.close();
                }
            }
            catch(SQLException se2){}
            try{
                if(conn!=null)
                {
                    conn.close();
                }
            }
            catch(SQLException se){
                    se.printStackTrace(); 
                }
            }
            System.out.println("End of Code");
    }
}

//Note : By default autocommit is on. you can set to false using con.setAutoCommit(false)

