package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j //로깅을 위한 어노테이션이다.
public class ArticleController {
    
    //게시물 작성하는 페이지
    @Autowired //스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결!
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleController(){

        return "articles/new";
    }

    //게시물 작성처리
    @PostMapping("articles/create")
    public String createArticle(ArticleForm form){
//      System.out.println(form.toString());
        log.info(form.toString());

        //1. Dto를 뱐환 ! Entity!
        Article article = form.toEntity();
//        System.out.println(article.toString());
        log.info(article.toString());
        //2. Repository에게 Entity를 DB안에 저장하게 함!
        Article saved = articleRepository.save(article);
//        System.out.println(saved.toString());
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    //해당 id번호 상세페이지 조회
    @GetMapping("/articles/{id}")
//    @PathVariable URL경로에 변수를 넣어주는 어노테이션
    public String show(@PathVariable Long id, Model model){
        log.info("id = " +  id);

        // 1 : id로 데이터를 가져옴!
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos =  commentService.comments(id);
        // orElse는 해당 id값이 없다면 null을 반환해라

        // 2 : 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos" , commentDtos);
        // 3 : 보여줄 페이지 설정!
        return "articles/show";

    }


    //게시물 전체 조회
    @GetMapping("/articles")
    public String index(Model model){

        //1 : 모든 Article을 가져온다
        List<Article> articlEntityList = articleRepository.findAll();
        //2 : 가져온 Article 묶음을 뷰로 전달
        model.addAttribute("articleList",articlEntityList);
        //3 : 뷰 페이지 설정!


        return "articles/index";
    }
    
    //게시물 수정 페이지 이동
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){

        //수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);


        // 모델에 데이터 등록!
        model.addAttribute("article", articleEntity);

        //뷰 페이지 설정
        return "articles/edit";
    }

    //게시물 수정
    @PostMapping("articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());

        //1: DTO를 엔티티로 변환한다.
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        //2: 엔티티를 DB로 저장한다.
        //2-1 : DB에 기존 데이터를 가져온다!
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //2-2 : 기존 데이터가 이싸면 값을 갱신한다.
        if(target != null){
            articleRepository.save(articleEntity);
        }
        //3: 수정 결과 페이지로 리다이렉트 한다.
        return "redirect:/articles/" + articleEntity.getId();
    }
    
    //게시물 삭제
    //RedirectAttributes : return 페이지로 돌아가는데 이때 내가 검색했던 내용이나 그 페이지 그대로 유지하고 싶을때
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어옴");
        //1 : 삭제 대상을 가져온다.
        Article articleEntity = articleRepository.findById(id).orElse(null);
        log.info(articleEntity.toString());
        //2 : 대상을 삭제 한다.
        if(articleEntity !=null){
            articleRepository.delete(articleEntity);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }
        //3 : 결과 페이지로 리다이렉트 한다!

        return "redirect:/articles";
    }
}
