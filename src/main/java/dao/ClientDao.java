package dao;

import models.user.Client;

import java.util.List;


public interface ClientDao extends EntityDao<Client> {

    List<Client> findByFirstName(String firstName);

    List<Client> findByLastName(String lastName);

    Client findByUsername(String username);

    Client findByEmail(String email);

    boolean removeByUsername(String username);
}
