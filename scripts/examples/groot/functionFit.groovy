import org.jlab.groot.data.*;
import org.jlab.groot.math.*;
import org.jlab.groot.ui.*;
import org.jlab.groot.fitter.*;


public class MyFunction extends Func1D {

public MyFunction(){
	super("MyFunction",0.0,5.0);
	addParameter("amp");
	addParameter("mean");
	addParameter("sigma");
}

@Override
public double evaluate(double x){
	return getParameter(0)*FunctionFactory.gauss(x,getParameter(1),getParameter(2));
}

public static void main(String[] args){

  TCanvas c1 = new TCanvas("c1",600,600);
  H1F  h1    = FunctionFactory.randomGausian(25, 0.1, 5.0, 8000, 2.2, 0.4);

  MyFunction func = new MyFunction();

  func.setParameter(0,10);
  func.setParameter(1,1.8);
  func.setParameter(2,0.25);
  DataFitter.fit(func,h1,"E");


  c1.divide(1,2);
  c1.cd(0);
  c1.draw(h1);
  c1.draw(func,"same");
  c1.cd(1);
  c1.draw(func);
}

}
