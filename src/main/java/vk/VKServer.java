package vk;

import DB.DataBase;
import Subscribers.Notifier;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import file.ReadCity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;


public class VKServer {
    private static final Logger log = LoggerFactory.getLogger(VKServer.class);
    private static VKCore vkCore;
    public static DataBase usersDataBase;
    public static List<String> Cites;

    static {
        try {
            vkCore = new VKCore();
        } catch (ApiException | ClientException e) {
            log.error(String.valueOf(e));
        }
    }

    public static void main(String[] args) throws NullPointerException, ApiException, InterruptedException, SQLException {
        log.info("Running server...");
        Cites = ReadCity.getCites();
        //create Notifier for subscribe
         new Notifier().Create();
        //initialize db
        usersDataBase = DataBase.getInstance();
        while (true) {
            Thread.sleep(300);
            try {
                Message message = vkCore.getMessage();
                if (message != null) {
                    ExecutorService exec = newCachedThreadPool();
                    exec.execute(new Messanger(message));
                }
            } catch (ClientException e) {
                log.error(String.valueOf(e));
                final int RECONNECT_TIME = 10000;
                log.info("Повторное соединение через " + RECONNECT_TIME / 1000 + " секунд");
                Thread.sleep(RECONNECT_TIME);
            }
        }
    }

}