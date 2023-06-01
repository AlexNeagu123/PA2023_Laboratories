package com.ro.api.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class PlayerNotFoundException extends EntityNotFoundException {
    public PlayerNotFoundException(Long id) {
        super(String.format("Player with id %d was not found", id));
    }
}
