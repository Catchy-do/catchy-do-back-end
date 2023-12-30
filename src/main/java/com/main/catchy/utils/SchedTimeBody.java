package com.main.catchy.utils;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchedTimeBody {
	private Long userId;
	private List<SchedDays> schedDays;

}
