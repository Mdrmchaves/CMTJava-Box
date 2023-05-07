package stm;

public class AbortedException extends Exception{
    public AbortedException(String errorMessage){
        super(errorMessage);
    }
    public AbortedException(){
    }
}
