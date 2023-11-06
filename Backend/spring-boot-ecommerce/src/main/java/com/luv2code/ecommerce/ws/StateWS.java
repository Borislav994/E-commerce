package com.luv2code.ecommerce.ws;

import com.luv2code.ecommerce.entity.Country;
import com.luv2code.ecommerce.entity.PagedData;
import com.luv2code.ecommerce.entity.State;
import com.luv2code.ecommerce.jpa.service.CountryService;
import com.luv2code.ecommerce.jpa.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("https://localhost:4200")
@RestController
@RequestMapping("/api")
public class StateWS {

    @Autowired
    public StateService stateService;

    @GetMapping("/states")
    public List<State> findAll(){

        return  stateService.findAll();
    }

    @GetMapping("/states/{stateId}")
    public State findById(@PathVariable int stateId) {

        State state = stateService.findById(stateId);
        if(state == null) {
            throw new RuntimeException("State id not found - " + stateId);
        }
        return  state;
    }
    @GetMapping("/states/search/findByCountryCode")
    public List<State> findByCountryCode(@RequestParam String code){
        return stateService.findByCountryCode(code);
    }
}
