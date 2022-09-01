/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Hadaragama
 */
public class SupOrder {

    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

    int g_id;
public   String s_id;
    String s_ooid;
    String g_bprice;
    String g_adate;
    String g_status ="Processing";
public void setInput(int g_id,String g_bprice,String g_adate,String s_ooid,String code){
         
         this.g_id = g_id;
         this.g_bprice = g_bprice;
         this.g_adate = g_adate;
         this.s_ooid = s_ooid;
         this.s_id = code;
      
        } 
public void supOrderInput(){
        try{ String query = "INSERT INTO `suporder`(`s_oid`, `s_no`, `g_no`, `s_odate`, `s_ostatus`, `s_ocost`) VALUES (?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setString(1,s_ooid);
           pst.setString(2,s_id);
           pst.setInt(3,g_id);
           pst.setString(4,g_adate);
           pst.setString(5,g_status);
           pst.setString(6,g_bprice);         
           pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Your Order Successfull");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }

    
}
