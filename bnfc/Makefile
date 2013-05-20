LIB = ../../lib/:../../lib/Cup/java-cup-11a.jar
TEST = ../../lib/JUnit/junit.jar:../../lib/JUnit/hamcrest.jar:../../test


all: Play Swag

#Play
Play: Play/Makefile
	cd Play; export CLASSPATH=.:$(LIB); make

Play/Makefile: Play/Play.cf
	cd Play; bnfc -m -java Play.cf

#Swag
Swag: Swag/Makefile
	cd Swag; export CLASSPATH=.:$(LIB); make

Swag/Makefile: Swag/Swag.cf
	cd Swag; bnfc -m -java Swag.cf

#Clean
clean:
	cd Swag; export CLASSPATH=.:$(LIB); make distclean
	cd Play; export CLASSPATH=.:$(LIB); make distclean

