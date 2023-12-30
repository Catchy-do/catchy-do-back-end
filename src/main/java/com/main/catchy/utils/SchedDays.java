package com.main.catchy.utils;

import com.main.catchy.utils.Responce.ScheTimeResp;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchedDays {
    private Long dayId;
    private String name;
    private List<ScheTimeResp> schdtime;
}
