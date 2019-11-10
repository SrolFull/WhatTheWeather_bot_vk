package vk;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

class VKCore {
    private final Logger log = LoggerFactory.getLogger(VKCore.class);
    private VkApiClient vk;
    private static int ts;
    private GroupActor actor;
    private static int maxMsgId = -1;

    VKCore() throws ClientException, ApiException {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
        // Загрузка конфигураций
        Properties prop = new Properties();
        int groupId;
        String access_token;
        try {
            prop.load(new FileInputStream("src/main/resources/vkconfig.properties"));
            groupId = Integer.parseInt(prop.getProperty("groupId"));
            access_token = prop.getProperty("accessToken");
            actor = new GroupActor(groupId, access_token);
            ts = vk.messages().getLongPollServer(actor).execute().getTs();
        } catch (IOException e) {
            log.error(e+"ощибка загрузки файла конфигураций");
        }
    }

    GroupActor getActor() {
        return actor;
    }
    VkApiClient getVk() {
        return vk;
    }
    Message getMessage() throws ClientException, ApiException {
        MessagesGetLongPollHistoryQuery eventsQuery = vk.messages()
                .getLongPollHistory(actor)
                .ts(ts);
        if (maxMsgId > 0){
            eventsQuery.maxMsgId(maxMsgId);
        }
        List<Message> messages = eventsQuery.execute().getMessages().getMessages();

        if (!messages.isEmpty()){
            try {
                ts =  vk.messages()
                        .getLongPollServer(actor)
                        .execute()
                        .getTs();
            } catch (ClientException e) {
                log.error(String.valueOf(e));
            }
        }
        if (!messages.isEmpty() && !messages.get(0).isOut()) {
            int messageId = messages.get(0).getId();
            if (messageId > maxMsgId){
                maxMsgId = messageId;
            }
            return messages.get(0);
        }
        return null;
    }
}

