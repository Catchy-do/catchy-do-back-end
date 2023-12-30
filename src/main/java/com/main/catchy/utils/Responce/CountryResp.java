package com.main.catchy.utils.Responce;

import com.main.catchy.utils.CountryBody;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryResp {
    private String id;
    private String text;

    private List<CountryBody> children;


}
