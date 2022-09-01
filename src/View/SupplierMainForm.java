
package View;

import AppPackage.AnimationClass;
import Model.Expense;
import Model.ExpenseAgent;
import Model.ExpenseSupplier;
import Model.FinishedGem;
import Model.Gem;
import Model.MapMain;
import Model.RawGem;
import Model.SqlConnection;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.BorderLayout;
import java.sql.*;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;





public class SupplierMainForm extends javax.swing.JFrame {
String status = "Paid";
    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;
    AnimationClass ac = new AnimationClass();
       public int g_id;
       public int g_fid;
       public int g_rid;
       public String today;
       public String g_name;
       public String g_type;
       public String g_color;
       public String g_weight;
       public String g_bprice;
       public String g_sprice;
       public String g_status;
       public String g_adate;
       public String g_variety;

       public String g_ffid;
       public String g_cid;
       public String g_shape;
       public String g_pieces;

       public String g_rrid;
       public String g_origin;
       public String g_nature;

      public int s_oid;
      public String s_ooid;
      public String code;

      String s_aid;
      int ex_id;
      int exs_id;
      int exa_id;
      String ex_no;
      String exs_no;
      String exa_no;
     
      
    
    public SupplierMainForm(String code){
        this.code = code;     
        initComponents();
        getID();
        fetchAllSupOrders();
        getSupplerData();
        calSupplierExpenseID();
 
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        today = sdf.format(date);
        System.out.println(""+today);

        lbl_gemNature.setVisible(false);
        lbl_gOrigin.setVisible(false);
        lbl_gemCID.setVisible(false);
        lbl_gemPieces.setVisible(false);
        lbl_gShape.setVisible(false);
        lbl_certiID1.setVisible(false);
        txt_gshape.setVisible(false);
         txt_gcetiID.setVisible(false);
         txt_gpieces.setVisible(false);
        txt_gnature.setVisible(false);
          txt_gOrigin.setVisible(false);


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
    private void getID(){

         try{
            lblUserName.setText(code);
           }catch(Exception e)
           {
            JOptionPane.showMessageDialog(null, e);
           }
    }
   
    public void calGemID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(g_id) from gem");
            
            if(rs.next()){
                
                g_id = rs.getInt(1);
                g_id =g_id+1;                      
            }else
            {
                g_id = 1;
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println(""+g_id);
       
    }
    public void calFGemID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(fg_id) from finish");
            
            if(rs.next()){
                
                g_fid = rs.getInt(1);
                g_fid =g_fid+1;
                g_ffid = String.valueOf("f"+g_fid);                      
            }else
            {
                g_fid = 1;
                g_ffid = String.valueOf("f"+g_fid);  
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println(""+g_ffid);
       
    }
    public void calRGemID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(rg_id) from raw");
            
            if(rs.next()){
                
                g_rid = rs.getInt(1);
                g_rid =g_rid+1;
                g_rrid = String.valueOf("r"+g_rid);                      
            }else
            {
                g_rid = 1;
                g_rrid = String.valueOf("r"+g_rid);  
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println(""+g_rrid);
       
    }
    public void calSupOrderID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(s_oid) from suporder");
            
            if(rs.next()){
                
                s_oid = rs.getInt(1);
                s_oid =s_oid+1;
                s_ooid = String.valueOf("so"+s_oid);                      
            }else
            {
                s_oid = 1;
                s_ooid = String.valueOf("so"+s_oid);  
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println(""+g_ffid);
       
    }
    public void calExpenseID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(ex_id) from expense");
            
            if(rs.next()){
                
                ex_id = rs.getInt(1);
                ex_id =ex_id+1; 
                ex_no = String.valueOf(""+ex_id); 
                   
            }else
            {
                ex_id =1; 
                ex_no = String.valueOf(""+ex_no);
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
       
    }
    public void calSupplierExpenseID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(exs_id) from expensesupplier");
            
            if(rs.next()){
                
                exs_id= rs.getInt(1);
                exs_id =exs_id+1; 
                exs_no = String.valueOf("SE"+exs_id); 
                   
            }else
            {
                exs_id =1; 
                exs_no = String.valueOf("SE"+exs_id);
            }
            System.out.println(exs_no);
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
       
    }
public void calExpenseAgentID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(aex_id) from expenseagent");
            
            if(rs.next()){
                
                exa_id= rs.getInt(1);
                exa_id =exa_id+1; 
                exa_no = String.valueOf("AE"+exa_id); 
                   
            }else
            {
                exa_id =1; 
                exa_no = String.valueOf("AE"+exa_id);
            }
            System.out.println(exa_no);
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
       
    }

    public void getDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        today = sdf.format(date);
    }

public void loadreport() {


        HashMap a = new HashMap();
        a.put("Supid", code);
        a.put("date", today);
        a.put("status",status );
      
        panelLoad.removeAll();
        panelLoad.repaint();
        panelLoad.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\SupplierTransaction.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad.setLayout(new BorderLayout());
              panelLoad.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }

}

public void loadGraphreport() {


        HashMap a = new HashMap();
        a.put("Supid", code);
        a.put("date", today);
        a.put("status",status );
      
        panelLoad.removeAll();
        panelLoad.repaint();
        panelLoad.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\GraphSupplierTransaction.jrxml");
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
        a.put("Supid", ""+code);
        a.put("today", today);
        a.put("sdate", fdate);
        a.put("edate", tdate);
       a.put("status",status );
        panelLoad1.removeAll();
        panelLoad1.repaint();
        panelLoad1.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\FilterSupplierTransactionReport.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad1.setLayout(new BorderLayout());
              panelLoad1.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }
}


private void getSupplerData(){
        Connection con = SqlConnection.getCon();
        PreparedStatement pst = null;
        ResultSet rs = null;
       try{
           String query = "SELECT * FROM `supplier` where s_id =?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,code);
           rs =pst.executeQuery();
           if(rs.next()){
                 s_aid=rs.getString("a_no");
                System.out.println(s_aid);
                 
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
public void setData(){

         g_name = txt_gname.getText();
         g_type = txt_gtype.getText();
         g_color= txt_gcolor.getText();
         g_weight= txt_gweight.getText();
         g_bprice= txt_gbprice.getText();
         g_sprice = "00.00";
         g_status = "Processing";
         g_adate = today;
         g_cid = txt_gcetiID.getText();
         g_shape = txt_gshape.getText();
         g_pieces = txt_gpieces.getText();
         g_origin = txt_gOrigin.getText();
         g_nature = txt_gnature.getText();
          
         
}
public void validateFinishGem(){
       if(g_name.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Name");
        }
        else if(g_name.length()<3 || g_name.length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Name");
        }else if(g_type.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Type");
        }else if(g_type.length()<3 || g_type.length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Type");
        }else if(g_color.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem color");
        }else if(g_color.length()<3 || g_color.length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Color");
        }else if(g_weight.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Weight");
        }else if(g_weight.equals("0") || g_weight.length()>8){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Weight");
        }else if(g_bprice.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Estimated Price");
        }
        else if(g_bprice.equals("0") || g_bprice.length()<2|| g_bprice.length()>10){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Estimated Price");
        }
        else if(g_cid.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Finished Gem Certificate ID!");
        }else if(g_cid.length()<8 || g_cid.length()>15){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem License Number");
        }
        else if(g_shape.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Gem Shape!");
        }else if(g_shape.length()<3 || g_shape.length()>15){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Shape!");
        }
        else if(g_pieces.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter No of Pieces of Gem!");
        }else if(g_pieces.length()>2 ||g_pieces.equals("0")){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Shape!");
        }
        else if(lbl_gemimage.getIcon()==null){
            JOptionPane.showMessageDialog(this,"Please Upload your gem Image!");
        }
        else{
            Gem gem = new Gem();
            gem.setInput(g_id, g_name, g_type, g_color, g_weight, g_bprice, today,imagePathStr,s_ooid,code);
            gem.input();

            FinishedGem fgem = new FinishedGem();
            fgem.setInput(g_id, g_name, g_type, g_color, g_weight, g_bprice, today,imagePathStr,g_ffid,g_shape,g_cid,g_pieces);
            fgem.input();

           Expense mainc = new Expense(ex_no,"0",g_bprice,"Not Selected" ,today,"Order Processing","SupplierExpense");
           mainc.input();
           ExpenseSupplier mains = new ExpenseSupplier(ex_no,"0",g_bprice, "Not Selected", today, "Order Processing", exs_no, s_aid, code, s_ooid);
           mains.input();
           if (!s_aid.equals("0")) {
           calExpenseID();
           calExpenseAgentID();
           Expense maincc = new Expense(ex_no,"0","00.00","Not Selected" ,today,"Order Processing","AgentExpense");
           maincc.input();
           
           ExpenseAgent maine = new ExpenseAgent();
           maine.ExpenseAgent(ex_no, "0", "00.00", "Not Selected", today, "Order Processing", exa_no, s_aid, "0",s_ooid, g_bprice,"0.0");
           maine.input();
           }
           
        jInternalFrameSellGems.setVisible(false);
        jInternalFrameInvoice.setVisible(true);
        loadInvoice();

            clear();
        }
}
public void validateRawGem(){
       if(g_name.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Name");
        }
        else if(g_name.length()<3 || g_name.length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Name");
        }else if(g_type.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Type");
        }else if(g_type.length()<3 || g_type.length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Type");
        }else if(g_color.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem color");
        }else if(g_color.length()<3 || g_color.length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Color");
        }else if(g_weight.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Weight");
        }else if(g_weight.equals("0")|| g_weight.length()>8){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Weight");
        }else if(g_bprice.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Estimated Price");
        }
        else if(g_bprice.equals("0")||g_bprice.length()<2|| g_bprice.length()>10){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Estimated Price");
        }
        else if(g_origin.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Origin Of Gem");
        }else if(g_origin.length()<3 || g_origin.length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Origin");
        }
        else if(g_nature.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Gem Nature");
        }else if(g_nature.length()<3 || g_nature.length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Nature");
        }
        else if(lbl_gemimage.getIcon()==null){
            JOptionPane.showMessageDialog(this,"Please Upload your gem Image!");
        }
        else{
            
            Gem gem = new Gem();
            gem.setInput(g_id, g_name, g_type, g_color, g_weight, g_bprice, today,imagePathStr,s_ooid,code);
            gem.input();

            RawGem rgem = new RawGem();
            rgem.setInput(g_id, g_name, g_type, g_color, g_weight, g_bprice, today,imagePathStr,g_rrid,g_origin,g_nature);
            rgem.input();

           Expense mainc = new Expense(ex_no,"0",g_bprice,"Not Selected" ,today,"Order Processing","SupplierExpense");
           mainc.input();
           ExpenseSupplier mains = new ExpenseSupplier(ex_no,"0",g_bprice, "Not Selected", today, "Order Processing", exs_no, s_aid, code, s_ooid);
           mains.input();
           if (!s_aid.equals("0")) {
           calExpenseID();
           calExpenseAgentID();
           Expense maincc = new Expense(ex_no,"0","00.00","Not Selected" ,today,"Order Processing","AgentExpense");
           maincc.input();
           ExpenseAgent maine = new ExpenseAgent();
           maine.ExpenseAgent(ex_no, "0", "00.00", "Not Selected", today, "Order Processing", exa_no, s_aid, "0",s_ooid, g_bprice,"0.0");
           maine.input();
           }

        jInternalFrameSellGems.setVisible(false);
        jInternalFrameInvoice.setVisible(true);
        loadInvoice();


           clear();
        }
}
private void clear(){
       txt_gOrigin.setText("");
       txt_gbprice.setText("");
       txt_gcetiID.setText("");
       txt_gcolor.setText("");
       comboVariety.setSelectedIndex(0);
       txt_gname.setText("");
       txt_gnature.setText("");
       txt_gpieces.setText("");
       txt_gshape.setText("");
       txt_gtype.setText("");
       txt_gweight.setText("");
     lbl_gemimage.setIcon(null);
  
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        btnTransactions = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblBar3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        buttonHome = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblBar1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnSellGems = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblBar2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnReports = new javax.swing.JPanel();
        lblBar4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnHelp = new javax.swing.JPanel();
        lblBar5 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnLogOut = new javax.swing.JLabel();
        btnProfile = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        btnExit = new javax.swing.JLabel();
        btnMiniMize = new javax.swing.JLabel();
        lblUserName1 = new javax.swing.JLabel();
        form1 = new javax.swing.JPanel();
        jInternalFrameSellGems = new javax.swing.JInternalFrame();
        jPanelFrame2 = new javax.swing.JPanel();
        lbl_gemname = new javax.swing.JLabel();
        lbl_gweight = new javax.swing.JLabel();
        lbl_sellprice = new javax.swing.JLabel();
        txt_gOrigin = new javax.swing.JTextField();
        lbl_gOrigin = new javax.swing.JLabel();
        txt_gcetiID = new javax.swing.JTextField();
        lbl_gemtype = new javax.swing.JLabel();
        txt_gpieces = new javax.swing.JTextField();
        lbl_gemPieces = new javax.swing.JLabel();
        btn_upload = new View.MyButton();
        btnClear = new View.MyButton();
        lbl_gemcolor = new javax.swing.JLabel();
        lbl_gemimage = new javax.swing.JLabel();
        lbl_gemCID = new javax.swing.JLabel();
        lbl_gShape = new javax.swing.JLabel();
        lbl_gemVariety = new javax.swing.JLabel();
        txt_gname = new javax.swing.JTextField();
        txt_gcolor = new javax.swing.JTextField();
        txt_gweight = new javax.swing.JTextField();
        txt_gbprice = new javax.swing.JTextField();
        txt_gtype = new javax.swing.JTextField();
        btn_send = new View.MyButton();
        lbl_gimageAddress1 = new javax.swing.JLabel();
        lbl_gemNature = new javax.swing.JLabel();
        txt_gnature = new javax.swing.JTextField();
        comboVariety = new javax.swing.JComboBox<>();
        txt_gshape = new javax.swing.JTextField();
        lbl_weight = new javax.swing.JLabel();
        lbl_certiID1 = new javax.swing.JLabel();
        jInternalFrameTransaction = new javax.swing.JInternalFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_suporder = new javax.swing.JTable();
        btn_processing = new View.MyButton();
        btn_regect = new View.MyButton();
        btn_approved1 = new View.MyButton();
        btn_sold = new View.MyButton();
        jInternalFrameReports = new javax.swing.JInternalFrame();
        panelLoad = new javax.swing.JPanel();
        btnGenerateGraph = new View.MyButton();
        btnGenerateReport = new View.MyButton();
        btnfilter = new View.MyButton();
        jInternalFrameHelp = new javax.swing.JInternalFrame();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jInternalFrameSupplierProfile = new javax.swing.JInternalFrame();
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
        jInternalFrameInvoice = new javax.swing.JInternalFrame();
        btnDone = new View.MyButton();
        panelLoad2 = new javax.swing.JPanel();
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
        jLabel49 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu.setBackground(new java.awt.Color(46, 43, 43));
        menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTransactions.setBackground(new java.awt.Color(46, 43, 43));
        btnTransactions.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTransactions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTransactionsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTransactionsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTransactionsMouseExited(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-transaction-30.png"))); // NOI18N

        lblBar3.setBackground(new java.awt.Color(255, 51, 51));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("My Orders");

        javax.swing.GroupLayout btnTransactionsLayout = new javax.swing.GroupLayout(btnTransactions);
        btnTransactions.setLayout(btnTransactionsLayout);
        btnTransactionsLayout.setHorizontalGroup(
            btnTransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnTransactionsLayout.createSequentialGroup()
                .addComponent(lblBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnTransactionsLayout.setVerticalGroup(
            btnTransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnTransactionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnTransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menu.add(btnTransactions, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 170, 60));

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
                .addComponent(jLabel4)
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

        btnSellGems.setBackground(new java.awt.Color(46, 43, 43));
        btnSellGems.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSellGems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSellGemsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSellGemsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSellGemsMouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-jewel-30 (1).png"))); // NOI18N

        lblBar2.setBackground(new java.awt.Color(255, 51, 51));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Sell Gems");

        javax.swing.GroupLayout btnSellGemsLayout = new javax.swing.GroupLayout(btnSellGems);
        btnSellGems.setLayout(btnSellGemsLayout);
        btnSellGemsLayout.setHorizontalGroup(
            btnSellGemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSellGemsLayout.createSequentialGroup()
                .addComponent(lblBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnSellGemsLayout.setVerticalGroup(
            btnSellGemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSellGemsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnSellGemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(btnSellGems, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 170, 60));

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

        lblBar4.setBackground(new java.awt.Color(255, 51, 51));

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
                .addComponent(lblBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        btnReportsLayout.setVerticalGroup(
            btnReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar4, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addGroup(btnReportsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        menu.add(btnReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 170, -1));

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

        lblBar5.setBackground(new java.awt.Color(255, 51, 51));

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
                .addComponent(lblBar5, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        btnHelpLayout.setVerticalGroup(
            btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnHelpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menu.add(btnHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 170, -1));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bbrdbb.png"))); // NOI18N
        jLabel34.setText("jLabel34");
        menu.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(-170, 440, 340, 360));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/sub logo.png"))); // NOI18N
        menu.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, 90));

        bg.add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 610));

        jPanel1.setBackground(new java.awt.Color(46, 43, 43));

        btnLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/closing.png"))); // NOI18N
        btnLogOut.setToolTipText("Log Out");
        btnLogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogOutMouseClicked(evt);
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
        lblUserName1.setText("Supplier ID  :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(403, Short.MAX_VALUE)
                .addComponent(btnProfile)
                .addGap(18, 18, 18)
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMiniMize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUserName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(591, 591, 591))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblUserName1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProfile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 840, 50));

        form1.setBackground(new java.awt.Color(255, 255, 255));
        form1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jInternalFrameSellGems.setBorder(null);
        jInternalFrameSellGems.setVisible(false);
        jInternalFrameSellGems.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelFrame2.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFrame2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_gemname.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gemname.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gemname.setText("Gem Name        :");
        jPanelFrame2.add(lbl_gemname, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, -1, 30));

        lbl_gweight.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gweight.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gweight.setText("Gem Weight      :");
        jPanelFrame2.add(lbl_gweight, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, -1, -1));

        lbl_sellprice.setBackground(new java.awt.Color(255, 255, 255));
        lbl_sellprice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_sellprice.setText("Expecting price :");
        jPanelFrame2.add(lbl_sellprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, -1, -1));

        txt_gOrigin.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gOrigin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gOriginActionPerformed(evt);
            }
        });
        txt_gOrigin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_gOriginKeyPressed(evt);
            }
        });
        jPanelFrame2.add(txt_gOrigin, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, 160, 40));

        lbl_gOrigin.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gOrigin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gOrigin.setText("Gem Origin          :");
        jPanelFrame2.add(lbl_gOrigin, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, -1, 50));

        txt_gcetiID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gcetiID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gcetiIDActionPerformed(evt);
            }
        });
        txt_gcetiID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_gcetiIDKeyPressed(evt);
            }
        });
        jPanelFrame2.add(txt_gcetiID, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, 160, 40));

        lbl_gemtype.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gemtype.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gemtype.setText("Gem Type          :");
        jPanelFrame2.add(lbl_gemtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, -1, -1));

        txt_gpieces.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gpieces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gpiecesActionPerformed(evt);
            }
        });
        txt_gpieces.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_gpiecesKeyPressed(evt);
            }
        });
        jPanelFrame2.add(txt_gpieces, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 110, 160, 40));

        lbl_gemPieces.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gemPieces.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gemPieces.setText("Gem pieces         :");
        jPanelFrame2.add(lbl_gemPieces, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, -1, -1));

        btn_upload.setText("Upload");
        btn_upload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_uploadMouseClicked(evt);
            }
        });
        btn_upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_uploadActionPerformed(evt);
            }
        });
        jPanelFrame2.add(btn_upload, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 310, 80, 30));

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanelFrame2.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 500, 120, 40));

        lbl_gemcolor.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gemcolor.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gemcolor.setText("Gem Color          :");
        jPanelFrame2.add(lbl_gemcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, -1));

        lbl_gemimage.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gemimage.setForeground(new java.awt.Color(204, 204, 255));
        lbl_gemimage.setText("Gem Image");
        jPanelFrame2.add(lbl_gemimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 260, 150, 120));

        lbl_gemCID.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gemCID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gemCID.setText("Gem certifiate ID:");
        jPanelFrame2.add(lbl_gemCID, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, -1, -1));

        lbl_gShape.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gShape.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gShape.setText("Gem shape          :");
        jPanelFrame2.add(lbl_gShape, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 170, -1, 30));

        lbl_gemVariety.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gemVariety.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gemVariety.setText("Gem Variety      :");
        jPanelFrame2.add(lbl_gemVariety, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, -1, -1));

        txt_gname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gnameActionPerformed(evt);
            }
        });
        txt_gname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_gnameKeyPressed(evt);
            }
        });
        jPanelFrame2.add(txt_gname, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 160, 40));

        txt_gcolor.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gcolor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gcolorActionPerformed(evt);
            }
        });
        txt_gcolor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_gcolorKeyPressed(evt);
            }
        });
        jPanelFrame2.add(txt_gcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 210, 160, 40));

        txt_gweight.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gweight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gweightActionPerformed(evt);
            }
        });
        txt_gweight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_gweightKeyPressed(evt);
            }
        });
        jPanelFrame2.add(txt_gweight, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 260, 160, 40));

        txt_gbprice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gbprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gbpriceActionPerformed(evt);
            }
        });
        txt_gbprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_gbpriceKeyPressed(evt);
            }
        });
        jPanelFrame2.add(txt_gbprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 310, 160, 40));

        txt_gtype.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gtypeActionPerformed(evt);
            }
        });
        txt_gtype.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_gtypeKeyPressed(evt);
            }
        });
        jPanelFrame2.add(txt_gtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 160, 40));

        btn_send.setText("Send Request");
        btn_send.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_sendMouseClicked(evt);
            }
        });
        btn_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendActionPerformed(evt);
            }
        });
        jPanelFrame2.add(btn_send, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 500, 120, 40));

        lbl_gimageAddress1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gimageAddress1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gimageAddress1.setText("Gem Image       :");
        jPanelFrame2.add(lbl_gimageAddress1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 270, -1, -1));

        lbl_gemNature.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gemNature.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gemNature.setText("Gem Nature       :");
        jPanelFrame2.add(lbl_gemNature, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, -1, 30));

        txt_gnature.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gnature.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gnatureActionPerformed(evt);
            }
        });
        txt_gnature.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_gnatureKeyPressed(evt);
            }
        });
        jPanelFrame2.add(txt_gnature, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 110, 160, 40));

        comboVariety.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboVariety.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Finished", "Raw" }));
        comboVariety.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboVarietyActionPerformed(evt);
            }
        });
        jPanelFrame2.add(comboVariety, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 160, 40));

        txt_gshape.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gshape.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gshapeActionPerformed(evt);
            }
        });
        txt_gshape.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_gshapeKeyPressed(evt);
            }
        });
        jPanelFrame2.add(txt_gshape, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 150, 160, 40));

        lbl_weight.setText("Carats");
        jPanelFrame2.add(lbl_weight, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 40, 20));

        lbl_certiID1.setText("Gem Certificate ID");
        jPanelFrame2.add(lbl_certiID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, -1, -1));

        jInternalFrameSellGems.getContentPane().add(jPanelFrame2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 580));

        form1.add(jInternalFrameSellGems, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -30, 870, 590));

        jInternalFrameTransaction.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameTransaction.setVerifyInputWhenFocusTarget(false);
        jInternalFrameTransaction.setVisible(false);
        jInternalFrameTransaction.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_suporder.setAutoCreateRowSorter(true);
        tbl_suporder.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_suporder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_suporderMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_suporder);

        jInternalFrameTransaction.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 760, 270));

        btn_processing.setText("Processing Orders");
        btn_processing.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_processingMouseClicked(evt);
            }
        });
        btn_processing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_processingActionPerformed(evt);
            }
        });
        jInternalFrameTransaction.getContentPane().add(btn_processing, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, 150, 50));

        btn_regect.setText("Rejected Orders");
        btn_regect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_regectMouseClicked(evt);
            }
        });
        btn_regect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regectActionPerformed(evt);
            }
        });
        jInternalFrameTransaction.getContentPane().add(btn_regect, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 440, 150, 50));

        btn_approved1.setText("Approved Orders");
        btn_approved1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_approved1MouseClicked(evt);
            }
        });
        btn_approved1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_approved1ActionPerformed(evt);
            }
        });
        jInternalFrameTransaction.getContentPane().add(btn_approved1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 440, 150, 50));

        btn_sold.setText("Finished Orders");
        btn_sold.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_soldMouseClicked(evt);
            }
        });
        btn_sold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_soldActionPerformed(evt);
            }
        });
        jInternalFrameTransaction.getContentPane().add(btn_sold, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 440, 150, 50));

        form1.add(jInternalFrameTransaction, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 880, 600));

        jInternalFrameReports.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameReports.setVisible(false);
        jInternalFrameReports.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelLoad.setBackground(new java.awt.Color(255, 255, 255));
        panelLoad.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panelLoadLayout = new javax.swing.GroupLayout(panelLoad);
        panelLoad.setLayout(panelLoadLayout);
        panelLoadLayout.setHorizontalGroup(
            panelLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 824, Short.MAX_VALUE)
        );
        panelLoadLayout.setVerticalGroup(
            panelLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
        );

        jInternalFrameReports.getContentPane().add(panelLoad, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 830, 500));

        btnGenerateGraph.setText("Generate Graph Report");
        btnGenerateGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateGraphActionPerformed(evt);
            }
        });
        jInternalFrameReports.getContentPane().add(btnGenerateGraph, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 230, 30));

        btnGenerateReport.setText("Generate Order Income Report");
        btnGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateReportActionPerformed(evt);
            }
        });
        jInternalFrameReports.getContentPane().add(btnGenerateReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 200, 30));

        btnfilter.setText("Filter Report By Date");
        btnfilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfilterActionPerformed(evt);
            }
        });
        jInternalFrameReports.getContentPane().add(btnfilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 210, 30));

        form1.add(jInternalFrameReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameHelp.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameHelp.setVisible(false);
        jInternalFrameHelp.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel86.setBackground(new java.awt.Color(255, 255, 255));
        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel86.setText("Explore and Learn More About Gem Varieties and Ashraf Gems");
        jInternalFrameHelp.getContentPane().add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, 30));

        jLabel77.setBackground(new java.awt.Color(255, 255, 255));
        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel77.setText("Scan the QR Code Using Your Mobile Phone");
        jInternalFrameHelp.getContentPane().add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, -1, 30));

        jLabel76.setBackground(new java.awt.Color(255, 255, 255));
        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel76.setText("Find us one Map");
        jInternalFrameHelp.getContentPane().add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, 30));

        btnMap.setText("Click Here");
        btnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapActionPerformed(evt);
            }
        });
        jInternalFrameHelp.getContentPane().add(btnMap, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 100, 40));

        jLabel75.setBackground(new java.awt.Color(255, 255, 255));
        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel75.setText("Contact us :");
        jInternalFrameHelp.getContentPane().add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, -1));

        jLabel82.setBackground(new java.awt.Color(255, 255, 255));
        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-call-30.png"))); // NOI18N
        jInternalFrameHelp.getContentPane().add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, -1, 30));

        jLabel83.setBackground(new java.awt.Color(255, 255, 255));
        jLabel83.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-mail-30.png"))); // NOI18N
        jInternalFrameHelp.getContentPane().add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 280, 40, 20));

        jLabel74.setBackground(new java.awt.Color(255, 255, 255));
        jLabel74.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-address-30.png"))); // NOI18N
        jInternalFrameHelp.getContentPane().add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 40, 30));

        jLabel87.setBackground(new java.awt.Color(255, 255, 255));
        jLabel87.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel87.setText("NO 68/10 DHARGAROAD CHINAFORT BERUWALA");
        jInternalFrameHelp.getContentPane().add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, -1, 30));

        jLabel88.setBackground(new java.awt.Color(255, 255, 255));
        jLabel88.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel88.setText("ASHRAFGEMS@GMAIL.COM");
        jInternalFrameHelp.getContentPane().add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, -1, 30));

        jLabel85.setBackground(new java.awt.Color(255, 255, 255));
        jLabel85.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel85.setText("0773431901 /  0112874534");
        jInternalFrameHelp.getContentPane().add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, -1, 30));

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

        jInternalFrameHelp.getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 220, 200));

        jLabel79.setBackground(new java.awt.Color(255, 255, 255));
        jLabel79.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-facebook-30.png"))); // NOI18N
        jLabel79.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameHelp.getContentPane().add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 320, -1, 30));

        jLabel80.setBackground(new java.awt.Color(255, 255, 255));
        jLabel80.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-instagram-30.png"))); // NOI18N
        jLabel80.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameHelp.getContentPane().add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 320, -1, 30));

        jLabel81.setBackground(new java.awt.Color(255, 255, 255));
        jLabel81.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-wechat-30.png"))); // NOI18N
        jLabel81.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameHelp.getContentPane().add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 320, -1, 30));

        jLabel78.setBackground(new java.awt.Color(255, 255, 255));
        jLabel78.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel78.setText("---------------------------------------------------------------------------------------------------------");
        jInternalFrameHelp.getContentPane().add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, 30));

        jLabel89.setBackground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("Licence Dealer of National Gem and Jewellery Authority");
        jInternalFrameHelp.getContentPane().add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, 30));

        jLabel90.setBackground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("Member of Chinafort Gem and Jewellery Trade Association (CGJTA)");
        jInternalFrameHelp.getContentPane().add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, -1, 30));

        jLabel91.setBackground(new java.awt.Color(255, 255, 255));
        jLabel91.setText("Registered Exporter of Sri Lanka Export Development Board");
        jInternalFrameHelp.getContentPane().add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, -1, 30));

        jLabel84.setBackground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("Copyright © Ashraf Gems 2022");
        jInternalFrameHelp.getContentPane().add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 490, -1, 30));

        form1.add(jInternalFrameHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameSupplierProfile.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameSupplierProfile.setVisible(false);
        jInternalFrameSupplierProfile.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setBackground(new java.awt.Color(255, 255, 255));
        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel46.setText("Manage your profile");
        jInternalFrameSupplierProfile.getContentPane().add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, -1, -1));

        jLabel55.setBackground(new java.awt.Color(46, 43, 43));
        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel55.setText("User ID");
        jInternalFrameSupplierProfile.getContentPane().add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, -1, -1));

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(255, 255, 255));
        jTextField4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameSupplierProfile.getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 96, 180, -1));

        jLabel50.setBackground(new java.awt.Color(46, 43, 43));
        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel50.setText("First Name");
        jInternalFrameSupplierProfile.getContentPane().add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, -1, -1));

        jTextField5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameSupplierProfile.getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 145, 180, -1));

        jLabel51.setBackground(new java.awt.Color(46, 43, 43));
        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel51.setText("Last Name");
        jInternalFrameSupplierProfile.getContentPane().add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 140, -1, -1));

        jTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameSupplierProfile.getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 160, -1));

        jLabel48.setBackground(new java.awt.Color(46, 43, 43));
        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel48.setText("Address");
        jInternalFrameSupplierProfile.getContentPane().add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, -1, -1));

        jTextField6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameSupplierProfile.getContentPane().add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 195, 490, -1));

        jLabel56.setBackground(new java.awt.Color(46, 43, 43));
        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel56.setText("Email");
        jInternalFrameSupplierProfile.getContentPane().add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, -1, -1));

        jTextField7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameSupplierProfile.getContentPane().add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 238, 490, -1));

        jLabel53.setBackground(new java.awt.Color(46, 43, 43));
        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel53.setText("Bank Account No");
        jInternalFrameSupplierProfile.getContentPane().add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 320, -1, -1));

        jTextField8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameSupplierProfile.getContentPane().add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 330, 190, -1));

        jLabel47.setBackground(new java.awt.Color(46, 43, 43));
        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel47.setText("Phone Number");
        jInternalFrameSupplierProfile.getContentPane().add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, -1, -1));

        jTextField9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameSupplierProfile.getContentPane().add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, 190, -1));

        jLabel54.setBackground(new java.awt.Color(46, 43, 43));
        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel54.setText("Joined Date");
        jInternalFrameSupplierProfile.getContentPane().add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, -1, -1));

        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameSupplierProfile.getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 360, 190, -1));

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
        jInternalFrameSupplierProfile.getContentPane().add(btnClear2, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 463, 130, 50));

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
        jInternalFrameSupplierProfile.getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(535, 463, 140, 50));

        jLabel57.setBackground(new java.awt.Color(46, 43, 43));
        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel57.setText("NIC Number");
        jInternalFrameSupplierProfile.getContentPane().add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, -1, -1));

        jTextField10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameSupplierProfile.getContentPane().add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, 190, -1));

        jLabel58.setBackground(new java.awt.Color(46, 43, 43));
        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel58.setText("gemSellLicense No");
        jInternalFrameSupplierProfile.getContentPane().add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 280, -1, -1));

        jTextField11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameSupplierProfile.getContentPane().add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 290, 190, -1));

        form1.add(jInternalFrameSupplierProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(-22, -34, 870, 600));

        jInternalFrameInvoice.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameInvoice.setVisible(false);
        jInternalFrameInvoice.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnDone.setText("Done");
        btnDone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDoneMouseClicked(evt);
            }
        });
        btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });
        jInternalFrameInvoice.getContentPane().add(btnDone, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 510, 120, 40));

        panelLoad2.setBackground(new java.awt.Color(255, 255, 255));
        panelLoad2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panelLoad2Layout = new javax.swing.GroupLayout(panelLoad2);
        panelLoad2.setLayout(panelLoad2Layout);
        panelLoad2Layout.setHorizontalGroup(
            panelLoad2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 814, Short.MAX_VALUE)
        );
        panelLoad2Layout.setVerticalGroup(
            panelLoad2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 484, Short.MAX_VALUE)
        );

        jInternalFrameInvoice.getContentPane().add(panelLoad2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 820, 490));

        form1.add(jInternalFrameInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 880, 600));

        jInternalFrameReports1.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameReports1.setVisible(false);
        jInternalFrameReports1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelLoad1.setBackground(new java.awt.Color(255, 255, 255));
        panelLoad1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

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
        jLabel2.setText("To :");
        jInternalFrameReports1.getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, -1, 30));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("Date From :");
        jInternalFrameReports1.getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, 30));

        btnBack6.setText("Back");
        btnBack6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack6ActionPerformed(evt);
            }
        });
        jInternalFrameReports1.getContentPane().add(btnBack6, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, 80, 30));

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
        jInternalFrameHome.getContentPane().add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, -20, 380, 430));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/11111111.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 300, 480));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/depositphotos_387823732-stock-illustration-jewelry-store-vector-flat-style-removebg-preview.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 270, -1, -1));

        jLabel43.setBackground(new java.awt.Color(255, 255, 255));
        jLabel43.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 48)); // NOI18N
        jLabel43.setText("WELCOME !");
        jInternalFrameHome.getContentPane().add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, -1, -1));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 50, -1));

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 50, -1));

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 50, -1));

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 50, -1));

        jLabel41.setBackground(new java.awt.Color(255, 255, 255));
        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel41.setText("Quick User Guide :");
        jInternalFrameHome.getContentPane().add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, 30));

        jLabel60.setBackground(new java.awt.Color(255, 255, 255));
        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel60.setText("Send your gem order request us.");
        jInternalFrameHome.getContentPane().add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, -1, 30));

        jLabel61.setBackground(new java.awt.Color(255, 255, 255));
        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel61.setText(" View your gem order request status(Approved/Rejected).");
        jInternalFrameHome.getContentPane().add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, -1, 30));

        jLabel63.setBackground(new java.awt.Color(255, 255, 255));
        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel63.setText("Generate Order cost report.");
        jInternalFrameHome.getContentPane().add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, -1, 30));

        jLabel65.setBackground(new java.awt.Color(255, 255, 255));
        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel65.setText("Find more about Ashraf Gems.");
        jInternalFrameHome.getContentPane().add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, -1, 30));

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
       jInternalFrameSellGems.setVisible(false);
       jInternalFrameTransaction.setVisible(false);
        jInternalFrameReports.setVisible(false);
        jInternalFrameHelp.setVisible(false);
        jInternalFrameSupplierProfile.setVisible(false);
       
    }//GEN-LAST:event_buttonHomeMouseClicked
    private void hideFields(){
     if(g_variety.equals("Finished")){
            lbl_gemNature.setVisible(false);
            txt_gnature.setVisible(false);
            lbl_gOrigin.setVisible(false);
            txt_gOrigin.setVisible(false);

            lbl_gShape.setVisible(true);
            txt_gshape.setVisible(true);
            lbl_gemCID.setVisible(true);
            lbl_certiID1.setVisible(true);
            txt_gcetiID.setVisible(true);
            lbl_gemPieces.setVisible(true);
            txt_gpieces.setVisible(true);
            }
        else if(g_variety.equals("Raw"))
        {
            lbl_gShape.setVisible(false);
            txt_gshape.setVisible(false);
            lbl_gemCID.setVisible(false);
            lbl_certiID1.setVisible(false);
            txt_gcetiID.setVisible(false);
            lbl_gemPieces.setVisible(false);
            txt_gpieces.setVisible(false);

            lbl_gemNature.setVisible(true);
            txt_gnature.setVisible(true);
            lbl_gOrigin.setVisible(true);
            txt_gOrigin.setVisible(true);
        }
        else{
            /*lbl_gShape.setVisible(true);
            txt_gshape.setVisible(true);
            lbl_gemCID.setVisible(true);
            txt_gcetiID.setVisible(true);
            lbl_gemPieces.setVisible(true);
            txt_gpieces.setVisible(true);
            lbl_gemNature.setVisible(true);
            txt_gnature.setVisible(true);
            lbl_gOrigin.setVisible(true);
            txt_gOrigin.setVisible(true);
            lbl_certiID1.setVisible(true);*/
            lbl_gemNature.setVisible(false);
        lbl_gOrigin.setVisible(false);
        lbl_gemCID.setVisible(false);
        lbl_gemPieces.setVisible(false);
        lbl_gShape.setVisible(false);
        lbl_certiID1.setVisible(false);
         txt_gshape.setVisible(false);
         txt_gcetiID.setVisible(false);
         txt_gpieces.setVisible(false);
        txt_gnature.setVisible(false);
          txt_gOrigin.setVisible(false);
            }
    }
    private void fetchAllSupOrders(){
        
        try{String query = "SELECT * FROM suporder where s_no=?";
           pst = con.prepareStatement(query);
           pst.setString(1,code);
           rs=pst.executeQuery();
           tbl_suporder.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
    private void fetchApprovedSupOrders(){
        
        try{String query = "SELECT * FROM suporder where s_ostatus =? and s_no=?";
           pst = con.prepareStatement(query);
           pst.setString(1,"Approved");
           pst.setString(2,code);
           rs=pst.executeQuery();
           tbl_suporder.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
    private void fetchFinishedSupOrders(){
        
        try{String query = "SELECT * FROM suporder where s_ostatus =?and s_no=?";
           pst = con.prepareStatement(query);
           pst.setString(1,"Finished");
           pst.setString(2,code);
           rs=pst.executeQuery();
           tbl_suporder.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
    private void fetchRejectSupOrders(){
        
        try{String query = "SELECT * FROM suporder where s_ostatus =?and s_no=?";
           pst = con.prepareStatement(query);
           pst.setString(1,"Rejected");
           pst.setString(2,code);
           rs=pst.executeQuery();
           tbl_suporder.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
    public void fetchProcessingSupOrders(){
       
        try{String query = "SELECT * FROM suporder where s_ostatus = ?and s_no=?";
           pst = con.prepareStatement(query);
           pst.setString(1,"Processing");
           pst.setString(2,code);
           rs=pst.executeQuery();
           tbl_suporder.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }

public void loadInvoice() {

 


        HashMap a = new HashMap();
        a.put("sid",code);
        a.put("gname", g_name);
        a.put("gtype", g_type);
        a.put("gcolor", g_color);
        a.put("gweight", g_weight);
        a.put("gprice", g_bprice);
        a.put("gvariety",g_status);
        a.put("gorigin",g_origin );
        a.put("gnature", g_nature);
        a.put("gcid", g_cid);
        a.put("gpieces",g_pieces);
        a.put("gshape",g_shape );
       ;
        a.put("date",today );
      
        panelLoad2.removeAll();
        panelLoad2.repaint();
        panelLoad2.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\InvoiceSupplierRequest.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad2.setLayout(new BorderLayout());
              panelLoad2.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }

}



    private void buttonHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonHomeMouseEntered
        buttonHome.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_buttonHomeMouseEntered

    private void buttonHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonHomeMouseExited
        buttonHome.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_buttonHomeMouseExited

    private void btnSellGemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSellGemsMouseClicked
       bar(lblBar2);
       jInternalFrameHome.setVisible(false);
       jInternalFrameSellGems.setVisible(true);
       jInternalFrameTransaction.setVisible(false);
       jInternalFrameReports.setVisible(false);
       jInternalFrameHelp.setVisible(false);
       jInternalFrameSupplierProfile.setVisible(false);

    }//GEN-LAST:event_btnSellGemsMouseClicked

    private void btnSellGemsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSellGemsMouseEntered
        btnSellGems.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnSellGemsMouseEntered

    private void btnSellGemsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSellGemsMouseExited
         btnSellGems.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnSellGemsMouseExited

    private void btnTransactionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransactionsMouseClicked
        bar(lblBar3);
       jInternalFrameHome.setVisible(false);
       jInternalFrameSellGems.setVisible(false);
       jInternalFrameTransaction.setVisible(true);
        jInternalFrameReports.setVisible(false);
        jInternalFrameHelp.setVisible(false);
        jInternalFrameSupplierProfile.setVisible(false);
        fetchAllSupOrders();
    }//GEN-LAST:event_btnTransactionsMouseClicked

    private void btnTransactionsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransactionsMouseEntered
          btnTransactions.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnTransactionsMouseEntered

    private void btnTransactionsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransactionsMouseExited
        btnTransactions.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnTransactionsMouseExited

    private void btnReportsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseClicked
        bar(lblBar4);
        jInternalFrameHome.setVisible(false);
       jInternalFrameSellGems.setVisible(false);
       jInternalFrameTransaction.setVisible(false);
       jInternalFrameReports.setVisible(true);
       jInternalFrameHelp.setVisible(false);
       
       jInternalFrameSupplierProfile.setVisible(false);
    }//GEN-LAST:event_btnReportsMouseClicked

    private void btnReportsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseEntered
       btnReports.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnReportsMouseEntered

    private void btnReportsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseExited
        btnReports.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnReportsMouseExited

    private void btnHelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHelpMouseClicked
       bar(lblBar5);
       jInternalFrameHome.setVisible(false);
       jInternalFrameSellGems.setVisible(false);
       jInternalFrameTransaction.setVisible(false);
       jInternalFrameReports.setVisible(false);
       jInternalFrameHelp.setVisible(true);
       
       jInternalFrameSupplierProfile.setVisible(false);
    }//GEN-LAST:event_btnHelpMouseClicked

    private void btnHelpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHelpMouseEntered
        btnHelp.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnHelpMouseEntered

    private void btnHelpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHelpMouseExited
         btnHelp.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnHelpMouseExited

    private void btnProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfileMouseClicked
       jInternalFrameHome.setVisible(false);
       jInternalFrameSellGems.setVisible(false);
       jInternalFrameTransaction.setVisible(false);
       jInternalFrameReports.setVisible(false);
       jInternalFrameHelp.setVisible(false);
       
       jInternalFrameSupplierProfile.setVisible(true);
        lblBar1.setOpaque(false);
        lblBar2.setOpaque(false);
        lblBar3.setOpaque(false);
        lblBar4.setOpaque(false);
        lblBar5.setOpaque(false);
       
        menu.repaint();
    }//GEN-LAST:event_btnProfileMouseClicked

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btnExitMouseClicked

    private void btnMiniMizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMiniMizeMouseClicked
       this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMiniMizeMouseClicked

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

    private void txt_gOriginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gOriginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gOriginActionPerformed

    private void txt_gpiecesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gpiecesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gpiecesActionPerformed

    private void txt_gcetiIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gcetiIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gcetiIDActionPerformed

    private void btn_uploadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_uploadMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btn_uploadMouseClicked

    private void txt_gnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gnameActionPerformed

    private void txt_gcolorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gcolorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gcolorActionPerformed

    private void txt_gweightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gweightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gweightActionPerformed

    private void txt_gbpriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gbpriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gbpriceActionPerformed

    private void txt_gtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gtypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gtypeActionPerformed

    private void btn_sendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_sendMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_sendMouseClicked

    private void btn_uploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_uploadActionPerformed
        JFileChooser filechooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE","jpg","png","gif");
        filechooser.addChoosableFileFilter(filter);
        int result = filechooser.showSaveDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
        File selectImage = filechooser.getSelectedFile();
        String imagepath = selectImage.getAbsolutePath();
        try{
        lbl_gemimage.setIcon(ResizeImage(imagepath));
        imagePathStr = imagepath;
        }catch(Exception exception){
        JOptionPane.showMessageDialog(this,"Image Error: "+ exception.getMessage());
        }
       }

    }//GEN-LAST:event_btn_uploadActionPerformed

    private void btn_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendActionPerformed
        getDate();
        calGemID();
        calFGemID();
        calRGemID();
        calSupOrderID();
        calExpenseID();
        calSupplierExpenseID();
        setData();
        
        if(g_variety =="Finished"){
          validateFinishGem();
            
           
          }
        else if(g_variety =="Raw"){
          validateRawGem();
        }else{
               JOptionPane.showMessageDialog(this,"Please Select your Gem Variety First!");
             }

    }//GEN-LAST:event_btn_sendActionPerformed

    private void txt_gnatureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gnatureActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gnatureActionPerformed

    private void comboVarietyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboVarietyActionPerformed
        g_variety = comboVariety.getSelectedItem().toString();
        hideFields();
        
    }//GEN-LAST:event_comboVarietyActionPerformed

    private void txt_gweightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_gweightKeyPressed
        char c = evt.getKeyChar();
           if(Character.isLetter(c))
           {
              JOptionPane.showMessageDialog(this,"This Field must have Numbers");
              txt_gweight.setText("");
           }
    }//GEN-LAST:event_txt_gweightKeyPressed

    private void txt_gshapeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gshapeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gshapeActionPerformed

    private void txt_gbpriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_gbpriceKeyPressed
        char c = evt.getKeyChar();
           if(Character.isLetter(c))
           {
              JOptionPane.showMessageDialog(this,"This Field must have Numbers");
              txt_gbprice.setText("");
           }
    }//GEN-LAST:event_txt_gbpriceKeyPressed

    private void txt_gpiecesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_gpiecesKeyPressed
         char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_gpieces.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            txt_gpieces.setText("");
            
        }

    }//GEN-LAST:event_txt_gpiecesKeyPressed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear();
        
       
    }//GEN-LAST:event_btnClearActionPerformed

    private void tbl_suporderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_suporderMouseClicked
       
    }//GEN-LAST:event_tbl_suporderMouseClicked

    private void btn_processingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_processingMouseClicked

    }//GEN-LAST:event_btn_processingMouseClicked

    private void btn_processingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_processingActionPerformed
     fetchProcessingSupOrders();        
    }//GEN-LAST:event_btn_processingActionPerformed

    private void btn_regectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_regectMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_regectMouseClicked

    private void btn_regectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regectActionPerformed
        fetchRejectSupOrders();
    }//GEN-LAST:event_btn_regectActionPerformed

    private void btn_approved1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_approved1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_approved1MouseClicked

    private void btn_approved1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_approved1ActionPerformed
        fetchApprovedSupOrders();
    }//GEN-LAST:event_btn_approved1ActionPerformed

    private void btn_soldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_soldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_soldMouseClicked

    private void btn_soldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_soldActionPerformed
        fetchFinishedSupOrders();
    }//GEN-LAST:event_btn_soldActionPerformed

    private void btnGenerateGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateGraphActionPerformed
        // TODO add your handling code here:
        loadGraphreport();
    }//GEN-LAST:event_btnGenerateGraphActionPerformed

    private void btnGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateReportActionPerformed
        // TODO add your handling code here:
       loadreport();
    }//GEN-LAST:event_btnGenerateReportActionPerformed

    private void btnDoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDoneMouseClicked

    }//GEN-LAST:event_btnDoneMouseClicked

    private void btnDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed
        jInternalFrameInvoice.setVisible(false);
        jInternalFrameSellGems.setVisible(true);
    }//GEN-LAST:event_btnDoneActionPerformed

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

    private void txt_gnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_gnameKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            txt_gname.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            txt_gname.setText("");
        }

    }//GEN-LAST:event_txt_gnameKeyPressed

    private void txt_gtypeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_gtypeKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            txt_gtype.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            txt_gtype.setText("");
        }

    }//GEN-LAST:event_txt_gtypeKeyPressed

    private void txt_gcolorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_gcolorKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            txt_gcolor.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            txt_gcolor.setText("");
        }

    }//GEN-LAST:event_txt_gcolorKeyPressed

    private void txt_gOriginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_gOriginKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            txt_gOrigin.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            txt_gOrigin.setText("");
        }

    }//GEN-LAST:event_txt_gOriginKeyPressed

    private void txt_gshapeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_gshapeKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            txt_gshape.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            txt_gshape.setText("");
        }
    }//GEN-LAST:event_txt_gshapeKeyPressed

    private void txt_gnatureKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_gnatureKeyPressed
         char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            txt_gnature.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            txt_gnature.setText("");
        }
    }//GEN-LAST:event_txt_gnatureKeyPressed

    private void txt_gcetiIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_gcetiIDKeyPressed
       char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            txt_gcetiID.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            txt_gcetiID.setText("");
        }

    }//GEN-LAST:event_txt_gcetiIDKeyPressed
    
    public void bar(JLabel lab){
        lblBar1.setOpaque(false);
        lblBar2.setOpaque(false);
        lblBar3.setOpaque(false);
        lblBar4.setOpaque(false);
        lblBar5.setOpaque(false);
        
        lab.setOpaque(true);
        menu.repaint();
    }
    
    
   
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private View.MyButton btnBack6;
    private View.MyButton btnClear;
    private View.MyButton btnClear2;
    private View.MyButton btnDone;
    private javax.swing.JLabel btnExit;
    private View.MyButton btnGenerate;
    private View.MyButton btnGenerateGraph;
    private View.MyButton btnGenerateReport;
    private javax.swing.JPanel btnHelp;
    private javax.swing.JLabel btnLogOut;
    private View.MyButton btnMap;
    private javax.swing.JLabel btnMiniMize;
    private javax.swing.JLabel btnProfile;
    private javax.swing.JPanel btnReports;
    private View.MyButton btnSave;
    private javax.swing.JPanel btnSellGems;
    private javax.swing.JPanel btnTransactions;
    private View.MyButton btn_approved1;
    private View.MyButton btn_processing;
    private View.MyButton btn_regect;
    private View.MyButton btn_send;
    private View.MyButton btn_sold;
    private View.MyButton btn_upload;
    private View.MyButton btnfilter;
    private javax.swing.JPanel buttonHome;
    private javax.swing.JComboBox<String> comboVariety;
    private com.toedter.calendar.JDateChooser datechooser_from;
    private com.toedter.calendar.JDateChooser datechooser_to;
    private javax.swing.JPanel form1;
    private javax.swing.JInternalFrame jInternalFrameHelp;
    private javax.swing.JInternalFrame jInternalFrameHome;
    private javax.swing.JInternalFrame jInternalFrameInvoice;
    private javax.swing.JInternalFrame jInternalFrameReports;
    private javax.swing.JInternalFrame jInternalFrameReports1;
    private javax.swing.JInternalFrame jInternalFrameSellGems;
    private javax.swing.JInternalFrame jInternalFrameSupplierProfile;
    private javax.swing.JInternalFrame jInternalFrameTransaction;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel25;
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
    private javax.swing.JLabel jLabel49;
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
    private javax.swing.JLabel jLabel60;
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
    private javax.swing.JLabel lblBar5;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserName1;
    private javax.swing.JLabel lbl_certiID1;
    private javax.swing.JLabel lbl_gOrigin;
    private javax.swing.JLabel lbl_gShape;
    private javax.swing.JLabel lbl_gemCID;
    private javax.swing.JLabel lbl_gemNature;
    private javax.swing.JLabel lbl_gemPieces;
    private javax.swing.JLabel lbl_gemVariety;
    private javax.swing.JLabel lbl_gemcolor;
    private javax.swing.JLabel lbl_gemimage;
    private javax.swing.JLabel lbl_gemname;
    private javax.swing.JLabel lbl_gemtype;
    private javax.swing.JLabel lbl_gimageAddress1;
    private javax.swing.JLabel lbl_gweight;
    private javax.swing.JLabel lbl_sellprice;
    private javax.swing.JLabel lbl_weight;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel panelLoad;
    private javax.swing.JPanel panelLoad1;
    private javax.swing.JPanel panelLoad2;
    private javax.swing.JTable tbl_suporder;
    private javax.swing.JTextField txt_gOrigin;
    private javax.swing.JTextField txt_gbprice;
    private javax.swing.JTextField txt_gcetiID;
    private javax.swing.JTextField txt_gcolor;
    private javax.swing.JTextField txt_gname;
    private javax.swing.JTextField txt_gnature;
    private javax.swing.JTextField txt_gpieces;
    private javax.swing.JTextField txt_gshape;
    private javax.swing.JTextField txt_gtype;
    private javax.swing.JTextField txt_gweight;
    // End of variables declaration//GEN-END:variables
    private String imagePathStr;
   // image resize
   private ImageIcon ResizeImage(String imgPath){
   int imageX = 177;
   int imageY = 202;
   lbl_gemimage.setSize(imageX,imageY);
    
   ImageIcon myImage = new ImageIcon(imgPath);
   Image img = myImage.getImage();
   Image newImage = img.getScaledInstance(lbl_gemimage.getWidth(), lbl_gemimage.getHeight(), Image.SCALE_SMOOTH);
   ImageIcon image = new ImageIcon(newImage);
   return image;
}

}
