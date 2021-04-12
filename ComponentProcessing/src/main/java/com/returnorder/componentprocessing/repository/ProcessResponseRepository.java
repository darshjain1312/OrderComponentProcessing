package com.returnorder.componentprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.returnorder.componentprocessing.model.ProcessResponse;

@Repository
public interface ProcessResponseRepository extends JpaRepository<ProcessResponse, String> {

}
