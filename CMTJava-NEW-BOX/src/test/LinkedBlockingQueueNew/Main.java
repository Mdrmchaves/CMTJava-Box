package test.LinkedBlockingQueueNew;
import java.util.concurrent.Callable;
import java.lang.*;
import stm.*;

class Main{

	public static void main (String args[]) throws Exception {

		LinkedBlockingQueue lol = new LinkedBlockingQueue(1000);
		int commits=0;
		int nthreads = Integer.parseInt(args[0]);
		Thread t;
		

		ThreadRandom vet[] = new ThreadRandom[nthreads];
		long tempoInicial = System.currentTimeMillis();		
		
		for(int i = 0;i<nthreads; i++){
			vet[i] = new ThreadRandom(lol,4000);
			vet[i].start();
	    	}
	    
        	
		for(int i = 0;i<nthreads; i++)   
			vet[i].join();
		
		long tempoFinal = System.currentTimeMillis();  
		System.out.println( tempoFinal - tempoInicial );	

	}

}

class ThreadRandom extends Thread{
	
	public int commits=0;
    	volatile boolean cond = true;
	private LinkedBlockingQueue l;
	public int ops;

	ThreadRandom(LinkedBlockingQueue<Integer> l, int nops) {
		this.l = l;
		this.ops = nops;
	}	

	public void run() {
		
		int operacao;
		int elementos = 0;

		while(commits <  ops) {
		
	    	operacao = 1 + (int) (Math.random()*2); 


	    	if(operacao == 1) {
		    	Trans.atomic(((Callable<java.lang.Void>) () -> {l.put((Integer) (int)(5*Math.random()+1)); return null;}));
		    	commits++;
			elementos++;
			} else {
				if(elementos > 0){								
					Trans.atomic(((Callable<java.lang.Void>) () -> {l.poll(); return null;}));			
					commits++;
					elementos--;
				} else { continue; }
			}
	    }
	}

}

