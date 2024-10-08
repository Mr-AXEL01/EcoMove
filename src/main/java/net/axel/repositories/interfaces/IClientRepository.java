package net.axel.repositories.interfaces;

import net.axel.models.dto.ClientDto;
import net.axel.models.entities.Client;

import java.util.List;

public interface IClientRepository {
    public void addClient(Client client);
    public Client getClientByEmail(String email);
    public List<Client> getAllClients();
    public void updateClient(Client client);
    public void deleteClient(String email);
}
