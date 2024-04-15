package learn.DWMH.data;

import learn.DWMH.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findByHostId(String id);

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

    boolean delete(Reservation reservation) throws DataException;



}
