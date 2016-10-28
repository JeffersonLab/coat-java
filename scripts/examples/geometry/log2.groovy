//---------------------------------

int number = Integer.parseInt(args[0]);

int order  = (int) (Math.log(65536)/Math.log(2));
int nbits  = Integer.SIZE - Integer.numberOfLeadingZeros(number);
System.out.println("number = " + number + "  order = " + order + " nbits = " + nbits); 
