package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;


    public List<CommentDto> comments(Long articleId) {
        //조회: 댓글 목록
//        List<Comment> comments =commentRepository.findByArticleId(articleId);
//        //변환: 엔티티 -> dto
//        List<CommentDto> dtos =  new ArrayList<CommentDto>();

//        for (int i = 0; i < comments.size(); i++) {
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }
        //반환
        return commentRepository.findByArticleId(articleId).stream()
                .map(comment -> CommentDto.createCommentDto(comment)).collect(Collectors.toList());

    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        //게시글 조회 및 예외 처리
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException("댓글 생성 실패!"));
        //댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);
        //댓글 엔티티를 DB로 저장
        Comment created = commentRepository.save(comment);
        //DTO로 변경되어 반환

        return CommentDto.createCommentDto(created);
    }
}
