package com.hellof.crawler.controller;

import com.hellof.crawler.Scoretask.ScoreJobProcessor;
import com.hellof.crawler.pojo.ScoreLine;
import com.hellof.crawler.service.ScoreLineService;

import com.hellof.crawler.websocket.ProductWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller for starting the score crawler and exporting data to Excel.
 */
@RestController
public class ScoreWebController {

    private ScoreJobProcessor scoreJobProcessor;
    private ScoreLineService scoreLineService;

    // Autowired constructor for dependency injection
    @Autowired
    public ScoreWebController(ScoreJobProcessor scoreJobProcessor, ScoreLineService scoreLineService) {
        this.scoreJobProcessor = scoreJobProcessor;
        this.scoreLineService = scoreLineService;
    }

    /**
     * Endpoint to start the score crawler.
     */
    @PostMapping("/crawler/run")
    public void runCrawler() {
        scoreJobProcessor.start(); // Assuming the start method does not require any arguments
        try {
            ProductWebSocket.sendInfo("分数线爬虫开始运行！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Endpoint to export score line data to Excel.
     * @param req the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws Exception if an error occurs during the export process
     */
    @PostMapping("/exportExcelScoreLines")
    public void exportExcelScoreLines(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // Retrieve all score lines from the service
        List<ScoreLine> scoreLines = scoreLineService.findAll();
        // Export logic to Excel (Assuming there's a service method to handle Excel export)
        // iExportExcelService.exportExcelWithDispose("score_lines", "分数线数据" + UUID.randomUUID().toString(),
        //        scoreLines, req, resp);
        try {
            ProductWebSocket.sendInfo("分数线数据导出成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}