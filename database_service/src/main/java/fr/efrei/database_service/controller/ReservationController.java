package main.java.fr.efrei.database_service.controller;

import main.java.fr.efrei.database_service.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public String save(@Valid @RequestBody ReservationVO vO) {
        return reservationService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Long id) {
        reservationService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Long id,
                       @Valid @RequestBody ReservationUpdateVO vO) {
        reservationService.update(id, vO);
    }

    @GetMapping("/{id}")
    public ReservationDTO getById(@Valid @NotNull @PathVariable("id") Long id) {
        return reservationService.getById(id);
    }

    @GetMapping
    public Page<ReservationDTO> query(@Valid ReservationQueryVO vO) {
        return reservationService.query(vO);
    }
}
