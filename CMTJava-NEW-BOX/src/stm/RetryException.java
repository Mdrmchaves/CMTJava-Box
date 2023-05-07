package stm;

public class RetryException extends Exception{
    public RetryException(String errorMessage){
        super(errorMessage);
    }
    public RetryException(){
    }
}
