import org.jlab.io.evio.*;


String evtstring  = args[0];
String filename   = args[1];

int nevents = Integer.parseInt(evtstring);

EvioSource reader = new EvioSource();
reader.open(filename);
EvioDataSync writer = new EvioDataSync();
writer.open("output.evio");

int counter = 0;

while(reader.hasEvent()){
   EvioDataEvent event = reader.getNextEvent();
   writer.writeEvent(event);
   counter++;
   if(counter>nevents) break;
   //event.show(); // print out all banks in the event
}

writer.close();