package core.commands;

import com.vk.api.sdk.objects.messages.Message;
import core.Command;
import core.modules.WeatherParser;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vk.VKManager;

import java.io.IOException;
public class Weather extends Command {
    private static final Logger log = LoggerFactory.getLogger(Weather.class);


    public Weather() {
        super("Weather");
    }

    @Override
    public void exec(@NotNull Message message) {
        String[] bodyPart = message.getBody().split(" ");
        try{
            new VKManager().sendMessage(getWeather(bodyPart[1]), message.getUserId());
        }catch (ArrayIndexOutOfBoundsException e)
        {
            new SpecifyCity().exec(message);
        }
    }
    public static void exec(@NotNull Integer user_id, String city){
        new VKManager().sendMessage(getWeather(city),user_id);
    }

    private static String getWeather(String city){
        String weather;
        try {
            weather = new WeatherParser(city).getWeatherTodayDescription(city);
        } catch (IOException e) {
            weather = "не удалось получить погоду";
            log.error(String.valueOf(e));
        }
        return weather;
    }
}
