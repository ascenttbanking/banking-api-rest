package com.ascentt.bankingservice.repository;

import com.ascentt.bankingservice.model.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepositorio extends CrudRepository<Ticket, Long> {
}
