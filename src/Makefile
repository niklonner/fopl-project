#settings and variables
LIB = ../lib/:../lib/Cup/java-cup-11a.jar:../lib/Ayatana/jayatana-1.2.4.jar:../lib/Batik/batik.jar
SWAGDIR = ../bnfc/Swag
PLAYDIR = ../bnfc/Play
CP = .:$(LIB):$(SWAGDIR):$(PLAYDIR)
TEST = ../lib/JUnit/junit.jar:../lib/JUnit/hamcrest.jar:../test

#main targets
all: BasicParser util/ContextException.class parse/TournamentParser.class parse/PlayerParser.class \
	model/Node.class model/Tournament.class model/SubTournament.class model/Bracket.class \
	model/BracketNode.class model/Player.class sets/BottomMod.class sets/IdentityMod.class \
	sets/SetModifier.class sets/TopMod.class \
    sets/AttributeCmpMod.class sets/HasAttributeMod.class \
    sets/IntersectMod.class sets/NotIntersectMod.class \
    sets/UnionMod.class \
    sets/Operator.class sets/EqOp.class sets/LtOp.class \
    sets/LeOp.class sets/GtOp.class sets/GeOp.class \
	util/ContextException.class util/ReflectionHelper.class \
	util/Pair.class viewer/DisplayPanel.class viewer/Viewer.class viewer/CodeArea.class viewer/Main.class \
	bwlag

run: all
	java -cp $(CP) viewer/Main

parse: all
	java -cp $(CP) parse/TournamentParser ../src/simple_tournament

parsePlayers: all
	java -cp $(CP) parse/PlayerParser ../syntax/players

quick:
	javac -cp $(CP) ViewGeneratedSVGDemo.java
	java -cp $(CP) ViewGeneratedSVGDemo

#basic parser
BasicParser: $(SWAGDIR)/Swag/BasicParser.class $(PLAYDIR)/Play/BasicParser.class

$(SWAGDIR)/Swag/BasicParser.class: util/ContextException.class BasicParser.java
	sed 's/<<package>>/Swag/g' BasicParser.java > $(SWAGDIR)/Swag/BasicParser.java
	javac -cp $(CP) $(SWAGDIR)/Swag/BasicParser.java

$(PLAYDIR)/Play/BasicParser.class: util/ContextException.class BasicParser.java
	sed 's/<<package>>/Play/g' BasicParser.java > $(PLAYDIR)/Play/BasicParser.java
	javac -cp $(CP) $(PLAYDIR)/Play/BasicParser.java

#viewer
viewer/Main.class: viewer/Viewer.class viewer/Main.java
	javac -cp $(CP) viewer/Main.java

viewer/DisplayPanel.class: model/SubTournament.class viewer/DisplayPanel.java
	javac -cp $(CP) viewer/DisplayPanel.java

viewer/Viewer.class: parse/TournamentParser.class viewer/DisplayPanel.class viewer/CodeArea.class viewer/Viewer.java
	javac -cp $(CP) viewer/Viewer.java

viewer/CodeArea.class: viewer/CodeArea.java
	javac -cp $(CP) viewer/CodeArea.java

#model
model/Node.class: model/Node.java
	javac -cp $(CP) model/Node.java

model/Tournament.class: model/Tournament.java
	javac -cp $(CP) model/Tournament.java

model/SubTournament.class: model/SubTournament.java
	javac -cp $(CP) model/SubTournament.java

model/Bracket.class: model/Bracket.java
	javac -cp $(CP) model/Bracket.java

model/BracketNode.class: model/BracketNode.java
	javac -cp $(CP) model/BracketNode.java

model/Player.class: model/Player.java
	javac -cp $(CP) model/Player.java

#parse
parse/TournamentParser.class: parse/TournamentParser.java
	javac -cp $(CP) parse/TournamentParser.java

parse/PlayerParser.class: parse/PlayerParser.java
	javac -cp $(CP) parse/PlayerParser.java

#sets
sets/BottomMod.class: sets/BottomMod.java
	javac -cp $(CP) sets/BottomMod.java

sets/IdentityMod.class: sets/IdentityMod.java
	javac -cp $(CP) sets/IdentityMod.java

sets/SetModifier.class: sets/SetModifier.java
	javac -cp $(CP) sets/SetModifier.java

sets/TopMod.class: sets/TopMod.java
	javac -cp $(CP) sets/TopMod.java

sets/AttributeCmpMod.class: sets/AttributeCmpMod.java
	javac -cp $(CP) sets/AttributeCmpMod.java

sets/HasAttributeMod.class: sets/HasAttributeMod.java
	javac -cp $(CP) sets/HasAttributeMod.java

sets/IntersectMod.class: sets/IntersectMod.java
	javac -cp $(CP) sets/IntersectMod.java

sets/NotIntersectMod.class: sets/NotIntersectMod.java
	javac -cp $(CP) sets/NotIntersectMod.java

sets/UnionMod.class: sets/UnionMod.java
	javac -cp $(CP) sets/UnionMod.java

sets/Operator.class: sets/Operator.java
	javac -cp $(CP) sets/Operator.java

sets/EqOp.class: sets/EqOp.java
	javac -cp $(CP) sets/EqOp.java
	
sets/LtOp.class: sets/LtOp.java
	javac -cp $(CP) sets/LtOp.java
	
sets/LeOp.class: sets/LeOp.java
	javac -cp $(CP) sets/LeOp.java
	
sets/GtOp.class: sets/GtOp.java
	javac -cp $(CP) sets/GtOp.java
	
sets/GeOp.class: sets/GeOp.java
	javac -cp $(CP) sets/EqOp.java

#util
util/ContextException.class: util/ContextException.java
	javac -cp $(CP) util/ContextException.java

util/ReflectionHelper.class: util/ReflectionHelper.java
	javac -cp $(CP) util/ReflectionHelper.java

util/Pair.class: util/Pair.java
	javac -cp $(CP) util/Pair.java

#tests
test: ../test/util/ReflectionHelperTest.class ../test/AllTests.class
	java -cp $(CP):$(TEST) org.junit.runner.JUnitCore AllTests

../test/AllTests.class: ../test/util/ReflectionHelperTest.class ../test/AllTests.java
	javac -cp $(CP):$(TEST) ../test/AllTests.java

../test/util/ReflectionHelperTest.class: util/ReflectionHelper.class ../test/util/ReflectionHelperTest.java
	javac -cp $(CP):$(TEST) ../test/util/ReflectionHelperTest.java

#bwlag
bwlag: bwlag/model/CmpStandard.class

bwlag/model/CmpStandard.class: bwlag/model/CmpStandard.java
	javac -cp $(CP) bwlag/model/CmpStandard.java

#clean
clean:
	rm $(SWAGDIR)/Swag/BasicParser.*
	rm $(PLAYDIR)/Play/BasicParser.*
	rm */*.class
