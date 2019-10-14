package Subscribers;

import core.commands.Weather;
import vk.VKServer;

import java.util.List;
import java.util.TimerTask;

public class ReminTask extends TimerTask {
    @Override
    public void run() {
        SendMessages();
    }
    private static void SendMessages(){
        List<Integer> Subs = VKServer.UsersDb.getSubscribers();
        for (Integer sub_id : Subs){
            Weather.exec(sub_id);
        }
    }
}
