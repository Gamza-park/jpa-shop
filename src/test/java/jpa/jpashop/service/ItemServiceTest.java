package jpa.jpashop.service;

import jpa.jpashop.domain.Item;
import jpa.jpashop.domain.Member;
import jpa.jpashop.domain.item.Book;
import jpa.jpashop.repository.ItemRepository;
import jpa.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("Item Save Test")
    public void SignIn() throws Exception {

        // given
        Item item = new Book();
        item.setPrice(1000);
        item.setName("Study Java");


        // when
        Long saveId = itemService.saveItem(item);

        // then
        em.flush();
        assertEquals(item, itemRepository.findOne(saveId));

    }
}
