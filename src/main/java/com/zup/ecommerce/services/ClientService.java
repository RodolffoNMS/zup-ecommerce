package com.zup.ecommerce.services;

import java.util.List;

import com.zup.ecommerce.models.Client;

public interface ClientService {

    Client creatClient(Client clientToCreate);
    Client findClientById(Long Id);
    List<Client> findAllClient();
   
}
