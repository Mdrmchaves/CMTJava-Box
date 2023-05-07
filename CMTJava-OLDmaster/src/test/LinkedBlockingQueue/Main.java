package test.LinkedBlockingQueue;
import java.util.List;
import stm.*;

class Main{

	public static void main (String args[])throws Exception {

		LinkedBlockingQueue lol = new LinkedBlockingQueue(1000);
		int commits=0;
		int nthreads = Integer.parseInt(args[0]);
		Thread t;
		
		//STMRTS.atomic(lol.put(9));
		//STMRTS.atomic(lol.put(4));

/* testes
		Node t = (Node)STMRTS.atomic(l.take());
		Object i = STMRTS.atomic(t.getItem());
		System.out.println(i);

		t = (Node)STMRTS.atomic(l.take());
		i = STMRTS.atomic(t.getItem());
		System.out.println(i);

		t = (Node)STMRTS.atomic(l.extract());
		i = STMRTS.atomic(t.getItem());
		System.out.println(i);
*/      	

		ThreadRandom vet[] = new ThreadRandom[nthreads];
		long tempoInicial = System.currentTimeMillis();		
		
		for(int i = 0;i<nthreads; i++){
			vet[i] = new ThreadRandom(lol,4000);
			vet[i].start();
	    	}
		
		//Thread.sleep(1000);
	    
		//System.out.println(lol);
        	
		for(int i = 0;i<nthreads; i++)   
			vet[i].join();
		
		long tempoFinal = System.currentTimeMillis();  
		System.out.println( tempoFinal - tempoInicial );			
	        
		//Thread.sleep(1000);

	        //for(int i = 0;i<nthreads; i++){
		//	commits = commits + vet[i].commits;
		//	System.out.println("Numero de Commits: "+ commits);
		//}
	}

}

class ThreadRandom extends Thread{
	
	public int commits=0;
    	volatile boolean cond = true;
	private LinkedBlockingQueue l;
	public int ops;

	ThreadRandom(LinkedBlockingQueue l, int nops) {
		this.l = l;
		this.ops = nops;
	}	

	public void run() {
		
		Integer v;
		int operacao;
		int elementos = 0;

		while(commits <  ops) {
		
	    	operacao = 1 + (int) (Math.random()*2); 
	    	v = (Integer) (int)(5*Math.random()+1);
		
		//System.out.println("operação" + operacao);
		//System.out.println("valor" + v);

	    	if(operacao == 1) {
		    	//System.out.println(Thread.currentThread() + " Insert: "+ v);
		    	STMRTS.atomic(l.put(v));
			//System.out.println(l);
		    	commits++;
			elementos++;
			//System.out.println("Elementos: " + elementos);
		    	//System.out.println(l);System.out.flush();

			} else {
				if(elementos > 0){				
					//System.out.println(Thread.currentThread() + " Delete cauda");					
					STMRTS.atomic(l.poll());			
					commits++;
					elementos--;
					//System.out.println("Elementos: " + elementos);					
					//System.out.println(l); System.out.flush();
				} else { continue; }
			}

		//System.out.println(Thread.currentThread() + " " + l);	
	     	}
	}
}

