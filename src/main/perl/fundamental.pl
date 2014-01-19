use application "polytope";

# Points of the point group
# Test is a cube with fixed first component.
my $points = new Matrix<Rational>([[1,1,1,1],[1,1,1,-1],[1,1,-1,1],[1,-1,1,1],[1,1,-1,-1],[1,-1,1,-1],[1,-1,-1,1],[1,-1,-1,-1]]);

my $v = new VoronoiDiagram(SITES=>$points);

#
# Should be only one positioned at ~(0,0,0) otherwise error
#
print $v->VORONOI_VERTICES;

#
# Prints the the adjacent voronoi cells.
# For the first node
#
my $adj = $v->DUAL_GRAPH->ADJACENCY->adjacent_nodes(0);

#
# Computes the rays of the voronoi cell,
# we assume there are only triangualar fundemantal regions
# at this point
#

# First save the sites
my $s1 = $points->[$adj->[0]];
my $s2 = $points->[$adj->[1]];
my $s3 = $points->[$adj->[2]];

# Compute the points on the edge * 2, because polymake cannot divide a vector by a scalar.
my $p1 = $s1 + $s2;
my $p2 = $s2 + $s3;
my $p3 = $s3 + $s1;

#
# We create a new polytope from these points, such that they are normalized.
# and print them.
#
my $poly = new Polytope(POINTS=>[$p1,$p2,$p3,[2,0,0,0]]);
print "----------\n";
print $poly->VERTICES;
