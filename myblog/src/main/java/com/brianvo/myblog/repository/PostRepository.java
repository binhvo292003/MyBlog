package com.brianvo.myblog.repository;

import com.brianvo.myblog.domain.PostStatus;
import com.brianvo.myblog.domain.entity.Category;
import com.brianvo.myblog.domain.entity.Post;
import com.brianvo.myblog.domain.entity.Tag;
import com.brianvo.myblog.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);
    List<Post> findAllByStatusAndCategory(PostStatus status, Category category);
    List<Post> findAllByStatusAndTagsContaining(PostStatus status, Tag tag);
    List<Post> findAllByStatus(PostStatus status);
    List<Post> findAllByAuthorAndStatus(User user, PostStatus status);

}
