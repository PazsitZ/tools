package hu.pazsitz.tools.interfaceaccessor;

public final class UserDto implements UserDtoNameAware {
	private final String name;
	private final int age;
	private final String title;

	public UserDto(String name, int age, String title) {
		this.name = name;
		this.age = age;
		this.title = title;
	}

	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	public String getTitle() {
		return title;
	}



}
