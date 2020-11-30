package org.sudarsan.wikitableparser.parser;


import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static junit.framework.TestCase.*;

public class WikiParserTest {
    private WikiParser wikiParser;

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testParseWithSampleWikiURLs() {
        wikiParser = new WikiParser("table.wikitable tr", "^(\\d{1,2}) (.*) (\\d{4})", "^([0-9]+.[0-9]+)", 2,0, 3, 0);
        final List results = wikiParser.parse("https://en.wikipedia.org/wiki/Women%27s_high_jump_world_record_progression");

        Assertions.assertAll("Valid URL parsing Women High jump",
                () -> assertNotNull(results),
                () -> assertEquals(56, results.size())
        );

        wikiParser = new WikiParser("table.wikitable tr", "^(\\d{4})", "^([0-9]+)", 1, 2, 1, 0);
        final List results2 = wikiParser.parse("https://en.wikipedia.org/wiki/List_of_films_with_a_100%25_rating_on_Rotten_Tomatoes");

        Assertions.assertAll("Valid URL parsing Movie review",
                () -> assertNotNull(results2),
                () -> assertEquals(390, results2.size())
        );

        wikiParser = new WikiParser("table.wikitable tr", "^(\\d{1,2}) (.*) (\\d{4})", "^([0-9]+.[0-9]+)", 3,0, 3, 0);
        final List results3 = wikiParser.parse("https://en.wikipedia.org/wiki/Men%27s_high_jump_world_record_progression");
        Assertions.assertAll("Valid URL parsing Men High jump",
                () -> assertNotNull(results3),
                () -> assertEquals(40, results3.size())
        );
    }

    @Test
    public void testParseForInvalidURLNoException() {
        wikiParser = new WikiParser("table.wikitable tr", "^(\\d{1,2}) (.*) (\\d{4})", "^([0-9]+.[0-9]+)", 2,0, 3, 0);
        assertNull(wikiParser.parse("https://en.wikipedia123.org"));        //No test fail while executing this means Exception is handled properly
    }

    @Test
    public void testParseForNullURL() {
        wikiParser = new WikiParser("table.wikitable tr", "^(\\d{1,2}) (.*) (\\d{4})", "^([0-9]+.[0-9]+)", 2,0, 3, 0);
        assertNull(wikiParser.parse(null));        //No test fail while executing this means Exception is handled properly
    }

    @Test
    public void testParseForEmptyURL() {
        wikiParser = new WikiParser("table.wikitable tr", "^(\\d{1,2}) (.*) (\\d{4})", "^([0-9]+.[0-9]+)", 2,0, 3, 0);
        assertNull(wikiParser.parse(""));        //No test fail while executing this means Exception is handled properly
    }

    @Test
    public void testParseForRandomWikiURLNoException() {
        wikiParser = new WikiParser("table.wikitable tr", "^(\\d{4})", "^([0-9]+)", 1,2, 1, 0);
        assertNotNull(wikiParser.parse("https://en.wikipedia.org/wiki/List_of_films_with_a_100%25_rating_on_Rotten_Tomatoes"));
    }
}
