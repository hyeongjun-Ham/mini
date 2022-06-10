package com.project.mini.service;

import com.project.mini.domain.Contents;
import com.project.mini.dto.ContentsRequestDto;
import com.project.mini.dto.ContentsResponseDto;
import com.project.mini.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ContentsService {

    private final ContentsRepository ContentsRepository;


    // 게시글 작성
    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public Contents createContents(ContentsRequestDto requestDto, String username) {
        String contentsCheck = requestDto.getContents();
        String titleCheck = requestDto.getTitle();
        if (contentsCheck.contains("script") || contentsCheck.contains("<") || contentsCheck.contains(">")) {
            Contents contents = new Contents(requestDto, username, "xss 안돼요,,하지마세요ㅠㅠ");
            ContentsRepository.save(contents);
            return contents;
        }
        if (titleCheck.contains("script") || titleCheck.contains("<") || titleCheck.contains(">")) {
            Contents contents = new Contents("xss 안돼요,,하지마세요ㅠㅠ", username, "xss 안돼요,,하지마세요ㅠㅠ");
            ContentsRepository.save(contents);
            return contents;
        }
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Contents contents = new Contents(requestDto, username);
        ContentsRepository.save(contents);
        return contents;
    }

    // 게시글 조회
    public List<ContentsResponseDto> getContents() {
        List<Contents> contents = ContentsRepository.findAll();
        List<ContentsResponseDto> listContents = new ArrayList<>();
        for (Contents content : contents) {
            // + 댓글 개수 카운팅 (추가 기능)

            ContentsResponseDto contentsResponseDto = ContentsResponseDto.builder()
                    .content(content)
                    .build();
            listContents.add(contentsResponseDto);
        }
        return listContents;
    }


}