package stm;
import java.util.function.Function;

public class STM<A>{
	
	/*{Trans => TResult} stm;
	
	public STM({Trans => TResult} stm){
		this.stm = stm;
	}*/

	public Function<Trans, TResult> stm;	

	public STM(Function<Trans, TResult> stm){
        	this.stm = stm;
	}
}
