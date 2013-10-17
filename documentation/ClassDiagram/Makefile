tags:
	exctags --fields=fkstS ../../services/src/edu/cs673/plm/model/*.java ../../services/src/edu/cs673/plm/*.java; perl parseJava.pl | sed 's/\\\\ }/}/g' > service_classes.tex ; rm tags
pdf:
	pdflatex *.tex
clean:
	rm *.aux *.log
