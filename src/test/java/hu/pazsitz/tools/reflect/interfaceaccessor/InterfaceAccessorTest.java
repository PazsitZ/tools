package hu.pazsitz.tools.reflect.interfaceaccessor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        Assert.assertEquals(THE__JUNIOR, UserDtoNameAware.ACCESSOR.apply(userData));
        Assert.assertEquals(THE__MEDIOR, UserDtoNameAware.ACCESSOR.apply(userData2_2));
        Assert.assertEquals(THE__SENIOR, UserDtoNameAware.ACCESSOR.apply(userData3));
	}

    @Test(expected = IllegalArgumentException.class)
	public void testInterfaceAccessorDifferentReturntype() {
		UserDto3 userData2_3 = new UserDto3(THE__MEDIOR, "Engineer", "Medior");

		Assert.assertEquals(THE__MEDIOR, UserDtoNameAware.ACCESSOR.apply(userData2_3));
	}
    
    @Test(expected = IllegalArgumentException.class)
    public void testInterfaceAccessorNoSuchMethod() {
    	String anyClass = new String();
    	
    	Assert.assertEquals("", UserDtoNameAware.ACCESSOR.apply(anyClass));
    }

    @Test
	public void testInterfaceAccessorMap() {
		UserDto userData = new UserDto(THE__JUNIOR, 18, "Junior");
		UserDto2 userData2_2 = new UserDto2(THE__MEDIOR, "Engineer", "Medior");
		UserDto userData3 = new UserDto(THE__SENIOR, 48, "Senior");

        List<Object> list = Arrays.asList(userData, userData2_2, userData3);
        
        List<String> names = list.stream()
        	.map(UserDtoNameAware.ACCESSOR)
        	.collect(Collectors.toList());
        
        Assert.assertTrue(names.contains(THE__JUNIOR));
        Assert.assertTrue(names.contains(THE__MEDIOR));
        Assert.assertTrue(names.contains(THE__SENIOR));
	}
}

class UserDto implements UserDtoNameAware {
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

class UserDto2 {
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

class UserDto3 {
    public UserDto3(String the_Medior2, String engineer, String medior) {
    }

    public int getName() {
        return 0;
    }
}