LIB = ../../lib/:../../lib/Cup/java-cup-11a.jar
IMPL = ../../impl
TEST = ../../lib/JUnit/junit.jar:../../lib/JUnit/hamcrest.jar:../../test

all: Play Play/Play/PlayerParser.class Swag

Play: Play/Makefile
	cd Play; export CLASSPATH=.:$(LIB); make

Play/Makefile: Play/Play.cf
	cd Play; bnfc -m -java Play.cf

Play/Play/PlayerParser.class: Play/Play/PlayerParser.java
	cd Play; javac -cp .:$(lib):$(impl) Play/PlayerParser.java

Swag: Swag/Makefile
	cd Swag; export CLASSPATH=.:$(LIB); make

Swag/Makefile: Swag/Swag.cf
	cd Swag; bnfc -m -java Swag.cf
