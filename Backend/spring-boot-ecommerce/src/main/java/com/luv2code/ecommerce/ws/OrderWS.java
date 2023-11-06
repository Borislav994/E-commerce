package com.luv2code.ecommerce.ws;

import com.luv2code.ecommerce.entity.PagedData;
import com.luv2code.ecommerce.jpa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://localhost:4200")
@RestController
@RequestMapping("/api")
public class OrderWS {

    @Autowired
    public OrderService os;

    @GetMapping("/orders/search/findByCustomerEmail")
    public PagedData findByCategoryId(@RequestParam String theEmail,
                                      @RequestParam int page,
                                      @RequestParam int size) {
        return os.findByCustomerEmail(theEmail, page, size);
    }
}
