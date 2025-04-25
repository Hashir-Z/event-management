package com.software.combinedVolunteerList;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerHistoryWithDetailsRepository extends JpaRepository<VolunteerHistoryWithDetailsDTO, String> {

    // Fetch all records from the view
    List<VolunteerHistoryWithDetailsDTO> findAll();
}