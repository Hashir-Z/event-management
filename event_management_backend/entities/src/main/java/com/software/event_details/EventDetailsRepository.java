package com.software.event_details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventDetailsRepository extends JpaRepository<EventDetails, Integer> {}