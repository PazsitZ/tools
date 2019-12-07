package hu.pazsitz.tools.reflect.interfaceaccessor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Assert;

import org.junit.Test;

public class InterfaceAccessorTest {
    private static final String THE__JUNIOR = "The Junior";
    private static final String THE__MEDIOR = "The Medior";
    private static final String THE__SENIOR = "The Senior";

	@Test
	public void testInterfaceAccessor() {
		UserDto userData = new UserDto(THE__JUNIOR, 18, "Junior");
		UserDto2 userData2_2 = new UserDto2(THE__MEDIOR, "Engineer", "Medior");
		UserDto userData3 = new UserDto(THE__SENIOR, 48, "Senior");


        Assert.assertEquals(THE__JUNIOR, Stream.of(userData).map(UserDtoNameAware.ACCESSOR).findFirst().get());
        Assert.assertEquals(THE__MEDIOR, Stream.of(userData2_2).map(UserDtoNameAware.ACCESSOR).findFirst().get());
        Assert.assertEquals(THE__SENIOR, Stream.of(userData3).map(UserDtoNameAware.ACCESSOR).findFirst().get());
		List<Object> list = Arrays.asList(userData, userData2_2, userData3);
	}


    @Test
        //(expected = IllegalArgumentException.class)
	public void testInterfaceAccessorNoSuchMethod() {
		UserDto userData = new UserDto(THE__JUNIOR, 18, "Junior");
		UserDto userData2 = new UserDto(THE__MEDIOR, 28, "Medior");
		UserDto2 userData2_2 = new UserDto2(THE__MEDIOR+"2", "Engineer", "Medior");
		UserDto3 userData2_3 = new UserDto3(THE__MEDIOR+"2", "Engineer", "Medior");
		UserDto userData3 = new UserDto(THE__SENIOR, 48, "Senior");
		List<Object> list = Arrays.asList(userData, userData2, userData2_2, userData2_3, userData3);

		list.stream()
			.map(UserDtoNameAware.ACCESSOR)
			.forEach(System.out::println);
	}

}
