lib = ../../lib/:../../lib/Cup/java-cup-11a.jar
impl = ../../impl


all: Play Play/Play/PlayerParser.class Swag

Play: Play/Makefile
	cd Play; export CLASSPATH=$(lib); make

Play/Makefile: Play/Play.cf
	cd Play; bnfc -m -java Play.cf

Play/Play/PlayerParser.class: Play/Play/PlayerParser.java
	cd Play; javac -cp .:$(lib):$(impl) Play/PlayerParser.java

Swag: Swag/Makefile
	cd Swag; export CLASSPATH=$(lib); make

Swag/Makefile: Swag/Swag.cf
	cd Swag; bnfc -m -java Swag.cf
