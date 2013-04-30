# I have no idea how to write make files.

SWAGDIR = ../bnfc/Swag

test: Player BasicParser TestMain Node SubTournament Tournament Bracket

#GenericVisitor temprarily removed

TestMain: TestMain.java
	javac -cp .:$PATH):$(SWAGDIR) TestMain.java

Node: generic/Node.java
	javac -cp .:$PATH):$(SWAGDIR) generic/Node.java

Tournament: generic/Tournament.java
	javac -cp .:$PATH):$(SWAGDIR) generic/Tournament.java

SubTournament: generic/SubTournament.java
	javac -cp .:$PATH):$(SWAGDIR) generic/SubTournament.java

Bracket: generic/Bracket.java
	javac -cp .:$PATH):$(SWAGDIR) generic/Bracket.java

BasicParser: BasicParser/BasicParser.java
	cp BasicParser/BasicParser.java $(SWAGDIR)/Swag
	javac -cp $PATH):$(SWAGDIR) $(SWAGDIR)/Swag/BasicParser.java

GenericVisitor: generic/GenericVisitor.java
	javac -cp .:$PATH):$(SWAGDIR) generic/GenericVisitor.java

Player: generic/Player.java
	javac -cp .:$PATH):$(SWAGDIR) generic/Player.java


clean:
	rm $(SWAGDIR)/Swag/BasicParser.*
	rm */*
	rm *
