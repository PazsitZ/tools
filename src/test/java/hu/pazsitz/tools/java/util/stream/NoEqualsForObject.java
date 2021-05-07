package hu.pazsitz.tools.java.util.stream;

public class NoEqualsForObject {
    private String attr1;
    private String attr2;

    public NoEqualsForObject(String attr1, String attr2) {
        this.attr1 = attr1;
        this.attr2 = attr2;
    }

    public String getAttr1() {
        return attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    @Override
    public String toString() {
        return "{" + attr1 + ":" + attr2 +  "}";
    }
}
