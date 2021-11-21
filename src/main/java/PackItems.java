import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import util.Pair;

import javax.swing.plaf.IconUIResource;
import java.util.*;
import java.util.stream.Collectors;

public class PackItems {

    public static String findOptimumPackage(Pair<Integer, List<PackageItem>> items) {



//        Iterator<PackageItem> iterator=items.right.iterator();
        List<Integer> indexes = new ArrayList<>();
        List<Integer> costs = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();

        SubList optCost = new SubList();                // optimal solution so far

        Set<SubList> sums = new HashSet<>();
        sums.add(optCost);
        int maxCost=Constants.MAX_COST;

        PackageItem packageItems;

       for(PackageItem packageItem:items.right) {
           packageItems=packageItem;
           costs.add((int)packageItem.getCost());
           indexes.add(packageItem.getIndex());
           weights.add((int)packageItem.getWeight());

        }
        Integer maxWeight= items.left;

        for (Integer index:indexes){
                for (Integer cost : costs) {

                    Set<SubList> newSums = new HashSet<>();
                    for (SubList sum : sums) {
                        List<Integer> newSublist = new ArrayList<>(sum.subList);
                        newSublist.add(index);
                        SubList newSum = new SubList(sum.size + cost, newSublist);
                        if (newSum.size <= maxCost) {
                            newSums.add(newSum);
                            if (newSum.size > optCost.size) {
                                optCost = newSum;
                            }

                        }

                    }
                    sums.addAll(newSums);
                    index++;
                }

       }

        SubList optWeight = new SubList();                // optimal weight so far

        Set<SubList> weightSums = new HashSet<>();
        weightSums.add(optWeight);

        for (Integer index:indexes){
            for (Integer weight : weights) {

                Set<SubList> newSums = new HashSet<>();
                for (SubList sum : weightSums) {
                    List<Integer> newSublist = new ArrayList<>(sum.subList);
                    newSublist.add(index);
                    SubList newSum = new SubList(sum.size + weight, newSublist);
                    if (newSum.size <= maxWeight) {
                        newSums.add(newSum);
                        if (newSum.size > optWeight.size) {
                            optWeight = newSum;
                        }

                    }

                }
                sums.addAll(newSums);
                index++;
            }

        }

//        String result =
//                indexes.stream()
//                        .mapToInt(i -> i)
//                        .sorted()
//                        .mapToObj(index -> Integer.toString(index))
//                        .collect(Collectors.joining(","));

        return optCost.subList.toString() + " " + optWeight.size.toString();
    }

    private static class SubList{
        public Integer size;
        public List<Integer> subList;

        public SubList() {
            this(0, new ArrayList<>());
        }

        public SubList(int size, List<Integer> subList) {
            this.size = size;
            this.subList = subList;
        }
    }
}
