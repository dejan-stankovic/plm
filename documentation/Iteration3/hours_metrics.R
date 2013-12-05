#!/usr/local/bin/R

library(ggplot2)
library(reshape2)

df <- data.frame(week=1:12,
		Manav=c(7,8,14,11,13,10,17,8,12,10,8,10),
		Vipul=c(6,10,11,13,14,10,10,8,11,10,4,14),
		Yuvaraj=c(5,13,17,15,27,6,6,6,10,2,7,18),
		Rachit=c(5,8,15,13,14,9,14,8,14,2,2,10),
		Alan=c(3,10,9,14,13,3,6,7,3,2,0,15),
		Christian=c(12,21,28,25,31,10,14,26,3,7,7,14),
		Tandhy=c(7,8,7,12,20,10,8,9,9,7,6,19))

df <- melt(df, id.vars='week')

g <- ggplot(df, aes(week, value, group=variable)) +
geom_path(aes(colour=variable)) +
labs(title="Project Contributions") +
xlab("Week") +
ylab("Hours of Work")
#g

h <- g + facet_wrap(~ variable, ncol=2)
#h

bars <- qplot(week, value, data=df, fill=factor(variable), geom="bar", stat="identity") +
labs(title="Project Contributions") +
xlab("Week") +
ylab("Hours of Work")
bars
