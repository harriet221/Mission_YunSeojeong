package com.ll;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    Scanner sc;
    int lastId;
    List<Quotation> quotationList;
    String fileName;
    ObjectMapper objectMapper;

    App() {
        sc = new Scanner(System.in);
        lastId = 0;
        quotationList = new ArrayList<>();
        fileName = "data.txt";
        objectMapper = new ObjectMapper();
    }

    void run() {
        try {
            loadValueFromFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("파일을 불러올 수 없습니다.");
        }

        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine();
            Rq rq = new Rq(cmd);

            switch (rq.getAction()) {
                case "종료":
                    actionSave();
                    return;
                case "등록":
                    actionWrite();
                    break;
                case "목록":
                    actionShow();
                    break;
                case "삭제":
                    actionRemove(rq);
                    break;
                case "수정":
                    actionModify(rq);
                    break;
                case "빌드":
                    actionBuild();
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
        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
    }

    void actionShow() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        if (quotationList.size() == 0) {
            System.out.println("등록된 명언이 없습니다.");
            return;
        }

        for (int i = quotationList.size() - 1; i >= 0; i--) {
            Quotation quote = quotationList.get(i);
            System.out.printf("%d / %s / %s\n", quote.getId(), quote.getAuthor(), quote.getContent());
            lastId = quote.getId();
        }
    }

    int getIndexOfId(int id) {
        for (int i = 0; i < quotationList.size(); i++) {
            Quotation quote = quotationList.get(i);
            if (id == quote.getId()) {
                return i;
            }
        }
        return -1;
    }

    void actionRemove(Rq rq) {
        int id = rq.getParamId("id", 0);

        if (id == 0) {
            System.out.println("id를 정확히 적어주세요");
            return;
        }

        int index = getIndexOfId(id);

        if (index == -1) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        quotationList.remove(index);
        System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
    }

    void actionModify(Rq rq) {
        int id = rq.getParamId("id", 0);

        if (id == 0) {
            System.out.println("id를 정확히 적어주세요");
            return;
        }

        int index = getIndexOfId(id);

        if (index == -1) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        Quotation quote = quotationList.get(index);

        System.out.printf("명언(기존) : %s\n", quote.getContent());
        System.out.print("명언 : ");
        quote.setContent(sc.nextLine());

        System.out.printf("작가(기존) : %s\n", quote.getAuthor());
        System.out.print("작가 : ");
        quote.setAuthor(sc.nextLine());

        System.out.printf("%d번 명언이 수정되었습니다.\n", id);
    }

    void actionSave() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (int i=0; i<quotationList.size(); i++) {
                Quotation quote = quotationList.get(i);
                String quotation = String.format("%d,%s,%s\n", quote.getId(), quote.getAuthor(), quote.getContent());
                writer.write(quotation);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void loadValueFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String quotation = "";
            while ((quotation = reader.readLine()) != null) {
                String[] quotationBits = quotation.split(",", 3);
                int id = Integer.parseInt(quotationBits[0].trim());
                lastId = id;
                String author = quotationBits[1].trim();
                String content = quotationBits[2].trim();

                Quotation quote = new Quotation(id, content, author);
                quotationList.add(quote);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void actionBuild(){
        try {
            FileWriter fileWriter = new FileWriter("data.json");
            for(int i=0; i<quotationList.size(); i++) {
                Quotation quote = quotationList.get(i);

                // 객체를 JSON 문자열로 변환
                String jsonData = objectMapper.writeValueAsString(quote)+"\n";
                fileWriter.write(jsonData);
            }

            fileWriter.close();

            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}