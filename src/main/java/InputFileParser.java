

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import util.PackageException;
import util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFileParser {


    public List<Pair<Double, String>> parseItems(String inputFile) throws PackageException, IOException {
        List<Pair<Double, String>> lines = new ArrayList<>();

        String[] splitedLine=null;

        try (Scanner input=new Scanner(new File(inputFile))){

            if (!input.hasNext()){
                throw new PackageException("The file is empty, please correct add your items to the file.",PackageException.EMPTY_FILE);
            }

            while (input.hasNext()){
                splitedLine=input.nextLine().split(":");
                Pair<Double, String> pair = new Pair<>(Double.parseDouble(splitedLine[0]), splitedLine[1]);
                if(Double.parseDouble(splitedLine[0])<100){
                    lines.add(pair);
                }
            }
        } catch (IOException ioe){
            throw new IOException("There was an error reading your file, please check your permission and the file format");
        }

        if (lines.isEmpty()){
            return null;
        }

      return lines;

    }

    public  List<PackageItem> parseStringItems(String item) throws PackageException{

        List<PackageItem> packageItems = new ArrayList<>();
        Pattern pattern = Pattern.compile(Constants.PACKAGE_REGEX);
        Matcher matcher = pattern.matcher(item);

        int lastEnd=0;

        while (matcher.find()){

            if(matcher.start()!= lastEnd+1 || item.charAt(lastEnd)!=' '){
                throw new PackageException(String.format("The package items should be in (%s) format", Constants.PACKAGE_REGEX), PackageException.INVALID_ITEM_FORMAT);
            }

            try{
                Integer index=Integer.valueOf(matcher.group(Constants.INDEX));
                Double weight = Double.valueOf(matcher.group(Constants.WEIGHT));
                Double cost = Double.valueOf(matcher.group(Constants.COST));

                if (index>Constants.MAX_ITEMS_IN_LINE || index <=0){
                    throw new PackageException("The file contains an invalid index for the items. Please correct the index or upload a new file", PackageException.INVALID_INDEX_VALUE);
                }

                if(weight>Constants.MAX_WEIGHT || weight <= 0){
                    throw new PackageException(String.format("The weight in the package item is must be above zero and less than (%s)", Constants.MAX_WEIGHT), PackageException.MAX_PACKAGE_WEIGHT_EXCEEDED);
                }




            } catch (NumberFormatException numberFormatException){

                throw new PackageException("Invalid number", PackageException.INVALID_ITEM_FORMAT);
            }

        }


        return packageItems;

    }
}
