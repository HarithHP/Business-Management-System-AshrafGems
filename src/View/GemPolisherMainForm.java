
package View;

import AppPackage.AnimationClass;
import Model.Job;
import Model.MapMain;
import Model.SqlConnection;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;


public class GemPolisherMainForm extends javax.swing.JFrame {
String status = "Paid";
    AnimationClass ac = new AnimationClass();
    public String code;
    String edate;
    String jstatus;
    String today;
    String jid;
    String rgid;
    int gid;
   Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;
    public GemPolisherMainForm(String code) {
        this.code = code;
        initComponents();
        getID();
        setEDate();
        fetch();
        txt_edate.setText(today);
        


     try {
            String QrCodeData= "www.ashrafgems.blogspot.com/";
            String filePath= "D:\\AshrafGems\\Ashraf Gems\\QR.png";
            String charset= "UTF-8";
            Map <EncodeHintType,ErrorCorrectionLevel> hintMap= new HashMap <EncodeHintType,ErrorCorrectionLevel> ();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix  matrix= new MultiFormatWriter().encode(
                new String (QrCodeData.getBytes(charset),charset),
                BarcodeFormat.QR_CODE,200,200,hintMap);

            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.')+1),new File(filePath));
            System.out.println("Qr code has been generated at the location "+filePath);

             
           jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
            ImageIcon icon = new ImageIcon("D:\\AshrafGems\\Ashraf Gems\\QR.png");
            JLabel label = new JLabel(icon);
            jPanel2.setLayout(new BorderLayout());
            jPanel2.add(label);
            jPanel2.setVisible(true);



        } catch (Exception e) {
         System.err.println(e);
        }

            
    }
    private void fetch(){
        
        try{String query = "SELECT * FROM job where j_eno =? and j_status=?";
           pst = con.prepareStatement(query);
           pst.setString(1,code);
           pst.setString(2,"Assigned");
           rs=pst.executeQuery();
           tbl_job.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
 
     private void getID(){

         try{
            lblUserName.setText(code);
           }catch(Exception e)
           {
            JOptionPane.showMessageDialog(null, e);
           }
    }



public void loadreport() {


        HashMap a = new HashMap();
        a.put("eid", code);
        a.put("date", today);
        a.put("status", status);
      
        panelLoad.removeAll();
        panelLoad.repaint();
        panelLoad.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\GempolisherExpences.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad.setLayout(new BorderLayout());
              panelLoad.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }

}

public void loadFilteredreport() {
       SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
       String fdate=date1.format(datechooser_from.getDate());
       SimpleDateFormat date2 = new SimpleDateFormat("yyyy-MM-dd");
       String tdate=date2.format(datechooser_to.getDate());


        HashMap a = new HashMap();
         a.put("eid", code);
        a.put("today", today);
        a.put("sdate", fdate);
        a.put("edate", tdate);
       a.put("status",status );
        panelLoad1.removeAll();
        panelLoad1.repaint();
        panelLoad1.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\FilterdGempolisherreport.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad1.setLayout(new BorderLayout());
              panelLoad1.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }
}

public void loadGraphreport() {


        HashMap a = new HashMap();
        a.put("eid", code);
        a.put("date", today);
        a.put("status", status);
      
        panelLoad.removeAll();
        panelLoad.repaint();
        panelLoad.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\GraphGempolisherSalary.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad.setLayout(new BorderLayout());
              panelLoad.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }

}
    private void fetchClick(){
       int row = tbl_job.getSelectedRow();
       String tc =tbl_job.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM job where j_eno =? and j_id=? "; 
           pst = con.prepareStatement(query);
           pst.setString(1,code);
           pst.setString(2,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                  jid=rs.getString("j_id");
                 rgid=rs.getString("j_rgno");
                 String owid=rs.getString("j_owno");
                 String jsdate=rs.getString("j_sdate");;
                getGemId();
                 txt_jid.setText(jid);
                 txt_rgid.setText(rgid);
                 txt_owid.setText(owid);
                 txt_sdate.setText(jsdate);
                 
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
private void getGemId(){
try{
           String query = "SELECT * FROM raw where rg_id=? "; 
           pst = con.prepareStatement(query);
           pst.setString(1,rgid);
           rs =pst.executeQuery();
           if(rs.next()){
                  gid=rs.getInt("g_no");                 
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
}
private void setEDate()
        {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        today = sdf.format(date);
        System.out.println(""+today);
        }
public void updateGemStatus(){
          try{
           String sql = "UPDATE gem SET g_status=? WHERE g_id =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,"Completed");
           pst.setInt(2,gid);
           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Gem Status Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
 public void updateRawGemStatus(){
          try{
           String sql = "UPDATE raw SET rg_status=? WHERE rg_id =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,"Completed");
           pst.setString(2,rgid);
           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Raw Gem Status Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        btnReports = new javax.swing.JPanel();
        lblBar3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        buttonHome = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblBar1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnJobs = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblBar2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnHelp = new javax.swing.JPanel();
        lblBar4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnLogOut = new javax.swing.JLabel();
        btnProfile = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        btnExit = new javax.swing.JLabel();
        btnMiniMize = new javax.swing.JLabel();
        lblUserName1 = new javax.swing.JLabel();
        form1 = new javax.swing.JPanel();
        jInternalFrameJobs = new javax.swing.JInternalFrame();
        jPanelFrame2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_job = new javax.swing.JTable();
        lbl_title = new javax.swing.JLabel();
        lbl_jid = new javax.swing.JLabel();
        lbl_rgid = new javax.swing.JLabel();
        lbl_owid = new javax.swing.JLabel();
        txt_owid = new javax.swing.JTextField();
        txt_rgid = new javax.swing.JTextField();
        txt_jid = new javax.swing.JTextField();
        lbl_sdate = new javax.swing.JLabel();
        lbl_edate = new javax.swing.JLabel();
        lbl_otot = new javax.swing.JLabel();
        comboStatus = new javax.swing.JComboBox<>();
        txt_edate = new javax.swing.JTextField();
        txt_sdate = new javax.swing.JTextField();
        btn_update = new View.MyButton();
        jInternalFrameReports = new javax.swing.JInternalFrame();
        panelLoad = new javax.swing.JPanel();
        btnGenerateReport = new View.MyButton();
        btnGenerateGraphReport = new View.MyButton();
        btnfilter = new View.MyButton();
        jInternalFrameCustomerHelp = new javax.swing.JInternalFrame();
        jLabel86 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        btnMap = new View.MyButton();
        jLabel75 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jInternalFrameEmployeeProfile = new javax.swing.JInternalFrame();
        jLabel46 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        btnClear2 = new View.MyButton();
        btnSave = new View.MyButton();
        jLabel57 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jInternalFrameReports1 = new javax.swing.JInternalFrame();
        panelLoad1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btnBack6 = new View.MyButton();
        btnGenerate = new View.MyButton();
        datechooser_to = new com.toedter.calendar.JDateChooser();
        datechooser_from = new com.toedter.calendar.JDateChooser();
        jInternalFrameHome = new javax.swing.JInternalFrame();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu.setBackground(new java.awt.Color(46, 43, 43));
        menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnReports.setBackground(new java.awt.Color(46, 43, 43));
        btnReports.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReports.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReportsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReportsMouseExited(evt);
            }
        });

        lblBar3.setBackground(new java.awt.Color(255, 51, 51));

        jLabel13.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/007.png"))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Reports");

        javax.swing.GroupLayout btnReportsLayout = new javax.swing.GroupLayout(btnReports);
        btnReports.setLayout(btnReportsLayout);
        btnReportsLayout.setHorizontalGroup(
            btnReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnReportsLayout.createSequentialGroup()
                .addComponent(lblBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        btnReportsLayout.setVerticalGroup(
            btnReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnReportsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menu.add(btnReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 170, 60));

        buttonHome.setBackground(new java.awt.Color(46, 43, 43));
        buttonHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonHomeMouseExited(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/home.png"))); // NOI18N

        lblBar1.setBackground(new java.awt.Color(255, 51, 51));
        lblBar1.setOpaque(true);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Home");

        javax.swing.GroupLayout buttonHomeLayout = new javax.swing.GroupLayout(buttonHome);
        buttonHome.setLayout(buttonHomeLayout);
        buttonHomeLayout.setHorizontalGroup(
            buttonHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonHomeLayout.createSequentialGroup()
                .addComponent(lblBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        buttonHomeLayout.setVerticalGroup(
            buttonHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(buttonHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 170, 60));

        btnJobs.setBackground(new java.awt.Color(46, 43, 43));
        btnJobs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnJobs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnJobsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnJobsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnJobsMouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-job-seeker-30.png"))); // NOI18N

        lblBar2.setBackground(new java.awt.Color(255, 51, 51));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Jobs");

        javax.swing.GroupLayout btnJobsLayout = new javax.swing.GroupLayout(btnJobs);
        btnJobs.setLayout(btnJobsLayout);
        btnJobsLayout.setHorizontalGroup(
            btnJobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnJobsLayout.createSequentialGroup()
                .addComponent(lblBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addContainerGap(55, Short.MAX_VALUE))
        );
        btnJobsLayout.setVerticalGroup(
            btnJobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnJobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnJobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(btnJobs, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 170, 60));

        btnHelp.setBackground(new java.awt.Color(46, 43, 43));
        btnHelp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHelpMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHelpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHelpMouseExited(evt);
            }
        });

        lblBar4.setBackground(new java.awt.Color(255, 51, 51));

        jLabel16.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/006.png"))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Help");

        javax.swing.GroupLayout btnHelpLayout = new javax.swing.GroupLayout(btnHelp);
        btnHelp.setLayout(btnHelpLayout);
        btnHelpLayout.setHorizontalGroup(
            btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHelpLayout.createSequentialGroup()
                .addComponent(lblBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        btnHelpLayout.setVerticalGroup(
            btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnHelpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menu.add(btnHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 170, -1));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bbrdbb.png"))); // NOI18N
        jLabel34.setText("jLabel34");
        menu.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(-170, 350, 340, 450));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/sub logo.png"))); // NOI18N
        menu.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, 90));

        bg.add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 610));

        jPanel1.setBackground(new java.awt.Color(46, 43, 43));

        btnLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/closing.png"))); // NOI18N
        btnLogOut.setToolTipText("Log Out");
        btnLogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogOutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogOutMouseEntered(evt);
            }
        });

        btnProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/profile.png"))); // NOI18N
        btnProfile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProfileMouseClicked(evt);
            }
        });

        lblUserName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName.setText("User Name");

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-close-window-48.png"))); // NOI18N
        btnExit.setToolTipText("Exit");
        btnExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
        });

        btnMiniMize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-minimize-window-48.png"))); // NOI18N
        btnMiniMize.setToolTipText("Minimize");
        btnMiniMize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMiniMize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMiniMizeMouseClicked(evt);
            }
        });

        lblUserName1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUserName1.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName1.setText("Employee ID :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(419, Short.MAX_VALUE)
                .addComponent(btnProfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUserName1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUserName)
                .addGap(31, 31, 31)
                .addComponent(btnLogOut)
                .addGap(18, 18, 18)
                .addComponent(btnMiniMize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit)
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMiniMize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(592, 592, 592))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProfile)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUserName1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 840, 50));

        form1.setBackground(new java.awt.Color(255, 255, 255));
        form1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jInternalFrameJobs.setBorder(null);
        jInternalFrameJobs.setVisible(false);
        jInternalFrameJobs.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelFrame2.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFrame2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_job.setAutoCreateRowSorter(true);
        tbl_job.setBackground(new java.awt.Color(204, 204, 204));
        tbl_job.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_job.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_jobMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_job);

        jPanelFrame2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 760, 290));

        lbl_title.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_title.setText("Available Jobs");
        jPanelFrame2.add(lbl_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, -1));

        lbl_jid.setBackground(new java.awt.Color(255, 255, 255));
        lbl_jid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_jid.setForeground(new java.awt.Color(0, 0, 0));
        lbl_jid.setText("Job ID");
        jPanelFrame2.add(lbl_jid, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, -1, -1));

        lbl_rgid.setBackground(new java.awt.Color(255, 255, 255));
        lbl_rgid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_rgid.setForeground(new java.awt.Color(0, 0, 0));
        lbl_rgid.setText("Raw Gem ID");
        jPanelFrame2.add(lbl_rgid, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, -1, -1));

        lbl_owid.setBackground(new java.awt.Color(255, 255, 255));
        lbl_owid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_owid.setForeground(new java.awt.Color(0, 0, 0));
        lbl_owid.setText("Assigned Owner ID");
        jPanelFrame2.add(lbl_owid, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, -1, -1));

        txt_owid.setEditable(false);
        txt_owid.setBackground(new java.awt.Color(255, 255, 255));
        txt_owid.setForeground(new java.awt.Color(0, 0, 0));
        txt_owid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanelFrame2.add(txt_owid, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 460, 180, 40));

        txt_rgid.setEditable(false);
        txt_rgid.setBackground(new java.awt.Color(255, 255, 255));
        txt_rgid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanelFrame2.add(txt_rgid, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 410, 180, 40));

        txt_jid.setEditable(false);
        txt_jid.setBackground(new java.awt.Color(255, 255, 255));
        txt_jid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanelFrame2.add(txt_jid, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 360, 180, 40));

        lbl_sdate.setBackground(new java.awt.Color(255, 255, 255));
        lbl_sdate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_sdate.setForeground(new java.awt.Color(0, 0, 0));
        lbl_sdate.setText("Job Start Date");
        jPanelFrame2.add(lbl_sdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 380, -1, -1));

        lbl_edate.setBackground(new java.awt.Color(255, 255, 255));
        lbl_edate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_edate.setForeground(new java.awt.Color(0, 0, 0));
        lbl_edate.setText("Job Completed Date");
        jPanelFrame2.add(lbl_edate, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 430, -1, -1));

        lbl_otot.setBackground(new java.awt.Color(255, 255, 255));
        lbl_otot.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_otot.setForeground(new java.awt.Color(0, 0, 0));
        lbl_otot.setText("Job Status");
        jPanelFrame2.add(lbl_otot, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 480, -1, -1));

        comboStatus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Completed" }));
        comboStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboStatusActionPerformed(evt);
            }
        });
        jPanelFrame2.add(comboStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 480, 180, -1));

        txt_edate.setEditable(false);
        txt_edate.setBackground(new java.awt.Color(255, 255, 255));
        txt_edate.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_edate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_edateActionPerformed(evt);
            }
        });
        jPanelFrame2.add(txt_edate, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 410, 180, 40));

        txt_sdate.setEditable(false);
        txt_sdate.setBackground(new java.awt.Color(255, 255, 255));
        txt_sdate.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanelFrame2.add(txt_sdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 360, 180, 40));

        btn_update.setForeground(new java.awt.Color(0, 0, 0));
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanelFrame2.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 523, 180, 30));

        jInternalFrameJobs.getContentPane().add(jPanelFrame2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 870, 580));

        form1.add(jInternalFrameJobs, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -30, 870, 590));

        jInternalFrameReports.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameReports.setVisible(false);
        jInternalFrameReports.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelLoad.setBackground(new java.awt.Color(255, 255, 255));
        panelLoad.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelLoad.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelLoadLayout = new javax.swing.GroupLayout(panelLoad);
        panelLoad.setLayout(panelLoadLayout);
        panelLoadLayout.setHorizontalGroup(
            panelLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelLoadLayout.setVerticalGroup(
            panelLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jInternalFrameReports.getContentPane().add(panelLoad, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 820, 500));

        btnGenerateReport.setForeground(new java.awt.Color(0, 0, 0));
        btnGenerateReport.setText("Generate Salary Report");
        btnGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateReportActionPerformed(evt);
            }
        });
        jInternalFrameReports.getContentPane().add(btnGenerateReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 190, 30));

        btnGenerateGraphReport.setForeground(new java.awt.Color(0, 0, 0));
        btnGenerateGraphReport.setText("Generate Graph Report");
        btnGenerateGraphReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateGraphReportActionPerformed(evt);
            }
        });
        jInternalFrameReports.getContentPane().add(btnGenerateGraphReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 190, 30));

        btnfilter.setForeground(new java.awt.Color(0, 0, 0));
        btnfilter.setText("Filter Report By Date");
        btnfilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfilterActionPerformed(evt);
            }
        });
        jInternalFrameReports.getContentPane().add(btnfilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 210, 30));

        form1.add(jInternalFrameReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -40, 880, 610));

        jInternalFrameCustomerHelp.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameCustomerHelp.setVisible(false);
        jInternalFrameCustomerHelp.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel86.setBackground(new java.awt.Color(255, 255, 255));
        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(0, 0, 0));
        jLabel86.setText("Explore and Learn More About Gem Varieties and Ashraf Gems");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, 30));

        jLabel77.setBackground(new java.awt.Color(255, 255, 255));
        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(0, 0, 0));
        jLabel77.setText("Scan the QR Code Using Your Mobile Phone");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, -1, 30));

        jLabel76.setBackground(new java.awt.Color(255, 255, 255));
        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(0, 0, 0));
        jLabel76.setText("Find us one Map");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, 30));

        btnMap.setForeground(new java.awt.Color(0, 0, 0));
        btnMap.setText("Click Here");
        btnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapActionPerformed(evt);
            }
        });
        jInternalFrameCustomerHelp.getContentPane().add(btnMap, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 100, 40));

        jLabel75.setBackground(new java.awt.Color(255, 255, 255));
        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(0, 0, 0));
        jLabel75.setText("Contact us :");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, -1));

        jLabel82.setBackground(new java.awt.Color(255, 255, 255));
        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(0, 0, 0));
        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-call-30.png"))); // NOI18N
        jInternalFrameCustomerHelp.getContentPane().add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, -1, 30));

        jLabel83.setBackground(new java.awt.Color(255, 255, 255));
        jLabel83.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(0, 0, 0));
        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-mail-30.png"))); // NOI18N
        jInternalFrameCustomerHelp.getContentPane().add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 280, 40, 20));

        jLabel74.setBackground(new java.awt.Color(255, 255, 255));
        jLabel74.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(0, 0, 0));
        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-address-30.png"))); // NOI18N
        jInternalFrameCustomerHelp.getContentPane().add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 40, 30));

        jLabel87.setBackground(new java.awt.Color(255, 255, 255));
        jLabel87.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(0, 0, 0));
        jLabel87.setText("NO 68/10 DHARGAROAD CHINAFORT BERUWALA");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, -1, 30));

        jLabel88.setBackground(new java.awt.Color(255, 255, 255));
        jLabel88.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(0, 0, 0));
        jLabel88.setText("ASHRAFGEMS@GMAIL.COM");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, -1, 30));

        jLabel85.setBackground(new java.awt.Color(255, 255, 255));
        jLabel85.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(0, 0, 0));
        jLabel85.setText("0773431901 /  0112874534");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, -1, 30));

        jLabel79.setBackground(new java.awt.Color(255, 255, 255));
        jLabel79.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(0, 0, 0));
        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-facebook-30.png"))); // NOI18N
        jLabel79.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameCustomerHelp.getContentPane().add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 320, -1, 30));

        jLabel80.setBackground(new java.awt.Color(255, 255, 255));
        jLabel80.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(0, 0, 0));
        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-instagram-30.png"))); // NOI18N
        jLabel80.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameCustomerHelp.getContentPane().add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 320, -1, 30));

        jLabel81.setBackground(new java.awt.Color(255, 255, 255));
        jLabel81.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(0, 0, 0));
        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-wechat-30.png"))); // NOI18N
        jLabel81.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameCustomerHelp.getContentPane().add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 320, -1, 30));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        jInternalFrameCustomerHelp.getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 220, 200));

        jLabel89.setBackground(new java.awt.Color(255, 255, 255));
        jLabel89.setForeground(new java.awt.Color(0, 0, 0));
        jLabel89.setText("Licence Dealer of National Gem and Jewellery Authority");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, 30));

        jLabel90.setBackground(new java.awt.Color(255, 255, 255));
        jLabel90.setForeground(new java.awt.Color(0, 0, 0));
        jLabel90.setText("Member of Chinafort Gem and Jewellery Trade Association (CGJTA)");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, -1, 30));

        jLabel91.setBackground(new java.awt.Color(255, 255, 255));
        jLabel91.setForeground(new java.awt.Color(0, 0, 0));
        jLabel91.setText("Registered Exporter of Sri Lanka Export Development Board");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, -1, 30));

        jLabel84.setBackground(new java.awt.Color(255, 255, 255));
        jLabel84.setForeground(new java.awt.Color(0, 0, 0));
        jLabel84.setText("Copyright © Ashraf Gems 2022");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 490, -1, 30));

        jLabel78.setBackground(new java.awt.Color(255, 255, 255));
        jLabel78.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(0, 0, 0));
        jLabel78.setText("---------------------------------------------------------------------------------------------------------");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, 30));

        form1.add(jInternalFrameCustomerHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameEmployeeProfile.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameEmployeeProfile.setVisible(false);
        jInternalFrameEmployeeProfile.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setBackground(new java.awt.Color(255, 255, 255));
        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel46.setText("Manage your profile");
        jInternalFrameEmployeeProfile.getContentPane().add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, -1, -1));

        jLabel55.setBackground(new java.awt.Color(46, 43, 43));
        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel55.setText("User ID");
        jInternalFrameEmployeeProfile.getContentPane().add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, -1, -1));

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(255, 255, 255));
        jTextField4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameEmployeeProfile.getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 96, 180, -1));

        jLabel50.setBackground(new java.awt.Color(46, 43, 43));
        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel50.setText("First Name");
        jInternalFrameEmployeeProfile.getContentPane().add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, -1, -1));

        jTextField5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameEmployeeProfile.getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 145, 180, -1));

        jLabel51.setBackground(new java.awt.Color(46, 43, 43));
        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel51.setText("Last Name");
        jInternalFrameEmployeeProfile.getContentPane().add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 140, -1, -1));

        jTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameEmployeeProfile.getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 160, -1));

        jLabel48.setBackground(new java.awt.Color(46, 43, 43));
        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel48.setText("Address");
        jInternalFrameEmployeeProfile.getContentPane().add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, -1, -1));

        jTextField6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameEmployeeProfile.getContentPane().add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 195, 490, -1));

        jLabel56.setBackground(new java.awt.Color(46, 43, 43));
        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel56.setText("Email");
        jInternalFrameEmployeeProfile.getContentPane().add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, -1, -1));

        jTextField7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameEmployeeProfile.getContentPane().add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 238, 490, -1));

        jLabel53.setBackground(new java.awt.Color(46, 43, 43));
        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel53.setText("Work type");
        jInternalFrameEmployeeProfile.getContentPane().add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 320, -1, -1));

        jTextField8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameEmployeeProfile.getContentPane().add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 330, 190, -1));

        jLabel47.setBackground(new java.awt.Color(46, 43, 43));
        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel47.setText("Phone Number");
        jInternalFrameEmployeeProfile.getContentPane().add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, -1, -1));

        jTextField9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameEmployeeProfile.getContentPane().add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 190, -1));

        jLabel54.setBackground(new java.awt.Color(46, 43, 43));
        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel54.setText("Joined Date");
        jInternalFrameEmployeeProfile.getContentPane().add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, -1, -1));

        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameEmployeeProfile.getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 370, 190, -1));

        btnClear2.setText("Clear");
        btnClear2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClear2MouseClicked(evt);
            }
        });
        btnClear2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear2ActionPerformed(evt);
            }
        });
        jInternalFrameEmployeeProfile.getContentPane().add(btnClear2, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 463, 130, 50));

        btnSave.setText("Save ");
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jInternalFrameEmployeeProfile.getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(535, 463, 140, 50));

        jLabel57.setBackground(new java.awt.Color(46, 43, 43));
        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel57.setText("NIC Number");
        jInternalFrameEmployeeProfile.getContentPane().add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, -1));

        jTextField10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameEmployeeProfile.getContentPane().add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 280, 190, -1));

        jLabel58.setBackground(new java.awt.Color(46, 43, 43));
        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel58.setText("Bank Account No");
        jInternalFrameEmployeeProfile.getContentPane().add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 280, -1, -1));

        jTextField11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameEmployeeProfile.getContentPane().add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 290, 190, -1));

        form1.add(jInternalFrameEmployeeProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(-22, -34, 870, 600));

        jInternalFrameReports1.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameReports1.setVisible(false);
        jInternalFrameReports1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelLoad1.setBackground(new java.awt.Color(255, 255, 255));
        panelLoad1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelLoad1.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelLoad1Layout = new javax.swing.GroupLayout(panelLoad1);
        panelLoad1.setLayout(panelLoad1Layout);
        panelLoad1Layout.setHorizontalGroup(
            panelLoad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelLoad1Layout.setVerticalGroup(
            panelLoad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jInternalFrameReports1.getContentPane().add(panelLoad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 820, 500));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("To :");
        jInternalFrameReports1.getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, -1, 30));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Date From :");
        jInternalFrameReports1.getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, 30));

        btnBack6.setForeground(new java.awt.Color(0, 0, 0));
        btnBack6.setText("Back");
        btnBack6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack6ActionPerformed(evt);
            }
        });
        jInternalFrameReports1.getContentPane().add(btnBack6, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, 80, 30));

        btnGenerate.setForeground(new java.awt.Color(0, 0, 0));
        btnGenerate.setText("Generate");
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });
        jInternalFrameReports1.getContentPane().add(btnGenerate, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 20, 80, 30));

        datechooser_to.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                datechooser_toAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        datechooser_to.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datechooser_toMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                datechooser_toMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                datechooser_toMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                datechooser_toMousePressed(evt);
            }
        });
        jInternalFrameReports1.getContentPane().add(datechooser_to, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 120, 30));

        datechooser_from.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                datechooser_fromAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        datechooser_from.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datechooser_fromMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                datechooser_fromMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                datechooser_fromMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                datechooser_fromMousePressed(evt);
            }
        });
        jInternalFrameReports1.getContentPane().add(datechooser_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 120, 30));

        form1.add(jInternalFrameReports1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -40, 880, 610));

        jInternalFrameHome.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameHome.setBorder(null);
        jInternalFrameHome.setVisible(true);
        jInternalFrameHome.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/11111111_1.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, -30, 380, 430));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/11111111.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 300, 480));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/jeweler-working-diamond-stone-female-goldsmith-master-repair-appraise-jewel-cartoon-professional-jeweller-examining-gem-183998415-removebg-preview.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, 380, 400));

        jLabel43.setBackground(new java.awt.Color(255, 255, 255));
        jLabel43.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 48)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setText("WELCOME !");
        jInternalFrameHome.getContentPane().add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, -1, -1));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 50, -1));

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 50, -1));

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 50, -1));

        jLabel41.setBackground(new java.awt.Color(255, 255, 255));
        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("Quick User Guide :");
        jInternalFrameHome.getContentPane().add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, 30));

        jLabel61.setBackground(new java.awt.Color(255, 255, 255));
        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(0, 0, 0));
        jLabel61.setText("View your jobs and Manage job status.");
        jInternalFrameHome.getContentPane().add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, -1, 30));

        jLabel63.setBackground(new java.awt.Color(255, 255, 255));
        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(0, 0, 0));
        jLabel63.setText("Generate salary report.");
        jInternalFrameHome.getContentPane().add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, -1, 30));

        jLabel65.setBackground(new java.awt.Color(255, 255, 255));
        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(0, 0, 0));
        jLabel65.setText("Find more about Ashraf Gems.");
        jInternalFrameHome.getContentPane().add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, -1, 30));

        form1.add(jInternalFrameHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -30, 870, 590));

        bg.add(form1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 840, 560));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogOutMouseClicked
        this.setVisible(false);
        SignInUp main = new SignInUp();
        main.setVisible(true);
    }//GEN-LAST:event_btnLogOutMouseClicked

    private void buttonHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonHomeMouseClicked
       bar(lblBar1);
       jInternalFrameHome.setVisible(true);
       jInternalFrameJobs.setVisible(false);
       
        jInternalFrameReports.setVisible(false);
        jInternalFrameCustomerHelp.setVisible(false);
        jInternalFrameEmployeeProfile.setVisible(false);
      
    }//GEN-LAST:event_buttonHomeMouseClicked

    private void buttonHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonHomeMouseEntered
        buttonHome.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_buttonHomeMouseEntered

    private void buttonHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonHomeMouseExited
        buttonHome.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_buttonHomeMouseExited

    private void btnJobsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnJobsMouseClicked
       bar(lblBar2);
       jInternalFrameHome.setVisible(false);
       jInternalFrameJobs.setVisible(true);
       
        jInternalFrameReports.setVisible(false);
        jInternalFrameCustomerHelp.setVisible(false);
        jInternalFrameEmployeeProfile.setVisible(false);
    }//GEN-LAST:event_btnJobsMouseClicked

    private void btnJobsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnJobsMouseEntered
        btnJobs.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnJobsMouseEntered

    private void btnJobsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnJobsMouseExited
         btnJobs.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnJobsMouseExited

    private void btnReportsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseClicked
        bar(lblBar3);
       jInternalFrameHome.setVisible(false);
       jInternalFrameJobs.setVisible(false);
       
        jInternalFrameReports.setVisible(true);
        jInternalFrameCustomerHelp.setVisible(false);
        jInternalFrameEmployeeProfile.setVisible(false);
    }//GEN-LAST:event_btnReportsMouseClicked

    private void btnReportsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseEntered
          btnReports.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnReportsMouseEntered

    private void btnReportsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseExited
        btnReports.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnReportsMouseExited

    private void btnHelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHelpMouseClicked
        bar(lblBar4);
        jInternalFrameHome.setVisible(false);
       jInternalFrameJobs.setVisible(false);
       
       jInternalFrameReports.setVisible(false);
       jInternalFrameCustomerHelp.setVisible(true);
       jInternalFrameEmployeeProfile.setVisible(false);
    }//GEN-LAST:event_btnHelpMouseClicked

    private void btnHelpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHelpMouseEntered
       btnHelp.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnHelpMouseEntered

    private void btnHelpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHelpMouseExited
        btnHelp.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnHelpMouseExited

    private void btnProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfileMouseClicked
       jInternalFrameHome.setVisible(false);
       jInternalFrameJobs.setVisible(false);
      
       jInternalFrameReports.setVisible(false);
       jInternalFrameCustomerHelp.setVisible(false);
       jInternalFrameEmployeeProfile.setVisible(true);
        lblBar1.setOpaque(false);
        lblBar2.setOpaque(false);
        lblBar3.setOpaque(false);
        lblBar4.setOpaque(false);
        
        menu.repaint();
    }//GEN-LAST:event_btnProfileMouseClicked

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btnExitMouseClicked

    private void btnMiniMizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMiniMizeMouseClicked
       this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMiniMizeMouseClicked

    private void btnLogOutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogOutMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLogOutMouseEntered

    private void btnClear2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClear2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClear2MouseClicked

    private void btnClear2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClear2ActionPerformed

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked

    }//GEN-LAST:event_btnSaveMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tbl_jobMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_jobMouseClicked
        fetchClick();
    }//GEN-LAST:event_tbl_jobMouseClicked

    private void comboStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboStatusActionPerformed
        jstatus = comboStatus.getSelectedItem().toString();
       

    }//GEN-LAST:event_comboStatusActionPerformed

    private void txt_edateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_edateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_edateActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed

        

        if(txt_jid.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Select Job First!");
        }else if(comboStatus.getSelectedItem().equals("Select")){
            JOptionPane.showMessageDialog(null, "Please Select Job Status!");
        }
        else{
         
        Job main = new Job();
        main.jobUpdate(jid,today , comboStatus.getSelectedItem().toString());
        main.updateGemStatus();
        updateRawGemStatus();
        updateGemStatus();
        fetch();
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btnGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateReportActionPerformed
        // TODO add your handling code here:
       loadreport();

    }//GEN-LAST:event_btnGenerateReportActionPerformed

    private void btnGenerateGraphReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateGraphReportActionPerformed
        // TODO add your handling code here:
      loadGraphreport();
    }//GEN-LAST:event_btnGenerateGraphReportActionPerformed

    private void btnMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapActionPerformed
        // TODO add your handling code here:
        MapMain map = new MapMain();
        map.setVisible(true);
    }//GEN-LAST:event_btnMapActionPerformed

    private void btnfilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfilterActionPerformed
        jInternalFrameReports.setVisible(false);
        jInternalFrameReports1.setVisible(true);
    }//GEN-LAST:event_btnfilterActionPerformed

    private void btnBack6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack6ActionPerformed
        // TODO add your handling code here:
        jInternalFrameReports1.setVisible(false);
        jInternalFrameReports.setVisible(true);
    }//GEN-LAST:event_btnBack6ActionPerformed

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed

        if(datechooser_from.getDate()==null){
            JOptionPane.showMessageDialog(this,"Select Start date");
        }else if(datechooser_to.getDate()==null){
            JOptionPane.showMessageDialog(this,"Select End date");
        }else{

            loadFilteredreport();
        }
    }//GEN-LAST:event_btnGenerateActionPerformed

    private void datechooser_toAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_datechooser_toAncestorAdded

    }//GEN-LAST:event_datechooser_toAncestorAdded

    private void datechooser_toMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_toMouseClicked

    }//GEN-LAST:event_datechooser_toMouseClicked

    private void datechooser_toMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_toMouseEntered

    }//GEN-LAST:event_datechooser_toMouseEntered

    private void datechooser_toMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_toMouseExited

    }//GEN-LAST:event_datechooser_toMouseExited

    private void datechooser_toMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_toMousePressed

    }//GEN-LAST:event_datechooser_toMousePressed

    private void datechooser_fromAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_datechooser_fromAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_fromAncestorAdded

    private void datechooser_fromMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_fromMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_fromMouseClicked

    private void datechooser_fromMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_fromMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_fromMouseEntered

    private void datechooser_fromMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_fromMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_fromMouseExited

    private void datechooser_fromMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_fromMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_fromMousePressed
    
    public void bar(JLabel lab){
        lblBar1.setOpaque(false);
        lblBar2.setOpaque(false);
        lblBar3.setOpaque(false);
        lblBar4.setOpaque(false);
        
        lab.setOpaque(true);
        menu.repaint();
    }
    
    
   
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private View.MyButton btnBack6;
    private View.MyButton btnClear2;
    private javax.swing.JLabel btnExit;
    private View.MyButton btnGenerate;
    private View.MyButton btnGenerateGraphReport;
    private View.MyButton btnGenerateReport;
    private javax.swing.JPanel btnHelp;
    private javax.swing.JPanel btnJobs;
    private javax.swing.JLabel btnLogOut;
    private View.MyButton btnMap;
    private javax.swing.JLabel btnMiniMize;
    private javax.swing.JLabel btnProfile;
    private javax.swing.JPanel btnReports;
    private View.MyButton btnSave;
    private View.MyButton btn_update;
    private View.MyButton btnfilter;
    private javax.swing.JPanel buttonHome;
    private javax.swing.JComboBox<String> comboStatus;
    private com.toedter.calendar.JDateChooser datechooser_from;
    private com.toedter.calendar.JDateChooser datechooser_to;
    private javax.swing.JPanel form1;
    private javax.swing.JInternalFrame jInternalFrameCustomerHelp;
    private javax.swing.JInternalFrame jInternalFrameEmployeeProfile;
    private javax.swing.JInternalFrame jInternalFrameHome;
    private javax.swing.JInternalFrame jInternalFrameJobs;
    private javax.swing.JInternalFrame jInternalFrameReports;
    private javax.swing.JInternalFrame jInternalFrameReports1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelFrame2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel lblBar1;
    private javax.swing.JLabel lblBar2;
    private javax.swing.JLabel lblBar3;
    private javax.swing.JLabel lblBar4;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserName1;
    private javax.swing.JLabel lbl_edate;
    private javax.swing.JLabel lbl_jid;
    private javax.swing.JLabel lbl_otot;
    private javax.swing.JLabel lbl_owid;
    private javax.swing.JLabel lbl_rgid;
    private javax.swing.JLabel lbl_sdate;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel panelLoad;
    private javax.swing.JPanel panelLoad1;
    private javax.swing.JTable tbl_job;
    private javax.swing.JTextField txt_edate;
    private javax.swing.JTextField txt_jid;
    private javax.swing.JTextField txt_owid;
    private javax.swing.JTextField txt_rgid;
    private javax.swing.JTextField txt_sdate;
    // End of variables declaration//GEN-END:variables
}
