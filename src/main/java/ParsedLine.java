import java.util.List;

public class ParsedLine {
    private double lineMaxWeight;
    List<PackageItem> packageItems;

    public ParsedLine(double maxLineWeight, List<PackageItem> packageItems) {
        this.lineMaxWeight=maxLineWeight;
        this.packageItems=packageItems;
    }

    public ParsedLine(List<PackageItem> packageItems) {
        this.packageItems=packageItems;
    }

    public List<PackageItem> getPackageItems() {
        return packageItems;
    }

    public void setPackageItems(List<PackageItem> packageItems) {
        this.packageItems = packageItems;
    }

    public double getLineMaxWeight() {
        return lineMaxWeight;
    }

    public void setLineMaxWeight(double lineMaxWeight) {
        this.lineMaxWeight = lineMaxWeight;
    }


}
