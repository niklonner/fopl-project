# I have no idea how to write make files.

SWAGDIR = ../bnfc/Swag

test: Player.class BasicParser.class TestMain.class Node.class

#GenericVisitor.class temprarily removed

test: BasicParser.class TestMain.class 

#GenericVisitor.class

TestMain.class: TestMain.java
	javac -cp .:$(CLASSPATH):$(SWAGDIR) TestMain.java

Node.class: generic/Node.java
	javac -cp .:$(CLASSPATH):$(SWAGDIR) TestMain.java

BasicParser.class: BasicParser/BasicParser.java
	cp BasicParser/BasicParser.java $(SWAGDIR)/Swag
	javac $(SWAGDIR)/Swag/BasicParser.java

GenericVisitor.class: generic/GenericVisitor.java
	javac -cp .:$(CLASSPATH):$(SWAGDIR) generic/GenericVisitor.java

Player.class: generic/Player.java
	javac -cp .:$(CLASSPATH):$(SWAGDIR) generic/Player.java


clean:
	rm $(SWAGDIR)/Swag/BasicParser.*
	rm */*.class
	rm *.class
