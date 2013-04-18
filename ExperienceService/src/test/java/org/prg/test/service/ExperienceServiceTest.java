package org.prg.test.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.prg.domain.EventType;
import org.prg.domain.GameEvent;
import org.prg.domain.GameStatistic;
import org.prg.service.ExperienceService;
import org.prg.test.config.ApplicationContext;
import org.prg.test.util.TestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationContext.class })
public class ExperienceServiceTest {

    @Autowired
    private ExperienceService service;

    private CountDownLatch latch;

    private Date timeTest1, timeTest2;

    @Test
    public void receiveEvent() throws InterruptedException {
        GameEvent startEvent = TestUtil.createSingleEvent(1000, EventType.START, 0);
        service.receiveEvent(startEvent);
        Assert.assertTrue(service.hasIncomingEvents());
        GameEvent expEvent = TestUtil.createSingleEvent(1000, EventType.EXPERIENCE, 10);
        service.receiveEvent(expEvent);
        Assert.assertTrue(service.hasIncomingEvents());
        GameEvent expEvent2 = TestUtil.createSingleEvent(1000, EventType.EXPERIENCE, 20);
        service.receiveEvent(expEvent2);
        Assert.assertTrue(service.hasIncomingEvents());
        GameEvent finishEvent = TestUtil.createSingleEvent(1000, EventType.FINISH, 0);
        service.receiveEvent(finishEvent);
        latch = new CountDownLatch(1);
        latch.await(1, TimeUnit.SECONDS);
        Assert.assertFalse(service.hasIncomingEvents());
    }

    @Test
    public void getGameStatistic() throws InterruptedException {
        launchEvents();
        latch = new CountDownLatch(1);
        latch.await(3, TimeUnit.SECONDS);
        List<GameStatistic> stat1 = service.getGameStatistics(0, timeTest1, null);
        Assert.assertNotNull(stat1);
        List<GameStatistic> stat2 = service.getGameStatistics(0, timeTest1, timeTest2);
        Assert.assertNotNull(stat2);
    }

    @Test
    public void receiveEvents() throws InterruptedException {
        launchEvents();
        latch = new CountDownLatch(1);
        latch.await(3, TimeUnit.SECONDS);
        Assert.assertFalse(service.hasIncomingEvents());
    }

    private void launchEvents() throws InterruptedException {
        for (int j = 0; j < 10; j++) {
            if (j == 1) {
                timeTest1 = new Date();
            }
            if (j == 8) {
                timeTest2 = new Date();
            }
            for (int i = 0; i < 10; i++) {
                final List<GameEvent> events = TestUtil.generateEventSequence(i);
                new Thread(new Runnable() {
                    public void run() {
                        for (GameEvent event : events) {
                            service.receiveEvent(event);
                        }
                    }
                }).start();
            }
        }

    }

}
