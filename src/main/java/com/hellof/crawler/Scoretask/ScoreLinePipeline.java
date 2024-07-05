package com.hellof.crawler.Scoretask;



import com.hellof.crawler.pojo.ScoreLine;
import com.hellof.crawler.service.ScoreLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Pipeline for processing and saving score line data.
 */
@Component
public class ScoreLinePipeline implements Pipeline {

    private ScoreLineService scoreLineService;

    /**
     * Autowire the ScoreLineService for data persistence.
     * @param scoreLineService the service to be injected
     */
    @Autowired
    public ScoreLinePipeline(ScoreLineService scoreLineService) {
        this.scoreLineService = scoreLineService;
    }

    /**
     * Process the page results and save the score line data.
     *
     * @param resultItems the result items to process
     * @param task the current task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        // Retrieve the list of ScoreLine objects from the result items
        List<ScoreLine> scoreLines = resultItems.get("scoreLines");

        // Check if the list is not null and not empty
        if (scoreLines != null && !scoreLines.isEmpty()) {
            // Save the list of score lines to the database
            scoreLineService.saveAll(scoreLines);
        }
    }
}