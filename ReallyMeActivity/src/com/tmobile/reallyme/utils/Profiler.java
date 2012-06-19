package com.tmobile.reallyme.utils;

/**
 * User: Kolesnik Aleksey
 * Date: 02.07.2009
 * Time: 16:07:43
 */
public class Profiler {
    private Log log = new Log("PROFILER");

    private String name;
    private Long s;

    public Profiler(String name) {
        this.name = name;
    }

    public void start() {
        s = System.currentTimeMillis();
    }
    public void end() {
        Long currentTime = System.currentTimeMillis();
        log.info(name + " takes " + (currentTime - s) + " ms. | Free memory = {r.freeMemory()}");
    }
}
