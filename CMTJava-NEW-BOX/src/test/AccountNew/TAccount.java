package test.AccountNew;
import stm.*;

public class TAccount{
    private Box<Double> balance; 
	private Box<Integer> ops;

    public void setOps (Integer n) throws Exception{
		ops.setBox(n);
	}

	public Integer getOps () throws Exception{
		return ops.getBox();
	}

	public void setBalance (Double n) throws Exception{
	
	    balance.setBox(n);
        }

	public Double getBalance () throws Exception{
		return balance.getBox();
	}

    TAccount() {
        balance =  new Box<Double>(0.0);
        ops = new Box<Integer>(0);
    }

    TAccount (Double b){
	   balance =  new Box<Double>(b);
	   ops = new Box<Integer>(0);
	}
}