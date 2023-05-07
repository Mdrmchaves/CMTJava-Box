for i in $(seq 1 30)
do 
	res=`java -cp ./build test.LinkedBlockingQueue.Main 2 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/Linke_2.txt`
done

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.LinkedBlockingQueue.Main 4 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/Linke_4.txt`
done

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.LinkedBlockingQueue.Main 8 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/Linke_8.txt`
done

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.LinkedBlockingQueue.Main 12 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/Linke_12.txt`
done






for i in $(seq 1 30)
do 
	res=`java -cp ./build test.Account.Test 1 4000 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/Account_2.txt`
done

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.Account.Test 2 4000 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/Account_4.txt`
done

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.Account.Test 4 4000 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/Account_8.txt`
done

for i in $(seq 1 30)
do 
	res=`java -cp ./build test.Account.Test 6 4000 >> /mnt/d/mdrmc/Documents/TCC/CMTJava-Compiler/Resultados/Account_12.txt`
done

echo 'fim'