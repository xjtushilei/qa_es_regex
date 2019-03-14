package com.example.demo.controller;


import com.example.demo.entity.QuestionAnswer;
import com.example.demo.service.SearchService;
import com.example.demo.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class EsController {


    @Autowired
    SearchService searchService;

    @Autowired
    SpiderService spiderService;

    @GetMapping("/searchFirst")
    public QuestionAnswer searchFirst(String input) {
        return searchService.search(input, 0, 1).get(0);
    }

    @GetMapping("/search")
    public List<QuestionAnswer> search(String input, int page, int size) {
        return searchService.search(input, page, size);
    }

    @GetMapping("/spiderAndIndex")
    public HashMap<String, Object> spiderAndIndex() {
        long timeStart = System.currentTimeMillis();
        long count = spiderService.spiderToEs();
        long time = System.currentTimeMillis() - timeStart;
        HashMap<String, Object> res = new HashMap<>();
        res.put("count", count);
        res.put("time", time);
        return res;
    }
}
