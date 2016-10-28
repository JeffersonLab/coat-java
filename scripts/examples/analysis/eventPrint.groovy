import org.jlab.io.evio.*;
import org.jlab.io.hipo.*;
import org.jlab.clas.physics.*;
import org.jlab.clas12.physics.*;

import org.jlab.groot.data.H1F;
import org.jlab.groot.ui.*;
import org.jlab.groot.base.*;

file = args[0];

//EvioSource reader = new EvioSource();
HipoDataSource reader = new HipoDataSource();
reader.open(file);
// create new kinematic fitter, with beam energy 11.0 GeV and electron filter
GenericKinematicFitter fitter = new GenericKinematicFitter(11.0);

while(reader.hasEvent()==true){

     EvioDataEvent event = reader.getNextEvent();
     RecEvent  recEvent  = fitter.getRecEvent(event);
     //System.out.println(recEvent.toLundString());
     System.out.println("=====================================");     
     recEvent.doPidMatch();
     /*Particle p = recEvent.getReconstructed().getParticle("[211]");
     if(p.vector().p()>0.1&&p.vector().p()<1.2&&p.vector().theta()*57.29<15.0)
     */
     Particle p = recEvent.getReconstructed().getParticle("[2212]");
     Particle e = recEvent.getReconstructed().getParticle("[11]");
     if(e.vector().p()>0.1&&p.vector().p()>0.1&&p.vector().theta()*57.29>45.0){ 
     	System.out.println(recEvent);
     }
}
