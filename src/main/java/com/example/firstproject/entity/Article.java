package com.example.firstproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor // 생성자
@ToString
@Getter
@Setter
@NoArgsConstructor //디폴트 생성자를 추가!
public class Article {

    @Id //대푯값을 지정! = 주민등록번호 같은
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id를 자동 생성 어노테이션
    private Long id;

    @Column
    private String title;

    @Column
    private String content;


    //제목과 내용이 null
    public void patch(Article article) {
        if(article.title != null){
            this.title = article.title;
        }
        if (article.content != null) {
            this.content = article.content;
        }
    }
}
