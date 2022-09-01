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
public class ExpenseGemPolisher {
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
   String ex_gpexid;
   String ex_gsalary;
   String ex_bonus;
   String ex_gpno;

    public ExpenseGemPolisher(String ex_id, String ex_ono, String ex_tot, String ex_pmethod, String ex_pdate, String ex_status, String ex_type, String ex_gpexid, String ex_gsalary, String ex_bonus,String ex_gpno) {
        this.ex_id = ex_id;
        this.ex_ono = ex_ono;
        this.ex_tot = ex_tot;
        this.ex_pmethod = ex_pmethod;
        this.ex_pdate = ex_pdate;
        this.ex_status = ex_status;
        this.ex_type = ex_type;
        this.ex_gpexid = ex_gpexid;
        this.ex_gsalary = ex_gsalary;
        this.ex_bonus = ex_bonus;
        this.ex_gpno =ex_gpno;
    }
        public void input(){
        try{ String query = "INSERT INTO `expensegempolisher`(`gex_id`, `ex_no`, `ex_ono`, `ex_eno`, `ex_fsalary`, `ex_bonus`, `ex_tot`, `ex_pmethod`, `ex_pdate`, `ex_status`) VALUES  (?,?,?,?,?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setString(1,ex_gpexid);
           pst.setInt(2,Integer.valueOf(ex_id));
           pst.setInt(3,Integer.valueOf(ex_ono));
           pst.setString(4,ex_gpno);
           pst.setString(5,ex_gsalary);
           pst.setString(6,ex_bonus);
           pst.setString(7,ex_tot);
           pst.setString(8,ex_pmethod);
           pst.setString(9,ex_pdate);
           pst.setString(10,ex_status);
           pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Gem Polisher Salary Paid Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }



    
}
