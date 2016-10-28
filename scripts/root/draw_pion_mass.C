{

	TNtuple *T = new TNtuple("T","","m");
	T->ReadFile("pion_data.dat");
	gStyle->SetOptFit(1111);
	TCanvas *c1 = new TCanvas("c1","",800,500);
	c1->Divide(2,1);
	c1->cd(1);

	T->Draw("m>>h1(80,0.001,0.06)");
	c1->cd(2);
	T->Draw("sqrt(m)>>h2(80,0.001,0.3)");
	TF1 *func = new TF1("func","gaus",0.12,0.16);
	h2->Fit(func,"RME");
	c1->Print("figure_pion_mass.pdf");
}
