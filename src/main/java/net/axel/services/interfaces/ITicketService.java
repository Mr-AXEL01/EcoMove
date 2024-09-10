package net.axel.services.interfaces;

import net.axel.models.dto.TicketDto;
import net.axel.models.entities.Ticket;

import java.util.List;
import java.util.UUID;

public interface ITicketService {
    public boolean addTicket(TicketDto dto);
    public List<Ticket> getAllTickets();
    public List<Ticket> getTicketsByPartner(UUID partnerId);

}
