import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import security_systems.*;

public abstract class HousingEstate<S, T> {
	private S mainSecuritySystem;
	private T[] subordinateSecuritySystems;

	public HousingEstate() {
		createGenericTypeSInstance();
		createGenericTypeSArrayInstance(10);
	}
	
	private void createGenericTypeSInstance() {
		try {
			Class<S> clazz = (Class<S>)ReflectionHelper.getGenericClassOfType(this, 0);
			mainSecuritySystem = ReflectionHelper.getDefaultInstance(clazz);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}	
	}
	
	private void createGenericTypeSArrayInstance(int arrLength) {
		Class<T> clazz = (Class<T>)ReflectionHelper.getGenericClassOfType(this, 1);
		subordinateSecuritySystems = (T[]) Array.newInstance(clazz, arrLength);
	}

	public S getMainSecuritySystem() {
		return mainSecuritySystem;
	}
	
	public boolean addSubordinateSecuritySystem(T securitySystem) {
		int i = 0;
		for (; i < subordinateSecuritySystems.length && subordinateSecuritySystems[i] != null; i++);
		if (i < subordinateSecuritySystems.length)
			subordinateSecuritySystems[i] = securitySystem;
		else
			return false;
		return true;				
	}
	
	public static void main(String[] args) {
		// instantiation of HousingEstate['s subclass] with empty anonymous subclass using {}, to allow getting stored generic type's class using ...getGenericSuperclass()...
		HousingEstate<SecuritySystem, VideoSurveillance> he1 = new HousingEstate<SecuritySystem, VideoSurveillance>(){};
		System.out.println("he1.getMainSecuritySystem() = " + he1.getMainSecuritySystem().toString());
		
		HousingEstate<Dog, VideoSurveillance> he2 = new HousingEstate<Dog, VideoSurveillance>(){};
    	Dog dog = he2.getMainSecuritySystem();
    	System.out.println("dog.getVicitmsCount() = " + dog.getVicitmsCount());
    	System.out.println("Added subordinate security system: " + he2.addSubordinateSecuritySystem(new VideoSurveillance()));
    	System.out.println(Arrays.toString(he2.subordinateSecuritySystems));
	}
}
