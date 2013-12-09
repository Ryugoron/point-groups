use application "polytope";
use strict; use warnings; 
use IO::Socket;
use IO::Handle;

STDOUT->autoflush(1);
my $sock = IO::Socket::INET->new (
                               LocalHost => 'localhost',
                               LocalPort => '0',
                               Proto => 'tcp',
                               Listen => 1,
                               Reuse => 1,
                              );
print $sock->sockport();
die "Polymake: Couln't open socket. $!" unless $sock;
                              
my $bridge = $sock->accept();
open STDOUT, '>&', $bridge or die "Polymake: Could not redirect STDOUT. $!\n";
STDOUT->autoflush(1);

 while(<$bridge>) {
   my($line) = $_;
   chomp($line);
   if ($line eq "__EXIT__") { last; }

   eval($line);
   print "__END__\n";
 }
 close($sock);
