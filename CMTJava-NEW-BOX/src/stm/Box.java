package stm;

import java.util.*;

public class Box <A> {
	private Conteiner<A> box;
    private FieldInfo<A> boxFieldInfo; 
	


	public Box(A conteudo){
		this.box = new Conteiner (conteudo);
        this.boxFieldInfo = new FieldInfo<A> ("box", (A a) -> {box.setConteudo(a); return null;});
	}
	
	public void setBox (A n) throws Exception {
		
            Trans t = TransLocal.local.get();
			
			boolean mark[] = {false};
			Long version = boxFieldInfo.wlock.get(mark);
		    if(mark[0] && t.transId.equals(version)){
		    	assert(boxFieldInfo.wlock.isMarked() && boxFieldInfo.wlock.getReference().equals(t.transId));
				t.writeSet.put(boxFieldInfo, n);
			}else{
				if(boxFieldInfo.wlock.compareAndSet(null, t.transId, false, true)){
					t.writeSet.put(boxFieldInfo, n);
					if (boxFieldInfo.rlock.getReference() > t.validationStamp && !t.extend()){
                        t.status = Trans.Status.ABORTED;
						throw new AbortedException();
					}
				}
				else{
                    t.status = Trans.Status.ABORTED;
					throw new AbortedException();
				}
			}	
			TransLocal.local.set(t);
				
			
	}


	public A getBox () throws Exception {
			
			Trans t = TransLocal.local.get();
			
			A result;
			boolean mark[] = {false};
			Long version = boxFieldInfo.wlock.get(mark);
			
			if(mark[0] && t.transId.equals(version)){
				assert(boxFieldInfo.wlock.isMarked() && boxFieldInfo.wlock.getReference().equals(t.transId));
				result = (A)t.writeSet.get(boxFieldInfo);
				//t.status = Trans.Status.ACTIVE;
			}else{
				version = boxFieldInfo.rlock.get(mark);
				while(true){
					if(mark[0]==true){
						version = boxFieldInfo.rlock.get(mark);
						continue;
					}

					result = box.getConteudo();
					long version2 = boxFieldInfo.rlock.get(mark);
					if(version == version2 && mark[0]==false) break;
					version=version2;
				}

				boolean added = t.readSet.put(boxFieldInfo, version);
				if (!added || (version > t.validationStamp && !t.extend()) ){
					t.status = Trans.Status.ABORTED;
					throw new AbortedException();
				}/* else{
					t.status = Trans.Status.ACTIVE;
				} */
			}
			/* TransLocal.local.set(t); */
			return result;
	}

}