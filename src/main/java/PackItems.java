import util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class PackItems {

    public static String findOptimumPackage(Pair<Integer, List<PackageItem>> items) {


        List<Integer> indexes = new ArrayList<>();
        List<Integer> costs = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();

        PackageItem packageItems = null;

        Map<Integer,Integer> indexWeights = new HashMap<>();
        Map<Integer,Integer> indexCosts = new HashMap<>();
        int maxWeight=items.left;

        // iterate through the items to determine list of weight and list of costs.
        for (PackageItem packageItem : items.right) {
            packageItems = packageItem;
            indexWeights.put(packageItems.getIndex(), (int)packageItems.getWeight());
            indexCosts.put(packageItems.getIndex(), (int)packageItems.getCost());
            costs.add((int) packageItems.getCost());
            indexes.add(packageItems.getIndex());
            weights.add((int) packageItems.getWeight());
        }

        Integer maxCost=items.left;

        SubList optWeight = new SubList();                // determin list of indices whose sum of weight value doesn't exceed maxWeight
        Set<SubList> weightSum = new HashSet<>();
        weightSum.add(optWeight);
        List<Integer> optIndices=new ArrayList<>();
        int wCounter=1;
        for(Integer weight: weights){
            Set<SubList> newWeightSum=new HashSet<>();
            weightSum.add(optWeight);
            for(SubList sum:weightSum){
                List<Integer> newSublist = new ArrayList<>(optIndices);

                SubList newSum=new SubList(sum.size+weight,newSublist);

                if (newSum.size<=maxWeight){
                    optIndices=newSublist;
                    newSublist.add(wCounter);
                    newWeightSum.add(newSum);
                }

            }
            weightSum.addAll(newWeightSum);
            wCounter++;

        }

        List<Integer> optIndicesNoDuplicate=optIndices.stream().distinct().collect(Collectors.toList()); // all the indices who we are sure will get us sum of less than maxWeight

//        int costSum=0;

        List<Integer> finalIndices = new ArrayList<>();
        Map<Integer, Integer> filteredCosts= new HashMap<>();
        Map<Integer, Integer> filteredWeight=new HashMap<>();
        List<Integer> finalI=new ArrayList<>();

        List<Integer> costValues=new ArrayList<>();
        List<Integer> weightValues=new ArrayList<>();
        for (Integer index : optIndicesNoDuplicate) {
                                                            //now we filter the indices from optIndicesNoDuplicate whose cost value will give us the maximum value and optimum weight.
            int newWeightSum=0;
            int costSum=0;
            Map<Integer, Integer> newCosts=new HashMap<>(indexCosts);
            Map<Integer, Integer> newWeights=new HashMap<>(indexWeights);
            if(newCosts.containsKey(index)){
                filteredCosts.put(index,newCosts.get(index));
                costValues.add(newCosts.get(index));
                finalIndices.add(index);
                costSum=costSum+filteredCosts.get(index);
            }

            if(newWeights.containsKey(index)){
                filteredWeight.put(index,newWeights.get(index));
                weightValues.add(newWeights.get(index));
                finalIndices.add(index);
                newWeightSum=newWeightSum+filteredWeight.get(index);
            }


            if(costSum<=maxCost && !(newWeightSum>maxWeight)){
                finalI.add(index);
            }
        }


                String result =
                finalI.stream()
                        .mapToInt(i -> i)
                        .sorted()
                        .mapToObj(Integer::toString)
                        .collect(Collectors.joining(","));

        return result.isEmpty() ? "-" : result ;
    }


    private static class SubList {
        public Integer size;
        public List<Integer> subList;

        public SubList() {
            this(0, new ArrayList<>());
        }

        public SubList(Integer size, List<Integer> subList) {
            this.size = size;
            this.subList = subList;
        }
    }
}
