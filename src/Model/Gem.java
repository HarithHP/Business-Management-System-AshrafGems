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
public class Gem {
    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

        int g_id;
        String g_name;
        String g_type;
        String g_color;
        String g_weight;
        String g_bprice;
        String g_sprice;
        String g_status = "Processing";
        String g_image;
        String g_adate;
        String s_ooid;
        String s_id;

       public void setInput(int g_id,String g_name,String g_type,String g_color,String g_weight,String g_bprice,String g_adate,String g_image,String s_ooid,String code){
         this.g_id = g_id;
         this.g_name =g_name;
         this.g_type = g_type;
         this.g_color = g_color;
         this.g_weight = g_weight;
         this.g_bprice = g_bprice;
         this.g_image = g_image;
         this.g_adate = g_adate;
         this.s_ooid = s_ooid;
         this.s_id = code;
      
         float sellprice=Float.parseFloat(g_bprice); 
         sellprice = sellprice*1.5f;
         g_sprice=String.valueOf(sellprice);
      
        } 

    public void setInputOwner(int g_id, String g_name, String g_type, String g_color, String g_weight, String g_bprice, String g_sprice, String g_image, String g_adate, String g_status) {
        this.g_id = g_id;
        this.g_name = g_name;
        this.g_type = g_type;
        this.g_color = g_color;
        this.g_weight = g_weight;
        this.g_bprice = g_bprice;
        this.g_sprice = g_sprice;
        this.g_image = g_image;
        this.g_adate = g_adate;
        this.g_status = g_status;
    }
       
         
       public void inputOwner(){
        try{ String query = "INSERT INTO `gem`(`g_id`, `g_name`, `g_type`, `g_color`, `g_weight`, `g_bprice`, `g_sprice`, `g_status`, `g_adate`, `g_image`) VALUES(?,?,?,?,?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setInt(1,g_id);
           pst.setString(2,g_name);
           pst.setString(3,g_type);
           pst.setString(4,g_color);
           pst.setString(5,g_weight);
           pst.setString(6,g_bprice);
           pst.setString(7,g_sprice);
           pst.setString(8,g_status);
           pst.setString(9,g_adate);
           try{
               InputStream inputStream = new FileInputStream(new File(g_image));
               pst.setBlob(10, inputStream);
               }
           catch(Exception exception){
                 JOptionPane.showMessageDialog(null,"Image Error");
                }
           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Gem Registered Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
       public void input(){
        try{ String query = "INSERT INTO `gem`(`g_id`, `g_name`, `g_type`, `g_color`, `g_weight`, `g_bprice`, `g_sprice`, `g_status`, `g_adate`, `g_image`) VALUES(?,?,?,?,?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setInt(1,g_id);
           pst.setString(2,g_name);
           pst.setString(3,g_type);
           pst.setString(4,g_color);
           pst.setString(5,g_weight);
           pst.setString(6,g_bprice);
           pst.setString(7,g_sprice);
           pst.setString(8,g_status);
           pst.setString(9,g_adate);
           try{
               InputStream inputStream = new FileInputStream(new File(g_image));
               pst.setBlob(10, inputStream);
               }
           catch(Exception exception){
                 JOptionPane.showMessageDialog(null,"Image Error");
                }
           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Gem Registered Successfully");
             SupOrder main =  new SupOrder();
             main.setInput(g_id, g_bprice, g_adate, s_ooid, s_id);
             main.supOrderInput();
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
public void update(){
          try{
           String sql = "UPDATE `gem` SET `g_name`=?,`g_type`=?,`g_color`=?,`g_weight`=?,`g_bprice`=?,`g_sprice`=?,`g_status`=?,`g_adate`=? WHERE `g_id`=?";
           pst = con.prepareStatement(sql);
           pst.setInt(9,g_id);
           pst.setString(1,g_name);
           pst.setString(2,g_type);
           pst.setString(3,g_color);
           pst.setString(4,g_weight);
           pst.setString(5,g_bprice);
           pst.setString(6,g_sprice);
           pst.setString(7,g_status);
           pst.setString(8,g_adate);
//           try{
//               InputStream inputStream = new FileInputStream(new File(g_image));
//               pst.setBlob(9, inputStream);
//               }
//           catch(Exception exception){
//                 JOptionPane.showMessageDialog(null,"Image Error");
//                }
           pst.executeUpdate();
           // JOptionPane.showMessageDialog(null, "Gem Data Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
        public void delete(){
        try{ String query = "DELETE FROM `gem` WHERE g_id =?"; 
           pst = con.prepareStatement(query);
           pst.setInt(1,g_id);
           pst.executeUpdate();
            // JOptionPane.showMessageDialog(null, "Gem Data Delete Successfully");
         }catch(SQLException | HeadlessException ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
    
    
}
