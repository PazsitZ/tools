package hu.pazsitz.tools.interfaceaccessor;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class InterfaceAccessorTest {

	@Test
	public void testInterfaceAccessor() {
		UserDto userData = new UserDto("The Junior", 18, "Junior");
		UserDto userData2 = new UserDto("The Medior", 28, "Medior");
		UserDto2 userData2_2 = new UserDto2("The Medior2", "Engineer", "Medior");
		UserDto userData3 = new UserDto("The Senior", 48, "Senior");
		List<Object> list = Arrays.asList(userData, userData2, userData2_2, userData3);

		list.stream()
			.map(UserDtoNameAware.ACCESSOR)
			.forEach(System.out::println);
	}


    @Test(expected = IllegalArgumentException.class)
	public void testInterfaceAccessorNoSuchMethod() {
		UserDto userData = new UserDto("The Junior", 18, "Junior");
		UserDto userData2 = new UserDto("The Medior", 28, "Medior");
		UserDto2 userData2_2 = new UserDto2("The Medior2", "Engineer", "Medior");
		UserDto3 userData2_3 = new UserDto3("The Medior2", "Engineer", "Medior");
		UserDto userData3 = new UserDto("The Senior", 48, "Senior");
		List<Object> list = Arrays.asList(userData, userData2, userData2_2, userData2_3, userData3);

		list.stream()
			.map(UserDtoNameAware.ACCESSOR)
			.forEach(System.out::println);
	}

}
