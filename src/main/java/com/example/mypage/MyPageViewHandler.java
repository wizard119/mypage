package com.example.mypage;

import com.example.mypage.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageViewHandler {

    @Autowired MyPageRepository myPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderPlaced_then_CREATE_1(@Payload OrderPlaced orderPlaced) {
        try {
            if (orderPlaced.validate()) {

                System.out.println("\n\n##### listener MyPage orderPlaced : " + orderPlaced.toJson() + "\n\n");

                // view 객체 생성
                MyPage myPage = new MyPage();
                // view 객체에 이벤트의 Value 를 set 함
                myPage.setOrderId(orderPlaced.getId());
                myPage.setCustomerId(orderPlaced.getCustomerId());
                myPage.setProductId(orderPlaced.getProductId());
                myPage.setAddress(orderPlaced.getAddress());
                myPage.setStatus(orderPlaced.getStatus());
                myPage.setAmt(orderPlaced.getAmt());
                myPageRepository.save(myPage);
            }
        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaymentApproved_then_UPDATE_1(@Payload PaymentApproved paymentApproved) {
        try {
            if (paymentApproved.validate()) {
                // view 객체 조회
                MyPage myPage = myPageRepository.findByOrderId(paymentApproved.getOrderId());
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setPaymentId(paymentApproved.getId());
                myPage.setStatus(paymentApproved.getStatus());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryStarted_then_UPDATE_2(@Payload DeliveryStarted deliveryStarted) {
        try {
            if (deliveryStarted.validate()) {
                // view 객체 조회
                MyPage myPage = myPageRepository.findByPaymentId(deliveryStarted.getPaymentId());
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setDeliveryId(deliveryStarted.getId());
                myPage.setStatus("DeliveryStarted");
                // view 레파지 토리에 save
                myPageRepository.save(myPage);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}