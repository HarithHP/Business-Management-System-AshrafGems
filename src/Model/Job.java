/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Hadaragama
 */
public class Job {
    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

        String j_id;
        String j_edate;
        String j_status;

       String j_eno;
       String j_rgno;
       int j_owno;
       String j_statuss;
       String j_today;
       String j_endDate;

    public void ownerSetJob(String j_eno, String j_rgno, int j_owno, String j_statuss, String j_today, String j_endDate) {
        this.j_eno = j_eno;
        this.j_rgno = j_rgno;
        this.j_owno = j_owno;
        this.j_statuss = j_statuss;
        this.j_today = j_today;
        this.j_endDate = j_endDate;
    }
     
    public void jobUpdate(String j_id, String j_edate, String j_status) {
        this.j_id = j_id;
        this.j_edate = j_edate;
        this.j_status = j_status;

    }
    public void updateGemStatus(){
          try{
           String sql = "UPDATE job SET j_edate=?,j_status=? WHERE j_id =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,j_edate);
           pst.setString(2,j_status);
           pst.setString(3,j_id);
           pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Job Status Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
public void inputJob(){
        try{ String query = "INSERT INTO `job`(`j_eno`, `j_rgno`, `j_owno`, `j_status`, `j_sdate`, `j_edate`) VALUES (?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setString(1,j_eno);
           pst.setString(2,j_rgno);
           pst.setInt(3,j_owno);
           pst.setString(4,j_statuss);
           pst.setString(5,j_today);
           pst.setString(6,j_endDate);
           pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Job Added Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
    
    
}
