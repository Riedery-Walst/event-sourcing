package ru.andreev.ufanet.strategy;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import ru.andreev.ufanet.event.Event;


public class SuperClassExclusionStrategy implements ExclusionStrategy {
    public boolean shouldSkipClass(Class<?> clazz) {
        return clazz.equals(Event.class) || clazz.isAssignableFrom(Event.class);
    }

    public boolean shouldSkipField(FieldAttributes field) {
        Class<?> declaringClass = field.getDeclaringClass();
        return declaringClass.equals(Event.class) && (field.getName().equals("eventId") || field.getName().equals("dateTime"));
    }
}
