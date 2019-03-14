package com.example.demo.service;


import com.example.demo.entity.QuestionAnswer;
import com.example.demo.repository.QuestionAnswerRepository;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpiderService {


    public static void main(String[] args) {
        spiderToTerminal();
    }


    @Autowired
    QuestionAnswerRepository questionAnswerRepository;

    private static final Logger logger = LoggerFactory.getLogger(SpiderService.class);

    /**
     * 索引到es中，样例
     */
    public long spiderToEs() {
        long count = 0;
        for (Object key : getTopics()) {
            logger.info("关键字：" + key.toString());
            List<String> urls = getUrls(key.toString());
            logger.info("发现的大概问题数量：" + urls.size());
            for (String url : urls) {
                QuestionAnswer questionAnswer = praseUrl(url);
                if (null != questionAnswer) {
                    count++;
                    questionAnswerRepository.index(questionAnswer);
                }
            }
        }
        return count;
    }


    /**
     * 输出到控制台，样例
     */
    public static void spiderToTerminal() {
        for (Object key : getTopics()) {
            logger.info("关键字：" + key.toString());
            List<String> urls = getUrls(key.toString());
            logger.info("发现的大概问题数量：" + urls.size());
            for (String url : urls) {
                QuestionAnswer questionAnswer = praseUrl(url);
                if (questionAnswer != null) {
                    System.out.println(questionAnswer.toString());
                }
            }
        }
    }


    private static QuestionAnswer praseUrl(String url) {
        QuestionAnswer res = new QuestionAnswer();
        try {
            Document doc = Jsoup.connect(url).get();
            String question = doc.selectFirst(".ask-title").text();
            String extraText = doc.select(".wgt-best-mask").text();
            String answer = doc.select(".bd.answer .best-text").text().replaceFirst(extraText, "");
            if (answer.length() == 0 || null == question) {
                return null;
            }
            res.setAnswer(answer);
            res.setId(url);
            res.setQuestion(question);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }


    private static List<String> getUrls(String key) {
        List<String> res = new ArrayList<>();
        for (int pn = 10; pn <= 200; pn = pn + 10) {
            try {
                String url = "https://zhidao.baidu.com/search?word=" + URLEncoder
                        .encode(key, "gbk") + "&ie=gbk&site=-1&sites=0&date=0&pn=" + pn;
                Document doc = Jsoup.connect(url).get();
                Elements qList = doc.select("#wgt-list dl dt a");
                for (Element element : qList) {
                    //注意这里有abs，自行百度搜一下原因
                    res.add(element.attr("abs:href"));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private static List getTopics() {
        List res = new ArrayList<>();
        try {
            res = FileUtils.readLines(ResourceUtils.getFile("classpath:data/topic.txt"), "utf-8");
        } catch (IOException e) {
            logger.error("打开文件 'data/topic.txt' 错误");
            e.printStackTrace();
        }
        return res;
    }


}
