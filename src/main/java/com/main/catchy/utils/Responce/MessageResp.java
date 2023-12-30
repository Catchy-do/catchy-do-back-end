package com.main.catchy.utils.Responce;

import java.util.List;

import com.main.catchy.utils.MessageBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResp {
private String hashids;
private List<MessageBody> details;

}
