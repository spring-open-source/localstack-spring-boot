package com.hardik.killchamber.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.killchamber.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID>{

}
