/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbi_atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static jdk.nashorn.internal.objects.NativeArray.map;

/**
 *
 * @author HP
 */
public class DAO 
{
    String curr;
    Model mo = new Model();
    public Connection Dbconnect() throws SQLException, ClassNotFoundException
    {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SBI_ATM","postgres","jash96");
        return con;
    }
    public int creation(Model mo) throws SQLException, ClassNotFoundException
    {
        Connection con = Dbconnect();
        PreparedStatement pt = con.prepareStatement("insert into Atm_transactions (account_no, acc_name, debit_card_no, amount) values (?,?,?,?)");
        pt.setLong(1, mo.getAccount_no());
        pt.setString(2, mo.getAcc_name());
        pt.setLong(3, mo.getDebit_card_no());
        pt.setInt(4, mo.getAmount());
        int ins = pt.executeUpdate();
        return ins;
    }
    public int pingen(Model mo) throws SQLException, ClassNotFoundException
    {
        Connection con = Dbconnect();
        PreparedStatement pt = con.prepareStatement("update Atm_transactions set card_no = ? where debit_card_no = ? ");
        pt.setString(1, mo.getCardPin());
        pt.setLong(2, mo.getDebit_card_no());
        int pgen = pt.executeUpdate();
        return pgen;
    }
    public int deposit(Model mo) throws SQLException, ClassNotFoundException
    {
        Connection con =Dbconnect();
        PreparedStatement pt1 = con.prepareStatement("select account_no, card_no from Atm_transactions where account_no = ? ");
        PreparedStatement pt2 = con.prepareStatement("select * from Atm_transactions where account_no = ? ");
        PreparedStatement pt = con.prepareStatement("update Atm_transactions set amount = amount+? where account_no = ? and card_no = ?  ");
        pt1.setLong(1, mo.getAccount_no());
       ResultSet re = pt1.executeQuery();
       if(!re.next()){
           return 0;
       }
       boolean chk = re.getString("card_no").equals(mo.getCardPin());
       if (!chk)
       {
           return 1;
       }
       else
       {
        pt.setInt(1, mo.getAmount());
        pt.setLong(2, mo.getAccount_no());
        pt.setString(3, mo.getCardPin()); 
        int r = pt.executeUpdate();
        if (r>0){
        pt2.setLong(1, mo.getAccount_no());
        ResultSet depa = pt2.executeQuery();
        depa.next();
        curr = depa.getString("amount");
        }
        
       }
      return 2;
    }
    public int Withdraw(Model mo) throws SQLException, ClassNotFoundException
    {
        Connection con =Dbconnect();
        PreparedStatement pt1 = con.prepareStatement("select account_no, card_no, amount from Atm_transactions where account_no = ? ");
        PreparedStatement pt2 = con.prepareStatement("select *  from Atm_transactions where account_no = ? ");
        PreparedStatement pt = con.prepareStatement("update Atm_transactions set amount = amount - ? where account_no = ? and card_no = ?  ");
        pt1.setLong(1, mo.getAccount_no());
       ResultSet re = pt1.executeQuery();
       if(!re.next()){
           return 0;
       }
       boolean chk = re.getString("card_no").equals(mo.getCardPin());
       int reqamount = Integer.valueOf(re.getString("amount"));
       int avlamount = mo.getAmount();
       if (!chk)
       {
           return 1;
       }
       else if(avlamount > reqamount)
       {
           return 3;
       }
       else
       {
        pt.setInt(1, mo.getAmount());
        pt.setLong(2, mo.getAccount_no());
        pt.setString(3, mo.getCardPin()); 
        int r = pt.executeUpdate();
        if (r>0){
        pt2.setLong(1, mo.getAccount_no());
        ResultSet out = pt2.executeQuery();
        out.next();
        curr = out.getString("amount");
        }
       }
        
      return 2;
    }
    
    public Map<String, Object> enquiry(Model mo) throws SQLException, ClassNotFoundException {
        Connection con = Dbconnect();

        PreparedStatement st = con.prepareStatement("SELECT * FROM Atm_transactions WHERE debit_card_no = ?");
        
        st.setLong(1, mo.getDebit_card_no()); 
        ResultSet rs = st.executeQuery();
        
        Map<String, Object> row = new HashMap<>();

        if (!rs.next()) {
            row.put("DebitCard", "Invalid Debit Card Number \nEnter the Correct Debit Card Number");
            return row;
        }

        String actualPin = rs.getString("card_no");
        if (!actualPin.equals(mo.getCardPin())) {
            row.put("Pin", "Invalid Pin Number \nEnter the Correct Pin");
            return row;
        }

        
        row.put("Account_Name", rs.getString("acc_name"));
        row.put("Account_Number", rs.getLong("account_no"));
        row.put("Debit_Card_Number", rs.getLong("debit_card_no"));
        row.put("Balance", rs.getInt("amount"));

        return row;

}
   public ArrayList<Map<String, Object>> Code() throws SQLException, ClassNotFoundException {
    Connection con = Dbconnect();

    
    PreparedStatement st = con.prepareStatement("SELECT acc_name, account_no, debit_card_no, card_no, amount FROM Atm_transactions");
    ResultSet rs = st.executeQuery();

    ArrayList<Map<String, Object>> details = new ArrayList<>();

    while (rs.next()) {
        Map<String, Object> row = new HashMap<>();
        row.put("acc_name", rs.getString("acc_name"));
        row.put("account_no", rs.getLong("account_no"));
        row.put("debit_card_no", rs.getLong("debit_card_no"));
        row.put("card_no", rs.getString("card_no"));
        row.put("amount", rs.getInt("amount"));

        details.add(row); 
    }

    return details;
}
}

