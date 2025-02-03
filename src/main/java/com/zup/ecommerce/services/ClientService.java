package com.zup.ecommerce.services;

import java.util.List;

import com.zup.ecommerce.dtos.ClientRequestDTO;
import com.zup.ecommerce.models.Client;

public interface ClientService {

    Client createClient(ClientRequestDTO clientToCreate);
    Client findClientById(Long Id);
    List<Client> findAllClients();
   
}
