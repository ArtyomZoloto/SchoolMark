package com.inno.service;

import com.inno.db.dto.TestAndWorksInfoDto;
import com.inno.db.dto.TestStatisticDto;
import com.inno.db.dto.TestStatisticWithoutOrganizerDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Сервис класс предназначенный для получения информации по проведенным тестам
 */
public interface TestStatisticService {
    /**
     * Получение информации по контрольным за указанный период
     * @param dateFrom дата с которой начинается выборка (включая), null для игнорирования параметра
     * @param dateTo на какой дате заканчивается выборка (включая), null для игнорирования параметра
     * @return список TestStatisticDto содержащий краткую инфрмацию о тесте
     */
    List<TestStatisticDto> getTestsStatistic(LocalDate dateFrom, LocalDate dateTo);

    /**
     * Получение информации по тестам за указанный период с группировкой по учителю, проводившему контрольную
     * @param dateFrom дата с которой начинается выборка (включая), null для игнорирования параметра
     * @param dateTo на какой дате заканчивается выборка (включая), null для игнорирования параметра
     * @return Map, в котором ключ это имя учителя, проводившего контрольную,
     *  а значение это список TestStatisticDto содержащий краткую инфрмацию о тесте
     */
    Map<String, List<TestStatisticWithoutOrganizerDto>> getTestsStatisticGroupedByOwner(LocalDate dateFrom,
                                                                                        LocalDate dateTo);

    /**
     * Получение более подробной информации о контрольной включая статистику о сданных работах учеников
     * @param testId идентификатор контрольной работы
     * @return Описание контрольной работы и статистика по сданных учениками работах
     */
    TestAndWorksInfoDto getTestAndWorksInfo(int testId);
}
