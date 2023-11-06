package com.ll;

import java.util.Scanner;

public class App {
    Scanner sc;
    int lastId;

    App() {
        sc = new Scanner(System.in);
        lastId = 0;
    }

    void run() {
        System.out.println("== 명언 앱 ==");
        while(true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine();

            switch(cmd) {
                case "종료":
                    return;
                case "등록":
                    actionWrite();
                    break;
                default:
                    System.out.println("유효하지 않은 명령입니다.");
                    break;
            }
        }
    }

    void actionWrite() {
        System.out.print("명언 : ");
        String content = sc.nextLine();

        System.out.print("작가 : ");
        String author = sc.nextLine();

        lastId++;
        int id = lastId;

        System.out.printf("%d번 명령이 등록되었습니다.\n", id);
    }
}