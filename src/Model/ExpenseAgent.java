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
public class ExpenseAgent {

    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

   String ex_id;
   String ex_ono;
   String ex_tot;
   String ex_pmethod;
   String ex_pdate;
   String ex_status;

   String aex_id;
   String ex_ano;
   String ex_cono;
   String ex_sono;
   String ex_otot;
   String ex_rate;

    public void ExpenseAgent(String ex_id, String ex_ono, String ex_tot, String ex_pmethod, String ex_pdate, String ex_status, String aex_id, String ex_ano, String ex_cono, String ex_sono, String ex_otot, String ex_rate) {
        this.ex_id = ex_id;
        this.ex_ono = ex_ono;
        this.ex_tot = ex_tot;
        this.ex_pmethod = ex_pmethod;
        this.ex_pdate = ex_pdate;
        this.ex_status = ex_status;
        this.aex_id = aex_id;
        this.ex_ano = ex_ano;
        this.ex_cono = ex_cono;
        this.ex_sono = ex_sono;
        this.ex_otot = ex_otot;
        this.ex_rate = ex_rate;
    }
     public void input(){
        try{ String query = "INSERT INTO `expenseagent`(`aex_id`, `ex_no`, `ex_ono`, `ex_ano`, `ex_sono`, `ex_cono`, `ex_otot`, `ex_rate`, `ex_tot`, `ex_pmethod`, `ex_pdate`, `ex_status`) VALUES  (?,?,?,?,?,?,?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setString(1,aex_id);
           pst.setInt(2,Integer.valueOf(ex_id));
           pst.setInt(3,Integer.valueOf(ex_ono));
           pst.setString(4,ex_ano);
           pst.setString(5,ex_sono);
           pst.setString(6,ex_cono);
           pst.setString(7,ex_otot);
           pst.setString(8,ex_rate);
           pst.setString(9,ex_tot);
           pst.setString(10,ex_pmethod);
           pst.setString(11,ex_pdate);
           pst.setString(12,ex_status);
           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Agent Expense Recorded");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }






   

    
}
