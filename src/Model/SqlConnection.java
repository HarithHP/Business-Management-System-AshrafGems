/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Hadaragama
 */
public class SqlConnection {
    public static Connection getCon(){
        
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/ashrafgems","root","");
            return con;
            
        } catch (Exception e) {
            
            return null;
        }
    
}
    
}
