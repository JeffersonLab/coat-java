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
EventFilter  filter  = new EventFilter("11:2212:-211:X+:X-:Xn");
EventFilter  filter2 = new EventFilter("11:2212:211:X+:X-:Xn");

EventFilter  filter3 = new EventFilter("-211:211:X+:X-:Xn");
//EventFilter  filter3 = new EventFilter("11:2212:X+:X-:Xn");

H1F h1 = new H1F("h1","Pion Mass",100,-0.05,0.8);
H1F h2 = new H1F("h2","Pion Mass",100,-0.05,0.8);
H1F h3 = new H1F("h3","Pion Mass",60,0.3,2.0);

H1F h11 = new H1F("h11","Missing Energy",100,-0.5,0.5);
H1F h21 = new H1F("h11","Missing Energy",100,-0.5,0.5);
H1F h31 = new H1F("h11","Missing Energy",100,-0.5,0.5);


H1F h4gen = new H1F("h4gen","Pion Mass",100,0.05,0.2);
H1F h4rec = new H1F("h4rec","Pion Mass",60,-0.05,0.85);
H1F h4rec2 = new H1F("h4rec2","Pion Mass",100,-0.05,0.85);

while(reader.hasEvent()==true){

     EvioDataEvent event = reader.getNextEvent();
     RecEvent  recEvent  = fitter.getRecEvent(event);
     //System.out.println(recEvent.toLundString());
     //System.out.println("=====================================");
     	 //recEvent.getMatched(3);
     recEvent.doPidMatch();

     Particle  piplus_gen = recEvent.getGenerated().getParticle("[b]+[t]-[11]-[2212]-[-211]");
     h4gen.fill(piplus_gen.vector().mass());
     double pp_mass = piplus_gen.vector().mass();
     if(pp_mass>0.135&&pp_mass<0.145){
        if(filter.isValid(recEvent.getReconstructed())==true){
            Particle p1_p  = recEvent.getReconstructed().getParticle("[2212]");
            Particle p1_pm = recEvent.getReconstructed().getParticle("[-211]");
            Particle p1_mx = recEvent.getReconstructed().getParticle("[b]+[t]-[11]-[2212]-[-211]");
            	     if(p1_p.vector().p()>0.9&&p1_pm.vector().p()>0.9&&
            p1_p.vector().theta()*57.29<25.0&&p1_pm.vector().theta()*57.29<25.0){
                h4rec.fill(p1_mx.vector().mass());
                h4rec2.fill(p1_mx.vector().mass2());
            }                        
        }
     }
     if(filter.isValid(recEvent.getReconstructed())==true){
        Particle piplus = recEvent.getReconstructed().getParticle("[b]+[t]-[11]-[2212]-[-211]");
        System.out.println(piplus.mass());
        Particle reaction = recEvent.getGenerated().getParticle("[b]+[t]-[11]-[2212]-[-211]-[211]");
        h11.fill(reaction.vector().e());

        h1.fill(piplus.mass());
   	    System.out.println(recEvent);
    }
    if(filter2.isValid(recEvent.getReconstructed())==true){
        Particle piplus = recEvent.getReconstructed().getParticle("[b]+[t]-[11]-[2212]-[211]");
        System.out.println(piplus.mass());
        h2.fill(piplus.mass());
        Particle reaction = recEvent.getGenerated().getParticle("[b]+[t]-[11]-[2212]-[-211]-[211]");
        h21.fill(reaction.vector().e());
        System.out.println(recEvent);
    }

    if(filter3.isValid(recEvent.getReconstructed())==true){
        //Particle piplus = recEvent.getReconstructed().getParticle("[b]+[t]-[11]-[2212]");
        Particle piplus = recEvent.getReconstructed().getParticle("[211]+[-211]");
        System.out.println(piplus.mass());
        h3.fill(piplus.mass());
        System.out.println(recEvent);
    }
//     PhysicsEvent  genEvent  = fitter.getGeneratedEvent(event);
	
}

h1.setOptStat(111111);
h2.setOptStat(111111);
h3.setOptStat(111111);

h1.setTitleX("Mx (e p #pi^+) [GeV]");
h2.setTitleX("Mx (e p #pi^-) [GeV]");
h3.setTitleX("M (#pi^- #pi^+) [GeV]");

h1.setTitle("ep #rarrow ep#pi^+#pi^-");
h2.setTitle("ep #rarrow ep#pi^+#pi^-");
h3.setTitle("ep #rarrow ep#pi^+#pi^-");

h1.setLineWidth(2);
h2.setLineWidth(2);
h3.setLineWidth(2);

h1.setFillColor(44);
h2.setFillColor(45);
h3.setFillColor(43);

TCanvas canvas = new TCanvas("canvas",1400,1400);
canvas.divide(2,1);
canvas.cd(0);
canvas.draw(h4rec2);
canvas.cd(1);
canvas.draw(h4rec);

//canvas.draw(h21);
