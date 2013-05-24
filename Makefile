all:
	cd bnfc; make
	cd src; make

run: all
	cd src; make run

clean:
	cd bnfc; make clean
	cd src; make clean
