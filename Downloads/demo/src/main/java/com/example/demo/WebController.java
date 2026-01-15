package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;

@Controller
public class WebController {

    private final SubscriptionRepository subRepo;
    private final UserRepository userRepo;

    public WebController(SubscriptionRepository subRepo, UserRepository userRepo) {
        this.subRepo = subRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @PostMapping("/login")
    public String loginProcess(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userRepo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Incorrect user name or password!");
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage() { return "register"; }

    @PostMapping("/register")
    public String registerProcess(@RequestParam String username, @RequestParam String password, Model model) {
        if (userRepo.findByUsername(username) != null) {
            model.addAttribute("error", "User name already created!");
            return "register";
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userRepo.save(newUser);
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        User user = (User) session.getAttribute("user");
        model.addAttribute("username", user.getUsername());

        List<Subscription> subscriptions = subRepo.findAll();
        model.addAttribute("subscriptions", subscriptions);

        double monthlySum = calculateMonthlyTotal(subscriptions);
        double yearlySum = calculateYearlyTotal(subscriptions);

        model.addAttribute("monthlyTotal", String.format("%.2f", monthlySum));
        model.addAttribute("yearlyTotal", String.format("%.2f", yearlySum));
        model.addAttribute("weeklyTotal", String.format("%.2f", monthlySum / 4));

        return "dashboard";
    }

  
    @GetMapping("/stats")
    public String statsPage(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        List<Subscription> subscriptions = subRepo.findAll();
        
        double monthlySum = calculateMonthlyTotal(subscriptions);
        
        Subscription mostExpensive = subscriptions.stream()
                .max(Comparator.comparingDouble(Subscription::getPrice)) 
                .orElse(null);

        model.addAttribute("totalSubs", subscriptions.size());
        model.addAttribute("monthlyTotal", String.format("%.2f", monthlySum));
        
        if (mostExpensive != null) {
            model.addAttribute("expName", mostExpensive.getServiceName());
            model.addAttribute("expPrice", mostExpensive.getPrice());
        } else {
            model.addAttribute("expName", "-");
            model.addAttribute("expPrice", "0");
        }

        return "stats";
    }

    private double calculateMonthlyTotal(List<Subscription> subs) {
        double sum = 0;
        for (Subscription sub : subs) {
            if ("Monthly".equals(sub.getBillingCycle())) sum += sub.getPrice();
            else if ("Yearly".equals(sub.getBillingCycle())) sum += sub.getPrice() / 12;
        }
        return sum;
    }
    
    private double calculateYearlyTotal(List<Subscription> subs) {
        double sum = 0;
        for (Subscription sub : subs) {
            if ("Yearly".equals(sub.getBillingCycle())) sum += sub.getPrice();
            else if ("Monthly".equals(sub.getBillingCycle())) sum += sub.getPrice() * 12;
        }
        return sum;
    }

  
    @PostMapping("/add")
    public String addSubscription(@ModelAttribute Subscription subscription) {
        subscription.setActive(true);
        subRepo.save(subscription);
        return "redirect:/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubscription(@PathVariable Long id) {
        subRepo.deleteById(id);
        return "redirect:/dashboard";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/")
    public String home() { return "redirect:/login"; }
}