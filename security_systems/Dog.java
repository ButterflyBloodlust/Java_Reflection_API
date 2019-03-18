package security_systems;

public class Dog extends SecuritySystem{

	int victimsCount;
	
	public Dog(boolean isPassive) {
		super(isPassive);
		
	}
	
	public Dog() {
		super(false);
	}
	
	public int getVicitmsCount() { return victimsCount; }
	
	public void setVicitmsCount(int victimsCount) { this.victimsCount = victimsCount; }
	
	public String toString() {
		return super.toString() + " : " + "victimsCount = " + victimsCount;
	}
}
