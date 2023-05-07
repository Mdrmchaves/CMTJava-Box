package stm;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TransLocal {
    public static ThreadLocal<Trans> local = new ThreadLocal<Trans>();   
}


