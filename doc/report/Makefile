TEMPORARIES=*aux *toc *log *blg *bbl *out *lol *lot *lof parts/*aux parts/*log
ARTIFACTS=*pdf *dvi *ps
LATEX_OPTS=-interaction=nonstopmode

all: clean compile removetemporaries

# TODO: Wirklich 3x latex?!
compile:
	pdflatex ${LATEX_OPTS} report.tex
	bibtex report
	pdflatex ${LATEX_OPTS} report.tex
	pdflatex ${LATEX_OPTS} report.tex

pdf: compile removetemporaries
	dvips report.dvi
	ps2pdf report.ps
	rm -f *dvi *ps

clean:
	rm -f $(TEMPORARIES) $(ARTIFACTS)
	
removetemporaries:
	rm -f $(TEMPORARIES)
