package core.modules;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WeatherParser {
    private Document doc;
    public WeatherParser(String city) throws IOException {
        doc = Jsoup.connect(String.format("https://world-weather.ru/pogoda/russia/%s/",city)).get();
    }
    public String getWeatherTodayDescription(String city) {
        Elements elements = doc.select("span.dw-into");
        return city+"\n"+elements.text().split("Подробнее")[0];
    }
}
