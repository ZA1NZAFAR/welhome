package fr.efrei.database_service.service;

import fr.efrei.database_service.entity.ReservationEntity;
import fr.efrei.database_service.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService implements CRUD<ReservationEntity, Long> {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public ReservationEntity save(ReservationEntity object) {
        return null;
    }

    @Override
    public ReservationEntity findById(Long id) {
        return null;
    }

    @Override
    public ReservationEntity update(Long id, ReservationEntity object) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
