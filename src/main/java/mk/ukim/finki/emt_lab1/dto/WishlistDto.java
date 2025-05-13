
package mk.ukim.finki.emt_lab1.dto;

import java.util.List;
import mk.ukim.finki.emt_lab1.model.domain.Wishlist;

public record WishlistDto(Long id, UpdateUserDto userDto, List<UpdateBookDto> bookDtoList) {
    public WishlistDto(Long id, UpdateUserDto userDto, List<UpdateBookDto> bookDtoList) {
        this.id = id;
        this.userDto = userDto;
        this.bookDtoList = bookDtoList;
    }

    public static WishlistDto from(Wishlist wishlist) {
        return new WishlistDto(wishlist.getId(), UpdateUserDto.from(wishlist.getUser()), UpdateBookDto.from(wishlist.getBooks()));
    }

    public Long id() {
        return this.id;
    }

    public UpdateUserDto userDto() {
        return this.userDto;
    }

    public List<UpdateBookDto> bookDtoList() {
        return this.bookDtoList;
    }
}