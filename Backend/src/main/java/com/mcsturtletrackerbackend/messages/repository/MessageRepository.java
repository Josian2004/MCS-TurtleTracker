package com.mcsturtletrackerbackend.messages.repository;

import com.mcsturtletrackerbackend.messages.model.ComputerMessage;
import com.mcsturtletrackerbackend.messages.model.Message;
import com.mcsturtletrackerbackend.messages.model.MessageType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findAllByMessageTypeIn(ArrayList<MessageType> messageTypeList, Pageable pageable);


    long countAllByMessageTypeIn(ArrayList<MessageType> messageTypeList);

    @Query(value = "{_class:'ComputerMessage'}", count = true)
    long countAllByComputerIdAndMessageTypeIn(int computerId, ArrayList<MessageType> messageTypeList);

    @Query("{_class:'ComputerMessage'}")
    List<ComputerMessage> findAllByComputerIdAndMessageTypeIn(int computerId, ArrayList<MessageType> messageTypes, Pageable pageable);


    long count();
}
