package com.example.demo.repository;

import com.example.demo.entity.QuestionAnswer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface QuestionAnswerRepository extends ElasticsearchRepository<QuestionAnswer, String> {

    // 语法参考：   https://www.cnblogs.com/chengyangyang/p/10233337.html
    List<QuestionAnswer> findByQuestionOrAnswer(String q,String a, Pageable pageable);

}