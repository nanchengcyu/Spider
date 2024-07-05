package cn.nanchengyu.spider_score.service;

import cn.nanchengyu.spider_score.entity.ScoreLine;
import cn.nanchengyu.spider_score.mapper.ScoreLineMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

@Service
public class SpiderService {

    private static final String URL = "https://www.gaokao.cn/school/371/provinceline";

    @Autowired
    private ScoreLineMapper scoreLineMapper;

    private static final Logger logger = Logger.getLogger(SpiderService.class.getName());

    public void fetchData() {
        try {
            // 增加日志输出
            logger.info("Fetching data from URL: " + URL);

            Document doc = Jsoup.connect(URL).get();

            // 输出获取的页面内容，便于调试
            logger.info("Fetched HTML content:\n" + doc.html());

            // 根据新的HTML结构，选择表格元素
            // 选择最外层的div，然后找到包含province_score_line_table类的div，最后选择table元素
            Element tableDiv = doc.selectFirst(".bgwhite.p20.radius8.mb20 .province_score_line_table table");

            if (tableDiv == null) {
                logger.warning("Could not find table element within .bgwhite.p20.radius8.mb20 .province_score_line_table.");
                return;
            }

            Elements rows = tableDiv.select("tbody tr"); // 选择tbody下的tr元素
            for (Element row : rows) {
                Elements columns = row.select("td");

                if (columns.size() < 5) {
                    logger.warning("Row does not contain expected number of columns.");
                    continue;
                }

                String majorName = columns.get(0).text();
                String admissionBatch = columns.get(1).text();
                String scoreAndRank = columns.get(2).text();
                // 解析最低分和最低位次
                String[] parts = scoreAndRank.split("/");
                int lowestScore = Integer.parseInt(parts[0]);
                int lowestRank = Integer.parseInt(parts[1]);
                // 获取录取率，这里假设录取率是数字形式，如果不是，需要调整解析逻辑
                String admissionRateText = columns.get(3).select("span.colorF60").first().text();
                double admissionRate = Double.parseDouble(admissionRateText); // 假设录取率是数字
                String subjectRequirements = columns.get(4).text();

                ScoreLine scoreLine = new ScoreLine();
                scoreLine.setProvince("北京");
                // 这里需要根据实际情况设置学校名称
                scoreLine.setSchoolName("具体学校名称");
                scoreLine.setMajorName(majorName);
                scoreLine.setAdmissionBatch(admissionBatch);
                scoreLine.setLowestScore(lowestScore);
                scoreLine.setLowestRank(lowestRank);
                scoreLine.setAdmissionRate(admissionRate);
                scoreLine.setSubjectRequirements(subjectRequirements);

                scoreLineMapper.insertScoreLine(scoreLine);
            }
            logger.info("Data fetched and stored successfully.");
        } catch (IOException e) {
            logger.warning("Error fetching data: " + e.getMessage());
        }
    }
}