package test.AccountNew;

import stm.*;
import java.util.concurrent.Callable;

class TestAccount{
  
   public static void main(String [] args) throws Exception
   {
   
     Account ac1 = new Account (100.0);
     Account ac2 = new Account (200.0);
     
     Callable<Integer> trans = () -> {
       ac1.setBalance(200.0);
       ac2.setBalance(100.0);
       ac1.deposit(15.0);
       ac2.withdraw(15.0);
       ac1.transfer(ac2, 15.0);
       System.out.println(ac2.getBalance());
       System.out.println(ac1.getBalance());
       return 0;
     };

     //STM<Integer> trans = () -> {
     //   ac1.setBalance(200.0);
     //   ac2.setBalance(100.0);
     //   return 0;
     // };

     Trans.atomic(trans);
     //STMRTS.atomic(trans);
   }

}