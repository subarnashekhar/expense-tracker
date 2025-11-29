package com.techsmarthub.expensetracker.service;

import com.techsmarthub.expensetracker.dto.ExpenseDTO;
import com.techsmarthub.expensetracker.dto.IncomeDTO;
import com.techsmarthub.expensetracker.entity.ProfileEntity;
import com.techsmarthub.expensetracker.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final IncomeService incomeService;
    private final ProfileService profileService;
    private final ExpenseService expenseService;

    public Map<String, Object> getDashBoardData() {
        Map<String, Object> dashBoardData = new LinkedHashMap<>();
        List<IncomeDTO> latest5IncomesForCurrentUser = incomeService.getLatest5IncomesForCurrentUser();
        List<ExpenseDTO> latest5ExpensesForCurrentUser = expenseService.getLatest5ExpensesForCurrentUser();
        dashBoardData.put("totalIncomeForCurrentUser", incomeService.getTotalIncomeForCurrentUser());
        dashBoardData.put("totalExpensesForCurrentUser", expenseService.getTotalExpenseForCurrentUser());
        dashBoardData.put("totalBalance", incomeService.getTotalIncomeForCurrentUser().subtract(expenseService.getTotalExpenseForCurrentUser()));
        dashBoardData.put("latest5Incomes", latest5IncomesForCurrentUser);
        dashBoardData.put("latest5Expenses", latest5ExpensesForCurrentUser);
        return dashBoardData;
    }
}
