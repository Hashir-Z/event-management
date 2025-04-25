package com.software.preference;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface PreferenceRepository extends JpaRepository<Preference, Integer> {}
