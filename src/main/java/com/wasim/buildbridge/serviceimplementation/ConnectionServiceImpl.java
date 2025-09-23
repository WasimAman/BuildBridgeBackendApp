package com.wasim.buildbridge.serviceimplementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wasim.buildbridge.exception.ConnectionNotFoundException;
import com.wasim.buildbridge.mapper.UserMapper;
import com.wasim.buildbridge.model.User;
import com.wasim.buildbridge.model.UserConnections;
import com.wasim.buildbridge.model.enums.InvitationStatus;
import com.wasim.buildbridge.repository.ConnectionRepository;
import com.wasim.buildbridge.repository.UserRepository;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.responseDTO.ConnectionDTO;
import com.wasim.buildbridge.service.ConnectionService;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ApiResponseDTO sendRequest(String senderUsername, String receiverUsername) {
        try {
            User sender = userRepository.findByUsernameOrEmail(senderUsername)
                    .orElseThrow(
                            () -> new UsernameNotFoundException(
                                    "User not found with sender username: " + senderUsername));

            User receiver = userRepository.findByUsernameOrEmail(receiverUsername)
                    .orElseThrow(
                            () -> new UsernameNotFoundException(
                                    "User not found with receiver username: " + receiverUsername));

            UserConnections connection = new UserConnections();
            connection.setSender(sender);
            connection.setReceiver(receiver);
            connection.setStatus(InvitationStatus.PENDING);
            connection.setSentAt(LocalDateTime.now());

            connectionRepository.save(connection);
            return new ApiResponseDTO(
                    true,
                    "Connection request sent from " + senderUsername + " to " + receiverUsername,
                    null);
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: while sending request");
        }
    }

    @Override
    public ApiResponseDTO acceptRequest(long connectionId) {
        try {
            UserConnections connection = connectionRepository.findById(connectionId).orElseThrow(() -> {
                throw new ConnectionNotFoundException("Connection not found with id: " + connectionId);
            });

            connection.setStatus(InvitationStatus.ACCEPTED);

            connectionRepository.save(connection);
            return new ApiResponseDTO(
                    true,
                    "Connection request accepted (id: " + connectionId + ")",
                    null);
        } catch (ConnectionNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: while accepting request");
        }
    }

    @Override
    public ApiResponseDTO rejectRequest(long connectionId) {
        try {
            UserConnections connection = connectionRepository.findById(connectionId).orElseThrow(() -> {
                throw new ConnectionNotFoundException("Connection not found with id: " + connectionId);
            });

            connection.setStatus(InvitationStatus.REJECTED);

            connectionRepository.delete(connection);
            return new ApiResponseDTO(
                    true,
                    "Connection request rejected (id: " + connectionId + ")",
                    null);
        } catch (ConnectionNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: while rejecting request");
        }

    }

    @Override
    public ApiResponseDTO getAllPendingRequest(String username) {
        List<UserConnections> pendingConnections = connectionRepository
                .findByReceiver_UsernameAndStatusOrderBySentAtDesc(username, InvitationStatus.PENDING);

        ConnectionDTO connections = userMapper.mapToConnectionDTO(pendingConnections);
        return new ApiResponseDTO(
                true,
                "Pending requests for user: " + username,
                connections);
    }

    @Override
    public ApiResponseDTO getUserConnection(String username) {
        User user = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "User not found with username: " + username));

        ConnectionDTO connectionDTO = userMapper.mapToConnectionDTO(user.getSentConnections(),
                user.getReceivedConnections());

        return new ApiResponseDTO(
                true,
                "Connections for user: " + username,
                connectionDTO);
    }

    @Override
    public ApiResponseDTO removeConnection(long connectionId) {
        UserConnections connection = connectionRepository.findById(connectionId).orElseThrow(() -> {
            throw new ConnectionNotFoundException("Connection not found with id: " + connectionId);
        });

        connectionRepository.delete(connection);
        return new ApiResponseDTO(
                true,
                "Connection removed (id: " + connectionId + ")",
                null);
    }
}
