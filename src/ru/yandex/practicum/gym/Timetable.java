package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable =
            new TreeMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> sessions =
                timetable.computeIfAbsent(day, k -> new TreeMap<>());

        List<TrainingSession> trainingSessions =
                sessions.computeIfAbsent(time, k -> new ArrayList<>());

        trainingSessions.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(
            DayOfWeek dayOfWeek) {

        TreeMap<TimeOfDay, List<TrainingSession>> sessions =
                timetable.get(dayOfWeek);

        if (sessions == null) {
            return new TreeMap<>();
        }

        return sessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(
            DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {

        TreeMap<TimeOfDay, List<TrainingSession>> sessions =
                timetable.get(dayOfWeek);

        if (sessions == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> result = sessions.get(timeOfDay);

        if (result == null) {
            return new ArrayList<>();
        }

        return result;
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> counters = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> sessionsByTime : timetable.values()) {
            for (List<TrainingSession> sessions : sessionsByTime.values()) {
                for (TrainingSession trainingSession : sessions) {

                    Coach coach = trainingSession.getCoach();

                    counters.put(
                            coach,
                            counters.getOrDefault(coach, 0) + 1
                    );
                }
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
