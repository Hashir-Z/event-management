package com.software.volunteer_history;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerHistoryRepository extends JpaRepository<VolunteerHistory, VolunteerHistoryId> {}