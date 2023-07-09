package com.freeing.test.od.od1;

import java.util.*;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 *
 * @author yanggy
 */
public class Od23 {
    /*
3 1
3 15 (0)
1 20 (2)
2 10 (3)

4 2
4 20 ()
5 30 ()
2 10 (4,5)
1 40 ()

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ");
        int rows = Integer.parseInt(line[0]);
        String targetDir = line[1];
        HashMap<String, Dir> map = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            line = scanner.nextLine().split(" ");
            Dir dir = new Dir(line[0], Integer.parseInt(line[1]));
            if (line.length == 3) {
                String[] childId =
                    line[2].replace("(", "").replace(")", "").split(",");
                if (childId.length > 0) {
                    dir.children.addAll(Arrays.asList(childId));
                }
            }
            map.put(line[0], dir);
        }
        System.out.println(dfsComputeSize(map, targetDir));
    }

    public static int dfsComputeSize(HashMap<String, Dir> map, String target) {
        Dir dir = map.get(target);
        if (dir == null) {
            return 0;
        }
        if (dir.children.isEmpty()) {
            return dir.size;
        }
        int size = dir.size;
        for (String child : dir.children) {
            size += dfsComputeSize(map, child);
        }
        return size;
    }

    private static class Dir {
        String id;
        List<String> children = new ArrayList<>();
        int size;

        public Dir() {
        }

        public Dir(String id, int size) {
            this.id = id;
            this.size = size;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getChildren() {
            return children;
        }

        public void setChildren(List<String> children) {
            this.children = children;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
