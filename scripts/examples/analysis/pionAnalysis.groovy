import org.jlab.io.evio.*;
import org.jlab.clas.physics.*;
import org.jlab.clas12.physics.*;


file = args[0];

EvioSource reader = new EvioSource();
reader.open(file);
// create new kinematic fitter, with beam energy 11.0 GeV and electron filter

GenericKinematicFitter fitter = new GenericKinematicFitter(11.0);
ParticleFinder         pFinder = new ParticleFinder();

while(reader.hasEvent()==true){

     EvioDataEvent event = reader.getNextEvent();
     PhysicsEvent  recEvent  = fitter.getPhysicsEvent(event);
     //System.out.println(recEvent.toLundString());
     Particle pion = pFinder.getPion(recEvent);
     if(pion!=null){
     	System.out.println(pion.vector().mass());
     }
//     PhysicsEvent  genEvent  = fitter.getGeneratedEvent(event);
	
}
