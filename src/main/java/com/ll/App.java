package com.ll;

import java.util.Scanner;

public class App {
    Scanner sc;
    App() {
        sc = new Scanner(System.in);
    }
    void run() {
        System.out.println("== 명언 앱 ==");
        while(true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine();

            switch(cmd) {
                case "종료":
                    return;
                default:
                    System.out.println("유효하지 않은 명령입니다.");
                    break;
            }
        }
    }
}