package jpa.jpashop.service;

import jpa.jpashop.domain.*;
import jpa.jpashop.domain.item.Book;
import jpa.jpashop.exception.NotEnoughStockException;
import jpa.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("Make Order Test")
    public void orderMake() throws Exception {
        // given
        Member member = createMember("memberA", new Address("Tokyo", "okubo", "183-0000"));

        Book book = createBook("Study JPA", 2400, 10);

        // when
        int orderCnt = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCnt);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(2400 * orderCnt, getOrder.getTotalPrice());
        assertEquals(8, book.getStockQuantity());
    }

    @Test
    @DisplayName("Over Stock Quantity Value Test")
    public void order_over_StockQuantity() throws Exception {
        // given
        Member member = createMember("memberA", new Address("Tokyo", "okubo", "183-0000"));
        Book book = createBook("Study JPA", 2400, 10);

        int orderCnt = 11;

        // when
        NotEnoughStockException ex = assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCnt);
        });

        // then
        assertEquals(ex.getMessage(), "Need more Stock");

    }

    @Test
    @DisplayName("Order Cancled Test")
    public void orderCancle() throws Exception {
        // given
        Member member = createMember("memberA", new Address("Tokyo", "okubo", "183-0000"));
        Book book = createBook("Study JPA", 2400, 10);

        int orderCnt = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCnt);

        // when
        orderService.cancle(orderId);

        // then
        Order order = orderRepository.findOne(orderId);

        assertEquals(order.getStatus(), OrderStatus.CANCEL);
        assertEquals(book.getStockQuantity(), 10);

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}