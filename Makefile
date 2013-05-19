all:
	cd bnfc; make
	cd src; make

run: all
	cd src; make run
