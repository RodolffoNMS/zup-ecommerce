package com.zup.ecommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zup.ecommerce.models.Client;
import com.zup.ecommerce.exceptions.ClientNotFoundException;
import com.zup.ecommerce.repositories.ClientRepository;

@Service
public class ClientServiceImp implements ClientService {

    private final ClientRepository clientRepository;
    // Lendo, aprendi q é preferível ao @Autowired
    public ClientServiceImp(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }
    
    
    @Override
    public Client creatClient(Client clientToCreate) {
        // Verifica se o cliente já existe no banco de dados
        if(clientToCreate.getId() != null && clientRepository.existsById(clientToCreate.getId())){
            throw new IllegalArgumentException("Esse cliente já existe no Banco de dados.");
        }

        // Salva o cliente no banco de dados e retorna o cliente criado
        return clientRepository.save(clientToCreate);
    }

    @Override
    public Client findClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Cliente com ID " + id + " não encontrado"));
    }

    @Override
    public List<Client> findAllClient() {
        return clientRepository.findAll();
    }

}
