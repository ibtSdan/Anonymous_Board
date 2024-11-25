package com.example.anonymous_board.post.service;

import com.example.anonymous_board.exception.InvalidCredentialsException;
import com.example.anonymous_board.exception.NoChangesException;
import com.example.anonymous_board.post.common.Api;
import com.example.anonymous_board.post.common.ApiPagination;
import com.example.anonymous_board.post.common.Pagination;
import com.example.anonymous_board.post.db.PostEntity;
import com.example.anonymous_board.post.db.PostRepository;
import com.example.anonymous_board.post.model.PostDelete;
import com.example.anonymous_board.post.model.PostDto;
import com.example.anonymous_board.post.model.PostRequest;
import com.example.anonymous_board.post.model.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor

public class PostService {
    private final PostRepository postRepository;
    private final PostConverter postConverter;


    public Api<PostDto> create(PostRequest postRequest) {
        var entity = PostEntity.builder()
                .userName(postRequest.getUserName())
                .password(postRequest.getPassword())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .postedAt(LocalDateTime.now())
                .build();
        var dto = postConverter.toDto(postRepository.save(entity));
        return Api.<PostDto>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(dto)
                .build();
    }

    public Api<PostDto> view(Long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> {
                    return new NoSuchElementException("해당 게시글이 존재하지 않습니다. id = "+id);
                });
        var dto = postConverter.toDto(postEntity);
        return Api.<PostDto>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(dto)
                .build();
    }

    public Api<ApiPagination<List<PostDto>>> all(Pageable pageable) {
        var list = postRepository.findAll(pageable);
        var pagination = Pagination.builder()
                .page(list.getNumber())
                .size(list.getSize())
                .currentElements(list.getNumberOfElements())
                .totalPage(list.getTotalPages())
                .totalElements(list.getTotalElements())
                .build();
        var apiPage = ApiPagination.<List<PostDto>>builder()
                .body(list.toList().stream()
                        .map(postConverter::toDto).toList())
                .pagination(pagination)
                .build();
        return Api.<ApiPagination<List<PostDto>>>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(apiPage)
                .build();
    }


    public Api<PostDto> update(PostUpdate postUpdate) {
        // 비밀번호 확인 및 게시글 존재 확인
        var entity =postRepository.findById(postUpdate.getId())
                .map( it -> {
                    // 존재한다면?
                    if(!it.getPassword().equals(postUpdate.getPassword())){
                        throw new InvalidCredentialsException("비밀번호가 다릅니다.");
                    }
                    return it;
                })
                .orElseThrow( () -> {
                    return new NoSuchElementException("해당 게시글이 존재하지 않습니다. id = "+postUpdate.getId());
                });
        if(entity.getTitle().equals(postUpdate.getTitle()) && entity.getContent().equals(postUpdate.getContent())){
            throw new NoChangesException("수정된 내용이 존재하지 않습니다.");
        }

        // 업데이트 후 저장
        entity.setTitle(postUpdate.getTitle());
        entity.setContent(postUpdate.getContent());
        entity.setPostedAt(LocalDateTime.now());
        var dto = postConverter.toDto(postRepository.save(entity));

        return Api.<PostDto>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(dto)
                .build();
    }

    public Api<String> delete(PostDelete postDelete) {
        postRepository.findById(postDelete.getId())
                .map( it -> {
                    if(!it.getPassword().equals(postDelete.getPassword())){
                        throw new InvalidCredentialsException("비밀번호가 다릅니다.");
                    }
                    postRepository.delete(it);
                    return "";
                })
                .orElseThrow( () -> {
                    return new NoSuchElementException("해당 게시글이 존재하지 않습니다. id = "+postDelete.getId());
                });
        return Api.<String>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(postDelete.getId()+ "번째 글이 삭제 되었습니다.")
                .build();
    }
}
