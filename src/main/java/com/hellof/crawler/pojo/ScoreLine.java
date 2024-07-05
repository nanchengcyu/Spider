package com.hellof.crawler.pojo;// ScoreLine.java
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "score_line")
public class ScoreLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
