use application "polytope";
use strict; use warnings; 
use IO::Socket;
use IO::Handle;


my $sock = IO::Socket::INET->new (
                               LocalHost => 'localhost',
                               LocalPort => '57177',
                               Proto => 'tcp',
                               Listen => 1,
                               Reuse => 1,
                              );
die "Couln't open socket: $!" unless $sock;
                              
my $bridge = $sock->accept();
open STDOUT, '>&', $bridge or die "Nope: $!\n";
STDOUT->autoflush(1);

 while(<$bridge>) {
   my($line) = $_;
   chomp($line);
   if ($line eq "__EXIT__") { last; }

   eval($line);
   print "__END__\n";
 }
 close($sock);