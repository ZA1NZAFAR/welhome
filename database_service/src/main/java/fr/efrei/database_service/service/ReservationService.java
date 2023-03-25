package main.java.fr.efrei.database_service.service;

import main.java.fr.efrei.database_service.entity.Reservation;
import main.java.fr.efrei.database_service.repository.ReservationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Long save(ReservationVO vO) {
        Reservation bean = new Reservation();
        BeanUtils.copyProperties(vO, bean);
        bean = reservationRepository.save(bean);
        return bean.getReservationId();
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    public void update(Long id, ReservationUpdateVO vO) {
        Reservation bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        reservationRepository.save(bean);
    }

    public ReservationDTO getById(Long id) {
        Reservation original = requireOne(id);
        return toDTO(original);
    }

    public Page<ReservationDTO> query(ReservationQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private ReservationDTO toDTO(Reservation original) {
        ReservationDTO bean = new ReservationDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Reservation requireOne(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
