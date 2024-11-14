package com.ezen.spring.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private BoardDTO bvo;
    private List<FileVO> flist;
}
