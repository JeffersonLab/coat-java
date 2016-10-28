//********************************************************
// DVCS process analyzer
//********************************************************

import org.jlab.physics.process.*;
import org.jlab.physics.base.*;
import org.jlab.clas.physics.*;
//********************************************************
// PLOTTING PACKAGE IMPORTS
//********************************************************
import org.jlab.groot.data.*;
import org.jlab.groot.math.*;
import org.jlab.groot.ui.*;
import org.jlab.groot.fitter.*;

DVCSProcess  dvcs  = new DVCSProcess();
PhaseSpace   space =  dvcs.getPhaseSpace();

H2F h2 = new H2F("h2",100,0.0,1.0,100,1.0,5.0);

for(int i = 0; i < 4000000; i++){
	space.getDimension("Q2").setRandom();
    space.getDimension("xb").setRandom();
    PhysicsEvent  event = dvcs.getEvent(space);

    if(event.count()>0){
    	//System.out.println(" EVENT is fine ");
    	double q2 = dvcs.getProperty("Q2",event);
	  	double xb = dvcs.getProperty("xb",event);
	  	h2.fill(xb,q2);
	  	//System.out.println("q2/xb = " + q2 + " " + xb);
    }
}

TCanvas c1 = new TCanvas("c1",600,600);
c1.draw(h2);
