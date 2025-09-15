import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HTMLBuildingRankingParser {

    public Ranking extractRanking(String html) {
        Ranking ranking = new Ranking();
        String[] splitString = html.split("(;|\\s|<>|/)+");
        for (String split : splitString) {
            if (split.equals("B&nbsp")) {
                ranking.incrementB();
            } else if (split.equals("A&nbsp")) {
                ranking.incrementA();
            } else if (split.equals("C&nbsp")) {
                ranking.incrementC();
            }
        }
        return ranking;
    }

}
