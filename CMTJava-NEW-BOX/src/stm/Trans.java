package stm;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class Trans{
	
	public enum Status {
		ACTIVE, ABORTED, RETRY
	};

	public long validationStamp;
	public WriteSet writeSet;
	public ReadSet readSet;
	public Long transId;
	public Status status;

	
	public Trans(){

		validationStamp = VersionClock.getReadStamp();
		writeSet = new WriteSet();
		readSet = new ReadSet();
		transId = Thread.currentThread().getId();
		status = Status.ACTIVE;
	}

	private boolean validate(){
		
		for (Map.Entry<FieldInfo, Long> entry : readSet.log.entrySet()) {
			FieldInfo fieldInfo = entry.getKey();
			Long entryVersion = entry.getValue();

			boolean mark[] = {false};
			Long version = (Long)fieldInfo.rlock.get(mark);

			if(!version.equals(entryVersion)){
				}
				if(!mark[0] || !version.equals(transId)){			
					return false;
			}
		}
		return true;
	}

	public void rollback(){

		for (FieldInfo f : writeSet.log.keySet()) {
			if(!f.wlock.compareAndSet(transId, null, true, false)){
				System.out.println(transId+" rollback: Error releasing wlock: isMarked "+f.wlock.isMarked() 
				+" reference " +f.wlock.getReference() );
				System.exit(0);
			}
		}
	}

	private void releaseReadLocks(){

		for (FieldInfo fieldInfo : writeSet.log.keySet()) {
			boolean mark[] = {false};
			Long version = (Long)fieldInfo.rlock.get(mark);
			if(mark[0] && transId.equals(version)){
				Long stamp = readSet.get(fieldInfo);
				if(stamp == null){
					stamp = validationStamp;
				}
				if(!fieldInfo.rlock.compareAndSet(transId,stamp, true, false)){
					System.out.println(transId+" releaseReadLocks: Error realeasing rlock: isMarked "+fieldInfo.rlock.isMarked() 
					+" reference " +fieldInfo.rlock.getReference() );
					System.exit(0);
				}
			}
		}
	}

	public boolean extend(){

		long globalTimestamp = VersionClock.getReadStamp();
		if(validate()){
			validationStamp = globalTimestamp;
			return true;
		}
		return false;
	}

	public void retry(){
		Block block = new Block();
		boolean errorOnValidation = false;

		if(!validate()){
			errorOnValidation=true;
		}else{
			for (FieldInfo fieldInfo : readSet.log.keySet()){//acquire wlock for each entry in read set
				boolean mark[] = {false};
				Long version = (Long)fieldInfo.wlock.get(mark);
				if(!mark[0] || !transId.equals(version)){
					if(!fieldInfo.wlock.compareAndSet(null, transId, false, true)){
						errorOnValidation=true;
						break;
					}
				}
			}
		}

		if(!errorOnValidation){
			for (FieldInfo f : readSet.log.keySet()) {
				f.addBlockedThread(block);
			}
		}

		for(FieldInfo fieldInfo : readSet.log.keySet()){
			boolean mark[] = {false};
			Long version = (Long)fieldInfo.wlock.get(mark);
			if(mark[0] && transId.equals(version)){//release wlocks acquired by t
				if(!fieldInfo.wlock.compareAndSet(transId, null, true, false)){
					System.out.println(transId+" retry: Error releasing wlock "+fieldInfo.wlock.isMarked() 
							+" reference " +fieldInfo.wlock.getReference()+ " "+ fieldInfo.name);
					System.exit(0);
				}
			}
		}

		if(!errorOnValidation){
			block.block();
		}
	}
	
	public <A> boolean commit(){

		if(writeSet.log.size()==0){
			return true;//read only-transaction
		}	

		//acquires read locks
		for(FieldInfo field : writeSet.log.keySet()){
			Long ref = (Long)field.rlock.getReference();
			if(!field.rlock.compareAndSet(ref,transId,false, true)){
				releaseReadLocks();
				rollback();
				return false;
			}
		}
		/*
		for (FieldInfo field : writeSet.log.keySet()) {
			assert(field.rlock.isMarked() && field.rlock.getReference().equals(transId));
			assert(field.wlock.isMarked() && field.wlock.getReference().equals(transId));
		}*/

		Long writeVersion = VersionClock.getWriteStamp();

		if(writeVersion > validationStamp + 1){//some transaction has commited since Trans start
			if (!validate()) {
				releaseReadLocks();
				rollback();
				return false;
			}
		}

		for(Map.Entry entry : writeSet){

			FieldInfo<A> key = (FieldInfo<A>) entry.getKey();
			A newvalue = (A)entry.getValue();
			key.updateField.apply(newvalue);

			if(!key.rlock.compareAndSet(transId, writeVersion, true, false)){
				System.out.println(transId+" commit: Error releasing rlock: isMarked "+key.rlock.isMarked() 
				+" reference " +key.rlock.getReference() );
				System.exit(0);
			}

			if(!key.wlock.compareAndSet(transId, null, true, false)){
				System.out.println(transId+" commit: Error releasing wlock: isMarked "+key.rlock.isMarked() 
				+" reference " +key.rlock.getReference() );
				System.exit(0);
			}
		}

		for(FieldInfo field : writeSet.log.keySet()){
			field.wakeupBlockedThreads();
		}
		return true;
	}

	public static <A> A atomic(Callable<A> a){

		while(true){

			try{
				Trans t = new Trans();
				TransLocal.local.set(t);
				A result = a.call();
				if (t.commit()) {							
					return result;
				}
			}catch(RetryException e){
				Trans t = TransLocal.local.get();
				t.retry();	
			}catch(AbortedException e){
				Trans t = TransLocal.local.get();
				t.rollback();
			}
			catch(Exception e) {
				System.out.println(Thread.currentThread().getId() + " Exception "+e);
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
}
