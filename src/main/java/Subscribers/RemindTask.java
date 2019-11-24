package Subscribers;

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
        Map<Integer, String> Subs = VKServer.usersDataBase.getSubscribers();
        Subs.forEach(Weather::exec);
    }
}
