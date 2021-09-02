package com.example.mypage;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MyPageRepository extends CrudRepository<MyPage, Long> {

    MyPage findByOrderId(Long orderId);
    
    MyPage findByDeliveryId(Long deliveryId);
    
    MyPage findByPaymentId(Long paymentId);

}