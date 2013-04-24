all:
	happy -gca ParSwag.y
	alex -g LexSwag.x
	latex DocSwag.tex
	dvips DocSwag.dvi -o DocSwag.ps
	ghc --make TestSwag.hs -o TestSwag
clean:
	 rm -f *.log *.aux *.hi *.o *.dvi DocSwag.ps *.o
distclean: clean
	 rm -f DocSwag.* LexSwag.* ParSwag.* ParSwagData.* LayoutSwag.* SkelSwag.* PrintSwag.* TestSwag.* AbsSwag.* TestSwag ErrM.* SharedString.* Swag.dtd XMLSwag.* Makefile*
