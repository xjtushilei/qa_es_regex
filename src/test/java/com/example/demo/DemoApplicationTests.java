package com.example.demo;

import com.example.demo.repository.DependencyRepository;
import com.example.demo.service.DependencyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    DependencyRepository dependencyRepository;

    @Autowired
    DependencyService dependencyService;

    @Test
    public void t1() {
        System.out.println(dependencyService.getAnswerWithDependency("学了二叉树学什么"));
        System.out.println(dependencyService.getAnswerWithDependency("学数组前学习什么"));
        System.out.println(dependencyService.getAnswerWithDependency("学习二叉树之前学习什么"));
    }

}
