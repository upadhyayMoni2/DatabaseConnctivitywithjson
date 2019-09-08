package javaapplication4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JavaApplication4 {

    Connection conn = null;
    Statement stm = null;
    ResultSet rs = null;
    String id, regionName;
    JSONObject singleConnError = new JSONObject();
    JSONObject singleStmError = new JSONObject();
    JSONObject singleRsError = new JSONObject();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String ans = sc.nextLine();
        do {

            System.out.println("Menu: ");
            System.out.println("1.  show List");
            System.out.println("2.  Insert record");
            System.out.println("3.  update record");
            System.out.println("4.  Delete record");
            System.out.println("5.  singleList");

            System.out.println("Emter your choice :");

            JavaApplication4 ja = new JavaApplication4();
            int a = sc.nextInt();

            switch (a) {

                case 1:
                    ja.getDataList();
                    break;
                case 2:
                    ja.insertData();
                    break;
                case 3:
                    ja.updateData();
                    break;
                case 4:
                    ja.deleteData();
                    break;
                case 5:
                    ja.getSingleList();
                    break;

                default:
                    System.out.println("Please choose appropriate number");

            }
            System.out.println("Continue for Not?(Y/N)?");

        } while (ans.equalsIgnoreCase("Y"));
    }

    public Connection getConnection() {

        try {

            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");

            if (conn != null) {
                System.out.println("connected...");

            } else {
                System.out.println("connection fail...");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JavaApplication4.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JavaApplication4.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conn;
    }

    public void getDataList() {

        JSONArray mainarrList = new JSONArray();
        JSONObject singleobjList = new JSONObject();

        conn = getConnection();

        if (conn != null) {
            try {
                String sql = "select region_id, region_name from regions";
                stm = conn.createStatement();
                int i = stm.executeUpdate(sql);

                rs = stm.executeQuery(sql);

                while (rs.next()) {

                    id = rs.getString("region_id");

                    regionName = rs.getString("region_name");
                    singleobjList.accumulate("id", id);
                    singleobjList.accumulate("regionName", regionName);

                    mainarrList.add(singleobjList);
                    singleobjList.clear();
//                    conn.close();
//                    rs.close();
//                    stm.close();
//                
                }
                System.out.println(mainarrList);
            } catch (SQLException ex) {
                Logger.getLogger(JavaApplication4.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            singleConnError.accumulate("message", "Connecyion Error");
            System.out.println(singleConnError);
        }

    }

    public void insertData() {

        conn = getConnection();
        JSONObject singleInsert = new JSONObject();

        String sql = "insert into regions(region_id , region_name) values(897 , 'mehsana') ";

        try {
            stm = conn.createStatement();
            int i = stm.executeUpdate(sql);

            if (i > 0) {
                singleInsert.accumulate("message", "Record inserted");
                System.out.println(singleInsert);
//                conn.close();
//                stm.close();
//               
            } else {
                singleInsert.accumulate("message", "Record Not inserted");
                System.out.println(singleInsert);
            }

        } catch (SQLException ex) {
            Logger.getLogger(JavaApplication4.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteData() {

        conn = getConnection();
        JSONObject singleDelete = new JSONObject();

        String sql = "Delete from regions where region_id = 897";

        try {
            stm = conn.createStatement();
            int i = stm.executeUpdate(sql);
            if (i > 0) {
                singleDelete.accumulate("message", "Record Deleted");
                System.out.println(singleDelete);
//                conn.close();
//                rs.close();
//                stm.close();
            } else {
                singleDelete.accumulate("message", "Record not Deleted");
                System.out.println(singleDelete);
            }

        } catch (SQLException ex) {
            Logger.getLogger(JavaApplication4.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getSingleList() {

        conn = getConnection();
        JSONObject singleList = new JSONObject();

        String sql = "select * from regions where region_id=777";

        try {
            stm = conn.createStatement();
            int i = stm.executeUpdate(sql);

            rs = stm.executeQuery(sql);

            while (rs.next()) {

                id = rs.getString("region_id");

                regionName = rs.getString("region_name");
                singleList.accumulate("id", id);
                singleList.accumulate("regionName", regionName);
//                conn.close();
//                rs.close();
//                stm.close();
            }
            System.out.println(singleList);

        } catch (SQLException ex) {
            Logger.getLogger(JavaApplication4.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateData() {

        conn = getConnection();
        JSONObject singleupdate = new JSONObject();
        String sql = "update regions set region_name='manipur' where region_id=777";

        try {
            stm = conn.createStatement();

            int i = stm.executeUpdate(sql);

            if (i > 0) {
                singleupdate.accumulate("message", "Record Updated");
                System.out.println(singleupdate);
//                conn.close();
//                rs.close();
//                stm.close();
//            
            } else {
                singleupdate.accumulate("message", "Record Not Updated");
                System.out.println(singleupdate);
            }

        } catch (SQLException ex) {
            Logger.getLogger(JavaApplication4.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
