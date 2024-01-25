package ru.andreev.ufanet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andreev.ufanet.event.Event;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByEntityIdOrderByDateTime(UUID entityId);
}

