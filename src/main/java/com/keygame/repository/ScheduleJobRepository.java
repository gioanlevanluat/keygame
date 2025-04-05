package com.keygame.repository;

import com.keygame.entity.ScheduleJob;
import com.keygame.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, Long> {

}
