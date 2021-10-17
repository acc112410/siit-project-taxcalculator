package com.java.siit.taxcalculator.controller.business;


//import com.java.siit.taxcalculator.config.EmailConfiguration;
import com.java.siit.taxcalculator.config.EmailConfiguration;
import com.java.siit.taxcalculator.config.EmailSender;
import com.java.siit.taxcalculator.domain.entity.LoginEntity;
import com.java.siit.taxcalculator.domain.entity.business.PfaEntity;
import com.java.siit.taxcalculator.repository.LoginRepository;
import com.java.siit.taxcalculator.repository.business.PfaRepository;
import com.java.siit.taxcalculator.service.LoginService;
import com.java.siit.taxcalculator.service.business.PfaService;
import lombok.AllArgsConstructor;
//import org.jetbrains.annotations.NotNull;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import java.util.*;


import java.util.List;


@Controller
@RequestMapping("/pfa")
@AllArgsConstructor
public class PfaController {
    private final PfaService pfaService;
    private EmailConfiguration emailConfiguration;
    private final LoginService loginService;
    private final LoginRepository loginRepository;
    private final PfaRepository pfaRepository;
    private final EmailSender emailSender;


    @RequestMapping("/{id}")
    public ModelAndView createCalcul(@PathVariable(name = "id") Long id) {

        ModelAndView modelAndView = new ModelAndView("pfa");
        LoginEntity loginEntity = loginService.get(id);
        PfaEntity pfaEntity = new PfaEntity();

        pfaEntity.setLoginId(loginEntity.getId());

        modelAndView.addObject("pfaEntity", pfaEntity);
        return modelAndView;
    }

    @PostMapping("/saveCalcul")
    public RedirectView saveCalcul(PfaEntity pfaEntity) {

        pfaService.createPfa(pfaService.toDto(pfaEntity));
        saveTotalTaxes(pfaEntity);
        pfaEntity.setTotalTaxesById(saveTotalTaxes(pfaEntity));

        System.out.println(pfaEntity.getTotalTaxesById());

        return new RedirectView("http://localhost:8080/pfa/taxes/" + Long.toString(pfaEntity.getLoginId()));
    }




    @PostMapping("/save")
    public RedirectView saveProduct(PfaEntity pfaEntity) {

        pfaService.updatePfa(pfaService.toDto(pfaEntity));
        saveTotalTaxes(pfaEntity);
        pfaEntity.setTotalTaxesById(saveTotalTaxes(pfaEntity));
        System.out.println(pfaEntity.getTotalTaxesById());

        return new RedirectView("http://localhost:8080/pfa/taxes/" + Long.toString(pfaEntity.getLoginId()));
    }

    @PostMapping("/saveFiscalYear")
    public RedirectView saveFiscalYear(PfaEntity pfaEntity) {
        pfaService.save(pfaEntity);
        saveTotalTaxes(pfaEntity);
        pfaEntity.setTotalTaxesById(saveTotalTaxes(pfaEntity));
        System.out.println(pfaEntity.getTotalTaxesById());
        return new RedirectView("http://localhost:8080/pfa/taxes/fiscalYear/" + Long.toString(pfaEntity.getFiscalYear()));
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView loginEdit(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("editPfaTaxes");

        PfaEntity pfaEntity = pfaService.get(id);
        modelAndView.addObject("pfaEntity", pfaEntity);
        return modelAndView;
    }

    @RequestMapping("/taxes/fiscalYear/{id}}")
    public ModelAndView fiscalYearSelector(@PathVariable(name = "id") Long id, PfaEntity pfaEntity, @PathVariable(name = "fiscal_Year") Long fiscalYear) {
        ModelAndView modelAndView = new ModelAndView("fiscalYear");
        List<PfaEntity> lista = pfaService.findAll(pfaEntity);
        List<PfaEntity> list = new ArrayList<PfaEntity>();
        List<PfaEntity> listByYear = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getLoginId() == id) {
                list.add(lista.get(i));
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getFiscalYear() == fiscalYear) {
                listByYear.add(list.get(i));
            }
        }
//        List<PfaEntity> pfaEntity = pfaService.getAllFromFiscalYear(fiscalYear);


        modelAndView.addObject("pfaEntity", pfaEntity);
        return modelAndView;
    }


    @RequestMapping("/delete/{id}")
    public RedirectView deleteTaxes(@PathVariable(name = "id") Long id, PfaEntity pfaEntity) {
        PfaEntity pfaEntity1 = pfaService.get(id);
        Long nr = pfaEntity1.getLoginId();
        pfaService.delete(id);
        System.out.println(pfaEntity.getTotalTaxesById());
        return new RedirectView("http://localhost:8080/pfa/taxes/" + Long.toString(nr));
    }

    @GetMapping("/taxes/{id}")
    public ModelAndView afisareTaxe(@PathVariable("id") Long id, PfaEntity pfaEntity) {
        ModelAndView modelAndView = new ModelAndView("pfaTaxes");
        saveTotalTaxes(pfaEntity);
        pfaEntity.setTotalTaxesById(saveTotalTaxes(pfaEntity));

        List<PfaEntity> lista = pfaService.findAll(pfaEntity);
        List<PfaEntity> list = new ArrayList<PfaEntity>();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getLoginId() == id) {
                list.add(lista.get(i));
            }
        }
        modelAndView.addObject("pfaLista", list);
        modelAndView.addObject("pfaEntity", pfaEntity);
        return modelAndView;
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
        List<PfaEntity> lista = pfaService.findAll(pfaEntity);
        List<Long> pfaTaxesByID = new ArrayList<>();

//        methodThatSumAllTaxes(pfaEntity, lista, pfaTaxesByID);

        modelAndView.addObject("pfaEntity", new PfaEntity());
        modelAndView.setViewName("pfa");
        return modelAndView;
    }

    private long saveTotalTaxes(PfaEntity pfaEntity) {
        List<PfaEntity> lista = pfaService.findAll(pfaEntity);
        List<Long> pfaTaxesByID = new ArrayList<>();

        int sum = 0;
        for (int i = 0; i < lista.size(); i++) {
            pfaTaxesByID.add(lista.get(i).getTaxesTotal());
        }

        for(int i =0; i<pfaTaxesByID.size();i++)
            sum += pfaTaxesByID.get(i);

//        pfaEntity.setTotalTaxesById(sum);
        return sum;
    }

    @GetMapping("/taxes")
    public ModelAndView afisareTaxe() {
        ModelAndView modelAndView = new ModelAndView("pfaTaxes");
        PfaEntity pfaEntity = new PfaEntity();
        List<PfaEntity> lista = pfaService.findAll(pfaEntity);
        System.out.println(pfaService.findAll(pfaEntity));
        saveTotalTaxes(pfaEntity);
        modelAndView.addObject("pfaLista", lista);
        return modelAndView;
    }

    @PostMapping("/sendEmail")
    public void sendFeedback() throws MessagingException {
       emailSender.sendEmail();
    }


    // @RequestMapping("/edit/{id}")     public ModelAndView loginEdit(@PathVariable(name = "id") Long id) {         ModelAndView modelAndView = new ModelAndView("editPfaTaxes");         PfaEntity pfaEntity = pfaService.get(id);         pfaService.delete(id);         modelAndView.addObject("pfaEntity", pfaEntity);         return modelAndView;     }
}

