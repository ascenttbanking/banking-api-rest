package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.dto.ReceiptDTO;
import com.ascentt.bankingservice.model.entities.Receipt;
import com.ascentt.bankingservice.repository.ReceiptRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository, ModelMapper modelMapper) {
        this.receiptRepository = receiptRepository;
        this.modelMapper = modelMapper;
    }

    public ReceiptDTO generateReceipt(String transactionId, Double amount, int userId) {
        Receipt receipt = new Receipt(null, transactionId, amount, LocalDateTime.now(), userId);
        Receipt savedReceipt = receiptRepository.save(receipt);
        return modelMapper.map(savedReceipt, ReceiptDTO.class);
    }

    public List<ReceiptDTO> getReceiptsByUserId(int userId) {
        List<Receipt> receipts = receiptRepository.findByUserId(userId);
        return receipts.stream()
                .map(receipt -> modelMapper.map(receipt, ReceiptDTO.class))
                .collect(Collectors.toList());
    }
}
