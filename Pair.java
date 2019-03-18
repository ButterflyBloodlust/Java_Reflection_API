
import java.lang.reflect.InvocationTargetException;

import javax.activation.UnsupportedDataTypeException;

import security_systems.*;

public class Pair<T> implements Cloneable{

	private final T first;
	private final T second;
	
	// default constructor to enable deep copy via reflection; this can be omitted when using ReflectionFactory or 3rd party libraries
	private Pair() { first = null; second = null; }
	
	public Pair(T first, T second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		
	    Pair<T> pair = null;
	    //T newFirst = null;
	    //T newSecond = null;
	    try {
	    	//newFirst = ReflectionHelper.getDeepCopyInstance(first);
			//newSecond = ReflectionHelper.getDeepCopyInstance(second);
			pair = ReflectionHelper.getDeepCopyInstance(this);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | UnsupportedDataTypeException e) {
			e.printStackTrace();
		}
		return pair;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws CloneNotSupportedException{
		
		System.out.println(">> Pair<Dog> demo: \n");
		
		Pair<Dog> pair1 = new Pair<>(new Dog(), null);
		System.out.println("Executing: pair1.first.setVicitmsCount(55) ...\n");
		pair1.first.setVicitmsCount(55);
		System.out.println("pair1.first = " + pair1.first.toString());
		
		System.out.println("\nCloning Pair ...\n");
		Pair<Dog> pair2 = (Pair<Dog>) pair1.clone();
		
		System.out.println("Executing: pair1.first.setVicitmsCount(1) ...\n");
		pair1.first.setVicitmsCount(1);
		
		System.out.println("pair1.first = " + pair1.first.toString());
		System.out.println("pair2.first = " + pair2.first.toString());
		System.out.println("pair2.second = " + pair2.second);
		
		
		System.out.println("\n>> Pair<HousingEstate<Dog, VideoSurveillance>> demo: \n");
		
		Pair<HousingEstate<Dog, VideoSurveillance>> pairHE1 = 
				new Pair<>(new HousingEstate<Dog, VideoSurveillance>(){}, new HousingEstate<Dog, VideoSurveillance>(){});
		
		System.out.println("Executing: pairHE1.first.getMainSecuritySystem().setVicitmsCount(89) ...\n");
		pairHE1.first.getMainSecuritySystem().setVicitmsCount(89);
		System.out.println("Executing: pairHE1.first.getMainSecuritySystem().setPassive(true) ...\n");
		pairHE1.first.getMainSecuritySystem().setPassive(true);
		System.out.println("pairHE1.first.getMainSecuritySystem() = " + pairHE1.first.getMainSecuritySystem().toString());
		
		System.out.println("\nCloning Pair ...\n");
		Pair<HousingEstate<Dog, VideoSurveillance>> pairHE2 = (Pair<HousingEstate<Dog, VideoSurveillance>>)pairHE1.clone();
		
		System.out.println("Executing: pairHE1.first.getMainSecuritySystem().setVicitmsCount(70) ...\n");
		pairHE1.first.getMainSecuritySystem().setVicitmsCount(70);
		
		System.out.println("pairHE1.first.getMainSecuritySystem() = " + pairHE1.first.getMainSecuritySystem().toString());
		System.out.println("pairHE2.first.getMainSecuritySystem() = " + pairHE2.first.getMainSecuritySystem().toString());

	}
}
