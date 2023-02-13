

public class SDGSS extends SteepestDescent {
	private final double _PHI_ = (1. + Math . sqrt (5) ) /2.;
	private double maxStep ; // Armijo max step size
	private double minStep ; // Armijo beta parameter
	private double delta ; // Armijo delta parameter

	// constructors
	public SDGSS () {
		this.maxStep=1.0;
		this.minStep=0.001;
		this.delta=0.001;	
	}
	
	public SDGSS ( double maxStep , double minStep , double delta ) {
		this.maxStep=maxStep;
		this.minStep=minStep;
		this.delta=delta;	
	}
	
	
	// getters
	public double getMaxStep () {
		return maxStep;	
	}
	
	public double getMinStep () {
		return minStep;	
	}
	
	public double getDelta () {
		return delta;	
	}
	
	
	// setters
	public void setMaxStep ( double a ) {
		this.maxStep=a;	
	}
	
	public void setMinStep ( double a ) {
		this.minStep=a;		
	}
	
	public void setDelta ( double a ) {
		this.delta=a;	
	}
	
	
	// other methods
	public double lineSearch ( Polynomial P , double [] x ) { // step size from GSS
		
		double c=this.getMinStep()+(this.getMaxStep()-this.getMinStep())/_PHI_;
		
		
		return GSS(this.getMinStep(), this.getMaxStep(), c, x, P.gradient(x), P );
		
	}//main
	
	public boolean getParamsUser () { // get algorithm parameters from user
		
		double GSS_maxstepsize;
		double GSS_minstepsize;
		double GSS_delta;
		double GSS_toleps;
		int GSS_maxiter;
		double GSS_startingpoint;
		
	
	System.out.println("");
	System.out.println("Set parameters for SD with a golden section line search:");

	GSS_maxstepsize=Pro5_kocalara.getDouble("Enter GSS maximum step size (0 to cancel): ",0, Double.MAX_VALUE);
	if (GSS_maxstepsize==0) {
		return false;	
	}
	GSS_minstepsize=Pro5_kocalara.getDouble("Enter GSS minimum step size (0 to cancel): ",0, GSS_maxstepsize);
	if (GSS_minstepsize==0) {
		return false;
	}
	GSS_delta=Pro5_kocalara.getDouble("Enter GSS delta (0 to cancel): ",0, Double.MAX_VALUE);
	if (GSS_delta==0) {
		return false;
	}
	GSS_toleps=Pro5_kocalara.getDouble("Enter tolerance epsilon (0 to cancel): ",0, Double.MAX_VALUE);
	if (GSS_toleps==0) {
		return false;	
	}
	GSS_maxiter=Pro5_kocalara.getInteger("Enter maximum number of iterations (0 to cancel): ",0, 10000);
	if (GSS_maxiter==0) {
		return false;
	}
	GSS_startingpoint=Pro5_kocalara.getDouble("Enter value for starting point (0 to cancel): ",Double.MAX_VALUE*(-1), Double.MAX_VALUE);
	if (GSS_startingpoint==0) {
		return false;
	}
	
	setMaxStep(GSS_maxstepsize);
	setMinStep(GSS_minstepsize);	
	setDelta(GSS_delta);
	setEps(GSS_toleps);
	setMaxIter(GSS_maxiter);
	setX0(GSS_startingpoint);
	
	return true;
	
	}
	
	public void print () { // print parameter
		System.out.println("");
		System.out.println("SD with a golden section line search:");
		System.out.println("Tolerance (epsilon): " + this.getEps() );
		System.out.println("Maximum iterations: " + this.getMaxIter() );
		System.out.println("Starting point (x0): " + this.getX0());
		System.out.println("GSS maximum step size: " + this.getMaxStep() );
		System.out.println("GSS minimum step size: " + this.getMinStep() );
		System.out.println("GSS delta: " + getDelta() );	
		System.out.println("");
	}
	
	
	public void printStats() {
		
		System.out.println("");
		System.out.println("Statistical summary for SD with a golden section line search:");
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
			System.out.println("Detailed results for SD with a golden section line search:");
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
	
	private double GSS ( double a , double b , double c , double [] x , double [] dir , Polynomial P ) {
		
		double [] temp_c = new double[x.length];
		double [] temp_y1 = new double[x.length];
		double [] temp_y2 = new double[x.length];
		double [] first_p= new double[x.length];
		double [] second_p = new double[x.length];
		double y=0;
	
		
		//initial conditions
		int count = 0;
		while (count<x.length) {
			temp_c[count] = x[count] - (dir[count]*c );
			first_p[count] = x[count] - ( dir[count]*a);
			second_p[count] = x[count] - (dir[count]*b);
			count++;
		}
		
		//special considerations
		if (P.f(temp_c)>P.f(first_p) || P.f(temp_c)>P.f(second_p)) {
			if (P.f(second_p)>P.f(first_p)) {
				return a;
			} 
			else {
				return b;
			}	
		}//if
		
		//first case
		double bound1=b-c;
		double bound2=c-a;
		
		for(double n=0; n<getDelta(); n++) {	
		
		if ((bound1)<(bound2)){
			y = a+ ((bound2)/_PHI_);

			int count2=0;
			while (count2<x.length) {
				temp_y1[count2] = x[count2] - (dir[count2]*y); 
				count2++;
				}
			
			if( P.f(temp_c) > P.f(temp_y1) && P.f(first_p) > P.f(temp_y1)) {
				return GSS(a,c,y,x,dir,P); //this makes sense
			}
			
			else {
				return GSS(c,b,y,x,dir,P);	 //bounds change
			}
		}//if
		
		
		//second case
		else{
			y = b + ((bound1)/_PHI_);
			if (P.f(first_p) > P.f(temp_c) && P.f(second_p) > P.f(temp_c)) {
			
				int count3=0;
				while(count3<x.length) {
					temp_y2[count3] = x[count3] - (dir[count3]*y); 
					count3++;
				}
			
				if( P.f(temp_c) > P.f(temp_y2) && P.f(first_p) > P.f(temp_y2)) {
					return GSS(a,c,y,x,dir,P);
				}
				else {
					return GSS(c,b,y,x,dir,P);
				}	
			} //if
		    } //else
		} //for loop
		return y;
	} //gss
}
	




