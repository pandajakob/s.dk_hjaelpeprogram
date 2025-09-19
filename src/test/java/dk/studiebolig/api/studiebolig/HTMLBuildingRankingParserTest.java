package dk.studiebolig.api.studiebolig;

import dk.studiebolig.api.studiebolig.utility.HTMLBuildingRankingParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HTMLBuildingRankingParserTest {
    HTMLBuildingRankingParser htmlBuildingRankingParser = new HTMLBuildingRankingParser();
    @Test
    void testExtractRanking() {
        Ranking ranking = htmlBuildingRankingParser.extractRanking("" +
                "<html>" +
                "tenancy1:  B&nbsp;<i class=\"material-icons\">info_outline</i> " +
                "tenancy2:  B&nbsp;<i class=\"material-icons\">info_outline</i> " +
                "tenancy3:  A&nbsp;<i class=\"material-icons\">info_outline</i> " +
                "tenancy4:  A&nbsp;<i class=\"material-icons\">info_outline</i> " +
                "tenancy5:  A&nbsp;<i class=\"material-icons\">info_outline</i> " +
                "tenancy6:  C&nbsp;<i class=\"material-icons\">info_outline</i> " +
                "tenancy7:  &nbsp;<i class=\"material-icons\">info_outline</i> " +
                "tenancy8:  &nbsp;<i class=\"material-icons\">info_outline</i> " +
                "tenancy9:  &nbsp;<i class=\"material-icons\">info_outline</i> " +
                "</html>");

        assertEquals(3, ranking.getA());
        assertEquals(2, ranking.getB());
        assertEquals(1, ranking.getC());
    }

}