/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbi_atm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author HP
 */
public class Service 
{
  Scanner sc = new Scanner(System.in);
  Scanner ss = new Scanner(System.in);
  DAO dl = new DAO();
  Model mo = new Model();
  long l1 = 100000l;
  long l2 = 999999l;
  
  public void UserCreate() throws SQLException, ClassNotFoundException
  {
      long ac_no=0l;
      long deb_no=0l;
      while (true){
      System.out.println("Enter the Account Number");
       ac_no = sc.nextLong();
      if(!(ac_no>=l1 && ac_no<=l2))
              System.out.println("Enter 14 digit AccountNumber!!");
      else
          break;
      }
      System.out.println("Enter the Account Name");
      String ac_holder = ss.nextLine();
      while (true){
      System.out.println("Enter the DebitCard Number");
       deb_no = sc.nextLong();
      if (!(deb_no>=l1 && deb_no<=l2))
              System.out.println("Card Number Should be 14 Diigts!!");
      else
          break;
      }
      System.out.println("Enter the Amount");
      int amt = sc.nextInt();
      mo.setAccount_no(ac_no);
      mo.setAcc_name(ac_holder);
      mo.setDebit_card_no(deb_no);
      mo.setAmount(amt);
      int ins = dl.creation(mo);
      if(ins>0)
          System.out.println("Account created Successfully!!");
  }
  
  public void PinCreate() throws SQLException, ClassNotFoundException
  {
      System.out.println("Enter the Account Number::");
      long ac_no = sc.nextLong();
      System.out.println("Enter the Debit Card Number::");
      long dc_no = sc.nextLong();
      String pin, fpin;
      while(true)
      {
         while(true)
         {
          System.out.println("Enter 4-Digit New PIN Number::");
          pin = ss.next();
          ss.nextLine();
          if (!pin.matches("\\d{4}"))
                 System.out.println("Invalid pin Only 4-Digits Allowed");
          else
              break;
         }   
          System.out.println("Re-Enter PIN Number to Confirm ::");
          fpin = ss.next();
          ss.nextLine();
          if (pin.equals(fpin)) {
            break;
          } else {
              System.out.println("Pin Mismatch  \n***TRY AGAIN***");
          }
          }
      
          mo.setCardPin(fpin);
          mo.setDebit_card_no(dc_no);
          int cpin = dl.pingen(mo);
          if (cpin>0)
          {
              System.out.println("Pin Created SuccessFully");
          }
          else{  
              System.out.println("Enter the Valid Debit Card Number");
              this.PinCreate();
          }
      
  }
  public void deposit() throws ClassNotFoundException, SQLException {
       while (true){
      System.out.println("Enter the Account Number:");
    long number = sc.nextLong();
    mo.setAccount_no(number);

    // Validate deposit amount
    int dep;
       while (true) {
        System.out.println("Enter the Amount to Deposit:");
        dep = sc.nextInt();
        if (dep % 100 != 0) {
            System.out.println("Amount must be in multiples of 100...");
        } else {
            mo.setAmount(dep);
            break;
        }
    }
    
    System.out.print("Enter Your Pin: ");
    String pin = sc.next();
    mo.setCardPin(pin);

     int re = dl.deposit(mo);

    
    if (re == 0) {
        System.out.println("Wrong Account Number");
    } else if (re == 1) {
        System.out.println("Wrong Pin. Please try again.");
    } else if (re == 2) {
        System.out.println("Deposited Successfully");
        System.out.println("Your Current Balance is "+dl.curr);
        break;
    } else {
        System.out.println("!!!!!!!!!!");
        break;
    }
   }
}
       public void withdraw() throws ClassNotFoundException, SQLException {
        while (true) {
            System.out.println("Enter the Account Number:");
            long number = sc.nextLong();
            mo.setAccount_no(number);

            // Validate deposit amount
            int dep;
            while (true) {
                System.out.println("Enter the Amount to Withdraw:");
                dep = sc.nextInt();
                if (dep % 100 != 0) {
                    System.out.println("Amount must be in multiples of 100...");
                } else {
                    mo.setAmount(dep);
                    break;
                }
            }

            System.out.print("Enter Your Pin: ");
            String pin = sc.next();
            mo.setCardPin(pin);

            int re = dl.Withdraw(mo);

            if (re == 0) {
                System.out.println("Wrong Account Number");
            } else if (re == 1) {
                System.out.println("Wrong Pin. Please try again.");
            } else if (re == 2) {
                System.out.println("Withdrawed Successfully");
                System.out.println("Your Current Balance is "+dl.curr);
                break;
            } else if (re==3) {
                System.out.println("Insufficient Balance");
                
            }
}
  }
       public void Balance() throws SQLException, ClassNotFoundException {
        boolean chk = false;
        int atmpt = 1;
        int max_atmpt = 5;
        int msg = 1;
        while (!chk && (atmpt < max_atmpt)) {
            System.out.print("Enter Your DebitCard Number :: ");
            long debitCard = sc.nextLong();
            System.out.print("Enter Your Pin :: ");
            String pin = ss.nextLine();

            mo.setDebit_card_no(debitCard);
            mo.setCardPin(pin);

            Map<String, Object> obj = dl.enquiry(mo);

            if (obj.containsKey("DebitCard")) {
                System.out.println(obj.get("DebitCard"));
                atmpt++;
                System.out.println("Only " + (max_atmpt - atmpt) + " times Remaining");
                
            } else if (obj.containsKey("Pin")) {
                System.out.println(obj.get("Pin"));
                atmpt++;
                System.out.println("Only " + (max_atmpt - atmpt) + " times Remaining");
                
            } else {
                for (Map.Entry<String, Object> entry : obj.entrySet()) {
                    System.out.println(entry.getKey() + " :: " + entry.getValue());
                    chk = true;
                    msg = 2;
                }
            }
        }
        if (msg == 1)
        {
            System.out.println("Maximum Attempts Reached \n Try Again After Some time");
        }
    }
    public void Secret() throws SQLException, ClassNotFoundException {
        String usr = "jash";
        int pass = 201096;
        System.out.print("Enter the UserName :: ");
        String usrname = ss.nextLine();
        System.out.print("Enter the Password :: ");
        int pasw = sc.nextInt();
        if (usr.equals(usrname) && (pass == pasw)) {
            ArrayList<Map<String, Object>> details = dl.Code();
            System.out.println("---------------------------");
            for (Map<String, Object> row : details) {
                System.out.println("Holder Name       :: " + row.get("acc_name"));
                System.out.println("Account Number    :: " + row.get("account_no"));
                System.out.println("DebitCard Number  :: " + row.get("debit_card_no"));
                System.out.println("Card Pin          :: " + row.get("card_pin"));
                System.out.println("Balance           :: " + row.get("amount"));
                System.out.println("---------------------------");
            }
        }
        else
        {
            System.out.println("Access Denied!!!!");
        }

    }
}



           

           
       
        
        


  

  

