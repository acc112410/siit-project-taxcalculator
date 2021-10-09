package com.java.siit.taxcalculator.controller.business;


import com.java.siit.taxcalculator.domain.Mappers.PfaMapper;
import com.java.siit.taxcalculator.domain.entity.LoginEntity;
import com.java.siit.taxcalculator.domain.entity.business.PfaEntity;
import com.java.siit.taxcalculator.domain.entity.business.PfiEntity;
import com.java.siit.taxcalculator.domain.model.business.PfaDTO;
import com.java.siit.taxcalculator.service.LoginService;
import com.java.siit.taxcalculator.service.business.PfaService;
import lombok.AllArgsConstructor;
//import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.parameters.P;

import com.java.siit.taxcalculator.domain.entity.business.PfaEntity;
import com.java.siit.taxcalculator.service.business.PfaService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.*;


import java.lang.reflect.Array;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/pfa")
@AllArgsConstructor
public class PfaController {
    private final PfaService pfaService;

    private final LoginService loginService;


    @RequestMapping("/{id}")
    public ModelAndView createCalcul(@PathVariable(name = "id") Long id) {

        ModelAndView modelAndView = new ModelAndView("pfa");
        LoginEntity loginEntity = loginService.get(id);
        System.out.println(loginEntity.getId());
        PfaEntity pfaEntity = new PfaEntity();
        pfaEntity.setLoginId(loginEntity.getId());
        modelAndView.addObject("pfaEntity", pfaEntity);
        return modelAndView;
    }

    @PostMapping("/saveCalcul")
    public RedirectView saveCalcul(PfaEntity pfaEntity) {

        pfaService.createPfa(pfaService.toDto(pfaEntity));

        return new RedirectView("http://localhost:8080/pfa/taxes/" + Long.toString(pfaEntity.getLoginId()));
    }

    @GetMapping("/taxes/{id}")
    public ModelAndView afisareTaxe(@PathVariable("id") Long id, PfaEntity pfaEntity) {
        ModelAndView modelAndView = new ModelAndView("pfaTaxes");
        List<PfaEntity> lista = pfaService.findAllByLoginId(id);
//        List<PfaEntity> list = new ArrayList<PfaEntity>();
//
//        for (int i = 0; i < lista.size(); i++) {
//            if (lista.get(i).getLoginId() == id) {
//                list.add(lista.get(i));
//            }
//        }
        modelAndView.addObject("pfaLista", lista);
        return modelAndView;
    }

    @GetMapping ("/taxes/{id}/{fiscalYear}")
    public ModelAndView afisareTaxeDupaAnFiscal(@PathVariable("id") Long id, @PathVariable("fiscalYear") Long fiscalYear){
        ModelAndView modelAndView = new ModelAndView("pfaTaxes2");
        List<PfaEntity> listAni = pfaService.findAllByLoginId(id);
        List <PfaEntity> lista = pfaService.findAllByFiscalYearAndLoginId(fiscalYear,id);
        modelAndView.addObject("pfaLista", lista);
        modelAndView.addObject("pfaLista2",listAni);
        return modelAndView;
    }

    @PostMapping("/save")
    public RedirectView saveProduct(PfaEntity pfaEntity) {

        pfaService.updatePfa(pfaService.toDto(pfaEntity));

        return new RedirectView("http://localhost:8080/pfa/taxes/" + Long.toString(pfaEntity.getLoginId()));
    }


    @RequestMapping("/edit/{id}")
    public ModelAndView loginEdit(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("editPfaTaxes");
        PfaEntity pfaEntity = pfaService.get(id);
        modelAndView.addObject("pfaEntity", pfaEntity);
        return modelAndView;
    }

    @RequestMapping("/delete/{id}")
    public RedirectView deleteTaxes(@PathVariable(name = "id") Long id,PfaEntity pfaEntity) {
        PfaEntity pfaEntity1 = pfaService.get(id);
        Long nr = pfaEntity1.getLoginId();
        pfaService.delete(id);
        return new RedirectView("http://localhost:8080/pfa/taxes/" + Long.toString(nr));
    }




    @GetMapping
    public ModelAndView getPage() {
        ModelAndView modelAndView = new ModelAndView();
        PfaEntity pfaEntity = new PfaEntity();
        modelAndView.addObject("pfaEntity", pfaEntity);
        modelAndView.setViewName("pfa");
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView createCalcul(PfaEntity pfaEntity) {

        ModelAndView modelAndView = new ModelAndView();
        pfaService.createPfa(pfaEntity);
        modelAndView.addObject("pfaEntity", new PfaEntity());
        modelAndView.setViewName("pfa");
        return modelAndView;
    }

    @GetMapping("/taxes")
    public ModelAndView afisareTaxe( ) {
        ModelAndView modelAndView = new ModelAndView("pfaTaxes");
        PfaEntity pfaEntity = new PfaEntity();
        List<PfaEntity> lista = pfaService.findAll(pfaEntity);
        System.out.println(pfaService.findAll(pfaEntity));
        modelAndView.addObject("pfaLista", lista);
        return modelAndView;


    }
    // @RequestMapping("/edit/{id}")     public ModelAndView loginEdit(@PathVariable(name = "id") Long id) {         ModelAndView modelAndView = new ModelAndView("editPfaTaxes");         PfaEntity pfaEntity = pfaService.get(id);         pfaService.delete(id);         modelAndView.addObject("pfaEntity", pfaEntity);         return modelAndView;     }
}

