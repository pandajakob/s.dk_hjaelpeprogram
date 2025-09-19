package dk.studiebolig.api.studiebolig.utility;

import dk.studiebolig.api.studiebolig.Ranking;

public class HTMLBuildingRankingParser {

    public Ranking extractRanking(String html) {
        System.out.println("Extracting ratings from html...");
        Ranking ranking = new Ranking();
        String safeSubstring = html.substring(html.indexOf("Ranking"), html.indexOf("</form>"));
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
