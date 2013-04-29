# I have no idea how to write make files.

SWAGDIR = ../bnfc/Swag/Swag

test: Player.class BasicParser.class TestMain.class 

#GenericVisitor.class temprarily removed

test: BasicParser.class TestMain.class 

#GenericVisitor.class

TestMain.class: TestMain.java
	javac -cp .:$(CLASSPATH):$(SWAGDIR) TestMain.java

BasicParser.class: BasicParser/BasicParser.java
	cp BasicParser/BasicParser.java $(SWAGDIR)
	javac $(SWAGDIR)/BasicParser.java

GenericVisitor.class: generic/GenericVisitor.java
	javac -cp .:$(CLASSPATH):$(SWAGDIR) generic/GenericVisitor.java

Player.class: generic/Player.java
	javac -cp .:$(CLASSPATH):$(SWAGDIR) generic/Player.java


clean:
	rm ../bnfc/Swag/Swag/BasicParser.*
	rm */*.class
	rm *.class
