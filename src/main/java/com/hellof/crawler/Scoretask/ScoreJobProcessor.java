package com.hellof.crawler.Scoretask;

import com.hellof.crawler.pojo.ScoreLine;
import com.hellof.crawler.service.ScoreLineService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PageProcessor for processing score line data from a specific webpage.
 */
@Component
public class ScoreJobProcessor implements PageProcessor {

    private Site site = Site.me()
            .setCharset("utf-8")
            .setTimeOut(10000)
            .setRetrySleepTime(3000)
            .setRetryTimes(3);

    @Autowired
    private ScoreLineService scoreLineService;

    @Autowired
    private ScoreLinePipeline scoreLinePipeline;

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        // Check if the page URL is the one we want to process
        if ("https://www.gaokao.cn/school/371/provinceline".equals(page.getUrl().toString())) {
            processScoreLinePage(page);
        } else {
            // Handle other pages if necessary
        }
    }

    private void processScoreLinePage(Page page) {
        List<ScoreLine> scoreLines = new ArrayList<>();
        Document document = Jsoup.parse(page.getHtml().toString());
        Elements rows = document.select("table.table.table-bordered tr");

        // Skip the header row
        for (Element row : rows) {
            Elements columns = row.select("td");
            if (columns.size() >= 8) {
                ScoreLine scoreLine = new ScoreLine();
                scoreLine.setProvince(columns.get(0).text());
                scoreLine.setSchoolName(columns.get(1).text());
                scoreLine.setMajorName(columns.get(2).text());
                scoreLine.setAdmissionBatch(columns.get(3).text());
                scoreLine.setLowestScore(Integer.parseInt(columns.get(4).text().split("/")[0]));
                scoreLine.setLowestRank(Integer.parseInt(columns.get(4).text().split("/")[1]));
                // Assuming the admission rate is a percentage as text, remove the % sign
                scoreLine.setAdmissionRate(Double.parseDouble(columns.get(5).text().replace("%", "")));
                scoreLine.setSubjectRequirements(columns.get(6).text());
                scoreLine.setCreateTime(new Date());
                scoreLines.add(scoreLine);
            }
        }

        // Add the list of score lines to the page for the pipeline to process
        page.putField("scoreLines", scoreLines);
    }

    // Define the start method to initiate the spider
    public void start() {
        Spider.create(this)
                .addUrl("https://www.gaokao.cn/school/371/provinceline")
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(scoreLinePipeline)
                .thread(5)
                .run();
    }
}