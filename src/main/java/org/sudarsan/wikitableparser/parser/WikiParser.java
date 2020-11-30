package org.sudarsan.wikitableparser.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.sudarsan.wikitableparser.domain.ChartData;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class helps to parse the Wikipedia page and extract the table information.
 * Given the CSS tags and the website code can change anytime and is out of our control,
 * this class accepts the required Html tags used for the proper parsing of table and the regex patterns and the column details.
 * <br><br> wikiTableTag: The Html tag used to identify the table in the Wiki page
 * <br> xDataColumnNumber/ yDataColumnNumber: The column of the table for X and Y data. 0 indicates first column and so on.
 * <br> xDataPattern/ yDataPattern: The regex pattern which will help extract proper X, Y values for the chart.
 * <br> xDataGroupPosition/ yDataGroupPosition: Use this according to the regex pattern used. which group from regex pattern gives the required data.
 */
public class WikiParser implements HtmlParser {
    private final String wikiTableTag;

    private final Pattern yDataPattern;
    private final Pattern xDataPattern;

    private final int yDataColumnNumber;
    private final int xDataColumnNumber;

    private final int yDataGroupPosition;
    private final int xDataGroupPosition;

    public WikiParser(String wikiTableTag, String xDataPatternString, String yDataPatternString, int xDataColumnNumber, int yDataColumnNumber, int xDataGroupPosition, int yDataGroupPosition) {
        this.wikiTableTag =  wikiTableTag;
        this.xDataPattern = Pattern.compile(xDataPatternString);
        this.yDataPattern = Pattern.compile(yDataPatternString);
        this.yDataColumnNumber = yDataColumnNumber;
        this.xDataColumnNumber = xDataColumnNumber;
        this.yDataGroupPosition = yDataGroupPosition;
        this.xDataGroupPosition = xDataGroupPosition;
    }

    /**
     * This method parses the URL given and go through the columns of the table to extract a numeric value as Y axis data
     * and a text value for the X axis data.
     * @param url The URL of the website to extract the table details from.
     * @return List<ChartData>: The collection of ChartData where there will be one record per row of the table.
     * If any data not matching the expected format, that row will be ignored.
     */
    @Override
    public List<ChartData> parse(String url) {
        if(url == null || url.isEmpty()) {
            System.out.println("WikiParser.parse: URL cannot be null or empty");
            return null;
        }
        System.out.printf("Extracting table information from wiki URL: %s \n", url);
        try {
            Document document = Jsoup.connect(url).get();

            return document.select(wikiTableTag)
                    .stream()
                    .map(ele -> ele.getElementsByTag("td")) //to get the column data for the table row.
                    .map(this::processElements)
                    .filter(Objects::nonNull)                       //Can add more filters if needed
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.printf("IO error in parse method. URL: %s Error: %s%n", url, e);
        }
        return null;
    }

    /**
     * This a private utility method helps to process one row of the table to extract X, Y data
     * @param elements JSoup Elements- an extension of the ArrayList which contains the column data of one row.
     * @return populated ChartData object representing the information from one row.
     * Returns null if the data format is not correct.
     */
    private ChartData processElements(Elements elements) {
        if(elements.size() == 0) {
            return null;        //no column details to extract, so no point proceeding further
        }

        Matcher yDataMatcher = yDataPattern.matcher(elements.eq(yDataColumnNumber).text());
        Matcher xDataMatcher = xDataPattern.matcher(elements.eq(xDataColumnNumber).text());

        if (!xDataMatcher.find()) {
            System.out.println((String.format("Record %s is not matching the X Data pattern, ignored.", elements.eq(xDataColumnNumber).text())));
            return null;
        }
        if (!yDataMatcher.find()) {
            System.out.println((String.format("Record %s is not matching the Y Data pattern, ignored.", elements.eq(yDataColumnNumber).text())));
            return null;
        }

        return new ChartData(xDataMatcher.group(xDataGroupPosition), Double.parseDouble(yDataMatcher.group(yDataGroupPosition)));
    }
}
