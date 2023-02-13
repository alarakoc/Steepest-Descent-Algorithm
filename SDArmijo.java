

public class SDArmijo extends SteepestDescent {
	
	private double maxStep ; // Armijo max step size
	private double beta ; // Armijo beta parameter
	private double tau ; // Armijo tau parameter

	private int K ; // Armijo max no. of iterations
	
	// constructors
	public SDArmijo () {
		this.maxStep=1.0;
		this.beta=1.0*Math.pow(10, -4);
		this.tau=0.5;
		this.K=10;	
	}
	
	public SDArmijo ( double maxStep , double beta , double tau , int K ) {
		this.maxStep=maxStep;
		this.beta=beta;
		this.tau=tau;
		this.K=K;	
	}

	//getters
	public double getMaxStep () {
		return maxStep;
	}
	
	public double getBeta () {
		return beta;	
	}
	
	public double getTau () {
		return tau;	
	}
	
	public int getK () {
		return K;	
	}
	
	// setters
	public void setMaxStep ( double a ) {
		maxStep=a;	
	}
	
	public void setBeta ( double a ) {
		beta=a;	
	}
	
	public void setTau ( double a ) {
		tau=a;	
	}
	
	public void setK ( int a ) {
		K=a;	
	}

	// other methods
	public double lineSearch ( Polynomial P , double [] x ) { // Armijo line search
		double temp_array [] = new double[x.length];
		double alpha=maxStep;
		int curr_step=0;
		
		while (curr_step<K) {
		temp_array = P.gradient(x); 
		
		
		for (int n=0;n<temp_array.length;n++) { 	//for loop
			temp_array[n]=x[n]-temp_array[n]*alpha;	 //x = x + w since it is inside f()
		}//end for loop
		
		//testing time (inequality)
		
		double firstpart=0;
		double secondpart=0;
		
		firstpart=P.f(temp_array);		
		secondpart=P.f(x)-alpha*this.getBeta()*Math.pow(P.gradientNorm(x),2); // add to f(x)
		
	
		if (firstpart<=secondpart) { //test if the formula satisfied by the inequality
			return alpha;
		}
		alpha = alpha * this.getTau(); //continue testing until max iteration no is reached
		curr_step++;
		} //steepest algorithm quits (????)
		
		return Double.POSITIVE_INFINITY;
	}
	
	
	
	public boolean getParamsUser () {// get algorithm parameters from user
		
		double Armijo_maxstepsize;
		double Armijo_beta;
		double Armijo_tau;
		int Armijo_K;
		double Armijo_toleps;
		int Armijo_maxiter;
		double Armijo_startingpoint;
		
		System.out.println("");
		System.out.println("Set parameters for SD with an Armijo line search:");
		
		Armijo_maxstepsize=Pro5_kocalara.getDouble("Enter Armijo max step size (0 to cancel): ",0, Double.MAX_VALUE);
		if (Armijo_maxstepsize==0) {
			return false;	
		}	
		
		Armijo_beta=Pro5_kocalara.getDouble("Enter Armijo beta (0 to cancel): ",0, 1);
		if (Armijo_beta==0) {
			return false;
		}	
		Armijo_tau=Pro5_kocalara.getDouble("Enter Armijo tau (0 to cancel): ",0, 1);
		if (Armijo_tau==0) {
			return false;
		}
		Armijo_K=Pro5_kocalara.getInteger("Enter Armijo K (0 to cancel): ",0, Integer.MAX_VALUE);
		if (Armijo_K==0) {
			return false;
		}
		Armijo_toleps=Pro5_kocalara.getDouble("Enter tolerance epsilon (0 to cancel): ",0, Double.MAX_VALUE);
		if (Armijo_toleps==0) {
			return false;
		}
		Armijo_maxiter=Pro5_kocalara.getInteger("Enter maximum number of iterations (0 to cancel): ",0, 10000);
		if (Armijo_maxiter==0) {
			return false;
		}
		Armijo_startingpoint=Pro5_kocalara.getDouble("Enter value for starting point (0 to cancel): ",Double.MAX_VALUE*(-1), Double.MAX_VALUE);
		if (Armijo_startingpoint==0) {
			return false;
		}
		
		setMaxStep(Armijo_maxstepsize);
		setBeta(Armijo_beta);
		setTau(Armijo_tau);
		setK(Armijo_K);
		setEps(Armijo_toleps);
		setMaxIter(Armijo_maxiter);
		setX0(Armijo_startingpoint);
		
		return true;	
	}
	
	public void print () {// print parameters //CHANGE
		
		System.out.println("");
		System.out.println("SD with an Armijo line search:");
		System.out.println("Tolerance (epsilon): " + this.getEps() );
		System.out.println("Maximum iterations: " + this.getMaxIter() );
		System.out.println("Starting point (x0): " + this.getX0());
		System.out.println("Armijo maximum step size: " + this.getMaxStep() );
		System.out.println("Armijo beta: " + this.getBeta() );
		System.out.println("Armijo tau: " + this.getTau() );
		System.out.println("Armijo maximum iterations: " + getK() );
					
	}
	
	public void printStats() {
		
		System.out.println("");
		System.out.println("Statistical summary for SD with an Armijo line search:");
		System.out.println("---------------------------------------------------");
		System.out.println("          norm(grad)       # iter    Comp time (ms)");
		System.out.println("---------------------------------------------------");
		
		double average_norm = 0;
		double st_dev_norm = 0;
		double min_norm = 999999999;
		double max_norm = 0;
		double average_iter = 0;
		double st_dev_iter = 0;
		double min_iter = 999999999;
		double max_iter = 0;
		double average_comp = 0;
		double st_dev_comp = 0;
		double min_comp = 999999999;
		double max_comp = 0;
		double sum = 0;
		
		//Get the average values
		for (int i=0; i<this.getBestGradNorm().length; i++) {
			sum = sum+this.getBestGradNorm()[i];
		}
		average_norm = sum/this.getBestGradNorm().length;
		sum = 0;
		for (int i=0; i<this.getNIter().length; i++) {
			sum = sum+this.getNIter()[i];
		}
		average_iter = sum/this.getNIter().length;
		sum = 0;
		for (int i=0; i<this.getCompTime().length; i++) {
			sum = sum+this.getCompTime()[i];
		}
		average_comp = (double) (sum/this.getCompTime().length);
		sum = 0;
		
		
		//Get the standard deviation
		for (int i=0; i<this.getBestGradNorm().length; i++) {
			sum = sum+Math.pow(this.getBestGradNorm()[i]-average_norm,2);
		}
		st_dev_norm = Math.pow((sum/(this.getBestGradNorm().length-1)),0.5);
		sum = 0;
		for (int i=0; i<this.getNIter().length; i++) {
			sum = sum+ Math.pow(this.getNIter()[i]-average_iter,2);
		}
		st_dev_iter = Math.pow((sum/(this.getNIter().length-1)),0.5);
		sum = 0;
		for (int i=0; i<this.getCompTime().length; i++) {
			sum = sum+ Math.pow(this.getCompTime()[i]-average_comp,2);
		}
		st_dev_comp = (double) Math.pow((sum/(this.getCompTime().length-1)),0.5);
		sum = 0;
		
		//Get the min
		for (int i=0; i<this.getBestGradNorm().length; i++) {
			if (this.getBestGradNorm()[i]<min_norm) {
				min_norm = this.getBestGradNorm()[i];
			}
		}
		for (int i=0; i<this.getNIter().length; i++) {
			if (this.getNIter()[i]<min_iter) {
				min_iter = this.getNIter()[i];
			}
		}
		for (int i=0; i<this.getCompTime().length; i++) {
			if (this.getCompTime()[i]<min_comp) {
				min_comp = this.getCompTime()[i];
			}		
		}
		
		//Get the max
		for (int i=0; i<this.getBestGradNorm().length; i++) {
			if (this.getBestGradNorm()[i]>max_norm) {
				max_norm = this.getBestGradNorm()[i];
			}
		}
		for (int i=0; i<this.getNIter().length; i++) {
			if (this.getNIter()[i]>max_iter) {
				max_iter = this.getNIter()[i];
			}
		}
		for (int i=0; i<this.getCompTime().length; i++) {
			if (this.getCompTime()[i]>max_comp) {
				max_comp = this.getCompTime()[i];
			}		
		}

		System.out.printf("%s %12.3f %12.3f %17.3f", "Average", average_norm, average_iter, average_comp);
		System.out.println("");
		System.out.printf("%s %13.3f %12.3f %17.3f", "St Dev", st_dev_norm, st_dev_iter, st_dev_comp);
		System.out.println("");
		System.out.printf("%s %16.3f %12.0f %17.0f", "Min", min_norm, min_iter, min_comp);
		System.out.println("");
		System.out.printf("%s %16.3f %12.0f %17.0f", "Max", max_norm, max_iter, max_comp);
		System.out.println("");
		System.out.println("");

		
	} // print statistical summary of results
	public void printAll() {
		printSingleResult(0, false);
		for (int i = 0; i<this.getBestObjVal().length; i++) {
			printSingleResult(i,true);
		}
	} // print final results for all polynomials

	public void printSingleResult( int i , boolean rowOnly ) {
		if (!rowOnly) {
			System.out.println("");
			System.out.println("Detailed results for SD with an Armijo line search:");
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");   
			System.out.println("-------------------------------------------------------------------------");
			return;
		}
		System.out.format("%8d%13.6f%13.6f%9d%17d", i+1, this.getBestObjVal()[i], this.getBestGradNorm()[i], this.getNIter()[i], this.getCompTime()[i]);
		//print best point
		System.out.print("   ");
		for (int k=0; k<this.getBestPoint(i).length;k++) {
			System.out.printf("%.4f", this.getBestPoint(i)[k]);
			if (k+1!=this.getBestPoint(i).length) {
				System.out.print(", ");
			}
		}
		System.out.println("");
		return;
	} 
	
}//main
