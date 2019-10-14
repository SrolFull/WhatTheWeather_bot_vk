package vk;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VKManager {
    private static final Logger log = LoggerFactory.getLogger(VKManager.class);
    private static VKCore vkCore;

    static {
        try {
            vkCore = new VKCore();
        } catch (ApiException | ClientException e) {
            log.error(String.valueOf(e));
        }
    }

    public void sendMessage(String msg, int peerId){
        if (msg == null){
            log.info("Message is null");
            return;
        }
        try {
            vkCore.getVk().messages().send(vkCore.getActor()).peerId(peerId).message(msg).execute();
        } catch (ApiException | ClientException e) {
            log.error(String.valueOf(e));
        }
    }
}
