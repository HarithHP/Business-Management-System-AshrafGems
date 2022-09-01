/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


import View.ClientMainForm;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Hadaragama
 */
public class CliOrder {
    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

        String c_oid;
        String c_no;
        int g_no;
        String c_odate;
        String c_opmethod;
        String c_cbacc;
        String c_ototal;
        public JFrame frame;

    public CliOrder(String c_oid, String c_no, int g_no, String c_odate,String c_opmethod, String c_cbacc, String c_ototal,JFrame frame) {
        this.c_oid = c_oid;
        this.c_no = c_no;
        this.g_no = g_no;
        this.c_odate = c_odate;
        this.c_opmethod = c_opmethod;
        this.c_cbacc = c_cbacc;
        this.c_ototal = c_ototal;
        this.frame = frame;
    }
    
    public void input(){
        try{ String query = "INSERT INTO `cliorder`(`c_oid`, `c_no`, `g_no`, `c_odate`,`c_ototal`) VALUES (?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setString(1,c_oid);
           pst.setString(2,c_no);
           pst.setInt(3,g_no);
           pst.setString(4,c_odate);
           pst.setString(5,c_ototal);
           pst.executeUpdate();

           JOptionPane.showMessageDialog(null, "Your Order Successfull");
           Invoice emian = new Invoice(null,c_oid, "", c_no, c_opmethod, c_odate, c_cbacc, c_ototal);
           emian.input();
           updateGemStatus();
           updateFinishGemStatus();
           // frame.setVisible(false);
           // ClientMainForm main = new ClientMainForm(c_no);
            //main.setVisible(true);
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
public void updateGemStatus(){
          try{
           String sql = "UPDATE gem SET g_status=? WHERE g_id =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,"Sold");
           pst.setInt(2,g_no);

           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Gem Status Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
    public void updateFinishGemStatus(){
          try{
           String sql = "UPDATE finish SET fg_status=? WHERE g_no =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,"Sold");
           pst.setInt(2,g_no);

           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Gem Status Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }


    

}
