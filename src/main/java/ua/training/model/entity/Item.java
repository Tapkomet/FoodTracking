package ua.training.model.entity;

import lombok.Data;

@Data
public class Item {
    private int itemId;
    private String name;
    private long price;
    private boolean available;
}
