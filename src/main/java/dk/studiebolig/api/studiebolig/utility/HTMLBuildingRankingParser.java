package dk.studiebolig.api.studiebolig.utility;

import dk.studiebolig.api.studiebolig.Ranking;

public class HTMLBuildingRankingParser {

    public Ranking extractRanking(String html) {
        System.out.println("Extracting ratings from html...");
        Ranking ranking = new Ranking();

        String[] splitString = html.split("(;|\\s|<>|/)+");
        for (String split : splitString) {
            if (split.contains("B&nbsp")) {
                ranking.incrementB();
            } else if (split.contains("A&nbsp")) {
                ranking.incrementA();
            } else if (split.contains("C&nbsp")) {
                ranking.incrementC();
            }
        }
        return ranking;
    }

}
