make -C src/stm/

make -C src/test/AccountNew/

make -C src/test/LinkedBlockingQueueNew/

java -cp ./build test.LinkedBlockingQueueNew.Main 2

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.LinkedBlockingQueueNew.Main 2 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/LinkeNew_2.txt`
done

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.LinkedBlockingQueueNew.Main 4 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/LinkeNew_4.txt`
done

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.LinkedBlockingQueueNew.Main 8 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/LinkeNew_8.txt`
done

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.LinkedBlockingQueueNew.Main 12 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/LinkeNew_16.txt`
done

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.AccountNew.Test 1 4000 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/AccountNew_2.txt`
done

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.AccountNew.Test 2 4000 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/AccountNew_4.txt`
done

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.AccountNew.Test 4 4000 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/AccountNew_8.txt`
done

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.AccountNew.Test 6 4000 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/AccountNew_16.txt`
done