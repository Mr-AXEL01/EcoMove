package net.axel.models.entities;

import net.axel.models.enums.BookingStatus;

import java.util.UUID;

public class Booking {
    private UUID id;
    private BookingStatus bookingStatus;
    private Client client_email;

    public Booking(UUID id, BookingStatus bookingStatus, Client client_email) {
        this.id = id;
        this.bookingStatus = bookingStatus;
        this.client_email = client_email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Client getClient_email() {
        return client_email;
    }

    public void setClient_email(Client client_email) {
        this.client_email = client_email;
    }
}
