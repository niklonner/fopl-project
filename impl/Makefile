# I have no idea how to write make files.

test: Player.class BasicParser.class TestMain.class GenericVisitor.class

test: BasicParser.class TestMain.class GenericVisitor.class

TestMain.class: TestMain.java
	javac -cp .:$(CLASSPATH):../bnfc/Swag TestMain.java

BasicParser.class: BasicParser/BasicParser.java
	cp BasicParser/BasicParser.java ../bnfc/Swag/Swag
	javac ../bnfc/Swag/Swag/BasicParser.java

GenericVisitor.class: generic/GenericVisitor.java
	javac -cp .:$(CLASSPATH):../bnfc/Swag generic/GenericVisitor.java

Player.class: generic/Player.java
	javac -cp .:$(CLASSPATH):../bnfc/Swag generic/Player.java


clean:
	rm ../bnfc/Swag/Swag/BasicParser.*
	rm */*.class
	rm *.class
