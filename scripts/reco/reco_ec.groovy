import org.jlab.io.evio.*;
import org.jlab.service.ec.*;
import org.jlab.clas.physics.*;
import org.jlab.clas12.physics.*;


file = args[0];

EvioSource reader = new EvioSource();
reader.open(file);

ECEngine ecEngine = new ECEngine();
ecEngine.init();

int icounter = 0;
while(reader.hasEvent()==true){

     EvioDataEvent event = reader.getNextEvent();
     System.out.println("===============>>>>>> PROCESSING EVENT # " + icounter);
     ecEngine.processDataEvent(event);
     icounter++;
}
