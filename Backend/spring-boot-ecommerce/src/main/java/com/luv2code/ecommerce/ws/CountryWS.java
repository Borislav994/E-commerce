package com.luv2code.ecommerce.ws;

import com.luv2code.ecommerce.entity.Country;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.jpa.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("https://localhost:4200")
@RestController
@RequestMapping("/api")
public class CountryWS {

    @Autowired
    public CountryService countryService;

    @GetMapping("/countries")
    public List<Country> findAll(){

        return  countryService.findAll();
    }

    @GetMapping("/countries/{countryId}")
    public Country findById(@PathVariable int countryId) {

        Country country = countryService.findById(countryId);
        if(country == null) {
            throw new RuntimeException("Country id not found - " + countryId);
        }
        return  country;
    }
}
