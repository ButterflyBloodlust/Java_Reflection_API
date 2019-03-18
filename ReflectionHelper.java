import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

import javax.activation.UnsupportedDataTypeException;

public class ReflectionHelper {
	
	/** 
	 *	Used for getting class of generic type "stored" by parent of object. 
	*/
	public static Class<?> getGenericClassOfType(Object object, int typeIndex) {
        Class<?> clazz = (Class<?>) ((ParameterizedType) object.getClass().getGenericSuperclass()).getActualTypeArguments()[typeIndex];
        return clazz;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getDefaultInstance(Class<T> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
	    T instance = null;
	    Constructor<T>[] constructors = (Constructor<T>[]) clazz.getDeclaredConstructors();
	    Constructor<T> constructor = null;
	    
	    for (Constructor<T> cstr : constructors) {
	        if (cstr.getParameters().length == 0) {
	            constructor = (Constructor<T>) cstr;
	            break;
	        }
	    }
	    
	    if (constructor != null) {
	        constructor.setAccessible(true);
	        instance = constructor.newInstance();
	    }

	    return instance;
	}//public static <T> T getDefaultInstance(Class<T> clazz)
	
	/**
	 *	Used for deep copying objects. Null objects stay null and are not instantiated.
	 *	Note1: this is a very basic version, written for user created classes; may fail on some java.lang.* classes, among other things. 
	 *	Note2: this code was written to learn about reflection. In general, 3rd party reflection libraries are the way to go.
	 *	(for example, they do not require presence of default (or copy) constructor in a class, in order to deep copy it).
	 */
	//@SuppressWarnings("unchecked")
	public static <T> T getDeepCopyInstance(T oldInstance) throws IllegalAccessException, InstantiationException, InvocationTargetException, UnsupportedDataTypeException {
		
		T newInstance = null;
		//System.out.println("oldInstance = " + oldInstance);	// DEBUG
		
		if (oldInstance != null) {
			Class<T> clazz = (Class<T>)oldInstance.getClass();
			//System.out.println("clazz : " + clazz);	// DEBUG
			newInstance = (T)getDefaultInstance(clazz);		// (T)clazz.newInstance() will not work on private default constructor
			
			// If object has copy constructor - call it.
    		try {
				Constructor<?> con = clazz.getDeclaredConstructor(clazz);
				if (con != null) {
					return (T)con.newInstance(oldInstance);
				}
			} catch (NoSuchMethodException | SecurityException e) {
				//e.printStackTrace();
			}
    		
    		// java.lang.* objects that don't have copy constructor tend to crash this method when working with fields - need special handling
    		if (isJavaLang(oldInstance)) {
    			if (oldInstance instanceof Integer) {
    				return (T)new Integer((Integer)oldInstance);
    			}
    			else if (oldInstance instanceof Double) {
    				return (T)new Double((Double)oldInstance);
    			}
    			else if (oldInstance instanceof Float) {
    				return (T)new Float((Float)oldInstance);
    			}
    			else {
    				throw new UnsupportedDataTypeException(clazz + " is not supported");
    			}
    		}//if (isJavaLang(oldInstance))
			
	    	Class<?> current = clazz;
	    	while (current.getSuperclass() != null) {	// make sure to get fields of parent classes as well

		    	Field[] firstFields = current.getDeclaredFields();
		    	//System.out.println(Arrays.toString(firstFields));	// DEBUG
		    	for (Field field : firstFields) {
		    		field.setAccessible(true);
		    		if (field.getType().isPrimitive() == true) {
		    			//System.out.println(field.toString() + "  is Primitive");	// DEBUG
		    			field.set(newInstance, field.get(oldInstance));
		    		}
		    		else {
		    			//System.out.println(field.toString() + "  is NOT Primitive");	// DEBUG
		    			field.set(newInstance, getDeepCopyInstance(field.get(oldInstance)));
		    		}
		    	}//for (Field field : firstFields)
		    	current = current.getSuperclass();
	    	}//while (current.getSuperclass() != null)
		}//if (oldInstance != null)
		
		return newInstance;
	}//public static <T> T getDeepCopyInstance(T oldInstance)
	
	public static boolean isJavaLang(Object check) {
	    return check.getClass().getName().startsWith("java.lang");
	}
	
	public static boolean isStaticFinal(Field field) {
		int modifiers = field.getModifiers();
		return (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
	}
}
