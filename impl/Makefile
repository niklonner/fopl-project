LIB = ../lib/:../lib/Cup/java-cup-11a.jar:../lib/Ayatana/jayatana-1.2.4.jar
SWAGDIR = ../bnfc/Swag
PLAYDIR = ../bnfc/Play
CP = .:$(LIB):$(SWAGDIR):$(PLAYDIR)
TEST = ../lib/JUnit/junit.jar:../lib/JUnit/hamcrest.jar:../test

all:  BasicParser TestMain Node SubTournament Tournament Bracket BracketNode util/TournamentParser.class Player util/ReflectionHelper.class util/PlayerParser.class viewer/Viewer.class Main.class

parse: all
	java -cp $(CP) util/TournamentParser ../src/simple_tournament

parsePlayers: all
	java -cp $(CP) util/PlayerParser ../syntax/players


quick: Main.class
	java -cp $(CP) Main

# Compile and run tests
test: ../test/util/ReflectionHelperTest.class ../test/AllTests.class
	java -cp $(CP):$(TEST) org.junit.runner.JUnitCore AllTests

../test/AllTests.class: ../test/util/ReflectionHelperTest.class ../test/AllTests.java
	javac -cp $(CP):$(TEST) ../test/AllTests.java

../test/util/ReflectionHelperTest.class: util/ReflectionHelper.class ../test/util/ReflectionHelperTest.java
	javac -cp $(CP):$(TEST) ../test/util/ReflectionHelperTest.java


#GenericVisitor temprarily removed
Main.class: Main.java viewer/Viewer.class viewer/DisplayPanel.class
	javac -cp $(CP) Main.java

viewer/DisplayPanel.class: viewer/DisplayPanel.java
	javac -cp $(CP) viewer/DisplayPanel.java

viewer/Viewer.class: util/TournamentParser.class viewer/CodeArea.class viewer/Viewer.java
	javac -cp $(CP) viewer/Viewer.java

viewer/CodeArea.class: viewer/CodeArea.java
	javac -cp $(CP) viewer/CodeArea.java

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

BracketNode: generic/BracketNode.java
	javac -cp $(CP) generic/BracketNode.java

util:
	javac -cp $(CP) util/*.java

BasicParser: util/ContextException.class BasicParser/BasicParser.java
	sed 's/<<package>>/Swag/g' BasicParser/BasicParser.java > $(SWAGDIR)/Swag/BasicParser.java
	sed 's/<<package>>/Play/g' BasicParser/BasicParser.java > $(PLAYDIR)/Play/BasicParser.java
	javac -cp $(CP) $(SWAGDIR)/Swag/BasicParser.java
	javac -cp $(CP) $(PLAYDIR)/Play/BasicParser.java

util/ContextException.class: util/ContextException.java
	javac -cp $(CP) util/ContextException.java

GenericVisitor: generic/GenericVisitor.java
	javac -cp $(CP) generic/GenericVisitor.java

Player: generic/Player.java
	javac -cp $(CP) generic/Player.java

util/TournamentParser.class: util/TournamentParser.java
	javac -cp $(CP) util/TournamentParser.java

util/ReflectionHelper.class: util/ReflectionHelper.java
	javac -cp $(CP) util/ReflectionHelper.java

util/PlayerParser.class: util/PlayerParser.java
	javac -cp $(CP) util/PlayerParser.java

clean:
	rm $(SWAGDIR)/Swag/BasicParser.*
	rm */*
	rm *
