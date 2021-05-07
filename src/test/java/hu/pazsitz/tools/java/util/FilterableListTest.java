package hu.pazsitz.tools.java.util;

import hu.pazsitz.tools.java.util.stream.Collectors;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class FilterableListTest {
    private static ArrayList<String> collection = new ArrayList() {{ add(null); add("asd"); add("fgh"); add("jkl"); add(null); }};
    private static ArrayList<String> collectionWithoutNulls = new ArrayList<>();

    private FilterableList<String> list;
    private ArrayList<String> regularList;

    @Before
    public void init() {
        collectionWithoutNulls.addAll(collection);
        collectionWithoutNulls.removeAll(Collections.singleton(null));
        list = new FilterableList<>();
        regularList = new ArrayList<>();
    }

    @Test
    public void testAdd( ) {
        list.add("");
        list.add("asd");
        list.add(null);
        list.add("fgh");
        list.add("jkl");
        list.add(null);

        Assert.assertEquals(4, list.size());
    }


    @Test
    public void testAddAsCollector() {
        FilterableList<String> nonNullList = collection.stream().collect(Collectors.toNonNullList());

        Assert.assertEquals(3, nonNullList.size());
    }

    @Test
    public void testAddIndexed( ) {
        regularList.add("");
        regularList.add("asd");
        regularList.add("fgh");
        regularList.add("jkl");

        list.add(0, "");
        list.add(1, "asd");
        list.add(2, null);
        list.add(2, "fgh");
        list.add(3, "jkl");
        list.add(4, null);

        Assert.assertEquals(4, list.size());

        MatcherAssert.assertThat(list, Matchers.contains(regularList.toArray()));
    }

    @Test
    public void testAll( ) {
        list.add("");
        list.add("asd");
        list.addAll(collection);
        list.add(null);

        Assert.assertEquals(5, list.size());
    }

    @Test
    public void testAllIndexed( ) {
        int index = 1;
        regularList.add("");
        regularList.add("qwe");
        regularList.add("rty");
        regularList.addAll(index, collectionWithoutNulls);
        regularList.add("uio");

        list.add("");
        list.add("qwe");
        list.add("rty");
        list.addAll(index, collection);
        list.add("uio");
        list.add(null);

        MatcherAssert.assertThat(list, Matchers.contains(regularList.toArray()));
    }

    @Test
    public void testPredicate() {
        list = new FilterableList<>(new ArrayList<>(), StringUtils::isNotBlank);
        list.add("qwe");
        list.add(null);
        list.add("");
        list.add(" ");

        Assert.assertEquals(1, list.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFilterAction() {
        FilterableList.FilterAction action = () -> { throw new IllegalArgumentException("invalid value appended"); };

        list = new FilterableList<>(new ArrayList<>(), StringUtils::isNotBlank, action);
        list.add("qwe");
        list.add("");
        list.add(" ");

        Assert.assertEquals(1, list.size());

        list.add(null);
    }

}
