package com.java.siit.taxcalculator.controller.business;


import com.java.siit.taxcalculator.domain.entity.business.PfaEntity;
import com.java.siit.taxcalculator.domain.entity.business.SrlEntity;
import com.java.siit.taxcalculator.service.business.SrlService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/srl")
@AllArgsConstructor
public class SrlController {

    private final SrlService srlService;
//    @GetMapping
//    public ModelAndView getPage() {
//        ModelAndView modelAndView = new ModelAndView();
//        SrlEntity srlEntity = new SrlEntity();
//        modelAndView.addObject("srlEntity", srlEntity);
//        modelAndView.setViewName("srl");
//        return modelAndView;
//    }

    @PostMapping()
    public ModelAndView createCalcul(SrlEntity srlEntity) {

        ModelAndView modelAndView = new ModelAndView();
        srlService.createSRL(srlEntity);
        modelAndView.addObject("srlEntity", new SrlEntity());
        modelAndView.setViewName("srl");
        return modelAndView;
    }


    @GetMapping("/taxes/{user_id}")
    String afisareTaxe(@PathVariable("user_id") Long userId, Principal principal, Model model) {
        SrlEntity srlEntity = new SrlEntity();
        List<SrlEntity> lista = srlService.findAll(srlEntity);
        System.out.println(srlService.findAll(srlEntity));
        model.addAttribute("srlLista", lista);
        return "srlTaxes";

    }



    @GetMapping("/taxes")
    public void afisareTaxe(SrlEntity srlEntity) {
        ModelAndView modelAndView = new ModelAndView();
        srlService.findAll(srlEntity);
        modelAndView.addObject("srlTaxes", new PfaEntity());
        modelAndView.setViewName("srlTaxes");
        System.out.println(srlService.findAll(srlEntity));
    }

    @PostMapping("/save")
    public RedirectView saveProduct(@ModelAttribute("srlEntity") SrlEntity srlEntity) {
        srlService.createSRL(srlEntity);
        return new RedirectView("http://localhost:8080/srl/taxes/1");
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView loginEdit(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("editSrlTaxes");
        SrlEntity srlEntity = srlService.get(id);
        srlService.delete(id);
        modelAndView.addObject("srlEntity", srlService);
        return modelAndView;
    }

    @RequestMapping("/delete/{id}")
    public RedirectView deleteTaxes(@PathVariable(name = "id") Long id) {
        srlService.delete(id);
        return new RedirectView("http://localhost:8080/srl/taxes/1");
    }

}
