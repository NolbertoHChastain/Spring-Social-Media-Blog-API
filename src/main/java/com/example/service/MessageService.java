package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Add {@code Message} to database.
     * 
     * @param message to persist
     * @return the persisted {@code Message} 
     */
    public Message addMessage(Message message) {
        if (messageRepository.existsById(message.getPostedBy())) {
            String messageText = message.getMessageText();
            if (!(messageText.isBlank() || messageText.length() > 255)) return messageRepository.save(message);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Message details");
    }

    /**
     * Retrieve all {@code Message} records
     * @return all {@code Message} records
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}
