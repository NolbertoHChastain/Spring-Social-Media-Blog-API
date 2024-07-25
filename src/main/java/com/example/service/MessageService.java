package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

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

    /**
     * Retrieve {@code Message} by {@code messageId}
     * 
     * @param messageId
     * @return the existing {@code Message} found by {@code messageId}
     */
    public Message getMessageById(int messageId) {
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        return existingMessage.isPresent() ? existingMessage.get() : null;
    }

    /**
     * Deletes {@code Message} by {@code messageId}
     * 
     * @param messageId
     * @return updated rows (1 or 0)
     */
    public int deleteMessageById(int messageId) {
        Message existingMessage = this.getMessageById(messageId);
        if (existingMessage != null) {
            messageRepository.deleteById(messageId);
            return 1;
        } else return 0;
    }
    
    /**
     * Update {@code messageText} given {@code messageId}
     * 
     * @param message
     * @return updated rows (1 or 0)
     */
    public int updateMessageTextById(Message message) {
        Message existingMessage = this.getMessageById(message.getMessageId());
        if (existingMessage != null) {
            String updateMessage = message.getMessageText();
            if (!(updateMessage.isBlank() || updateMessage.length() > 255)) {
                messageRepository.updateMessageTextById(updateMessage, existingMessage.getMessageId());
                return 1;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to update non-existent Message");
    }

    public List<Message> getAllMessagesPostedBy(int postedBy) {
        return messageRepository.findAllByPostedBy(postedBy); 
    }

}
