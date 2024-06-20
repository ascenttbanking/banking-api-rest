package com.ascentt.bankingservice;

import com.ascentt.bankingservice.controllers.PaymentController;
import com.ascentt.bankingservice.controllers.ReceiptController;
import com.ascentt.bankingservice.model.dto.PaymentDto;
import com.ascentt.bankingservice.model.dto.PaymentResultDto;
import com.ascentt.bankingservice.model.entities.Receipt;
import com.ascentt.bankingservice.services.PaymentService;
import com.ascentt.bankingservice.services.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BankingServiceApplicationTests {

	@Mock
	private PaymentService paymentService;

	@Mock
	private ReceiptService receiptService;

	@InjectMocks
	private PaymentController paymentController;

	@InjectMocks
	private ReceiptController receiptController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testProcessPayment() {
		PaymentDto paymentDto = new PaymentDto(null, 1L, 250.0, "USD", "123456789", "PENDING", null);
		PaymentResultDto paymentResultDto = new PaymentResultDto(true, "Payment processed successfully", "123456789", 250.0);

		when(paymentService.processPayment(any(PaymentDto.class))).thenReturn(paymentResultDto);

		ResponseEntity<PaymentResultDto> response = paymentController.processPayment(paymentDto);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(paymentResultDto, response.getBody());
		verify(paymentService, times(1)).processPayment(any(PaymentDto.class));
	}

	@Test
	public void testGetAllPayments() {
		List<PaymentDto> paymentDtos = Arrays.asList(new PaymentDto(), new PaymentDto());
		when(paymentService.getAllPayments()).thenReturn(paymentDtos);

		ResponseEntity<List<PaymentDto>> response = paymentController.getAllPayments();

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(paymentDtos, response.getBody());
		verify(paymentService, times(1)).getAllPayments();
	}

	@Test
	public void testGetPaymentById() {
		PaymentDto paymentDto = new PaymentDto();
		when(paymentService.getPaymentById(1L)).thenReturn(paymentDto);

		ResponseEntity<PaymentDto> response = paymentController.getPaymentById(1L);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(paymentDto, response.getBody());
		verify(paymentService, times(1)).getPaymentById(1L);
	}

	@Test
	public void testUpdatePayment() {
		PaymentDto paymentDto = new PaymentDto();
		when(paymentService.updatePayment(anyLong(), any(PaymentDto.class))).thenReturn(paymentDto);

		ResponseEntity<PaymentDto> response = paymentController.updatePayment(1L, paymentDto);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(paymentDto, response.getBody());
		verify(paymentService, times(1)).updatePayment(anyLong(), any(PaymentDto.class));
	}

	@Test
	public void testDeletePayment() {
		doNothing().when(paymentService).deletePayment(1L);

		ResponseEntity<?> response = paymentController.deletePayment(1L);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		verify(paymentService, times(1)).deletePayment(1L);
	}

	@Test
	public void testGetAllReceipts() {
		List<Receipt> receipts = Arrays.asList(new Receipt(), new Receipt());
		when(receiptService.getAllReceipts()).thenReturn(receipts);

		List<Receipt> response = receiptController.getAllReceipts();

		assertNotNull(response);
		assertEquals(receipts, response);
		verify(receiptService, times(1)).getAllReceipts();
	}
}