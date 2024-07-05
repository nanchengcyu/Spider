package com.hellof.crawler.dao;


import com.hellof.crawler.pojo.ScoreLine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ClassName: ScoreLine
 * Package: com.hellof.crawler.dao
 * Description:
 *
 * @Author 南城余
 * @Create 2024/7/5 22:58
 * @Version 1.0
 */
public interface ScoreLineDao extends JpaRepository<ScoreLine, Long> {
}
