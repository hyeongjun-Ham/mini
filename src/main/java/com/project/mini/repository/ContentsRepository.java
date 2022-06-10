package com.project.mini.repository;

import com.project.mini.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsRepository extends JpaRepository<Contents, Long> {
//        List<Contents> findAllByOrderByCreatedAtDesc();
}
