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
public class RawGem {
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
        String g_rrid;
        String g_origin;
        String g_nature;
       
       
       public void setInput(int g_id,String g_name,String g_type,String g_color,String g_weight,String g_bprice,String g_adate,String g_image,String g_rrid,String g_origin,String g_nature){
         this.g_id = g_id;
         this.g_name =g_name;
         this.g_type = g_type;
         this.g_color = g_color;
         this.g_weight = g_weight;
         this.g_bprice = g_bprice;
         this.g_image = g_image;
         this.g_adate = g_adate;
         this.g_rrid =g_rrid ;
         this.g_origin = g_origin;
         this.g_nature =g_nature ;
         float sellprice=Float.parseFloat(g_bprice); 
         sellprice = sellprice*1.5f;
         g_sprice=String.valueOf(sellprice);
        } 

    public void setRawGemOwner(int g_id, String g_name, String g_type, String g_color, String g_weight, String g_bprice, String g_sprice, String g_image, String g_adate, String g_rrid, String g_origin, String g_nature,String g_status) {
        this.g_id = g_id;
        this.g_name = g_name;
        this.g_type = g_type;
        this.g_color = g_color;
        this.g_weight = g_weight;
        this.g_bprice = g_bprice;
        this.g_sprice = g_sprice;
        this.g_image = g_image;
        this.g_adate = g_adate;
        this.g_rrid = g_rrid;
        this.g_origin = g_origin;
        this.g_nature = g_nature;
        this.g_status = g_status;
    }
       
       
       public void input(){
        try{ String query = "INSERT INTO `raw`(`rg_id`, `g_no`, `rg_name`, `rg_type`, `rg_color`, `rg_weight`, `rg_bprice`, `rg_sprice`, `rg_status`, `rg_adate`, `rg_orgin`, `rg_nature`, `rg_image`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setString(1,g_rrid);
           pst.setInt(2,g_id);
           pst.setString(3,g_name);
           pst.setString(4,g_type);
           pst.setString(5,g_color);
           pst.setString(6,g_weight);
           pst.setString(7,g_bprice);
           pst.setString(8,g_sprice);
           pst.setString(9,g_status);
           pst.setString(10,g_adate);
           pst.setString(11,g_origin);
           pst.setString(12,g_nature);
           try{
               InputStream inputStream = new FileInputStream(new File(g_image));
               pst.setBlob(13, inputStream);
               }
           catch(Exception exception){
                 JOptionPane.showMessageDialog(null,"Image Error");
                }
           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Raw Gem Registered Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
       public void ownerInput(){
        try{ String query = "INSERT INTO `raw`(`rg_id`, `g_no`, `rg_name`, `rg_type`, `rg_color`, `rg_weight`, `rg_bprice`, `rg_sprice`, `rg_status`, `rg_adate`, `rg_orgin`, `rg_nature`, `rg_image`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setString(1,g_rrid);
           pst.setInt(2,g_id);
           pst.setString(3,g_name);
           pst.setString(4,g_type);
           pst.setString(5,g_color);
           pst.setString(6,g_weight);
           pst.setString(7,g_bprice);
           pst.setString(8,g_sprice);
           pst.setString(9,g_status);
           pst.setString(10,g_adate);
           pst.setString(11,g_origin);
           pst.setString(12,g_nature);
           try{
               InputStream inputStream = new FileInputStream(new File(g_image));
               pst.setBlob(13, inputStream);
               }
           catch(Exception exception){
                 JOptionPane.showMessageDialog(null,"Image Error");
                }
           pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Raw Gem Registered Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
public void update(){
          try{
           String sql = "UPDATE `raw` SET `g_no`=?,`rg_name`=?,`rg_type`=?,`rg_color`=?,`rg_weight`=?,`rg_bprice`=?,`rg_sprice`=?,`rg_status`=?,`rg_adate`=?,`rg_orgin`=?,`rg_nature`=?WHERE `rg_id`=?";
           pst = con.prepareStatement(sql);
           pst.setString(12,g_rrid);
           pst.setInt(1,g_id);
           pst.setString(2,g_name);
           pst.setString(3,g_type);
           pst.setString(4,g_color);
           pst.setString(5,g_weight);
           pst.setString(6,g_bprice);
           pst.setString(7,g_sprice);
           pst.setString(8,g_status);
           pst.setString(9,g_adate);
           pst.setString(10,g_origin);
           pst.setString(11,g_nature);
//           try{
//               InputStream inputStream = new FileInputStream(new File(g_image));
//               pst.setBlob(12, inputStream);
//               }
//           catch(Exception exception){
//                 JOptionPane.showMessageDialog(null,"Image Error");
//                }
           pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Raw Gem Data Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
        public void delete(){
        try{ String query = "DELETE FROM `raw` WHERE rg_id =?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,g_rrid);
           pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Raw Gem Data Delete Successfully");
         }catch(SQLException | HeadlessException ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
    
}
