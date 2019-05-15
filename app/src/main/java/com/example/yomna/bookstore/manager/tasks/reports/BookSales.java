package com.example.yomna.bookstore.manager.tasks.reports;

public class BookSales {
    private String ISBNBookSale;
    private String titleBookSale;

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    private int totalSales;

    public String getISBNBookSale() {
        return ISBNBookSale;
    }

    public void setISBNBookSale(String ISBNBookSale) {
        this.ISBNBookSale = ISBNBookSale;
    }

    public String getTitleBookSale() {
        return titleBookSale;
    }

    public void setTitleBookSale(String titleBookSale) {
        this.titleBookSale = titleBookSale;
    }

}
