package com.project.moflis.Specification;

import com.project.moflis.entity.Schedule;
import com.project.moflis.enums.SchedulesStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ScheduleSpecs {

    //userId 필터
    public static Specification<Schedule> hasUserId(int userId) {
        return (root, query, builder) -> builder.equal(root.get("user").get("id"), userId);
    }

    //날짜 필터
    public static Specification<Schedule> dateBetween(String startDate, String endDate) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if (startDate != null) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("scheduleDate"), LocalDate.parse(startDate)));
            }
            if (endDate != null) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get("scheduleDate"), LocalDate.parse(endDate)));
            }
            return predicate;
        };
    }

    public static Specification<Schedule> isActive() {
        return (root, query, builder) -> builder.equal(root.get("schedulesStatus"), SchedulesStatus.ACTIVE);
    }
}
