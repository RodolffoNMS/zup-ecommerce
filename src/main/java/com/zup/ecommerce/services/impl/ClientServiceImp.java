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

        if (clientRepository.existsByCpf(clientToCreate.getCpf())) {
            throw new IllegalArgumentException("Já existe um cliente com este CPF.");
        }
        if (clientRepository.existsByEmail(clientToCreate.getEmail())) {
            throw new IllegalArgumentException("Já existe um cliente com o email '" + clientToCreate.getEmail() + "'.");
        }

        Client client = new Client();
        client.setName(clientToCreate.getName());
        client.setCpf(clientToCreate.getCpf());
        client.setEmail(clientToCreate.getEmail());

        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(String cpf, ClientRequestDTO clientRequestDTO) {
        // Validações de entrada
        ValidationUtils.validateNotEmpty(clientRequestDTO.getName(), "Nome do Cliente");
        ValidationUtils.validateEmail(clientRequestDTO.getEmail());

        // Busca o cliente pelo CPF
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClientNotFoundException("Cliente com CPF " + cpf + " não encontrado"));

        // Verifica se o e-mail já está em uso por outro cliente
        if (clientRepository.existsByEmail(clientRequestDTO.getEmail()) &&
                !client.getEmail().equals(clientRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Já existe um cliente com o email '" + clientRequestDTO.getEmail() + "'.");
        }

        // Atualiza os campos do cliente
        client.setName(clientRequestDTO.getName());
        client.setEmail(clientRequestDTO.getEmail());

        // Salva as alterações no banco de dados
        return clientRepository.save(client);
    }

    @Override
    public Client findClientByCpf(String cpf) {
        return clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClientNotFoundException("Cliente com CPF " + cpf + " não encontrado"));
    }

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }
}
