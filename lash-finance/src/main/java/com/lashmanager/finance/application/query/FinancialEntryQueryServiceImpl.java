package com.lashmanager.finance.application.query;

import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.model.FinancialEntryDetails;
import com.lashmanager.finance.domain.model.FinancialEntryFilter;
import com.lashmanager.finance.domain.model.FinancialSummary;
import com.lashmanager.finance.domain.port.in.FinancialEntryQueryService;
import com.lashmanager.finance.domain.port.out.FinancialEntryQueryRepository;
import com.lashmanager.finance.domain.port.out.FinancialSummaryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialEntryQueryServiceImpl implements FinancialEntryQueryService {

    private final FinancialEntryQueryRepository repository;
    private final FinancialSummaryRepository summaryRepository;

    @Override
    public FinancialSummary getSummary() {
        LocalDate monthStart = LocalDate.now().withDayOfMonth(1);
        LocalDate monthEnd = monthStart.plusMonths(1).minusDays(1);

        BigDecimal incomeReceived = summaryRepository.sumIncomePaidInMonth(monthStart, monthEnd);
        BigDecimal incomePredicted = summaryRepository.sumIncomeTotalInMonth(monthStart, monthEnd);
        BigDecimal expensePaid = summaryRepository.sumExpensePaidInMonth(monthStart, monthEnd);
        BigDecimal expensePredicted = summaryRepository.sumExpenseTotalInMonth(monthStart, monthEnd);
        BigDecimal currentBalance = summaryRepository.sumAllTimePaidBalance();

        return new FinancialSummary(
                incomePredicted.subtract(expensePredicted),
                currentBalance,
                incomeReceived,
                incomePredicted,
                expensePaid,
                expensePredicted,
                summaryRepository.last6MonthsStats());
    }

    @Override
    public Page<FinancialEntryDetails> list(FinancialEntryFilter filter) {
        var pageable = PageRequest.of(filter.page(), filter.size(), Sort.by(Sort.Direction.DESC, "dueDate"));
        return repository
                .listWithFilters(
                        filter.from(), filter.to(), filter.category(), filter.expenseType(), filter.type(), pageable)
                .map(w -> toDetails(w.entry(), w.counterpart()));
    }

    @Override
    public List<String> findDistinctCategories() {
        return repository.findDistinctCategories();
    }

    private FinancialEntryDetails toDetails(FinancialEntry e, String counterpart) {
        return new FinancialEntryDetails(
                e.getId(),
                e.getType(),
                e.getExpenseType(),
                e.getDescription(),
                e.getAmount(),
                e.getDueDate(),
                e.getPaymentDate(),
                e.getStatus(),
                e.getCategory(),
                e.getPaymentMethod(),
                counterpart,
                e.getNotes(),
                e.getAppointmentId() != null,
                e.getAppointmentId());
    }
}
