package com.example.demo.service;


import com.example.demo.entity.QuestionAnswer;
import com.example.demo.repository.QuestionAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    QuestionAnswerRepository questionAnswerRepository;


    public List<QuestionAnswer> search(String input, int page, int size) {
        return questionAnswerRepository.findByQuestionOrAnswer(input, input, PageRequest.of(page, size));
    }
}
