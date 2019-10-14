package vk;

import DB.db;
import Subscribers.ReminTask;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Timer;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;


public class VKServer {
    private static final Logger log = LoggerFactory.getLogger(VKServer.class);
    private static VKCore vkCore;
    public static db UsersDb;
    static {
        try {
            vkCore = new VKCore();
        } catch (ApiException | ClientException e) {
            log.error(String.valueOf(e));
        }
    }

    public static void main(String[] args) throws NullPointerException, ApiException, InterruptedException, SQLException, ParseException {
        log.info("Running server...");
        Timer tm = new Timer();
        tm.schedule(new ReminTask(),6600000, 86400000);
        //initialaze db
        UsersDb = db.getInstance();
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