package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
}
