package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    Scanner sc;
    int lastId;
    List<Quotation> quotationList;

    App() {
        sc = new Scanner(System.in);
        lastId = 0;
        quotationList = new ArrayList<>();
    }

    void run() {
        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine();

            switch (cmd) {
                case "종료":
                    return;
                case "등록":
                    actionWrite();
                    break;
                case "목록":
                    actionShow();
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

        Quotation quote = new Quotation(id, content, author);
        quotationList.add(quote);
        System.out.printf("%d번 명령이 등록되었습니다.\n", id);
    }

    void actionShow() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        if(quotationList.size() == 0) {
            System.out.println("등록된 명언이 없습니다.");
            return;
        }

        for (int i = quotationList.size() - 1; i >= 0; i--) {
            Quotation quote = quotationList.get(i);
            System.out.printf("%d / %s / %s\n", quote.getId(), quote.getAuthor(), quote.getContent());
        }
    }
}