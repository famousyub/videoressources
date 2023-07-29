package com.bookstore.entity;

import javax.persistence.*;

@Entity
public class BookToCartItem {
    private static final long serialVersionUID = 222668L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookVideo book;

    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookVideo getBook() {
        return book;
    }

    public void setBook(BookVideo book) {
        this.book = book;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }
}
