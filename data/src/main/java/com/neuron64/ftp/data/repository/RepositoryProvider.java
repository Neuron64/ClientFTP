package com.neuron64.ftp.data.repository;

import com.neuron64.ftp.domain.repository.ConnectionRepository;

public class RepositoryProvider {

    private static ConnectionRepository connectionRepository;

    public static ConnectionRepository getConnectionRepository() {
        if (connectionRepository == null) {
            connectionRepository = new ConnectionDataRepository();
        }
        return connectionRepository;
    }

    public static void setConnectionRepository(ConnectionRepository connectionRepository) {
        connectionRepository = connectionRepository;
    }
}