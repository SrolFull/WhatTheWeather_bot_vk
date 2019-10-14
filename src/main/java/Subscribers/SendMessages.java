package Subscribers;

import core.commands.Weather;
import vk.VKServer;

import java.util.List;

public class SendMessages {
     SendMessages(){
        List<Integer> Subs = VKServer.UsersDb.getSubscribers();
        for (Integer sub_id : Subs){
            Weather.exec(sub_id);
        }
    }
}
