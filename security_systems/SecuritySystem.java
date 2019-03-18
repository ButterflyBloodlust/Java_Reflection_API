package security_systems;

public class SecuritySystem {
	
	private boolean isPassive;
	
	public SecuritySystem(boolean isPassive) {
		this.setPassive(isPassive);
	}
	
	public SecuritySystem() {
		this.setPassive(true);
		//System.out.println("in SecuritySystem constructor");
	}

	public boolean isPassive() {
		return isPassive;
	}

	public void setPassive(boolean isPassive) {
		this.isPassive = isPassive;
	}
	
	public String toString() {
		return super.toString() + " : " + "isPassive = " + isPassive;
	}
	
}
