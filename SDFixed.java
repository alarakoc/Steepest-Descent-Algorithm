

public class SDFixed extends SteepestDescent {
	private double alpha ; // fixed step size

	// constructors
	public SDFixed () {
		this.alpha=0.01;
	}
	
	public SDFixed ( double alpha ) {
		this.alpha=alpha;	
	}

	
	// getters
	public double getAlpha () {
		return alpha;	
	}
	
	// setters
	public void setAlpha ( double a ) {
		alpha=a;	
	}
	
	
	// other methods
	public double lineSearch ( Polynomial P , double [] x ) { // fixed step size
		return alpha;
		
	}
	public boolean getParamsUser () {// get algorithm parameters from user
		double S_input_fixedstepsize;
		double S_input_toleps;
		int S_input_maxiter;
		double S_input_startingpoint;
		
		System.out.println("");
		System.out.println("Set parameters for SD with a fixed line search:");
		
		S_input_fixedstepsize=Pro5_kocalara.getDouble("Enter fixed step size (0 to cancel): ",0, Double.MAX_VALUE);
		if (S_input_fixedstepsize==0) {
			return false;
		}
	
		S_input_toleps = Pro5_kocalara.getDouble("Enter tolerance epsilon (0 to cancel): ", 0, Double.MAX_VALUE);
		if (S_input_toleps==0) {
			return false;
		}
		
		S_input_maxiter = Pro5_kocalara.getInteger("Enter maximum number of iterations (0 to cancel): ", 0, 10000);
		if (S_input_maxiter==0) {
			return false;	
		}
		
		S_input_startingpoint = Pro5_kocalara.getDouble("Enter value for starting point (0 to cancel): ", Double.MAX_VALUE*(-1), Double.MAX_VALUE);
		if (S_input_startingpoint==0) {
			return false;
		}
		
		setAlpha(S_input_fixedstepsize);	
		setEps(S_input_toleps);
		setMaxIter(S_input_maxiter);
		setAlpha(S_input_fixedstepsize);
		setX0(S_input_startingpoint);
	
		return true;
		
	}
	
	public void print () {// print parameters //CHANGE
		
		System.out.println("");
		System.out.println("SD with a fixed line search:");
		System.out.println("Tolerance (epsilon): " + this.getEps());
		System.out.println("Maximum iterations: " + this.getMaxIter());
		System.out.println("Starting point (x0): " + this.getX0());
		System.out.println("Fixed step size (alpha): " + this.getAlpha() );
			
	}
	
	public void printStats() {
		
		System.out.println("");
		System.out.println("Statistical summary for SD with a fixed line search:");
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
			System.out.println("Detailed results for SD with a fixed line search:");
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
	
	
}