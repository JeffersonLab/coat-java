//***************************************************************
// This script illustrates how to work with fastMC with EVENT
// A Physics Event is passed to fastMC, and it returns a 
// reconstructed event with particles that were accepted.
//***************************************************************
import org.jlab.physics.io.*;
import org.jlab.clas.physics.*;
import org.jlab.clas.fastmc.*;
import org.jlab.detector.base.*;
import org.jlab.geom.base.*;

filename = args[0];

LundReader    reader = new LundReader(filename);
Clas12FastMC  fastMC = new Clas12FastMC(-1.0,1.0);

// Load EC and DC detectord from GeometryFactory
Detector      detDC  = GeometryFactory.getDetector(DetectorType.DC);
Detector      detEC  = GeometryFactory.getDetector(DetectorType.EC);

// set verbose mode for debugging default is 0, no printouts
fastMC.setDebugMode(1);
// Add detectord to fast MC with given names
fastMC.addDetector("DC",detDC); 
fastMC.addDetector("EC",detEC);

String[] ddneg = ["DC","EC"]; // required detectors for negative particle identification
int[]    dhneg = [36,9];      // required hits in each detector

String[] ddneutral = ["EC"]; // required detector for neutral identification
int[]    dhneutral = [9];    // required hits

// add detector filters. multiple filters can be added for particular charge
// for example to also detect negative particles in BST, use
// String[] bstNEG  = ["BST"];
// int[]    hitsNEG = [3]; // require three layers to be hit
// fastMC.addFilter(-1, bstNEG, hitsNEG);

fastMC.addFilter(-1,ddneg,dhneg);
fastMC.addFilter( 1,ddneg,dhneg);
fastMC.addFilter( 0,ddneutral,dhneutral);

fastMC.show();

int icounter = 0;

while(reader.next()){
	
	PhysicsEvent physEvent = reader.getEvent();
	PhysicsEvent recEvent  = fastMC.getEvent(physEvent);
	System.out.println("=======================>>>>>> EVENT # " + icounter);
	System.out.println(physEvent.toLundString());
	System.out.println( recEvent.toLundString());

	icounter++;	
}
