package com.bills.web.services;

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

    public String dashboardMoth(Model model, HttpSession session, String userName) {
        try {
            Users user = usersRepository.findByUserName(userName).get();
            session.setAttribute("salary", user.getSalary());
            session.setAttribute("user", user.getId());
        } catch (Exception e) {
            return "login";
        }

        Integer user = Integer.parseInt(session.getAttribute("user").toString());
        LocalDate today = LocalDate.now();
        Integer year = today.getYear();
        Integer month = today.getMonthValue() - 1;
        if (today.getMonthValue() == 1) {
            year = year - 1;
        }
        beforeMonth(today.getMonthValue(), today.getYear(), user, model,
                today.getMonthValue() != LocalDate.now().getMonthValue());
        beforeMonth(month, year, user, model, !month.equals(LocalDate.now().getMonthValue()));

        model.addAttribute("date", LocalDate.now());
        model.addAttribute("salary", session.getAttribute("salary"));
        return "web/index";
    }

    public String moth(Model model, HttpSession session, Integer month, Integer year) {
        Integer user = Integer.parseInt(session.getAttribute("user").toString());
        String salary = session.getAttribute("salary").toString();
        List<MonthArray> monthModel = new ArrayList();
        for (int i = 1; Month.values().length >= i; i++) {
            monthModel.add(new MonthArray(String.valueOf(i), Month.of(i).name()));
        }
        List<YearArray> arrayInteger = new ArrayList();
        for (int i = 2023; Integer.parseInt(Year.now().toString()) >= i; i++) {
            arrayInteger.addFirst(new YearArray(String.valueOf(i)));
        }

        model.addAttribute("months", monthModel);
        model.addAttribute("years", arrayInteger);

        if (month == null && year == null) {
            month = LocalDate.now().getMonthValue();
            year = LocalDate.now().getYear();
        }

        beforeMonth(month, year, user, model, false);

        model.addAttribute("monthCurrent", month);
        model.addAttribute("yearCurrent", year);
        model.addAttribute("salary", salary);

        return "web/month";
    }

    public String insertBills(String name, Double price,
            String type, String subtype, LocalDate dateBills, Integer amount, HttpSession session, Model model) {
        Long idUser = Long.parseLong(session.getAttribute("user").toString());
        Optional<Users> optUsers = usersRepository.findById(idUser);
        if (optUsers.isPresent()) {
            Users user = optUsers.get();
            Bills bill = new Bills(name, price, type, subtype, dateBills, amount, user.getId());
            billsRepository.save(bill);
            model.addAttribute("success", "save find bill");
        } else {
            model.addAttribute("error", "user not found in data base");
        }
        return Constants.REDIRECT + "/";
    }

    public String deleteBill(Integer id, String page, Integer amount, HttpSession session, Model model) {
        Long idUser = Long.parseLong(session.getAttribute("user").toString());
        Optional<Users> optUsers = usersRepository.findById(idUser);
        if (optUsers.isPresent()) {
            if (amount == 0) {
                billsRepository.deleteById(Long.parseLong(id.toString()));
            } else {
                Optional<Bills> optBills = billsRepository.findById(Long.parseLong(id.toString()));
                if (optBills.isPresent()) {
                    Bills bill = optBills.get();
                    bill.setAmount(bill.getAmount() - amount);
                    billsRepository.save(bill);
                }
            }

            model.addAttribute("success", "delete bill");
        } else {
            model.addAttribute("error", "user not found in data base");
        }

        if (page == null || page.isEmpty()) {
            return Constants.REDIRECT + "/";
        } else {
            return Constants.REDIRECT + page;
        }

    }

    public String getYear(HttpSession session, Model model) {
        Long idUser = Long.parseLong(session.getAttribute("user").toString());
        Optional<Users> optUsers = usersRepository.findById(idUser);

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

    public String updateBill(Model model, HttpSession session, Integer id,
            String page, Integer amount, String name,
            Double price, String type, String subType, LocalDate dateBills) {
        Long idUser = Long.parseLong(session.getAttribute("user").toString());
        Optional<Users> optUsers = usersRepository.findById(idUser);
        if (optUsers.isPresent()) {
            Optional<Bills> optBills = billsRepository.findById(Long.parseLong(id.toString()));
            if (optBills.isPresent()) {
                Bills bill = optBills.get();
                bill.setAmount(amount);
                bill.setName(name);
                bill.setPrice(price);
                bill.setType(type);
                bill.setSubType(subType);
                bill.setDateBills(dateBills);
                billsRepository.save(bill);
                model.addAttribute("success", "delete bill");
            } else {
                model.addAttribute("error", "user not found in data base");
            }
        } else {
            model.addAttribute("error", "user not found in data base");
        }
        if (page == null || page.isEmpty()) {
            return Constants.REDIRECT + "/";
        } else {
            return Constants.REDIRECT + page;
        }
    }

    private void beforeMonth(Integer month, Integer year, Integer user, Model model, boolean moth) {
        double total = 0;
        Integer yearBefore = year;
        List<Bills> listBills = billsRepository.getOneMonthBills(month, year, user, month, year);

        Integer mothBefore = month - 1;

        if (mothBefore == 0) {
            mothBefore = 12;
        }

        for (Bills bill : listBills) {
            total = total + (bill.getPrice() * bill.getAmount());
        }
        if (mothBefore == 12) {
            yearBefore = year - 1;
        }
        Optional<Double> revenuaLastMoth = revenueMonthRepository.getRevenue(mothBefore, yearBefore,user);
        if (moth) {
            if (listBills.isEmpty()) {
                model.addAttribute("existsData", true);
            } else {
                model.addAttribute("billsBefore", listBills);
                model.addAttribute("totalBefore", total);
                model.addAttribute("monthBefore", Month.of(month));
                model.addAttribute("amountBillsBefore", listBills.size() + 2);
                if (revenuaLastMoth.isPresent()) {
                    model.addAttribute("revenueBefore", revenuaLastMoth.get() + Constants.SALARY - total);
                } else {
                    model.addAttribute("revenueBefore", Constants.SALARY - total);
                }
            }

        } else {
            if (listBills.isEmpty()) {
                model.addAttribute("existsDatabefore", true);
            } else {
                model.addAttribute("bills", listBills);
                model.addAttribute("total", total);
                model.addAttribute("month", Month.of(month));
                model.addAttribute("amountBills", listBills.size() + 2);
                if (revenuaLastMoth.isPresent()) {
                    model.addAttribute("revenue", revenuaLastMoth.get() + Constants.SALARY - total);
                } else {
                    model.addAttribute("revenue", Constants.SALARY - total);
                }
            }

        }

    }

    private Long getIdUser(String username) {
        return usersRepository.findByUserName(username).get().getId();
    }
}
