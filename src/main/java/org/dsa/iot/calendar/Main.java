package org.dsa.iot.calendar;

import org.dsa.iot.dslink.DSLinkFactory;

public class Main {
    private Main() {
    }

    public static void main(String[] args) {
        DSLinkFactory.start(args, new CalendarHandler());
    }
}
