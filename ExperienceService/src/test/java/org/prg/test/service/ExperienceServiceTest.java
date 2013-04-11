package org.prg.test.service;

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
public class ExperienceServiceTest{

    @Autowired
    private ExperienceService service;

    private CountDownLatch latch;
    
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
        receiveEvent();
        List<GameStatistic> stat = service.getGameStatistics(1000);
        Assert.assertNotNull(stat);
        Assert.assertEquals(30, stat.get(0).getPoints());
    }

    @Test
    public void receiveEvents()  throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            final List<GameEvent> events = TestUtil.generateEventSequence(i);
            new Thread(new Runnable() {
                public void run() {
                    for (GameEvent event : events) {
                        service.receiveEvent(event);
                    }
                }
            }).start();
        }
        latch = new CountDownLatch(1);
        latch.await(1, TimeUnit.SECONDS);
        Assert.assertFalse(service.hasIncomingEvents());
    }

}
