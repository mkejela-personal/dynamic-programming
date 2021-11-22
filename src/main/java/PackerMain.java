import util.PackageException;

import java.util.ArrayList;
import java.util.List;

public class PackerMain {

    public static void main(String[] args) throws PackageException {

        String packageItems=InputFileParser.parseStringItems(args[0]);

        System.out.println(packageItems);

        if (args.length != 1){
            System.err.println("input file path must provider as args[0], extra args is forbidden");
            System.exit(1);
        }


    }

}
