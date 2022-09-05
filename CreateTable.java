import java.sql.*;
public class CreateTable {
    private String table;
    Statement s;

    public CreateTable(String name, String schema, Statement stmt, int status)
    {
        table = name;
        s = stmt;
        String sql;
        if(status == 1)
        {
            sql = "CREATE TABLE " + name + schema;
            try{
                s.execute(sql);
            }catch(SQLException se){
                se.printStackTrace();
                System.out.println("Error in creating table " + table);
            }
        }
    }

    public void foreign_constraint(String src, String ref, String refTable, int a)
    {
        String sql;
        sql = "Alter Table " + table + " ADD CONSTRAINT fk_"+ a + src + refTable + " FOREIGN KEY (" + src + ") REFERENCES " + refTable + "(" + ref + ")";
        try{
            s.executeUpdate(sql);
        } catch (SQLException se) {
            System.out.println("Can't add foreign key constraint in " + table);
        }
    }
    public void insert(String attr, String record)
    {
        String sql;
        sql = "INSERT INTO " + table + attr + " VALUES" + record;
        try{
            s.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            // System.out.println("Unable to insert Record in table " + table);
        }
    }
}
