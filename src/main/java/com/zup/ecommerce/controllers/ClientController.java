package com.zup.ecommerce.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.zup.ecommerce.dtos.ClientRequestDTO;
import com.zup.ecommerce.dtos.ClientResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zup.ecommerce.models.Client;
import com.zup.ecommerce.services.ClientService;

@RestController
@RequestMapping("api/client")
public class ClientController {

    private final ClientService clientService;
    
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        // Chama o serviço para criar o cliente
        Client createdClient = clientService.createClient(clientRequestDTO);
        // Converte a entidade criada para ResponseDTO antes de retornar
        ClientResponseDTO responseDTO = convertToResponseDTO(createdClient);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        // Busca o cliente pelo ID
        Client client = clientService.findClientById(id);
        // Converte a entidade para ResponseDTO antes de retornar
        ClientResponseDTO responseDTO = convertToResponseDTO(client);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        // Busca todos os clientes
        List<Client> clients = clientService.findAllClients();
        // Converte a lista de entidades para uma lista de ResponseDTOs
        List<ClientResponseDTO> responseDTOs = clients.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    // Metodo para converter Client para ClientResponseDTO
    private ClientResponseDTO convertToResponseDTO(Client client) {
        ClientResponseDTO responseDTO = new ClientResponseDTO();
        responseDTO.setId(client.getId());
        responseDTO.setName(client.getName());
        responseDTO.setCpf(client.getCpf());
        responseDTO.setEmail(client.getEmail());
        return responseDTO;
    }

}