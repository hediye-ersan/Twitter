package com.workintech.Twitter.controller;

import com.workintech.Twitter.entity.Comment;
import com.workintech.Twitter.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> addComment(
            @RequestParam Long tweetId,
            @RequestParam Long userId,
            @RequestBody String content) {
        Comment createdComment = commentService.addComment(tweetId, userId, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);//Bu satırda 201 Created döndürülüyor.
    }
    //Yorum güncelleme işlemi yapılırken yorumun sahibi olma durumu kontrol ediliyor.
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestBody String newContent) {
        Comment updatedComment = commentService.updateComment(id, userId, newContent);
        return ResponseEntity.ok(updatedComment);//Bu satırda 200 OK döndürülüyor.
    }
    //Yorum silme işlemi yapılırken yorumun sahibi ya da tweetin sahibi olma durumu kontrol ediliyor.
    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(
            @PathVariable Long id,
            @RequestParam Long userId) {
        commentService.deleteComment(id, userId);
        return ResponseEntity.noContent().build();//Bu satırda 204 No Content döndürülüyor.
        // build() metodu ile ResponseEntity nesnesi oluşturuluyor.
        //.noContent().build() metodu ile 204 No Content döndürülüyor.
        //204 hatası şu demektir : Sunucu isteği başarılı bir şekilde aldı ve işledi, ancak yanıt olarak bir içerik göndermedi.
    }

}
