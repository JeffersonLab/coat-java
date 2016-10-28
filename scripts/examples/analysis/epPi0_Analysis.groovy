import org.jlab.io.evio.*;
import org.jlab.io.hipo.*;
import org.jlab.clas.physics.*;
import org.jlab.clas12.physics.*;

import org.jlab.groot.data.H1F;
import org.jlab.groot.ui.*;
import org.jlab.groot.base.*;


GStyle.getAxisAttributesX().setTitleFontSize(24);
GStyle.getAxisAttributesX().setLabelFontSize(18);
GStyle.getAxisAttributesY().setTitleFontSize(24);
GStyle.getAxisAttributesY().setLabelFontSize(18);

file = args[0];

//EvioSource reader = new EvioSource();
HipoDataSource reader = new HipoDataSource();
reader.open(file);
// create new kinematic fitter, with beam energy 11.0 GeV and electron filter

GenericKinematicFitter fitter = new GenericKinematicFitter(11.0);

EventFilter  filterE   = new EventFilter("11:22:22:X+:X-");
EventFilter  filterEp  = new EventFilter("11:2212:22:22");
EventFilter  filterGG  = new EventFilter("22:22:X+:X-");
EventFilter  filterEpX = new EventFilter("11:2212:X+:X-:Xn");

//EventFilter  filter3 = new EventFilter("11:2212:X+:X-:Xn");

H1F h1   = new H1F("h1","Pion Mass",80,-0.05,0.6);
H1F h2   = new H1F("h2","Pion Mass",80,-0.05,0.6);
H1F h2mx = new H1F("h2mx","Pion Mass",80,0.005,0.8);
H1F h3   = new H1F("h2","Pion Mass",80,-0.05,0.6);
H1F h3mx = new H1F("h3mx","Pion Mass",80,-0.05,3.5);


H1F h11 = new H1F("h11","Missing Energy",100,-0.5,0.5);
H1F h21 = new H1F("h11","Missing Energy",100,-0.5,0.5);
H1F h31 = new H1F("h11","Missing Energy",100,-0.5,0.5);

while(reader.hasEvent()==true){

     EvioDataEvent event = reader.getNextEvent();
     RecEvent  recEvent  = fitter.getRecEvent(event);
     //System.out.println(recEvent.toLundString());
     //System.out.println("=====================================");
     	 //recEvent.getMatched(3);
     recEvent.doPidMatch();

     if(filterE.isValid(recEvent.getReconstructed())==true){
        Particle pi0 = recEvent.getReconstructed().getParticle("[22,0]+[22,1]");
        Particle proton = recEvent.getReconstructed().getParticle("[b]+[t]-[11]-[22,0]-[22,1]");
        h1.fill(pi0.mass());
        double mass = pi0.mass();
        if(Math.abs(mass-0.134)<0.015){
            h3mx.fill(proton.mass);
        }
    }
    if(filterEp.isValid(recEvent.getReconstructed())==true){
        Particle pi0   = recEvent.getReconstructed().getParticle("[22,0]+[22,1]");
        Particle pi0mx = recEvent.getReconstructed().getParticle("[b]+[t]-[11]-[2212]");
        h2.fill(pi0.mass());    
    }

    if(filterEpX.isValid(recEvent.getReconstructed())==true){
        Particle pi0mx = recEvent.getReconstructed().getParticle("[b]+[t]-[11]-[2212]");
        Particle proton = recEvent.getGenerated().getParticle("[2212]");
        if(proton.theta()*57.29>45.0&&proton.vector().p()>0.8){
            h2mx.fill(pi0mx.mass());
            //System.out.println(recEvent);
        }
        //System.out.println(recEvent);
    }

    if(filterGG.isValid(recEvent.getReconstructed())==true){
        Particle pi0 = recEvent.getReconstructed().getParticle("[22,0]+[22,1]");
        h3.fill(pi0.mass());
    }
}

h1.setOptStat(111111);
h2.setOptStat(111111);
h3mx.setOptStat(111111);
h3.setOptStat(111111);

h1.setTitleX("M (#gamma #gamma) [GeV]");
h2.setTitleX("M (#gamma #gamma) [GeV]");
h3.setTitleX("M (#gamma #gamma) [GeV]");
h3mx.setTitleX("Mx (e#pi^0) [GeV]");

h1.setTitle("ep #rarrow ep#pi^+#pi^-");
h2.setTitle("ep #rarrow ep#pi^+#pi^-");
h3.setTitle("ep #rarrow ep#pi^+#pi^-");

h1.setLineWidth(2);
h2.setLineWidth(2);
h3.setLineWidth(2);

h1.setFillColor(44);
h2.setFillColor(45);
h3.setFillColor(43);
h3mx.setFillColor(42);

TCanvas canvas = new TCanvas("canvas",1400,1400);
canvas.divide(3,2);
canvas.cd(0);
canvas.draw(h1);
canvas.cd(1);
canvas.draw(h2);
canvas.cd(2);
canvas.draw(h3mx);
canvas.cd(3);
canvas.draw(h3);
canvas.cd(4);
canvas.divide(1,1);
canvas.draw(h2mx);
