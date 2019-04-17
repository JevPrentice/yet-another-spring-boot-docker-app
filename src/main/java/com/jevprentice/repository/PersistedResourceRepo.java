package com.jevprentice.repository;

import com.jevprentice.model.PersistedResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersistedResourceRepo extends JpaRepository<PersistedResource, UUID> {
}
