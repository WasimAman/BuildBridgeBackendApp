package com.wasim.buildbridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wasim.buildbridge.model.UserConnections;
import com.wasim.buildbridge.model.enums.InvitationStatus;

@Repository
public interface ConnectionRepository extends JpaRepository<UserConnections, Long> {

    // Get all connections for receiver username + status, sorted oldest → newest
    List<UserConnections> findByReceiver_UsernameAndStatusOrderBySentAtAsc(String receiverUsername,
                                                                           InvitationStatus status);

    // Get all connections for receiver username + status, sorted newest → oldest
    List<UserConnections> findByReceiver_UsernameAndStatusOrderBySentAtDesc(String receiverUsername,
                                                                            InvitationStatus status);
}

