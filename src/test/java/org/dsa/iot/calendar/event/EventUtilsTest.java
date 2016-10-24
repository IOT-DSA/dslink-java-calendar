package org.dsa.iot.calendar.event;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;
import static org.assertj.core.api.Assertions.assertThat;

public class EventUtilsTest {
    private static final Duration wantedDuration = Duration.of(2, HOURS);
    private static final String TITLE = "jlkfdsjalk";
    private final Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private final Duration eventDuration = Duration.of(1, HOURS);
    private Instant startOfEvent;

    @Before
    public void setUp() {
        EventUtils.clock = clock;
        startOfEvent = Instant.now(clock);
    }

    @Test
    public void now_when_no_events() {
        TimeRange expectedResult = new TimeRange(startOfEvent, startOfEvent.plus(wantedDuration));

        TimeRange timeRange = EventUtils.findNextFreeTimeRange(new ArrayList<>(), wantedDuration);

        assertThat(timeRange).isEqualTo(expectedResult);
    }

    @Test
    public void end_of_current_event() {
        Instant endOfEvent = startOfEvent.plus(eventDuration);
        TimeRange expectedResult = new TimeRange(endOfEvent, endOfEvent.plus(wantedDuration));
        List<DSAEvent> events = Lists.newArrayList(new DSAEvent(TITLE, startOfEvent, startOfEvent.plus(eventDuration)));

        TimeRange timeRange = EventUtils.findNextFreeTimeRange(events, wantedDuration);

        assertThat(timeRange).isEqualTo(expectedResult);
    }

    @Test
    public void when_only_one_event_and_its_over() {
        Instant now = Instant.now(clock);
        Instant startOfEvent = now.minus(10, HOURS);
        Instant endOfEvent = now.minus(1, HOURS);
        TimeRange expectedResult = new TimeRange(now, now.plus(wantedDuration));
        List<DSAEvent> events = Lists.newArrayList(new DSAEvent(TITLE, startOfEvent, endOfEvent));

        TimeRange timeRange = EventUtils.findNextFreeTimeRange(events, wantedDuration);

        assertThat(timeRange).isEqualTo(expectedResult);
    }
}
