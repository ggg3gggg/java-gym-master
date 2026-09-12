package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1,
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        Assertions.assertTrue(
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assertions.assertEquals(1,
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        Assertions.assertEquals(2,
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());

        Assertions.assertEquals(thursdayChildTrainingSession,
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).get(0));

        Assertions.assertEquals(thursdayAdultTrainingSession,
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).get(1));

        Assertions.assertTrue(
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1,
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY,
                        new TimeOfDay(13, 0)
                ).size());

        Assertions.assertTrue(
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY,
                        new TimeOfDay(14, 0)
                ).isEmpty());
    }

    @Test
    void testGetCountByCoachesOneCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);

        timetable.addNewTrainingSession(
                new TrainingSession(group, coach,
                        DayOfWeek.MONDAY, new TimeOfDay(10, 0))
        );

        timetable.addNewTrainingSession(
                new TrainingSession(group, coach,
                        DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0))
        );

        List<CounterOfTrainings> result =
                timetable.getCountByCoaches();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(coach, result.get(0).getCoach());
        Assertions.assertEquals(2, result.get(0).getCount());
    }

    @Test
    void testGetCountByCoachesSeveralCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Александр", "Иванович");

        Group group = new Group("Акробатика", Age.CHILD, 60);

        timetable.addNewTrainingSession(
                new TrainingSession(group, coach1,
                        DayOfWeek.MONDAY, new TimeOfDay(10, 0))
        );

        timetable.addNewTrainingSession(
                new TrainingSession(group, coach1,
                        DayOfWeek.TUESDAY, new TimeOfDay(10, 0))
        );

        timetable.addNewTrainingSession(
                new TrainingSession(group, coach2,
                        DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0))
        );

        List<CounterOfTrainings> result =
                timetable.getCountByCoaches();

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(coach1, result.get(0).getCoach());
        Assertions.assertEquals(2, result.get(0).getCount());

        Assertions.assertEquals(coach2, result.get(1).getCoach());
        Assertions.assertEquals(1, result.get(1).getCount());
    }

    @Test
    void testGetCountByCoachesNoTrainings() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> result =
                timetable.getCountByCoaches();

        Assertions.assertTrue(result.isEmpty());
    }
}
