CS471-prodcons
==============

Producer-consumer multithreading problem solved in Java

To run this program, compile into a runnable java program and copy it to your 
Windows desktop. Open the command prompt and browse to the folder. To make sure 
Java is in your path, run the following command: 

set path=C:\Program Files\Java\jre7\bin 
(This may be different depending on the installed version of Java.)

The program is invoked at the line command with two arguments. The first is
the number of producers you want to set; the second is the number of consumers.
So to run it for 3 producers and 5 consumers, enter:

>java -jar prodcons.jar 3 5

To output the results to a file, use the > redirect like this:

>java -jar prodcons.jar 3 5 > 3and5.txt

The program's producer will create 10,000 transactions, and the shared buffer size 
will be 10.
