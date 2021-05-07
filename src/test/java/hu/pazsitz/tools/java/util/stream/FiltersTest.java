package hu.pazsitz.tools.java.util.stream;

import org.hamcrest.MatcherAssert;
import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Filter;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.beans.HasPropertyWithValue.*;
import static org.hamcrest.core.Is.is;

public class FiltersTest {

	private List<NoEqualsForObject> list = new ArrayList<>();

	@Before
	public void initList() {
		list.add(new NoEqualsForObject("asd", "fgh"));
		list.add(new NoEqualsForObject("asd", "fgj"));
		list.add(new NoEqualsForObject("asd", "fgk"));
		list.add(new NoEqualsForObject("asd", "fgl"));
		list.add(new NoEqualsForObject("asf", "fgh"));
		list.add(new NoEqualsForObject("asf", "fgj"));
		list.add(new NoEqualsForObject("asf", "fgk"));
		list.add(new NoEqualsForObject("asg", "fgk"));
	}
	
	@Test
	public void testFilterByAttr1() {
		final String attribName = "attr1";
		Set<NoEqualsForObject> collect = list.stream()
				.filter(Filters.distinctBy(NoEqualsForObject::getAttr1))
				.collect(Collectors.toSet());

		assertThat(collect, containsInAnyOrder(
				hasProperty(attribName, is("asd")),
				hasProperty(attribName, is("asf")),
				hasProperty(attribName, is("asg"))
		));
	}

	@Test
	public void testFilterByAttr2() {
		final String attribName = "attr2";
		Set<NoEqualsForObject> collect = list.stream()
				.filter(Filters.distinctBy(NoEqualsForObject::getAttr2))
				.collect(Collectors.toSet());

		assertThat(collect, containsInAnyOrder(
				hasProperty(attribName, is("fgh")),
				hasProperty(attribName, is("fgj")),
				hasProperty(attribName, is("fgk")),
				hasProperty(attribName, is("fgl"))
		));
	}
}