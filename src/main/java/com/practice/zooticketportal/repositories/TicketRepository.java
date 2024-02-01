package com.practice.zooticketportal.repositories;



import com.practice.zooticketportal.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
