public class Constants {

    protected final static String INDEX = "index";
    protected final static String WEIGHT = "weight";
    protected final static String COST = "cost";
    protected final static String PACKAGE_REGEX = "\\((?<" + INDEX + ">\\d+)\\,(?<" + WEIGHT + ">\\d+(\\.\\d{1,2})?)\\,€(?<" + COST + ">\\d+(\\.\\d{1,2})?)\\)";
    protected final static int MAX_ITEMS_IN_LINE = 15;
    protected final static int MAX_WEIGHT = 100 * 100;
    protected final static int MAX_COST = 100 * 100;
}
