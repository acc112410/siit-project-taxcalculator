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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/pfa")
@AllArgsConstructor
public class PfaController {
    private final PfaService pfaService;
    private final LoginService loginService;
    private final LoginRepository loginRepository;
    private final PfaRepository pfaRepository;
    private final EmailSender emailSender;
    private EmailConfiguration emailConfiguration;

    @RequestMapping("/{id}")
    public ModelAndView createCalcul(@PathVariable(name = "id") Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailTemp = loginService.findById(id).getEmail();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();

        }
        if (email.equals(emailTemp)) {

            ModelAndView modelAndView = new ModelAndView("pfa");
            LoginEntity loginEntity = loginService.get(id);
            System.out.println(loginEntity.getId());

            PfaEntity pfaEntity = new PfaEntity();
            pfaEntity.setTotalTaxesById(saveTotalTaxes(pfaEntity));
            pfaEntity.setLoginId(loginEntity.getId());
            modelAndView.addObject("pfaEntity", pfaEntity);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("blank");
            PfaEntity pfaEntity = new PfaEntity();
            modelAndView.addObject("pfaEntity", pfaEntity);
            return modelAndView;
        }


    }


    @PostMapping("/saveCalcul")
    public RedirectView saveCalcul(PfaEntity pfaEntity) {

        pfaService.createPfa(pfaService.toDto(pfaEntity));
        saveTotalTaxes(pfaEntity);
        pfaEntity.setTotalTaxesById(saveTotalTaxes(pfaEntity));

        System.out.println(pfaEntity.getTotalTaxesById());

        return new RedirectView("http://localhost:8080/user/pfa/taxes/" + pfaEntity.getLoginId());
    }


    @GetMapping("/taxes/{id}/{fiscalYear}")
    public ModelAndView afisareTaxeDupaAnFiscal(@PathVariable("id") Long id, @PathVariable("fiscalYear") Long fiscalYear) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailTemp = loginService.findById(id).getEmail();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();

        }

        if (email.equals(emailTemp)) {
            ModelAndView modelAndView = new ModelAndView("pfaTaxes2");
            List<PfaEntity> listAni = pfaService.findAllByLoginId(id);
            List<PfaEntity> lista = pfaService.findAllByFiscalYearAndLoginId(fiscalYear, id);
            modelAndView.addObject("pfaLista", lista);
            modelAndView.addObject("pfaLista2", listAni);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("blank");
            PfaEntity pfaEntity = new PfaEntity();
            modelAndView.addObject("pfaEntity", pfaEntity);
            return modelAndView;
        }
    }


    @PostMapping("/save")
    public RedirectView saveProduct(PfaEntity pfaEntity) {

        pfaService.updatePfa(pfaService.toDto(pfaEntity));
        saveTotalTaxes(pfaEntity);
        pfaEntity.setTotalTaxesById(saveTotalTaxes(pfaEntity));
        System.out.println(pfaEntity.getTotalTaxesById());

        return new RedirectView("http://localhost:8080/user/pfa/taxes/" + pfaEntity.getLoginId());
    }

    @PostMapping("/saveFiscalYear")
    public RedirectView saveFiscalYear(PfaEntity pfaEntity) {
        pfaService.save(pfaEntity);
        saveTotalTaxes(pfaEntity);
        pfaEntity.setTotalTaxesById(saveTotalTaxes(pfaEntity));
        System.out.println(pfaEntity.getTotalTaxesById());
        return new RedirectView("http://localhost:8080/pfa/taxes/fiscalYear/" + pfaEntity.getFiscalYear());
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
    public RedirectView deleteTaxes(@PathVariable(name = "id") Long id) {

        PfaEntity pfaEntity1 = pfaService.get(id);
        Long nr = pfaEntity1.getLoginId();
        pfaService.delete(id);
        return new RedirectView("http://localhost:8080/user/pfa/taxes/" + nr);
    }


    @GetMapping("/taxes/{id}")
    public ModelAndView afisareTaxe(@PathVariable("id") Long id, PfaEntity pfaEntity) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailTemp = loginService.findById(id).getEmail();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();

        }

        if (email.equals(emailTemp)) {
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
        } else {
            ModelAndView modelAndView = new ModelAndView("blank");
            ;
            modelAndView.addObject("pfaEntity", null);
            return modelAndView;
        }

    }

//    @GetMapping
//    public ModelAndView getPage() {
//        ModelAndView modelAndView = new ModelAndView();
//        PfaEntity pfaEntity = new PfaEntity();
//        modelAndView.addObject("pfaEntity", pfaEntity);
//        modelAndView.setViewName("pfa");
//        return modelAndView;
//    }



    private long saveTotalTaxes(PfaEntity pfaEntity) {
        List<PfaEntity> lista = pfaService.findAll(pfaEntity);
        List<Long> pfaTaxesByID = new ArrayList<>();

        int sum = 0;
        for (int i = 0; i < lista.size(); i++) {
            pfaTaxesByID.add(lista.get(i).getTaxesTotal());
        }

        for (int i = 0; i < pfaTaxesByID.size(); i++)
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
}

