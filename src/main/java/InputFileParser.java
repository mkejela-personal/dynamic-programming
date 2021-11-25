
import util.PackageException;
import util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFileParser {


    //split each item in the file into a pair of max weight and string of items.
    public static List<Pair<Double, String>> parseItems(String inputFile) throws PackageException, IOException {
        List<Pair<Double, String>> lines = new ArrayList<>();

        String[] splitedLine = null;

        try (FileInputStream inputStream = new FileInputStream(inputFile)) {

            Scanner input = new Scanner(inputStream);

            if (!input.hasNext()) {
                throw new PackageException("The file is empty, please correct add your items to the file.", PackageException.EMPTY_FILE);
            }

            //split each line into weight:item pair
            while (input.hasNext()) {
                splitedLine = input.nextLine().split(":");
                if (!input.nextLine().isEmpty()) {
                    continue;
                }
                Pair<Double, String> pair = new Pair<>(Double.parseDouble(splitedLine[0]), splitedLine[1]);
                if (Double.parseDouble(splitedLine[0]) < 100) {
                    lines.add(pair);
                }
            }

        } catch (IOException ioe) {
            throw new IOException(ioe.getMessage());
        }

        if (lines.isEmpty()) {
            return null;
        }

        return lines;

    }

    public static String parseStringItems(String inputFile) throws PackageException {
        String result ="";

        List<Pair<Double, String>> parsedLines;
        ParsedLine parsedLine = null;
        try {
            parsedLines = parseItems(inputFile);
        } catch (PackageException | IOException packageException) {
            throw new PackageException(packageException.getMessage(), PackageException.UNKNOWN_ERROR);
        }
        if (parsedLines == null) {

            return null;
        }


        double maxLineWeight = 0;

        List<PackageItem> packageItems = new ArrayList<>();
        Pair<Integer, List<PackageItem>> packages = null;


        //this will iterater through the items to get maxWeight:item pairs
            for (Pair<Double, String> pairItem : Objects.requireNonNull(parsedLines)) {
                maxLineWeight = pairItem.left;

                String item = pairItem.right;

                Pattern pattern = Pattern.compile(Constants.PACKAGE_REGEX);
                Matcher matcher = pattern.matcher(item);
                int lastEnd = 0;

                while (matcher.find()) {
                    PackageItem packageItem;

                    if (matcher.start() != lastEnd + 1 || item.charAt(lastEnd) != ' ') {
                        throw new PackageException(String.format("The package items should be in (%s) format", Constants.PACKAGE_REGEX), PackageException.INVALID_ITEM_FORMAT);
                    }


                    try {
                        //get the pairs based on the Constants and validate the inputs
                        Integer index = Integer.valueOf(matcher.group(Constants.INDEX));
                        Double weight = Double.valueOf(matcher.group(Constants.WEIGHT));
                        Double cost = Double.valueOf(matcher.group(Constants.COST));

                        validateItemParameters(cost, weight, index, maxLineWeight);

                        packageItem = new PackageItem(index, weight, cost);
                        packageItems.add(packageItem);
                        parsedLine = new ParsedLine(packageItems);
                        parsedLine.setLineMaxWeight(maxLineWeight);

                        packages = new Pair<>((int) maxLineWeight, packageItems);


                    } catch (PackageException numberFormatException) {

                        throw new PackageException("Invalid number", PackageException.INVALID_ITEM_FORMAT);
                    }

                    lastEnd = matcher.end();
                }

                result = PackItems.findOptimumPackage(packages).toString();

            }

        return result;

    }

    private static void validateItemParameters(double cost, double weight, int index, double givenMaxWeight) throws PackageException {


        try {
            if (index > Constants.MAX_ITEMS_IN_LINE || index <= 0) {
                throw new PackageException("The file contains an invalid index for the items. Please correct the index or upload a new file", PackageException.INVALID_INDEX_VALUE);
            }

            if (weight > Constants.MAX_WEIGHT || weight <= 0 || givenMaxWeight > Constants.MAX_WEIGHT || givenMaxWeight <= 0) {
                throw new PackageException(String.format("The weight in the package item is must be above zero and less than (%s)", Constants.MAX_WEIGHT), PackageException.MAX_PACKAGE_WEIGHT_EXCEEDED);
            }

            if (cost > 100 || cost < 0) {
                throw new PackageException(String.format("The cost in the package item is must be above zero and less than (%s)", Constants.MAX_COST), PackageException.MAX_PACKAGE_COST_EXCEEDED);
            }

        } catch (NumberFormatException nfe) {
            throw new PackageException(nfe.getMessage(), PackageException.UNKNOWN_ERROR);
        }

    }
}
