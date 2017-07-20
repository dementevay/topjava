package ru.javawebinar.topjava.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Andrey Dementev on 19.07.17.
 */
public class BaseEntity extends Entity{
    private static AtomicInteger counts = new AtomicInteger(0);

    public BaseEntity() {
        id = isNew() ? counts.incrementAndGet() : id;
    }

    public boolean isNew() {
        return id <= 0;
    }
}
