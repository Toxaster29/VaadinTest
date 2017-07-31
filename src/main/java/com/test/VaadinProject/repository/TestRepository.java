package com.test.VaadinProject.repository;

import com.test.VaadinProject.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
