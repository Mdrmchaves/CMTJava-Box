package test.LinkedListNew;

import java.util.concurrent.Callable;

import stm.*;

class Test {
	
	public static void main (String args[])throws Exception {

		LinkedList<Integer> l = new LinkedList<Integer>();
		int nthreads = Integer.parseInt(args[0]);
		int nops = Integer.parseInt(args[1]);

		ThreadRandom vet[] = new ThreadRandom[nthreads];
		for(int i = 0;i<nthreads; i++) {
			vet[i] = new ThreadRandom(l, nops, ThreadRandom.OP_TYPE_ADDTOTAIL, nthreads*nops);
			vet[i].start();
		}

		for(int i = 0;i<nthreads; i++) {
			vet[i].join();
		}

	assert( (Trans.atomic((Callable<Integer>) () -> {return l.getSize();})) == nops*nthreads);
		for(int i = 0;i<nthreads; i++) {
			vet[i] = new ThreadRandom(l, nops, ThreadRandom.OP_TYPE_REMOVE, nthreads*nops);
			vet[i].start();
		}

		for(int i = 0;i<nthreads; i++) {
			vet[i].join();
		}

		int commits = 0;
		for(int i = 0;i<nthreads; i++) {
			commits += vet[i].removes;
		}

	assert( (Integer)(Trans.atomic((Callable<Integer>) () -> {return l.getSize();}))+commits == nops*nthreads);
	}

	public static <E> void print(Node<E> node){
		
		if(node!=null){
			E e = Trans.atomic((Callable<E>) () -> {return node.getElement();});
			System.out.print(e +"-");
			print(Trans.atomic((Callable<Node<E>>) () -> {return node.getNext();}));
		}else{
			System.out.println();
    	}
	}
}

class ThreadRandom extends Thread {

	public static final int OP_TYPE_ADDTOTAIL = 1;
	public static final int OP_TYPE_REMOVE = 2;

  	public int removes;
	private LinkedList l;
	private int nops;
	private int opType;
	private int maxValue;

	ThreadRandom(LinkedList l, int numOps, int opType, int maxValue) {
		this.l = l;
    	this.nops = numOps;
    	this.opType = opType;
    	this.maxValue = maxValue;
	}

	public void run() {
    	int operacao;
		for(int i=0;i<nops;i++) {
			switch(opType){
				case OP_TYPE_ADDTOTAIL:
//					System.out.println("inserting "+v);
					Trans.atomic((Callable<java.lang.Void>) () -> {return l.addFirst((Integer) (int)(maxValue*Math.random()+1));});
					break;
				case OP_TYPE_REMOVE:{
					//System.out.println(Thread.currentThread().getId()+"Removing "+v);
					if(Trans.atomic((Callable<Boolean>) ()-> {return l.remove((Integer) (int)(maxValue*Math.random()+1));})){
              			removes++;
              		}
              		break;
              	}
			}
		}
	}
}


