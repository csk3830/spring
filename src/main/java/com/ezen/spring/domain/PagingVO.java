package com.ezen.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PagingVO {
    private int pageNo;     // 현재 페이지 번호
    private int qty;    // 한 페이지에 출력되는 게시글 개수

    //search 변수 포함
    private String type;
    private String keyword;

    public PagingVO() {
        pageNo = 1;
        qty = 10;
    }

    public PagingVO(int pageNo, int qty) {
        this.pageNo = pageNo;
        this.qty = qty;
    }

    public int getStartIndex(){
        return (this.pageNo-1)*this.qty;
    }

    //type의 복합 타입을 각각 검색하기 위해 배열로 생성
    //type == twc  [t, w, c]
    public String[] getTypeToArray() {
        return this.type == null ? new String[] {} : this.type.split("");
    }
}
