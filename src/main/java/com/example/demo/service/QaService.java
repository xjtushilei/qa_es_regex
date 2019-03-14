package com.example.demo.service;

import com.example.demo.entity.QuestionAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QaService {


    @Autowired
    SearchService searchService;

    @Autowired
    DependencyService dependencyService;

    public String qa(String input) {
        String ans = dependencyService.getAnswerWithDependency(input);
        if (ans != null) {
            return "您需要学习：" + ans;
        }

        List<QuestionAnswer> answers = searchService.search(input, 0, 1);
        if (answers.size() == 0) {
            return "我也不知道你在说什么？您可以换个问题？";
        }
        ans = answers.get(0).getAnswer();
        return ans;
    }
}
