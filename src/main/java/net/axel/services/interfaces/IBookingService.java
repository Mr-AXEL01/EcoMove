package net.axel.services.interfaces;

import net.axel.models.entities.Booking;

import java.util.List;
import java.util.UUID;

public interface IBookingService {
    public void addBooking(Booking booking);
    public Booking getBookingById(UUID id);
    public List<Booking> getAllBookings();
    public void cancelBooking(UUID id);
}
