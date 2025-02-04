package com.zup.ecommerce.services;

import java.util.List;

import com.zup.ecommerce.dtos.ClientRequestDTO;
import com.zup.ecommerce.models.Client;

public interface ClientService {

    Client createClient(ClientRequestDTO clientToCreate);
    List<Client> findAllClients();
    Client updateClient(String cpf, ClientRequestDTO clientRequestDTO);
    Client findClientByCpf(String cpf);

}
