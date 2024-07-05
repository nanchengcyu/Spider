package com.hellof.crawler.service;

import com.hellof.crawler.pojo.InstitutionInfo;
import com.hellof.crawler.pojo.ScoreLine;

import java.util.List;

/**
 * ClassName: ScoreLineService
 * Package: com.hellof.crawler.service
 * Description:
 *
 * @Author 南城余
 * @Create 2024/7/5 23:00
 * @Version 1.0
 */
public interface ScoreLineService {
    /**
     * fetch data by rule id
     *
     * @param scoreLine rule id
     */
    void save(ScoreLine scoreLine);

    /**
     * fetch data by rule id
     *
     * @param scoreLine rule id
     * @return Result<institutionInfo>
     */
    List<ScoreLine> findScoreLine(ScoreLine scoreLine);

    /**
     * 查询数据库中所有的数据
     *
     * @return InstitutionInfo
     */
    List<ScoreLine> findAll();

     void saveAll(List<ScoreLine> scoreLines);

}
