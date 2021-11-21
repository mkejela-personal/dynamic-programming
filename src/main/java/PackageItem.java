
public class PackageItem {
    private int index;
    private  double weight;
    private double cost;

    public PackageItem(Integer index, Double weight, Double cost) {
        this.index=index;
        this.weight=weight;
        this.cost=cost;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return  " " + index +  " " + weight + " " + cost;
    }


}
