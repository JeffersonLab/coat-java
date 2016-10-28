import org.jlab.groot.data.*;
import org.jlab.groot.math.*;
import org.jlab.groot.ui.*;
import org.jlab.groot.fitter.*;


TCanvas c1 = new TCanvas("c1",600,600);

BufferedWriter writer = new BufferedWriter(new FileWriter(new File("training_sample.txt")));

F1D func = new F1D("func","2.0+[a]*cos(x)+[b]*cos(2*x)" ,0.0,2.0*3.1415);
func.setParameter(0,0.5);
func.setParameter(1,1.0);
RandomFunc randfunc = new RandomFunc(func);

H1F h1 = new H1F("h1","",25,0.0,2.0*3.1415);

for(int i = 0; i < 800; i ++){
  h1.fill(randfunc.random());
}
c1.divide(1,2);

c1.cd(0);
c1.draw(func);

c1.cd(1);
c1.draw(h1);



