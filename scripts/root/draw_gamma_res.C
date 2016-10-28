{

	TNtuple *T = new TNtuple("T","","p:theta:phi:gp:gtheta:gphi");

	T->ReadFile("gamma_data.dat");
	gStyle->SetOptFit(1111);
	TCanvas *c1 = new TCanvas("c1","",1000,500);
	c1->Divide(3,1);
	c1->cd(1);

	T->Draw("(p-gp)/gp>>h1(80,-0.3,0.3)");
	c1->cd(2);
	T->Draw("theta-gtheta>>h2(80,-3.,3.)");
	c1->cd(3);
	T->Draw("phi-gphi>>h3(80,-3.,3.)");
	c1->Print("figure_gamma_res.pdf");
}