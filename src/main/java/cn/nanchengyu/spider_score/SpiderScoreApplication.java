package cn.nanchengyu.spider_score;


import cn.nanchengyu.spider_score.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpiderScoreApplication implements CommandLineRunner {

    @Autowired
    private SpiderService spiderService;

    public static void main(String[] args) {
        SpringApplication.run(SpiderScoreApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        spiderService.fetchData();
    }
}
