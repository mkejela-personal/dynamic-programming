package util;

public class PackageException extends Exception{
    public static final int EMPTY_FILE=1000;
    public static final int INVALID_MAX_WEIGHT=1001;
    public static final int INVALID_ITEM_FORMAT=1002;
    public static final int MAX_PACKAGE_WEIGHT_EXCEEDED=1003;
    public static final int MAX_PACKAGE_COST_EXCEEDED = 1004;
    public static final int INVALID_INDEX_VALUE = 1005;

    public final int code;

    public PackageException(String message, int code) {
        super(message);
        this.code=code;
    }
}
