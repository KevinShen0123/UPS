package amazon_ups.controller;
import amazon_ups.CustomUserRepository;
import amazon_ups.Delivery;
import amazon_ups.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;
import amazon_ups.UserEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Controller
public class HomeController {
    @Autowired
    private CustomUserRepository repo;
    @PersistenceContext
    private EntityManager entityManager;
    @PostMapping("/start/search")
    public void searchOrder( String trackingnumber, Model model) {
        // Use a named query to select orders based on the tracking number
        String queryString = "SELECT d FROM Delivery d WHERE d.package_id = :trackingNumber";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("trackingNumber", trackingnumber);

        // Execute the query and retrieve the results
        List<Delivery> resultList = query.getResultList();

        // Create a list of order details from the results
        Map<String, Object> orderDetailsMap = new HashMap<>();

        for (Delivery delivery : resultList) {
            // Add relevant details from the Delivery entity to the list
            // For example, you can access delivery.getId(), delivery.getCustomerName(), etc.
            orderDetailsMap.put("orderId", delivery.order.order_id);
            orderDetailsMap.put("package_id", delivery.package_id);
            orderDetailsMap.put("status", delivery.D_STATUS);
        }

        // Add the list of order details to the model
        model.addAttribute("orders", orderDetailsMap);

        // Return the name of the view to display the results (you can customize this)
    }
    @PostMapping("/start/registerpost")
    public String registerUser(String lusername,String lpassword,String lemail,Model model){
        UserEntity user=new UserEntity();
        user.setUsername(lusername);
        user.setEmail(lemail);
        user.setPassword(lpassword);
        System.out.println("Post mapping called!!!!!!!!!!!!!!");
        try{
            repo.saveUser(user);
            model.addAttribute("rresult",1);
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("rresult",0);
            return "register";
        }
        return "register";
    }
}
