package com.tmobile.reallyme.core.persistence.impl;

import java.util.UUID;

/**
 * Generates a version 4 UUID.
 * Based on {@link java.util.UUID}
 * User: Kolesnik Aleksey
 * Date: 14.07.2009
 * Time: 0:43:09
 */
public class GUIDGenerator {
    /**
     * Generates UUID.
     * @return new ID;
     */
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
