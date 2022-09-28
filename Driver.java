import java.io.File;
import java.io.IOException;

public class Driver {

	public static void main(String[] args) throws IOException {
	    double[] d1 = {2, 8, 7};
	    int[] i1 = {0, 1, 2};
	    double[] d2 = {5, 6, 8};
	    int[] i2 = {0, 1, 2};
	    Polynomial t1 = new Polynomial(d1, i1);
	    Polynomial t2 = new Polynomial(d2, i2);
	    Polynomial t = t1.multiply(t2);
	    System.out.println("t(0.5) = " + t.evaluate(0.5));
	    File f = new File("C:\\Users\\jsdan\\eclipse-workspace\\Lab2\\polynomial.txt");
	    Polynomial a = new Polynomial(f);
	    a.display();
	    
	    a.saveToFile("mypoly.txt");
	}
}