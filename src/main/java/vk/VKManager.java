package vk;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

public class VKManager {
    private final static Logger LOG = LogManager.getLogger(VKManager.class);
    private static VKCore vkCore;

    static {
        try {
            vkCore = new VKCore();
        } catch (ApiException | ClientException e) {
            LOG.error(String.valueOf(e));
        }
    }

    public void sendMessage(String msg, int peerId){
        if (msg == null){
            LOG.info("Message is null");
            return;
        }
        try {
            vkCore.getVk().messages().send(vkCore.getActor()).peerId(peerId).message(msg).execute();
        } catch (ApiException | ClientException e) {
            LOG.error(String.valueOf(e));
        }
    }
}
