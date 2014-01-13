use application "polytope";

# Points of the point group
my $points = new Matrix<Rational>([[1,-1,-1],[1,1,-1],[1,-1,1],[1,1,1],[1,0,0]]);

my $v = new VoronoiDiagram(SITES=>$points);

# Selects the first facet of the voronoi diagram
my $vert = $v->VERTICES_IN_FACETS->[0];

# Prints all Vertices in the first facet
for ($count = 0; $count < $vert->size; $count++){
   print $v->VERTICES->[$vert[$count]];
}
