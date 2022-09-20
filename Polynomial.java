public class Polynomial {
	double[] coefficients;
	public Polynomial(){
		this.coefficients = new double[1];
		this.coefficients[0] = 0;
	}
	public Polynomial(double[] polynom){
		this.coefficients = new double[polynom.length];
		for(int i=0; i<polynom.length; i++){
			this.coefficients[i] = polynom[i];
		}
	}
	public Polynomial add(Polynomial polynom){
		double[] result = new double[Math.max(polynom.coefficients.length, this.coefficients.length)];
		if(polynom.coefficients.length >= this.coefficients.length){
			for(int i=0;i<polynom.coefficients.length;i++){
				result[i] = polynom.coefficients[i];
			}
			for(int i=0;i<this.coefficients.length;i++) {
				result[i] = result[i]+this.coefficients[i];
			}
		}
		if(polynom.coefficients.length<this.coefficients.length){
			for(int i=0;i<this.coefficients.length;i++){
				result[i] = this.coefficients[i];
			}
			for(int i=0;i<polynom.coefficients.length;i++) {
				result[i] = result[i]+polynom.coefficients[i];
			}
		}
		Polynomial newpolynomial = new Polynomial(result);
		return newpolynomial;
	}
	public double evaluate(double x) {
		double sum = 0;
		for (int i = 0; i < this.coefficients.length; i++) {
			sum = sum + (coefficients[i]*Math.pow(x, i));
		}
		return sum;
	}
	public boolean hasRoot(double x) {
		return this.evaluate(x) == 0;
	}
}
