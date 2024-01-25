package ru.andreev.ufanet.strategy;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import ru.andreev.ufanet.event.Event;


public class SuperClassExclusionStrategy implements ExclusionStrategy {
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes field) {
        return field.getDeclaringClass().equals(Event.class) && field.getName()
                .equals("eventId");
    }
}