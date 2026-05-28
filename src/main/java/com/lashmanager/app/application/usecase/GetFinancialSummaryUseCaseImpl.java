package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.port.in.GetFinancialSummaryUseCase;
import com.lashmanager.app.domain.port.out.FinancialSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GetFinancialSummaryUseCaseImpl implements GetFinancialSummaryUseCase {

    private final FinancialSummaryRepository summaryRepository;

    @Override
    public SummaryResult execute() {
        LocalDate monthStart = LocalDate.now().withDayOfMonth(1);
        LocalDate monthEnd = monthStart.plusMonths(1).minusDays(1);

        BigDecimal incomeReceived = summaryRepository.sumIncomePaidInMonth(monthStart, monthEnd);
        BigDecimal incomePredicted = summaryRepository.sumIncomeTotalInMonth(monthStart, monthEnd);
        BigDecimal expensePaid = summaryRepository.sumExpensePaidInMonth(monthStart, monthEnd);
        BigDecimal expensePredicted = summaryRepository.sumExpenseTotalInMonth(monthStart, monthEnd);
        BigDecimal currentBalance = summaryRepository.sumAllTimePaidBalance();
        BigDecimal predictedMonthResult = incomePredicted.subtract(expensePredicted);

        return new SummaryResult(
                predictedMonthResult,
                currentBalance,
                incomeReceived,
                incomePredicted,
                expensePaid,
                expensePredicted,
                summaryRepository.last6MonthsStats()
        );
    }
}
