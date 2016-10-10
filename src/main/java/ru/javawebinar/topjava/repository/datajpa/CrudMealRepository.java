package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    //@Query("SELECT m FROM Meal m WHERE m.user.id =:userId ORDER BY m.dateTime DESC")
    List<Meal> findByUser_IdOrderByDateTimeDesc(int userId);

    //@Transactional
    @Modifying
    //@Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int deleteByIdAndUser_Id(int id, int userId);
    //int delete (@Param("id") int id, @Param("userId") int userId);

    //@Query("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    //Meal get (@Param("id") int id, @Param("userId") int userId);
    Meal findByIdAndUser_Id(int id, int userId);

    //@Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime>=:startDate AND m.dateTime<=:endDate ORDER BY m.dateTime DESC")
    //List<Meal> getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);
    List<Meal> findAllByUser_IdAndDateTimeGreaterThanEqualAndDateTimeLessThanEqualOrderByDateTimeDesc(int userId, LocalDateTime startDate, LocalDateTime endDate);

    @Override
    Meal save(Meal meal);
}
