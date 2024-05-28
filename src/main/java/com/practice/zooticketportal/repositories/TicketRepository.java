package com.practice.zooticketportal.repositories;



import com.practice.zooticketportal.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findTicketById(Long id);
    List<Ticket> findByUser_AllUserId(Long allUserId);

}
