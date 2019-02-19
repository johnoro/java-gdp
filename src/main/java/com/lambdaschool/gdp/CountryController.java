package com.lambdaschool.gdp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
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

  @GetMapping("/gdp/{name}")
  public Country byName(@PathVariable String name) {
    List<Country> countries = repository.findAll();
    Long id = -1L;

    for (Country country : countries) {
      if (country.getCountry().equals(name)) {
        id = country.getId();
      }
    }

    template.convertAndSend(GdpApplication.QUEUE_NAME, "Someone looked up " + name);

    // lambda expressions need a variable that's final (or effectively final);
    // so only, in any case, assigned to once,
    // although our country names are unique
    final Long finalId = id;

    return repository.findById(id)
      .orElseThrow(() -> new CountryNotFoundException(finalId));
  }

  @PostMapping("/gdp")
  public List<Country> addCountries(@RequestBody List<Country> countries) {
    return repository.saveAll(countries);
  }
}
