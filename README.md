# netmemo

Provides useful tips for Network & System administrators, to learners and also to curious folks :)
NetMemo uses a library called "jnetparse" and to packqge NetMemo with all its dependencies within 
a single JAR I used an open source product called "OneJar" (see: http://one-jar.sourceforge.net/).

To rebuild NetMemo (on Windows):
 - Install and launch Git Bash
 - Clone NetMemo by issuing : git clone https://www.github.com/cybconv/netmemo
 - Then issue the following commands in sequence: 
    cd netmemo
    ./make.bat
    ./deploy.bat
 - Then, to run NetMemo: 
    java -jar netmemo.jar

    or you can double-click on netmemo.jar from your windows explorer.


Current NetMemo featues are:
- Cisco troubleshooting tips on:
  + IPv4/IPv6 Routing: BGP, OSPF, IS-IS and EIGRP
	+ QoS & performances issues
	+ MPLS
	+ Multicasting
	+ IP services
	+ Switching
	+ ...

- QoS:
	+ mapping between DSCP & ToS values
	+ mapping between DSCP & CoS values
	+ mapping between DSCP & IP Precedence values

- IPv4/IPv6 addresses calculator
  + IPv6:
	  * build different addresses
		  # link-local
		  # multicast
		  # EUI-64
		  # tunnels:
			  | ISATAP
			  | automatic 6to4
			  | IPv4-mapped IPv6 addresses
			  | automatic ipv4-compatible

- IP/MAC Multicast (MC) addresses mapping: 
	+ compute MultiCast MAC addresse from a MC IP address
	+ given a MC IP address => show MC IP addresses that might cause MAC addresses collision

- Security:
  + generate pseudo-random passwords

- Helpers features to view networking configuration of your host



Enjoy :-)
