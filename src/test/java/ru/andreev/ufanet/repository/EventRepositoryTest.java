package ru.andreev.ufanet.repository;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import ru.andreev.ufanet.event.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testFindByEntityIdOrderByDateTime() {
        UUID randomId = UUID.randomUUID();

        Event firstEvent = new Event();
        firstEvent.setEntityId(randomId);
        firstEvent.setEventData("First");
        firstEvent.setDateTime(LocalDateTime.of(2010,10,10,10,10));

        Event secondEvent = new Event();
        secondEvent.setEntityId(randomId);
        secondEvent.setEventData("Second");
        secondEvent.setDateTime(LocalDateTime.of(2005,5,5,5,5));

        eventRepository.save(firstEvent);
        eventRepository.save(secondEvent);

        List<Event> events = eventRepository.findByEntityIdOrderByDateTime(randomId);

        assertEquals(2, events.size());
        assertEquals("First", events.get(0).getEventData());
        assertEquals("Second", events.get(1).getEventData());
    }
}