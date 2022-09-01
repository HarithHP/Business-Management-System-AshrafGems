
package View;

import AppPackage.AnimationClass;
import Model.Job;
import Model.SqlConnection;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;


public class GemPolisherMainForm1 extends javax.swing.JFrame {
    String edate;
    String jstatus;
    String today;

   Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;
    public String code;
    AnimationClass ac = new AnimationClass();
   

    public GemPolisherMainForm1(String code) {
        
        this.code = code;
        initComponents();
        getID();
        fetch();
        setEDate();
       txt_edate.setText(today);

    }

     private void getID(){

         try{
            lblUserName.setText(code);
           }catch(Exception e)
           {
            JOptionPane.showMessageDialog(null, e);
           }
    }
    private void setEDate()
        {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        today = sdf.format(date);
        System.out.println(""+today);
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
                 String jid=rs.getString("j_id");
                 String rgid=rs.getString("j_rgno");
                 String owid=rs.getString("j_owno");
                 String jsdate=rs.getString("j_sdate");;

                 txt_jid.setText(jid);
                 txt_rgid.setText(rgid);
                 txt_owid.setText(owid);
                 txt_sdate.setText(jsdate);
                 setEDate();
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }

    
   public void validate(){
   if(jstatus.equals("")){
            JOptionPane.showMessageDialog(null, "Please Select Job Status!");
        }else if(jstatus.equals("")){
            JOptionPane.showMessageDialog(null, "Please Select Job Status!");
        }
       else{
        Job main = new Job();
        main.jobUpdate(code, edate, jstatus);
        main.updateGemStatus();
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
        jInternalFrameHome = new javax.swing.JInternalFrame();
        jPanelFrame1 = new javax.swing.JPanel();
        jInternalFrameJobs = new javax.swing.JInternalFrame();
        jPanelFrame2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_job = new javax.swing.JTable();
        lbl_jid = new javax.swing.JLabel();
        txt_jid = new javax.swing.JTextField();
        txt_rgid = new javax.swing.JTextField();
        lbl_rgid = new javax.swing.JLabel();
        lbl_owid = new javax.swing.JLabel();
        txt_owid = new javax.swing.JTextField();
        lbl_otot = new javax.swing.JLabel();
        lbl_edate = new javax.swing.JLabel();
        lbl_sdate = new javax.swing.JLabel();
        txt_sdate = new javax.swing.JTextField();
        lbl_title = new javax.swing.JLabel();
        comboStatus = new javax.swing.JComboBox<>();
        txt_edate = new javax.swing.JTextField();
        btn_update = new View.MyButton();
        jInternalFrameReports = new javax.swing.JInternalFrame();
        jInternalFrameCustomerHelp = new javax.swing.JInternalFrame();
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
                .addContainerGap(412, Short.MAX_VALUE)
                .addComponent(btnProfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUserName1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addComponent(btnProfile)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUserName1))
                .addContainerGap())
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 840, 50));

        form1.setBackground(new java.awt.Color(255, 255, 255));
        form1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jInternalFrameHome.setBorder(null);
        jInternalFrameHome.setVisible(true);
        jInternalFrameHome.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelFrame1.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFrame1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jInternalFrameHome.getContentPane().add(jPanelFrame1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 840, 570));

        form1.add(jInternalFrameHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -30, 870, 590));

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

        lbl_jid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_jid.setText("Job ID");
        jPanelFrame2.add(lbl_jid, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, -1, -1));

        txt_jid.setEditable(false);
        txt_jid.setBackground(new java.awt.Color(255, 255, 255));
        txt_jid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanelFrame2.add(txt_jid, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 360, 180, 40));

        txt_rgid.setEditable(false);
        txt_rgid.setBackground(new java.awt.Color(255, 255, 255));
        txt_rgid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanelFrame2.add(txt_rgid, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 410, 180, 40));

        lbl_rgid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_rgid.setText("Raw Gem ID");
        jPanelFrame2.add(lbl_rgid, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, -1, -1));

        lbl_owid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_owid.setText("Assigned Owner ID");
        jPanelFrame2.add(lbl_owid, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, -1, -1));

        txt_owid.setEditable(false);
        txt_owid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanelFrame2.add(txt_owid, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 460, 180, 40));

        lbl_otot.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_otot.setText("Job Status");
        jPanelFrame2.add(lbl_otot, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 480, -1, -1));

        lbl_edate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_edate.setText("Job Completed Date");
        jPanelFrame2.add(lbl_edate, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 430, -1, -1));

        lbl_sdate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_sdate.setText("Job Start Date");
        jPanelFrame2.add(lbl_sdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 380, -1, -1));

        txt_sdate.setEditable(false);
        txt_sdate.setBackground(new java.awt.Color(255, 255, 255));
        txt_sdate.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanelFrame2.add(txt_sdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 360, 180, 40));

        lbl_title.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_title.setText("Available Jobs");
        jPanelFrame2.add(lbl_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, -1));

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
        form1.add(jInternalFrameReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -40, 880, 610));

        jInternalFrameCustomerHelp.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameCustomerHelp.setVisible(false);
        jInternalFrameCustomerHelp.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
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
   // fetchClick();        
    }//GEN-LAST:event_tbl_jobMouseClicked

    private void comboStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboStatusActionPerformed
       // jstatus = comboStatus.getSelectedItem().toString();
      

    }//GEN-LAST:event_comboStatusActionPerformed

    private void txt_edateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_edateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_edateActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_updateActionPerformed
    
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
    private View.MyButton btnClear2;
    private javax.swing.JLabel btnExit;
    private javax.swing.JPanel btnHelp;
    private javax.swing.JPanel btnJobs;
    private javax.swing.JLabel btnLogOut;
    private javax.swing.JLabel btnMiniMize;
    private javax.swing.JLabel btnProfile;
    private javax.swing.JPanel btnReports;
    private View.MyButton btnSave;
    private View.MyButton btn_update;
    private javax.swing.JPanel buttonHome;
    private javax.swing.JComboBox<String> comboStatus;
    private javax.swing.JPanel form1;
    private javax.swing.JInternalFrame jInternalFrameCustomerHelp;
    private javax.swing.JInternalFrame jInternalFrameEmployeeProfile;
    private javax.swing.JInternalFrame jInternalFrameHome;
    private javax.swing.JInternalFrame jInternalFrameJobs;
    private javax.swing.JInternalFrame jInternalFrameReports;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelFrame1;
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
    private javax.swing.JTable tbl_job;
    private javax.swing.JTextField txt_edate;
    private javax.swing.JTextField txt_jid;
    private javax.swing.JTextField txt_owid;
    private javax.swing.JTextField txt_rgid;
    private javax.swing.JTextField txt_sdate;
    // End of variables declaration//GEN-END:variables
}
