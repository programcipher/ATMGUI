package ramaloko.m;

import java.util.Random;

public class BankAccount {
   private String userAccountNumber;
   private String userPIN;
   private double userBalance;
   private String userName;
   private Random random;

   public BankAccount() {
      userAccountNumber = "A123456789";
      userPIN = "0000";
      userBalance = 0.0;
      userName = "INITIALS SURNAME";
   }

   public BankAccount(String userAccountNumber, String userPIN, double userBalance, String userName) {
      this.userAccountNumber = userAccountNumber;
      this.userPIN = userPIN;
      this.userBalance = userBalance;
      this.userName = userName;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getUserAccountNumber() {
      return userAccountNumber;
   }

   public void setUserAccountNumber(String userAccountNumber) {
      this.userAccountNumber = userAccountNumber;
   }

   public String getUserPIN() {
      return userPIN;
   }

   public void setUserPIN(String userPIN) {
      this.userPIN = userPIN;
   }

   public double getUserBalance() {
      return userBalance;
   }

   public void setUserBalance() {
      random = new Random();
      userBalance = (double)this.random.nextInt(0, 1000);
   }

   public void setBalance(double balance) {
      userBalance = balance;
   }

   public boolean authenticateAccount(String userAccountNumber) {
      return userAccountNumber.equals(userAccountNumber);
   }

   public boolean authenticatePIN(String enteredPIN) {
      return userPIN.equals(enteredPIN);
   }

   public void deposit(double amount) {
      userBalance += amount;
   }

   public void withdraw(double amount) {
      userBalance -= amount;
   }
}
