import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HTMLBuildingRankingParserTest {
    HTMLBuildingRankingParser htmlBuildingRankingParser = new HTMLBuildingRankingParser();
    @Test
    void testExtractRanking() {
        Ranking ranking = htmlBuildingRankingParser.extractRanking("" +
                "<html>" +
                "tenancy1:  B&nbsp;<i class=\"material-icons\">info_outline</i> " +
                "Buildin2:  B&nbsp;<i class=\"material-icons\">info_outline</i> " +
                "Buildin3:  A&nbsp;<i class=\"material-icons\">info_outline</i> " +
                "Buildin4:  A&nbsp;<i class=\"material-icons\">info_outline</i> " +
                "Buildin5:  A&nbsp;<i class=\"material-icons\">info_outline</i> " +
                "Buildin6:  C&nbsp;<i class=\"material-icons\">info_outline</i> " +
                "Buildin7:  &nbsp;<i class=\"material-icons\">info_outline</i> " +
                "Buildin8:  &nbsp;<i class=\"material-icons\">info_outline</i> " +
                "Buildin9:  &nbsp;<i class=\"material-icons\">info_outline</i> " +
                "</html>");

        assertEquals(3, ranking.getA());
        assertEquals(2, ranking.getB());
        assertEquals(1, ranking.getC());
    }

}