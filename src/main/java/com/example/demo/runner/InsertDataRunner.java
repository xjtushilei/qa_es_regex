package com.example.demo.runner;

import com.example.demo.entity.Dependency;
import com.example.demo.repository.DependencyRepository;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class InsertDataRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InsertDataRunner.class);

    @Autowired
    DependencyRepository dependencyRepository;

    @Override
    public void run(String... args) {
        List res = new ArrayList<>();
        try {
            res = FileUtils.readLines(ResourceUtils.getFile("classpath:data/dependency.csv"), "utf-8");
        } catch (IOException e) {
            logger.error("打开文件 'data/dependency.csv' 错误");
            e.printStackTrace();
        }
        // 删除之前的所有数据，每次以csv中的为准。
        dependencyRepository.deleteAll();
        for (int i = 0; i < res.size(); i++) {
            String[] t = res.get(i).toString().split(",");
            dependencyRepository.save(new Dependency(i, t[0], t[1], t[2]));
        }
        logger.info("初始化数据库结束");
        logger.info("目前该数据库表中共有："+dependencyRepository.count());


    }
}
