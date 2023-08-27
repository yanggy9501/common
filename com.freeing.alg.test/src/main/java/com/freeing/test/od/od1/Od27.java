package com.freeing.test.od.od1;

import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od27 {
    /*
1
ello
INSERT h

2
hllo
FORWARD 1
INSERT e

2
hell
FORWARD 1000
INSERT o
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        scanner.nextLine();
        String content = scanner.nextLine();
        String[] commands = new String[k];
        for (int i = 0; i < k; i++) {
            commands[i] = scanner.nextLine();
        }
        Text text = new Text(0, content);
        func(text, commands);
    }

    public static void func(Text text, String[] commands) {
        for (String command : commands) {
            String[] split = command.split(" ");
            String cmd = split[0];
            String arg = split[1];
            switch (cmd) {
                case "FORWARD": {
                    text.forward(Integer.parseInt(arg));
                    break;
                }
                case "BACKWARD": {
                    text.backward(Integer.parseInt(arg));
                    break;
                }
                case "SEARCH_FORWARD": {
                    text.search_forward(arg);
                    break;
                }
                case "SEARCH_BACKWARD": {
                    text.search_backward(arg);
                    break;
                }
                case "INSERT": {
                    text.insert(arg);
                    break;
                }
                case "REPLACE": {
                    text.replace(arg);
                    break;
                }
                case "DELETE": {
                    text.delete(Integer.parseInt(arg));
                    break;
                }
            }
        }
        System.out.println(text);
    }

    static class Text {
        int point;

        String content;

        public Text() {
        }

        public Text(int point, String content) {
            this.point = point;
            this.content = content;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void forward(int step) {
            point = Math.min(point + step, content.length());
        }

        public void backward(int step) {
            point = Math.max(point - step, 0);
        }

        public void search_forward(String word) {
            int index = content.indexOf(word);
            if (index > 0) {
                if (index < point) {
                    point = index;
                }
            }
        }

        public void search_backward(String word) {
            int index = content.indexOf(word, point);
            if (index > 0) {
                point = index;
            }
        }

        public void insert(String word) {
            content = content.substring(0, point) + word + content.substring(point);
        }

        public void replace(String word) {
            content = content.substring(0, point) + word;
        }

        public void delete(int x) {
            content = content.substring(0, point) + content.substring(point + x);
        }

        @Override
        public String toString() {
            return "Text{" +
                "point=" + point +
                ", content='" + content + '\'' +
                '}';
        }
    }
}
