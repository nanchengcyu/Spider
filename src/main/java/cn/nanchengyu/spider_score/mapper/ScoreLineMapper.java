package cn.nanchengyu.spider_score.mapper;// ScoreLineMapper.java
import cn.nanchengyu.spider_score.entity.ScoreLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface ScoreLineMapper {
    
    @Insert("INSERT INTO score_line (province, school_name, major_name, admission_batch, lowest_score, lowest_rank, admission_rate, subject_requirements, create_time) " +
            "VALUES (#{province}, #{schoolName}, #{majorName}, #{admissionBatch}, #{lowestScore}, #{lowestRank}, #{admissionRate}, #{subjectRequirements}, #{createTime})")
    void insertScoreLine(ScoreLine scoreLine);
}
