import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;


public class Polynomial {
	double[] coefficients;
	int[] exponents;
	public Polynomial(){
		this.coefficients = new double[1];
		this.coefficients[0] = 0;
		this.exponents = new int[1];
		this.exponents[0]=0;
	}
	public Polynomial(double[] coefficients, int[] exponents){
		this.coefficients = new double[coefficients.length];
		this.exponents = new int[exponents.length];
		for(int i=0; i<coefficients.length; i++){
			this.coefficients[i] = coefficients[i];
		}
		for(int i=0; i<exponents.length; i++){
			this.exponents[i] = exponents[i];
		} 
	}
	public Polynomial(File file) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(file));
		String polynomial = input.readLine();
		int count=0;
		for(int i=0;i<polynomial.length();i++) {
			if(polynomial.charAt(i)=='+' || polynomial.charAt(i)=='-') {
				count++;
			}
		}
		int[] plusminus;
		int j=0;
		if(polynomial.charAt(0)=='-') {
			plusminus = new int[count];
			for(int i=0;i<polynomial.length();i++) {
				if(polynomial.charAt(i)=='+') {
					plusminus[j]=1;
					j++;
				}
				if(polynomial.charAt(i)=='-') {
					plusminus[j]=-1;
					j++;
				}
			}
		}
		else {
			plusminus = new int[count+1];
			plusminus[0] = 1;
			for(int i=0;i<polynomial.length();i++) {
				if(polynomial.charAt(i)=='+') {
					plusminus[j+1]=1;
					j++;
				}
				if(polynomial.charAt(i)=='-') {
					plusminus[j+1]=-1;
					j++;
				}
			}
		}

		

		polynomial = polynomial.replace('+', ' ');
		polynomial = polynomial.replace('-', ' ');
		String[] terms = polynomial.split(" ");

		if(terms[0]=="") {
			this.coefficients = new double[terms.length-1];
			this.exponents = new int[terms.length-1];
		}
		else {
			this.coefficients = new double[terms.length];
			this.exponents = new int[terms.length];
		}
		double coefficient;
		int exponent;
		for(int i=0;i<coefficients.length;i++) {
			if(terms[0]=="") {
				if(terms[i+1].charAt(0) =='x') coefficient = 1;
				else if(terms[i+1].indexOf('x')!=-1) {
					coefficient = Double.parseDouble(terms[i+1].substring(0, terms[i+1].indexOf('x')));
				}
				else{
					coefficient = Double.parseDouble(terms[i+1].substring(0, terms[i+1].length()));
				}
				coefficient = coefficient * plusminus[i];
				if(terms[i+1].charAt(terms[i+1].length()-1)=='x') exponent = 1;
				else if(terms[i+1].indexOf('x')==-1) exponent = 0;
				else exponent = Integer.parseInt(terms[i+1].substring(terms[i+1].indexOf('x')+1, terms[i+1].length()));
			}
			else {
				if(terms[i].charAt(0) =='x') coefficient = 1;
				else if(terms[i].indexOf('x')!=-1) {
					coefficient = Double.parseDouble(terms[i].substring(0, terms[i].indexOf('x')));
				}
				else{
					coefficient = Double.parseDouble(terms[i].substring(0, terms[i].length()));
				}
				coefficient = coefficient * plusminus[i];
				if(terms[i].charAt(terms[i].length()-1)=='x') exponent = 1;
				else if(terms[i].indexOf('x')==-1) exponent = 0;
				else exponent = Integer.parseInt(terms[i].substring(terms[i].indexOf('x')+1, terms[i].length()));		
			}
			this.coefficients[i] = coefficient;
			this.exponents[i] = exponent;
		}
		input.close();
	}
	public Polynomial add(Polynomial polynom){
		Polynomial newpolynomial;
		int[] exponents;
		double[] coefficients;
		//MAXIMUM POSSIBLE LENGTH FOR EXPONENTS ARRAY
		int[] temp = new int[this.exponents.length + polynom.exponents.length];
		//INITIALIZING TEMP
		for(int i=0; i<temp.length; i++) {
			temp[i] = -1;
		}
		//Adding all exponents from calling polynomial to temp
		for(int i=0; i<this.exponents.length;i++) {
			temp[i] = this.exponents[i];
		}
		//Adding all exponents from polynom that aren't in calling polynomial to temp
		for(int i=0; i<polynom.exponents.length; i++) {
			for(int j=0; j<temp.length;j++) {
				if(polynom.exponents[i]==temp[j]) break;
				if(temp[j] == -1) {
					temp[j] = polynom.exponents[i];
					break;
				}
			}
		}
		int length=0;
		for(int i=0;i<temp.length;i++) {
			if(temp[i] != -1) length++;
		}
		exponents = new int[length];
		coefficients = new double[length];
		for(int i=0;i<length;i++) {
			exponents[i] = temp[i];
		}
		Arrays.sort(exponents);
		double sum;
		for(int i=0;i<length;i++) {
			sum = 0;
			for(int j=0;j<this.exponents.length;j++) {
				if(exponents[i] == this.exponents[j]) sum = sum+this.coefficients[j];
			}
			for(int j=0;j<polynom.exponents.length;j++) {
				if(exponents[i] == polynom.exponents[j]) sum = sum+polynom.coefficients[j];
			}
			coefficients[i] = sum;
		}
		newpolynomial = new Polynomial(coefficients, exponents);
		return newpolynomial;
	}
	public Polynomial multiply(Polynomial polynom) {
		Polynomial[] array = new Polynomial[this.coefficients.length];
		for(int i=0;i<array.length;i++) {
			array[i] = new Polynomial(polynom.coefficients, polynom.exponents);
		}
		for(int i=0;i<array.length;i++) {
			for(int j=0;j<array[i].coefficients.length;j++) {
				array[i].coefficients[j] = array[i].coefficients[j]*this.coefficients[i];
				array[i].exponents[j] = array[i].exponents[j] + this.exponents[i];
			}
		}
		Polynomial result = new Polynomial(array[0].coefficients, array[0].exponents);
		for(int i=1;i<array.length;i++) {
			result = result.add(array[i]);
		}
		return result;
	}
	public void saveToFile(String filename) throws IOException {
		String polynomial = "";
		String toadd;
		for(int i=0;i<this.coefficients.length;i++) {
			toadd = "";
			if(i!=0) toadd = toadd + "+";
			if(coefficients[i] !=1 && coefficients[i] % 1 ==0) toadd = toadd + Integer.toString((int)coefficients[i]); 
			else if(coefficients[i]!=1) toadd = toadd + Double.toString(coefficients[i]);
			if(exponents[i]!=0) toadd = toadd + "x";
			if(exponents[i]!=1) toadd = toadd + Integer.toString(exponents[i]);
			polynomial=polynomial + toadd;
		}
		FileWriter fileWriter = new FileWriter(filename);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print(polynomial);
		printWriter.close();
		fileWriter.close();
	}
 	public void display() {
		for(int i=0;i<this.exponents.length;i++) {
			if(i+1 >= this.exponents.length) System.out.print(this.coefficients[i]+"x^"+this.exponents[i]);
			else System.out.print(this.coefficients[i]+"x^"+this.exponents[i]+" + ");
		}
		System.out.println();
	}
	
	public double evaluate(double x) {
		double sum = 0;
		for (int i = 0; i < this.coefficients.length; i++) {
			sum = sum + (this.coefficients[i]*Math.pow(x, this.exponents[i]));
		}
		return sum;
	}
	public boolean hasRoot(double x) {
		return this.evaluate(x) == 0;
	}
}