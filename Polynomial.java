

public class Polynomial {

	private int n ; // no. of variables
	private int degree ; // degree of polynomial
	private double [][] coefs ; // coefficients
	
	
	// constructors
	public Polynomial () {
		this.n = 0;
		this.degree = 0;
		this.init();	
	}
	
	public Polynomial ( int n , int degree , double [][] coefs ) {
		this.n = n;
		this.degree = degree;	
	}
		
	
	// getters
	public int getN () {
		return n; 	
	}
	
	public int getDegree () {
		return degree;	
	}
	
	public double [][] getCoefs (){
		return coefs;	
	}
	
	
	// setters
	public void setN ( int a ) {
		n = a; 	
	}
	
	public void setDegree ( int a ) {
		degree = a; 
	}
	
	public void setCoef ( int j , int d , double a ) {
		coefs[j][d] = a; 
	}
	
	
	
	// other methods
	public void init () {// init member arrays to correct size
		this.coefs = new double[this.getN()][this.getDegree()+1];	
	}
	
	public double f ( double [] x ) {// calculate function value at point x
		double sum_fun=0;
		for(int num_var=0; num_var<this.getN(); num_var++) {
			for(int deg_var = this.getDegree(); deg_var>=0; deg_var--) {
				sum_fun = sum_fun + (this.getCoefs()[num_var][this.getDegree()-deg_var]) * Math.pow(x[num_var], (deg_var)) ;
			}
		}
		return sum_fun;
	}
	
	public double [] gradient ( double [] x ) {// calculate gradient at point x
		double par_der[] = new double[this.getN()];
		double sum_der;
		for(int num_var=0; num_var<this.getN(); num_var++) {
			sum_der = 0;
			for(int deg_var = this.getDegree(); deg_var>0; deg_var--) {
				sum_der += (this.getCoefs()[num_var][this.getDegree()-deg_var]*deg_var) * Math.pow(x[num_var], (deg_var-1)) ;
			}
			par_der[num_var] = sum_der;
		}
		return par_der;
	}
	
	public double gradientNorm ( double [] x ) {// calculate norm of gradient at point x
	double norm=0;
		
		for(int i = 0; i<this.getN(); i++) {
			norm += Math.pow(this.gradient(x)[i],2);
		}
		
		return Math.pow(norm,0.5);
	}
	
	public boolean isSet () { // indicate whether polynomial is set
		return (this.getN()!=0);
	}
	
	public void print_title() {// print out the polynomial
		System.out.println("");
		System.out.println("---------------------------------------------------------");
		System.out.println("Poly No.  Degree   # vars   Function");   
		System.out.println("---------------------------------------------------------");
		}
		
		public void print (int printed_pol_num) {// print out the polynomial
			System.out.printf("%8d %7d %8d %2s", printed_pol_num+1 , this.getDegree(), this.getN(), " ");
			print_2();
		}
		
		public void print_2() {
			System.out.print("f(x) = ");
			for (int index_1 = 0; index_1<this.getN(); index_1++) {
				System.out.print("( ");
				for (int index_2 = 0; index_2<=this.getDegree(); index_2++) {
					System.out.print(String.format("%.2f",this.getCoefs()[index_1][index_2]));
									
					if ((this.getDegree()-index_2)>0) {
						System.out.print("x");
						System.out.print(index_1+1);
						System.out.print("^");
						System.out.print(this.getDegree()-index_2);
						System.out.print(" + ");
					}
				}	
				System.out.print(" )");
				if (index_1+1!=this.getN()) {
					System.out.print(" + ");
				}
			}
			System.out.println("");
		}
}

