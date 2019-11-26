package vk;

import DB.DataBase;
import Subscribers.Notifier;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import file.ReadCity;
import org.apache.commons.logging.Log;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;


public class VKServer {
    private final static Logger LOG = LogManager.getLogger(VKServer.class);
    private static VKCore vkCore;
    public static DataBase usersDataBase;
    public static List<String> Cites;

    static {
        try {
            vkCore = new VKCore();
        } catch (ApiException | ClientException e) {
            LOG.error(String.valueOf(e));
        }
    }

    public static void main(String[] args) throws NullPointerException, ApiException, InterruptedException, SQLException {
        LOG.info("Get cities list");
        Cites = ReadCity.getCites();
        //create Notifier for subscribe
        new Notifier().Create();
        //initialize db
        LOG.info("Initialize DataBase");
        usersDataBase = DataBase.getInstance();
        LOG.info("Server Running");
        while (true) {
            Thread.sleep(300);
            try {
                Message message = vkCore.getMessage();
                if (message != null) {
                    ExecutorService exec = newCachedThreadPool();
                    exec.execute(new Messanger(message));
                }
            } catch (ClientException e) {
                LOG.error(String.valueOf(e));
                final int RECONNECT_TIME = 10000;
                LOG.info("Повторное соединение через " + RECONNECT_TIME / 1000 + " секунд");
                Thread.sleep(RECONNECT_TIME);
            }
        }
    }

}