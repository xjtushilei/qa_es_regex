package com.example.demo.repository;

import com.example.demo.entity.Dependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface DependencyRepository extends JpaRepository<Dependency, Integer> {

    List<Dependency> findByStartTopic(String startTopic);

    List<Dependency> findByEndTopic(String endTopic);
}
