# I have no idea how to write make files.

lib = ../lib/:../lib/Cup/java-cup-11a.jar
CP = .:$(lib):$(SWAGDIR)
SWAGDIR = ../bnfc/Swag

test: Player BasicParser TestMain Node SubTournament Tournament Bracket

#GenericVisitor temprarily removed

TestMain: TestMain.java
	javac -cp $(CP) TestMain.java

Node: generic/Node.java
	javac -cp $(CP) generic/Node.java

Tournament: generic/Tournament.java
	javac -cp $(CP) generic/Tournament.java

SubTournament: generic/SubTournament.java
	javac -cp $(CP) generic/SubTournament.java

Bracket: generic/Bracket.java
	javac -cp $(CP) generic/Bracket.java

BasicParser: BasicParser/BasicParser.java
	cp BasicParser/BasicParser.java $(SWAGDIR)/Swag/
	javac -cp $(CP) $(SWAGDIR)/Swag/BasicParser.java

GenericVisitor: generic/GenericVisitor.java
	javac -cp $(CP) generic/GenericVisitor.java

Player: generic/Player.java
	javac -cp $(CP) generic/Player.java


clean:
	rm $(SWAGDIR)/Swag/BasicParser.*
	rm */*
	rm *
