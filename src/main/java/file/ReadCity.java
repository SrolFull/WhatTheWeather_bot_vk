package file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import vk.VKServer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadCity{
    private final static Logger LOG = LogManager.getLogger(ReadCity.class);
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
            LOG.error(String.valueOf(e));
        }
        return cites;
    }
}
