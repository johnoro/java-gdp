package com.lambdaschool.gdp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class CountryController {
  private final CountryRepository repository;
  private final RabbitTemplate template;

  public CountryController(CountryRepository repository, RabbitTemplate template) {
    this.repository = repository;
    this.template = template;
  }

  @GetMapping("/names")
  public List<Country> allByName() {
    List<Country> countries = repository.findAll();
    countries.sort((c1, c2) -> c1.getCountry().compareToIgnoreCase(c2.getCountry()));
    return countries;
  }

  @GetMapping("/economy")
  public List<Country> allByGdp() {
    List<Country> countries = repository.findAll();
    countries.sort(Comparator.comparingLong(c -> c.getGdp()));
    Collections.reverse(countries);
    return countries;
  }

  @GetMapping("/total")
  public Country totalGdp() {
    List<Country> countries = repository.findAll();
    Long total = 0L;
    Long id = 0L;

    for (Country country : countries) {
      if (id.equals(country.getId())) {
        id++;
      }
      total += country.getGdp();
    }

    Country country = new Country("Total", total);
    country.setId(id);

    return country;
  }

  @PostMapping("/gdp")
  public List<Country> addCountries(@RequestBody List<Country> countries) {
    return repository.saveAll(countries);
  }
}
