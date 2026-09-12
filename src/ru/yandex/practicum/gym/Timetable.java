package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Timetable {

    private Map<DayOfWeek, List<TrainingSession>> timetable = new TreeMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {

        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        List<TrainingSession> sessions =
                timetable.computeIfAbsent(day, k -> new ArrayList<>());

        int index = 0;
        while (index < sessions.size()
                && sessions.get(index).getTimeOfDay().compareTo(time) <= 0) {
            index++;
        }
        sessions.add(index, trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {

        List<TrainingSession> sessions = timetable.get(dayOfWeek);

        if (sessions == null) {
            return new ArrayList<>();
        }

        return sessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(
            DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {

        List<TrainingSession> sessions = timetable.get(dayOfWeek);

        if (sessions == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> result = new ArrayList<>();

        for (TrainingSession trainingSession : sessions) {
            if (trainingSession.getTimeOfDay().equals(timeOfDay)) {
                result.add(trainingSession);
            }
        }

        return result;
    }

    public List<CounterOfTrainings> getCountByCoaches() {

        Map<Coach, Integer> counters = new HashMap<>();

        for (List<TrainingSession> sessions : timetable.values()) {
            for (TrainingSession trainingSession : sessions) {

                Coach coach = trainingSession.getCoach();

                counters.put(
                        coach,
                        counters.getOrDefault(coach, 0) + 1
                );
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : counters.entrySet()) {
            result.add(new CounterOfTrainings(
                    entry.getKey(),
                    entry.getValue()
            ));
        }

        result.sort((a, b) -> b.getCount() - a.getCount());

        return result;
    }
}
