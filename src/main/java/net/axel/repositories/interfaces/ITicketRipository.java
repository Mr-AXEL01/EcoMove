package net.axel.repositories.interfaces;

import net.axel.models.entities.Ticket;

import java.util.List;
import java.util.UUID;

public interface ITicketRipository {
    public void addTicket(Ticket ticket);
    public List<Ticket> getAllTickets();
    public List<Ticket> getTicketsByPartner(UUID id);
}
