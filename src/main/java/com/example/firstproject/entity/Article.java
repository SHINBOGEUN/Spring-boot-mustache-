package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor // 생성자
@ToString
@NoArgsConstructor //디폴트 생성자를 추가!
public class Article {

    @Id //대푯값을 지정! = 주민등록번호 같은
    @GeneratedValue // 1, 2, 3 /...... 자동 생성 어노테이션
    private Long id;

    @Column
    private String title;

    @Column
    private String content;


}
