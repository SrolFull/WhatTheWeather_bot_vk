package Subscribers;

import DB.DataBase;
import core.commands.Weather;
import vk.VKServer;

import java.util.Map;
import java.util.TimerTask;

public class RemindTask extends TimerTask {
    @Override
    public void run() {
        SendMessages();
    }
    private static void SendMessages(){
        Map<Integer, String> Subs = VKServer.usersDataBase.getSubscribers(DataBase.connection);
        Subs.forEach(Weather::exec);
    }
}
