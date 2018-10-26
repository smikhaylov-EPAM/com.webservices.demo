package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import com.example.demo.soap.countries.CountryInfoServiceSoapType;

@RestController
public class DemoController {

  @Autowired
  @Qualifier("CountryInfoServiceSoap")
  private CountryInfoServiceSoapType countryInfoService;

  @GetMapping("/countries")
  public List<String> fullCountryInfoAllCountries() {
    String separator = "  |  ";
    List<String> countries = new ArrayList<>();
    countryInfoService.fullCountryInfoAllCountries().getTCountryInfo()
        .stream()
        .forEach(c -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(c.getSName()).append(separator)
            .append("Capital: ").append(c.getSCapitalCity()).append(separator)
            .append("Code: ").append(c.getSISOCode()).append(separator)
            .append("Currency: ").append(c.getSCurrencyISOCode());
            countries.add(sb.toString());
        });
    return countries;
  }

  @GetMapping("/flag/{countryISO}")
  public RedirectView flag(@PathVariable String countryISO) {
    return new RedirectView(countryInfoService.countryFlag(countryISO));
  }

}
