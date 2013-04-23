# I have no idea how to write make files.

test: BasicParser.class TestMain.class GenericVisitor.class

TestMain.class: TestMain.java
	javac -cp .:$(CLASSPATH):../bnfc TestMain.java

BasicParser.class: BasicParser/BasicParser.java
	cp BasicParser/BasicParser.java ../bnfc/Swag
	javac ../bnfc/Swag/BasicParser.java

GenericVisitor.class: generic/GenericVisitor.java
	javac -cp .:$(CLASSPATH):../bnfc generic/GenericVisitor.java

clean:
	rm ../bnfc/Swag/BasicParser.*
	rm */*.class
	rm *.class
