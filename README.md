# Moto_XCMP_XNL_master
Motorola XCMP-XNL develop
  Model Number Command
  1.	Request XCMP Command
      0f 00 0b 01 02 00 06 00 1a 00 01 00 03 00 0e 07
  2.	XcmpMsg receive Ack
      00 0c 00 0c 01 02 00 1a 00 06 00 01 00 00
  3.	XcmpMsg use MN.java to receive reply
      00 1d 00 0b 01 07 00 1a 00 06 00 01 00 11 80 0e 00 07 48 35 36 52 44 4e 39 52 41 31 41 4e 00
  4.	MN.java will Pruning 00 1d 00 0b
      01 07 00 1a 00 06 00 01 00 11 80 0e 00 07 (28) 48 35 36 52 44 4e 39 52 41 31 41 4e 00
  5.	Finally use function DataProcess.hexToAscii(28,HexicmalData.length(),HexicmalData.toString());
      receive Model Number
