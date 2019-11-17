package file;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadCity{
    private static final Logger log = LoggerFactory.getLogger(ReadCity.class);
    @NotNull
    public static List<String> getCites(){
        List<String> cites = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("src/main/resources/City_list.txt")))
        {
            while (scanner.hasNextLine()) {
                cites.add(scanner.nextLine());
            }
        }
        catch (IOException e)
        {
            log.error(String.valueOf(e));
        }
        return cites;
    }
}
