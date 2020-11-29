package org.sudarsan.wikitableparser.parser;

import org.sudarsan.wikitableparser.domain.ChartData;

import java.util.List;

public interface HtmlParser {
    List<ChartData> parse(String url);
}
