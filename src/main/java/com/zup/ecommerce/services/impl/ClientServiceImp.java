package com.zup.ecommerce.services.impl;

import java.util.List;

import com.zup.ecommerce.dtos.ClientRequestDTO;
import com.zup.ecommerce.services.ClientService;
import org.springframework.stereotype.Service;

import com.zup.ecommerce.models.Client;
import com.zup.ecommerce.exceptions.ClientNotFoundException;
import com.zup.ecommerce.repositories.ClientRepository;
import com.zup.ecommerce.utils.ValidationUtils;

@Service
public class ClientServiceImp implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImp(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(ClientRequestDTO clientToCreate) {

        ValidationUtils.validateNotEmpty(clientToCreate.getName(), "Nome do Cliente");
        ValidationUtils.validateCpf(clientToCreate.getCpf());
        ValidationUtils.validateEmail(clientToCreate.getEmail());

        // Verifica se já existe um cliente com o mesmo CPF
        if (clientRepository.existsByCpf(clientToCreate.getCpf())) {
            throw new IllegalArgumentException("Já existe um cliente com este CPF.");
        }
        if (clientRepository.existsByEmail(clientToCreate.getEmail())) {
            throw new IllegalArgumentException("Já existe um cliente com o email '" + clientToCreate.getEmail() + "'.");
        }

        // Converter ClientRequestDTO para Client
        Client client = new Client();
        client.setName(clientToCreate.getName());
        client.setCpf(clientToCreate.getCpf());
        client.setEmail(clientToCreate.getEmail());

        return clientRepository.save(client);
    }

    @Override
    public Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente com ID " + id + " não encontrado"));
    }

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }
}
