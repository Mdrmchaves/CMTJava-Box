package test.AccountNew;

import stm.*;

class Account extends TAccount{
	
	Account(){
	   super();
	}

	Account (Double b)
	{
	   super(b);
	}
	
	

	public Double deposit(Double n) throws Exception{
		n += getBalance();
		setBalance(n);
		setOps(getOps()+1);
		return getBalance();
	}

	public Double withdraw (Double n) throws Exception{
		Double value = getBalance();
		if(value >= n){
			setBalance(value - n);
		}else{
			throw new RetryException();
		}
		setOps(getOps()+1);
		return getBalance();
	}

	public Double transfer (Account toDeposit,Double n) throws Exception{
		try{
			this.withdraw(n);
			toDeposit.deposit(n);
			setOps(getOps()+1);
			return getBalance();
		}catch(Exception e){
			throw new Exception(e);
		}
		
	}
}