package security_systems;

public class VideoSurveillance extends SecuritySystem{
	
	int cameraCount;

	public VideoSurveillance(boolean isPassive) {
		super(isPassive);
	}
	
	public VideoSurveillance() {
		super(true);
	}
	
	public String toString() {
		return super.toString() + " : " + "cameraCount = " + cameraCount;
	}

}
