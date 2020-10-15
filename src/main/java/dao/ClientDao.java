package dao;

import models.user.Client;


public interface ClientDao extends EntityDao<Client> {
    Client findByFirstName(String firstName);

    Client findByLastName(String lastName);

    Client findByUsername(String username);

    Client findByEmail(String email);

    boolean removeByUsername(String username);
}
