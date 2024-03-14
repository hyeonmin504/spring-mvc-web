package hello.itemservice.domain;

public enum ItemType {
    BOOK("도서"), FOOD("음식"), ETC("기타");

    private final String description;

    public String getDescription() {
        return description;
    }

    ItemType(String description) {
        this.description = description;
    }
}
