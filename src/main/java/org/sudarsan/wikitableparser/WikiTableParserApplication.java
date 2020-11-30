package org.sudarsan.wikitableparser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.sudarsan.wikitableparser.chart.GraphOutput;
import org.sudarsan.wikitableparser.chart.LineGraphOutput;
import org.sudarsan.wikitableparser.domain.ChartData;
import org.sudarsan.wikitableparser.parser.HtmlParser;
import org.sudarsan.wikitableparser.parser.WikiParser;

import java.util.List;
import java.util.regex.Pattern;

@SpringBootApplication
public class WikiTableParserApplication implements CommandLineRunner {
	private final String wikiTableTag;

	private final String yDataPatternString;
	private final String xDataPatternString;

	private final int yDataColumnNumber;
	private final int xDataColumnNumber;

	private final int yDataGroupPosition;
	private final int xDataGroupPosition;

	public static void main(String[] args) {
		SpringApplication.run(WikiTableParserApplication.class, args);
	}

	public WikiTableParserApplication(
			@Value("${wikiTableTag}") final String wikiTableTag,
			@Value("${xDataPatternString.regexp}") final String xDataPatternString,
			@Value("${yDataPatternString.regexp}") final String yDataPatternString,
			@Value("${xDataColumnNumber}") final int xDataColumnNumber,
			@Value("${yDataColumnNumber}") final int yDataColumnNumber,
			@Value("${xDataGroupPosition}") final int xDataGroupPosition,
			@Value("${yDataGroupPosition}") final int yDataGroupPosition
	) {
		this.wikiTableTag = wikiTableTag;
		this.xDataPatternString = xDataPatternString;
		this.yDataPatternString = yDataPatternString;
		this.xDataColumnNumber = xDataColumnNumber;
		this.yDataColumnNumber = yDataColumnNumber;
		this.xDataGroupPosition = xDataGroupPosition;
		this.yDataGroupPosition = yDataGroupPosition;

	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length < 1) {
			System.out.println("Please pass the Wiki URL as the first parameter while executing");
			return;
		}

		HtmlParser wikiParser = new WikiParser(wikiTableTag, xDataPatternString, yDataPatternString, xDataColumnNumber, yDataColumnNumber, xDataGroupPosition, yDataGroupPosition);
		GraphOutput lineGraphOutput = new LineGraphOutput();

		lineGraphOutput.drawChart(wikiParser.parse(args[0]));
	}

}
