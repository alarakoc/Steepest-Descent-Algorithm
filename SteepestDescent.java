
import java.util.ArrayList;
import java.util.Arrays;

public class SteepestDescent {
	
	private double eps ; // tolerance
	private int maxIter ; // maximum number of iterations
	private double x0 ; // starting point
	private double [] x1 ; // no. of iterations needed for all polynomials
	private ArrayList < double [] > bestPoint ; // best point found for all polynomials
	private double [] bestObjVal ; // best obj fn value found for all polynomials
	private double [] bestGradNorm ; // best gradient norm found for all polynomials
	private long [] compTime ; // computation time needed for all polynomials
	private int [] nIter ; // no. of iterations needed for all polynomials
	private boolean resultsExist ; // whether or not results exist
	
	// constructors
	public SteepestDescent () {
		this.maxIter = 100;
		//this.stepSize = 0.05;
		this.eps = 0.001;
		this.x0 = 1.00;
		this.setHasResults(false);
	}
	
	public SteepestDescent ( double eps , int maxIter , double x0 ) {
		this.maxIter = maxIter;
		//this.stepSize = stepSize;
		this.eps = eps;
		this.x0 = x0; 
		this.setHasResults(false);
	}
	
	// getters
	public double getEps () {
		return eps;
	}
	
	public int getMaxIter () {
		return maxIter;
	}
	
	public double getX0 () {
		return x0;
	}
	public double [] getX1 () {
		return x1;
	}
	
	public double [] getBestObjVal () {
		return bestObjVal;
	}
	
	public double [] getBestGradNorm () {
		return bestGradNorm;
	}
	
	public double [] getBestPoint ( int i ) {
		return  bestPoint.get(i); 
	}
	
	public int [] getNIter () {
		return nIter;
	}
	
	public long [] getCompTime () {
		return compTime;
	}
	
	public boolean hasResults () {
		return resultsExist;
	}
	
	
	// setters
	public void setEps ( double a ) {
		eps = a;
	}
	
	public void setMaxIter ( int a ) {
		maxIter=a;
	}
	
	public void setX0 ( double a ) {
		x0=a;
	}
	
	public void setX1 ( int i , double a ) {
		x1[i] = a;
	}
	
	public void setBestObjVal ( int i , double a ) {
		bestObjVal[i] = a;	
	}
	
	public void setBestGradNorm ( int i , double a ) {
		bestGradNorm[i]	= a;
	}
	
	public void setBestPoint ( int i , double [] a ) {
		bestPoint.set(i, a); 
	}
	
	public void setCompTime ( int i , long a ) {
		compTime[i]=a;
	}
	
	public void setNIter ( int i , int a ) {
		nIter[i]=a;
	}
	
	public void setHasResults ( boolean a ) {
		resultsExist=a;	
	}
	
	// other methods
	public void init ( ArrayList < Polynomial > P ) { // init member arrays to correct size
		bestObjVal = new double[P.size()];	
		bestGradNorm = new double[P.size()];		
		compTime = new long[P.size()];	
		nIter = new int[P.size()];	
		bestPoint= new ArrayList <double[]>();
		for (int k = 0; k < P.size(); k++) {
			  bestPoint.add(bestObjVal);
		}
	}
	
	public void run ( int i , Polynomial P ) { // run the steepest descent algorithm
		getUser(P.getN()); //before using the part with x0, make sure to implement this function
		this.setHasResults(false);

		double[] temp_array;
		double[] temp_best;
		double[] old_x0;
		this.setBestPoint(i, this.getX1());

		old_x0 = new double[P.getN()];
		temp_array = new double[P.getN()];
		temp_best = new double[P.getN()];
		temp_array = Arrays.copyOf(this.direction(P, this.getX1()) , P.getN()); 
		temp_best = Arrays.copyOf(this.getX1() , P.getN()); 
		
		
		
		long start = System . currentTimeMillis () ; // Get current time
		
		for (int num_iter=0; num_iter<=this.getMaxIter(); num_iter++) {

			temp_array = Arrays.copyOf(this.direction(P, this.getBestPoint(i)) , P.getN()); 
			temp_best = Arrays.copyOf(this.getBestPoint(i) , P.getN()); 
			old_x0 = Arrays.copyOf(this.getBestPoint(i) , P.getN()); 

			this.setBestObjVal(i, P.f(this.getBestPoint(i)));
			this.setBestGradNorm(i, P.gradientNorm(this.getBestPoint(i)));
			this.setNIter(i, num_iter);
			
			if (lineSearch(P,old_x0) == Double.POSITIVE_INFINITY) {
				for (int num_var = 0; num_var<P.getN(); num_var++){
					temp_best[num_var] = this.getBestPoint(i)[num_var]+0*temp_array[num_var];	
			}
			}
			
			else {
			for (int num_var = 0; num_var<P.getN(); num_var++){
				temp_best[num_var] = this.getBestPoint(i)[num_var]+lineSearch(P,old_x0)*temp_array[num_var];
			}
			}
			
			this.setBestPoint(i, temp_best); //temp_best is used to assign the array to best array 
			
			long elapsedTime = System . currentTimeMillis () - start ; // Get elapsed time in ms
			this.setCompTime(i, elapsedTime);
		
			//check if we made it
			if (this.getBestGradNorm()[i]<=this.getEps()) {
				this.setHasResults(true);
			}
			
			//check infinities
			for (int j=0; j<P.getN(); j++) {
				if ((Double.isNaN(old_x0[j])) || (Double.isNaN(this.getBestGradNorm()[i])) || (old_x0[j]>=Double.MAX_VALUE) || (old_x0[j]<=Double.MAX_VALUE*(-1))) {
					this.setHasResults(true);
				}
				if (this.getBestPoint(i)[j]>=Double.MAX_VALUE) {
					old_x0[j] = Double.POSITIVE_INFINITY;
				}
				if (this.getBestPoint(i)[j]<=Double.MAX_VALUE*(-1)) {
					old_x0[j] = Double.NEGATIVE_INFINITY;
				}
				if (this.getBestGradNorm()[i]>=Double.MAX_VALUE) {
					this.getBestGradNorm()[i] = Double.POSITIVE_INFINITY;
				}
				if (this.getBestGradNorm()[i]<=Double.MAX_VALUE*(-1)) {
					this.getBestGradNorm()[i] = Double.NEGATIVE_INFINITY;
				}
			}
			
			if (this.hasResults()) {
				break;
			}	
		}
		
		
		this.setHasResults(true);
		this.setBestPoint(i, old_x0); //temp_best is used to assign the array to best array 
		
		if(lineSearch(P, old_x0) == Double.POSITIVE_INFINITY) {
			System.out.println("   Armijo line search did not converge!");
			this.setNIter(i,1);	
			}
		
		System.out.print("Polynomial ") ;
		System.out.print(i+1) ;
		System.out.print(" done in ");
		System.out.print(this.getCompTime()[i]) ;
		System.out.print("ms.");
		System.out.println("");

	}
	
	public double lineSearch ( Polynomial P , double [] x ) {// find the next step size
		//literally can return any number (double)
		return 10; // overrides so no problem
	}
	
	public double [] direction ( Polynomial P , double [] x ) { // find the next direction
		double[] temp_array;
		temp_array = Arrays.copyOf(P.gradient(x), P.gradient(x).length); 
		for (int i=0;i<P.gradient(x).length;i++) {
			temp_array[i] = temp_array[i]*(-1);
		}
		return temp_array;
	}
	
	public boolean getParamsUser () { // get parameters from user , return success
		return true; // overrides so no problem
		}
	
	public void getUser ( int n ) { // use this function to set all elements in X1 to X0
		x1 = new double[n];
		for (int j=0; j<n; j++) {
			this.setX1(j, this.getX0());
		}
		return;
	}
	
	public void print () { // print algorithm parameters
		// overrides so no problem
		//returns void anyways
	}
	
	public void printStats () {// print statistical summary of results
		// overrides so no problem
		//returns void anyways
	}
	
	public void printAll () { // print final results for all polynomials
		// overrides so no problem
		//returns void anyways
	}
	
	public void printSingleResult ( int i , boolean rowOnly ) {// print final result for
		// overrides so no problem
		//returns void anyways
	}

}

