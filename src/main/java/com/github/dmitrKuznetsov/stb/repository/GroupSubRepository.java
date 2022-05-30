package com.github.dmitrKuznetsov.stb.repository;

import com.github.dmitrKuznetsov.stb.repository.entity.GroupSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupSubRepository extends JpaRepository<GroupSub, Integer> {
}