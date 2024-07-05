/**
 * FileName: InstitutionInfoServiceImpl
 * Author:   90934
 * Date:     2020/2/2 22:57
 * Description: 机构基本信息爬虫项目的Service接口实现类
 */

package com.hellof.crawler.service.impl;

import com.hellof.crawler.dao.ScoreLineDao;
import com.hellof.crawler.pojo.ScoreLine;
import com.hellof.crawler.service.ScoreLineService;
import com.hellof.crawler.websocket.ProductWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * @author 90934
 */
@Service
public class ScoreLineServiceImpl implements ScoreLineService {


    private ScoreLineDao scoreLineDao;

    @Autowired
    public void setScoreLineDao(ScoreLineDao scoreLineDao) {
        this.scoreLineDao = scoreLineDao;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ScoreLine scoreLine) {
        //根据机构名称查询数据
        ScoreLine param = new ScoreLine();
        param.setSchoolName(scoreLine.getSchoolName());

        //执行查询
        List<ScoreLine> list = this.findScoreLine(param);

        //判断查询结果是否为空
        if (list.size() == 0) {
            //如果结果为空，表示机构基本信息不存在，需要更新数据库
            this.scoreLineDao.save(scoreLine);
        }
        //打开注释，将爬取的数据显示到web端页面进行查看，注意当爬虫数据过快已造成页面崩溃
        try {
            ProductWebSocket.sendInfo("已成功采集 1 条数据！");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<ScoreLine> findScoreLine(ScoreLine scoreLine) {
        //设置查询条件
        Example<ScoreLine> example = Example.of(scoreLine);
        //执行查询
        return this.scoreLineDao.findAll(example);

    }

    @Override
    public List<ScoreLine> findAll() {
        return this.scoreLineDao.findAll();
    }

    @Override
    public void saveAll(List<ScoreLine> scoreLines) {
        for (ScoreLine scoreLine : scoreLines) {
            save(scoreLine); // Use the existing save method
        }
        // After saving all entities, you can send a message via WebSocket if needed
        try {
            ProductWebSocket.sendInfo("已成功采集 " + scoreLines.size() + " 条数据！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
