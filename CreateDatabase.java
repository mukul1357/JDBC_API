import java.sql.*;

public class CreateDatabase {
    private String db;
    public CreateDatabase(String name)
    {
        db = name;
    }

    public int load_database(Statement stmt)
    {
        String sql1,sql2;
        int status = 0;
        sql1 = "create database " + db;
        sql2 = "use " + db;
        try{
            stmt.execute(sql1);
            status = 1;
        }catch(SQLException se1) {
            // System.out.println("Can't create data base");
            status = -1;
        }
        try{
            stmt.execute(sql2);
        }
        catch(SQLException se2) {
            System.out.println("Can't use the database");
        }
        return status;
    }    
}
