package com.lashmanager.finance.application.usecase;

import com.lashmanager.finance.domain.port.in.GetFinancialSummaryUseCase;
import com.lashmanager.finance.domain.port.out.FinancialSummaryRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

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

        return new SummaryResult(
                incomePredicted.subtract(expensePredicted),
                currentBalance,
                incomeReceived,
                incomePredicted,
                expensePaid,
                expensePredicted,
                summaryRepository.last6MonthsStats()
        );
    }
}
