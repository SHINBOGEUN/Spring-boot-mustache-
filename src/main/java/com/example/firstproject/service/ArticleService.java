package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service // 서비스 선언 (서비스 객체를 스프링부트에 생성)
public class ArticleService {
    @Autowired //DI
    private ArticleRepository articleRepository;

    public List<Article> index(){
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }


    public Article create(ArticleForm dto) {
       Article article =  dto.toEntity();
       if(article.getId() != null){
           return null;
       }
       return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1 : 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("" , id , article.toString());

        //2 : 대상 엔티티 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //3 : 잘못된 요청 처리(대싱이 없거나, id가 다른 경우
        if(target == null || id != article.getId()){
            log.info("잘못된 요청");
            return null;
        }

        //4 : 업데이트
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        //1 : 대상 엔티티 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //2 : 잘못된 요청 처리
        if(target == null){
            return null;
        }
        
        //3 : 대상 삭제 후 응답 반환
        articleRepository.delete(target);
        return target;
    }
}