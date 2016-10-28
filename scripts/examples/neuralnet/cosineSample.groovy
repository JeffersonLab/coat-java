import org.jlab.groot.data.*;
import org.jlab.groot.math.*;
import org.jlab.groot.ui.*;
import org.jlab.groot.fitter.*;


TCanvas c1 = new TCanvas("c1",600,600);

BufferedWriter writer = new BufferedWriter(new FileWriter(new File("training_sample.txt")));

for(double a=0.0; a < 1.0; a+=0.05){
  for(double b = 0.0; b < 1.0; b+=0.05){
    F1D func = new F1D("func","[a]*cos(x)+[b]*cos(2*x)" ,0.0,2.0*3.1415);
    func.setParameter(0,a);
    func.setParameter(1,b);
    double step = 3.1415*2.0/25.0;
    StringBuilder str = new StringBuilder();
    for(int i = 0; i < 25; i++){
      double x = step*i;
      str.append(String.format("%.3f;",func.evaluate(x)));
    }
    str.append(String.format("%.3f;%.3f\n",a,b));
    writer.write(str.toString());
    c1.cd(0);
    c1.draw(func,"same");
  }
}



