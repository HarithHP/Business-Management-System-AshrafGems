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
public class Invoice {
    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

        String i_id ;
        String i_cono;
        String i_cbno;
        String i_cno;
        String i_pmethod;
        String i_pdate;
        String i_cbacc;
        String i_tot; 

    public Invoice(String i_id ,String i_cono, String i_cbno, String i_cno, String i_pmethod, String i_pdate, String i_cbacc, String i_tot) {
        this.i_id=i_id;
        this.i_cono = i_cono;
        this.i_cbno = i_cbno;
        this.i_cno = i_cno;
        this.i_pmethod = i_pmethod;
        this.i_pdate = i_pdate;
        this.i_cbacc = i_cbacc;
        this.i_tot = i_tot;
    }
    public void input(){
        try{ String query = "INSERT INTO `invoice`( `i_cono`,`i_cno`, `i_baccNo`, `i_cardtype`, `i_odate`, `i_tot`) VALUES (?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           //pst.setInt(1,Integer.parseInt(i_id));
           pst.setString(1,i_cono);
           pst.setString(2,i_cno);
           pst.setString(3,i_cbacc);
           pst.setString(4,i_pmethod);
           pst.setString(5,i_pdate);
           pst.setString(6,i_tot);
           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Order Invoice Recorded");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
 
}
