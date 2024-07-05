package cn.nanchengyu.spider_score.entity;// ScoreLine.java
import lombok.Data;

import java.util.Date;
@Data

public class ScoreLine {
    private Long id;
    private String province;
    private String schoolName;
    private String majorName;
    private String admissionBatch;
    private int lowestScore;
    private int lowestRank;
    private double admissionRate;
    private String subjectRequirements;
    private Date createTime;
}
