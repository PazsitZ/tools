package hu.pazsitz.tools.reflect.interfaceaccessor;

public class UserDto2 {
	private final String name;
	private final String job;
	private final String workTitle;
	
	public UserDto2(String name, String job, String workTitle) {
		this.name = name;
		this.job = job;
		this.workTitle = workTitle;
	}
	
	public String getName() {
		return name;
	}
	public String getJob() {
		return job;
	}
	public String getWorkTitle() {
		return workTitle;
	}

}
