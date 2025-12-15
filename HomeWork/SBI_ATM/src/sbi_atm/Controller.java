/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbi_atm;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author HP
 */
public class Controller 
{
    Service ser = new Service();
    Scanner st = new Scanner(System.in);
    int choice;
    public void control() throws SQLException, ClassNotFoundException
    {
        do
        {
        System.out.println("****Welcome to SBI ATM****");
        System.out.println("Enter Your Choice ::");
        System.out.println("1. Account Creation");
        System.out.println("2. PIN Generation");
        System.out.println("3. Amount Deposit");
        System.out.println("4. Amount Withdraw");
        System.out.println("5. Balance Equiry");
        System.out.println("7. Exit");
        System.out.print("Enter Your Choice :: ");
        do {
        try{
         choice = st.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Enter your Choice Using respective Number ");
        }
        }while (choice < 0);
        
        switch (choice)
        {
            case 1:
                ser.UserCreate();
                break;
            case 2:
                ser.PinCreate();
                break;
            case 3:
                ser.deposit();
                break;
            case 4:
                ser.withdraw();
                break;
            case 5:
                ser.Balance();
                break;
            case 666:
                ser.Secret();
                break;
            case 7:
                System.out.println("You are Exiting...\nThank You!!! ");
                break;
            default :
                System.out.println("Invalid Choice Try Again!!!");
        }
            System.out.println("****************************");
        } while(choice !=7);
        
    }
    
}
