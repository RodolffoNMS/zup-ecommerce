package com.zup.ecommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zup.ecommerce.models.Client;
import com.zup.ecommerce.exceptions.ClientNotFoundException;
import com.zup.ecommerce.repositories.ClientRepository;

@Service
public class ClientServiceImp implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImp(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(Client clientToCreate) {

        if (clientToCreate.getName() == null || clientToCreate.getName().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }
        if (clientToCreate.getCpf() == null || !isValidCpf(clientToCreate.getCpf())) {
            throw new IllegalArgumentException("O CPF informado é inválido.");
        }
        if (clientToCreate.getEmail() == null || !isValidEmail(clientToCreate.getEmail())) {
            throw new IllegalArgumentException("O email informado é inválido.");
        }
        if (clientRepository.findAll().stream()
                .anyMatch(client -> client.getCpf().equals(clientToCreate.getCpf()))) {
            throw new IllegalArgumentException("Já existe um cliente com este CPF.");
        }
        if (clientRepository.findAll().stream()
                .anyMatch(client -> client.getEmail().equalsIgnoreCase(clientToCreate.getEmail()))) {
            throw new IllegalArgumentException("Já existe um cliente com este email.");
        }
        return clientRepository.save(clientToCreate);
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


    //TODO: Melhorar funcoes de validacoes, essas são funcoes simples.
    private boolean isValidCpf(String cpf) {return cpf.matches("\\d{11}");}
    
    private boolean isValidEmail(String email) {return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");}
    

}
