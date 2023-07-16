package com.freeing.test.od.od1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * https://renjie.blog.csdn.net/article/details/130921467
 * 回溯
 *
 * @author yanggy
 */
public class Od24 {
    /*
500 3
6
0 80 100
0 90 200
1 50 50
1 70 210
2 50 100
2 60 150

100 1
1
0 90 200

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line1 = scanner.nextLine().split(" ");
        int budget = Integer.parseInt(line1[0]);
        int kind = Integer.parseInt(line1[1]);
        int rows = Integer.parseInt(scanner.nextLine());

        List<Device> list = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            String[] line = scanner.nextLine().split(" ");
            Device device = new Device();
            device.setType(line[0]);
            device.setReliability(Integer.parseInt(line[1]));
            device.setPrice(Integer.parseInt(line[2]));
            list.add(device);
        }

        Map<String, List<Device>> group = list.stream().collect(Collectors.groupingBy(Device::getType));
        backTracking(group, 0, budget, kind, 0, 0, Integer.MAX_VALUE);
        System.out.println(realReliability);
    }

    static int realReliability = -1;
    public static void backTracking(Map<String, List<Device>> group, int startIndex, int s, int n, int cur_s, int cur_n, int cur_rb) {
        if (cur_s > s) {
            return;
        }
        if (cur_n >= n) {
            realReliability = Math.max(realReliability, cur_rb);
            return;
        }

        List<Device> devices = group.get(startIndex + "");
        for (int i = 0; i < devices.size(); i++) {
            cur_n++;
            cur_s +=  devices.get(i).getPrice();
            int tmp_cur_rb = Math.min(cur_rb, devices.get(i).getReliability());
            backTracking(group, startIndex + 1, s, n, cur_s, cur_n, tmp_cur_rb);
            cur_n--;
            cur_s -=  devices.get(i).getPrice();
        }
    }


    static class Device {
        String type;
        int reliability;
        int price;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getReliability() {
            return reliability;
        }

        public void setReliability(int reliability) {
            this.reliability = reliability;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
