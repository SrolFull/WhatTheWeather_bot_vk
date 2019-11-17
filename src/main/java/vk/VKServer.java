package vk;

import DB.db;
import Subscribers.RemindTask;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    public static void main(String[] args) throws NullPointerException, ApiException, InterruptedException, SQLException {
        log.info("Running server...");
        //init remind task
        Timer tm = new Timer();
        Calendar start_calendar = new GregorianCalendar(2019, Calendar.NOVEMBER,18,7,30,0);
        Date start_date = start_calendar.getTime();
        tm.schedule(new RemindTask(),start_date, 86400000);
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