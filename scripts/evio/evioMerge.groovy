import org.jlab.io.evio.*;


String outputFile  = args[0];

List<String>  inputFiles = new ArrayList<String>();

for(int i = 1; i < args.length; i++){
	inputFiles.add(args[i]);
}

EvioDataSync  writer = new EvioDataSync();
writer.open(outputFile);

for(int nf = 0; nf < inputFiles.size(); nf++){
	EvioSource reader = new EvioSource();
	reader.open(inputFiles.get(nf));
	int counter = 0;

	while(reader.hasEvent()){
   		EvioDataEvent event = reader.getNextEvent();
   		writer.writeEvent(event);
   		counter++;
   	}
   //event.show(); // print out all banks in the event
}

writer.close();
