package com.eduardosdl.financecontrol.services;

import com.eduardosdl.financecontrol.dtos.RequestAddressDTO;
import com.eduardosdl.financecontrol.models.Address;
import com.eduardosdl.financecontrol.models.Client;
import com.eduardosdl.financecontrol.repositories.AddressRepository;
import com.eduardosdl.financecontrol.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    AddressRepository repository;

    @Autowired
    ClientRepository clientRepository;

    public void create(RequestAddressDTO addressData, UUID clientId) {
        try {
            var client = clientRepository.getReferenceById(clientId);
            var newAddress = new Address();

            mapToEntity(addressData, newAddress, client);

            repository.save(newAddress);
        } catch (DataAccessException e) {
            // Handle database access exception
            throw new RuntimeException("Error accessing the database while creating the address", e);
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("An unexpected error occurred while creating the address", e);
        }
    }

    public void update(RequestAddressDTO addressData, UUID clientId) {
        try {
            var client = clientRepository.getReferenceById(clientId);
            var updatedAddress = repository.getByClientId(clientId);

            if (updatedAddress == null) {
                throw new RuntimeException("Address not found for client ID: " + clientId);
            }

            mapToEntity(addressData, updatedAddress, client);

            repository.save(updatedAddress);
        } catch (DataAccessException e) {
            // Handle database access exception
            throw new RuntimeException("Error accessing the database while updating the address", e);
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("An unexpected error occurred while updating the address", e);
        }
    }

    private void mapToEntity(RequestAddressDTO addressData, Address newAddress, Client client) {
        newAddress.setClient(client);
        newAddress.setAddressNumber(addressData.addressNumber());
        newAddress.setStreet(addressData.street());
        newAddress.setNeighborhood(addressData.neighborhood());
        newAddress.setCity(addressData.city());
        newAddress.setState(addressData.state());
        newAddress.setZipCode(addressData.zipCode());
    }
}
