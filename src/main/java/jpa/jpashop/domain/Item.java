package jpa.jpashop.domain;

import jpa.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    //== Business Logic ==//

    /**
     * Add Stack
     * @param quantity
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * Stack Down
     */
    public void removeStock(int quntity) {
        int restStock = this.stockQuantity - quntity;
        if (restStock < 0) {
            throw new NotEnoughStockException("Need more Stock");
        }
        this.stockQuantity = restStock;
    }
}
