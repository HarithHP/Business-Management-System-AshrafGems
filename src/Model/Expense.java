/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Hadaragama
 */
public class Expense {

    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

   String ex_id;
   String ex_ono;
   String ex_tot;
   String ex_pmethod;
   String ex_pdate;
   String ex_status;
   String ex_type;

    public Expense(String ex_id, String ex_ono, String ex_tot, String ex_pmethod, String ex_pdate, String ex_status, String ex_type) {
        this.ex_id = ex_id;
        this.ex_ono = ex_ono;
        this.ex_tot = ex_tot;
        this.ex_pmethod = ex_pmethod;
        this.ex_pdate = ex_pdate;
        this.ex_status = ex_status;
        this.ex_type = ex_type;
    }
        public void input(){
        try{ String query = "INSERT INTO `expense`(`ex_id`, `ex_ono`, `ex_tot`, `ex_pmethod`, `ex_pdate`, `ex_status`, `ex_type`) VALUES (?,?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setInt(1,Integer.valueOf(ex_id));
           pst.setInt(2,Integer.valueOf(ex_ono));
           pst.setString(3,ex_tot);
           pst.setString(4,ex_pmethod);
           pst.setString(5,ex_pdate);
           pst.setString(6,ex_status);
           pst.setString(7,ex_type);
           pst.executeUpdate();
            // JOptionPane.showMessageDialog(null, "Expense Recorded");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }

    
}
