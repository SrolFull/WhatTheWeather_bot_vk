package core.commands;

import com.vk.api.sdk.objects.messages.Message;
import core.Command;
import org.jetbrains.annotations.NotNull;
import vk.VKManager;

public class SpecifyCity extends Command {
    public SpecifyCity() {
        super("SpecifyCity");
    }

    @Override
    public void exec(@NotNull Message message) {
        new VKManager().sendMessage("Please, specify city", message.getUserId());
        new VKManager().sendMessage("Example: abakan,yekaterinburg, moscow", message.getUserId());
    }
}
