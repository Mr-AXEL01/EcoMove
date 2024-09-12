package net.axel.services.implementations;

import net.axel.models.dto.ClientDto;
import net.axel.models.entities.Client;
import net.axel.repositories.interfaces.IClientRepository;
import net.axel.services.interfaces.IClientService;

import java.util.List;

public class ClientService implements IClientService {
    private final IClientRepository clientRepository;

    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void addClient(Client client) {
        clientRepository.addClient(client);
    }

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.getClientByEmail(email);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    @Override
    public void updateClient(Client client) {
        clientRepository.updateClient(client);
    }

    @Override
    public void deleteClient(String email) {
        clientRepository.deleteClient(email);
    }

}
