
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;


public class Pro5_kocalara {
	
public static BufferedReader cin = new BufferedReader (new InputStreamReader(System.in));
	
	public static void main(String [] args) throws NumberFormatException, IOException { 

		//get a polynomial object
		//Polynomial pol = new Polynomial();
		ArrayList<Polynomial> array_pol= new ArrayList <Polynomial>();
		//get a SteepestDescent object
		//SteepestDescent sd = new SteepestDescent();
		SDFixed SDF = new SDFixed();
		SDArmijo SDA = new SDArmijo();
		SDGSS SDG= new SDGSS();
		
		boolean valid_input=false;
		while(!valid_input){
			valid_input = true;
			displayMenu();
			String menu_input = cin.readLine().toUpperCase().strip();
	        menu_input = menu_input.toUpperCase().strip();

			if (menu_input.equals("L")) {				
				loadPolynomialFile(array_pol);
				SDF.setHasResults(false);
				SDF.init(array_pol);
				SDA.setHasResults(false);
				SDA.init(array_pol);
				SDG.setHasResults(false);
				SDG.init(array_pol);
				valid_input = false;
				continue;
			}// if for L
			else if (menu_input.equals("F")) {
				if (array_pol.size()==0) {
					System.out.println("");
					System.out.println("ERROR: No polynomial functions are loaded!");
					System.out.println("");
					valid_input = false;
					continue;
				}//if
				printPolynomials(array_pol);
				valid_input = false;
				continue;
				}//else if for F
			
			else if (menu_input.equals("C")) {
				array_pol.clear();
				System.out.println("");
				System.out.println("All polynomials cleared.");
				System.out.println("");
				valid_input = false;
				} // else if C
			
			else if (menu_input.equals("S")) {
				getAllParams(SDF, SDA, SDG);	
				
				if((array_pol.size()!=0) && !(SDF.hasResults()) && !(SDA.hasResults()) &&  !(SDG.hasResults())) {
					SDF.init(array_pol);
					SDA.init(array_pol);
					SDG.init(array_pol);
					}
				
				valid_input = false;
				continue;
				}
			
			else if (menu_input.equals("P")) {
				printAllParams(SDF, SDA, SDG);
				valid_input = false;
				continue;
			} // else if 
			
			else if (menu_input.equals("R")){
				if (array_pol.size()==0) {
					System.out.println("");
					System.out.println("ERROR: No polynomial functions are loaded!");
					System.out.println("");
					valid_input = false;
					continue;
				}
					runAll(SDF, SDA, SDG,  array_pol);
					System.out.println("");
					System.out.println("All polynomials done.");
					System.out.println("");
					valid_input = false;
					continue;	
			}
			
			else if (menu_input.equals("D")) {
				
				//check if a polynomial is set
				//if not error if given run
				if (array_pol.size()==0) {
					System.out.println("");
					System.out.println("ERROR: Results do not exist for all line searches!");
					System.out.println("");
					valid_input = false;
					continue;
				} //if
				if (!SDF.hasResults()) {
					System.out.println("");
					System.out.println("ERROR: Results do not exist for all line searches!");
					System.out.println("");
					valid_input = false;
					continue;
				} //if
				else if (!SDA.hasResults()) {
					System.out.println("");
					System.out.println("ERROR: Results do not exist for all line searches!");
					System.out.println("");
					valid_input = false;
					continue;
				}
				else if (!SDG.hasResults()) {
					System.out.println("");
					System.out.println("ERROR: Results do not exist for all line searches!");
					System.out.println("");
					valid_input = false;
					continue;
				}//if
			
				printAllResults(SDF,SDA,SDG, array_pol);
				valid_input = false;
				continue;
			}
			
			else if (menu_input.equals("X")) {
				
				//check if a polynomial is set
				//if not error if given run
				if (array_pol.size()==0) {
					System.out.println("");
					System.out.println("ERROR: Results do not exist for all line searches!");
					System.out.println("");
					valid_input = false;
					continue;
				} //if
				if (!SDF.hasResults()) {
					System.out.println("");
					System.out.println("ERROR: Results do not exist for all line searches!");
					System.out.println("");
					valid_input = false;
					continue;
				} //if
				else if (!SDA.hasResults()) {
					System.out.println("");
					System.out.println("ERROR: Results do not exist for all line searches!");
					System.out.println("");
					valid_input = false;
					continue;
				}
				else if (!SDG.hasResults()) {
					System.out.println("");
					System.out.println("ERROR: Results do not exist for all line searches!");
					System.out.println("");
					valid_input = false;
					continue;
				}
				compare(SDF,SDA, SDG);
				valid_input = false;
				continue;
			}
			
			else if (menu_input.equals("Q")) {
				System.out.println("");
				System.out.println("Arrivederci.");
				System.exit(0);		
			}else {
				valid_input = false;
				System.out.println("");
				System.out.println("ERROR: Invalid menu choice!");
				System.out.println("");
			} //else
		}//while
	}//main
	
	public static void displayMenu () {
		System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
		System.out.println("L - Load polynomials from file");
		System.out.println("F - View polynomial functions");
		System.out.println("C - Clear polynomial functions");
		System.out.println("S - Set steepest descent parameters");
		System.out.println("P - View steepest descent parameters");
		System.out.println("R - Run steepest descent algorithms");
		System.out.println("D - Display algorithm performance");
		System.out.println("X - Compare average algorithm performance");
		System.out.println("Q - Quit");
		System.out.println("");
		System.out.print("Enter choice: ");	
	}
	
	public static boolean loadPolynomialFile(ArrayList<Polynomial> P) throws IOException {
		
		String L_input_file;
		
		L_input_file = "Enter file name (0 to cancel): ";
		System.out.println("");
		System.out.print(L_input_file);
		String filename=cin.readLine();
		
		if (filename.equals("0")) {
			System.out.println("");
			System.out.println("File loading process canceled.");
			System.out.println("");
			return false;
		}
		
		else {		
		File file= new File(filename);	
		boolean exists=file.exists();					
		
		if (!(exists) | !(file.canRead())) {
				System.out.println("");
				System.out.println("ERROR: File not found!");
				System.out.println("");
				return false;
		}//if
		}//else
		
		BufferedReader fin = new BufferedReader (new FileReader(filename));
		String fileline = "alara.koc";
		ArrayList<double[]> listOfLines = new ArrayList<double[]>(); 
		
		int no=0;
		int deg=0;
		int temp_size = P.size();
		while (fileline!=null) { // while
				fileline=fin.readLine();
				
				//if there is a star
				if ( (fileline==null)||(fileline.equals("*")) ){ 
					no=no+1;
					boolean valid_pol = true;
					for (int n=0; n<listOfLines.size(); n++) {	
				
						if (!(listOfLines.get(0).length==listOfLines.get(n).length)) {
							System.out.println("");
							System.out.println("ERROR: Inconsistent dimensions in polynomial " + no + "!");
							valid_pol=false;
							deg=0;
							break;
						}//if
						else {
							deg=listOfLines.get(0).length;	
						} //else
					}//for
					
					if ((valid_pol) && !(deg==0)) {
						Polynomial pol = new Polynomial ();
						pol.setN(listOfLines.size());
						pol.setDegree(deg-1);
						pol.init();
						
						for (int i=0; i<listOfLines.size(); i++) {
							for (int j=0; j<deg; j++) {
								pol.setCoef(i, j, listOfLines.get(i)[j]);
							}//for
						}//for	
						P.add(pol);
					}//if
					listOfLines.removeAll(listOfLines);
					continue;	
				}//if
	
				//if there is no star
				else if(fileline!=null){
					String[] splitString=fileline.split(",");
					double [] co_num= new double[splitString.length];
	
					for (int co=0; co < splitString.length; co++) 
					{
						co_num[co] =Double.parseDouble(splitString[co]);

					}//for 
					listOfLines.add(co_num);	
					continue;
					}//else
		} //while
		fin.close();
	
		System.out.println("");
		System.out.println(P.size()-temp_size + " polynomials loaded!");
		System.out.println("");

		return true;
	} //main

	public static void getAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
		
		if (!(SDF.getParamsUser())) {
			System.out.println("");
			System.out.println("Process canceled. No changes made to algorithm parameters.");
		}
		else {
			SDF.setHasResults(false);
			System.out.println("");
			System.out.println("Algorithm parameters set!");
		}
		
		if (!(SDA.getParamsUser())) {
			System.out.println("");
			System.out.println("Process canceled. No changes made to algorithm parameters.");
			}
		else {
			SDA.setHasResults(false);
			System.out.println("");
			System.out.println("Algorithm parameters set!");
			}
		
		if (!(SDG.getParamsUser())) {
			System.out.println("");
			System.out.println("Process canceled. No changes made to algorithm parameters.");
			System.out.println("");
			
			}
		else {
			SDG.setHasResults(false);
			System.out.println("");
			System.out.println("Algorithm parameters set!");
			System.out.println("");
			}

	}//main

	public static void printAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
		
		SDF.print();
		SDA.print();
		SDG.print();	
	}
	
	public static void runAll(SDFixed SDF, SDArmijo SDA, SDGSS SDG, ArrayList<Polynomial> P ) {
		
		System.out.println("");
		System.out.println("Running SD with a fixed line search:");
		for (int k = 0; k<P.size(); k++) {
			SDF.run(k, P.get(k));		
	}
		System.out.println("");
		System.out.println("Running SD with an Armijo line search:");
		for (int k = 0; k<P.size(); k++) {
			SDA.run(k, P.get(k));
		
	}
		System.out.println("");
		System.out.println("Running SD with a golden section line search:");
		for (int k = 0; k<P.size(); k++) {
			SDG.run(k, P.get(k));
		}
	}
		
	public static void printPolynomials(ArrayList<Polynomial> P) {
		 P.get(0).print_title();
		 for (int i=0; i<P.size();i++) {
			 P.get(i).print(i);
		 }
		 System.out.println("");
		 return;
	 }
	
	public static void printAllResults(SDFixed SDF, SDArmijo SDA, SDGSS SDG, ArrayList<Polynomial>P) {
	
		SDF.printAll();
		SDF.printStats();
		SDA.printAll();
		SDA.printStats();
		SDG.printAll();
		SDG.printStats();	
		
	}
	
	public static void compare(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
		
		double average_norm = 0;
		double average_iter = 0;
		double average_comp = 0;
		double average_norm1 = 0;
		double average_iter1 = 0;
		double average_comp1 = 0;
		double average_norm2 = 0;
		double average_iter2 = 0;
		double average_comp2 = 0;
		double sum = 0;
		double sum1 = 0;
		double sum2 = 0;
		
		System.out.println("");
		System.out.println("---------------------------------------------------");
		System.out.println("          norm(grad)       # iter    Comp time (ms)");
		System.out.println("---------------------------------------------------");
		
		//SDF
		
		for (int i=0; i<SDF.getBestGradNorm().length; i++) {
			sum = sum+SDF.getBestGradNorm()[i];
		}
		average_norm = sum/SDF.getBestGradNorm().length;
		sum = 0;
		for (int i=0; i<SDF.getNIter().length; i++) {
			sum = sum+SDF.getNIter()[i];
		}
		average_iter = sum/SDF.getNIter().length;
		sum = 0;
		for (int i=0; i<SDF.getCompTime().length; i++) {
			sum = sum+SDF.getCompTime()[i];
		}
		average_comp = (double) (sum/SDF.getCompTime().length);
		sum = 0;
		
		//SDA
		
		for (int i=0; i<SDA.getBestGradNorm().length; i++) {
			sum1 = sum1+SDA.getBestGradNorm()[i];
		}
		average_norm1 = sum1/SDA.getBestGradNorm().length;
		sum1 = 0;
		for (int i=0; i<SDA.getNIter().length; i++) {
			sum1 = sum1+SDA.getNIter()[i];
		}
		average_iter1 = sum1/SDA.getNIter().length;
		sum1 = 0;
		for (int i=0; i<SDA.getCompTime().length; i++) {
			sum1 = sum1+SDA.getCompTime()[i];
		}
		average_comp1 = (double) (sum1/SDA.getCompTime().length);
		sum1 = 0;
		
		//SDG
		
		for (int i=0; i<SDG.getBestGradNorm().length; i++) {
			sum2 = sum2+SDG.getBestGradNorm()[i];	
		}
		average_norm2 = sum2/SDG.getBestGradNorm().length;
		sum2 = 0;
		for (int i=0; i<SDG.getNIter().length; i++) {
			sum2 = sum2+SDG.getNIter()[i];
		}
		average_iter2 = sum2/SDG.getNIter().length;
		sum2 = 0;
		for (int i=0; i<SDG.getCompTime().length; i++) {
			sum2 = sum2+SDG.getCompTime()[i];
		}		average_comp2 = (double) (sum2/SDG.getCompTime().length);
		sum2 = 0;
		
		
		
		String f;
		String a;
		String g;
		f="Fixed";
		a="Armijo";
		g="GSS";
		
		System.out.printf("%s %14.3f %12.3f %17.3f", f, average_norm, average_iter, average_comp);
		System.out.println("");
		System.out.printf("%s %13.3f %12.3f %17.3f", a, average_norm1, average_iter1, average_comp1);
		
		System.out.println("");
		System.out.printf("%s %16.3f %12.3f %17.3f", g, average_norm2, average_iter2, average_comp2);
		
		double norm_winner;
		double iter_winner;
		double comp_winner;
		String w1;
		String w2;
		String w3;
		
		//for gardnorm
		norm_winner=Math.min(Math.min(average_norm, average_norm1), average_norm2);
		
		if (norm_winner==average_norm){
			w1 = "Fixed";	
		}
		else if (norm_winner==average_norm1) {
			w1 = "Armijo";
		}
		else {
			w1="GSS";	
		}
		
		//for iterations
		iter_winner=Math.min(Math.min(average_iter, average_iter1), average_iter2);
		
		if (iter_winner==average_iter){
			w2 = "Fixed";	
		}
		else if (iter_winner==average_iter1) {
			w2 = "Armijo";
		}
		else {
			w2="GSS";
		}
		
		//for comp time
		comp_winner=Math.min(Math.min(average_comp, average_comp1), average_comp2);
		
		if (comp_winner==average_comp){
			w3 = "Fixed";	
		}
		else if (comp_winner==average_comp1) {
			w3 = "Armijo";
		}
		else {
			w3="GSS";
		}
		
		String s;
		s="Winner";
		
		System.out.println("");
		System.out.println("---------------------------------------------------");
		System.out.printf("%-6s%14s%13s%18s", s, w1, w2, w3 );
		System.out.println("\n---------------------------------------------------");
		
		
//	if(average_norm==average_norm1 && average_norm<average_norm2||  average_norm==average_norm2 && average_norm<average_norm1|| average_norm2==average_norm1 && average_norm2<average_norm || average_iter==average_iter1 && average_iter<average_iter2|| average_iter==average_iter2 && average_iter<average_iter1|| average_iter1==average_iter2 && average_iter1<average_iter|| average_comp==average_comp1 && average_comp<average_comp2|| average_comp==average_comp2 && average_comp<average_comp1|| average_comp1==average_comp2 && average_comp1<average_comp) {
//			System.out.println("Overall winner: Unclear");
//		}else {
		
		if ((w1==w2) && (w2==w3) && (w1==w3)) {
			System.out.println("Overall winner: " + w1);	
		}else {
			System.out.println("Overall winner: Unclear");
		}
			
//			if (w1==w2) {
//			System.out.println("Overall winner: " + w1);	
//		}else if (w2==w3) {
//			System.out.println("Overall winner: " + w2);	
//		}else if (w1==w3) {
//			System.out.println("Overall winner: " + w1);	
//		}else if (!(w1==w2) && !(w2==w3) && !(w1==w3)){
//			System.out.println("Overall winner: Unclear");
//		}		
		System.out.println("");
		}

			
	

	 public static int getInteger(String prompt, int LB, int UB) {
			boolean valid_input = false;
			int number = 0;
			while(!valid_input){
				valid_input=true;
				try {
					System.out.print(prompt);
					number = Integer.parseInt(cin.readLine());
					if (number >  UB || number < LB) {
						if (UB==Integer.MAX_VALUE && LB==Integer.MAX_VALUE*(-1)) {
							System.out.println("ERROR: Input must be an integer in [ -infinity, infinity]!");
						}else if (UB==Integer.MAX_VALUE) {
							System.out.println("ERROR: Input must be an integer in [" + LB + ", infinity]!");
						}else if (LB==Integer.MAX_VALUE*(-1)){
							System.out.println("ERROR: Input must be an integer in [ -infinity, " + UB + "]!");
						}else {
							System.out.println("ERROR: Input must be an integer in [" + LB + ", " + UB + "]!");
						}
						
						System.out.println("");
						valid_input=false;
					} 
			
				}catch (NumberFormatException e ) {
					if (UB==Integer.MAX_VALUE && LB==Integer.MAX_VALUE*(-1)) {
						System.out.println("ERROR: Input must be an integer in [ -infinity, infinity]!");
					}else if (UB==Integer.MAX_VALUE) {
						System.out.println("ERROR: Input must be an integer in [" + LB + ", infinity]!");
					}else if (LB==Integer.MAX_VALUE*(-1)){
						System.out.println("ERROR: Input must be an integer in [ -infinity, " + UB + "]!");
					}else {
						System.out.println("ERROR: Input must be an integer in [" + LB + ", " + UB + "]!");
					}
					System.out.println("");
					valid_input=false;
				} catch (IOException e) {
			
					System.out.print("ERROR: Input must be an integer in [" + LB + ", " + UB + "]!\n");
					valid_input=false;
				} // catch	
			} 
			return number;	
		}
	 
	 public static double getDouble(String prompt, double LB, double UB) {
	 boolean valid_input = false;
		double number = 0;
		while(!valid_input){
			valid_input=true;
			try {
				System.out.print(prompt);
				number = Double.parseDouble(cin.readLine());
				if (number >  UB || number < LB) {
					if (UB==Double.MAX_VALUE && LB==Double.MAX_VALUE*(-1)) {
						System.out.println("ERROR: Input must be a real number in [-infinity, infinity]!");
					}else if (UB==Double.MAX_VALUE) {
						System.out.println("ERROR: Input must be a real number in [" + String.format("%.2f",LB) + ", infinity]!");
					}else if (LB==Double.MAX_VALUE*(-1)){
						System.out.println("ERROR: Input must be a real number in [-infinity, " + String.format("%.2f",UB) + "]!");
					}else {
						System.out.println("ERROR: Input must be a real number in [" + String.format("%.2f",LB) + ", " + String.format("%.2f",UB) + "]!");
					}
					
					System.out.println("");
					valid_input=false;
				} 
		
			}catch (NumberFormatException e) {
				
				if (UB==Double.MAX_VALUE && LB==Double.MAX_VALUE*(-1)) {
					System.out.println("ERROR: Input must be a real number in [-infinity, infinity]!");
				}else if (UB==Double.MAX_VALUE) {
					System.out.println("ERROR: Input must be a real number in [" + String.format("%.2f",LB) + ", infinity]!");
				}else if (LB==Double.MAX_VALUE*(-1)){
					System.out.println("ERROR: Input must be a real number in [-infinity, " + String.format("%.2f",UB) + "]!");
				}else {
					System.out.println("ERROR: Input must be a real number in [" + String.format("%.2f",LB) + ", " + String.format("%.2f",UB) + "]!");
				}
			
				System.out.println("");
				valid_input=false;
		
			} catch (IOException e) {
				System.out.print("ERROR: Input must be a real number in [" + LB + ", " + UB + "]!\n");
				valid_input=false;
			} // catch	
		} 
		return number;	
	}		
}




