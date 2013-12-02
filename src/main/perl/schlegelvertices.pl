use application "polytope";

my $foo = "";

for my $str (@ARGV) {
 $foo = $foo.$str."\n";
}
$foo = substr($foo, 0, -1);
###print $foo."\n";

my $mat=new Matrix<Rational>(<<_END_);
$foo
_END_

##print $mat->(0,0);
##print "-----";
##print "\n";
my $p = new Polytope(POINTS=>$mat);

my $schlegel = $p->SCHLEGEL_DIAGRAM;
print $schlegel->VERTICES;
