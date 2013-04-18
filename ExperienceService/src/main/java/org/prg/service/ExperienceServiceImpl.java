package org.prg.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.prg.domain.EventType;
import org.prg.domain.GameEvent;
import org.prg.domain.GameStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    private BlockingQueue<GameEvent> incomingEvents = new LinkedBlockingQueue<GameEvent>();

    private Map<Integer, GameStatistic> inGame = new HashMap<Integer, GameStatistic>();

    @Autowired
    private GameStatisticProvider provider;

    public ExperienceServiceImpl() {
        super();
        new Thread(new ProcessQueue()).start();
    }

    public void receiveEvent(GameEvent event) {
        try {
            incomingEvents.put(event);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<GameStatistic> getGameStatistics(int userId, Date from, Date to) {
        List<GameStatistic> statistics = provider.getStatistics(userId, from, to);
        LOGGER.debug("Game Statistics for user Id:  " + userId + " " + Arrays.toString(statistics.toArray(new GameStatistic[]{})));
        return statistics;
    }

    public class ProcessQueue implements Runnable {

        public void run() {
            while (true) {
                // checking queue
                if (!incomingEvents.isEmpty()) {
                    try {
                        GameEvent event = incomingEvents.take();
                        LOGGER.debug("Taking event '" + event + "' from queue");
                        updateInGame(event);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void updateInGame(GameEvent event) {
            Integer userId = Integer.valueOf(event.getUserId());
            if (inGame.containsKey(userId)) {
                GameStatistic stat = inGame.get(userId);
                switch (event.getType()) {
                case EXPERIENCE:
                    stat.addPoints(event.getPoints());
                    break;
                case FINISH:
                    stat.setEndTimestamp(event.getTimestamp());
                    provider.store(stat);
                    inGame.remove(userId);
                    break;
                case START:
                    throw new RuntimeException("Cannot start new game until current one does not finished.");
                }
            } else {
                if (EventType.START.equals(event.getType())) {
                    GameStatistic stat = new GameStatistic();
                    stat.setStartTimestamp(event.getTimestamp());
                    stat.setUserId(event.getUserId());
                    inGame.put(userId, stat);
                } else {
                    throw new RuntimeException("Cannot update statistics without started game.");
                }
            }
        }

    }

    public boolean hasIncomingEvents() {
        return !incomingEvents.isEmpty() || !inGame.isEmpty();
    }

}
