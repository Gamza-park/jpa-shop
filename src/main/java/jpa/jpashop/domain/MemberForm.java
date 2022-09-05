package jpa.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "We Need Member Name")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
