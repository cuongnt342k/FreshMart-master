package com.dt.ducthuygreen.services.impl;

import com.dt.ducthuygreen.dto.OrdersStatistics;
import com.dt.ducthuygreen.repos.StatisticsRepository;
import com.dt.ducthuygreen.services.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StatisticsServiceImpl implements IStatisticsService {

    @Autowired
    StatisticsRepository statisticsRepository;

    public static Timestamp getStartTimestamp(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp getEndTimestamp(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    @Override
    public Page<OrdersStatistics> findOrdersStatistics(Pageable pageable) {
        Date currentDate = new Date();
        Date start = new Date(getStartTimestamp(currentDate.getMonth()).getTime());
        Date end = new Date(getEndTimestamp(currentDate.getMonth()).getTime());
        return statisticsRepository.findOrdersStatistics(pageable,start, end);
    }

    @Override
    public Page<OrdersStatistics> findCategoryStatistics(Pageable pageable) {
        Date currentDate = new Date();
        Date start = new Date(getStartTimestamp(currentDate.getMonth()).getTime());
        Date end = new Date(getEndTimestamp(currentDate.getMonth()).getTime());
        return statisticsRepository.findCategoryStatistics(pageable,start, end);
    }

    @Override
    public List<OrdersStatistics> findSaleStatistics() {
        return statisticsRepository.findSaleStatistics();
    }
}
