package vk;

import com.vk.api.sdk.objects.messages.Message;
import core.Commander;

class Messanger implements Runnable{

    private Message message;

    Messanger(Message message){
        this.message = message;
    }

    @Override
    public void run() {
        Commander.execute(message);
    }

}
