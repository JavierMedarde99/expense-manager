package com.bills.web.services;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bills.web.entities.Bills;
import com.bills.web.entities.Users;
import com.bills.web.model.BillDto;
import com.bills.web.model.MonthArray;
import com.bills.web.model.YearArray;
import com.bills.web.repository.BillsRepository;
import com.bills.web.repository.RevenueMonthRepository;
import com.bills.web.repository.UsersRepository;
import com.bills.web.utils.Constants;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebService {

    private final BillsRepository billsRepository;

    private final UsersRepository usersRepository;

    private final RevenueMonthRepository revenueMonthRepository;

    /**
     * We obtain the data from the dashboard, which is all the expenses of the
     * current and previous month.
     * 
     * @param model
     * @param session
     * @param userName
     * @return
     */
    public String dashboardMoth(Model model, HttpSession session, String userName) {

        // get user by username and save in session the salary and user id
        Optional<Users> optionalUser = usersRepository.findByUserName(userName);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            session.setAttribute(Constants.SALARY_PARAM, user.getSalary());
            session.setAttribute("user", user.getId());
        } else {
            return "login";
        }

        Integer user = Integer.parseInt(session.getAttribute("user").toString());
        Double salary = Double.parseDouble(session.getAttribute(Constants.SALARY_PARAM).toString());
        LocalDate today = LocalDate.now();

        // We obtain the date and year data one month less than the current one.
        Integer year = today.getYear();
        Integer month = today.getMonthValue() - 1;
        if (today.getMonthValue() == 1) {
            year = year - 1;
        }

        // We get the data for the current month
        beforeMonth(today.getMonthValue(), today.getYear(), user, model,
                today.getMonthValue() != LocalDate.now().getMonthValue(), salary);
        // We obtain the data of the previous month and the current month
        beforeMonth(month, year, user, model, !month.equals(LocalDate.now().getMonthValue()), salary);

        model.addAttribute("date", LocalDate.now());
        model.addAttribute(Constants.SALARY_PARAM, session.getAttribute(Constants.SALARY_PARAM));
        return "web/index";
    }

    /**
     * We obtain the data from the month, which is all the expenses of the current
     * and previous month.
     * Or only show the page
     * 
     * @param model
     * @param session
     * @param month
     * @param year
     * @return
     */
    public String moth(Model model, HttpSession session, Integer month, Integer year) {

        // get the user id and salary
        Integer user = Integer.parseInt(session.getAttribute("user").toString());
        String salary = session.getAttribute(Constants.SALARY_PARAM).toString();

        // we get every month to pass it to the front-side
        List<MonthArray> monthModel = new ArrayList<>();
        for (int i = 1; Month.values().length >= i; i++) {
            monthModel.add(new MonthArray(String.valueOf(i), Month.of(i).name()));
        }

        // we get every year to pass it to the front-side
        List<YearArray> arrayInteger = new ArrayList<>();
        for (int i = 2023; Integer.parseInt(Year.now().toString()) >= i; i++) {
            arrayInteger.addFirst(new YearArray(String.valueOf(i)));
        }

        model.addAttribute("months", monthModel);
        model.addAttribute("years", arrayInteger);

        // If we do not receive any data, we will show you the current month.
        if (month == null && year == null) {
            month = LocalDate.now().getMonthValue();
            year = LocalDate.now().getYear();
        }

        try {
            // We get the data for the select month
            beforeMonth(month, year, user, model, false, Double.parseDouble(salary));
        } catch (Exception e) {
            log.error("error to obtein month. Error: {}", e.getMessage(), e);
        }

        model.addAttribute("monthCurrent", month);
        model.addAttribute("yearCurrent", year);
        model.addAttribute(Constants.SALARY_PARAM, salary);

        return "web/month";
    }

    /**
     * add an expense to the user
     * 
     * @param billDto
     * @param session
     * @param model
     * @return redirect to the dashboard
     */
    public String insertBills(BillDto billDto, HttpSession session, Model model) {

        // get the user to add the bill
        Optional<Users> optUsers = getUser(session);

        // create the bill and save it
        if (optUsers.isPresent()) {
            Users user = optUsers.get();
            Bills bill = new Bills(billDto, user);
            billsRepository.save(bill);
            model.addAttribute(Constants.SUCCESS_PARAM, "save find bill");
        } else {
            model.addAttribute(Constants.ERROR_PARAM, Constants.ERROR_MESSAGE);
        }
        return Constants.REDIRECT + "/";
    }

    /**
     * we eliminate the expense or eliminate the indicated amount
     * 
     * @param id
     * @param page
     * @param amount
     * @param session
     * @param model
     * @return dashboard or the page indicated
     */
    public String deleteBill(Long id, String page, Integer amount, HttpSession session, Model model) {

        // get the user to add the bill
        Optional<Users> optUsers = getUser(session);

        if (optUsers.isPresent()) {
            Optional<Bills> optBills = billsRepository.findById(id);
            // if the amount is 0, we delete the bill, if the amount is equal to the
            // indicated amount, we delete the bill
            if (amount == 0 || (optBills.isPresent() && optBills.get().getAmount().equals(amount))) {
                billsRepository.deleteById(Long.parseLong(id.toString()));
            } else {
                // if the amount is greater than 0, we subtract the amount and we remove the
                // indicated amount
                if (optBills.isPresent()) {
                    Bills bill = optBills.get();
                    bill.setAmount(bill.getAmount() - amount);
                    billsRepository.save(bill);
                }
            }

            model.addAttribute(Constants.SUCCESS_PARAM, "delete bill");
        } else {
            model.addAttribute(Constants.ERROR_PARAM, Constants.ERROR_MESSAGE);
        }

        // If the front-side has not indicated a page to redirect, we go to the
        // dashboard
        if (page == null || page.isEmpty()) {
            return Constants.REDIRECT + "/";
        } else {
            return Constants.REDIRECT + page;
        }

    }

    /**
     * We get the years of the user
     * 
     * @param session
     * @param model
     * @return
     */
    public String getYear(HttpSession session, Model model) {

        // get the user to add the bill
        Long idUser = Long.parseLong(session.getAttribute("user").toString());
        Optional<Users> optUsers = getUser(session);

        if (optUsers.isPresent()) {
            List<Map<String, Object>> listYear = revenueMonthRepository.geyAllYear(idUser);
            if (listYear.isEmpty()) {
                model.addAttribute("existsData", false);
            } else {
                model.addAttribute("years", listYear);
            }

        }

        return "web/year";

    }

    /**
     * 
     * We update the expense
     * 
     * @param model
     * @param session
     * @param page
     * @param billDto
     * @param id
     * @return dashboard or the page indicated
     */
    public String updateBill(Model model, HttpSession session,
            String page, BillDto billDto, Long id) {

        // get the user to add the bill
        Optional<Users> optUsers = getUser(session);

        if (optUsers.isPresent()) {
            Optional<Bills> optBills = billsRepository.findById(id);
            if (optBills.isPresent()) {
                Bills bill = optBills.get();
                bill.setAmount(billDto.getAmount());
                bill.setName(billDto.getName());
                bill.setPrice(billDto.getPrice());
                bill.setType(billDto.getType());
                bill.setSubType(billDto.getSubType());
                bill.setDateBills(billDto.getDateBills());
                billsRepository.save(bill);
                model.addAttribute(Constants.SUCCESS_PARAM, "delete bill");
            } else {
                model.addAttribute(Constants.ERROR_PARAM, Constants.ERROR_MESSAGE);
            }
        } else {
            model.addAttribute(Constants.ERROR_PARAM, Constants.ERROR_MESSAGE);
        }

        // If the front-side has not indicated a page to redirect, we go to the
        // dashboard
        if (page == null || page.isEmpty()) {
            return Constants.REDIRECT + "/";
        } else {
            return Constants.REDIRECT + page;
        }
    }

    private void beforeMonth(Integer month, Integer year, Integer user, Model model, boolean moth, Double salary) {
        DecimalFormat df = new DecimalFormat("#.##");
        double total = 0;
        Integer yearBefore = year;
        List<Bills> listBills = billsRepository.getOneMonthBills(month, year, user);

        Integer mothBefore = month - 1;

        // if the month is 0, we go to the previous year
        if (mothBefore == 0) {
            mothBefore = 12;
            yearBefore = year - 1;
        }

        // we get the total of the expenses
        for (Bills bill : listBills) {
            total = total + (bill.getPrice() * bill.getAmount());
        }
        Optional<Double> revenuaLastMoth = revenueMonthRepository.getRevenue(mothBefore, yearBefore,user);
        if (moth) {
            if (listBills.isEmpty()) {
                model.addAttribute("existsData", true);
            } else {
                model.addAttribute("billsBefore", listBills);
                model.addAttribute("totalBefore", df.format(total));
                model.addAttribute("monthBefore", Month.of(month));
                model.addAttribute("amountBillsBefore", listBills.size() + 2);
                if (revenuaLastMoth.isPresent()) {
                    model.addAttribute("revenueBefore", df.format(revenuaLastMoth.get() + salary - total));
                } else {
                    model.addAttribute("revenueBefore", df.format(salary - total));
                }
            }

        } else {
            if (listBills.isEmpty()) {
                model.addAttribute("existsDatabefore", true);
            } else {
                model.addAttribute("bills", listBills);
                model.addAttribute("total", df.format(total));
                model.addAttribute("month", Month.of(month));
                model.addAttribute("amountBills", listBills.size() + 2);
                if (revenuaLastMoth.isPresent()) {
                    model.addAttribute("revenue", df.format(revenuaLastMoth.get() + salary - total));
                } else {
                    model.addAttribute("revenue", df.format(salary - total));
                }
            }

        }

    }

    /**
     * We get the user by the session
     * 
     * @param session
     * @return Optional the user
     */
    private Optional<Users> getUser(HttpSession session) {
        Long idUser = Long.parseLong(session.getAttribute("user").toString());
        return usersRepository.findById(idUser);
    }
}
