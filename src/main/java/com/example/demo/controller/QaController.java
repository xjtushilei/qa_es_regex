package com.example.demo.controller;


import com.example.demo.service.QaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QaController {

    private final Logger logger = LoggerFactory.getLogger(QaController.class);

    @Autowired
    QaService qaService;

    @GetMapping("/q")
    public String q(String input) {
        logger.info("question:" + input);
        String s = qaService.qa(input);
        logger.info("answer:" + s);
        return s;
    }
}
