package org.sopt;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
import org.sopt.controller.PostController;
import org.sopt.dto.Request.CreatePostRequest;
import org.sopt.dto.Request.UpdatePostRequestDto;
import org.sopt.dto.Response.commonResponse;
import org.sopt.dto.Response.CreatePostResponse;
import org.sopt.dto.Response.ReadPostResponseDto;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 클라이언트는 Controller만 알면 돼요. Service도 Repository도 몰라도 돼요.
        PostController postController = new PostController();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== 에브리타임 게시판 ===");
            System.out.println("1. 게시글 작성");
            System.out.println("2. 전체 조회");
            System.out.println("3. 단건 조회");
            System.out.println("4. 게시글 수정");
            System.out.println("5. 게시글 삭제");
            System.out.println("0. 종료");
            System.out.print("메뉴 선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("제목: ");
                    String title = scanner.nextLine();
                    System.out.print("내용: ");
                    String content = scanner.nextLine();
                    System.out.print("작성자: ");
                    String author = scanner.nextLine();
                    // 클라이언트가 요청 객체를 만들어서 Controller에 전달
                    CreatePostResponse response = postController.createPost(
                            new CreatePostRequest(title, content, author)
                    );
                    System.out.println(response.message);
                    break;

                case 2:
                    commonResponse<List<ReadPostResponseDto>> postsResponse = postController.getAllPosts();
                    System.out.println(postsResponse.getMessage());

                    List<ReadPostResponseDto> posts = postsResponse.getData();
                    if (posts != null && !posts.isEmpty()) {
                        posts.forEach(p -> System.out.println(p + "\n---"));
                    }
                    break;

                case 3:
                    System.out.print("조회할 게시글 ID: ");
                    commonResponse<ReadPostResponseDto> postResponse = postController.getPost(scanner.nextLong());
                    scanner.nextLine();
                    System.out.println(postResponse.getMessage());

                    if (postResponse.isSuccess() && postResponse.getData() != null) {
                        System.out.println(postResponse.getData());
                    }
                    break;

                case 4:
                    System.out.print("수정할 게시글 ID: ");
                    Long updateId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("새 제목: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("새 내용: ");
                    String newContent = scanner.nextLine();

                    commonResponse<Void> updateResponse = postController.updatePost(
                            new UpdatePostRequestDto(updateId, newTitle, newContent)
                    );
                    System.out.println(updateResponse.getMessage());
                    break;

                case 5:
                    System.out.print("삭제할 게시글 ID: ");
                    commonResponse<Void> deleteResponse = postController.deletePost(scanner.nextLong());
                    System.out.println(deleteResponse.getMessage());
                    scanner.nextLine();
                    break;

                case 0:
                    running = false;
                    System.out.println("👋 프로그램 종료");
                    break;
                default:
                    System.out.println("❗ 잘못된 입력입니다.");
            }
        }
        scanner.close();
    }
}
