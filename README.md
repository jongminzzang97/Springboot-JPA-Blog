# JPABLOG

Spring Boot를 이용하여 만든 간단한 게시판

![총시연](https://user-images.githubusercontent.com/80828047/232826111-2ba4d308-13e6-4d4a-ace1-b7789a44942d.gif)


<br>
<br>

## 구현 내용
- 회원가입
    - BCryptPasswordEncoder를 이용하여 Password를 암호화
    - ID에 대한 중복검사 진행

- 로그인
    - Spring Security를 이용한 세션방식의 로그인
    - OAuth2를 이용한 카카오 로그인

- 게시글
    - 게시글을 작성
    - 게시글 목록을 4개 씩 보여주도록 페이징 처리
    - 삭제/수정 기능 구현

- 댓글
    - 게시글과의 매핑 관계를 이용한 댓글 조회
    - 댓글을 불러올 때 회원 id 포함

<br>
<br>

## 사용기술
- Spring Boot
- JPA
- MySQL
- AJAX


<br>
<br>

## 각 기능 시연
    
      
- 회원가입  
    - 회원가입
    ![회원가입](https://user-images.githubusercontent.com/80828047/232820024-3f115c84-95af-4383-a2c8-fd5f46a5e64e.gif)

    - 회원 정보 수정
    ![회원정보수정](https://user-images.githubusercontent.com/80828047/232826938-9f7f0502-f1ec-4ed0-8d73-480c67ef16ae.gif)

- 로그인
    - Spring Security가 제공하는 기능을 이용
    ![로그인](https://user-images.githubusercontent.com/80828047/232820323-b128a614-9add-41b9-bef9-0e3635f77bf1.gif)
    - OAuth2를 이용한 카카오톡 로그인
    ![카카오톡로그인](https://user-images.githubusercontent.com/80828047/232827090-7da63e85-ed57-459a-add7-8d3c485e4250.gif)

- 게시글
    - 작성
    ![글쓰기](https://user-images.githubusercontent.com/80828047/232827147-cdd72790-9766-4820-bb13-b878ffcc9f9d.gif)
    - 삭제
    ![글수정삭제](https://user-images.githubusercontent.com/80828047/232827169-1484f635-94bd-4682-8184-bf0d32387018.gif)
    - 게시글 목록 조회
    ![페이징](https://user-images.githubusercontent.com/80828047/232828468-478c7d3b-fb84-4eb0-9a38-4720beb51188.gif)

- 댓글 작성
    - 조회/작성/삭제
    ![댓글](https://user-images.githubusercontent.com/80828047/232828351-17fb550e-dc07-4281-a767-fc354875c416.gif)

