package org.launchcode.stocks.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

package org.launchcode.stocks.entity.Greeting;

@Controller
public class GreetingController {

	@GetMapping("/greeting")
    public String greeting(Model model) {
        model.addAttribute("mensagem", "This message came from my Bean");
        model.addAttribute("greeting", new Greeting()); 
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@Valid @ModelAttribute Greeting greeting) {
    	System.out.println("The button was clicked!! Your name is " + greeting.getName());
        return "greeting";
    }
    
}
