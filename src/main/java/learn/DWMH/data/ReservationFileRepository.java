package learn.DWMH.data;

import learn.DWMH.models.Host;
import learn.DWMH.models.Guest;
import learn.DWMH.models.Reservation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationFileRepository implements ReservationRepository {

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(@Value("${ReservationDataFilePath}")String directory){
        this.directory = directory;
    }

    @Override
    public List<Reservation> findByHostId(String id) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(id)))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, id));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException{
        if (reservation != null) {
            List<Reservation> reservations = findByHostId(reservation.getHostId());
            int nextId = reservations.stream().mapToInt(Reservation::getId).max().orElse(0)+1;
            reservation.setId(nextId);
            reservations.add(reservation);
            writeToFile(reservations, reservation.getHostId());
        }
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException{
        List<Reservation> all = findByHostId(reservation.getHostId());
        for (int i=0; i<all.size(); i++){
            if(all.get(i).getId()==reservation.getId()&&all.get(i).getGuestId()== reservation.getGuestId()){
                all.set(i, reservation);
                writeToFile(all, reservation.getHostId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException{
        List<Reservation> all = findByHostId(reservation.getHostId());
        for (int i=0; i<all.size(); i++){
            if(all.get(i).getId()==reservation.getId() && all.get(i).getGuestId()== reservation.getGuestId()){
                all.remove(i);
                writeToFile(all, reservation.getHostId());
                return true;
            }
        }
        return false;
    }


    private String getFilePath(String id) {
        return Paths.get(directory, id + ".csv").toString();
    }

    //not working. why.
    private Reservation deserialize(String[] fields,String hostId){

        Reservation reservation = new Reservation();
        reservation.setId(Integer.parseInt(fields[0]));
        reservation.setStartDate(LocalDate.parse(fields[1]));
        reservation.setEndDate(LocalDate.parse(fields[2]));

        Guest guest = new Guest();
        guest.setId(Integer.parseInt(fields[3]));
        reservation.setGuest(guest);

        reservation.setGuestId(Integer.parseInt(fields[3]));

        Host host = new Host();
        host.setId(hostId);
        reservation.setHost(host);

        reservation.setTotal(BigDecimal.valueOf(Double.parseDouble(fields[4])));
        return reservation;
    }

    private String serialize(Reservation reservation){
        return String.format("%s,%s,%s,%s,%s", reservation.getId(), reservation.getStartDate(), reservation.getEndDate(),
                reservation.getGuestId(), reservation.getTotal());
    }

    protected void writeToFile(List<Reservation> reservations, String id) throws DataException{
        try (PrintWriter writer = new PrintWriter(getFilePath(id))) {

            writer.println(HEADER);

            if (reservations == null) {
                return;
            }

            for (Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }

        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }
}
