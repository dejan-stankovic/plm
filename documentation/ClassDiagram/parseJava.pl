#!/usr/bin/perl
use Parse::ExuberantCTags;

my $parser = Parse::ExuberantCTags->new( 'tags' );

my $class_list = {};

my $this_class;
my $this_kind;
my $tag = $parser->firstTag();
while(defined $tag){
	$this_kind=$tag->{kind};
	$this_class=$tag->{extension}{class};
	if($this_kind eq "f"){ # exctags gets them backwards
		$class_list->{$this_class}{mem}{$tag->{name}}=1;
	}
	if($this_kind eq "m"){
		$class_list->{$this_class}{fun}{$tag->{name}}=$tag->{extension}->{signature};
		#print STDERR $class_list->{$this_class}{fun}{$tag->{name}};
		#print STDERR "\n";
		#for my $sk ($tag->{extension}->{signature}){
			#$class_list->{$this_class}{fun}{$tag->{name}}="$sk";
		#}
	}

	$tag = $parser->nextTag();
}

print "\\documentclass[tikz]{standalone}
%\\usepackage{tikzscale}
%\\pgfdeclarelayer{background,foreground}
%\\pgfsetlayers{background,main,foreground}
\\usepackage{tikz-uml}

\\begin{document}
\\begin{tikzpicture}
";

for my $ck (keys $class_list){
	next if($ck eq "");
	print "\\umlclass[x=0,y=0]{$ck}{";
	for my $mk (keys %{$class_list->{$ck}{mem}}){
		$mk=~s/_/\\_/g;
		print "$mk \\\\ ";
	}
	print "}{";
	for my $fk (keys %{$class_list->{$ck}{fun}}){
		print "$fk$class_list->{$ck}{fun}{$fk} \\\\ ";
	}
	print "}\n";

}

open LINK,"<class_links.txt";
while(<LINK>){
	print "$_";
}
close LINK;

print "\\end{tikzpicture}
\\end{document}";
