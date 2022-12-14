package com.example.sellyourthing.datatransferobjects;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
// change to string, use final = "
public class PostsSortDto {
    private String city = "";
    private String category = "";
    private String order;
    private String searchText;
}
